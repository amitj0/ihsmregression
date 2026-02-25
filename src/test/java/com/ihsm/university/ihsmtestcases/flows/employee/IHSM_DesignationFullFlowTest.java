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
        boolean isSuccess = false;
        try {
        	node.info("Entering Employee Rights Information");
        	node.info("Employment Type: "+ "Part Time");
        	node.info("FTE: "+ "0.25");
        	node.info("Action: "+ "Transfer");
        	node.info("Action Reason: "+ TestDataGenerator.randomNumber(5));
        	node.info("Effective Date: "+ "01/01/2026");
        	node.info("End Date: "+ "01/01/2027");
        	node.info("Academic Appointment Type: "+ "Academic");
        	node.info("Academic Appointment Sub Type: "+ "Faculty");	
        	node.info("Academic Plan: "+ "CENTRAL / Bachelor / MBBS");
        	node.info("Semester: "+ "2");
        	node.info("Date: "+ "01/01/2026");
        	node.info("FTE Change: "+ TestDataGenerator.randomNumber(3));
        	node.info("Salary: "+ "200000");
        	node.info("Notes: "+ TestDataGenerator.randomNotes());
        	
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
            isSuccess = true;
            node.pass("Employee Rights Information completed");
            
        } catch (Exception e) {
        	node.fail("Employee Rights Information failed: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
			stepStatus.put("Employee Rights Information", isSuccess ? "PASS" : "FAIL");
		}
    }

    @Test(groups = "Regression",dependsOnMethods = "employeeRightsInformation", alwaysRun = true, description = "Verify Employee Position Information Test")
    public void employeePositionInformation() {
        ExtentTest node = ExtentListener.createNode("Employee Position Information");
        boolean isSuccess = false;
        try {
        	node.info("Entering Employee Position Information");
        	node.info("Position: "+ "Experience in IHSM");
        	node.info("Start Date: "+ "01/01/2026");
        	node.info("End Date: "+ "01/01/2027");
        	node.info("University: "+ TestDataGenerator.randomUniversity());
        	node.info("University Position: "+ TestDataGenerator.randomUniversityPosition());
        	node.info("Notes: "+ TestDataGenerator.randomNotes());
        	
            Designation_Position empPosition = new Designation_Position(getDriver());
            empPosition.fillPositionInOtherOrgForm(
                    "Experience in IHSM", 
                    "01/01/2026", 
                    "01/01/2027",
                    TestDataGenerator.randomUniversity(), 
                    TestDataGenerator.randomUniversityPosition(),
                    TestDataGenerator.randomNotes()
            );
            isSuccess = true;
            node.pass("Employee Position Information completed");
           
        } catch (Exception e) {
        	node.fail("Employee Position Information failed: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
        		stepStatus.put("Employee Position Information", isSuccess ? "PASS" : "FAIL");
        }
    }

    // ---------------- SUMMARY ----------------
    @AfterClass(alwaysRun = true)
    public void summarizeDesignationFlow() {
        System.out.println("\n==== Designation Flow Status for Employee ====");
        stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
        System.out.println("=================================================\n");
    }
}
