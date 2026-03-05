package com.ihsm.university.ihsmtestcases.flows.attendance;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.attandence.IHSM_ClassAttendance;
import com.ihsm.university.ihsmpageobjects.classchedule.*;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_ClassAttendanceTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();

	@Test(priority = 0, description = "Verify Mark Attendance Of Lecture Class Test")
	public void verifyClassAttendace() throws IOException, InterruptedException {

		/*
		 * Open a new browser instance and login as employee for this flow
		 */
		WebDriver empDriver = getDriver();
		loginAsEmployee(empDriver);

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentTest node = ExtentListener.createNode("Class Attendance Of Lecture Information");
		boolean isSuccess = false;

		try {
			node.info("Entering Class Attendance Details");
			node.info("Academic Plan: " + "CENTRAL / Bachelor / MBBS");
			node.info("Semester: " + "1");
			node.info("Subject: " + "Russian Language (Main)");
			
			IHSM_ClassAttendance classAttendance = new IHSM_ClassAttendance(getDriver());
			classAttendance.fillClassAttendanceForLec(1, 1, 1);
			isSuccess = true;

			node.pass("Class Attendance Of Lec Successfully");
		} catch (Exception e) {
			node.fail("Class Attendance failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Class Attendance Of Lec", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(priority = 1, description = "Verify Class Marks Of Practical Test")
	public void verifyClassAttendace2() throws IOException {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentTest node = ExtentListener.createNode("Class Marks Of Practical Information");
		boolean isSuccess = false;

		try {
			node.info("Entering Class Marks Of Practical Details");

			List<Integer> attendanceValues = Arrays.asList(40, 40, 40, 40, 40, 40, 40, 40);
			IHSM_ClassAttendance classAttendance = new IHSM_ClassAttendance(getDriver());
			classAttendance.fillClassAttendanceForPrac(1, 1, 1, "p", attendanceValues);
			node.pass("Class Marks Of Practical added successfully");
			isSuccess = true;
		} catch (Exception e) {
			node.fail("Class Marks of Practical failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Class Marks of Practical", isSuccess ? "PASS" : "FAIL");
		}
	}

	@AfterClass(alwaysRun = true)
	public void summarizeClassSchedulingFlow() {
		System.out.println("\n==== Class Attendance & Marks Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("====================================\n");
	}
}
