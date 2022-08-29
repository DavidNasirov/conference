terraform {
  backend "s3" {
    bucket         = "conference-app-terraform-state"
    key            = "dev/ecs/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "conference-state-locks"
    encrypt        = true
    profile        = "default"
  }
}
