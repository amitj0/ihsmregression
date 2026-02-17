package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class ProfInfo_ProfessionalInfoDegreeLvl extends BasePage {

	public ProfInfo_ProfessionalInfoDegreeLvl(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@data-bs-target=\"#pills-position\"]")
	private WebElement profInfoTab;

	@FindBy(xpath = "//span[@data-bs-target=\"#ProfessionalInformationid\"]")
	private WebElement profInfoTabBtn;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//ng-select[@name=\"degree\"]")
	private WebElement degreeField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> degreeFieldList;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//label[contains(normalize-space(),'Degree')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement degreeFieldPlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@name='strMasterName']")
	private WebElement addValueField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveBtnAddValue;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//ng-select[@name=\"SPHERE\"]")
	private WebElement sphereField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> sphereFieldList;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//label[contains(normalize-space(),'Degree')]/following::span[contains(@class,'addvalue')])[2]")
	private WebElement sphereFieldPlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@name='strMasterName']")
	private WebElement addSphereValue;
	
	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okSuccessPop;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//input[@name=\"organizationtitle\"]")
	private WebElement orgField;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//input[@name=\"validation\"]")
	private WebElement diplomaNoField;

	@FindBy(name = "dttProtectionDate")
	private WebElement protectionDate;

	@FindBy(name = "dttFromLevelDate")
	private WebElement degreeDate;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//textarea[@name='notes'])[1]")
	private WebElement notesField;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//button[contains(@class, 'btnprimary') and text()='Save'])[1]")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	// method to perform the action here

	public void profInfoTab() {
		safeClick(profInfoTab);
	}

	public void profInfoTabBtn() {
		blinkElement(profInfoTabBtn);
		safeClick(profInfoTabBtn);
	}

	public void degreeField() {
		safeClick(degreeField);
	}

	public void selectDegreeField(String degree) {

		// Open dropdown
		safeClick(degreeField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : degreeFieldList) {
			if (option.getText().trim().equalsIgnoreCase(degree)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → add new Degree Type
		if (!found) {

			// Click +
			safeClick(degreeFieldPlusBtn);

			// Enter guardian type
			safeClick(addValueField);
			addValueField.sendKeys(degree);

			// Save
			safeClick(saveBtnAddValue);

			// Ok
			safeClick(okSuccessPop);

			// Reopen dropdown
			safeClick(degreeField);

			// Select newly added value
			for (WebElement option : degreeFieldList) {
				if (option.getText().trim().equalsIgnoreCase(degree)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Degree Type value not found even after adding: " + degree);
		}
	}

	public void sphereField() {
		safeClick(sphereField);
	}

	public void sphereFieldList(String sphere) {

		safeClick(sphereField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : sphereFieldList) {
			if (option.getText().trim().equalsIgnoreCase(sphere)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → add new Degree Type
		if (!found) {

			// Click +
			safeClick(sphereFieldPlusBtn);

			// Enter guardian type
			safeClick(addSphereValue);
			addSphereValue.sendKeys(sphere);

			// Save
			safeClick(saveBtnAddValue);

			// Ok
			safeClick(okSuccessPop);

			// Reopen dropdown
			safeClick(sphereField);

			// Select newly added value
			for (WebElement option : sphereFieldList) {
				if (option.getText().trim().equalsIgnoreCase(sphere)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Sphere Type value not found even after adding: " + sphere);
		}

	}

	public void orgField(String value) {
		orgField.sendKeys(value);
	}

	public void diplomaNoField(String value) {
		diplomaNoField.sendKeys(value);
	}

	public void protectionDate(String date) {
		enterDate(protectionDate, date);
	}

	public void degreeDate(String date) {
		enterDate(degreeDate, date);
	}

	public void notesField(String notes) {
		notesField.sendKeys(notes);
	}

	public void saveBtn() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Professional Degree Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void okButton() {
		blinkElement(okButton);
		handleModalOk(okButton);
	}
	
	public boolean isDegLvlInfoSavedSuccessfully() {
		return okButton.isDisplayed();
	}

	// fill the degree information here
	public void fillProfessionalInformationForm(String degree, String sphere, String org, String diplomaNo,
			String protectionDt, String degreeDt, String notes) {
		profInfoTab();
		profInfoTabBtn();
		degreeField();
		selectDegreeField(degree);
		sphereField();
		sphereFieldList(sphere);
		orgField(org);
		diplomaNoField(diplomaNo);
		protectionDate(protectionDt);
		degreeDate(degreeDt);
		notesField(notes);
		saveBtn();
		okButton();

		// return new instance
	}

}
