package com.ihsm.university.ihsmtestcases.apitests;

import org.testng.annotations.Test;

import com.ihsm.university.utilities.TokenManager;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetUserTest {

    @Test
    public void getUserTest() {

        String token = TokenManager.getToken();

        Response response =
                RestAssured.given()
                        .header("Authorization", token)
                .when()
                        .get("/API/User/GetUser");

        response.prettyPrint();
    }
}