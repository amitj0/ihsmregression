package com.ihsm.university.ihsmpageobjects.student.documents;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Documents_OtherDocuments extends BasePage {

	public Documents_OtherDocuments(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@data-bs-target='#pills-documents']")
	private WebElement documentTab;

	@FindBy(xpath = "//span[@data-bs-target='#DOCUMENTS']")
	private WebElement otherDocTab;

	@FindBy(xpath = "//div[@id='DOCUMENTS']//ng-select[@name='DOCUMENTTYPE']")
	private WebElement documentField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> documentFieldList;

	@FindBy(xpath = "//div[@id='DOCUMENTS']//input[@type='file']")
	private WebElement fileUploadField;

	@FindBy(xpath = "//div[@id='DOCUMENTS']//ancestor::div[contains(@class,'modal-content')]//button[normalize-space(text())='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;
	
	// methods to perform actions on the web elements can be added here
	public void clickDocumentTab() {
		safeClick(documentTab);
	}
	public void clickOtherDocTab() {
		blinkElement(otherDocTab);
		safeClick(otherDocTab);
	}
	public void selectDocumentType(String documentType) {
		safeClick(documentField);
		for (WebElement option : documentFieldList) {
			if (option.getText().trim().equalsIgnoreCase(documentType)) {
				safeClick(option);
				return;
			}
		}
	}
	public void uploadDocumentFile(String filePath) {
		
		fileUploadField.sendKeys(filePath);
	}
	public void clickSaveButton() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Documents Other Doc Information Saved");
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
	
	public boolean isOtherDocumentSavedSuccessfully() {
		return okButton.isDisplayed();
	}
	
	// fill other documents form
	public Documents_IdentificationCard fillOtherDocumentsForm(String documentType, String filePath) {
		clickDocumentTab();
		clickOtherDocTab();
		selectDocumentType(documentType);
		uploadDocumentFile(filePath);
		clickSaveButton();
		clickOkButton();
		return new Documents_IdentificationCard(driver);
		
	}
	
	
	
	
}
