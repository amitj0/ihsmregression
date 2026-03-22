package com.ihsm.university.ihsmtestcases.apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Learnity_StudentRegistration {

    // ─── CONFIG ──────────────────────────────────────────────────────────────
    // FIXED: Correct BASE_URL
    private static final String BASE_URL        = " https://learnitytestapi.softsolanalytics.com";
   
    private static final String ENROLL_ENDPOINT = "/API/Student/EnrollmentStudent";
    private static final String LOGIN_ENDPOINT  = "/API/User/UserLogin";
    private static final String ADMIN_EMAIL     = "admin@gmail.com";
    private static final String ADMIN_PASSWORD  = "123456";
    private static final String LOGIN_PASSWORD  = "123456";
    private static final int    TOTAL_REQUESTS  = 10;   
    private static final int    THREAD_POOL     = 10;   

    private String accessToken;

    // ─── Thread-safe result lists ─────────────────────────────────────────────
    private final List<String>  enrollSuccessList = Collections.synchronizedList(new ArrayList<>());
    private final List<String>  enrollFailureList = Collections.synchronizedList(new ArrayList<>());
    private final List<String>  loginSuccessList  = Collections.synchronizedList(new ArrayList<>());
    private final List<String>  loginFailureList  = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger counter           = new AtomicInteger(0);
    private final Random        random            = new Random();

    // ─────────────────────────────────────────────────────────────────────────
    // BEFORE CLASS: Fetch Admin Token
    // ─────────────────────────────────────────────────────────────────────────
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL.trim();
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("========================================");
        System.out.println("  Fetching admin token...");

        String loginBody = "{\n"
                + "  \"strEmail\": \"" + ADMIN_EMAIL + "\",\n"
                + "  \"strPassword\": \"" + ADMIN_PASSWORD + "\",\n"
                + "  \"intSource\": 1\n"
                + "}";

        Response loginResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post(LOGIN_ENDPOINT)
                .then().extract().response();

        int    loginStatus  = loginResponse.getStatusCode();
        String loginRawBody = loginResponse.getBody().asString();

        System.out.println("  Admin Login Status : " + loginStatus);
        System.out.println("  Admin Login Body   : " + loginRawBody);

        // Guard against empty or non-JSON response before calling jsonPath()
        if (loginRawBody != null && !loginRawBody.trim().isEmpty() && loginRawBody.trim().startsWith("{")) {
            accessToken = loginResponse.jsonPath().getString("access_Token");
            if (accessToken == null) accessToken = loginResponse.jsonPath().getString("access_token");
            if (accessToken == null) accessToken = loginResponse.jsonPath().getString("token");
            if (accessToken == null) accessToken = loginResponse.jsonPath().getString("data.token");
            if (accessToken == null) accessToken = loginResponse.jsonPath().getString("data.access_Token");
        } else {
            System.err.println("  ⚠️  Response is empty or not JSON! HTTP Status: " + loginStatus);
            System.err.println("  ⚠️  Raw Body: " + loginRawBody);
        }

        if (accessToken == null) {
            System.err.println("  ⚠️  Token not found! Check admin login response above.");
        } else {
            System.out.println("  Token fetched      : " + accessToken);
        }

        System.out.println("  Enroll URL         : " + BASE_URL + ENROLL_ENDPOINT);
        System.out.println("  Login  URL         : " + BASE_URL + LOGIN_ENDPOINT);
        System.out.println("========================================\n");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST: Bulk Enroll + Login
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void bulkStudentEnrollmentAndLogin() throws InterruptedException {

        System.out.println("========================================");
        System.out.println("  PARALLEL Bulk Enroll + Login Test");
        System.out.println("  Total Users  : " + TOTAL_REQUESTS);
        System.out.println("  Thread Pool  : " + THREAD_POOL);
        System.out.println("========================================\n");

        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
        CountDownLatch  latch    = new CountDownLatch(TOTAL_REQUESTS);

        for (int i = 1; i <= TOTAL_REQUESTS; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    enrollAndLogin(idx);
                } finally {
                    latch.countDown();
                    int done = counter.incrementAndGet();
                    if (done % 100 == 0) {
                        System.out.println("  >> Progress: " + done + "/" + TOTAL_REQUESTS + " completed");
                    }
                }
            });
        }

        latch.await();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        long elapsed = System.currentTimeMillis() - startTime;
        printSummary(elapsed);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // STEP 1 → Enroll  |  STEP 2 → Login with enrollmentNo + password
    // ─────────────────────────────────────────────────────────────────────────
    private void enrollAndLogin(int index) {

        String enrollmentNo = generateEnrollmentNo(index);
        String aadhaarNo    = generateAadhaarNo();
        String email        = generateEmail(index);
        String phone        = generatePhone();

        // ── STEP 1: Enroll ───────────────────────────────────────────────────
        System.out.println("[ENROLL] #" + index + " → enrollmentNo: " + enrollmentNo);

        try {
            Response enrollResponse = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("access_token", accessToken)
                    .body(buildEnrollPayload(enrollmentNo, aadhaarNo, email, phone, index))
                    .when()
                    .post(ENROLL_ENDPOINT)
                    .then().extract().response();

            int    enrollStatus = enrollResponse.getStatusCode();
            String enrollBody   = enrollResponse.getBody().asString();

            System.out.println("[ENROLL] #" + index + " | HTTP: " + enrollStatus + " | Body: " + enrollBody);

            if (enrollStatus == 200 || enrollStatus == 201) {
                enrollSuccessList.add("User #" + index + " | EnrollmentNo: " + enrollmentNo + " | Status: " + enrollStatus);
            } else {
                enrollFailureList.add("User #" + index + " | EnrollmentNo: " + enrollmentNo
                        + " | Status: " + enrollStatus + " | Response: " + enrollBody);
            }

        } catch (Exception e) {
            System.err.println("[ENROLL ERROR] #" + index + ": " + e.getMessage());
            enrollFailureList.add("User #" + index + " | EnrollmentNo: " + enrollmentNo + " | ERROR: " + e.getMessage());
        }

        // ── STEP 2: Login using enrollmentNo as strEmail ──────────────────────
        System.out.println("[LOGIN]  #" + index + " → strEmail: " + enrollmentNo + " | strPassword: " + LOGIN_PASSWORD);

        try {
            Response loginResponse = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(buildLoginPayload(enrollmentNo))
                    .when()
                    .post(LOGIN_ENDPOINT)
                    .then().extract().response();

            int    loginStatus = loginResponse.getStatusCode();
            String loginBody   = loginResponse.getBody().asString();

            System.out.println("[LOGIN]  #" + index + " | HTTP: " + loginStatus + " | Body: " + loginBody);

            if (loginStatus == 200 || loginStatus == 201) {
                loginSuccessList.add("User #" + index + " | EnrollmentNo: " + enrollmentNo + " | Status: " + loginStatus);
            } else {
                loginFailureList.add("User #" + index + " | EnrollmentNo: " + enrollmentNo
                        + " | Status: " + loginStatus + " | Response: " + loginBody);
            }

        } catch (Exception e) {
            System.err.println("[LOGIN ERROR] #" + index + ": " + e.getMessage());
            loginFailureList.add("User #" + index + " | EnrollmentNo: " + enrollmentNo + " | ERROR: " + e.getMessage());
        }
    }

    // ─── Payload Builders ────────────────────────────────────────────────────

    private String buildEnrollPayload(String enrollmentNo, String aadhaarNo,
                                      String email, String phone, int index) {
        return "{\n"
                + "  \"intStudentInformationId\": 0,\n"
                + "  \"intAcademicPlanId\": 1,\n"
                + "  \"intProgramId\": 0,\n"
                + "  \"intCurrentSessionId\": 1,\n"
                + "  \"intAdmissionType\": 1,\n"
                + "  \"intState\": 14,\n"
                + "  \"intBatchId\": 1,\n"
                + "  \"intCampusId\": 1,\n"
                + "  \"intDegreeId\": 0,\n"
                + "  \"intLanguageId\": 0,\n"
                + "  \"intCourseId\": 1,\n"
                + "  \"intSemesterId\": 21,\n" // changed(1,3,5,7,11.....etc)
                + "  \"intNewGroupId\": 0,\n"
                + "  \"strEnrollmentNo\": \"" + enrollmentNo + "\",\n"
                + "  \"strApplicationNo\": \"" + enrollmentNo + "\",\n"
                + "  \"strFirstName\": \"Student" + index + "\",\n"
                + "  \"strMiddleName\": \"Singh\",\n"
                + "  \"strLastName\": \"Sharma\",\n"
                + "  \"strFullnameOther\": \"\",\n"
                + "  \"strFatherName\": \"\",\n"
                + "  \"strMotherName\": \"\",\n"
                + "  \"strDOB\": \"2000-01-01\",\n"
                + "  \"strAddress\": \"India\",\n"
                + "  \"strGender\": \"1\",\n"
                + "  \"strCity\": \"177\",\n"
                + "  \"strParentPhone\": \"" + phone + "\",\n"
                + "  \"strParentEmail\": \"" + email + "\",\n"
                + "  \"strNationality\": \"98\",\n"
                + "  \"intCategoryId\": 0,\n"
                + "  \"dttCreationDate\": \"2023-03-07\",\n"
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

    private String buildLoginPayload(String enrollmentNo) {
        return "{\n"
                + "  \"strEmail\": \"" + enrollmentNo + "\",\n"
                + "  \"strPassword\": \"" + LOGIN_PASSWORD + "\",\n"
                + "  \"strFirebaseDeviceId\": \"firebase_token_" + enrollmentNo + "\",\n"
                + "  \"intSource\": 2\n"   // 2 = Android | 3 = iOS
                + "}";
    }

    // ─── Data Generators ─────────────────────────────────────────────────────

    private String generateEnrollmentNo(int index) {
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
        System.out.println("\n==========================================");
        System.out.println("       BULK ENROLL + LOGIN SUMMARY");
        System.out.println("==========================================");
        System.out.println("Total Users        : " + TOTAL_REQUESTS);
        System.out.println("Enroll  Success    : " + enrollSuccessList.size());
        System.out.println("Enroll  Failed     : " + enrollFailureList.size());
        System.out.println("Login   Success    : " + loginSuccessList.size());
        System.out.println("Login   Failed     : " + loginFailureList.size());
        System.out.printf( "Time Elapsed       : %.2f seconds%n", elapsedMs / 1000.0);
        System.out.printf( "Throughput         : %.1f users/sec%n", TOTAL_REQUESTS / (elapsedMs / 1000.0));

        if (!enrollFailureList.isEmpty()) {
            System.out.println("\n--- ENROLL FAILURES ---");
            enrollFailureList.forEach(System.out::println);
        }
        if (!loginFailureList.isEmpty()) {
            System.out.println("\n--- LOGIN FAILURES ---");
            loginFailureList.forEach(System.out::println);
        }
        System.out.println("==========================================\n");
    }
}