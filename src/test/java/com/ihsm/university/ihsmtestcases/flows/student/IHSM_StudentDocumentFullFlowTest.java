package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.documents.*;
import com.ihsm.university.ihsmtestcases.dataprovider.IdentificationCardData;
import com.ihsm.university.ihsmtestcases.dataprovider.OtherDocumentsData;
import com.ihsm.university.ihsmtestcases.dataprovider.PassportData;
import com.ihsm.university.ihsmtestcases.dataprovider.PassportLocationData;
import com.ihsm.university.ihsmtestcases.dataprovider.VisaOfflineData;
import com.ihsm.university.ihsmtestcases.dataprovider.VisaOnlineData;
import com.ihsm.university.ihsmtestcases.dataprovider.VisaRegisterData;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.ihsmtestcases.pojo.StudentIdentificationData;
import com.ihsm.university.ihsmtestcases.pojo.StudentOtherDocuments;
import com.ihsm.university.ihsmtestcases.pojo.StudentPassportData;
import com.ihsm.university.ihsmtestcases.pojo.StudentPassportLocationData;
import com.ihsm.university.ihsmtestcases.pojo.StudentVisaOfflineData;
import com.ihsm.university.ihsmtestcases.pojo.StudentVisaOnlineData;
import com.ihsm.university.ihsmtestcases.pojo.StudentVisaRegisterData;
import com.ihsm.university.utilities.ExtentListener;
import com.ihsm.university.utilities.TextUtility;

public class IHSM_StudentDocumentFullFlowTest extends BaseClass {

	private Documents_OtherDocuments otherDocuments;
	private Documents_IdentificationCard identificationCard;
	private Documents_VisaInfo_OffVisa visaOffline;
	private Documents_VisaInfo_OnVisa visaOnline;
	private Documents_VisaInfo_Register visaRegister;
	private Documents_VisaInfo_PassportLocation passportLocation;
	private Documents_PassportInformation passportInfo;

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	// ---------------- OTHER DOCUMENTS ----------------
	@Test(groups = "Regression", dataProvider = "StudentOtherDocuments", dataProviderClass = OtherDocumentsData.class, description = "Verify Student Other Documents Information Test")
	public void otherDocumentsInformation(StudentOtherDocuments data) {
		ExtentTest node = ExtentListener.createNode("Other Documents Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Other Documents Information");
			node.info("Document Type: " + data.getDocumentType());
//			node.info("Document File: " + TestDataGenerator.randomEmployeePhotoFile());

			otherDocuments = new Documents_OtherDocuments(getDriver());

			identificationCard = otherDocuments.fillOtherDocumentsForm(data.getDocumentType(),
					getTestDataPath("male.png"));

			String actualMsg = otherDocuments.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "The information has been saved and updated in the system.");

