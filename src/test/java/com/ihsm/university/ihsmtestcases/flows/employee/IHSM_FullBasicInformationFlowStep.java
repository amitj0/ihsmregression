package com.ihsm.university.ihsmtestcases.flows.employee;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.base.FlowStep;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_BiometricsInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_EnrollnmentInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_GuardianInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_LanguageInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_PersonalInformation;
import com.ihsm.university.ihsmpageobjects.employee.basicinformation.BasicInfo_VaccinationInformation;
import com.ihsm.university.utilities.FlowStateUtils;

public class IHSM_FullBasicInformationFlowStep extends BaseClass {

	@Test()
	public void verifyFullBasicInformation() throws Exception {

		logger.info("===== STARTING FULL BASIC INFORMATION FLOW =====");

		FlowStep lastStep = FlowStateUtils.getLastCompletedStep();

		if (shouldRun(lastStep, FlowStep.ENROLLMENT2)) {
			fillEnrollmentInfo();
			FlowStateUtils.saveStep(FlowStep.ENROLLMENT2);
			lastStep = FlowStep.ENROLLMENT2;

		}

		if (shouldRun(lastStep, FlowStep.PERSONAL2)) {
			fillPersonalInfo();
			FlowStateUtils.saveStep(FlowStep.PERSONAL2);
			lastStep = FlowStep.PERSONAL2;

		}

		if (shouldRun(lastStep, FlowStep.GUARDIAN)) {
			fillGuardianInfo();
			FlowStateUtils.saveStep(FlowStep.GUARDIAN);
			lastStep = FlowStep.GUARDIAN;

		}

		if (shouldRun(lastStep, FlowStep.LANGUAGE2)) {
			fillLanguageInfo();
			FlowStateUtils.saveStep(FlowStep.LANGUAGE2);
			lastStep = FlowStep.LANGUAGE2;

		}

		if (shouldRun(lastStep, FlowStep.VACCINATION)) {
			fillVaccinationInfo();
			FlowStateUtils.saveStep(FlowStep.VACCINATION);
			lastStep = FlowStep.VACCINATION;

		}

		if (shouldRun(lastStep, FlowStep.BIOMETRICS2)) {
			fillBiometricsInfo();
			FlowStateUtils.saveStep(FlowStep.BIOMETRICS2);
			lastStep = FlowStep.BIOMETRICS2;

		}

		logger.info("===== FULL BASIC INFORMATION FLOW COMPLETED SUCCESSFULLY =====");
	}

	/* ================== FLOW CONTROL ================== */

	private boolean shouldRun(FlowStep last, FlowStep current) {
		return last == null || last.ordinal() < current.ordinal();
	}

	/* ================== STEP METHODS ================== */

	private void fillEnrollmentInfo() {
		logger.info("Filling Employee Enrollment Information...");

		new BasicInfo_EnrollnmentInformation(getDriver()).fillEnrollnmentInformationForm(
				TestDataGenerator.generateRandomRussianFirstName(), TestDataGenerator.generateRandomFirstName(),
				TestDataGenerator.generateRandomGender(), TestDataGenerator.randomNumber(5),
				TestDataGenerator.randomEmail(), TestDataGenerator.randomCountry());
	}

	private void fillPersonalInfo() {
		logger.info("Filling Employee Personal Information...");

		new BasicInfo_PersonalInformation(getDriver()).fillPersonalInformationForm(TestDataGenerator.randomString(4),
				TestDataGenerator.randomString(3), TestDataGenerator.randomNumber(4), TestDataGenerator.randomNumber(5),
				"01012000", TestDataGenerator.generateRandomGender(), TestDataGenerator.randomMaritalStatus(),
				"01012026", "91", TestDataGenerator.randomPhone(), TestDataGenerator.randomIndianAddress(),
				TestDataGenerator.randomIndianAddress());
	}

	private void fillGuardianInfo() {
		logger.info("Filling Employee Guardian Information...");

		new BasicInfo_GuardianInformation(getDriver()).fillGuardianInformationForm(TestDataGenerator.randomGuardian(),
				TestDataGenerator.randomGuardianName(), "01011970", "No");
	}

	private void fillLanguageInfo() {
		logger.info("Filling Employee Language Information...");

		new BasicInfo_LanguageInformation(getDriver()).fillLanguageInformation("сертификат Duolingo", "B2");
	}

	private void fillVaccinationInfo() {
		logger.info("Filling Employee Vaccination Information...");

		new BasicInfo_VaccinationInformation(getDriver()).fillVaccinationForm(TestDataGenerator.randomVaccinationType(),
				TestDataGenerator.randomVaccinationPhase(), TestDataGenerator.randomNumber(5), "01012026",
				TestDataGenerator.randomNotes());
	}

	private void fillBiometricsInfo() throws Exception {
		logger.info("Filling Employee Biometrics Information...");

		new BasicInfo_BiometricsInformation(getDriver())
				.fillBiometricsInfo(TestDataGenerator.randomEmployeePhotoFile());
	}
}
