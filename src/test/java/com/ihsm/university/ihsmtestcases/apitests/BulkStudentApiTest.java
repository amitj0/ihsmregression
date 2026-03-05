package com.ihsm.university.ihsmtestcases.apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ihsm.university.utilities.TokenManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BulkStudentApiTest {

    private static final String BASE_URL  = "https://sedapi.softsolanalytics.com";
    private static final String ENDPOINT  = "/API/Student/EnrollmentStudent";
    private static final int TOTAL_REQUESTS = 1000;
    private static final int DELAY_MS       = 500;

    private String accessToken;
    private final Random random      = new Random();
    private final List<String> successList = new ArrayList<>();
    private final List<String> failureList = new ArrayList<>();

    @BeforeClass
    public void setup() {
        // Fetch token FIRST, then set baseURI so TokenManager doesn't overwrite it
        accessToken = TokenManager.getToken();
        RestAssured.baseURI = BASE_URL;

        System.out.println("========================================");
        System.out.println("  Token fetched: " + accessToken);
        System.out.println("========================================\n");
    }

    @Test
    public void bulkStudentEnrollment() throws InterruptedException {

        System.out.println("========================================");
        System.out.println("  Starting Bulk Student API Test");
        System.out.println("  Total Requests : " + TOTAL_REQUESTS);
        System.out.println("  Endpoint       : " + BASE_URL + ENDPOINT);
        System.out.println("========================================\n");

        for (int i = 1; i <= TOTAL_REQUESTS; i++) {

            String enrollmentNo = generateEnrollmentNo(i);
            String aadhaarNo    = generateAadhaarNo();
            String email        = generateEmail(i);
            String phone        = generatePhone();
            String payload      = buildPayload(enrollmentNo, aadhaarNo, email, phone, i);

            System.out.println("Request #" + i + " | Enrollment: " + enrollmentNo);

            try {
                Response response = RestAssured.given()
                        .baseUri(BASE_URL)              //  Always explicit, never relies on global
                        .contentType(ContentType.JSON)
                        .header("access_token", accessToken)
                        .body(payload)
                        .when()
                        .post(ENDPOINT)
                        .then().extract().response();

                int statusCode = response.getStatusCode();
                String body    = response.getBody().asString();

                System.out.println("  HTTP Status  : " + statusCode);
                System.out.println("  Response     : " + body);
                System.out.println();

                if (statusCode == 200 || statusCode == 201) {
                    successList.add("Request #" + i + " | Enrollment: " + enrollmentNo + " | Status: " + statusCode);
                } else {
                    failureList.add("Request #" + i + " | Enrollment: " + enrollmentNo + " | Status: " + statusCode + " | Response: " + body);
                }

            } catch (Exception e) {
                System.err.println("  ERROR on request #" + i + ": " + e.getMessage());
                failureList.add("Request #" + i + " | Enrollment: " + enrollmentNo + " | ERROR: " + e.getMessage());
            }

            if (i < TOTAL_REQUESTS) {
                Thread.sleep(DELAY_MS);
            }
        }

        printSummary();
    }

    private String buildPayload(String enrollmentNo, String aadhaarNo, String email, String phone, int index) {
        return "{\n" + "  \"intStudentInformationId\": 0,\n" + "  \"intAcademicPlanId\": 2,\n"
                + "  \"intProgramId\": 0,\n" + "  \"intCurrentSessionId\": 1,\n" + "  \"intAdmissionType\": 1,\n"
                + "  \"intState\": 14,\n" + "  \"intBatchId\": 1,\n" + "  \"intCampusId\": 1,\n"
                + "  \"intDegreeId\": 0,\n" + "  \"intLanguageId\": 0,\n" + "  \"intCourseId\": 3,\n"
                + "  \"intSemesterId\": 5,\n" + "  \"intNewGroupId\": 0,\n" + "  \"strEnrollmentNo\": \"" + enrollmentNo
                + "\",\n" + "  \"strApplicationNo\": \"" + enrollmentNo + "\",\n" + "  \"strFirstName\": \"Student"
                + index + "\",\n" + "  \"strMiddleName\": \"Singh\",\n" + "  \"strLastName\": \"Sharma\",\n"
                + "  \"strFullnameOther\": \"\",\n" + "  \"strFatherName\": \"\",\n" + "  \"strMotherName\": \"\",\n"
                + "  \"strDOB\": \"2023-03-04\",\n" + "  \"strAddress\": \"India\",\n" + "  \"strGender\": \"1\",\n"
                + "  \"strCity\": \"166\",\n" + "  \"strParentPhone\": \"" + phone + "\",\n"
                + "  \"strParentEmail\": \"" + email + "\",\n" + "  \"strNationality\": \"India\",\n"
                + "  \"intCategoryId\": 0,\n" + "  \"dttCreationDate\": \"2023-03-04\",\n" + "  \"strAadhaarNo\": \""
                + aadhaarNo + "\",\n" + "  \"strCityName\": \"Gurugram\",\n" + "  \"intStatusId\": 3,\n"
                + "  \"strBloodGroup\": \"\",\n" + "  \"intChildDiseaseCategoryId\": 0,\n"
                + "  \"strParentsWorkingBRGS\": \"\",\n" + "  \"strDistanceFromSchool\": \"\",\n"
                + "  \"strHealthCard\": \"\"\n" + "}";
    }

    private String generateEnrollmentNo(int index) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
        return timestamp + String.format("%03d", index);
    }

    private String generateAadhaarNo() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }

    private String generateEmail(int index) {
        return "student" + index + "_" + System.currentTimeMillis() + "@gmail.com";
    }

    private String generatePhone() {
        StringBuilder sb = new StringBuilder("9");
        for (int i = 0; i < 9; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }

    private void printSummary() {
        System.out.println("\n========================================");
        System.out.println("           BULK TEST SUMMARY");
        System.out.println("========================================");
        System.out.println("Total Sent   : " + TOTAL_REQUESTS);
        System.out.println("Success      : " + successList.size());
        System.out.println("Failed       : " + failureList.size());
        if (!successList.isEmpty()) { System.out.println("\n--- SUCCESS ---"); successList.forEach(System.out::println); }
        if (!failureList.isEmpty()) { System.out.println("\n--- FAILURES ---"); failureList.forEach(System.out::println); }
        System.out.println("========================================\n");
    }
}