package com.ihsm.university.ihsmtestcases.flows.exammanage;

import static org.testng.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.exammanagement.IHSM_ManageExam;
import com.ihsm.university.ihsmtestcases.pojo.ExamManage;
import com.ihsm.university.utilities.ExamManageDataProvider;
import com.ihsm.university.utilities.ExtentListener;
import com.ihsm.university.utilities.RetryAnalyzer;

public class IHSM_ExamManageTest extends BaseClass {

	@Test(priority = 0,
			groups = "Regression",
			description = "Verify the Exam Manage (Not Saved) Test",
			dataProvider = "ExamManageData",
			dataProviderClass = ExamManageDataProvider.class)
	public void verifyExamManage_NotSaved(ExamManage data) {
		
		ExtentTest node = ExtentListener.createNode("Exam Manage (Not Saved) Information");
		
		try {
			node.info("Entering Exam Manage (Not Saved) Details");
			
			IHSM_ManageExam exam = new IHSM_ManageExam(getDriver());
			
			exam.fillExamManageInfo(
					data.getProgram(),
					data.getSemester(),
					data.getStartDate(),
					data.getEndDate()
					);
			String errorMsg = exam.isExamErrorMsg();
			assertEquals(errorMsg, "Exam Not Saved.");
			node.pass("Exam Manage (Not Saved) Information Test Passed");
		} catch (Exception e) {
			node.fail("Exam Manage (Not Saved) Information Failed: " + e.getMessage());
			throw e;
		}
	}
	
}
