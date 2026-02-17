package com.ihsm.university.ihsmtestcases.flows.attendance;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.attandence.IHSM_ClassAttendance;
import com.ihsm.university.ihsmpageobjects.classchedule.*;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_ClassAttendanceTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();

	@Test(priority = 0, testName = "Verify Class Attendance")
	public void verifyClassAttendace() {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentListener.createNode("Class Attendance Information");
		int failCount = 0;

		try {
			IHSM_ClassAttendance classAttendance = new IHSM_ClassAttendance(getDriver());
			classAttendance.fillClassAttendance(1, 1, 3);
			ExtentListener.getNode().pass("Class Attendance added successfully");
			stepStatus.put("Class Attendance", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Class Attendance failed: " + e.getMessage());
			stepStatus.put("Class Attendance", "FAIL");
			soft.fail("Class Attendance failed: " + e.getMessage());
			failCount++;
		}

		if (failCount == 0) {
			ExtentListener.getNode().pass("All Class Attendance sections executed successfully.");
		} else {
			ExtentListener.getNode().fail("Total Failed Sections in Class Attendance Flow: " + failCount);
		}

		soft.assertAll();
	}

	@Test(priority = 1, testName = "Verify Class Attendance 2", dependsOnMethods = "verifyClassAttendace")
	public void verifyClassAttendace2() {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentListener.createNode("Class Attendance 2 Information");
		int failCount = 0;

		try {
			List<Integer> attendanceValues = Arrays.asList(40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40,
					40, 40,40,40,40,40);
			IHSM_ClassAttendance classAttendance = new IHSM_ClassAttendance(getDriver());
			classAttendance.fillClassAttendance2(1, 1, 3, "p", attendanceValues);
			ExtentListener.getNode().pass("Class Attendance 2 added successfully");
			stepStatus.put("Class Attendance", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Class Attendance 2 failed: " + e.getMessage());
			stepStatus.put("Class Attendance 2", "FAIL");
			soft.fail("Class Attendance 2 failed: " + e.getMessage());
			failCount++;
		}

		if (failCount == 0) {
			ExtentListener.getNode().pass("All Class Attendance 2 sections executed successfully.");
		} else {
			ExtentListener.getNode().fail("Total Failed Sections in Class Attendance 2 Flow: " + failCount);
		}

		soft.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void summarizeClassSchedulingFlow() {
		System.out.println("==== Class Attendance Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("====================================");
	}
}
