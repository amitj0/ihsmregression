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
import com.ihsm.university.ihsmtestcases.pojo.FacultyGroupAssignData2;
import com.ihsm.university.ihsmtestcases.pojo.FacultyShowData;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_VerifyGroupAssignmentTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	@Test(enabled = true, groups = "Regression", priority = 0, description = "Verify Group Lecture Assignment Test", dataProvider = "FacultyGroupAssignData", dataProviderClass = FacultyGroupAssignmentDataProvider.class)
	public void verifyGroupAssignmentLec(FacultyGroupAssignData data) {
		ExtentTest node = ExtentListener
				.createNode("Group Assignment Information [" + data.getSession() + "_" + data.getBatch() + "]");
		boolean isSuccess = false;
		try {
			node.info("Entering Group Assignment Details");
			node.info("Academic Session: " + data.getSession());
			node.info("Batch: " + data.getBatch());
			node.info("Academic Plan: " + data.getAcademicPlan());
			node.info("Semester: " + data.getSemester());
			node.info("Group Type: " + data.getGroupType());
			node.info("Subject: " + data.getSubjectName());
			node.info("Pending for Assignment: " + data.getColumnType());
			node.info("Select Faculty: " + data.getSelectFaculty());

			IHSM_FacultyGroupAssignment facultyGroup = new IHSM_FacultyGroupAssignment(getDriver());
			facultyGroup.fillGroupAssignmentInfo(data.getSession(), data.getBatch(), data.getAcademicPlan(),
					data.getSemester(), data.getGroupType(), data.getSubjectName(), data.getColumnType(),
					data.getSelectFaculty());

			isSuccess = true;
			node.pass("Group Lecture Assignment Information Test Passed");
		} catch (Exception e) {
			node.fail("Group Lecture Assignment Information Failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Group Lecture Assignment [" + data.getSession() + "-" + data.getBatch() + "]",
					isSuccess ? "PASS" : "FAIL");

		}
	}

	@Test(enabled = false, groups = "Regression", priority = 1, description = "Verify Group Practical Assignment Test", dataProvider = "FacultyGroupAssignData2", dataProviderClass = FacultyGroupAssignmentDataProvider.class)
	public void verifyGroupAssignmentPra(FacultyGroupAssignData2 data) {
		ExtentTest node = ExtentListener
				.createNode("Group Assignment Information [" + data.getSession() + "_" + data.getBatch() + "]");
		boolean isSuccess = false;
		try {
			node.info("Entering Group Assignment Details");
			node.info("Academic Session: " + data.getSession());
			node.info("Batch: " + data.getBatch());
			node.info("Academic Plan: " + data.getAcademicPlan());
			node.info("Semester: " + data.getSemester());
			node.info("Group Type: " + data.getGroupType());
			node.info("Subject: " + data.getSubjectName());
			node.info("Pending for Assignment: " + data.getColumnType());
			node.info("Select Faculty: " + data.getSelectFaculty());

			IHSM_FacultyGroupAssignment facultyGroup = new IHSM_FacultyGroupAssignment(getDriver());
			facultyGroup.fillGroupAssignmentInfo(data.getSession(), data.getBatch(), data.getAcademicPlan(),
					data.getSemester(), data.getGroupType(), data.getSubjectName(), data.getColumnType(),
					data.getSelectFaculty());

			isSuccess = true;
			node.pass("Group Practical Assignment Information Test Passed");
		} catch (Exception e) {
			node.fail("Group Practical Assignment Information Failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Group Practical Assignment [" + data.getSession() + "-" + data.getBatch() + "]",
					isSuccess ? "PASS" : "FAIL");

		}
	}

	@Test(enabled = true, groups = "Regression", description = "Verify Faculty Group Data Test", priority = 2, dataProvider = "FacultyShowDataProvider", dataProviderClass = FacultyShowDataProvider.class, alwaysRun = true)
	public void verifyFacultyGroupData(FacultyShowData data) {
		ExtentTest node = ExtentListener.createNode("Faculty Show Data Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Faculty Show Data Details");
			node.info("Academic Session: " + data.getSession());
			node.info("Batch: " + data.getBatch());
			node.info("Academic Plan: " + data.getAcademicPlan());
			node.info("Semester: " + data.getSemester());
			node.info("Faculty Name: " + data.getFacultyName());

			IHSM_FacultyShowData showData = new IHSM_FacultyShowData(getDriver());
			/*
			 * showData.fillFacultyShowData("2025 -2026", "1", "CENTRAL / Bachelor / MBBS",
			 * "1", "Демонстрационный факультет 3 (Demo Faculty 3)", "Home");
			 */

			showData.fillFacultyShowData(data.getSession(), data.getBatch(), data.getAcademicPlan(), data.getSemester(),
					data.getFacultyName()/* , data.getGroupType() */);
			isSuccess = true;
			node.pass("Faculty Show Data Test Passed");
		} catch (Exception e) {
			node.fail("Faculty Show Data Test Failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {

			stepStatus.put("Faculty Show Data [" + data.getSession() + "-" + data.getBatch() + "]",
					isSuccess ? "PASS" : "FAIL");

		}
	}

	@AfterClass(alwaysRun = true)
	public void summarizeGroupAssignmentFlow() {

		System.out.println("\n==== Group Assignment Flow Status ====");

		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));

		System.out.println("====================================\n");

	}
}
