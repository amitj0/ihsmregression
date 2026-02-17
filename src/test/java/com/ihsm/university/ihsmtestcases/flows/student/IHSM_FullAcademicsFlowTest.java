package com.ihsm.university.ihsmtestcases.flows.student;

import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.base.FlowStep;
import com.ihsm.university.ihsmpageobjects.student.academics.*;
import com.ihsm.university.utilities.FlowStateUtils;

public class IHSM_FullAcademicsFlowTest extends BaseClass {

	@Test
	public void verifyFullAcademicsInformation() throws Exception {

		logger.info("===== STARTING FULL ACADEMICS FLOW =====");

		FlowStep lastStep = FlowStateUtils.getLastCompletedStep();

		if (shouldRun(lastStep, FlowStep.LAST_EDUCATION)) {
			fillLastEducation();
			FlowStateUtils.saveStep(FlowStep.LAST_EDUCATION);
			lastStep = FlowStep.LAST_EDUCATION;

		}

		if (shouldRun(lastStep, FlowStep.DIPLOMA)) {
			fillDiploma();
			FlowStateUtils.saveStep(FlowStep.DIPLOMA);
			lastStep = FlowStep.DIPLOMA;

		}

		if (shouldRun(lastStep, FlowStep.QUALIFICATION)) {
			fillQualification();
			FlowStateUtils.saveStep(FlowStep.QUALIFICATION);
			lastStep = FlowStep.QUALIFICATION;

		}

		logger.info("===== FULL ACADEMICS FLOW COMPLETED SUCCESSFULLY =====");
	}

	/* ================= FLOW CONTROL ================= */
	private boolean shouldRun(FlowStep last, FlowStep current) {
		return last == null || last.ordinal() < current.ordinal();
	}

	/* ================= STEP METHODS ================= */
	private void fillLastEducation() throws Exception {
		logger.info("Filling Last Education Information...");

		new Academics_Qualification_LastEducation(getDriver()).fillLastEducationInfo(
				StudentFullRegistrationDataVariables.lastEducation,
				StudentFullRegistrationDataVariables.lastEducationSchool,
				StudentFullRegistrationDataVariables.lastEducationStartDate,
				StudentFullRegistrationDataVariables.lastEducationEndDate,
				StudentFullRegistrationDataVariables.lastEducationGraduationDate,
				StudentFullRegistrationDataVariables.lastEducationMarks,
				StudentFullRegistrationDataVariables.lastEducationSubject,
				StudentFullRegistrationDataVariables.lastEducationPercentage,
				getTestDataPath(StudentFullRegistrationDataVariables.lastEducationImage));
	}

	private void fillDiploma() {
		logger.info("Filling Diploma Information...");

		new Academics_Qualification_Diploma(getDriver()).fillDiplomaDetails(
				StudentFullRegistrationDataVariables.diplomaCode, StudentFullRegistrationDataVariables.diplomaNumber,
				StudentFullRegistrationDataVariables.diplomaRegistration,
				StudentFullRegistrationDataVariables.diplomaStartDate,
				StudentFullRegistrationDataVariables.diplomaEndDate,
				StudentFullRegistrationDataVariables.diplomaInstitution,
				StudentFullRegistrationDataVariables.diplomaRemarks, StudentFullRegistrationDataVariables.diplomaType,
				StudentFullRegistrationDataVariables.diplomaMarks,
				StudentFullRegistrationDataVariables.diplomaGraduationDate,
				getTestDataPath(StudentFullRegistrationDataVariables.diplomaImage));
	}

	private void fillQualification() throws Exception {
		logger.info("Filling Qualification Information...");

		new Academics_Qualification_Qualification(getDriver()).fillQualificationInformation(
				StudentFullRegistrationDataVariables.qualificationType,
				StudentFullRegistrationDataVariables.qualificationInstitution,
				StudentFullRegistrationDataVariables.qualificationRegistrationNumber,
				StudentFullRegistrationDataVariables.qualificationStartDate,
				StudentFullRegistrationDataVariables.qualificationEndDate,
				StudentFullRegistrationDataVariables.qualificationCompletionDate,
				StudentFullRegistrationDataVariables.qualificationStatus,
				StudentFullRegistrationDataVariables.qualificationCountry,
				StudentFullRegistrationDataVariables.qualificationState,
				StudentFullRegistrationDataVariables.qualificationCity,
				getTestDataPath(StudentFullRegistrationDataVariables.qualificationImage));
	}
}
