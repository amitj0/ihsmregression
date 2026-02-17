package com.ihsm.university.ihsmtestcases.flows.student;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.status.Status_Status;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_FullStatusFlow extends BaseClass {

	private WebDriver driver;

	public IHSM_FullStatusFlow(WebDriver driver) {
		this.driver = driver;
	}

	public void execute(StudentFullRegistrationDataVariables student) {
		logger.info("===== STARTING FULL STATUS FLOW =====");
		SoftAssert soft = new SoftAssert();
		int failCount = 0;
		try {
			ExtentListener.createNode("Status Information");
			logger.info("Filling Status Information...");
			Status_Status statusInfo = new Status_Status(getDriver());
			statusInfo.fillStatusStatusForm(student.status, student.statusDate, student.statusCode,
					student.statusRemarks, getTestDataPath(student.statusImage));

			logger.info("Status Information submitted successfully");
			ExtentListener.getNode().pass("Student Status Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Status Information Failed :" + e.getMessage());
			failCount++;
		}
		ExtentListener.createNode("STATUS INFORMATION FLOW SUMMARY");

		if (failCount == 0) {
		    ExtentListener.getNode().pass("All Status Information sections executed successfully.");
		} else {
		    ExtentListener.getNode().fail("Total Failed Sections in Status Information Flow: " + failCount);
		}
		soft.assertAll();
		logger.info("===== FULL STATUS INFORMATION FLOW COMPLETED =====");
	}
}
