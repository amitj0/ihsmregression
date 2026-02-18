package com.ihsm.university.ihsmtestcases.flows.employee;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.employee.designation.Designation_EmploymentRights;
import com.ihsm.university.ihsmpageobjects.employee.designation.Designation_Position;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_DesignationFullFlowTest extends BaseClass {

    private Map<String, String> stepStatus = new LinkedHashMap<>();

    @Test(groups = "Regression", description = "Verify Employee Rights Information Test")
    public void employeeRightsInformation() {
        ExtentTest node = ExtentListener.createNode("Employee Rights Information");
        try {
        	node.info("Entering Employee Rights Information");
            Designation_EmploymentRights empRights = new Designation_EmploymentRights(getDriver());
            empRights.fillEmploymentRightsForm(
                    "Part Time", 
                    "0.25", 
                    "Transfer", 
                    TestDataGenerator.randomNumber(5),
                    "01012026", 
                    "01012027", 
                    "Academic", 
                    "Faculty", 
                    "CENTRAL / Bachelor / MBBS", 
                    "2", 
                    "01012026",
                    TestDataGenerator.randomNumber(3), 
                    "200000", 
                    TestDataGenerator.randomNotes()
            );
            node.pass("Employee Rights Information completed");
            stepStatus.put("Employee Rights Information", "PASS");
        } catch (Exception e) {
        	node.fail("Employee Rights Information failed: " + e.getMessage());
            stepStatus.put("Employee Rights Information", "FAIL");
        }
    }

    @Test(groups = "Regression",dependsOnMethods = "employeeRightsInformation", alwaysRun = true, description = "Verify Employee Position Information Test")
    public void employeePositionInformation() {
        ExtentTest node = ExtentListener.createNode("Employee Position Information");
        try {
        	node.info("Entering Employee Position Information");
            Designation_Position empPosition = new Designation_Position(getDriver());
            empPosition.fillPositionInOtherOrgForm(
                    "Experience in IHSM", 
                    "01/01/2026", 
                    "01/01/2027",
                    TestDataGenerator.randomUniversity(), 
                    TestDataGenerator.randomUniversityPosition(),
                    TestDataGenerator.randomNotes()
            );
            node.pass("Employee Position Information completed");
            stepStatus.put("Employee Position Information", "PASS");
        } catch (Exception e) {
        	node.fail("Employee Position Information failed: " + e.getMessage());
            stepStatus.put("Employee Position Information", "FAIL");
        }
    }

    // ---------------- SUMMARY ----------------
    @AfterClass(alwaysRun = true)
    public void summarizeDesignationFlow() {
        System.out.println("==== Designation Flow Status for Employee ====");
        stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
        System.out.println("=================================================");
    }
}
