package com.ihsm.university.ihsmtestcases.flows.student;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.basicinformation.*;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_FullBasicInformationFlow extends BaseClass {

	private WebDriver driver;

	public IHSM_FullBasicInformationFlow(WebDriver driver) {
		this.driver = driver;
	}

	public static String studentEnrollmentId;

	public void execute(StudentFullRegistrationDataVariables student) throws InterruptedException {
		logger.info("===== STARTING FULL BASIC INFORMATION FLOW =====");
		SoftAssert soft = new SoftAssert();
		int failCount =0;

		try {
			ExtentListener.createNode("Enrollnment Information");
			// ---------------- Enrollment Information ----------------
			BasicInfo_EnrollnmentInformation enrollInfo = new BasicInfo_EnrollnmentInformation(getDriver());
			enrollInfo.fillEnrollmentInformation(student.term, student.course, student.year, student.semester,
					student.pin, student.firstName, student.middleName, student.lastName, student.gender, student.dob,
					student.country, student.state, student.mobile, student.email, student.nationality);
			studentEnrollmentId = enrollInfo.getStudentEnrollmentId();

			if (studentEnrollmentId == null || studentEnrollmentId.isEmpty()) {
				throw new RuntimeException("Student Enrollment ID not generated!");
			}
			ExtentListener.getNode().pass("Student Enrollnment Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Enrollnment Information Failed :" + e.getMessage());
			failCount++;
		}
		logger.info("Generated Enrollment ID = " + studentEnrollmentId);
		logger.info("Enrollment Information submitted successfully");

		// ---------------- Personal Information ----------------
		try {
			ExtentListener.createNode("Personal Information");
			BasicInfo_PersonalInformation personalInfo = new BasicInfo_PersonalInformation(getDriver());
			personalInfo.fillPersonalInformationForm(student.firstName2, student.lastName2, student.city,
					student.maritalStatus, student.country2);

			logger.info("Personal Information submitted successfully");
			ExtentListener.getNode().pass("Student Personal Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Personal Information Failed :" + e.getMessage());
			failCount++;
		}
		// ---------------- Biometrics ----------------
		try {
			ExtentListener.createNode("Biometrics Information");
			BasicInfo_Biometrics biometrics = new BasicInfo_Biometrics(getDriver());
			biometrics.fillBiometricsInfo(getTestDataPath(student.biometricsImage));
			logger.info("Biometrics Information submitted successfully");
			ExtentListener.getNode().pass("Student Biometrics Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Biometrics Information Failed :" + e.getMessage());
			failCount++;
		}

		// ---------------- Family Information ----------------
		try {
			ExtentListener.createNode("Family Information");
			BasicInfo_FamilyInformation familyInfo = new BasicInfo_FamilyInformation(getDriver());
			familyInfo.fillFamilyInformation(student.relation, student.familyName, student.familyDob,
					student.occupation, student.countryCode, student.phone, student.dependent, student.famCountry,
					student.famState, student.famCity, student.famNationality);
			logger.info("Family Information submitted successfully");
			ExtentListener.getNode().pass("Student Family Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Family Information Failed :" + e.getMessage());
			failCount++;
		}

		// ---------------- Language Information ----------------
		try {
			ExtentListener.createNode("Language Information");
			BasicInfo_LanguageInformation languageInfo = new BasicInfo_LanguageInformation(getDriver());
			languageInfo.fillLanguageInformationForm(student.language, student.languageLevel);
			logger.info("Language Information submitted successfully");
			ExtentListener.getNode().pass("Student Language Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Language Information Failed :" + e.getMessage());
			failCount++;
		}

		// ---------------- Pre Rights Information ----------------
		try {
			ExtentListener.createNode("General Pre Rights Information");
			BasicInfo_GeneralInformation_Prerights prerightsInfo = new BasicInfo_GeneralInformation_Prerights(
					getDriver());
			prerightsInfo.fillPreferRightsInformation(student.preRights, getTestDataPath(student.preRightsImage));
			logger.info("Pre Rights Information submitted successfully");
			ExtentListener.getNode().pass("Student Pre Rights Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Pre Rights Information Failed :" + e.getMessage());
			failCount++;
		}

		// ---------------- Social Status ----------------
		try {
			ExtentListener.createNode("General Social Status Information");
			BasicInfo_GeneralInformation_SocialStatus socialInfo = new BasicInfo_GeneralInformation_SocialStatus(
					getDriver());
			socialInfo.fillSocialStatusForm(student.socialStatus, getTestDataPath(student.socialStatusImage));
			logger.info("Social Status Information submitted successfully");
			ExtentListener.getNode().pass("Student Social Status Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Social Status Information Failed :" + e.getMessage());
			failCount++;
		}

		// ---------------- Work Location ----------------
		try {
			ExtentListener.createNode("General Social Work Location Information");
			BasicInfo_GeneralInformation_SocialWorkLocation socialWorkInfo = new BasicInfo_GeneralInformation_SocialWorkLocation(
					getDriver());
			socialWorkInfo.fillSocialWorkLocationDetails(getTestDataPath(student.workLocationImage));
			logger.info("Work Location Information submitted successfully");
			ExtentListener.getNode().pass("Student Work Location Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Work Location Information Failed :" + e.getMessage());
			failCount++;
		}

		// ---------------- Medical - Vaccination ----------------
		try {
			ExtentListener.createNode("Medical Vaccination Information");
			BasicInfo_MedicalInformation_Vaccination medicalInfo = new BasicInfo_MedicalInformation_Vaccination(
					getDriver());
			medicalInfo.fillVaccinationInfo(student.vacDose, student.vacNumber, student.vacDate, student.vacCode,
					student.vacRemarks, getTestDataPath(student.vacImage));
			logger.info("Medical Information Information submitted successfully");
			ExtentListener.getNode().pass("Student Medical Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Medical Information Failed :" + e.getMessage());
			failCount++;
		}
		// ---------------- Medical - At Poly ----------------
		try {
			ExtentListener.createNode("Medical At Poly Information");
			BasicInfo_MedicalInforamtion_AtPoly medicalPolyInfo = new BasicInfo_MedicalInforamtion_AtPoly(getDriver());
			medicalPolyInfo.fillAtPolyMedicalInformation(student.polyDate, student.polyType,
					getTestDataPath(student.polyImage));
			logger.info("Medical At Poly Information submitted successfully");
			ExtentListener.getNode().pass("Student Medical At Poly Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Medical At Poly Information Failed :" + e.getMessage());
			failCount++;
		}
		// ---------------- Medical - Insurance ----------------
		try {
			ExtentListener.createNode("Medical Insurance Information");
			BasicInfo_MedicalInformation_Insurance medicalInsuranceInfo = new BasicInfo_MedicalInformation_Insurance(
					getDriver());
			medicalInsuranceInfo.fillInsuranceInformation(student.insStartDate, student.insEnd,
					getTestDataPath(student.insImage));
			logger.info("Medical Insurance Information submitted successfully");
			ExtentListener.getNode().pass("Student Medical Insurance Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Medical Insurance Information Failed :" + e.getMessage());
			failCount++;
		}
		// ---------------- Medical - Disability ----------------
		try {
			ExtentListener.createNode("Medical Disability Information");
			BasicInfo_MedicalInformation_Disability medicalDisabilityInfo = new BasicInfo_MedicalInformation_Disability(
					getDriver());
			medicalDisabilityInfo.fillDisabilityForm(student.disType, student.disCode, student.disDate,
					getTestDataPath(student.disImage));
			logger.info("Medical Disability Information submitted successfully");
			ExtentListener.getNode().pass("Student Medical Disability Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Medical Disability Information Failed :" + e.getMessage());
			failCount++;
		}
		ExtentListener.createNode("BASIC INFORMATION FLOW SUMMARY");

		if (failCount == 0) {
		    ExtentListener.getNode().pass("All Basic Information sections executed successfully.");
		} else {
		    ExtentListener.getNode().fail("Total Failed Sections in Basic Information Flow: " + failCount);
		}
		soft.assertAll();
		logger.info("===== FULL BASIC INFORMATION FLOW COMPLETED =====");
	}
}
