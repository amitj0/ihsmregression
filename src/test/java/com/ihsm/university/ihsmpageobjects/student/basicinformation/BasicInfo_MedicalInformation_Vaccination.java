package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class BasicInfo_MedicalInformation_Vaccination extends BasePage {

	public BasicInfo_MedicalInformation_Vaccination(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#MadicalInfoId']")
	private WebElement medicalInfoTab;

	@FindBy(xpath = "//a[@href='#tab25']")
	private WebElement vaccinationTab;

	@FindBy(xpath = "//div[@id='MadicalInfoId']//ng-select[@name='VACCINATIONTYPE']")
	private WebElement vaccChooseField;

	@FindBy(xpath = "//div[@id='MadicalInfoId']//div[@role='option']")
	private List<WebElement> vaccChooseFieldList;

	@FindBy(xpath = "(//div[@id='MadicalInfoId']//label[contains(normalize-space(),'Vaccination Type')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addVaccPlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//input[@name='ENTER_VALUE']")
	private WebElement vaccInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveVaccBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okVaccBtn;

	// vaccination dose

	@FindBy(xpath = "//div[@id='MadicalInfoId']//ng-select[@name='GUARDIAN']")
	private WebElement vaccDoseField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> vaccDoseFieldList;

	@FindBy(xpath = "(//div[@id='MadicalInfoId']//label[contains(normalize-space(),'Dose')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addDosePlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//input[@name='ENTER_VALUE']")
	private WebElement doseInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveDoseBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okDoseBtn;

	@FindBy(xpath = "//div[@id='tab25']//input[@type='date' and @name='DATE']")
	private WebElement dobField;

	@FindBy(xpath = "//div[@id='tab25']//input[@name='CERTIFICATENO']")
	private WebElement certificateField;

	@FindBy(xpath = "//div[@id='tab25']//textarea[@placeholder='Comments']")
	private WebElement commentField;

	@FindBy(xpath = "//div[@id='tab25']//input[@type='file']")
	private WebElement uploadFieldVacc;

	@FindBy(xpath = "//div[@id='tab25']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okBtn;

	// methods to perform the action
	public void medicalInfoTab() {
		blinkElement(medicalInfoTab);
		safeClick(medicalInfoTab);
	}

	public void vaccinationTab() {
		safeClick(vaccinationTab);
	}

	public void vaccChooseField() {
		safeClick(vaccChooseField);
	}

	public void vaccChooseFieldList(String vaccType) {
		safeClick(vaccChooseField);
		boolean found = false;
		for (WebElement option : vaccChooseFieldList) {
			if (option.getText().trim().equalsIgnoreCase(vaccType)) {
				option.click();
				found = true;
				break;
			}
		}

		if (!found) {
			safeClick(addVaccPlusBtn);

			safeClick(vaccInputField);
			vaccInputField.sendKeys(vaccType);

			blinkElement(saveVaccBtn);
			safeClick(saveVaccBtn);
			safeClick(okVaccBtn);
		}
	}

	public void vaccDoseField() {
		safeClick(vaccDoseField);
	}

	public void vaccDoseFieldList(String vaccDose) {
		safeClick(vaccDoseField);
		boolean found = false;
		for (WebElement option : vaccDoseFieldList) {
			if (option.getText().trim().equalsIgnoreCase(vaccDose)) {
				option.click();
				found = true;
				break;
			}
		}

		if (!found) {
			safeClick(addDosePlusBtn);
			safeClick(doseInputField);
			doseInputField.sendKeys(vaccDose);
			blinkElement(saveDoseBtn);
			safeClick(saveDoseBtn);
			safeClick(okDoseBtn);
		}
	}

	public void dobField(String dob) {
		dobField.clear();
		dobField.sendKeys(dob);
	}

	public void certificateField(String certNo) {
		certificateField.clear();
		certificateField.sendKeys(certNo);
	}

	public void commentField(String comment) {
		commentField.clear();
		commentField.sendKeys(comment);
	}

	public void uploadFieldVacc(String filePath) {
		uploadFieldVacc.sendKeys(filePath);
	}

	public void saveBtn() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Medical Vaccination Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void okBtn() {
		blinkElement(okBtn);
		handleModalOk(okBtn);
	}

	public boolean isVaccinationInfoSavedSuccessfully() {
		return okBtn.isDisplayed();
	}

	// fill the Vaccination Information

	public BasicInfo_MedicalInforamtion_AtPoly fillVaccinationInfo(String vaccType, String vaccDose, String dob,
			String certNo, String comment, String filePath) {
		medicalInfoTab();
		vaccinationTab();
		vaccChooseField();
		vaccChooseFieldList(vaccType);
		vaccDoseField();
		vaccDoseFieldList(vaccDose);
		dobField(dob);
		certificateField(certNo);
		commentField(comment);
		uploadFieldVacc(filePath);
		saveBtn();
		okBtn();
		return new BasicInfo_MedicalInforamtion_AtPoly(driver);
	}

}
