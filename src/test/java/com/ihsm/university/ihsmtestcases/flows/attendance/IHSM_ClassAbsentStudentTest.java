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

	@Test(priority = 1, description = "Verify Class Attendance 4")
	public void fillClassAttendance4() {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentTest node =  ExtentListener.createNode("Class Attendance 4 Information");
		int failCount = 0;

		try {
			node.info("Entering Class Attendance Details");
			List<Integer> attendanceValues = Arrays.asList(40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40);
			IHSM_ClassAttendance classAttendance = new IHSM_ClassAttendance(getDriver());
			classAttendance.fillClassAttendance4(1, 1, 1, attendanceValues);
			node.pass("Class Attendance 4 added successfully");
			stepStatus.put("Class Attendance", "PASS");
		} catch (Exception e) {
			node.fail("Class Attendance 4 failed: " + e.getMessage());
			stepStatus.put("Class Attendance 4", "FAIL");
			soft.fail("Class Attendance 4 failed: " + e.getMessage());
			failCount++;
		}

		if (failCount == 0) {
			node.pass("All Class Attendance 4 sections executed successfully.");
		} else {
			node.fail("Total Failed Sections in Class Attendance 4 Flow: " + failCount);
		}

		soft.assertAll();
	}

}
