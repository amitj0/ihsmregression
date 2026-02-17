package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class BasicInfo_MedicalInforamtion_AtPoly extends BasePage {

	public BasicInfo_MedicalInforamtion_AtPoly(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#MadicalInfoId']")
	private WebElement medicalInfoTab;

	@FindBy(xpath = "//a[@href='#tab26']")
	private WebElement atPolyTab;

	@FindBy(xpath = "//div[@id='tab26']//input[@type='date' and @name='Date']")
	private WebElement atPolyDateField;

	@FindBy(xpath = "//div[@id='tab26']//input[@name='Type']")
	private WebElement atPolyTypeField;

	@FindBy(xpath = "//div[@id='tab26']//input[@type='file']")
	private WebElement atPolyUploadField;

	@FindBy(xpath = "//div[@id='tab26']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to interact with the web elements can be added here
	public void clickMedicalInfoTab() {
		blinkElement(medicalInfoTab);
		safeClick(medicalInfoTab);
	}

	public void clickAtPolyTab() {
		safeClick(atPolyTab);
	}

	public void enterAtPolyDate(String date) {
		atPolyDateField.sendKeys(date);
	}

	public void enterAtPolyType(String type) {
		atPolyTypeField.sendKeys(type);
	}

	public void uploadAtPolyFile(String filePath) {
		atPolyUploadField.sendKeys(filePath);
	}

	public void clickSaveButton() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Medical AtPoly Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void clickOkButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}
	
	public boolean isAtPolyInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill the AtPoly medical information form
	public BasicInfo_MedicalInformation_Insurance fillAtPolyMedicalInformation(String date, String type,
			String filePath) {
		clickMedicalInfoTab();
		clickAtPolyTab();
		enterAtPolyDate(date);
		enterAtPolyType(type);
		uploadAtPolyFile(filePath);
		clickSaveButton();
		clickOkButtonSuccessPopup();
		return new BasicInfo_MedicalInformation_Insurance(driver);
	}

}
