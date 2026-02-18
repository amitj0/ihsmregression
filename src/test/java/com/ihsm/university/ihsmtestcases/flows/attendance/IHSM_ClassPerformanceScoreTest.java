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

public class IHSM_ClassPerformanceScoreTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();
	String[] dates = TestDataGenerator.getRandomScheduleDates();

	@Test(priority = 0, description = "Verify Class Attendance 3")
	public void fillClassAttendance3() {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentTest node =  ExtentListener.createNode("Class Attendance 3 Information");
		int failCount = 0;

		try {
			node.info("Entering Class Attendance Details");
			List<Integer> attendanceValues = Arrays.asList(40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40,
					40, 40,40,40,40,40);
			IHSM_ClassAttendance classAttendance = new IHSM_ClassAttendance(getDriver());
			classAttendance.fillClassAttendance3(1, 1, 3, attendanceValues);
			node.pass("Class Attendance 3 added successfully");
			stepStatus.put("Class Attendance", "PASS");
		} catch (Exception e) {
			node.fail("Class Attendance 3 failed: " + e.getMessage());
			stepStatus.put("Class Attendance 3", "FAIL");
			soft.fail("Class Attendance 3 failed: " + e.getMessage());
			failCount++;
		}

		if (failCount == 0) {
			node.pass("All Class Attendance 3 sections executed successfully.");
		} else {
			node.fail("Total Failed Sections in Class Attendance 3 Flow: " + failCount);
		}

		soft.assertAll();
	}

}
