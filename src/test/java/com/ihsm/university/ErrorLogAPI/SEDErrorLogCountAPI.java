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

public class SEDErrorLogCountAPI {

    @Test
    public void captureCurrentDateErrorLogCount() {

        RestAssured.baseURI = "https://wapi.thesed.in";

        String payloadDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String responseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        String loginPayload = "{\n" +
                "  \"strEmail\": \"admin@gmail.com\",\n" +
                "  \"strPassword\": \"123456\",\n" +
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

        String accessToken = loginResponse.jsonPath().getString("access_token");

        Assert.assertNotNull(accessToken, "Access token is null");
        Assert.assertFalse(accessToken.trim().isEmpty(), "Access token is empty");

        System.out.println("Captured Token: " + accessToken);

        String errorLogPayload = "{\n" +
                "  \"intType\": 0,\n" +
                "  \"dttDate\": \"" + payloadDate + "\"\n" +
                "}";

        Response errorLogResponse =
                given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", accessToken)
                    .header("access_token", accessToken)
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

        int currentDateErrorCount = getCurrentDateErrorCount(errorLogResponse, responseDate);

        System.out.println("Payload Date: " + payloadDate);
        System.out.println("Response Match Date: " + responseDate);
        System.out.println("Current Date Error Count: " + currentDateErrorCount);

        Assert.assertTrue(currentDateErrorCount >= 0, "Error count should not be negative");
    }

    private int getCurrentDateErrorCount(Response errorLogResponse, String responseDate) {

        List<Map<String, Object>> lstLogCount =
                errorLogResponse.jsonPath().getList("data.lstLogCount");

        if (lstLogCount != null) {
            for (Map<String, Object> row : lstLogCount) {

                String strDate = String.valueOf(row.get("strDate"));

                if (responseDate.equals(strDate)) {
                    Object countValue = row.get("intErrorCount");
                    return Integer.parseInt(String.valueOf(countValue));
                }
            }

            return 0;
        }

        List<Map<String, Object>> lstLogs =
                errorLogResponse.jsonPath().getList("data.lstLogs");

        if (lstLogs != null) {
            return lstLogs.size();
        }

        return 0;
    }
}