module "vpc" {
  source = "../../../modules/vpc"

  env = var.env
  public_subnet_cidr_blocks = var.public_subnet_cidr_blocks
  vpc_cidr_block = var.vpc_cidr_block
}

resource "aws_ecr_repository" "conference_app" {
  name                 = "conference_app"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_security_group" "conference_allow_http_lb" {
  name        = "conference_allow_http_lb"
  description = "Allow http inbound traffic"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description      = "HTTP from internet"
    from_port        = 80
    to_port          = 80
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "conference_allow_http_lb"
  }
}

resource "aws_security_group_rule" "conference_allow_http_lb_egress" {
  type                     = "egress"
  from_port                = 0
  to_port                  = 0
  protocol                 = "-1"
  security_group_id        = aws_security_group.conference_allow_http_lb.id
  source_security_group_id = aws_security_group.ecs_container.id
}

resource "aws_security_group" "ecs_container" {
  name        = "conference-ecs-container"
  description = "Allow http inbound traffic"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description     = "HTTP from ALB"
    from_port       = 0
    to_port         = 0
    protocol        = "-1"
    security_groups = [aws_security_group.conference_allow_http_lb.id]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "conference-ecs-container"
  }
}

resource "aws_lb" "conference" {
  name                       = "conference-lb-${var.env}"
  internal                   = false
  load_balancer_type         = "application"
  security_groups            = [aws_security_group.conference_allow_http_lb.id]
  subnets                    = module.vpc.public_subnet_ids
  enable_deletion_protection = false
}

resource "aws_lb_listener" "conference" {
  load_balancer_arn = aws_lb.conference.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.conference_lb_tg.arn
  }
}

resource "aws_lb_target_group" "conference_lb_tg" {
  name        = "conference-lb-tg-${var.env}"
  port        = 80
  protocol    = "HTTP"
  target_type = "ip"
  vpc_id      = module.vpc.vpc_id

  health_check {
    interval = 5
    timeout  = 2
  }
}

resource "aws_ecs_cluster" "cluster" {
  name = "cluster-${var.env}"

  setting {
    name  = "containerInsights"
    value = "enabled"
  }
}

resource "aws_ecs_task_definition" "conference" {
  count = var.release_version != "" ? 1 : 0
  family = "conference"
  execution_role_arn = aws_iam_role.ecs_task_execution_role_conference.arn
  container_definitions = jsonencode([
    {
      name      = "web"
      image     = "${aws_ecr_repository.conference_app.repository_url}:${var.release_version}"
      essential = true

      portMappings = [
        {
          containerPort = 3000
        }
      ]
    }
  ])

  requires_compatibilities = [
    "FARGATE"
  ]

  network_mode = "awsvpc"
  cpu          = "256"
  memory       = "512"
}

resource "aws_ecs_service" "conference" {
  count = var.release_version != "" ? 1 : 0
  name            = "conference"
  cluster         = aws_ecs_cluster.cluster.id
  task_definition = aws_ecs_task_definition.conference[0].arn
  desired_count   = 2
  launch_type     = "FARGATE"

  load_balancer {
    target_group_arn = aws_lb_target_group.conference_lb_tg.arn
    container_name   = jsondecode(aws_ecs_task_definition.conference[0].container_definitions)[0].name
    container_port   = 3000
  }

  network_configuration {
    // Using public subnet, as private subnet would require NAT Gateway/VPC Endpoints for AWS ECR for fetching images.
    // Internet Gateway is free but not the NAT Gateway/VPC Endpoint.
    // Restricting access to the instances can be done by SG itself.
    // No point in incurring charges for NAT (~ 3 INR/hr) /VPC Endpoint (~ 0.7 INR/hr)just for this assignment.
    subnets          = module.vpc.public_subnet_ids
    assign_public_ip = true
    security_groups  = [aws_security_group.ecs_container.id]
  }
}
