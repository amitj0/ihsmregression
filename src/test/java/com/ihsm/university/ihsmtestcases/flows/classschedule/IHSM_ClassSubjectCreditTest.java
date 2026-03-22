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
			node.info("Subject Value: " + data.getSubjectValue());

			IHSM_ClassSchedule_SubjectCredits subject = new IHSM_ClassSchedule_SubjectCredits(getDriver());
			String actualMsg = subject.fillSubjectCreditInformation(data.getSessionField(), data.getBatchField(),
					data.getAcademicPlanField(), data.getSemField(), data.getSubjectValue());
			Assert.assertEquals(actualMsg, "Subject Aready Saved");
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

	@AfterClass(alwaysRun = true)
	public void summarizeClassSubjectCreditFlow() {
		System.out.println("\n==== Class Subject Credit Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=========================================\n");
	}
}
