package com.ihsm.university.ihsmtestcases.flows.student;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.documents.*;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_FullDocumentsFlow extends BaseClass {

	private WebDriver driver;

	public IHSM_FullDocumentsFlow(WebDriver driver) {
		this.driver = driver;
	}

	public void execute(StudentFullRegistrationDataVariables student) {
		logger.info("===== STARTING FULL DOCUMENTS FLOW =====");
		SoftAssert soft = new SoftAssert();
		int failCount = 0;
		try {
			ExtentListener.createNode("Other Documents Information");
			Documents_OtherDocuments otherDocs = new Documents_OtherDocuments(getDriver());
			otherDocs.fillOtherDocumentsForm(student.otherDocumentName, getTestDataPath(student.otherDocumentImage));
			logger.info("Other Documents Information submitted successfully");
			ExtentListener.getNode().pass("Student Other Documents Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Other Documents Information Failed :" + e.getMessage());
			failCount++;
		}

		try {
			ExtentListener.createNode("Documents Identification Information");
			Documents_IdentificationCard idCard = new Documents_IdentificationCard(getDriver());
			idCard.fillIdentificationCardDetails(student.idNumber, student.idCountry, student.idIssueDate,
					student.idExpiryDate, getTestDataPath(student.idImage));
			logger.info("Identification Card Information submitted successfully");
			ExtentListener.getNode().pass("Student Identification Card Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Identification Information Failed :" + e.getMessage());
			failCount++;
		}

		try {
			ExtentListener.createNode("Documents Visa Off Information");
			Documents_VisaInfo_OffVisa visaInfo = new Documents_VisaInfo_OffVisa(getDriver());
			visaInfo.fillVisaInfoOffVisaForm(student.visaType, student.visaPlaceOfIssue, student.visaIssueDate,
					student.visaStartDate, student.visaEndDate, student.visaRenewDate, student.visaNumber,
					student.visaCountry, student.visaRemarks, getTestDataPath(student.visaImage));
			logger.info("Visa Offline Information submitted successfully");
			ExtentListener.getNode().pass("Student Visa Offline Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Visa Offline Information Failed :" + e.getMessage());
			failCount++;
		}

		try {
			ExtentListener.createNode("Documents Visa On Information");
			Documents_VisaInfo_OnVisa visaOnlineInfo = new Documents_VisaInfo_OnVisa(getDriver());
			visaOnlineInfo.fillOnlineVisaInfo(student.visaOnlineType, student.visaOnlineStartDate,
					student.visaOnlineIssueDate, student.visaOnlineEndDate, student.visaOnlineNumber);
			logger.info("Visa Online Information submitted successfully");
			ExtentListener.getNode().pass("Student Online Visa Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Visa Online Information Failed :" + e.getMessage());
			failCount++;
		}

		try {
			ExtentListener.createNode("Documents Visa Register Information");
			Documents_VisaInfo_Register visaRegisterInfo = new Documents_VisaInfo_Register(getDriver());
			visaRegisterInfo.fillRegisterInfo(student.visaRegisterPlace, student.visaRegisterCountry,
					student.visaRegisterDate, student.visaRegisterRemarks);
			logger.info("Visa Register Information submitted successfully");
			ExtentListener.getNode().pass("Student Visa Register Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Visa Register Information Failed :" + e.getMessage());
			failCount++;
		}

		try {
			ExtentListener.createNode("Documents Passport Location Information");
			Documents_VisaInfo_PassportLocation visaPassportLocInfo = new Documents_VisaInfo_PassportLocation(
					getDriver());
			visaPassportLocInfo.fillPassportLocationInfo(student.passportLocation, student.passportLocationDate);
			logger.info("Visa Passport Location Information submitted successfully");
			ExtentListener.getNode().pass("Student Visa Passport Location Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Visa Passport Information Failed :" + e.getMessage());
			failCount++;
		}

		try {
			ExtentListener.createNode("Documents Passport Information");
			Documents_PassportInformation passportInfo = new Documents_PassportInformation(getDriver());
			passportInfo.fillPassportInformation(student.passportNumber, student.passportIssuePlace,
					student.passportIssueDate, student.passportExpiryDate);
			logger.info("Passport Information submitted successfully");
			ExtentListener.getNode().pass("Student Passport Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Passport Information Failed :" + e.getMessage());
			failCount++;
		}
		ExtentListener.createNode("DOCUMENTS INFORMATION FLOW SUMMARY");

		if (failCount == 0) {
		    ExtentListener.getNode().pass("All Documents Information sections executed successfully.");
		} else {
		    ExtentListener.getNode().fail("Total Failed Sections in Documents Information Flow: " + failCount);
		}
		soft.assertAll();
		logger.info("===== FULL DOCUMENTS FLOW COMPLETED =====");
	}
}
