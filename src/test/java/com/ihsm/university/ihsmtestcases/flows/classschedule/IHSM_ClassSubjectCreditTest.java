package com.ihsm.university.ihsmtestcases.flows.classschedule;

import java.util.LinkedHashMap;
import java.util.Map;

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

public class IHSM_ClassSubjectCreditTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	@Test(groups = "Regression", enabled = true, priority = 0, dataProvider = "SubjectCreditData", dataProviderClass = ClassScheduleDataProvider.class, description = "Verify Subject Credit Test")
	public void verifySubjectCredit(SubjectCreditData data) {

		ExtentTest node = ExtentListener.createNode("Subject Credit Information");
		SoftAssert soft = new SoftAssert();
		boolean isSuccess = false;

		try {
			node.info("Entering Subject Credit Details");
			node.info("Academic Session: " + data.getSessionField());
			node.info("Batch: " + data.getBatchField());
			node.info("Academic Plan: " + data.getAcademicPlanField());
			node.info("Semester: " + data.getSemField());

			IHSM_ClassSchedule_SubjectCredits subject = new IHSM_ClassSchedule_SubjectCredits(getDriver());
			subject.fillSubjectCreditInformation(data.getSessionField(), data.getBatchField(),
					data.getAcademicPlanField(), data.getSemField());
//			Assert.assertEquals(subject.modalSuccessMsg(), "Subject Aready Saved");
			isSuccess = true;
			node.pass("Subject Credit Information Test Passed");

		} catch (Exception e) {
			node.fail("Subject Credit Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Subject Credit [" + data.getSessionField() + "-" + data.getBatchField() + "]",
					isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", enabled = true, description = "Verify Subject Hours Test", priority = 1, dependsOnMethods = "verifySubjectCredit", dataProvider = "SubjectHoursData", dataProviderClass = ClassScheduleDataProvider.class, alwaysRun = true)
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
			hours.fillSubjectHoursInformation(data.getSession(), data.getBatch(), data.getAcademicPlan(),
					data.getCourse(), data.getTotalCreditHours(), data.getCreditLectureHours(),
					data.getCreditPracticalHours(), data.getSelfStudyHours(), data.getTotalAcademicsHours(),
					data.getAcademicLecHours(), data.getAcademicPracHours(), data.getAcademicSaminarHours(),
					data.getLabHours(), data.getFacultyHours(), data.getExamType(), data.getControlPassingMarks(),
					data.getMaxMarks(), data.getExamPassingMarks(), data.getMaxExamMarks());
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
		System.out.println("\n==== Class Subject Credit Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=========================================\n");
	}
}
