package com.ihsm.university.ihsmtestcases.flows.employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_BiometricsInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_EnrollnmentInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_GuardianInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_LanguageInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_PersonalInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_VaccinationInformation;
import com.ihsm.university.navigation.Employee_Search;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_BasicFullFlowTest extends BaseClass {

	private static final String EMPLOYEE_FILE = System.getProperty("user.dir") + "/target/employee.data";

	// SAVE EMPLOYEE ID
	private void saveEmployeeId(String id) throws Exception {

		File file = new File(EMPLOYEE_FILE);
		file.getParentFile().mkdirs();

		java.nio.file.Files.write(file.toPath(), id.getBytes());
	}

	// LOAD EMPLOYEE ID
	private String loadEmployeeId() throws Exception {

		File file = new File(EMPLOYEE_FILE);

		if (!file.exists()) {
			return null;
		}

		return new String(java.nio.file.Files.readAllBytes(file.toPath())).trim();
	}

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	@Test(groups = "Regression", description = "Verify Employee Enrollnment Information Test")
	public void enrollmentInformation() {

		ExtentTest node = ExtentListener.createNode("Enrollment Information");

		try {
			node.info("Starting Employee Enrollment Information");
			// Read action from properties (NEW or EXISTING)
			String action = prop.getProperty("employee.action", "NEW").toUpperCase();

			// Load existing employee ID if any
			String existingId = loadEmployeeId();

			if ("EXISTING".equals(action)) {
				// Use existing employee if available
				if (existingId != null && !existingId.isEmpty()) {
					String numericId = existingId.replaceAll("[^0-9]", "");

					Employee_Search search = new Employee_Search(getDriver());
					search.fillEmployeeSearchInfo(numericId);

					node.pass("Employee already exists. Opened using ID: " + existingId);
					stepStatus.put("Enrollment Information", "SKIPPED");
				} else {
					throw new RuntimeException("No existing employee ID found for EXISTING mode!");
				}

			} else {
				node.info("Creating Employee Enrollment information");
				node.info("First Name: " + TestDataGenerator.generateRandomRussianFirstName());
				node.info("Last Name: " + TestDataGenerator.generateRandomFirstName());
				node.info("Gender: " + TestDataGenerator.generateRandomGender());
				node.info("Phone Number: " + TestDataGenerator.randomNumber(5));
				node.info("Email: " + TestDataGenerator.randomEmail());
				node.info("Country: " + "Казахстан");
				// Create new employee
				BasicInfo_EnrollnmentInformation enrollInfo = new BasicInfo_EnrollnmentInformation(getDriver());

				enrollInfo.fillEnrollnmentInformationForm(TestDataGenerator.generateRandomRussianFirstName(),
						TestDataGenerator.generateRandomFirstName(), TestDataGenerator.generateRandomGender(),
						TestDataGenerator.randomNumber(5), TestDataGenerator.randomEmail(), "Казахстан");

				String employeeId = enrollInfo.getEmployeeId();
				if (employeeId == null || employeeId.isEmpty()) {
					throw new RuntimeException("Employee ID not generated!");
				}

				// Save the new employee ID
				saveEmployeeId(employeeId);

				node.pass("Employee Enrollment completed. Employee ID: " + employeeId);
				stepStatus.put("Enrollment Information", "PASS");
			}

		} catch (Exception e) {
			node.fail("Enrollment Information failed: " + e.getMessage());
			stepStatus.put("Enrollment Information", "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "enrollmentInformation", alwaysRun = true, description = "Verify Employee Personal Information Test")
	public void personalInformation() {
		ExtentTest node = ExtentListener.createNode("Personal Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Employee Personal Details");
			node.info("First Name: " + TestDataGenerator.randomString(4));
			node.info("Last Name: " + TestDataGenerator.randomString(3));
			node.info("Passport Number: " + TestDataGenerator.randomNumber(4));
			node.info("National ID: " + TestDataGenerator.randomNumber(5));
			node.info("Date of Birth: " + "01-01-2000");
			node.info("Gender: " + TestDataGenerator.generateRandomGender());
			node.info("Marital Status: " + "Разведен(а) официально (развод зарегистрирован)");
			node.info("Date of Joining: " + "01-01-2026");
			node.info("Country Code: " + "91");
			node.info("Phone Number: " + TestDataGenerator.randomPhone());
			node.info("Address 1: " + TestDataGenerator.randomIndianAddress());
			node.info("Address 2: " + TestDataGenerator.randomIndianAddress());
			
			BasicInfo_PersonalInformation personalInfo = new BasicInfo_PersonalInformation(getDriver());
			personalInfo.fillPersonalInformationForm(TestDataGenerator.randomString(4),
					TestDataGenerator.randomString(3), TestDataGenerator.randomNumber(4),
					TestDataGenerator.randomNumber(5), "01012000", TestDataGenerator.generateRandomGender(),
					"Разведен(а) официально (развод зарегистрирован)", "01012026", "91",
					TestDataGenerator.randomPhone(), TestDataGenerator.randomIndianAddress(),
					TestDataGenerator.randomIndianAddress());
			isSuccess = true;
			node.pass("Employee Personal Information completed");

		} catch (Exception e) {
			node.fail("Personal Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Personal Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "personalInformation", alwaysRun = true, description = "Verify Employee Guardian Information Test")
	public void guardianInformation() {
		ExtentTest node = ExtentListener.createNode("Guardian Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Employee Guardian Details");
			node.info("Guardian Type: " + "Father");
			node.info("Guardian Name: " + TestDataGenerator.randomGuardianName());
			node.info("Guardian DOB: " + "01-01-1970");
			node.info("Is Guardian Employee: " + "No");
			BasicInfo_GuardianInformation guardianInfo = new BasicInfo_GuardianInformation(getDriver());
			guardianInfo.fillGuardianInformationForm("Father", TestDataGenerator.randomGuardianName(), "01011970",
					"No");
			isSuccess = true;
			node.pass("Employee Guardian Information completed");

		} catch (Exception e) {
			node.fail("Guardian Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Guardian Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "guardianInformation", alwaysRun = true, description = "Verify Employee Language Information Test")
	public void languageInformation() {
		ExtentTest node = ExtentListener.createNode("Language Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Employee Language Details");
			node.info("Language: " + "сертификат Duolingo");
			node.info("Proficiency: " + "B2");

			BasicInfo_LanguageInformation languageInfo = new BasicInfo_LanguageInformation(getDriver());
			languageInfo.fillLanguageInformation("сертификат Duolingo", "B2");
			isSuccess = true;
			node.pass("Employee Language Information completed");
		} catch (Exception e) {
			node.fail("Language Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Language Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "languageInformation", alwaysRun = true, description = "Verify Employee Vaccination Information Test")
	public void vaccinationInformation() {
		ExtentTest node = ExtentListener.createNode("Vaccination Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Employee Vaccination Details");
			node.info("Vaccine Name: " + "AstraZeneca");
			node.info("Dose Number: " + "2");
			node.info("Batch Number: " + TestDataGenerator.randomNumber(5));
			node.info("Vaccination Date: " + "01-01-2026");
			node.info("Notes: " + TestDataGenerator.randomNotes());

			BasicInfo_VaccinationInformation vaccinationInfo = new BasicInfo_VaccinationInformation(getDriver());
			vaccinationInfo.fillVaccinationForm("AstraZeneca", "2", TestDataGenerator.randomNumber(5), "01012026",
					TestDataGenerator.randomNotes());
			isSuccess = true;
			node.pass("Employee Vaccination Information completed");
		} catch (Exception e) {
			node.fail("Vaccination Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Vaccination Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "vaccinationInformation", alwaysRun = true, description = "Verify Employee Biometrics Information Test")
	public void biometricsInformation() {
		ExtentTest node = ExtentListener.createNode("Biometrics Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Employee Biometrics Information");
			
//			node.info("Photo File: " + TestDataGenerator.randomEmployeePhotoFile());
			
			BasicInfo_BiometricsInformation biometricsInfo = new BasicInfo_BiometricsInformation(getDriver());
			biometricsInfo.fillBiometricsInfo(getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Employee Biometrics Information completed");

		} catch (Exception e) {
			node.fail("Biometrics Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Biometrics Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeEmployeeFlow() {
		System.out.println("==== Basic Flow Status for Employee ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
