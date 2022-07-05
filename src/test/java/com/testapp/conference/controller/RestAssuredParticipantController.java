package com.testapp.conference.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class RestAssuredParticipantController {
    final static String ROOT_URI = "http://localhost:8080/api/v1/participant";

    @Test(priority=1)
    public void createParticipantTest() {
        Response response = given()
                .auth().basic("admin@gmail.com","admin1111")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"first_name\": \"ParticipantTestName\",\"last_name\": \"ParticipantTestLastName\"}")
                .when()
                .post(ROOT_URI);
        System.out.println("createParticipant Response\n" + response.asString());
        response.then().statusCode(200);
    }


    @Test(priority=2)
    public void getParticipantByIdTest() {
        Response response = given().auth().basic("admin@gmail.com","admin1111").get(ROOT_URI + "/1");
        System.out.println("getAllParticipant Response\n" + response.asString());
        response.then().statusCode(200);
    }

    @Test(priority=3)
    public void getAllParticipantTest() {
        Response response = given().auth().basic("admin@gmail.com","admin1111").get(ROOT_URI);
        System.out.println("getAllParticipant Response\n" + response.asString());
        response.then().statusCode(200);
    }


    @Test(priority=4)
    public void addOrRemoveParticipantToConferenceTest() {
        Response response = given()
                .auth().basic("admin@gmail.com","admin1111")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .params("participantId",1)
                .when()
                .put(ROOT_URI);
        System.out.println("addOrRemoveParticipantToConferenceTest Response\n" + response.asString());
        response.then().statusCode(200);
    }

    @Test(priority=5)
    public void deleteParticipantTest() {
        Response response = given()
                .auth().basic("admin@gmail.com","admin1111")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .params("participantId",1)
                .when()
                .delete(ROOT_URI);
        System.out.println("deleteParticipant Response\n" + response.asString());
        response.then().statusCode(200);
    }


}
