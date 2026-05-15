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

public class BIUErrorLogCountAPI {

    @Test
    public void captureCurrentDateErrorLogCount() {

        RestAssured.baseURI = "https://biuapi.softsolanalytics.com";

        String loginPayload = "{\n" +
                "  \"strEmail\": \"admin@biu.com\",\n" +
                "  \"strPassword\": \"biu123\",\n" +
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

        System.out.println("Login Status Code: " + loginResponse.getStatusCode());
        System.out.println("Login Response: " + loginResponse.asPrettyString());

        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login HTTP status failed");
        Assert.assertEquals(loginResponse.jsonPath().getInt("statusCode"), 200, "Login API failed");

        String accessToken = loginResponse.jsonPath().getString("access_Token");

        Assert.assertNotNull(accessToken, "Access token is null");
        Assert.assertFalse(accessToken.trim().isEmpty(), "Access token is empty");

        System.out.println("Captured Token: " + accessToken);

        String errorLogPayload = "{\n" +
                "  \"intType\": 0\n" +
                "}";

        Response errorLogResponse =
                given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", accessToken)
                    .header("access_Token", accessToken)
                    .body(errorLogPayload)
                .when()
                    .post("/API/ErrorLog/GetErrorLogList")
                .then()
                    .extract()
                    .response();

        System.out.println("Error Log HTTP Status Code: " + errorLogResponse.getStatusCode());
        System.out.println("Error Log Response: " + errorLogResponse.asPrettyString());

        Assert.assertEquals(errorLogResponse.getStatusCode(), 200, "Error Log HTTP status failed");

        int errorLogApiStatusCode = errorLogResponse.jsonPath().getInt("statusCode");
        Assert.assertEquals(errorLogApiStatusCode, 200, "Error Log API failed / unauthorized");

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Map<String, Object>> lstLogCount =
                errorLogResponse.jsonPath().getList("data.lstLogCount");

        int currentDateErrorCount = 0;

        if (lstLogCount != null) {
            for (Map<String, Object> row : lstLogCount) {
                String strDate = String.valueOf(row.get("strDate"));

                if (currentDate.equals(strDate)) {
                    currentDateErrorCount = Integer.parseInt(String.valueOf(row.get("intErrorCount")));
                    break;
                }
            }
        }

        System.out.println("Current Date: " + currentDate);
        System.out.println("Current Date Error Count: " + currentDateErrorCount);

        Assert.assertTrue(currentDateErrorCount >= 0, "Error count should not be negative");
    }
}