package com.ihsm.university.ihsmtestcases.flows.student;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
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

	// ── Basic Information ──────────────────────────────
	private BasicInfo_EnrollnmentInformation enrollInfo;
	private BasicInfo_PersonalInformation personalInfo;
	private BasicInfo_Biometrics biometrics;
	private BasicInfo_FamilyInformation familyInfo;
	private BasicInfo_LanguageInformation languageInfo;

	// ── General Information ────────────────────────────
	private BasicInfo_GeneralInformation_Prerights preRightsInfo;
	private BasicInfo_GeneralInformation_SocialStatus socialInfo;
	private BasicInfo_GeneralInformation_SocialWorkLocation socialWorkInfo;

	// ── Medical Information ────────────────────────────
	private BasicInfo_MedicalInformation_Vaccination medicalVacInfo;
	private BasicInfo_MedicalInforamtion_AtPoly medicalPolyInfo;
	private BasicInfo_MedicalInformation_Insurance medicalInsInfo;
	private BasicInfo_MedicalInformation_Disability medicalDisInfo;

	// ── Student ID ─────────────────────────────────────
	private String studentEnrollmentId;

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

	@Test(priority = 0, groups = "Regression", dataProvider = "StudentEnrollnmentData", dataProviderClass = EnrollnmentData.class, description = "Verify Student Enrollment Information Test")
	public void enrollmentInformation(StudentEnrollnmentData data) {

		ExtentTest node = ExtentListener.createNode("Enrollment Information");

		try {
			node.info("Starting Student Enrollment Information");
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
					node.pass("Student already exists. Opened using ID: " + existingId);
					return;
				} else {
					throw new RuntimeException("No existing student ID found for EXISTING mode!");
				}
			}

			// Otherwise create NEW student
			node.info("Creating student enrollment");
			node.info("Term: " + data.getTerm());
			node.info("Program: " + data.getProgram());
			node.info("Year: " + data.getYear());
			node.info("Semester: " + data.getSemester());
			node.info("PIN: " + data.getPin());
			node.info("First Name: " + data.getFirstName());
			node.info("Middle Name: " + data.getMiddleName());
			node.info("Last Name: " + data.getLastName());
			node.info("Gender: " + data.getGender());
			node.info("DOB: " + data.getDob());
			node.info("Country: " + data.getCountry());
			node.info("State: " + data.getState());
			node.info("Phone: " + data.getPhone());
			node.info("Email: " + data.getEmail());
			node.info("Nationality: " + data.getNationality());

			enrollInfo = new BasicInfo_EnrollnmentInformation(getDriver());

			// Fill enrollment form
			personalInfo = enrollInfo.fillEnrollmentInformation(data.getTerm(), data.getProgram(),
					String.valueOf(data.getYear()), String.valueOf(data.getSemester()), data.getPin(),
					data.getFirstName(), data.getMiddleName(), data.getLastName(), data.getGender(), data.getDob(),
					data.getCountry(), data.getState(), data.getPhone(), data.getEmail(), data.getNationality());

			String studentEnrollmentId = enrollInfo.getStudentEnrollmentId();
			if (studentEnrollmentId == null || studentEnrollmentId.isEmpty()) {
				throw new RuntimeException("Student Enrollment ID not generated!");
			}

			// Save the new student ID
			saveStudentId(studentEnrollmentId);

			node.pass("New student enrollment created: " + studentEnrollmentId);
			stepStatus.put("Enrollment Information", "PASS");

		} catch (Exception e) {
			e.printStackTrace();
			node.fail("Enrollment failed: " + e.getMessage());
			stepStatus.put("Enrollment Information", "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "enrollmentInformation", dataProvider = "StudentPersonalData", dataProviderClass = PersonalData.class, description = "Verify Student Personal Information Test")
	public void personalInformation(StudentPersonalData data) {
		ExtentTest node = ExtentListener.createNode("Personal Information");

		boolean isSuccess = false;
		try {
			node.info("Entering Student Personal Information");
			node.info("First Name: " + data.getFirstName());
			node.info("Last Name: " + data.getLastName());
			node.info("City: " + data.getCityName());
			node.info("Marital Status: " + data.getMaritalStatus());
			node.info("Country: " + data.getCountry());

			biometrics = personalInfo.fillPersonalInformationForm(data.getFirstName(), data.getLastName(),
					data.getCityName(), data.getMaritalStatus(), data.getCountry());

			isSuccess = true;
			node.pass("Personal Information completed");

		} catch (Exception e) {
			node.fail("Personal Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Personal Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "personalInformation", description = "Verify Student Biometrics Information Test")
	public void biometricsInformation() {
		ExtentTest node = ExtentListener.createNode("Biometrics Information");
		boolean isSuccess = false;
		try {

			node.info("Entering Student Biometrics Information");

			familyInfo = biometrics.fillBiometricsInfo(getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Biometrics Information completed");

		} catch (Exception e) {
			node.fail("Biometrics Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Biometrics Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "biometricsInformation", dataProvider = "StudentFamilyData", dataProviderClass = FamilyData.class, description = "Verify Student Family Information Test")
	public void familyInformation(StudentFamilyData data) {
		ExtentTest node = ExtentListener.createNode("Family Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Family Information");
			node.info("Relation: " + data.getRelation());
			node.info("Name: " + data.getName());
			node.info("DOB: " + data.getDob());
			node.info("Occupation: " + data.getOccupation());
			node.info("Country Code: " + data.getCountryCode());
			node.info("Phone: " + data.getPhone());
			node.info("Dependent: " + data.getDependent());
			node.info("Country: " + data.getCountry());
			node.info("State: " + data.getState());
			node.info("City: " + data.getCity());
			node.info("Nationality: " + data.getNationality());

			languageInfo = familyInfo.fillFamilyInformation(data.getRelation(), data.getName(), data.getDob(),
					data.getOccupation(), data.getCountryCode(), data.getPhone(), data.getDependent(),
					data.getCountry(), data.getState(), data.getCity(), data.getNationality());
			isSuccess = true;
			node.pass("Family Information completed");

		} catch (Exception e) {
			node.fail("Family Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Family Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "familyInformation", dataProvider = "StudentLanguageData", dataProviderClass = LanguageData.class, description = "Verify Student Language Information Test")
	public void languageInformation(StudentLanguageData data) {
		ExtentTest node = ExtentListener.createNode("Language Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Language Information");
			node.info("Language: " + data.getLanguage());
			node.info("Proficiency Level: " + data.getLevel());

			preRightsInfo = languageInfo.fillLanguageInformationForm(data.getLanguage(), data.getLevel());

			isSuccess = true;
			node.pass("Language Information completed");
		} catch (Exception e) {
			node.fail("Language Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Language Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "languageInformation", dataProvider = "StudentPreRightData", dataProviderClass = PreRightData.class, description = "Verify Student Pre Rights Information Test")
	public void preRightsInformation(StudentPreRightData data) {
		ExtentTest node = ExtentListener.createNode("Pre Rights Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Pre Rights Information");
			node.info("Prefer Rights: " + data.getPreferRights());

			socialInfo = preRightsInfo.fillPreferRightsInformation(data.getPreferRights(), getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Pre Rights Information completed");

		} catch (Exception e) {
			node.fail("Pre Rights Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Pre Rights Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "preRightsInformation", dataProvider = "StudentSocialStatusData", dataProviderClass = SocialStatusData.class, description = "Verify Student Social Status Information Test")
	public void socialStatusInformation(StudentSocialStatusData data) {
		ExtentTest node = ExtentListener.createNode("Social Status Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Social Status Information");
			node.info("Social Status: " + data.getSocialStatus());

			socialWorkInfo = socialInfo.fillSocialStatusForm(data.getSocialStatus(), getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Social Status Information completed");

		} catch (Exception e) {
			node.fail("Social Status Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Social Status Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "socialStatusInformation", description = "Verify Student Work Location Information Test")
	public void workLocationInformation() {
		ExtentTest node = ExtentListener.createNode("Work Location Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Work Location Information");

			medicalVacInfo = socialWorkInfo.fillSocialWorkLocationDetails(getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Work Location Information completed");

		} catch (Exception e) {
			node.fail("Work Location Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Work Location Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "workLocationInformation", dataProvider = "StudentMedicalVaccinationData", dataProviderClass = MedicalVaccination.class, description = "Verify Student Medical Vaccination Information Test")
	public void medicalVaccinationInformation(StudentMedicalVaccinationData data) {
		ExtentTest node = ExtentListener.createNode("Medical Vaccination Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Medical Vaccination Information");
			node.info("Vaccine Name: " + data.getVaccineName());
			node.info("Dose: " + data.getDose());
			node.info("Vaccination Date: " + data.getVaccinationDate());
			node.info("Batch No: " + data.getBatchNo());

			medicalPolyInfo = medicalVacInfo.fillVaccinationInfo(data.getVaccineName(), data.getDose(),
					data.getVaccinationDate(), data.getBatchNo(), TestDataGenerator.randomNotes(),
					getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Medical Vaccination Information completed");

		} catch (Exception e) {
			node.fail("Medical Vaccination Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Medical Vaccination Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "medicalVaccinationInformation", dataProvider = "StudentMedicalAtPolyData", dataProviderClass = MedicalAtPolyData.class, description = "Verify Student Medical At Poly Information Test")
	public void medicalAtPolyInformation(StudentMedicalAtPolyData data) {
		ExtentTest node = ExtentListener.createNode("Medical At Poly Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Medical At Poly Information");
			node.info("Visit Date: " + data.getVisitDate());
			node.info("Poly Type: " + data.getPolyType());

			medicalInsInfo = medicalPolyInfo.fillAtPolyMedicalInformation(data.getVisitDate(), data.getPolyType(),
					getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Medical At Poly Information completed");

		} catch (Exception e) {
			node.fail("Medical At Poly Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Medical At Poly Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "medicalAtPolyInformation", dataProvider = "StudentMedicalInsuranceData", dataProviderClass = MedicalInsuranceData.class, description = "Verify Student Medical Insurance Information Test")
	public void medicalInsuranceInformation(StudentMedicalInsuranceData data) {
		ExtentTest node = ExtentListener.createNode("Medical Insurance Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Medical Insurance Information");
			node.info("Insurance Start Date: " + data.getInsuranceStartDate());
			node.info("Insurance End Date: " + data.getInsuranceEndDate());

			medicalDisInfo = medicalInsInfo.fillInsuranceInformation(data.getInsuranceStartDate(),
					data.getInsuranceEndDate(), getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Medical Insurance Information completed");

		} catch (Exception e) {
			node.fail("Medical Insurance Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Medical Insurance Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "medicalInsuranceInformation", dataProvider = "StudentMedicalDisabilityData", dataProviderClass = MedicalDisabilityData.class, description = "Verify Student Medical Disability Information Test")
	public void medicalDisabilityInformation(StudentMedicalDisabilityData data) {
		ExtentTest node = ExtentListener.createNode("Medical Disability Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Student Medical Disability Information");
			node.info("Disability Type: " + data.getDisabilityType());
			node.info("Disability Document No: " + data.getDisabilityDocumentNo());
			node.info("Disability Date: " + data.getDisabilityDate());

			medicalDisInfo.fillDisabilityForm(data.getDisabilityType(), data.getDisabilityDocumentNo(),
					data.getDisabilityDate(), getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Medical Disability Information completed");

		} catch (Exception e) {
			node.fail("Medical Disability Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Medical Disability Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeEmployeeFlow() {
		System.out.println("\n==== Basic Flow for Student ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================\n");
	}
}
