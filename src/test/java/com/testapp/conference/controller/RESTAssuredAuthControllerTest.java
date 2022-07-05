package com.testapp.conference.controller;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RESTAssuredAuthControllerTest {

    final static String ROOT_URI = "http://localhost:8080/api/v1/auth";

    @Test
    void loginTest() {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("email","admin@gmail.com")
                .header("password", "admin1111")
                .when()
                .post(ROOT_URI + "/login");
        System.out.println("loginTest Response\n" + response.asString());
        response.then().statusCode(200);
    }

    @Test
    void signUpTest() {
        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\n" +
                        "    \"firstName\": \"testFirstName\",\n" +
                        "    \"lastName\": \"testLastName\",\n" +
                        "    \"password\": \"w43tr3e4r\",\n" +
                        "    \"email\": \"user@test.com\"\n" +
                        "}")
                .when()
                .post(ROOT_URI + "/signup");
        System.out.println("signUp Response\n" + response.asString());
        response.then().statusCode(200);
    }
}
