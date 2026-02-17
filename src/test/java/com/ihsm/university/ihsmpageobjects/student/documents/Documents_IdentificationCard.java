package com.ihsm.university.ihsmpageobjects.student.documents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Documents_IdentificationCard extends BasePage {

	public Documents_IdentificationCard(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#identificationInfo']")
	private WebElement identificationTab;

	@FindBy(xpath = "//div[@id='identificationInfo']//div[contains(@class,'input-group')]//input[@name='IDENTIFICATION']")
	private WebElement idNumberField;

	@FindBy(xpath = "//div[@id='identificationInfo']//label[contains(text(),'Issue Place')]/following-sibling::div//input")
	private WebElement issuePlaceField;

	@FindBy(xpath = "(//div[@class='modal-content']//div[label[contains(., 'Issue Date')]]//input)[1]")
	private WebElement issueDateField;

	@FindBy(xpath = "(//div[@class='modal-content']//div[label[contains(., 'Expiry Date')]]//input)[1]")
	private WebElement expiryDateField;

	@FindBy(xpath = "(//div[contains(.,'Drag') or contains(.,'upload') or contains(@class,'drop')]//input[@type='file'])[2]")
	private WebElement fileUploadField;

	@FindBy(xpath = "//div[@id='identificationInfo']//div[@class='modal-footer']//button[contains(text(), 'Save')]")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	// methods to perform actions on the web elements can be added here
	public void clickIdentificationTab() {
		blinkElement(identificationTab);
		safeClick(identificationTab);
	}

	public void enterIDNumber(String idNumber) {
		safeClick(idNumberField);
		idNumberField.sendKeys(idNumber);
	}

	public void enterIssuePlace(String place) {
		issuePlaceField.sendKeys(place);
	}

	public void enterIssueDate(String issueDate) {
		issueDateField.sendKeys(issueDate);
	}

	public void enterExpiryDate(String expiryDate) {
		expiryDateField.sendKeys(expiryDate);
	}

	public void uploadIdentificationCardDocument(String filePath) {

		fileUploadField.sendKeys(filePath);
	}

	public void clickSaveButton() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Document Identification Card Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void clickOkButton() {
		blinkElement(okButton);
		handleModalOk(okButton);
	}
	
	

	public boolean isIdentificationCardDetailsSavedSuccessfully() {
		return okButton.isDisplayed();
	}
	
	// fill identification card details
	public Documents_VisaInfo_OffVisa fillIdentificationCardDetails(String idNumber, String issuePlace, String issueDate, String expiryDate,
			String filePath) {
		clickIdentificationTab();
		enterIDNumber(idNumber);
		enterIssuePlace(issuePlace);
		enterIssueDate(issueDate);
		enterExpiryDate(expiryDate);
		uploadIdentificationCardDocument(filePath);
		clickSaveButton();
		clickOkButton();
		
		return new Documents_VisaInfo_OffVisa(driver);
	}

}
