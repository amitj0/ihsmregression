package com.ihsm.university.ihsmtestcases.apitests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ihsm.university.utilities.TokenManager;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LoginLoadTest {

    private static AtomicInteger totalRequests = new AtomicInteger(0);
    private static AtomicInteger successRequests = new AtomicInteger(0);
    private static AtomicInteger failedRequests = new AtomicInteger(0);

    private static AtomicLong totalResponseTime = new AtomicLong(0);
    private static AtomicLong maxResponseTime = new AtomicLong(0);

    private static long testStartTime = System.currentTimeMillis();

    @Test(invocationCount = 50000, threadPoolSize = 50)
    public void loginTest() {

        long startTime = System.currentTimeMillis();
        totalRequests.incrementAndGet();

        try {

            String token = TokenManager.getToken();

            long responseTime = System.currentTimeMillis() - startTime;

            totalResponseTime.addAndGet(responseTime);

            // Update max response time safely
            maxResponseTime.updateAndGet(prev ->
                    Math.max(prev, responseTime));

            if (token != null && !token.isEmpty()) {

                successRequests.incrementAndGet();

            } else {

                failedRequests.incrementAndGet();

                System.out.println("FAILED: Token is null or empty");
            }

        } catch (Exception e) {

            failedRequests.incrementAndGet();

            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @AfterClass
    public void printReport() {

        long testEndTime = System.currentTimeMillis();

        long duration = (testEndTime - testStartTime) / 1000;

        int total = totalRequests.get();
        int success = successRequests.get();
        int failed = failedRequests.get();

        long avgResponseTime = total > 0
                ? totalResponseTime.get() / total
                : 0;

        System.out.println("\n======================================");
        System.out.println(" LOAD TEST REPORT ");
        System.out.println("======================================");

        System.out.println("Total Requests: " + total);
        System.out.println("Successful Requests: " + success);
        System.out.println("Failed Requests: " + failed);

        System.out.println("Success Rate: " +
                ((success * 100) / total) + "%");

        System.out.println("Average Response Time: " +
                avgResponseTime + " ms");

        System.out.println("Max Response Time: " +
                maxResponseTime.get() + " ms");

        System.out.println("Test Duration: " +
                duration + " seconds");

        System.out.println("Requests Per Second: " +
                (total / (duration == 0 ? 1 : duration)));

        System.out.println("======================================");

        // Optional assertion
        Assert.assertTrue(failed < (total * 0.05),
                "Failure rate is more than 5%");
    }
}