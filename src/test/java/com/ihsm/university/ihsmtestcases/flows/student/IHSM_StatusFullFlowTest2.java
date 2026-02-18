package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.status.Status_Status;
import com.ihsm.university.ihsmtestcases.dataprovider.StudentStatusDataProvider;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.ihsmtestcases.pojo.StudentStatusData;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_StatusFullFlowTest2 extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	// ---------------- STATUS INFORMATION ----------------
	@Test(groups = "Regression",dataProvider = "StudentStatusData", dataProviderClass = StudentStatusDataProvider.class, description = "Verify Status Information Test")
	public void statusInformation(StudentStatusData data) {
		ExtentListener.createNode("Status Information");
		try {
			Status_Status statusInfo = new Status_Status(getDriver());
			statusInfo.fillStatusStatusForm(data.getStatus(), data.getStatusDate(), data.getStatusCode(),
					data.getNotes(), TestDataGenerator.randomEmployeePhotoFile());
			ExtentListener.getNode().pass("Status Information completed");
			stepStatus.put("Status Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Status Information failed: " + e.getMessage());
			stepStatus.put("Status Information", "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeStatusFlow() {
		System.out.println("==== Status Flow Status for Student ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
