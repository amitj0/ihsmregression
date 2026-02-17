package com.ihsm.university.ihsmpageobjects.employee.documents;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Documents_Documents extends BasePage {

	public Documents_Documents(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@data-bs-target='#pills-contact5']")
	private WebElement documentsTab;

	@FindBy(xpath = "//div[@id='divbtnempDoc']//span")
	private WebElement addDocumentsBtn;

	@FindBy(xpath = "//div[@id='DOCUMENTSModel']//label[contains(normalize-space(),'Document Type')]//following-sibling::div[@class='input-group']")
	private WebElement documentTypeDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> documentTypeDropdownOptions;

	@FindBy(xpath = "//div[@id='DOCUMENTSModel']//textarea[@name='notes']")
	private WebElement notesField;

	@FindBy(xpath = "//div[@id='DOCUMENTSModel']//input[@type='file']")
	private WebElement uploadDocumentField;

	@FindBy(xpath = "//div[@id='DOCUMENTSModel']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveDocumentBtn;
	

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	
	private WebElement okButtonSuccessPopup;

	// methods to perform the action
	public void documentsTab() {
		safeClick(documentsTab);
	}

	public void addDocumentsBtn() {
		blinkElement(addDocumentsBtn);
		safeClick(addDocumentsBtn);
	}

	public void documentTypeDropdownField() {
		safeClick(documentTypeDropdownField);
	}

	public void documentTypeDropdownOptions(String docType) {
		safeClick(documentTypeDropdownField);
		for (WebElement option : documentTypeDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(docType)) {
				safeClick(option);
				return;
			}
		}
	}

	public void notesField(String notes) {
		notesField.sendKeys(notes);
	}

	public void uploadDocumentField(String filePath) {
		uploadDocumentField.sendKeys(filePath);
	}

	public void saveDocumentBtn() {
		blinkElement(saveDocumentBtn);
		try {
			captureScreenshot("Document Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveDocumentBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}
	
	public boolean isDocInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill document information
	public Documents_Passport fillDocumentInformation(String docType, String notes, String filePath) {
		documentsTab();
		addDocumentsBtn();
		documentTypeDropdownField();
		documentTypeDropdownOptions(docType);
		notesField(notes);
		uploadDocumentField(filePath);
		saveDocumentBtn();
		okButtonSuccessPopup();
		return new Documents_Passport(driver);
	}

}
