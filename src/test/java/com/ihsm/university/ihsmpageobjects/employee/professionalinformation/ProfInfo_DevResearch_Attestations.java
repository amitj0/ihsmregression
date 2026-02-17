package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class ProfInfo_DevResearch_Attestations extends BasePage {
	public ProfInfo_DevResearch_Attestations(WebDriver driver) {
		super(driver);
	}

	// locate the web elements here

	@FindBy(xpath = "//div[@id='BTNDIVDEVELOPMENT']//span")
	private WebElement devResearchAddBtn;

	@FindBy(xpath = "//a[@href='#tab128']")
	private WebElement rewardsSubTab;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//ng-select[@name='intCorrespondsUniversityRules']")
	private WebElement attestationUniversityRulesDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> attestationUniversityRulesDropdownOptions;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//ng-select[@name='intCommunityCompitition']")
	private WebElement communityCompetitionDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> communityCompetitionDropdownOptions;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//ng-select[@name='intPersonalityPossibility']")
	private WebElement attestationPersonalityDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> attestationPersonalityDropdownOptions;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//ng-select[@name='intProfessionalCompitition']")
	private WebElement attestationProfessionalDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> attestationProfessionalDropdownOptions;

	@FindBy(name = "strValidationDate")
	private WebElement attestationValidationDateField;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Notes')]//following-sibling::div//textarea[@id='exampleFormControlTextarea1'])[4]")
	private WebElement notesField;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//button[contains(@class, 'btnprimary') and text()='Save'])[4]")
	private WebElement saveAttestationsBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonAttestations;

	// methods to perform the action

	public void addDevResearchAttestations() {
		blinkElement(devResearchAddBtn);
		safeClick(devResearchAddBtn);
	}

	public void clickAttestationsSubTab() {
		safeClick(rewardsSubTab);
	}

	public void selectUniversityRulesDrop() {
		safeClick(attestationUniversityRulesDropdownField);
	}

	public void selectUniversityRulesDropList(String type) {
		safeClick(attestationUniversityRulesDropdownField);

		for (WebElement option : attestationUniversityRulesDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(type)) {
				safeClick(option);
				return;
			}
		}
	}

	public void communityCompetitionDropdownField() {
		safeClick(communityCompetitionDropdownField);
	}

	public void communityCompetitionDropdownOptions(String type) {
		safeClick(communityCompetitionDropdownField);

		for (WebElement option : communityCompetitionDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(type)) {
				safeClick(option);
				return;
			}
		}
	}

	public void attestationPersonalityDropdownField() {
		safeClick(attestationPersonalityDropdownField);
	}

	public void attestationPersonalityDropdownOptions(String type) {
		safeClick(attestationPersonalityDropdownField);

		for (WebElement option : attestationPersonalityDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(type)) {
				safeClick(option);
				return;
			}
		}
	}

	public void attestationProfessionalDropdownField() {
		safeClick(attestationProfessionalDropdownField);
	}

	public void attestationProfessionalDropdownOptions(String type) {
		safeClick(attestationProfessionalDropdownField);

		for (WebElement option : attestationProfessionalDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(type)) {
				safeClick(option);
				return;
			}
		}
	}

	public void attestationValidationDateField(String date) {
		enterDate(attestationValidationDateField, date);
	}

	public void notesField(String notes) {
		notesField.sendKeys(notes);
	}

	public void saveAttestationsBtn() {
		blinkElement(saveAttestationsBtn);
		try {
			captureScreenshot("Attestations Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveAttestationsBtn);
		handleAlertIfPresent();
	}

	public void okButtonAttestations() {
		blinkElement(okButtonAttestations);
		handleModalOk(okButtonAttestations);
	}
	
	public boolean isAttestationsSavedSuccessfully() {
		return okButtonAttestations.isDisplayed();
	}

	// fill the Attestations form
	public void fillAttestationsForm(String universityRules, String communityCompetition, String personality,
			String professional, String validationDate, String notes) {
		addDevResearchAttestations();
		clickAttestationsSubTab();
		selectUniversityRulesDrop();
		communityCompetitionDropdownField();
		selectUniversityRulesDropList(universityRules);
		communityCompetitionDropdownOptions(communityCompetition);
		attestationPersonalityDropdownField();
		attestationPersonalityDropdownOptions(personality);
		attestationProfessionalDropdownField();
		attestationProfessionalDropdownOptions(professional);
		attestationValidationDateField(validationDate);
		notesField(notes);
		saveAttestationsBtn();
		okButtonAttestations();
	}

}
