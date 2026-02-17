package com.ihsm.university.ihsmtestcases.flows.student;

import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.base.FlowStep;
import com.ihsm.university.ihsmpageobjects.student.documents.*;
import com.ihsm.university.utilities.FlowStateUtils;

public class IHSM_FullDocumentsFlowTest extends BaseClass {

	@Test
	public void verifyFullDocumentsInformation() throws Exception {

		logger.info("===== STARTING FULL DOCUMENTS FLOW =====");

		FlowStep lastStep = FlowStateUtils.getLastCompletedStep();

		if (shouldRun(lastStep, FlowStep.OTHER_DOCUMENTS)) {
			fillOtherDocuments();
			FlowStateUtils.saveStep(FlowStep.OTHER_DOCUMENTS);
			lastStep = FlowStep.OTHER_DOCUMENTS;

		}

		if (shouldRun(lastStep, FlowStep.IDENTIFICATION_CARD)) {
			fillIdentificationCard();
			FlowStateUtils.saveStep(FlowStep.IDENTIFICATION_CARD);
			lastStep = FlowStep.IDENTIFICATION_CARD;

		}

		if (shouldRun(lastStep, FlowStep.VISA_OFFLINE)) {
			fillVisaOffline();
			FlowStateUtils.saveStep(FlowStep.VISA_OFFLINE);
			lastStep = FlowStep.VISA_OFFLINE;

		}

		if (shouldRun(lastStep, FlowStep.VISA_ONLINE)) {
			fillVisaOnline();
			FlowStateUtils.saveStep(FlowStep.VISA_ONLINE);
			lastStep = FlowStep.VISA_ONLINE;

		}

		if (shouldRun(lastStep, FlowStep.VISA_REGISTER)) {
			fillVisaRegister();
			FlowStateUtils.saveStep(FlowStep.VISA_REGISTER);
			lastStep = FlowStep.VISA_REGISTER;

		}

		if (shouldRun(lastStep, FlowStep.PASSPORT_LOCATION)) {
			fillPassportLocation();
			FlowStateUtils.saveStep(FlowStep.PASSPORT_LOCATION);
			lastStep = FlowStep.PASSPORT_LOCATION;

		}

		if (shouldRun(lastStep, FlowStep.PASSPORT)) {
			fillPassportInfo();
			FlowStateUtils.saveStep(FlowStep.PASSPORT);
			lastStep = FlowStep.PASSPORT;

		}

		logger.info("===== FULL DOCUMENTS FLOW COMPLETED SUCCESSFULLY =====");
	}

	/* ================= FLOW CONTROL ================= */

	private boolean shouldRun(FlowStep last, FlowStep current) {
		return last == null || last.ordinal() < current.ordinal();
	}

	/* ================= STEP METHODS ================= */

	private void fillOtherDocuments() {
		logger.info("Filling Other Documents...");

		new Documents_OtherDocuments(getDriver()).fillOtherDocumentsForm(
				StudentFullRegistrationDataVariables.otherDocumentName,
				getTestDataPath(StudentFullRegistrationDataVariables.otherDocumentImage));
	}

	private void fillIdentificationCard() {
		logger.info("Filling Identification Card...");

		new Documents_IdentificationCard(getDriver()).fillIdentificationCardDetails(
				StudentFullRegistrationDataVariables.idNumber, StudentFullRegistrationDataVariables.idCountry,
				StudentFullRegistrationDataVariables.idIssueDate, StudentFullRegistrationDataVariables.idExpiryDate,
				getTestDataPath(StudentFullRegistrationDataVariables.idImage));
	}

	private void fillVisaOffline() {
		logger.info("Filling Visa Offline...");

		new Documents_VisaInfo_OffVisa(getDriver()).fillVisaInfoOffVisaForm(
				StudentFullRegistrationDataVariables.visaType, StudentFullRegistrationDataVariables.visaPlaceOfIssue,
				StudentFullRegistrationDataVariables.visaIssueDate, StudentFullRegistrationDataVariables.visaStartDate,
				StudentFullRegistrationDataVariables.visaEndDate, StudentFullRegistrationDataVariables.visaRenewDate,
				StudentFullRegistrationDataVariables.visaNumber, StudentFullRegistrationDataVariables.visaCountry,
				StudentFullRegistrationDataVariables.visaRemarks,
				getTestDataPath(StudentFullRegistrationDataVariables.visaImage));
	}

	private void fillVisaOnline() {
		logger.info("Filling Visa Online...");

		new Documents_VisaInfo_OnVisa(getDriver()).fillOnlineVisaInfo(
				StudentFullRegistrationDataVariables.visaOnlineType,
				StudentFullRegistrationDataVariables.visaOnlineStartDate,
				StudentFullRegistrationDataVariables.visaOnlineIssueDate,
				StudentFullRegistrationDataVariables.visaOnlineEndDate,
				StudentFullRegistrationDataVariables.visaOnlineNumber);
	}

	private void fillVisaRegister() {
		logger.info("Filling Visa Register...");

		new Documents_VisaInfo_Register(getDriver()).fillRegisterInfo(
				StudentFullRegistrationDataVariables.visaRegisterPlace,
				StudentFullRegistrationDataVariables.visaRegisterCountry,
				StudentFullRegistrationDataVariables.visaRegisterDate,
				StudentFullRegistrationDataVariables.visaRegisterRemarks);
	}

	private void fillPassportLocation() {
		logger.info("Filling Passport Location...");

		new Documents_VisaInfo_PassportLocation(getDriver()).fillPassportLocationInfo(
				StudentFullRegistrationDataVariables.passportLocation,
				StudentFullRegistrationDataVariables.passportLocationDate);
	}

	private void fillPassportInfo() {
		logger.info("Filling Passport Information...");

		new Documents_PassportInformation(getDriver()).fillPassportInformation(
				StudentFullRegistrationDataVariables.passportNumber,
				StudentFullRegistrationDataVariables.passportIssuePlace,
				StudentFullRegistrationDataVariables.passportIssueDate,
				StudentFullRegistrationDataVariables.passportExpiryDate);
	}
}
