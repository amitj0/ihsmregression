package com.ihsm.university.ihsmtestcases.flows.employee;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.employee.documents.Documents_Documents;
import com.ihsm.university.ihsmpageobjects.employee.documents.Documents_Passport;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_EmployeeDocumentFullFlowTest extends BaseClass {

	private Documents_Documents docInfo;
	private Documents_Passport docPassInfo;

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private int failCount = 0;

	@Test(description = "Verify Employee Documents Information Test")
	public void documentsInformation() {
		ExtentTest node = ExtentListener.createNode("Document Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Document Information");
			node.info("Document Type: " + "Diploma");
			node.info("Notes: " + TestDataGenerator.randomNotes());
//			node.info("Upload Document: " + TestDataGenerator.randomPhotoFile());

			docInfo = new Documents_Documents(getDriver());
			docPassInfo = docInfo.fillDocumentInformation("Diploma", TestDataGenerator.randomNotes(),
					getTestDataPath("male.png"));
			String actualMsg = docInfo.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "Employee Document Inserted Successfully");
			isSuccess = true;
			node.pass("Documents Information completed");
		} catch (Exception e) {
			node.fail("Documents Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Documents Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(enabled = true, dependsOnMethods = "documentsInformation", description = "Verify Employee Passport Information Test")
	public void passportInformation() {
		ExtentTest node = ExtentListener.createNode("Passport Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Passport Information");
			node.info("Passport Type: " + "Development");
			node.info("Country: " + "England");
			node.info("Issue Agency: " + TestDataGenerator.randomIssueAgency());
			node.info("Issue Number: " + TestDataGenerator.randomNumber(5));
			node.info("Passport Number: " + "ABCD1234567");
			node.info("Place of Issue: " + "Manchester");
			node.info("Issue Date: " + "01/01/2026");
			node.info("Expiry Date: " + "01/01/2027");

			docPassInfo.fillPassportDetails("Development", "England", TestDataGenerator.randomIssueAgency(),
					TestDataGenerator.randomNumber(5), "ABCD1234567", "Bishkek", "01012026", "01012027");
			String actualMsg = docPassInfo.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "Passport Data saved");
			isSuccess = true;
			node.pass("Passport Information completed");

		} catch (Exception e) {
			node.fail("Passport Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Passport Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeDocumentsFlow() {
		ExtentTest node = ExtentListener.createNode("DOCUMENTS INFORMATION FLOW SUMMARY");
		if (failCount == 0) {
			node.pass("All Documents Information sections executed successfully.");
		} else {
			node.fail("Total Failed Sections in Documents Information Flow: " + failCount);
		}

		System.out.println("==== Documents Flow Status for Employee ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
