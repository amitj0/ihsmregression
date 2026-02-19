package com.ihsm.university.ihsmtestcases.flows.attendance;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.attandence.IHSM_ClassAttendance;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_ClassAbsentStudentTest extends BaseClass {
	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();

	@Test(priority = 1, description = "Verify Class Performance Absent Score Test")
	public void fillClassPerformanceAbsentScore() {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentTest node =  ExtentListener.createNode("Class Performance Absent Score Information");
		int failCount = 0;

		try {
			node.info("Entering Performance Absent Score Details");
			List<Integer> attendanceValues = Arrays.asList(40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40);
			IHSM_ClassAttendance classAttendance = new IHSM_ClassAttendance(getDriver());
			classAttendance.fillClassAttendance4(1, 1, 1, attendanceValues);
			node.pass("Class Performance Absent Score added successfully");
			stepStatus.put("Class Performance Absent Score", "PASS");
		} catch (Exception e) {
			node.fail("Class Performance Absent Score failed: " + e.getMessage());
			stepStatus.put("Class Performance Absent Score", "FAIL");
			soft.fail("Class Performance Absent Score failed: " + e.getMessage());
			failCount++;
		}

		if (failCount == 0) {
			node.pass("All Class Performance Absent Score sections executed successfully.");
		} else {
			node.fail("Total Failed Sections in Class Performance Absent Score Flow: " + failCount);
		}

		soft.assertAll();
	}

}
