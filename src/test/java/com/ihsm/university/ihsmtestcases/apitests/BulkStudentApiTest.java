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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BulkStudentApiTest {

    private static final String BASE_URL       = "https://sedapi.softsolanalytics.com";
    private static final String ENDPOINT       = "/API/Student/EnrollmentStudent";
    private static final int    TOTAL_REQUESTS = 100;
    private static final int    THREAD_POOL    = 50;   // concurrent threads — tune as needed

    private String accessToken;

    // Thread-safe collections
    private final List<String>   successList = Collections.synchronizedList(new ArrayList<>());
    private final List<String>   failureList = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger  counter     = new AtomicInteger(0);

    @BeforeClass
    public void setup() {
        accessToken = TokenManager.getToken();
        RestAssured.baseURI = BASE_URL;

        System.out.println("========================================");
        System.out.println("  Token fetched: " + accessToken);
        System.out.println("========================================\n");
    }

    @Test
    public void bulkStudentEnrollment() throws InterruptedException {

        System.out.println("========================================");
        System.out.println("  Starting PARALLEL Bulk Student API Test");
        System.out.println("  Total Requests : " + TOTAL_REQUESTS);
        System.out.println("  Thread Pool    : " + THREAD_POOL);
        System.out.println("  Endpoint       : " + BASE_URL + ENDPOINT);
        System.out.println("========================================\n");

        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
        CountDownLatch latch = new CountDownLatch(TOTAL_REQUESTS);

        for (int i = 1; i <= TOTAL_REQUESTS; i++) {
            final int requestIndex = i;

            executor.submit(() -> {
                try {
                    sendRequest(requestIndex);
                } finally {
                    latch.countDown();
                    int done = counter.incrementAndGet();
                    if (done % 100 == 0) {
                        System.out.println("  >> Progress: " + done + "/" + TOTAL_REQUESTS + " completed");
                    }
                }
            });
        }

        // Wait for ALL requests to finish
        latch.await();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        long elapsed = System.currentTimeMillis() - startTime;
        printSummary(elapsed);
    }

    private void sendRequest(int index) {
        String enrollmentNo = generateEnrollmentNo(index);
        String aadhaarNo    = generateAadhaarNo();
        String email        = generateEmail(index);
        String phone        = generatePhone();
        String payload      = buildPayload(enrollmentNo, aadhaarNo, email, phone, index);

        try {
            Response response = RestAssured.given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .header("access_token", accessToken)
                    .body(payload)
                    .when()
                    .post(ENDPOINT)
                    .then().extract().response();

            int    statusCode = response.getStatusCode();
            String body       = response.getBody().asString();

            System.out.println("Request #" + index + " | Enrollment: " + enrollmentNo
                    + " | HTTP: " + statusCode + " | Body: " + body);

            if (statusCode == 200 || statusCode == 201) {
                successList.add("Request #" + index + " | Enrollment: " + enrollmentNo + " | Status: " + statusCode);
            } else {
                failureList.add("Request #" + index + " | Enrollment: " + enrollmentNo
                        + " | Status: " + statusCode + " | Response: " + body);
            }

        } catch (Exception e) {
            System.err.println("  ERROR on request #" + index + ": " + e.getMessage());
            failureList.add("Request #" + index + " | Enrollment: " + enrollmentNo + " | ERROR: " + e.getMessage());
        }
    }

    // ─── Payload & Data Generators ───────────────────────────────────────────

    private String buildPayload(String enrollmentNo, String aadhaarNo, String email, String phone, int index) {
        return "{\n"
                + "  \"intStudentInformationId\": 0,\n"
                + "  \"intAcademicPlanId\": 2,\n"
                + "  \"intProgramId\": 0,\n"
                + "  \"intCurrentSessionId\": 1,\n"
                + "  \"intAdmissionType\": 1,\n"
                + "  \"intState\": 14,\n"
                + "  \"intBatchId\": 1,\n"
                + "  \"intCampusId\": 1,\n"
                + "  \"intDegreeId\": 0,\n"
                + "  \"intLanguageId\": 0,\n"
                + "  \"intCourseId\": 3,\n"
                + "  \"intSemesterId\": 5,\n"
                + "  \"intNewGroupId\": 0,\n"
                + "  \"strEnrollmentNo\": \"" + enrollmentNo + "\",\n"
                + "  \"strApplicationNo\": \"" + enrollmentNo + "\",\n"
                + "  \"strFirstName\": \"Student" + index + "\",\n"
                + "  \"strMiddleName\": \"Singh\",\n"
                + "  \"strLastName\": \"Sharma\",\n"
                + "  \"strFullnameOther\": \"\",\n"
                + "  \"strFatherName\": \"\",\n"
                + "  \"strMotherName\": \"\",\n"
                + "  \"strDOB\": \"2023-03-04\",\n"
                + "  \"strAddress\": \"India\",\n"
                + "  \"strGender\": \"1\",\n"
                + "  \"strCity\": \"166\",\n"
                + "  \"strParentPhone\": \"" + phone + "\",\n"
                + "  \"strParentEmail\": \"" + email + "\",\n"
                + "  \"strNationality\": \"India\",\n"
                + "  \"intCategoryId\": 0,\n"
                + "  \"dttCreationDate\": \"2023-03-04\",\n"
                + "  \"strAadhaarNo\": \"" + aadhaarNo + "\",\n"
                + "  \"strCityName\": \"Gurugram\",\n"
                + "  \"intStatusId\": 3,\n"
                + "  \"strBloodGroup\": \"\",\n"
                + "  \"intChildDiseaseCategoryId\": 0,\n"
                + "  \"strParentsWorkingBRGS\": \"\",\n"
                + "  \"strDistanceFromSchool\": \"\",\n"
                + "  \"strHealthCard\": \"\"\n"
                + "}";
    }

    private final Random random = new Random();

    private String generateEnrollmentNo(int index) {
        // Include thread-id to avoid collisions across concurrent threads
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
        return timestamp + String.format("%04d", index);
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

    // ─── Summary ─────────────────────────────────────────────────────────────

    private void printSummary(long elapsedMs) {
        System.out.println("\n========================================");
        System.out.println("           BULK TEST SUMMARY");
        System.out.println("========================================");
        System.out.println("Total Sent   : " + TOTAL_REQUESTS);
        System.out.println("Success      : " + successList.size());
        System.out.println("Failed       : " + failureList.size());
        System.out.printf("Time Elapsed : %.2f seconds%n", elapsedMs / 1000.0);
        System.out.printf("Throughput   : %.1f req/sec%n", TOTAL_REQUESTS / (elapsedMs / 1000.0));

        if (!successList.isEmpty()) {
            System.out.println("\n--- SUCCESS ---");
            successList.forEach(System.out::println);
        }
        if (!failureList.isEmpty()) {
            System.out.println("\n--- FAILURES ---");
            failureList.forEach(System.out::println);
        }
        System.out.println("========================================\n");
    }
}