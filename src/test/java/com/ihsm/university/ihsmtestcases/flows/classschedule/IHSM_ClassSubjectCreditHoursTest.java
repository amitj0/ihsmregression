package com.ihsm.university.ihsmtestcases.flows.classschedule;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.classchedule.IHSM_ClassSchedule_SubjectCredits;
import com.ihsm.university.ihsmpageobjects.classchedule.IHSM_ClassSchedule_SubjectHours;
import com.ihsm.university.ihsmtestcases.dataprovider.ClassScheduleDataProvider;
import com.ihsm.university.ihsmtestcases.pojo.SubjectCreditData;
import com.ihsm.university.ihsmtestcases.pojo.SubjectHoursData;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_ClassSubjectCreditHoursTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	@Test(groups = "Regression", enabled = true, description = "Verify Subject Hours Test", priority = 1, dataProvider = "SubjectHoursData", dataProviderClass = ClassScheduleDataProvider.class, alwaysRun = true)
	public void verifySubjectHours(SubjectHoursData data) {
		ExtentTest node = ExtentListener.createNode("Subject Credit HOURS Information");
		SoftAssert soft = new SoftAssert();
		boolean isSuccess = false;

		try {
			node.info("Entering Subject Credit Hours Details");
			node.info("Academic Session: " + data.getSession());
			node.info("Batch: " + data.getBatch());
			node.info("Academic Plan: " + data.getAcademicPlan());
			node.info("Course: " + data.getCourse());
			node.info("Subject Name: " + data.getSubjectValue());
			node.info("Total Credit Hours: " + data.getTotalCreditHours());
			node.info("Credit Lecture Hours: " + data.getCreditLectureHours());
			node.info("Credit Practical Hours: " + data.getCreditPracticalHours());
			node.info("Self Study Hours: " + data.getSelfStudyHours());
			node.info("Total Academics Hours: " + data.getTotalAcademicsHours());
			node.info("Academic Lecture Hours: " + data.getAcademicLecHours());
			node.info("Academic Practical Hours: " + data.getAcademicPracHours());
			node.info("Academic Seminar Hours: " + data.getAcademicSaminarHours());
			node.info("Lab Hours: " + data.getLabHours());
			node.info("Faculty Hours: " + data.getFacultyHours());
			node.info("Exam Type: " + data.getExamType());
			node.info("Control Passing Marks: " + data.getControlPassingMarks());
			node.info("Max Marks: " + data.getMaxMarks());
			node.info("Exam Passing Marks: " + data.getExamPassingMarks());
			node.info("Max Exam Marks: " + data.getMaxExamMarks());

			IHSM_ClassSchedule_SubjectHours hours = new IHSM_ClassSchedule_SubjectHours(getDriver());
			String actualMsg = hours.fillSubjectHoursInformation(data.getSession(), data.getBatch(), data.getAcademicPlan(),
					data.getCourse(), data.getSubjectValue(), data.getTotalCreditHours(), data.getCreditLectureHours(),
					data.getCreditPracticalHours(), data.getSelfStudyHours(), data.getTotalAcademicsHours(),
					data.getAcademicLecHours(), data.getAcademicPracHours(), data.getAcademicSaminarHours(),
					data.getLabHours(), data.getFacultyHours(), data.getExamType(), data.getControlPassingMarks(),
					data.getMaxMarks(), data.getExamPassingMarks(), data.getMaxExamMarks());
			Assert.assertEquals(actualMsg, "Subjects Saved Successfully");

			isSuccess = true;
			node.pass("Subject Credit HOURS Information Test Passed");

		} catch (Exception e) {
			node.fail("Subject Credit HOURS Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Subject Credit Hours [" + data.getSession() + "-" + data.getBatch() + "]",
					isSuccess ? "PASS" : "FAIL");
		}
	}

	@AfterClass(alwaysRun = true)
	public void summarizeClassSubjectCreditFlow() {
		System.out.println("\n==== Class Subject Hours Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=========================================\n");
	}
}
