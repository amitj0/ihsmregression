package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.testng.annotations.Test;

import com.ihsm.university.base.StudentRegistrationAPI;

public class BulkStudentTest {

	@Test
	public void bulkStudentRegistration() {

		int totalStudents = 500;
		int threadCount = 10; // CONTROL PARALLEL REQUESTS

		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failCount = new AtomicInteger();

		ForkJoinPool customThreadPool = new ForkJoinPool(threadCount);

		customThreadPool.submit(() ->

		IntStream.rangeClosed(1, totalStudents).parallel().forEach(i -> {

			String payload = StudentDataGenerator.generateStudentPayload();

			int statusCode = StudentRegistrationAPI.registerStudent(payload);

			if (statusCode == 201 || statusCode == 200) {
				successCount.incrementAndGet();
				System.out.println("Student " + i + " Registered ✅");
			} else {
				failCount.incrementAndGet();
				System.out.println("Student " + i + " Failed ❌ Status: " + statusCode);
			}

		})

		).join();

		System.out.println("================================");
		System.out.println("Total Students : " + totalStudents);
		System.out.println("Success Count  : " + successCount.get());
		System.out.println("Fail Count     : " + failCount.get());
	}

}
