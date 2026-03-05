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
import com.ihsm.university.ihsmtestcases.pojo.ClassSchedulingData2;
import com.ihsm.university.ihsmtestcases.pojo.ClassSchedulingData3;
import com.ihsm.university.utilities.ExtentListener;
import com.ihsm.university.utilities.RetryAnalyzer;

public class IHSM_ClassSchedulingInformationTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();

	@Test(enabled = true, groups = "Regression", priority = 0, description = "Verify Class Scheduling (Online) LEC Test", dataProvider = "ClassScheduling", dataProviderClass = ClassSchedulingDataProvider.class)
	public void verifyClassSchedulingLEC(ClassSchedulingData data) throws Exception {

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
			node.info("Subject Name of Class Scheduling: " + data.getSubjectName());
			node.info("Column Name: " + data.getColumnName());
			node.info("Subject Name: " + data.getSubjectName());
			node.info("& Teacher Name: " + data.getTeacherName());
			node.info("Group Number: " + data.getGroupNumber());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("Day: " + data.getDay());
			node.info("Frequency: " + data.getFrequency());
			node.info("Time Slot: " + data.getTimeSlot());

			IHSM_ClassSchedule classInfo = new IHSM_ClassSchedule(getDriver());
			classInfo.fillClassSchedulingInformation(data.getAcademicSession(), data.getBatch(), data.getAcademicPlan(),
					data.getSemester(), data.getSubject(), data.getSubjectName(), data.getColumnName(),
					data.getSubjectName(), data.getTeacherName(), data.getGroupNumber(), data.getStartDate(),
					data.getEndDate(), data.getDay(), data.getFrequency(), data.getTimeSlot());
			isSuccess = true;
			node.pass("Class Scheduling LEC added successfully");

		} catch (Exception e) {
			node.fail("Class Scheduling LEC failed: " + e.getMessage());

			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Class Scheduling LEC [" + data.getAcademicSession() + "-" + data.getBatch() + "]",
					isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(enabled = true, groups = "Regression", priority = 1, description = "Verify Class Scheduling (Online) PRA Test", dataProvider = "ClassScheduling2", dataProviderClass = ClassSchedulingDataProvider.class)
	public void verifyClassSchedulingPRA(ClassSchedulingData2 data) throws Exception {

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
			node.info("Subject Name of Class Scheduling: " + data.getSubjectName());
			node.info("Column Name: " + data.getColumnName());
			node.info("Subject Name: " + data.getSubjectName());
			node.info("& Teacher Name: " + data.getTeacherName());
			node.info("Group Number: " + data.getGroupNumber());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("Day: " + data.getDay());
			node.info("Frequency: " + data.getFrequency());
			node.info("Time Slot: " + data.getTimeSlot());

			IHSM_ClassSchedule classInfo = new IHSM_ClassSchedule(getDriver());
			classInfo.fillClassSchedulingInformation(data.getAcademicSession(), data.getBatch(), data.getAcademicPlan(),
					data.getSemester(), data.getSubject(), data.getSubjectName(), data.getColumnName(),
					data.getSubjectName(), data.getTeacherName(), data.getGroupNumber(), data.getStartDate(),
					data.getEndDate(), data.getDay(), data.getFrequency(), data.getTimeSlot());
			isSuccess = true;
			node.pass("Class Scheduling PRA added successfully");

		} catch (Exception e) {
			node.fail("Class Scheduling PRA failed: " + e.getMessage());

			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Class Scheduling PRA [" + data.getAcademicSession() + "-" + data.getBatch() + "]",
					isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(enabled = false, priority = 2, description = "Verify Class Scheduling (Offline) LEC Test", dataProvider = "ClassScheduling3", dataProviderClass = ClassSchedulingDataProvider.class)
	public void verifyClassSchedulingOfflineLEC(ClassSchedulingData3 data) throws Exception {

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
			node.info("Subject Name of Class Scheduling: " + data.getSubjectName());
			node.info("Column Name: " + data.getColumnName());
			node.info("Subject Name: " + data.getSub());
			node.info("& Teacher Name: " + data.getTeacherName());
			node.info("Group Number: " + data.getGroupNumber());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("Day: " + data.getDay());
			node.info("Room: " + data.getRoom());
			node.info("Frequency: " + data.getFrequency());
			node.info("Time Slot: " + data.getTimeSlot());

			IHSM_ClassSchedule classInfo = new IHSM_ClassSchedule(getDriver());
			classInfo.fillClassSchedulingOffline(data.getAcademicSession(), data.getBatch(), data.getAcademicPlan(),
					data.getSemester(), data.getSubject(), data.getSubjectName(), data.getColumnName(), data.getSub(),
					data.getTeacherName(), data.getGroupNumber(), data.getStartDate(), data.getEndDate(), data.getDay(),
					data.getRoom(), data.getFrequency(), data.getTimeSlot());

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
