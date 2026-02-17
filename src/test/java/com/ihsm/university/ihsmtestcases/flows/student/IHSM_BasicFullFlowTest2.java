package com.ihsm.university.ihsmtestcases.flows.student;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.basicinformation.*;
import com.ihsm.university.ihsmtestcases.dataprovider.EnrollnmentData;
import com.ihsm.university.ihsmtestcases.dataprovider.FamilyData;
import com.ihsm.university.ihsmtestcases.dataprovider.LanguageData;
import com.ihsm.university.ihsmtestcases.dataprovider.MedicalAtPolyData;
import com.ihsm.university.ihsmtestcases.dataprovider.MedicalDisabilityData;
import com.ihsm.university.ihsmtestcases.dataprovider.MedicalInsuranceData;
import com.ihsm.university.ihsmtestcases.dataprovider.MedicalVaccination;
import com.ihsm.university.ihsmtestcases.dataprovider.PersonalData;
import com.ihsm.university.ihsmtestcases.dataprovider.PreRightData;
import com.ihsm.university.ihsmtestcases.dataprovider.SocialStatusData;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.ihsmtestcases.pojo.StudentEnrollnmentData;
import com.ihsm.university.ihsmtestcases.pojo.StudentFamilyData;
import com.ihsm.university.ihsmtestcases.pojo.StudentLanguageData;
import com.ihsm.university.ihsmtestcases.pojo.StudentMedicalAtPolyData;
import com.ihsm.university.ihsmtestcases.pojo.StudentMedicalDisabilityData;
import com.ihsm.university.ihsmtestcases.pojo.StudentMedicalInsuranceData;
import com.ihsm.university.ihsmtestcases.pojo.StudentMedicalVaccinationData;
import com.ihsm.university.ihsmtestcases.pojo.StudentPersonalData;
import com.ihsm.university.ihsmtestcases.pojo.StudentPreRightData;
import com.ihsm.university.ihsmtestcases.pojo.StudentSocialStatusData;
import com.ihsm.university.navigation.Student_Search;
import com.ihsm.university.utilities.ExtentListener;
import com.ihsm.university.utilities.TextUtility;


public class IHSM_BasicFullFlowTest2 extends BaseClass {

	private static final String STUDENT_FILE = System.getProperty("user.dir") + "/target/student.data";

	private void saveStudentId(String id) throws Exception {
		File file = new File(STUDENT_FILE);
		file.getParentFile().mkdirs();
		java.nio.file.Files.write(file.toPath(), id.getBytes());
	}

