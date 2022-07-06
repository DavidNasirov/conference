How to use

1. Make sure you have Java 8 and Maven installed

2. Fork this repository and clone it

$ git clone https://github.com/DavidNasirov/conference

3. Navigate into the folder

$ cd conference

4. Install dependencies

$ mvn install

5. Run the project

$ mvn spring-boot:run

6. Navigate to http://localhost:8080/swagger-ui.html in your browser to check everything is working correctly. 
You can change the default port in the application.yml file

server:
  port: 8080
  
7. Make a POST request to /signup to register your plain user. You should receive a response with valid JWT token.

$ curl -X GET http://localhost:8080/api/v1/auth/signup

By default, I created admin when application started 

$  email:    admin@gmail.com;
$  password: admin1111;

8. Make a POST request to /login and get a valid JWT token

$ curl -X GET http://localhost:8080/api/v1/auth/login

9. Add the JWT token as a Header parameter and call other endpoint like GET request to /conference, should return all conferences, but don't forget to create them before that :) 
$ curl -X GET http://localhost:8080/api/v1/conference -H 'Authorization: Bearer <JWT_TOKEN>'
