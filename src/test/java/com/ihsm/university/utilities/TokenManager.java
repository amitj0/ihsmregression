package com.ihsm.university.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import config.ConfigReader;

public class TokenManager {

    private static String token;
    private static long tokenExpiry = 0;

    public static String getToken() {
        if (token == null || System.currentTimeMillis() > tokenExpiry) {
            token = generateToken();
            tokenExpiry = System.currentTimeMillis() + (7 * 60 * 60 * 1000);
        }
        return token;
    }

    private static String generateToken() {

        String baseUrl = ConfigReader.get("base.url");

        String body = "{\n" +
                "\"strEmail\":\"" + ConfigReader.get("email") + "\",\n" +
                "\"strPassword\":\"" + ConfigReader.get("password") + "\",\n" +
                "\"strFirebaseDeviceId\":\"" + ConfigReader.get("firebase") + "\",\n" +
                "\"intSource\":" + ConfigReader.get("source") + "\n" +
                "}";

        
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/API/User/UserLogin");  //  Fixed endpoint

        System.out.println("Base URI: " + baseUrl);
        System.out.println("Status Code: " + response.statusCode());
        response.prettyPrint();

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Login failed. Status: "
                            + response.statusCode()
                            + "\nResponse: "
                            + response.asString());
        }

        String token = response.jsonPath().getString("access_Token");

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token not found in response");
        }

        System.out.println("Generated Token: " + token);
        return token;
    }
}