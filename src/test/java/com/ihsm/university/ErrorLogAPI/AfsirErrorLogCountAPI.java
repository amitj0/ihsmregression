package com.ihsm.university.ErrorLogAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AfsirErrorLogCountAPI {

    @Test
    public void getTodayErrorLogCount() {

        RestAssured.baseURI = "https://liveafsir.afsir.in";

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        String requestBody = "{\n" +
                "  \"intType\": 0,\n" +
                "  \"dttDate\": \"" + todayDate + "\"\n" +
                "}";

        Response response =
                given()
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                .when()
                    .post("/API/Common/GetErrorLogList")
                .then()
                    .extract()
                    .response();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "API status code mismatch");

        List<Object> logs = response.jsonPath().getList("data.lstLogs");

        int todayErrorLogCount = logs == null ? 0 : logs.size();

        System.out.println("Today Date: " + todayDate);
        System.out.println("Today Error Log Count: " + todayErrorLogCount);

        Assert.assertTrue(todayErrorLogCount >= 0, "Error log count should not be negative");

       
    }
}