			isSuccess = true;
			node.pass("Other Documents Information completed");

		} catch (Exception e) {
			node.fail("Other Documents Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Other Documents", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- IDENTIFICATION CARD ----------------
	@Test(groups = "Regression", dependsOnMethods = "otherDocumentsInformation", dataProvider = "StudentIdentificationCardData", dataProviderClass = IdentificationCardData.class, description = "Verify Student Identification Card Information Test")
	public void identificationCardInformation(StudentIdentificationData data) {
		ExtentTest node = ExtentListener.createNode("Identification Card Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Identification Card Information");
			node.info("ID Number: " + data.getIdNumber());
			node.info("Country: " + data.getCountry());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
//			node.info("Photo: " + TestDataGenerator.randomEmployeePhotoFile());

			visaOffline = identificationCard.fillIdentificationCardDetails(data.getIdNumber(), data.getCountry(),
					data.getStartDate(), data.getEndDate(), getTestDataPath("male.png"));

			String actualMsg = identificationCard.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "The information has been saved and updated in the system.");
			isSuccess = true;
			node.pass("Identification Card Information completed");

		} catch (Exception e) {
			node.fail("Identification Card Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Identification Card", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- VISA OFFLINE ----------------
	@Test(groups = "Regression", dependsOnMethods = "identificationCardInformation", dataProvider = "StudentVisaOfflineData", dataProviderClass = VisaOfflineData.class, description = "Verify Student Visa Offline Information Test")
	public void visaOfflineInformation(StudentVisaOfflineData data) {
		ExtentTest node = ExtentListener.createNode("Visa Offline Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Visa Offline Information");
			node.info("Visa Type: " + data.getVisaType());
			node.info("Home Country: " + data.getHomeCountry());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("Issue Date: " + data.getIssueDate());
			node.info("Expiry Date: " + data.getExpiryDate());
			node.info("Visa Number: " + data.getVisaNumber());
			node.info("Country: " + data.getCountry());
			node.info("Notes: " + data.getNotes());
//			node.info("Photo: " + TestDataGenerator.randomEmployeePhotoFile());

			visaOnline = visaOffline.fillVisaInfoOffVisaForm(data.getVisaType(), data.getHomeCountry(),
					data.getStartDate(), data.getEndDate(), data.getIssueDate(), data.getExpiryDate(),
					data.getVisaNumber(), data.getCountry(), data.getNotes(), getTestDataPath("male.png"));
			String actualMsg = visaOffline.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "Student Visa Document Inserted Successfully");
			isSuccess = true;
			node.pass("Visa Offline Information completed");

		} catch (Exception e) {
			node.fail("Visa Offline Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Visa Offline", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- VISA ONLINE ----------------
	@Test(groups = "Regression", dependsOnMethods = "visaOfflineInformation", dataProvider = "StudentVisaOnlineData", dataProviderClass = VisaOnlineData.class, description = "Verify Student Visa Online Information Test")
	public void visaOnlineInformation(StudentVisaOnlineData data) {
		ExtentTest node = ExtentListener.createNode("Visa Online Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Visa Online Information");
			node.info("Visa Type: " + data.getVisaType());
			node.info("Visa Address: " + data.getVisaAddress());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("Expiry Date: " + data.getExpiryDate());
			node.info("Online Visa Number: " + data.getOnlineVisaNumber());

			visaRegister = visaOnline.fillOnlineVisaInfo(data.getVisaType(), data.getVisaAddress(), data.getStartDate(),
					data.getEndDate(), data.getExpiryDate(), data.getOnlineVisaNumber());
			String actualMsg = visaOnline.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "The information has been saved and updated in the system.");
			isSuccess = true;
			node.pass("Visa Online Information completed");

		} catch (Exception e) {
			node.fail("Visa Online Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Visa Online", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- VISA REGISTER ----------------
	@Test(groups = "Regression", dependsOnMethods = "visaOnlineInformation", dataProvider = "StudentVisaRegisterData", dataProviderClass = VisaRegisterData.class, description = "Verify Student Visa Register Information Test")
	public void visaRegisterInformation(StudentVisaRegisterData data) {
		ExtentTest node = ExtentListener.createNode("Visa Register Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Visa Register Information");
			node.info("Home Country: " + data.getHomeCountry());
			node.info("Country: " + data.getCountry());
			node.info("Register Date: " + data.getRegisterDate());
			node.info("Notes: " + data.getNotes());

			passportLocation = visaRegister.fillRegisterInfo(data.getHomeCountry(), data.getCountry(),
					data.getRegisterDate(), data.getNotes());
			String actualMsg = visaRegister.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "The information has been saved and updated in the system.");
			isSuccess = true;
			node.pass("Visa Register Information completed");
			stepStatus.put("Visa Register", "PASS");
		} catch (Exception e) {
			node.fail("Visa Register Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Visa Register", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- PASSPORT LOCATION ----------------
	@Test(groups = "Regression", dependsOnMethods = "visaRegisterInformation", dataProvider = "StudentPassportLocationData", dataProviderClass = PassportLocationData.class, description = "Verify Student Passport Location Information Test")
	public void passportLocationInformation(StudentPassportLocationData data) {
		ExtentTest node = ExtentListener.createNode("Passport Location Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Passport Location Information");
			node.info("Location: " + data.getLocation());
			node.info("Date: " + data.getDate());

			passportInfo = passportLocation.fillPassportLocationInfo(data.getLocation(), data.getDate());
			String actualMsg = passportLocation.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "Passport Location Saved");
			isSuccess = true;
			node.pass("Passport Location Information completed");

		} catch (Exception e) {
			node.fail("Passport Location Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Passport Location", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- PASSPORT ----------------
	@Test(groups = "Regression", dependsOnMethods = "passportLocationInformation", dataProvider = "StudentPassportData", dataProviderClass = PassportData.class, description = "Verify Student Passport Information Test")
	public void passportInformation(StudentPassportData data) {
		ExtentTest node = ExtentListener.createNode("Passport Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Passport Information");
			node.info("Passport Number: " + data.getPassportNumber());
			node.info("Place of Issue: " + data.getPlaceOfIssue());
			node.info("Issue Date: " + data.getIssueDate());
			node.info("Expiry Date: " + data.getExpiryDate());

			passportInfo.fillPassportInformation(data.getPassportNumber(), data.getPlaceOfIssue(), data.getIssueDate(),
					data.getExpiryDate());
			String actualMsg = passportInfo.getLastSuccessMsg();
			Assert.assertEquals(actualMsg, "The information has been saved and updated in the system.");
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
	public void summarizeDocumentFlow() {
		System.out.println("==== Document Flow Status for Student ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
