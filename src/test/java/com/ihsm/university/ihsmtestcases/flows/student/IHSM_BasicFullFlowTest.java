package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.basicinformation.*;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_BasicFullFlowTest extends BaseClass {

    private WebDriver driver;
    private Map<String, String> stepStatus = new LinkedHashMap<>();
    private int failCount = 0;

    public IHSM_BasicFullFlowTest(WebDriver driver) {
        this.driver = driver;
    }

    public static String studentEnrollmentId;
    private StudentFullRegistrationDataVariables student;

    public void setStudent(StudentFullRegistrationDataVariables student) {
        this.student = student;
    }

    @Test
    public void enrollmentInformation() {
        ExtentListener.createNode("Enrollment Information");
        try {
            BasicInfo_EnrollnmentInformation enrollInfo = new BasicInfo_EnrollnmentInformation(getDriver());
            enrollInfo.fillEnrollmentInformation(student.term, student.course, student.year, student.semester,
                    student.pin, student.firstName, student.middleName, student.lastName, student.gender,
                    student.dob, student.country, student.state, student.mobile, student.email, student.nationality);

            studentEnrollmentId = enrollInfo.getStudentEnrollmentId();
            if (studentEnrollmentId == null || studentEnrollmentId.isEmpty()) {
                throw new RuntimeException("Student Enrollment ID not generated!");
            }

            ExtentListener.getNode().pass("Enrollment Information completed");
            stepStatus.put("Enrollment Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Enrollment Information failed: " + e.getMessage());
            stepStatus.put("Enrollment Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "enrollmentInformation", alwaysRun = true)
    public void personalInformation() {
        ExtentListener.createNode("Personal Information");
        try {
            BasicInfo_PersonalInformation personalInfo = new BasicInfo_PersonalInformation(getDriver());
            personalInfo.fillPersonalInformationForm(student.firstName2, student.lastName2, student.city,
                    student.maritalStatus, student.country2);

            ExtentListener.getNode().pass("Personal Information completed");
            stepStatus.put("Personal Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Personal Information failed: " + e.getMessage());
            stepStatus.put("Personal Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "personalInformation", alwaysRun = true)
    public void biometricsInformation() {
        ExtentListener.createNode("Biometrics Information");
        try {
            BasicInfo_Biometrics biometrics = new BasicInfo_Biometrics(getDriver());
            biometrics.fillBiometricsInfo(TestDataGenerator.randomEmployeePhotoFile());

            ExtentListener.getNode().pass("Biometrics Information completed");
            stepStatus.put("Biometrics Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Biometrics Information failed: " + e.getMessage());
            stepStatus.put("Biometrics Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "biometricsInformation", alwaysRun = true)
    public void familyInformation() {
        ExtentListener.createNode("Family Information");
        try {
            BasicInfo_FamilyInformation familyInfo = new BasicInfo_FamilyInformation(getDriver());
            familyInfo.fillFamilyInformation(student.relation, student.familyName, student.familyDob,
                    student.occupation, student.countryCode, student.phone, student.dependent, student.famCountry,
                    student.famState, student.famCity, student.famNationality);

            ExtentListener.getNode().pass("Family Information completed");
            stepStatus.put("Family Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Family Information failed: " + e.getMessage());
            stepStatus.put("Family Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "familyInformation", alwaysRun = true)
    public void languageInformation() {
        ExtentListener.createNode("Language Information");
        try {
            BasicInfo_LanguageInformation languageInfo = new BasicInfo_LanguageInformation(getDriver());
            languageInfo.fillLanguageInformationForm(student.language, student.languageLevel);

            ExtentListener.getNode().pass("Language Information completed");
            stepStatus.put("Language Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Language Information failed: " + e.getMessage());
            stepStatus.put("Language Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "languageInformation", alwaysRun = true)
    public void preRightsInformation() {
        ExtentListener.createNode("Pre Rights Information");
        try {
            BasicInfo_GeneralInformation_Prerights prerightsInfo = new BasicInfo_GeneralInformation_Prerights(getDriver());
            prerightsInfo.fillPreferRightsInformation(student.preRights, getTestDataPath(student.preRightsImage));

            ExtentListener.getNode().pass("Pre Rights Information completed");
            stepStatus.put("Pre Rights Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Pre Rights Information failed: " + e.getMessage());
            stepStatus.put("Pre Rights Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "preRightsInformation", alwaysRun = true)
    public void socialStatusInformation() {
        ExtentListener.createNode("Social Status Information");
        try {
            BasicInfo_GeneralInformation_SocialStatus socialInfo = new BasicInfo_GeneralInformation_SocialStatus(getDriver());
            socialInfo.fillSocialStatusForm(student.socialStatus, getTestDataPath(student.socialStatusImage));

            ExtentListener.getNode().pass("Social Status Information completed");
            stepStatus.put("Social Status Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Social Status Information failed: " + e.getMessage());
            stepStatus.put("Social Status Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "socialStatusInformation", alwaysRun = true)
    public void workLocationInformation() {
        ExtentListener.createNode("Work Location Information");
        try {
            BasicInfo_GeneralInformation_SocialWorkLocation socialWorkInfo = new BasicInfo_GeneralInformation_SocialWorkLocation(getDriver());
            socialWorkInfo.fillSocialWorkLocationDetails(getTestDataPath(student.workLocationImage));

            ExtentListener.getNode().pass("Work Location Information completed");
            stepStatus.put("Work Location Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Work Location Information failed: " + e.getMessage());
            stepStatus.put("Work Location Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "workLocationInformation", alwaysRun = true)
    public void medicalVaccinationInformation() {
        ExtentListener.createNode("Medical Vaccination Information");
        try {
            BasicInfo_MedicalInformation_Vaccination medicalInfo = new BasicInfo_MedicalInformation_Vaccination(getDriver());
            medicalInfo.fillVaccinationInfo(student.vacDose, student.vacNumber, student.vacDate, student.vacCode,
                    student.vacRemarks, getTestDataPath(student.vacImage));

            ExtentListener.getNode().pass("Medical Vaccination Information completed");
            stepStatus.put("Medical Vaccination Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Medical Vaccination Information failed: " + e.getMessage());
            stepStatus.put("Medical Vaccination Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "medicalVaccinationInformation", alwaysRun = true)
    public void medicalAtPolyInformation() {
        ExtentListener.createNode("Medical At Poly Information");
        try {
            BasicInfo_MedicalInforamtion_AtPoly medicalPolyInfo = new BasicInfo_MedicalInforamtion_AtPoly(getDriver());
            medicalPolyInfo.fillAtPolyMedicalInformation(student.polyDate, student.polyType,
                    getTestDataPath(student.polyImage));

            ExtentListener.getNode().pass("Medical At Poly Information completed");
            stepStatus.put("Medical At Poly Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Medical At Poly Information failed: " + e.getMessage());
            stepStatus.put("Medical At Poly Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "medicalAtPolyInformation", alwaysRun = true)
    public void medicalInsuranceInformation() {
        ExtentListener.createNode("Medical Insurance Information");
        try {
            BasicInfo_MedicalInformation_Insurance medicalInsuranceInfo = new BasicInfo_MedicalInformation_Insurance(getDriver());
            medicalInsuranceInfo.fillInsuranceInformation(student.insStartDate, student.insEnd,
                    getTestDataPath(student.insImage));

            ExtentListener.getNode().pass("Medical Insurance Information completed");
            stepStatus.put("Medical Insurance Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Medical Insurance Information failed: " + e.getMessage());
            stepStatus.put("Medical Insurance Information", "FAIL");
            failCount++;
        }
    }

    @Test(dependsOnMethods = "medicalInsuranceInformation", alwaysRun = true)
    public void medicalDisabilityInformation() {
        ExtentListener.createNode("Medical Disability Information");
        try {
            BasicInfo_MedicalInformation_Disability medicalDisabilityInfo = new BasicInfo_MedicalInformation_Disability(getDriver());
            medicalDisabilityInfo.fillDisabilityForm(student.disType, student.disCode, student.disDate,
                    getTestDataPath(student.disImage));

            ExtentListener.getNode().pass("Medical Disability Information completed");
            stepStatus.put("Medical Disability Information", "PASS");
        } catch (Exception e) {
            ExtentListener.getNode().fail("Medical Disability Information failed: " + e.getMessage());
            stepStatus.put("Medical Disability Information", "FAIL");
            failCount++;
        }
    }

    // ---------------- SUMMARY ----------------
    @AfterClass(alwaysRun = true)
    public void summarizeBasicInformationFlow() {
        ExtentListener.createNode("BASIC INFORMATION FLOW SUMMARY");
        if (failCount == 0) {
            ExtentListener.getNode().pass("All Basic Information sections executed successfully.");
        } else {
            ExtentListener.getNode().fail("Total Failed Sections in Basic Information Flow: " + failCount);
        }

        System.out.println("==== Basic Information Flow Status for Student: " +
                student.firstName + " " + student.lastName + " ====");
        stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
        System.out.println("=================================================");
    }
}
