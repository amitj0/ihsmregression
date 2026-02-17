package com.ihsm.university.ihsmtestcases.flows.student;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.academics.Academics_Qualification_Diploma;
import com.ihsm.university.ihsmpageobjects.student.academics.Academics_Qualification_LastEducation;
import com.ihsm.university.ihsmpageobjects.student.academics.Academics_Qualification_Qualification;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_FullAcademicsFlow extends BaseClass {

	private WebDriver driver;

	public IHSM_FullAcademicsFlow(WebDriver driver) {
		this.driver = driver;
	}

	public void execute(StudentFullRegistrationDataVariables student) throws Exception {
		logger.info("===== STARTING FULL ACADEMICS FLOW =====");
		SoftAssert soft = new SoftAssert();
		int failCount = 0;

		// ---------------- Last Education ----------------
		try {
			ExtentListener.createNode("Academics Qualification Information");
			Academics_Qualification_LastEducation lastInfo = new Academics_Qualification_LastEducation(getDriver());
			lastInfo.fillLastEducationInfo(student.lastEducation, student.lastEducationSchool,
					student.lastEducationStartDate, student.lastEducationEndDate, student.lastEducationGraduationDate,
					student.lastEducationMarks, student.lastEducationSubject, student.lastEducationPercentage,
					getTestDataPath(student.lastEducationImage));
			logger.info("Last Education Information submitted successfully");
			ExtentListener.getNode().pass("Student Last Education Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Academic Last Qualification Information Failed :" + e.getMessage());
			failCount++;
		}

		// ---------------- Diploma Information ----------------
		try {
			ExtentListener.createNode("Academics Qual Diploma Information");
			Academics_Qualification_Diploma diplomaInfo = new Academics_Qualification_Diploma(getDriver());
			diplomaInfo.fillDiplomaDetails(student.diplomaCode, student.diplomaNumber, student.diplomaRegistration,
					student.diplomaStartDate, student.diplomaEndDate, student.diplomaInstitution,
					student.diplomaRemarks, student.diplomaType, student.diplomaMarks, student.diplomaGraduationDate,
					getTestDataPath(student.diplomaImage));
			logger.info("Diploma Information submitted successfully");
			ExtentListener.getNode().pass("Student Diploma Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Academic Diploma Qualification Information Failed :" + e.getMessage());
			failCount++;
		}

		// ---------------- Qualification Information ----------------
		try {
			ExtentListener.createNode("Academics Qual Qualification Information");
			Academics_Qualification_Qualification qualificationInfo = new Academics_Qualification_Qualification(
					getDriver());
			qualificationInfo.fillQualificationInformation(student.qualificationType, student.qualificationInstitution,
					student.qualificationRegistrationNumber, student.qualificationStartDate,
					student.qualificationEndDate, student.qualificationCompletionDate, student.qualificationStatus,
					student.qualificationCountry, student.qualificationState, student.qualificationCity,
					getTestDataPath(student.qualificationImage));
			logger.info("Qualification Information submitted successfully");
			ExtentListener.getNode().pass("Student Qualification Information Test Passed");
		} catch (Exception e) {
			ExtentListener.getNode().fail(e);
			soft.fail("Student Academic Qual Qualification Information Failed :" + e.getMessage());
			failCount++;
		}
		
		ExtentListener.createNode("ACADEMIC INFORMATION FLOW SUMMARY");

		if (failCount == 0) {
		    ExtentListener.getNode().pass("All Academics Information sections executed successfully.");
		} else {
		    ExtentListener.getNode().fail("Total Failed Sections in Academic Information Flow: " + failCount);
		}
		soft.assertAll();
		logger.info("===== FULL ACADEMICS INFORMATION FLOW COMPLETED =====");
	}
}
