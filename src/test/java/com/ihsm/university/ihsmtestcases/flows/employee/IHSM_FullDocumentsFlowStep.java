package com.ihsm.university.ihsmtestcases.flows.employee;

import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.base.FlowStep;
import com.ihsm.university.ihsmpageobjects.employee.documents.Documents_Documents;
import com.ihsm.university.ihsmpageobjects.employee.documents.Documents_Passport;
import com.ihsm.university.utilities.FlowStateUtils;

public class IHSM_FullDocumentsFlowStep extends BaseClass {

	@Test
	public void verifyFullDocumentsInformation() throws Exception {

		logger.info("===== STARTING FULL DOCUMENTS INFORMATION FLOW =====");

		FlowStep lastStep = FlowStateUtils.getLastCompletedStep();

		if (shouldRun(lastStep, FlowStep.DOCUMENTS)) {
			fillDocumentsInfo();
			FlowStateUtils.saveStep(FlowStep.DOCUMENTS);
			lastStep = FlowStep.DOCUMENTS;

		}

		if (shouldRun(lastStep, FlowStep.PASSPORT2)) {
			fillPassportInfo();
			FlowStateUtils.saveStep(FlowStep.PASSPORT2);
			lastStep = FlowStep.PASSPORT2;

		}

		logger.info("===== FULL DOCUMENTS INFORMATION FLOW COMPLETED SUCCESSFULLY =====");
	}

	/* ================= FLOW CONTROL ================= */

	private boolean shouldRun(FlowStep last, FlowStep current) {
		return last == null || last.ordinal() < current.ordinal();
	}

	/* ================= STEP METHODS ================= */

	private void fillDocumentsInfo() throws Exception {
		logger.info("Filling Documents Information...");

		new Documents_Documents(getDriver()).fillDocumentInformation(TestDataGenerator.randomDocumentType(),
				TestDataGenerator.randomNotes(), TestDataGenerator.randomPhotoFile());
	}

	private void fillPassportInfo() {
		logger.info("Filling Passport Information...");

		new Documents_Passport(getDriver()).fillPassportDetails(TestDataGenerator.randomPassportType(),
				TestDataGenerator.randomPassportCountry(), TestDataGenerator.randomIssueAgency(),
				TestDataGenerator.randomNumber(5), TestDataGenerator.randomPassportNumber(),
				TestDataGenerator.randomCountry(), "01012026", "01012027");
	}
}
