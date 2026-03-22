package com.ihsm.university.ihsmtestcases.flows.employee;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
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

	private ProfInfo_ProfessionalInfoDegreeLvl profInfo;
	private ProfInfo_ProfessionalInfoAcademics profAcadInfo;
	private ProfInfo_ProfessionalInfoTitle profTitleInfo;
	private ProfInfo_DevResearch_SciResearch profSciResearchInfo;
	private ProfInfo_DevResearch_Rewards profRewardsInfo;
	private ProfInfo_DevResearch_Patent profPatentInfo;
	private ProfInfo_DevResearch_Attestations profAttestationsInfo;
	private ProfInfo_Military profMilitaryInfo;

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	@Test(groups = "Regression", description = "Verify Employee Professional Degree Information Test")
	public void professionalDegreeInformation() {
		ExtentTest node = ExtentListener.createNode("Professional Degree Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Professional Degree Details");
			node.info("Degree Name: " + "Диплом кандидата медицинских наук");
			node.info("Speciality: " + "Biology");
			node.info("University: " + TestDataGenerator.randomUniversity());
			node.info("Diploma Number: " + TestDataGenerator.randomNumber(4));
			node.info("Diploma Date: " + "01/01/2026");
			node.info("End Date: " + "01/01/2027");
			node.info("Notes: " + TestDataGenerator.randomNotes());

			profInfo = new ProfInfo_ProfessionalInfoDegreeLvl(getDriver());
			profAcadInfo = profInfo.fillProfessionalInformationForm("Диплом кандидата медицинских наук", "Biology",
					TestDataGenerator.randomUniversity(), TestDataGenerator.randomNumber(4), "01012026", "01012027",
					TestDataGenerator.randomNotes());
			isSuccess = true;
			node.pass("Professional Degree Information completed");

		} catch (Exception e) {
			node.fail("Professional Degree Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Professional Degree Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "professionalDegreeInformation", description = "Verify Employee Professional Academics Information Test")
	public void professionalAcademicsInformation() {
		ExtentTest node = ExtentListener.createNode("Professional Academics Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Professional Academics Details");
			node.info("Academic Type: " + "Residency");
			node.info("Document Type: " + "Сертификат");
			node.info("Start Date: " + "01/01/2026");
			node.info("End Date: " + "01/01/2027");
			node.info("Academic Degree: " + TestDataGenerator.randomAcademicDegree());
			node.info("University: " + TestDataGenerator.randomUniversity());
			node.info("Speciality: " + TestDataGenerator.randomSpeciality());
			node.info("Diploma Number: " + TestDataGenerator.randomNumber(4));
			node.info("Diploma Date: " + "01/01/2028");
			node.info("Notes: " + TestDataGenerator.randomNotes());

			profTitleInfo = profAcadInfo.fillAcademicInfoForm("Residency", "Сертификат", "01012026", "01012027",
					TestDataGenerator.randomAcademicDegree(), TestDataGenerator.randomUniversity(),
					TestDataGenerator.randomSpeciality(), TestDataGenerator.randomNumber(4), "01012028",
					TestDataGenerator.randomNotes());
			isSuccess = true;
			node.pass("Professional Academics Information completed");

		} catch (Exception e) {
			node.fail("Professional Academics Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Professional Academics Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "professionalAcademicsInformation", description = "Verify Employee Professional Title Information Test")
	public void professionalTitleInformation() {
		ExtentTest node = ExtentListener.createNode("Professional Title Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Professional Title Details");
			node.info("Title: " + "Professor");
			node.info("University: " + TestDataGenerator.randomUniversity());
			node.info("Order Number: " + TestDataGenerator.randomNumber(5));
			node.info("Order Date: " + "01/01/2026");
			node.info("Notes: " + TestDataGenerator.randomNotes());
//			node.info("Upload Photo: " + TestDataGenerator.randomPhotoFile());

			profSciResearchInfo = profTitleInfo.fillTitleForm("Professor", TestDataGenerator.randomUniversity(),
					TestDataGenerator.randomNumber(5), "01012026", TestDataGenerator.randomNotes(),
					getTestDataPath("male.png"));
			isSuccess = true;
			node.pass("Professional Title Information completed");

		} catch (Exception e) {
			node.fail("Professional Title Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Professional Title Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "professionalTitleInformation", description = "Verify Employee Professional Scientific Research Information Test")
	public void professionalScientificResearchInformation() {
		ExtentTest node = ExtentListener.createNode("Professional Scientific Research Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Professional Scientific Research Details");
			node.info("Research Type: " + "Методические рекомедации");
			node.info("Research Date: " + "01/01/2026");
			node.info("Research Level: " + "Республикалық деңгей");
			node.info("Research URL: " + TestDataGenerator.randomUrl());
			node.info("Magazine Name: " + TestDataGenerator.randomMagazineName());
			node.info("Article Name: " + TestDataGenerator.randomArticleName());
			node.info("Authors: " + TestDataGenerator.randomAuthors());
			node.info("Notes: " + TestDataGenerator.randomNotes());

			profRewardsInfo = profSciResearchInfo.fillDevResearchForm("Методические рекомедации", "01012026",
					"Республикалық деңгей", TestDataGenerator.randomUrl(), TestDataGenerator.randomMagazineName(),
					TestDataGenerator.randomArticleName(), TestDataGenerator.randomAuthors(),
					TestDataGenerator.randomNotes());
			isSuccess = true;
			node.pass("Professional Scientific Research Information completed");

		} catch (Exception e) {
			node.fail("Professional Scientific Research Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Professional Scientific Research Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "professionalScientificResearchInformation", description = "Verify Employee Professional Rewards Information Test")
	public void professionalRewardsInformation() {
		ExtentTest node = ExtentListener.createNode("Professional Rewards Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Professional Rewards Details");
			node.info("Reward Type: " + TestDataGenerator.randomDocumentType());
			node.info("Reward Date: " + "01/01/2026");
			node.info("Rewarding Agency: " + TestDataGenerator.randomDocumentType());
			node.info("Reward Number: " + TestDataGenerator.randomNumber(4));
			node.info("Notes: " + TestDataGenerator.randomNotes());

			profPatentInfo = profRewardsInfo.fillRewardsForm(TestDataGenerator.randomDocumentType(), "01012026",
					TestDataGenerator.randomDocumentType(), TestDataGenerator.randomNumber(4),
					TestDataGenerator.randomNotes());
			isSuccess = true;
			node.pass("Professional Rewards Information completed");

		} catch (Exception e) {
			node.fail("Professional Rewards Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Professional Rewards Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "professionalRewardsInformation", description = "Verify Employee Professional Patent Information Test")
	public void professionalPatentInformation() {
		ExtentTest node = ExtentListener.createNode("Professional Patent Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Professional Patent Details");
			node.info("Patent Type: " + "Patent");
			node.info("Invention Name: " + TestDataGenerator.randomInvention());
			node.info("Patent Country: " + "Republican");
			node.info("Authors: " + TestDataGenerator.randomAuthors());
			node.info("Patent Number: " + TestDataGenerator.randomNumber(5));
			node.info("Patent Date: " + "01/01/2026");
			node.info("Notes: " + TestDataGenerator.randomNotes());
			node.info("Notes: " + TestDataGenerator.randomNotes());

			profAttestationsInfo = profPatentInfo.fillDevResearchPatentForm("Patent",
					TestDataGenerator.randomInvention(), "Republican", TestDataGenerator.randomAuthors(),
					TestDataGenerator.randomString(4), "01012026", TestDataGenerator.randomNumber(5),
					TestDataGenerator.randomNotes());
			isSuccess = true;
			node.pass("Professional Patent Information completed");

		} catch (Exception e) {
			node.fail("Professional Patent Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Professional Patent Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "professionalPatentInformation", description = "Verify Employee Professional Attestations Information Test")
	public void professionalAttestationsInformation() {
		ExtentTest node = ExtentListener.createNode("Professional Attestations Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Professional Attestations Details");
			node.info("Attestation Type: " + "Excellent");
			node.info("Attestation Result: " + "Not Suitable");
			node.info("Attestation Level: " + "Ok");
			node.info("Attestation Category: " + "Appropriate");
			node.info("Attestation Date: " + "01/01/2026");
			node.info("Notes: " + TestDataGenerator.randomNotes());

			profMilitaryInfo = profAttestationsInfo.fillAttestationsForm("Excellent", "Not Suitable", "Ok",
					"Appropriate", "01012026", TestDataGenerator.randomNotes());
			isSuccess = true;
			node.pass("Professional Attestations Information completed");

		} catch (Exception e) {
			node.fail("Professional Attestations Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Professional Attestations Information", isSuccess ? "PASS" : "FAIL");
		}
	}

	@Test(groups = "Regression", dependsOnMethods = "professionalAttestationsInformation", description = "Verify Employee Professional Military Information Test")
	public void professionalMilitaryInformation() {
		ExtentTest node = ExtentListener.createNode("Professional Military Information");
		boolean isSuccess = false;
		try {
			node.info("Entering Professional Military Details");
			node.info("Rank: " + "Lieutenant General");
			node.info("Service Number: " + TestDataGenerator.randomNumber(4));
			node.info("Date: " + "01/01/2026");
			node.info("Notes: " + TestDataGenerator.randomNotes());

			profMilitaryInfo.fillMilitaryInformationForm("Lieutenant General", TestDataGenerator.randomNumber(4),
					"01012026", TestDataGenerator.randomNotes());
			isSuccess = true;
			node.pass("Professional Military Information completed");

		} catch (Exception e) {
			node.fail("Professional Military Information failed: " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			stepStatus.put("Professional Military Information", isSuccess ? "PASS" : "FAIL");
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
