package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;
import com.ihsm.university.ihsmpageobjects.student.documents.Documents_OtherDocuments;

public class BasicInfo_MedicalInformation_Disability extends BasePage {

	public BasicInfo_MedicalInformation_Disability(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#MadicalInfoId']")
	private WebElement medicalInfoTab;

	@FindBy(xpath = "//a[@href='#tab55']")
	private WebElement disabilityTab;

	@FindBy(xpath = "//div[@id='tab55']//ng-select[@name='TYPE']")
	private WebElement disabilityTypeField;

	@FindBy(xpath = "//ng-select[@name='TYPE']//div[@role='option']")
	private List<WebElement> disabilityTypeFieldList;

	@FindBy(xpath = "(//div[@id='MadicalInfoId']//label[contains(normalize-space(),'Type')]/following::span[contains(@class,'addvalue')])[3]")
	private WebElement addDisabilityTypePlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//input[@name='ENTER_VALUE']")
	private WebElement disabilityTypeInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveDisabilityTypeBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okBtntonDisabilityTypeSuccessPopup;

	@FindBy(xpath = "//div[@id='tab55']//input[@name='DOCUMENT_NO']")
	private WebElement disabilityDocumentNoField;

	@FindBy(xpath = "//div[@id='tab55']//input[@name='FROM']")
	private WebElement dateOfDocumentField;

	@FindBy(xpath = "//div[@id='tab55']//input[@type='file']")
	private WebElement disabilityUploadField;

	@FindBy(xpath = "//div[@id='tab55']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement disabilitySaveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to perform the action
	public void medicalInfoTab() {
		blinkElement(medicalInfoTab);
		safeClick(medicalInfoTab);
	}

	public void disabilityTab() {
		safeClick(disabilityTab);
	}

	public void disabilityTypeField() {
		safeClick(disabilityTypeField);
	}

	public void disabilityTypeFieldList(String disabilityType) {
		safeClick(disabilityTypeField);

		boolean typeFound = false;
		for (WebElement option : disabilityTypeFieldList) {
			if (option.getText().trim().equalsIgnoreCase(disabilityType)) {
				safeClick(option);
				typeFound = true;
				break;
			}
		}

		// If the type is not found, add it using the plus button
		if (!typeFound) {
			safeClick(addDisabilityTypePlusBtn);
			safeClick(disabilityTypeInputField);
			disabilityTypeInputField.sendKeys(disabilityType);
			safeClick(saveDisabilityTypeBtn);
			safeClick(okBtntonDisabilityTypeSuccessPopup);
			// After adding, select the newly added type
			safeClick(disabilityTypeField);
			for (WebElement option : disabilityTypeFieldList) {
				if (option.getText().trim().equalsIgnoreCase(disabilityType)) {
					safeClick(option);
					return;
				}
			}
		}
	}

	public void disabilityDocumentNoField(String documentNo) {
		safeClick(disabilityDocumentNoField);
		disabilityDocumentNoField.sendKeys(documentNo);
	}

	public void disabilityStartDateField(String startDate) {
		safeClick(dateOfDocumentField);
		dateOfDocumentField.sendKeys(startDate);
	}

	public void disabilityUploadField(String filepath) {
		disabilityUploadField.sendKeys(filepath);
	}

	public void disabilitySaveBtn() {
		blinkElement(disabilitySaveBtn);
		try {
			captureScreenshot("Medical Disability Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(disabilitySaveBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}
	
	public boolean isDisabilitySavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill the disability form
	public Documents_OtherDocuments fillDisabilityForm(String disabilityType, String documentNo, String startDate,
			String filepath) {
		medicalInfoTab();
		disabilityTab();
		disabilityTypeField();
		disabilityTypeFieldList(disabilityType);
		disabilityDocumentNoField(documentNo);
		disabilityStartDateField(startDate);
		disabilityUploadField(filepath);
		disabilitySaveBtn();
		okButtonSuccessPopup();
		return new Documents_OtherDocuments(driver);
	}

}
