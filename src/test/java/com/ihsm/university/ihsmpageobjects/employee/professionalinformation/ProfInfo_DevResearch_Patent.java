package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class ProfInfo_DevResearch_Patent extends BasePage {
	public ProfInfo_DevResearch_Patent(WebDriver driver) {
		super(driver);
	}

	// locate the web elements here
	@FindBy(xpath = "//div[@id='BTNDIVDEVELOPMENT']//span")
	private WebElement devResearchAddBtn;

	@FindBy(xpath = "//a[@href='#tab127']")
	private WebElement rewardsSubTab;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//ng-select[@name='selectPatent']")
	private WebElement patentTypeField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> patentTypeOptions;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Type')]/following::span[contains(@class,'addvalue')])[3]")
	private WebElement addPatentTypePlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@type='text']")
	private WebElement addPatentTypeInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement savePatentTypeBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	@FindBy(name = "strInvention")
	private WebElement inventionTitleField;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//ng-select[@name='selectPatentLevel']")
	private WebElement patentLevelDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> patentLevelDropdownOptions;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Type')]/following::span[contains(@class,'addvalue')])[4]")
	private WebElement addPatentLevelPlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@type='text']")
	private WebElement patentLevelInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement savePatentLevelBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup2;

	@FindBy(name = "strPatentAuthors")
	private WebElement patentAuthorsField;

	@FindBy(name = "strPatentOrganization")
	private WebElement patentOrganizationField;

	@FindBy(name = "dttPatentDate")
	private WebElement patentDateField;

	@FindBy(name = "strRegistrationNumber")
	private WebElement regNoteNumberField;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Notes')]//following-sibling::div//textarea[@id='exampleFormControlTextarea1'])[3]")
	private WebElement notesField;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//button[contains(@class, 'btnprimary') and text()='Save'])[3]")
	private WebElement savePatentBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonPatent;

	// methods to perform the action

	public void addDevResearchPatent() {
		blinkElement(devResearchAddBtn);
		safeClick(devResearchAddBtn);
	}

	public void clickPatentSubTab() {
		safeClick(rewardsSubTab);
	}

	public void selectPatentTypeField() {
		safeClick(patentTypeField);
	}

	public void selectPatentTypeOptions(String type) {
		safeClick(patentTypeField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : patentTypeOptions) {
			if (option.getText().trim().equalsIgnoreCase(type)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		if (!found) {
			// Add new value
			safeClick(addPatentTypePlusBtn);
			safeClick(addPatentTypeInputField);
			addPatentTypeInputField.sendKeys(type);
			safeClick(savePatentTypeBtn);
			safeClick(okButtonSuccessPopup);
			// Select the newly added value
			safeClick(patentTypeField);
			for (WebElement option : patentTypeOptions) {
				if (option.getText().trim().equalsIgnoreCase(type)) {
					safeClick(option);
					return;
				}
			}
		}
	}

	public void inventionTitleField(String inventionTitle) {
		inventionTitleField.sendKeys(inventionTitle);
	}

	public void patentLevelDropdownField() {
		safeClick(patentLevelDropdownField);
	}

	public void patentLevelDropdownOptions(String patentLevel) {
		safeClick(patentLevelDropdownField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : patentLevelDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(patentLevel)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		if (!found) {
			// Add new value
			safeClick(addPatentLevelPlusBtn);
			safeClick(patentLevelInputField);
			patentLevelInputField.sendKeys(patentLevel);
			safeClick(savePatentLevelBtn);
			safeClick(okButtonSuccessPopup2);
			// Select the newly added value
			safeClick(patentLevelDropdownField);
			for (WebElement option : patentLevelDropdownOptions) {
				if (option.getText().trim().equalsIgnoreCase(patentLevel)) {
					safeClick(option);
					return;
				}
			}
		}
	}

	public void patentAuthorsField(String patentAuthors) {
		patentAuthorsField.sendKeys(patentAuthors);
	}

	public void patentOrganizationField(String patentOrganization) {
		patentOrganizationField.sendKeys(patentOrganization);
	}

	public void patentDateField(String patentDate) {
		enterDate(patentDateField, patentDate);
	}

	public void regNoteNumberField(String regNoteNumber) {
		regNoteNumberField.sendKeys(regNoteNumber);
	}

	public void notesField(String notes) {
		notesField.sendKeys(notes);
	}

	public void savePatentBtn() {
		blinkElement(savePatentBtn);
		try {
			captureScreenshot("Patent Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(savePatentBtn);
		handleAlertIfPresent();
	}

	public void okButtonPatent() {
		blinkElement(okButtonPatent);
		handleModalOk(okButtonPatent);
	}

	public boolean isPatentFormDisplayed() {
		return okButtonPatent.isDisplayed();
	}

	// fillDevResearchPatentForm method to fill the form in one go
	public void fillDevResearchPatentForm(String patentType, String inventionTitle, String patentLevel,
			String patentAuthors, String patentOrganization, String patentDate, String regNoteNumber, String notes) {
		addDevResearchPatent();
		clickPatentSubTab();
		selectPatentTypeField();
		selectPatentTypeOptions(patentType);
		inventionTitleField(inventionTitle);
		patentLevelDropdownField();
		patentLevelDropdownOptions(patentLevel);
		patentAuthorsField(patentAuthors);
		patentOrganizationField(patentOrganization);
		patentDateField(patentDate);
		regNoteNumberField(regNoteNumber);
		notesField(notes);
		savePatentBtn();
		okButtonPatent();
	}
}
