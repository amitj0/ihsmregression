package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;
import com.ihsm.university.ihsmpageobjects.student.documents.*;
import com.ihsm.university.ihsmtestcases.dataprovider.IdentificationCardData;
import com.ihsm.university.ihsmtestcases.dataprovider.OtherDocumentsData;
import com.ihsm.university.ihsmtestcases.dataprovider.PassportData;
import com.ihsm.university.ihsmtestcases.dataprovider.PassportLocationData;
import com.ihsm.university.ihsmtestcases.dataprovider.VisaOfflineData;
import com.ihsm.university.ihsmtestcases.dataprovider.VisaOnlineData;
import com.ihsm.university.ihsmtestcases.dataprovider.VisaRegisterData;
import com.ihsm.university.ihsmtestcases.flows.classschedule.TestDataGenerator;
import com.ihsm.university.ihsmtestcases.pojo.StudentIdentificationData;
import com.ihsm.university.ihsmtestcases.pojo.StudentOtherDocuments;
import com.ihsm.university.ihsmtestcases.pojo.StudentPassportData;
import com.ihsm.university.ihsmtestcases.pojo.StudentPassportLocationData;
import com.ihsm.university.ihsmtestcases.pojo.StudentVisaOfflineData;
import com.ihsm.university.ihsmtestcases.pojo.StudentVisaOnlineData;
import com.ihsm.university.ihsmtestcases.pojo.StudentVisaRegisterData;
import com.ihsm.university.utilities.ExtentListener;
import com.ihsm.university.utilities.TextUtility;

public class IHSM_DocumentFullFlowTest2 extends BaseClass {

	private Map<String, String> stepStatus = new LinkedHashMap<>();

