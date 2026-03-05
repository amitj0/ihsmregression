package com.ihsm.university.ihsmtestcases.flows.attendance;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.attandence.IHSM_ClassAttendance;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_ClassPerformanceScoreTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();
	String[] dates = TestDataGenerator.getRandomScheduleDates();

	@Test(priority = 0, description = "Verify Class Performance Score Test")
	public void fillClassPerformanceScore() throws IOException, InterruptedException {

		/*
		 * WebDriver empDriver = getDriver(); loginAsEmployee(empDriver);
		 */

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentTest node = ExtentListener.createNode("Class Performance Score Information");
		boolean isSuccess = false;

		try {
			node.info("Entering Class Performance Details");
			List<Integer> attendanceValues = Arrays.asList(40, 40, 40, 40, 40, 40);
			IHSM_ClassAttendance classAttendance = new IHSM_ClassAttendance(getDriver());
			classAttendance.fillClassAttendancePerformanceScore(1, 1, 1, attendanceValues);
			isSuccess = true;
			node.pass("Class Performance added successfully");
		} catch (Exception e) {
			node.fail("Class Performance failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Class Performance Score", isSuccess ? "PASS" : "FAIL");
		}
	}

}
