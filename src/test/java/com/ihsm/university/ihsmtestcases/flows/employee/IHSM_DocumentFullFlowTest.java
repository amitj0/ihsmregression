package com.ihsm.university.ihsmtestcases.flows.employee;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.employee.documents.Documents_Documents;
import com.ihsm.university.ihsmpageobjects.employee.documents.Documents_Passport;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_DocumentFullFlowTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private int failCount = 0;

	@Test(description = "Verify Documents Information Test")
	public void documentsInformation() {
		ExtentListener.createNode("Document Information");
		try {
			Documents_Documents docInfo = new Documents_Documents(getDriver());
			docInfo.fillDocumentInformation("Diploma", TestDataGenerator.randomNotes(),
					TestDataGenerator.randomPhotoFile());
			ExtentListener.getNode().pass("Documents Information completed");
			stepStatus.put("Documents Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Documents Information failed: " + e.getMessage());
			stepStatus.put("Documents Information", "FAIL");
			failCount++;
		}
	}

	@Test(enabled = true, dependsOnMethods = "documentsInformation", alwaysRun = true, description = "Verify Passport Information Test")
	public void passportInformation() {
		ExtentListener.createNode("Passport Information");
		try {
			Documents_Passport docPassInfo = new Documents_Passport(getDriver());
			docPassInfo.fillPassportDetails("Development", "England", TestDataGenerator.randomIssueAgency(),
					TestDataGenerator.randomNumber(5), "ABCD1234567", "Manchester", "01012026", "01012027");
			ExtentListener.getNode().pass("Passport Information completed");
			stepStatus.put("Passport Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Passport Information failed: " + e.getMessage());
			stepStatus.put("Passport Information", "FAIL");
			failCount++;
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeDocumentsFlow() {
		ExtentListener.createNode("DOCUMENTS INFORMATION FLOW SUMMARY");
		if (failCount == 0) {
			ExtentListener.getNode().pass("All Documents Information sections executed successfully.");
		} else {
			ExtentListener.getNode().fail("Total Failed Sections in Documents Information Flow: " + failCount);
		}

		System.out.println("==== Documents Flow Status for Employee ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
