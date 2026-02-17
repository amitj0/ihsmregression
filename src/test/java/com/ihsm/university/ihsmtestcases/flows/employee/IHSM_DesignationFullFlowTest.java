package com.ihsm.university.ihsmtestcases.flows.employee;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.employee.designation.Designation_EmploymentRights;
import com.ihsm.university.ihsmpageobjects.employee.designation.Designation_Position;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_DesignationFullFlowTest extends BaseClass {

    private Map<String, String> stepStatus = new LinkedHashMap<>();

    @Test(groups = "Regression")
    public void employeeRightsInformation() {
        ExtentListener.createNode("Employee Rights Information");
        try {
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
            ExtentListener.getNode().pass("Employee Rights Information completed");
            stepStatus.put("Employee Rights Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Employee Rights Information failed: " + e.getMessage());
            stepStatus.put("Employee Rights Information", "FAIL");
        }
    }

    @Test(groups = "Regression",dependsOnMethods = "employeeRightsInformation", alwaysRun = true)
    public void employeePositionInformation() {
        ExtentListener.createNode("Employee Position Information");
        try {
            Designation_Position empPosition = new Designation_Position(getDriver());
            empPosition.fillPositionInOtherOrgForm(
                    "Experience in IHSM", 
                    "01/01/2026", 
                    "01/01/2027",
                    TestDataGenerator.randomUniversity(), 
                    TestDataGenerator.randomUniversityPosition(),
                    TestDataGenerator.randomNotes()
            );
            ExtentListener.getNode().pass("Employee Position Information completed");
            stepStatus.put("Employee Position Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Employee Position Information failed: " + e.getMessage());
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
