package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class BasicInfo_MedicalInformation_Insurance extends BasePage {

	public BasicInfo_MedicalInformation_Insurance(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#MadicalInfoId']")
	private WebElement medicalInfoTab;

	@FindBy(xpath = "//a[@href='#tab27']")
	private WebElement insuranceTab;

	@FindBy(xpath = "//div[@id='tab27']//input[@placeholder='Date From']")
	private WebElement insuranceDateFromField;

	@FindBy(xpath = "//div[@id='tab27']//input[@placeholder='Date To']")
	private WebElement insuranceDateToField;

	@FindBy(xpath = "//div[@id='tab27']//input[@type='file']")
	private WebElement insuranceUploadField;

	@FindBy(xpath = "//div[@id='tab27']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement insuranceSaveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to interact with the web elements can be added here
	public void clickMedicalInfoTab() {
		blinkElement(medicalInfoTab);
		safeClick(medicalInfoTab);
	}

	public void clickInsuranceTab() {
		safeClick(insuranceTab);
	}

	public void enterInsuranceFromDate(String fromDate) {
		enterDate(insuranceDateFromField, fromDate);
	}

	public void enterInsuranceToDate(String toDate) {
		enterDate(insuranceDateToField, toDate);
	}

	public void uploadInsuranceDocument(String filePath) {
		insuranceUploadField.sendKeys(filePath);
	}

	public void clickInsuranceSaveBtn() {
		blinkElement(insuranceSaveBtn);
		try {
			captureScreenshot("Medical Insurance Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(insuranceSaveBtn);
		handleAlertIfPresent();
	}

	public void clickOkButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}
	
	public boolean isInsuranceInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill the insurance information
	public BasicInfo_MedicalInformation_Disability fillInsuranceInformation(String fromDate, String toDate,
			String filePath) {
		clickMedicalInfoTab();
		clickInsuranceTab();
		enterInsuranceFromDate(fromDate);
		enterInsuranceToDate(toDate);
		uploadInsuranceDocument(filePath);
		clickInsuranceSaveBtn();
		clickOkButtonSuccessPopup();
		return new BasicInfo_MedicalInformation_Disability(driver);
	}

}
