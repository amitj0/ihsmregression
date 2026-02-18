package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

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

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	// ---------------- LAST EDUCATION ----------------
	@Test(groups = "Regression",dataProvider = "StudentAcademicsLastEducation", dataProviderClass = LastEducationData.class, description = "Verify Last Education Information Test")
	public void lastEducationInformation(StudentAcademicsLastEducation data) {
		ExtentListener.createNode("Academics - Last Education Information");
		try {
			Academics_Qualification_LastEducation lastInfo = new Academics_Qualification_LastEducation(getDriver());
			/*
			 * lastInfo.fillLastEducationInfo( "Diploma", "Dps School", "01012025",
			 * "01012026", "01012027", "85", "Science", "90",
			 * TestDataGenerator.randomEmployeePhotoFile() );
			 */

			lastInfo.fillLastEducationInfo(data.getEducationType(), data.getSchoolName(), data.getStartDate(),
					data.getEndDate(), data.getGraduationDate(), data.getScore(), data.getMajor(), data.getPercentage(),
					TestDataGenerator.randomEmployeePhotoFile());

			ExtentListener.getNode().pass("Last Education Information completed");
			stepStatus.put("Last Education", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Last Education Information failed: " + e.getMessage());
			stepStatus.put("Last Education", "FAIL");
		}
	}

	// ---------------- DIPLOMA ----------------
	@Test(groups = "Regression",dependsOnMethods = "lastEducationInformation", dataProvider = "StudentAcademicsDiplomaData", dataProviderClass = DiplomaData.class, description = "Verify Diploma Information Test")
	public void diplomaInformation(StudentAcademicsDiplomaData data) {
		ExtentListener.createNode("Academics - Diploma Information");
		try {
			Academics_Qualification_Diploma diplomaInfo = new Academics_Qualification_Diploma(getDriver());
			/*
			 * diplomaInfo.fillDiplomaDetails("DIP123", "DN123", "DR123", "01012024",
			 * "01012025", "Diploma College", "Completed", "Type1", "88", "01012025",
			 * TestDataGenerator.randomEmployeePhotoFile());
			 */
			diplomaInfo.fillDiplomaDetails(data.getDiplomaNo(), data.getDnCode(), data.getDrCode(), data.getStartDate(),
					data.getEndDate(), data.getCollegeName(), data.getStatus(), data.getType(), data.getScore(),
					data.getGraduationDate(), TestDataGenerator.randomEmployeePhotoFile());
			ExtentListener.getNode().pass("Diploma Information completed");
			stepStatus.put("Diploma", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Diploma Information failed: " + e.getMessage());
			stepStatus.put("Diploma", "FAIL");
		}
	}

	// ---------------- QUALIFICATION ----------------
	@Test(groups = "Regression",dependsOnMethods = "diplomaInformation", dataProvider = "StudentAcademicsQualification", dataProviderClass = QualificationData.class, description = "Verify Qualification Information Test")
	public void qualificationInformation(StudentAcademicsQualification data) {
		ExtentListener.createNode("Academics - Qualification Information");
		try {
			Academics_Qualification_Qualification qualificationInfo = new Academics_Qualification_Qualification(
					getDriver());
			/*
			 * qualificationInfo.fillQualificationInformation("Diploma", "Dps School",
			 * "Q123", "01012023", "01012026", "01012026", "Completed", "India",
			 * "Haryana (HR)", "Gurgaon", TestDataGenerator.randomEmployeePhotoFile());
			 */
			qualificationInfo.fillQualificationInformation(data.getLastEducation(), data.getSchoolName(),
					data.getQualificationCode(), data.getStartDate(), data.getEndDate(), data.getGraduationDate(),
					data.getStatus(), data.getCountry(), data.getState(), data.getCity(),
					TestDataGenerator.randomEmployeePhotoFile());
			ExtentListener.getNode().pass("Qualification Information completed");
			stepStatus.put("Qualification", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Qualification Information failed: " + e.getMessage());
			stepStatus.put("Qualification", "FAIL");
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
