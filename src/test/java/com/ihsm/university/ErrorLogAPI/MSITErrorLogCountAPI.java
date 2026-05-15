package com.ihsm.university.ErrorLogAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MSITErrorLogCountAPI {

    @Test
    public void captureCurrentDateErrorLogCount() {

        RestAssured.baseURI = "https://intelsmsitapi.softsolanalytics.com";

        String loginPayload = "{\n" +
                "  \"strEmail\": \"emp-001\",\n" +
                "  \"strPassword\": \"tech@7id=1\",\n" +
                "  \"intSource\": 1\n" +
                "}";

        Response loginResponse =
                given()
                    .header("Content-Type", "application/json")
                    .body(loginPayload)
                .when()
                    .post("/API/User/UserLogin")
                .then()
                    .extract()
                    .response();

        System.out.println("Login HTTP Status Code: " + loginResponse.getStatusCode());
        System.out.println("Login Response: " + loginResponse.asPrettyString());

        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login HTTP status failed");
        Assert.assertEquals(loginResponse.jsonPath().getInt("statusCode"), 200, "Login API failed");

        String accessToken = loginResponse.jsonPath().getString("access_Token");

        Assert.assertNotNull(accessToken, "Access token is null");
        Assert.assertFalse(accessToken.trim().isEmpty(), "Access token is empty");

        System.out.println("Captured Token: " + accessToken);

        // For current date
        String payloadDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // For particular date, use this instead:
//         String payloadDate = "2026-05-05";

        String errorLogPayload = "{\n" +
                "  \"intType\": 0,\n" +
                "  \"dttDate\": \"" + payloadDate + "\"\n" +
                "}";

        Response errorLogResponse =
                given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", accessToken)
                    .header("access_Token", accessToken)
                    .body(errorLogPayload)
                .when()
                    .post("/API/Common/GetErrorLogList")
                .then()
                    .extract()
                    .response();

        System.out.println("Error Log HTTP Status Code: " + errorLogResponse.getStatusCode());
        System.out.println("Error Log Response: " + errorLogResponse.asPrettyString());

        Assert.assertEquals(errorLogResponse.getStatusCode(), 200, "Error Log HTTP status failed");
        Assert.assertEquals(errorLogResponse.jsonPath().getInt("statusCode"), 200, "Error Log API failed");

        List<Map<String, Object>> lstLogs =
                errorLogResponse.jsonPath().getList("data.lstLogs");

        int lstLogsCount = lstLogs == null ? 0 : lstLogs.size();

        System.out.println("Payload Date: " + payloadDate);
        System.out.println("lstLogs Count: " + lstLogsCount);

        Assert.assertTrue(lstLogsCount >= 0, "lstLogs count should not be negative");
    }
}