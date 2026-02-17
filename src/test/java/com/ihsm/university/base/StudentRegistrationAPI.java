package com.ihsm.university.base;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class StudentRegistrationAPI {

	static {
		baseURI = "https://free.mockerapi.com";
	}

	public static int registerStudent(String payload) {
		int retries = 3; // Number of retry attempts
		int statusCode = 0;

		for (int attempt = 1; attempt <= retries; attempt++) {
			try {
				Response response = given().contentType("application/json").body(payload).when().post("/post");

				statusCode = response.getStatusCode();

				// If not rate limited, return immediately
				if (statusCode != 429) {
					return statusCode;
				}

				// If 429, wait before retrying
				System.out.println("Rate limited (429). Retry attempt " + attempt + "...");
				Thread.sleep(1000); // Wait 1 second before retry
			} catch (Exception e) {
				System.out.println("Exception on attempt " + attempt + ": " + e.getMessage());
			}
		}

		return statusCode; // Return last status code if still failing
	}
}
