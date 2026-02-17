package com.ihsm.university.ihsmpageobjects.employee.basicinformation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class BasicInfo_LanguageInformation extends BasePage {

	public BasicInfo_LanguageInformation(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@id='divbtnlanguages']//span")
	private WebElement addLanguageInfoBtn;

	@FindBy(xpath = "(//div[@id='AddForeignLanguageModal']//ng-select[@name='Langguage'])[1]")
	private WebElement languageDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> languageDropdownOptions;

	@FindBy(xpath = "(//div[@id='AddForeignLanguageModal']//ng-select[@name='Langguage'])[2]")
	private WebElement proficiencyDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> proficiencyDropdownOptions;

	@FindBy(xpath = "//div[@id='AddForeignLanguageModal']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveLanguageInfoBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to perform the action
	public void addLanguageInfoBtn() {
		blinkElement(addLanguageInfoBtn);
		safeClick(addLanguageInfoBtn);
	}

	public void languageDropdownField() {
		safeClick(languageDropdownField);
	}

	public void languageDropdownOptions(String language) {
		safeClick(languageDropdownField);
		for (WebElement option : languageDropdownOptions) {
			if (option.getText().equalsIgnoreCase(language)) {
				safeClick(option);
				return;
			}
		}
	}

	public void proficiencyDropdownField() {
		safeClick(proficiencyDropdownField);
	}

	public void proficiencyDropdownOptions(String proficiency) {
		safeClick(proficiencyDropdownField);
		for (WebElement option : proficiencyDropdownOptions) {
			if (option.getText().equalsIgnoreCase(proficiency)) {
				safeClick(option);
				return;
			}
		}
	}

	public void saveLanguageInfoBtn() {
		blinkElement(saveLanguageInfoBtn);
		try {
			captureScreenshot("Language Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}

		safeClick(saveLanguageInfoBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}

	public boolean isLangInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill language information
	public BasicInfo_VaccinationInformation fillLanguageInformation(String language, String proficiency) {
		addLanguageInfoBtn();
		languageDropdownField();
		languageDropdownOptions(language);
		proficiencyDropdownField();
		proficiencyDropdownOptions(proficiency);
		saveLanguageInfoBtn();
		okButtonSuccessPopup();
		return new BasicInfo_VaccinationInformation(driver);
	}

}