	private String loadStudentId() throws Exception {
		File file = new File(STUDENT_FILE);
		if (!file.exists()) {
			return null;
		}
		return new String(java.nio.file.Files.readAllBytes(file.toPath())).trim();
	}

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	@Test(groups = "Regression",dataProvider = "StudentEnrollnmentData", dataProviderClass = EnrollnmentData.class)
	public void enrollmentInformation(StudentEnrollnmentData data) {

		ExtentListener.createNode("Enrollment Information");

		try {
			// Read action from properties
			String action = prop.getProperty("student.action", "NEW").toUpperCase();

			// Load existing student ID if any
			String existingId = loadStudentId();

			if ("EXISTING".equals(action)) {
				// Use existing student
				if (existingId != null && !existingId.isEmpty()) {
					String numericId = existingId.replaceAll("[^0-9]", "");

					logger.info("Using existing student: " + existingId);

					Student_Search searchPage = new Student_Search(getDriver());
					searchPage.fillStudentSearchInfo(numericId);

					stepStatus.put("Enrollment Information", "SKIPPED");
					ExtentListener.getNode().pass("Student already exists. Opened using ID: " + existingId);
					return;
				} else {
					throw new RuntimeException("No existing student ID found for EXISTING mode!");
				}
			}

			// Otherwise create NEW student
			BasicInfo_EnrollnmentInformation enrollInfo = new BasicInfo_EnrollnmentInformation(getDriver());

			// Fill enrollment form
			enrollInfo.fillEnrollmentInformation(data.getTerm(), data.getProgram(), String.valueOf(data.getYear()),
					String.valueOf(data.getSemester()), data.getPin(), data.getFirstName(), data.getMiddleName(),
					data.getLastName(), data.getGender(), data.getDob(), data.getCountry(), data.getState(),
					data.getPhone(), data.getEmail(), data.getNationality());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			String studentEnrollmentId = enrollInfo.getStudentEnrollmentId();
			if (studentEnrollmentId == null || studentEnrollmentId.isEmpty()) {
				throw new RuntimeException("Student Enrollment ID not generated!");
			}

			// Save the new student ID
			saveStudentId(studentEnrollmentId);

			ExtentListener.getNode().pass("New student enrollment created: " + studentEnrollmentId);
			stepStatus.put("Enrollment Information", "PASS");

		} catch (Exception e) {
			e.printStackTrace();
			ExtentListener.getNode().fail("Enrollment failed: " + e.getMessage());
			stepStatus.put("Enrollment Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "enrollmentInformation", dataProvider = "StudentPersonalData", dataProviderClass = PersonalData.class, alwaysRun = true)
	public void personalInformation(StudentPersonalData data) {
		ExtentListener.createNode("Personal Information");
		try {
			BasicInfo_PersonalInformation personalInfo = new BasicInfo_PersonalInformation(getDriver());
			personalInfo.fillPersonalInformationForm(data.getFirstName(), data.getLastName(), data.getCityName(),
					data.getMaritalStatus(), data.getCountry());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Personal Information completed");
			stepStatus.put("Personal Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Personal Information failed: " + e.getMessage());
			stepStatus.put("Personal Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "personalInformation", alwaysRun = true)
	public void biometricsInformation() {
		ExtentListener.createNode("Biometrics Information");
		try {
			BasicInfo_Biometrics biometrics = new BasicInfo_Biometrics(getDriver());
			biometrics.fillBiometricsInfo(TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Biometrics Information completed");
			stepStatus.put("Biometrics Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Biometrics Information failed: " + e.getMessage());
			stepStatus.put("Biometrics Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "biometricsInformation", dataProvider = "StudentFamilyData", dataProviderClass = FamilyData.class, alwaysRun = true)
	public void familyInformation(StudentFamilyData data) {
		ExtentListener.createNode("Family Information");
		try {
			BasicInfo_FamilyInformation familyInfo = new BasicInfo_FamilyInformation(getDriver());
			familyInfo.fillFamilyInformation(data.getRelation(), data.getName(), data.getDob(), data.getOccupation(),
					data.getCountryCode(), data.getPhone(), data.getDependent(), data.getCountry(), data.getState(),
					data.getCity(), data.getNationality());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content

			ExtentListener.getNode().pass("Family Information completed");
			stepStatus.put("Family Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Family Information failed: " + e.getMessage());
			stepStatus.put("Family Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "familyInformation", dataProvider = "StudentLanguageData", dataProviderClass = LanguageData.class, alwaysRun = true)
	public void languageInformation(StudentLanguageData data) {
		ExtentListener.createNode("Language Information");
		try {
			BasicInfo_LanguageInformation languageInfo = new BasicInfo_LanguageInformation(getDriver());
			languageInfo.fillLanguageInformationForm(data.getLanguage(), data.getLevel());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Language Information completed");
			stepStatus.put("Language Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Language Information failed: " + e.getMessage());
			stepStatus.put("Language Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "languageInformation", dataProvider = "StudentPreRightData", dataProviderClass = PreRightData.class, alwaysRun = true)
	public void preRightsInformation(StudentPreRightData data) {
		ExtentListener.createNode("Pre Rights Information");
		try {
			BasicInfo_GeneralInformation_Prerights prerightsInfo = new BasicInfo_GeneralInformation_Prerights(
					getDriver());
//			prerightsInfo.fillPreferRightsInformation("Үздік", TestDataGenerator.randomEmployeePhotoFile());

			prerightsInfo.fillPreferRightsInformation(data.getPreferRights(),
					TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Pre Rights Information completed");
			stepStatus.put("Pre Rights Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Pre Rights Information failed: " + e.getMessage());
			stepStatus.put("Pre Rights Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "preRightsInformation", dataProvider = "StudentSocialStatusData", dataProviderClass = SocialStatusData.class, alwaysRun = true)
	public void socialStatusInformation(StudentSocialStatusData data) {
		ExtentListener.createNode("Social Status Information");
		try {
			BasicInfo_GeneralInformation_SocialStatus socialInfo = new BasicInfo_GeneralInformation_SocialStatus(
					getDriver());
//			socialInfo.fillSocialStatusForm("Қандас", TestDataGenerator.randomEmployeePhotoFile());
			socialInfo.fillSocialStatusForm(data.getSocialStatus(), TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Social Status Information completed");
			stepStatus.put("Social Status Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Social Status Information failed: " + e.getMessage());
			stepStatus.put("Social Status Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "socialStatusInformation", alwaysRun = true)
	public void workLocationInformation() {
		ExtentListener.createNode("Work Location Information");
		try {
			BasicInfo_GeneralInformation_SocialWorkLocation socialWorkInfo = new BasicInfo_GeneralInformation_SocialWorkLocation(
					getDriver());
			socialWorkInfo.fillSocialWorkLocationDetails(TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Work Location Information completed");
			stepStatus.put("Work Location Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Work Location Information failed: " + e.getMessage());
			stepStatus.put("Work Location Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "workLocationInformation", dataProvider = "StudentMedicalVaccinationData", dataProviderClass = MedicalVaccination.class, alwaysRun = true)
	public void medicalVaccinationInformation(StudentMedicalVaccinationData data) {
		ExtentListener.createNode("Medical Vaccination Information");
		try {
			BasicInfo_MedicalInformation_Vaccination medicalInfo = new BasicInfo_MedicalInformation_Vaccination(
					getDriver());

			medicalInfo.fillVaccinationInfo(data.getVaccineName(), data.getDose(), data.getVaccinationDate(),
					data.getBatchNo(), TestDataGenerator.randomNotes(), TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Medical Vaccination Information completed");
			stepStatus.put("Medical Vaccination Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Medical Vaccination Information failed: " + e.getMessage());
			stepStatus.put("Medical Vaccination Information", "FAIL");

		}
	}

	@Test(groups = "Regression",dependsOnMethods = "medicalVaccinationInformation", dataProvider = "StudentMedicalAtPolyData", dataProviderClass = MedicalAtPolyData.class, alwaysRun = true)
	public void medicalAtPolyInformation(StudentMedicalAtPolyData data) {
		ExtentListener.createNode("Medical At Poly Information");
		try {
			BasicInfo_MedicalInforamtion_AtPoly medicalPolyInfo = new BasicInfo_MedicalInforamtion_AtPoly(getDriver());

			medicalPolyInfo.fillAtPolyMedicalInformation(data.getVisitDate(), data.getPolyType(),
					TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Medical At Poly Information completed");
			stepStatus.put("Medical At Poly Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Medical At Poly Information failed: " + e.getMessage());
			stepStatus.put("Medical At Poly Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "medicalAtPolyInformation", dataProvider = "StudentMedicalInsuranceData", dataProviderClass = MedicalInsuranceData.class, alwaysRun = true)
	public void medicalInsuranceInformation(StudentMedicalInsuranceData data) {
		ExtentListener.createNode("Medical Insurance Information");
		try {
			BasicInfo_MedicalInformation_Insurance medicalInsuranceInfo = new BasicInfo_MedicalInformation_Insurance(
					getDriver());

			/*
			 * medicalInsuranceInfo.fillInsuranceInformation("01012026", "01012027",
			 * TestDataGenerator.randomEmployeePhotoFile());
			 */

			medicalInsuranceInfo.fillInsuranceInformation(data.getInsuranceStartDate(), data.getInsuranceEndDate(),
					TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Medical Insurance Information completed");
			stepStatus.put("Medical Insurance Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Medical Insurance Information failed: " + e.getMessage());
			stepStatus.put("Medical Insurance Information", "FAIL");

		}
	}

	@Test(groups = "Regression",dependsOnMethods = "medicalInsuranceInformation", dataProvider = "StudentMedicalDisabilityData", dataProviderClass = MedicalDisabilityData.class, alwaysRun = true)
	public void medicalDisabilityInformation(StudentMedicalDisabilityData data) {
		ExtentListener.createNode("Medical Disability Information");
		try {
			BasicInfo_MedicalInformation_Disability medicalDisabilityInfo = new BasicInfo_MedicalInformation_Disability(
					getDriver());

			medicalDisabilityInfo.fillDisabilityForm(data.getDisabilityType(), data.getDisabilityDocumentNo(),
					data.getDisabilityDate(), TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing
																		// screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Medical Disability Information completed");
			stepStatus.put("Medical Disability Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Medical Disability Information failed: " + e.getMessage());
			stepStatus.put("Medical Disability Information", "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeEmployeeFlow() {
		System.out.println("==== Basic Flow for Student ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
