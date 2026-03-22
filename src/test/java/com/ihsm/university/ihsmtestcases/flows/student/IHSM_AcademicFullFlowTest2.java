package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.academics.*;
import com.ihsm.university.ihsmtestcases.dataprovider.DiplomaData;
import com.ihsm.university.ihsmtestcases.dataprovider.LastEducationData;
import com.ihsm.university.ihsmtestcases.dataprovider.QualificationData;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.ihsmtestcases.pojo.StudentAcademicsDiplomaData;
import com.ihsm.university.ihsmtestcases.pojo.StudentAcademicsLastEducation;
import com.ihsm.university.ihsmtestcases.pojo.StudentAcademicsQualification;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_AcademicFullFlowTest2 extends BaseClass {

	private Academics_Qualification_LastEducation lastInfo;
	private Academics_Qualification_Diploma diplomaInfo;
	private Academics_Qualification_Qualification qualificationInfo;

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	// ---------------- LAST EDUCATION ----------------
	@Test(groups = "Regression", dataProvider = "StudentAcademicsLastEducation", dataProviderClass = LastEducationData.class, description = "Verify Student Last Education Information Test")
	public void lastEducationInformation(StudentAcademicsLastEducation data) {
		ExtentTest node = ExtentListener.createNode("Academics - Last Education Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Last Education Information");
			node.info("Education Type: " + data.getEducationType());
			node.info("School Name: " + data.getSchoolName());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("Graduation Date: " + data.getGraduationDate());
			node.info("Score: " + data.getScore());
			node.info("Major: " + data.getMajor());
			node.info("Percentage: " + data.getPercentage());
//			node.info("Photo: " + TestDataGenerator.randomEmployeePhotoFile());

			lastInfo = new Academics_Qualification_LastEducation(getDriver());

			diplomaInfo = lastInfo.fillLastEducationInfo(data.getEducationType(), data.getSchoolName(),
					data.getStartDate(), data.getEndDate(), data.getGraduationDate(), data.getScore(), data.getMajor(),
					data.getPercentage(), getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Last Education Information completed");

		} catch (Exception e) {
			node.fail("Last Education Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Last Education", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- DIPLOMA ----------------
	@Test(groups = "Regression", dependsOnMethods = "lastEducationInformation", dataProvider = "StudentAcademicsDiplomaData", dataProviderClass = DiplomaData.class, description = "Verify Student Diploma Information Test")
	public void diplomaInformation(StudentAcademicsDiplomaData data) {
		ExtentTest node = ExtentListener.createNode("Academics - Diploma Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Diploma Information");
			node.info("Diploma No: " + data.getDiplomaNo());
			node.info("DN Code: " + data.getDnCode());
			node.info("DR Code: " + data.getDrCode());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("College Name: " + data.getCollegeName());
			node.info("Status: " + data.getStatus());
			node.info("Type: " + data.getType());
			node.info("Score: " + data.getScore());
			node.info("Graduation Date: " + data.getGraduationDate());
//			node.info("Photo: " + TestDataGenerator.randomEmployeePhotoFile());

			qualificationInfo = diplomaInfo.fillDiplomaDetails(data.getDiplomaNo(), data.getDnCode(), data.getDrCode(),
					data.getStartDate(), data.getEndDate(), data.getCollegeName(), data.getStatus(), data.getType(),
					data.getScore(), data.getGraduationDate(), getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Diploma Information completed");

		} catch (Exception e) {
			node.fail("Diploma Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Diploma", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- QUALIFICATION ----------------
	@Test(groups = "Regression", dependsOnMethods = "diplomaInformation", dataProvider = "StudentAcademicsQualification", dataProviderClass = QualificationData.class, description = "Verify Student Qualification Information Test")
	public void qualificationInformation(StudentAcademicsQualification data) {
		ExtentTest node = ExtentListener.createNode("Academics - Qualification Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Qualification Information");
			node.info("Last Education: " + data.getLastEducation());
			node.info("School Name: " + data.getSchoolName());
			node.info("Qualification Code: " + data.getQualificationCode());
			node.info("Start Date: " + data.getStartDate());
			node.info("End Date: " + data.getEndDate());
			node.info("Graduation Date: " + data.getGraduationDate());
			node.info("Status: " + data.getStatus());
			node.info("Country: " + data.getCountry());
			node.info("State: " + data.getState());
			node.info("City: " + data.getCity());
//			node.info("Photo: " + TestDataGenerator.randomEmployeePhotoFile());

			qualificationInfo.fillQualificationInformation(data.getLastEducation(), data.getSchoolName(),
					data.getQualificationCode(), data.getStartDate(), data.getEndDate(), data.getGraduationDate(),
					data.getStatus(), data.getCountry(), data.getState(), data.getCity(), getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Qualification Information completed");

		} catch (Exception e) {
			node.fail("Qualification Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Qualification", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeAcademicFlow() {
		System.out.println("==== Academic Flow Status for Student ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
