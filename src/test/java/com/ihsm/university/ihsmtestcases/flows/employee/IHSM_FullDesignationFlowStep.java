package com.ihsm.university.ihsmtestcases.flows.employee;

import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.base.FlowStep;
import com.ihsm.university.ihsmpageobjects.employee.designation.Designation_EmploymentRights;
import com.ihsm.university.ihsmpageobjects.employee.designation.Designation_Position;
import com.ihsm.university.utilities.FlowStateUtils;

public class IHSM_FullDesignationFlowStep extends BaseClass {

	@Test
	public void verifyFullDesignationInformation() throws Exception {

		logger.info("===== STARTING FULL DESIGNATION INFORMATION FLOW =====");

		FlowStep lastStep = FlowStateUtils.getLastCompletedStep();

		if (shouldRun(lastStep, FlowStep.EMP_RIGHTS)) {
			fillEmploymentRights();
			FlowStateUtils.saveStep(FlowStep.EMP_RIGHTS);
			lastStep = FlowStep.EMP_RIGHTS;

		}

		if (shouldRun(lastStep, FlowStep.EMP_POSITION)) {
			fillEmployeePosition();
			FlowStateUtils.saveStep(FlowStep.EMP_POSITION);
			lastStep = FlowStep.EMP_POSITION;

		}

		logger.info("===== FULL DESIGNATION INFORMATION FLOW COMPLETED SUCCESSFULLY =====");
	}

	/* ================= FLOW CONTROL ================= */

	private boolean shouldRun(FlowStep last, FlowStep current) {
		return last == null || last.ordinal() < current.ordinal();
	}

	/* ================= STEP METHODS ================= */

	private void fillEmploymentRights() {
		logger.info("Filling Employee Rights Information...");

		new Designation_EmploymentRights(getDriver()).fillEmploymentRightsForm("Part Time", "0.25", "Transfer",
				TestDataGenerator.randomNumber(5), "01012026", "01012027", "Academic", "Rector", "Nursuing / PG / MBBS",
				"2", "01012026", TestDataGenerator.randomNumber(3), "200000", TestDataGenerator.randomNotes());
	}

	private void fillEmployeePosition() {
		logger.info("Filling Employee Position Information...");

		new Designation_Position(getDriver()).fillPositionInOtherOrgForm("Experience in IHSM", "01/01/2026",
				"01/01/2027", TestDataGenerator.randomUniversity(), TestDataGenerator.randomUniversityPosition(),
				TestDataGenerator.randomNotes());
	}
}
