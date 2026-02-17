package com.ihsm.university.ihsmtestcases.flows.employee;

import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.base.FlowStep;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_DevResearch_Attestations;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_DevResearch_Patent;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_DevResearch_Rewards;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_DevResearch_SciResearch;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_Military;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_ProfessionalInfoAcademics;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_ProfessionalInfoDegreeLvl;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_ProfessionalInfoTitle;
import com.ihsm.university.utilities.FlowStateUtils;

public class IHSM_FullProfessionalInformationFlowStep extends BaseClass {

	@Test
	public void verifyFullProfessionalInformation() throws Exception {

		logger.info("===== STARTING FULL PROFESSIONAL INFORMATION FLOW =====");

		FlowStep lastStep = FlowStateUtils.getLastCompletedStep();

		if (shouldRun(lastStep, FlowStep.PROF_DEGREE)) {
			fillDegreeLevelInfo();
			FlowStateUtils.saveStep(FlowStep.PROF_DEGREE);
			lastStep = FlowStep.PROF_DEGREE;

		}

		if (shouldRun(lastStep, FlowStep.PROF_ACADEMIC)) {
			fillAcademicInfo();
			FlowStateUtils.saveStep(FlowStep.PROF_ACADEMIC);
			lastStep = FlowStep.PROF_ACADEMIC;

		}

		if (shouldRun(lastStep, FlowStep.PROF_TITLE)) {
			fillTitleInfo();
			FlowStateUtils.saveStep(FlowStep.PROF_TITLE);
			lastStep = FlowStep.PROF_TITLE;

		}

		if (shouldRun(lastStep, FlowStep.PROF_RESEARCH)) {
			fillScientificResearchInfo();
			FlowStateUtils.saveStep(FlowStep.PROF_RESEARCH);
			lastStep = FlowStep.PROF_RESEARCH;

		}

		if (shouldRun(lastStep, FlowStep.PROF_REWARD)) {
			fillRewardsInfo();
			FlowStateUtils.saveStep(FlowStep.PROF_REWARD);
			lastStep = FlowStep.PROF_REWARD;

		}

		if (shouldRun(lastStep, FlowStep.PROF_PATENT)) {
			fillPatentInfo();
			FlowStateUtils.saveStep(FlowStep.PROF_PATENT);
			lastStep = FlowStep.PROF_PATENT;

		}

		if (shouldRun(lastStep, FlowStep.PROF_ATTESTATION)) {
			fillAttestationInfo();
			FlowStateUtils.saveStep(FlowStep.PROF_ATTESTATION);
			lastStep = FlowStep.PROF_ATTESTATION;

		}

		if (shouldRun(lastStep, FlowStep.PROF_MILITARY)) {
			fillMilitaryInfo();
			FlowStateUtils.saveStep(FlowStep.PROF_MILITARY);
			lastStep = FlowStep.PROF_MILITARY;

		}

		logger.info("===== FULL PROFESSIONAL INFORMATION FLOW COMPLETED SUCCESSFULLY =====");
	}

	/* ================= FLOW CONTROL ================= */

	private boolean shouldRun(FlowStep last, FlowStep current) {
		return last == null || last.ordinal() < current.ordinal();
	}

	/* ================= STEP METHODS ================= */

	private void fillDegreeLevelInfo() {
		logger.info("Filling Professional Degree Level Information...");

		new ProfInfo_ProfessionalInfoDegreeLvl(getDriver()).fillProfessionalInformationForm(
				TestDataGenerator.randomDegreeLevel(), TestDataGenerator.randomSphere(),
				TestDataGenerator.randomUniversity(), TestDataGenerator.randomNumber(4), "01012026", "01012027",
				TestDataGenerator.randomNotes());
	}

	private void fillAcademicInfo() {
		logger.info("Filling Professional Academic Information...");

		new ProfInfo_ProfessionalInfoAcademics(getDriver()).fillAcademicInfoForm(
				TestDataGenerator.randomAcademicDegree(), "Сертификат", "01012026", "01012027",
				TestDataGenerator.randomAcademicDegree(), TestDataGenerator.randomUniversity(),
				TestDataGenerator.randomSpeciality(), TestDataGenerator.randomNumber(4), "01012028",
				TestDataGenerator.randomNotes());
	}

	private void fillTitleInfo() throws Exception {
		logger.info("Filling Professional Title Information...");

		new ProfInfo_ProfessionalInfoTitle(getDriver()).fillTitleForm(TestDataGenerator.randomTitle(),
				TestDataGenerator.randomUniversity(), TestDataGenerator.randomNumber(5), "01012026",
				TestDataGenerator.randomNotes(), TestDataGenerator.randomPhotoFile());
	}

	private void fillScientificResearchInfo() {
		logger.info("Filling Scientific Research Information...");

		new ProfInfo_DevResearch_SciResearch(getDriver()).fillDevResearchForm(
				TestDataGenerator.randomScienceResearchWork(), "01012026", TestDataGenerator.randomPublishingLevel(),
				TestDataGenerator.randomUrl(), TestDataGenerator.randomMagazineName(),
				TestDataGenerator.randomArticleName(), TestDataGenerator.randomAuthors(),
				TestDataGenerator.randomNotes());
	}

	private void fillRewardsInfo() {
		logger.info("Filling Rewards Information...");

		new ProfInfo_DevResearch_Rewards(getDriver()).fillRewardsForm(TestDataGenerator.randomDocumentType(),
				"01012026", TestDataGenerator.randomDocumentType(), TestDataGenerator.randomNumber(4),
				TestDataGenerator.randomNotes());
	}

	private void fillPatentInfo() {
		logger.info("Filling Patent Information...");

		new ProfInfo_DevResearch_Patent(getDriver()).fillDevResearchPatentForm(TestDataGenerator.randomPatentType(),
				TestDataGenerator.randomInvention(), TestDataGenerator.randomInventionLevel(),
				TestDataGenerator.randomAuthors(), TestDataGenerator.randomString(4), "01012026",
				TestDataGenerator.randomNumber(5), TestDataGenerator.randomNotes());
	}

	private void fillAttestationInfo() {
		logger.info("Filling Attestations Information...");

		new ProfInfo_DevResearch_Attestations(getDriver()).fillAttestationsForm("Excellent", "Not Suitable", "Ok",
				"Appropriate", "01012026", TestDataGenerator.randomNotes());
	}

	private void fillMilitaryInfo() {
		logger.info("Filling Military Information...");

		new ProfInfo_Military(getDriver()).fillMilitaryInformationForm(TestDataGenerator.randomMilitaryRank(),
				TestDataGenerator.randomNumber(4), "01012026", TestDataGenerator.randomNotes());
	}
}
