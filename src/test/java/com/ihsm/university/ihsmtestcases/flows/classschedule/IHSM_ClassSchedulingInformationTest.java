package com.ihsm.university.ihsmtestcases.flows.classschedule;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.classchedule.IHSM_ClassSchedule;
import com.ihsm.university.ihsmtestcases.dataprovider.ClassSchedulingDataProvider;
import com.ihsm.university.ihsmtestcases.pojo.ClassSchedulingData;
import com.ihsm.university.utilities.ExtentListener;
import com.ihsm.university.utilities.RetryAnalyzer;

public class IHSM_ClassSchedulingInformationTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();

	@Test(enabled = true, groups = "Regression", priority = 0, description = "Verify Class Scheduling (Online) Test", dataProvider = "ClassScheduling", dataProviderClass = ClassSchedulingDataProvider.class)
	public void verifyClassSchedule3(ClassSchedulingData data) throws Exception {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentTest node = ExtentListener.createNode("Class Scheduling Information");
		boolean isSuccess = false;

		try {
			node.info("Entering Class Scheduling Details");
			node.info("Academic Session: " + data.getAcademicSession());
			node.info("Batch: " + data.getBatch());
			node.info("Academic Plan: " + data.getAcademicPlan());
			node.info("Semester: " + data.getSemester());
			node.info("Subject: " + data.getSubject());
			node.info("Class Type: " + data.getClassType());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("Day: " + data.getDay());
			node.info("Frequency: " + data.getFrequency());
			node.info("Time Slot: " + data.getTimeSlot());
			
			IHSM_ClassSchedule classInfo = new IHSM_ClassSchedule(getDriver());
			/*
			 * classInfo.fillClassSchedulingInformation("2025 -2026", "1", 1, 1, 1, 0,
			 * "2026-02-02", "2026-02-08", "THU", "1 Class Every Week", "07:30 - 09:00");
			 */
			classInfo.fillClassSchedulingInformation(data.getAcademicSession(), data.getBatch(), data.getAcademicPlan(),
					data.getSemester(), data.getSubject(), data.getClassType(), data.getStartDate(), data.getEndDate(),
					data.getDay(), data.getFrequency(), data.getTimeSlot());
			isSuccess = true;
			node.pass("Class Scheduling added successfully");

		} catch (Exception e) {
			node.fail("Class Scheduling failed: " + e.getMessage());

			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Class Scheduling [" + data.getAcademicSession() + "-" + data.getBatch() + "]",
					isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(enabled = false, priority = 1, description = "Verify Class Scheduling (Offline) Test")
	public void verifyClassSchedule2() throws Exception {

		String[] dates = TestDataGenerator.getRandomScheduleDates();

		ExtentTest node = ExtentListener.createNode("Class Scheduling Information");
		boolean isSuccess = false;

		try {
			node.info("Entering Class Scheduling Details");
			node.info("Academic Session: 2025 -2026");
			node.info("Batch: 1");
			node.info("Academic Plan: 1");
			node.info("Semester: 1");
			node.info("Subject: 1");
			node.info("Class Type: 0");
			node.info("Start Date: " + dates[0]);
			node.info("End Date: " + dates[1]);
			node.info("Day: SAT");
			node.info("Location: Administration corpus, 310");
			node.info("Frequency: 1 Class Every Week");
			node.info("Time Slot: 07:30 - 09:00");
			
			IHSM_ClassSchedule classInfo = new IHSM_ClassSchedule(getDriver());
			classInfo.fillClassSchedulingOffline("2025 -2026", "1", 1, 1, 1, 0, "2026-06-08", "2026-06-16", "SAT",
					"Administration corpus, 310", "1 Class Every Week", "07:30 - 09:00");
			isSuccess = true;
			node.pass("Class Scheduling added successfully");

		} catch (Exception e) {
			node.fail("Class Scheduling failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Class Scheduling (Offline)", isSuccess ? "PASS" : "FAIL");
		}
	}

	@AfterClass(alwaysRun = true)
	public void summarizeClassSchedulingFlow() {
		System.out.println("\n==== Class Scheduling Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("====================================\n");
	}
}
