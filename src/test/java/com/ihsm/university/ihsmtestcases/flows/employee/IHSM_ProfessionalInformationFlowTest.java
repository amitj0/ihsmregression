package com.ihsm.university.ihsmtestcases.flows.employee;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_DevResearch_Attestations;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_DevResearch_Patent;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_DevResearch_Rewards;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_DevResearch_SciResearch;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_Military;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_ProfessionalInfoAcademics;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_ProfessionalInfoDegreeLvl;
import com.ihsm.university.ihsmpageobjects.employee.professionalinformation.ProfInfo_ProfessionalInfoTitle;
import com.ihsm.university.utilities.ExtentListener;

public class IHSM_ProfessionalInformationFlowTest extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	@Test(groups = "Regression", description = "Verify Professional Degree Information Test")
	public void professionalDegreeInformation() {
		ExtentListener.createNode("Professional Degree Information");
		try {
			ProfInfo_ProfessionalInfoDegreeLvl profInfo = new ProfInfo_ProfessionalInfoDegreeLvl(getDriver());
			profInfo.fillProfessionalInformationForm("Диплом кандидата медицинских наук", "Biology",
					TestDataGenerator.randomUniversity(), TestDataGenerator.randomNumber(4), "01012026", "01012027",
					TestDataGenerator.randomNotes());
			ExtentListener.getNode().pass("Professional Degree Information completed");
			stepStatus.put("Professional Degree Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Professional Degree Information failed: " + e.getMessage());
			stepStatus.put("Professional Degree Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "professionalDegreeInformation", alwaysRun = true, description = "Verify Professional Academics Information Test")
	public void professionalAcademicsInformation() {
		ExtentListener.createNode("Professional Academics Information");
		try {
			ProfInfo_ProfessionalInfoAcademics profAcadInfo = new ProfInfo_ProfessionalInfoAcademics(getDriver());
			profAcadInfo.fillAcademicInfoForm("Residency", "Сертификат", "01012026", "01012027",
					TestDataGenerator.randomAcademicDegree(), TestDataGenerator.randomUniversity(),
					TestDataGenerator.randomSpeciality(), TestDataGenerator.randomNumber(4), "01012028",
					TestDataGenerator.randomNotes());
			ExtentListener.getNode().pass("Professional Academics Information completed");
			stepStatus.put("Professional Academics Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Professional Academics Information failed: " + e.getMessage());
			stepStatus.put("Professional Academics Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "professionalAcademicsInformation", alwaysRun = true, description = "Verify Professional Title Information Test")
	public void professionalTitleInformation() {
		ExtentListener.createNode("Professional Title Information");
		try {
			ProfInfo_ProfessionalInfoTitle profTitleInfo = new ProfInfo_ProfessionalInfoTitle(getDriver());
			profTitleInfo.fillTitleForm("Professor", TestDataGenerator.randomUniversity(),
					TestDataGenerator.randomNumber(5), "01012026", TestDataGenerator.randomNotes(),
					TestDataGenerator.randomPhotoFile());
			ExtentListener.getNode().pass("Professional Title Information completed");
			stepStatus.put("Professional Title Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Professional Title Information failed: " + e.getMessage());
			stepStatus.put("Professional Title Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "professionalTitleInformation", alwaysRun = true, description = "Verify Professional Scientific Research Information Test")
	public void professionalScientificResearchInformation() {
		ExtentListener.createNode("Professional Scientific Research Information");
		try {
			ProfInfo_DevResearch_SciResearch profSciResearchInfo = new ProfInfo_DevResearch_SciResearch(getDriver());
			profSciResearchInfo.fillDevResearchForm("Методические рекомедации", "01012026", "Республикалық деңгей",
					TestDataGenerator.randomUrl(), TestDataGenerator.randomMagazineName(),
					TestDataGenerator.randomArticleName(), TestDataGenerator.randomAuthors(),
					TestDataGenerator.randomNotes());
			ExtentListener.getNode().pass("Professional Scientific Research Information completed");
			stepStatus.put("Professional Scientific Research Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Professional Scientific Research Information failed: " + e.getMessage());
			stepStatus.put("Professional Scientific Research Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "professionalScientificResearchInformation", alwaysRun = true, description = "Verify Professional Rewards Information Test")
	public void professionalRewardsInformation() {
		ExtentListener.createNode("Professional Rewards Information");
		try {
			ProfInfo_DevResearch_Rewards profRewardsInfo = new ProfInfo_DevResearch_Rewards(getDriver());
			profRewardsInfo.fillRewardsForm(TestDataGenerator.randomDocumentType(), "01012026",
					TestDataGenerator.randomDocumentType(), TestDataGenerator.randomNumber(4),
					TestDataGenerator.randomNotes());
			ExtentListener.getNode().pass("Professional Rewards Information completed");
			stepStatus.put("Professional Rewards Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Professional Rewards Information failed: " + e.getMessage());
			stepStatus.put("Professional Rewards Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "professionalRewardsInformation", alwaysRun = true, description = "Verify Professional Patent Information Test")
	public void professionalPatentInformation() {
		ExtentListener.createNode("Professional Patent Information");
		try {
			ProfInfo_DevResearch_Patent profPatentInfo = new ProfInfo_DevResearch_Patent(getDriver());
			profPatentInfo.fillDevResearchPatentForm("Patent", TestDataGenerator.randomInvention(), "Republican",
					TestDataGenerator.randomAuthors(), TestDataGenerator.randomString(4), "01012026",
					TestDataGenerator.randomNumber(5), TestDataGenerator.randomNotes());
			ExtentListener.getNode().pass("Professional Patent Information completed");
			stepStatus.put("Professional Patent Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Professional Patent Information failed: " + e.getMessage());
			stepStatus.put("Professional Patent Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "professionalPatentInformation", alwaysRun = true, description = "Verify Professional Attestations Information Test")
	public void professionalAttestationsInformation() {
		ExtentListener.createNode("Professional Attestations Information");
		try {
			ProfInfo_DevResearch_Attestations profAttestationsInfo = new ProfInfo_DevResearch_Attestations(getDriver());
			profAttestationsInfo.fillAttestationsForm("Excellent", "Not Suitable", "Ok", "Appropriate", "01012026",
					TestDataGenerator.randomNotes());
			ExtentListener.getNode().pass("Professional Attestations Information completed");
			stepStatus.put("Professional Attestations Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Professional Attestations Information failed: " + e.getMessage());
			stepStatus.put("Professional Attestations Information", "FAIL");
		}
	}

	@Test(groups = "Regression",dependsOnMethods = "professionalAttestationsInformation", alwaysRun = true, description = "Verify Professional Military Information Test")
	public void professionalMilitaryInformation() {
		ExtentListener.createNode("Professional Military Information");
		try {
			ProfInfo_Military profMilitaryInfo = new ProfInfo_Military(getDriver());
			profMilitaryInfo.fillMilitaryInformationForm("Lieutenant General", TestDataGenerator.randomNumber(4),
					"01012026", TestDataGenerator.randomNotes());
			ExtentListener.getNode().pass("Professional Military Information completed");
			stepStatus.put("Professional Military Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Professional Military Information failed: " + e.getMessage());
			stepStatus.put("Professional Military Information", "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeProfessionalFlow() {
		System.out.println("==== Professional Flow Status for Employee ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
