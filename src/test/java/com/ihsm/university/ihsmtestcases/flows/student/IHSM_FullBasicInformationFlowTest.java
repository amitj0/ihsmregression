package com.ihsm.university.ihsmtestcases.flows.student;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.base.FlowStep;
import com.ihsm.university.common.LoginPage;
import com.ihsm.university.ihsmpageobjects.student.basicinformation.*;
import com.ihsm.university.utilities.FlowStateUtils;

public class IHSM_FullBasicInformationFlowTest extends BaseClass {

	 private WebDriver driver;

	    public IHSM_FullBasicInformationFlowTest(WebDriver driver) {
	        this.driver = driver;
	    }
	@Test
	public void verifyFullBasicInformationFlow() throws Exception {

		logger.info("===== STARTING FULL BASIC INFORMATION FLOW =====");

		FlowStep lastStep = FlowStateUtils.getLastCompletedStep();

		if (shouldRun(lastStep, FlowStep.STU_ENROLLMENT)) {
			fillEnrollmentInfo();
			FlowStateUtils.saveStep(FlowStep.STU_ENROLLMENT);
			lastStep = FlowStep.STU_ENROLLMENT;

		}

		if (shouldRun(lastStep, FlowStep.STU_PERSONAL)) {
			fillPersonalInfo();
			FlowStateUtils.saveStep(FlowStep.STU_PERSONAL);
			lastStep = FlowStep.STU_PERSONAL;

		}

		if (shouldRun(lastStep, FlowStep.STU_BIOMETRICS)) {
			fillBiometricsInfo();
			FlowStateUtils.saveStep(FlowStep.STU_BIOMETRICS);
			lastStep = FlowStep.STU_BIOMETRICS;

		}

		if (shouldRun(lastStep, FlowStep.STU_FAMILY)) {
			fillFamilyInfo();
			FlowStateUtils.saveStep(FlowStep.STU_FAMILY);
			lastStep = FlowStep.STU_FAMILY;

		}

		if (shouldRun(lastStep, FlowStep.STU_LANGUAGE)) {
			fillLanguageInfo();
			FlowStateUtils.saveStep(FlowStep.STU_LANGUAGE);
			lastStep = FlowStep.STU_LANGUAGE;

		}

		if (shouldRun(lastStep, FlowStep.STU_PRERIGHTS)) {
			fillPreRightsInfo();
			FlowStateUtils.saveStep(FlowStep.STU_PRERIGHTS);
			lastStep = FlowStep.STU_PRERIGHTS;

		}

		if (shouldRun(lastStep, FlowStep.STU_SOCIAL_STATUS)) {
			fillSocialStatusInfo();
			FlowStateUtils.saveStep(FlowStep.STU_SOCIAL_STATUS);
			lastStep = FlowStep.STU_SOCIAL_STATUS;

		}

		if (shouldRun(lastStep, FlowStep.STU_WORK_LOCATION)) {
			fillWorkLocationInfo();
			FlowStateUtils.saveStep(FlowStep.STU_WORK_LOCATION);
			lastStep = FlowStep.STU_WORK_LOCATION;

		}

		if (shouldRun(lastStep, FlowStep.STU_MEDICAL)) {
			fillMedicalInfo();
			FlowStateUtils.saveStep(FlowStep.STU_MEDICAL);
			lastStep = FlowStep.STU_MEDICAL;

		}

		logger.info("===== FULL BASIC INFORMATION FLOW COMPLETED SUCCESSFULLY =====");
	}

	/* ================= FLOW CONTROL ================= */

	private boolean shouldRun(FlowStep last, FlowStep current) {
		return last == null || last.ordinal() < current.ordinal();
	}

	/* ================= STEP METHODS ================= */

	private void fillEnrollmentInfo() throws InterruptedException {

		new BasicInfo_EnrollnmentInformation(getDriver()).fillEnrollmentInformation(
				StudentFullRegistrationDataVariables.term, StudentFullRegistrationDataVariables.course,
				StudentFullRegistrationDataVariables.year, StudentFullRegistrationDataVariables.semester,
				StudentFullRegistrationDataVariables.pin, StudentFullRegistrationDataVariables.firstName,
				StudentFullRegistrationDataVariables.middleName, StudentFullRegistrationDataVariables.lastName,
				StudentFullRegistrationDataVariables.gender, StudentFullRegistrationDataVariables.dob,
				StudentFullRegistrationDataVariables.country, StudentFullRegistrationDataVariables.state,
				StudentFullRegistrationDataVariables.mobile, StudentFullRegistrationDataVariables.email,
				StudentFullRegistrationDataVariables.nationality);
	}

