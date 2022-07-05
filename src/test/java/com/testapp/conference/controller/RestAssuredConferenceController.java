package com.testapp.conference.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredConferenceController {

    final static String ROOT_URI = "http://localhost:8080/api/v1/conference";

    @Test(priority=1)
    public void createConferenceTest() {
        Response response = given()
                .auth().basic("admin@gmail.com","admin1111")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"conference_name\": \"ConferenceTestName\"}")
                .when()
                .post(ROOT_URI);
        System.out.println("createConference Response\n" + response.asString());
        response.then().body("conference_name", Matchers.is("ConferenceTestName"));
        response.then().body("conferenceStatus", Matchers.is("AVAILABLE"));
    }

    @Test(priority=2)
    public void getAllConferencesTest() {
        Response response = given().auth().basic("admin@gmail.com","admin1111")
                .when().get(ROOT_URI);
        System.out.println("getAllConferences Response\n" + response.asString());
        response.then().statusCode(200);
    }

    @Test(priority=3)
    public void checkAvailabilityTest() {
        Response response = given().auth().basic("admin@gmail.com","admin1111")
                .when().get(ROOT_URI+"/1");
        System.out.println("getAllConferences Response\n" +response.asString());
        response.then().statusCode(200);
    }

    @Test(priority=4)
    public void cancelConferenceTest() {
        Response response = given()
                .auth().basic("admin@gmail.com","admin1111")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .params("id",1)
                .when()
                .put(ROOT_URI);
        System.out.println("cancelConferenceTest Response\n" + response.asString());
        response.then().statusCode(200);
    }

    @Test(priority=5)
    public void deleteConferenceTest() {
        Response response = given()
                .auth().basic("admin@gmail.com","admin1111")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .params("id",1)
                .when()
                .delete(ROOT_URI);
        System.out.println("deleteConference Response\n" + response.asString());
        response.then().statusCode(200);
    }

}
