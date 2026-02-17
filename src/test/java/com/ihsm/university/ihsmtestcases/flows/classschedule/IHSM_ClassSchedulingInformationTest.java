package com.ihsm.university.ihsmtestcases.flows.classschedule;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.classchedule.IHSM_ClassSchedule;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_ClassSchedulingInformationTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();

	@Test(enabled = false,priority = 0, testName = "Verify Class Scheduling")
	public void verifyClassSchedule() {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentListener.createNode("Class Scheduling Information");
		int failCount = 0;

		try {
			IHSM_ClassSchedule classInfo = new IHSM_ClassSchedule(getDriver());
			classInfo.fillClassSchedulingInformation("2025 -2026", "1", 1, 1, 1, 0, "2026-02-02", "2026-02-08",
					"MON", "1 Class Every Week", "07:30 - 09:00");
			ExtentListener.getNode().pass("Class Scheduling added successfully");
			stepStatus.put("Class Scheduling", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Class Scheduling failed: " + e.getMessage());
			stepStatus.put("Class Scheduling", "FAIL");
			soft.fail("Class Scheduling failed: " + e.getMessage());
			failCount++;
		}

		if (failCount == 0) {
			ExtentListener.getNode().pass("All Class Scheduling sections executed successfully.");
		} else {
			ExtentListener.getNode().fail("Total Failed Sections in Class Scheduling Flow: " + failCount);
		}

		soft.assertAll();
	}
	@Test(groups = "Regression", priority = 1, testName = "Verify Class Scheduling")
	public void verifyClassSchedule3() {
		
		String[] dates = TestDataGenerator.getRandomScheduleDates();
		
		ExtentListener.createNode("Class Scheduling Information");
		int failCount = 0;
		
		try {
			IHSM_ClassSchedule classInfo = new IHSM_ClassSchedule(getDriver());
			classInfo.fillClassSchedulingInformation("2025 -2026", "1", 1, 1, 1, 0, "2026-02-02", "2026-02-08",
					"THU", "1 Class Every Week", "07:30 - 09:00");
			ExtentListener.getNode().pass("Class Scheduling added successfully");
			stepStatus.put("Class Scheduling", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Class Scheduling failed: " + e.getMessage());
			stepStatus.put("Class Scheduling", "FAIL");
			soft.fail("Class Scheduling failed: " + e.getMessage());
			failCount++;
		}
		
		if (failCount == 0) {
			ExtentListener.getNode().pass("All Class Scheduling sections executed successfully.");
		} else {
			ExtentListener.getNode().fail("Total Failed Sections in Class Scheduling Flow: " + failCount);
		}
		
		soft.assertAll();
	}

	@Test(enabled = false, priority = 1, testName = "Verify Class Scheduling")
	public void verifyClassSchedule2() {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentListener.createNode("Class Scheduling Information");
		int failCount = 0;

		try {
			IHSM_ClassSchedule classInfo = new IHSM_ClassSchedule(getDriver());
			classInfo.fillClassSchedulingOffline("2025 -2026", "1", 1, 1, 1, 0, "2026-06-08", "2026-06-16", "SAT",
					"Administration corpus, 310", "1 Class Every Week", "07:30 - 09:00");
			ExtentListener.getNode().pass("Class Scheduling added successfully");
			stepStatus.put("Class Scheduling", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Class Scheduling failed: " + e.getMessage());
			stepStatus.put("Class Scheduling", "FAIL");
			soft.fail("Class Scheduling failed: " + e.getMessage());
			failCount++;
		}

		if (failCount == 0) {
			ExtentListener.getNode().pass("All Class Scheduling sections executed successfully.");
		} else {
			ExtentListener.getNode().fail("Total Failed Sections in Class Scheduling Flow: " + failCount);
		}

		soft.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void summarizeClassSchedulingFlow() {
		System.out.println("==== Class Scheduling Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("====================================");
	}
}
