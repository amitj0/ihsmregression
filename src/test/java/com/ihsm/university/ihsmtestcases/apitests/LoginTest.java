package com.ihsm.university.ihsmtestcases.apitests;

import java.util.concurrent.atomic.AtomicInteger;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ihsm.university.utilities.TokenManager;

public class LoginTest {

    AtomicInteger failures = new AtomicInteger(0);
    AtomicInteger success = new AtomicInteger(0);

    long startTime;

    @BeforeClass
    public void start() {

        startTime = System.currentTimeMillis();

        System.out.println("Load Test Started");

    }

    @Test(invocationCount = 5000, threadPoolSize = 50)
    public void loginTest() {

        try {

            String token = TokenManager.getToken();

            if(token == null) {

                failures.incrementAndGet();

            } else {

                success.incrementAndGet();

            }

        }
        catch(Exception e) {

            failures.incrementAndGet();

        }

    }

    @AfterClass
    public void report() {
        System.out.println("Success: " + success.get());
        System.out.println("Failures: " + failures.get());
        Assert.assertEquals(failures.get(), 0, "Some login requests failed!");
    }

    }