	private void fillPersonalInfo() {

		new BasicInfo_PersonalInformation(getDriver()).fillPersonalInformationForm(
				StudentFullRegistrationDataVariables.firstName2, StudentFullRegistrationDataVariables.lastName2,
				StudentFullRegistrationDataVariables.city, StudentFullRegistrationDataVariables.maritalStatus,
				StudentFullRegistrationDataVariables.country2);
	}

	private void fillBiometricsInfo() {

		new BasicInfo_Biometrics(getDriver())
				.fillBiometricsInfo(getTestDataPath(StudentFullRegistrationDataVariables.biometricsImage));
	}

	private void fillFamilyInfo() {

		new BasicInfo_FamilyInformation(getDriver()).fillFamilyInformation(
				StudentFullRegistrationDataVariables.relation, StudentFullRegistrationDataVariables.familyName,
				StudentFullRegistrationDataVariables.familyDob, StudentFullRegistrationDataVariables.occupation,
				StudentFullRegistrationDataVariables.countryCode, StudentFullRegistrationDataVariables.phone,
				StudentFullRegistrationDataVariables.dependent, StudentFullRegistrationDataVariables.famCountry,
				StudentFullRegistrationDataVariables.famState, StudentFullRegistrationDataVariables.famCity,
				StudentFullRegistrationDataVariables.famNationality);
	}

	private void fillLanguageInfo() {

		new BasicInfo_LanguageInformation(getDriver()).fillLanguageInformationForm(
				StudentFullRegistrationDataVariables.language, StudentFullRegistrationDataVariables.languageLevel);
	}

	private void fillPreRightsInfo() {

		new BasicInfo_GeneralInformation_Prerights(getDriver()).fillPreferRightsInformation(
				StudentFullRegistrationDataVariables.preRights,
				getTestDataPath(StudentFullRegistrationDataVariables.preRightsImage));
	}

	private void fillSocialStatusInfo() {

		new BasicInfo_GeneralInformation_SocialStatus(getDriver()).fillSocialStatusForm(
				StudentFullRegistrationDataVariables.socialStatus,
				getTestDataPath(StudentFullRegistrationDataVariables.socialStatusImage));
	}

	private void fillWorkLocationInfo() {

		new BasicInfo_GeneralInformation_SocialWorkLocation(getDriver())
				.fillSocialWorkLocationDetails(getTestDataPath(StudentFullRegistrationDataVariables.workLocationImage));
	}

	private void fillMedicalInfo() {

		new BasicInfo_MedicalInformation_Vaccination(getDriver()).fillVaccinationInfo(
				StudentFullRegistrationDataVariables.vacDose, StudentFullRegistrationDataVariables.vacNumber,
				StudentFullRegistrationDataVariables.vacDate, StudentFullRegistrationDataVariables.vacCode,
				StudentFullRegistrationDataVariables.vacRemarks,
				getTestDataPath(StudentFullRegistrationDataVariables.vacImage));

		new BasicInfo_MedicalInforamtion_AtPoly(getDriver()).fillAtPolyMedicalInformation(
				StudentFullRegistrationDataVariables.polyDate, StudentFullRegistrationDataVariables.polyType,
				getTestDataPath(StudentFullRegistrationDataVariables.polyImage));

		new BasicInfo_MedicalInformation_Insurance(getDriver()).fillInsuranceInformation(
				StudentFullRegistrationDataVariables.insStartDate, StudentFullRegistrationDataVariables.insEnd,
				getTestDataPath(StudentFullRegistrationDataVariables.insImage));

		new BasicInfo_MedicalInformation_Disability(getDriver()).fillDisabilityForm(
				StudentFullRegistrationDataVariables.disType, StudentFullRegistrationDataVariables.disCode,
				StudentFullRegistrationDataVariables.disDate,
				getTestDataPath(StudentFullRegistrationDataVariables.disImage));
	}
}
