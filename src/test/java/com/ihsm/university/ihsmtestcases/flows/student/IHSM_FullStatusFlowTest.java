package com.ihsm.university.ihsmtestcases.flows.student;

import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.base.FlowStep;
import com.ihsm.university.ihsmpageobjects.student.status.Status_Status;
import com.ihsm.university.utilities.FlowStateUtils;

public class IHSM_FullStatusFlowTest extends BaseClass {

	@Test
	public void verifyFullStatusInformation() throws Exception {

		logger.info("===== STARTING FULL STATUS FLOW =====");

		FlowStep lastStep = FlowStateUtils.getLastCompletedStep();

		if (shouldRun(lastStep, FlowStep.STATUS)) {
			fillStatus();
			FlowStateUtils.saveStep(FlowStep.STATUS);
			lastStep = FlowStep.STATUS;

		}

		logger.info("===== FULL STATUS FLOW COMPLETED SUCCESSFULLY =====");
	}

	/* ================= FLOW CONTROL ================= */
	private boolean shouldRun(FlowStep last, FlowStep current) {
		return last == null || last.ordinal() < current.ordinal();
	}

	/* ================= STEP METHODS ================= */
	private void fillStatus() {
		logger.info("Filling Status Information...");

		new Status_Status(getDriver()).fillStatusStatusForm(StudentFullRegistrationDataVariables.status,
				StudentFullRegistrationDataVariables.statusDate, StudentFullRegistrationDataVariables.statusCode,
				StudentFullRegistrationDataVariables.statusRemarks,
				getTestDataPath(StudentFullRegistrationDataVariables.statusImage));
	}
}
