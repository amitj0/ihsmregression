package com.ihsm.university.ihsmpageobjects.employee.basicinformation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class BasicInfo_GuardianInformation extends BasePage {

	public BasicInfo_GuardianInformation(WebDriver driver) {
		super(driver);
	}

	// locate the web elements here
	@FindBy(xpath = "//div[@id='divbtnfamliyinfo']//span")
	private WebElement addGuardianInfoBtn;

	@FindBy(xpath = "//div[@id='FamilyInformationModelID']//ng-select[@name='GUARDIAN']")
	private WebElement guardianTypeDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> guardianTypeDropdownOptions;

	@FindBy(xpath = "//div[@id='FamilyInformationModelID']//label[contains(normalize-space(),'Select Guardian')]/following::span[contains(@class,'addvalue')][1]")
	private WebElement addGuardianTypePlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//input[@name='strMasterName']")
	private WebElement guardianTypeInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveGuardianTypeButton;

	@FindBy(xpath = "//div[@id='FamilyInformationModelID']//input[@name='FULLNAME']")
	private WebElement guardianFullNameField;

	@FindBy(xpath = "//div[@id='FamilyInformationModelID']//input[@type='date' and @name='DOB']")
	private WebElement guardianDobField;

	@FindBy(name = "DISABILITY")
	private WebElement guardianDisabilityField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> guardianDisabilityOptions;

	@FindBy(xpath = "//div[@id='FamilyInformationModelID']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveGuardianInfoBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to perform the action

	public void addGuardianInfoBtn() {
		blinkElement(addGuardianInfoBtn);
		safeClick(addGuardianInfoBtn);
	}

	public void guardianTypeDropdownField() {
		safeClick(guardianTypeDropdownField);
	}

	public void selectGuardianTypeOption(String guardianType) {
		selectNgDropdownValue(guardianTypeDropdownField, // dropdown element
				guardianType, // value to select
				addGuardianTypePlusButton, // "+" button
				guardianTypeInputField, // input for new value
				saveGuardianTypeButton, // save button
				okButtonSuccessPopup // OK button on success modal
		);
	}

	public void guardianFullNameField(String guardianFullName) {
		safeClick(guardianFullNameField);
		guardianFullNameField.sendKeys(guardianFullName);
	}

	public void guardianDobField(String guardianDob) {
		safeClick(guardianDobField);
		guardianDobField.sendKeys(guardianDob);
	}

	public void guardianDisabilityField() {
		safeClick(guardianDisabilityField);
	}

	public void guardianDisabilityOptions(String guardianDisability) {
		safeClick(guardianDisabilityField);

		for (WebElement option : guardianDisabilityOptions) {
			if (option.getText().trim().equalsIgnoreCase(guardianDisability)) {
				safeClick(option);
				return;
			}
		}
	}

	public void saveGuardianInfoBtn() {
		blinkElement(saveGuardianInfoBtn);
		try {
			captureScreenshot("Guardian InfoFormation Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveGuardianInfoBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}

	public boolean isGuardianInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill the guardian information form
	public BasicInfo_LanguageInformation fillGuardianInformationForm(String guardianType, String guardianFullName,
			String guardianDob, String guardianDisability) {
		addGuardianInfoBtn();
		selectGuardianTypeOption(guardianType);
		guardianFullNameField(guardianFullName);
		guardianDobField(guardianDob);
		guardianDisabilityOptions(guardianDisability);
		saveGuardianInfoBtn();
		okButtonSuccessPopup();
		return new BasicInfo_LanguageInformation(driver);

	}

}
