package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.status.Status_Status;
import com.ihsm.university.ihsmtestcases.dataprovider.StudentStatusDataProvider;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.ihsmtestcases.pojo.StudentStatusData;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_StudentStatusFullFlowTest extends BaseClass {

	private Status_Status statusInfo;
	private Map<String, String> stepStatus = new LinkedHashMap<>();

	// ---------------- STATUS INFORMATION ----------------
	@Test(groups = "Regression", dataProvider = "StudentStatusData", dataProviderClass = StudentStatusDataProvider.class, description = "Verify Student Status Information Test")
	public void statusInformation(StudentStatusData data) {
		ExtentTest node = ExtentListener.createNode("Status Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Status Information");
			node.info("Status: " + data.getStatus());
			node.info("Status Date: " + data.getStatusDate());
			node.info("Status Code: " + data.getStatusCode());
			node.info("Notes: " + data.getNotes());
//			node.info("Photo: " + TestDataGenerator.randomEmployeePhotoFile());

			statusInfo = new Status_Status(getDriver());
			statusInfo.fillStatusStatusForm(data.getStatus(), data.getStatusDate(), data.getStatusCode(),
					data.getNotes(), getTestDataPath("male.png"));
			
			String actualMsg = statusInfo.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "Student Status Inserted Successfully");
			isSuccess = true;
			node.pass("Status Information completed");

		} catch (Exception e) {
			node.fail("Status Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Status Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeStatusFlow() {
		System.out.println("\n==== Status Flow Status for Student ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================\n");
	}
}
