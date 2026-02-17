package com.ihsm.university.ihsmtestcases.flows.classschedule;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.classchedule.IHSM_ClassSchedule_SubjectCredits;
import com.ihsm.university.ihsmpageobjects.classchedule.IHSM_ClassSchedule_SubjectHours;
import com.ihsm.university.ihsmtestcases.dataprovider.ClassScheduleDataProvider;
import com.ihsm.university.ihsmtestcases.pojo.SubjectCreditData;
import com.ihsm.university.ihsmtestcases.pojo.SubjectHoursData;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_ClassSubjectCreditTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	@Test(enabled = true,priority = 0, dataProvider = "SubjectCreditData", dataProviderClass = ClassScheduleDataProvider.class, testName = "Verify Subject Credit")
	public void verifySubjectCredit(SubjectCreditData data) {

		ExtentListener.createNode("Subject Credit Information");
		SoftAssert soft = new SoftAssert();
		int failCount = 0;

		try {
			IHSM_ClassSchedule_SubjectCredits subject = new IHSM_ClassSchedule_SubjectCredits(getDriver());
			subject.fillSubjectCreditInformation(data.getSessionField(), data.getBatchField(),
					data.getAcademicPlanField(), data.getSemField());
//			Assert.assertEquals(subject.modalSuccessMsg(), "Subject Aready Saved");
			ExtentListener.getNode().pass("Subject Credit Information Test Passed");
			stepStatus.put("Subject Credit", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Subject Credit Information failed: " + e.getMessage());
			stepStatus.put("Subject Credit", "FAIL");
			soft.fail("Subject Credit Information failed: " + e.getMessage());
			failCount++;
		}

		if (failCount > 0) {
			ExtentListener.getNode().fail("Total Failed Sections in Subject Credit Flow: " + failCount);
		}

		soft.assertAll();
	}

	@Test(enabled = true, testName = "Verify Subject Hours", priority = 1, dependsOnMethods = "verifySubjectCredit", dataProvider = "SubjectHoursData", dataProviderClass = ClassScheduleDataProvider.class, alwaysRun = true)
	public void verifySubjectHours(SubjectHoursData data) {
		ExtentListener.createNode("Subject Credit HOURS Information");
		SoftAssert soft = new SoftAssert();
		int failCount = 0;

		try {
			IHSM_ClassSchedule_SubjectHours hours = new IHSM_ClassSchedule_SubjectHours(getDriver());
			hours.fillSubjectHoursInformation(data.getSession(), data.getBatch(), data.getAcademicPlan(),
					data.getCourse(), data.getTotalCreditHours(), data.getCreditLectureHours(),
					data.getCreditPracticalHours(), data.getSelfStudyHours(), data.getTotalAcademicsHours(),
					data.getAcademicLecHours(), data.getAcademicPracHours(), data.getAcademicSaminarHours(),
					data.getLabHours(), data.getFacultyHours(), data.getExamType(), data.getControlPassingMarks(),
					data.getMaxMarks(), data.getExamPassingMarks(), data.getMaxExamMarks());
			ExtentListener.getNode().pass("Subject Credit HOURS Information Test Passed");
			stepStatus.put("Subject Hours", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Subject Credit HOURS Information failed: " + e.getMessage());
			stepStatus.put("Subject Hours", "FAIL");
			soft.fail("Subject Credit HOURS Information failed: " + e.getMessage());
			failCount++;
		}

		if (failCount > 0) {
			ExtentListener.getNode().fail("Total Failed Sections in Subject Credit HOURS Flow: " + failCount);
		}

		soft.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void summarizeClassSubjectCreditFlow() {
		System.out.println("==== Class Subject Credit Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=========================================");
	}
}
