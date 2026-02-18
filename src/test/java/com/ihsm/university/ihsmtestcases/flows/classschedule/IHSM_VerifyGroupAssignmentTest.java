package com.ihsm.university.ihsmtestcases.flows.classschedule;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.classchedule.IHSM_FacultyGroupAssignment;
import com.ihsm.university.ihsmpageobjects.classchedule.IHSM_FacultyShowData;
import com.ihsm.university.ihsmtestcases.dataprovider.FacultyGroupAssignmentDataProvider;
import com.ihsm.university.ihsmtestcases.dataprovider.FacultyShowDataProvider;
import com.ihsm.university.ihsmtestcases.pojo.FacultyGroupAssignData;
import com.ihsm.university.ihsmtestcases.pojo.FacultyShowData;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_VerifyGroupAssignmentTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();
	private SoftAssert soft = new SoftAssert();

	@Test(groups = "Regression",priority = 0, description = "Verify Group Assignment Test", dataProvider = "FacultyGroupAssignData", dataProviderClass = FacultyGroupAssignmentDataProvider.class)
	public void verifyGroupAssignment(FacultyGroupAssignData data) {
		ExtentTest node = ExtentListener.createNode("Group Assignment Information");
		try {
			node.info("Entering Group Assignment Details");
			IHSM_FacultyGroupAssignment facultyGroup = new IHSM_FacultyGroupAssignment(getDriver());
//			facultyGroup.fillGroupAssignmentInfo("2025 -2026", "1", "CENTRAL / Bachelor / MBBS", "1", "Main");
			facultyGroup.fillGroupAssignmentInfo(data.getSession(), data.getBatch(), data.getAcademicPlan(),
					data.getSemester(), data.getGroupType(), data.getSelectFaculty());
			node.pass("Group Assignment Information Test Passed");
			stepStatus.put("Group Assignment", "PASS");
		} catch (Exception e) {
			node.fail("Group Assignment Information Failed: " + e.getMessage());
			stepStatus.put("Group Assignment", "FAIL");
			node.fail("Group Assignment Information Failed: " + e.getMessage());
		}
	}

	@Test(groups = "Regression",description = "Verify Faculty Group Data Test", priority = 1, dependsOnMethods = "verifyGroupAssignment", dataProvider = "FacultyShowDataProvider", dataProviderClass = FacultyShowDataProvider.class, alwaysRun = true)
	public void verifyFacultyGroupData(FacultyShowData data) {
		ExtentTest node = ExtentListener.createNode("Faculty Show Data Information");
		try {
			node.info("Entering Faculty Show Data Details");
			IHSM_FacultyShowData showData = new IHSM_FacultyShowData(getDriver());
			/*
			 * showData.fillFacultyShowData("2025 -2026", "1", "CENTRAL / Bachelor / MBBS",
			 * "1", "Демонстрационный факультет 3 (Demo Faculty 3)", "Home");
			 */

			showData.fillFacultyShowData(data.getSession(), data.getBatch(), data.getAcademicPlan(), data.getSemester(),
					data.getFacultyName()/* , data.getGroupType() */);

			node.pass("Faculty Show Data Test Passed");
			stepStatus.put("Faculty Show Data", "PASS");
		} catch (Exception e) {
			node.fail("Faculty Show Data Test Failed: " + e.getMessage());
			stepStatus.put("Faculty Show Data", "FAIL");
			node.fail("Faculty Show Data Test Failed: " + e.getMessage());
		}
	}

	@AfterClass(alwaysRun = true)
	public void summarizeGroupAssignmentFlow() {
		System.out.println("==== Group Assignment Flow Status ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("====================================");
		soft.assertAll();
	}
}