	// ---------------- OTHER DOCUMENTS ----------------
	@Test(dataProvider = "StudentOtherDocuments", dataProviderClass = OtherDocumentsData.class)
	public void otherDocumentsInformation(StudentOtherDocuments data) {
		ExtentListener.createNode("Other Documents Information");
		try {
			Documents_OtherDocuments documentsPage = new Documents_OtherDocuments(getDriver());
//            documentsPage.fillOtherDocumentsForm("Passport Front", TestDataGenerator.randomEmployeePhotoFile());
			documentsPage.fillOtherDocumentsForm(data.getDocumentType(), TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Other Documents Information completed");
			stepStatus.put("Other Documents", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Other Documents Information failed: " + e.getMessage());
			stepStatus.put("Other Documents", "FAIL");
		}
	}

	// ---------------- IDENTIFICATION CARD ----------------
	@Test(dependsOnMethods = "otherDocumentsInformation", dataProvider = "StudentIdentificationCardData", dataProviderClass = IdentificationCardData.class)
	public void identificationCardInformation(StudentIdentificationData data) {
		ExtentListener.createNode("Identification Card Information");
		try {
			/*
			 * new Documents_IdentificationCard(getDriver()).fillIdentificationCardDetails(
			 * "ID12345", "India", "01012026", "01012030",
			 * TestDataGenerator.randomEmployeePhotoFile());
			 */
			new Documents_IdentificationCard(getDriver()).fillIdentificationCardDetails(data.getIdNumber(),
					data.getCountry(), data.getStartDate(), data.getEndDate(),
					TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Identification Card Information completed");
			stepStatus.put("Identification Card", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Identification Card Information failed: " + e.getMessage());
			stepStatus.put("Identification Card", "FAIL");
		}
	}

	// ---------------- VISA OFFLINE ----------------
	@Test(dependsOnMethods = "identificationCardInformation", dataProvider = "StudentVisaOfflineData", dataProviderClass = VisaOfflineData.class)
	public void visaOfflineInformation(StudentVisaOfflineData data) {
		ExtentListener.createNode("Visa Offline Information");
		try {
			/*
			 * new Documents_VisaInfo_OffVisa(getDriver()).
			 * fillVisaInfoOffVisaForm("14 Month Single", "Home Country", "01012030",
			 * "01012031", "01012035", "01012035", "V12345", "India",
			 * TestDataGenerator.randomNotes(),
			 * TestDataGenerator.randomEmployeePhotoFile());
			 */
			new Documents_VisaInfo_OffVisa(getDriver()).fillVisaInfoOffVisaForm(data.getVisaType(),
					data.getHomeCountry(), data.getStartDate(), data.getEndDate(), data.getIssueDate(),
					data.getExpiryDate(), data.getVisaNumber(), data.getCountry(), data.getNotes(),
					TestDataGenerator.randomEmployeePhotoFile());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Visa Offline Information completed");
			stepStatus.put("Visa Offline", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Visa Offline Information failed: " + e.getMessage());
			stepStatus.put("Visa Offline", "FAIL");
		}
	}

	// ---------------- VISA ONLINE ----------------
	@Test(dependsOnMethods = "visaOfflineInformation", dataProvider = "StudentVisaOnlineData", dataProviderClass = VisaOnlineData.class)
	public void visaOnlineInformation(StudentVisaOnlineData data) {
		ExtentListener.createNode("Visa Online Information");
		try {
			/*
			 * new
			 * Documents_VisaInfo_OnVisa(getDriver()).fillOnlineVisaInfo("14 Month Single",
			 * "01012035", "01012038", "01012040", "OVNP1234");
			 */
			new Documents_VisaInfo_OnVisa(getDriver()).fillOnlineVisaInfo(data.getVisaType(), data.getStartDate(),
					data.getEndDate(), data.getExpiryDate(), data.getOnlineVisaNumber());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Visa Online Information completed");
			stepStatus.put("Visa Online", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Visa Online Information failed: " + e.getMessage());
			stepStatus.put("Visa Online", "FAIL");
		}
	}

	// ---------------- VISA REGISTER ----------------
	@Test(dependsOnMethods = "visaOnlineInformation", dataProvider = "StudentVisaRegisterData", dataProviderClass = VisaRegisterData.class)
	public void visaRegisterInformation(StudentVisaRegisterData data) {
		ExtentListener.createNode("Visa Register Information");
		try {
			/*
			 * new Documents_VisaInfo_Register(getDriver()).fillRegisterInfo("Home Country",
			 * "India", "01012026", TestDataGenerator.randomNotes());
			 */
			new Documents_VisaInfo_Register(getDriver()).fillRegisterInfo(data.getHomeCountry(), data.getCountry(),
					data.getRegisterDate(), data.getNotes());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Visa Register Information completed");
			stepStatus.put("Visa Register", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Visa Register Information failed: " + e.getMessage());
			stepStatus.put("Visa Register", "FAIL");
		}
	}

	// ---------------- PASSPORT LOCATION ----------------
	@Test(dependsOnMethods = "visaRegisterInformation", dataProvider = "StudentPassportLocationData", dataProviderClass = PassportLocationData.class)
	public void passportLocationInformation(StudentPassportLocationData data) {
		ExtentListener.createNode("Passport Location Information");
		try {
//			new Documents_VisaInfo_PassportLocation(getDriver()).fillPassportLocationInfo("Delhi", "01012026");
			new Documents_VisaInfo_PassportLocation(getDriver()).fillPassportLocationInfo(data.getLocation(),
					data.getDate());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Passport Location Information completed");
			stepStatus.put("Passport Location", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Passport Location Information failed: " + e.getMessage());
			stepStatus.put("Passport Location", "FAIL");
		}
	}

	// ---------------- PASSPORT ----------------
	@Test(dependsOnMethods = "passportLocationInformation", dataProvider = "StudentPassportData", dataProviderClass = PassportData.class)
	public void passportInformation(StudentPassportData data) {
		ExtentListener.createNode("Passport Information");
		try {
			/*
			 * new Documents_PassportInformation(getDriver()).fillPassportInformation(
			 * "AB123456", "Delhi", "01012026", "01012030");
			 */
			new Documents_PassportInformation(getDriver()).fillPassportInformation(data.getPassportNumber(),
					data.getPlaceOfIssue(), data.getIssueDate(), data.getExpiryDate());
			String pageText = TextUtility.getFullPageText(getDriver()); // Ensure all text is loaded before capturing screenshot
			System.out.println(pageText); // Debug: Print page text to verify content
			ExtentListener.getNode().pass("Passport Information completed");
			stepStatus.put("Passport Information", "PASS");
		} catch (Exception e) {
			ExtentListener.getNode().fail("Passport Information failed: " + e.getMessage());
			stepStatus.put("Passport Information", "FAIL");
		}
	}

	// ---------------- SUMMARY ----------------
	@AfterClass(alwaysRun = true)
	public void summarizeDocumentFlow() {
		System.out.println("==== Document Flow Status for Student ====");
		stepStatus.forEach((step, status) -> System.out.println(step + " : " + status));
		System.out.println("=================================================");
	}
}
