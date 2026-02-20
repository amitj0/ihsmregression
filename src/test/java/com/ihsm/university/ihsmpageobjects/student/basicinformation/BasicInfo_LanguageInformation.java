package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;
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
	@FindBy(xpath = "//span[@data-bs-target='#languageid']")
	private WebElement addLangBtn;

	@FindBy(xpath = "(//div[@id='languageid']//ng-select[@name='VACCINATIONTYPE']//div[@class='ng-input'])[1]")
	private WebElement langField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> langFieldList;

	@FindBy(xpath = "(//div[@id='languageid']//label[contains(normalize-space(),'Type')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addLangTypeBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//input[@name='ENTER_VALUE']")
	private WebElement langTypeInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//button[contains(@class,'btnprimary') and normalize-space()='Save']")
	private WebElement saveLangTypeBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement langTypeSaveOkBtn;

	@FindBy(xpath = "//label[text()=' Level']/following-sibling::div//div[contains(@class,'ng-select-container')]")
	private WebElement langLvlField;
	
	@FindBy(xpath = "//label[text()=' LEVEL']/following-sibling::div//div[contains(@class,'ng-select-container')]")
	private WebElement langLvlField2;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> langLvlFieldList;

	@FindBy(xpath = "(//div[@id='languageid']//label[contains(normalize-space(),'Type')]/following::span[contains(@class,'addvalue')])[2]")
	private WebElement addLangLevelBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//input[@name='ENTER_VALUE']")
	private WebElement langLevelInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//button[contains(@class,'btnprimary') and normalize-space()='Save']")
	private WebElement saveLangLevelBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement langLevelSaveOkBtn;

	@FindBy(xpath = "//h5[normalize-space()='Language']/ancestor::div[@class='modal-content']//button[normalize-space()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement saveOkBtn;

	// methods to perform the action
	public void addLangBtn() {
		blinkElement(addLangBtn);
		safeClick(addLangBtn);
	}

//	public void langField() {
//		safeClick(langField);
//	}

	public void langFieldList(String language) {
		safeClick(langField);

		boolean found = false;

		for (WebElement option : langFieldList) {
			if (option.getText().trim().equalsIgnoreCase(language)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If the language is not found, add it using the add button
		if (!found) {
			blinkElement(addLangTypeBtn);
			safeClick(addLangTypeBtn);
			safeClick(langTypeInputField);
			langTypeInputField.sendKeys(language);
			blinkElement(saveLangTypeBtn);
			safeClick(saveLangTypeBtn);
			blinkElement(langTypeSaveOkBtn);
			handleModalOk(langTypeSaveOkBtn);

			// After adding, select the newly added language
			safeClick(langField);
			for (WebElement option : langFieldList) {
				if (option.getText().trim().equalsIgnoreCase(language)) {
					safeClick(option);
					break;
				}
			}
		}
	}

	public void langLvlField() {
		safeClick(langLvlField);
	}

	public void langLvlFieldList(String level) {
		safeClick(langLvlField);

		boolean found = false;
		for (WebElement option : langLvlFieldList) {
			if (option.getText().trim().equalsIgnoreCase(level)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If the level is not found, add it using the add button
		if (!found) {
			blinkElement(addLangLevelBtn);
			safeClick(addLangLevelBtn);
			safeClick(langLevelInputField);
			langLevelInputField.sendKeys(level);
			blinkElement(saveLangLevelBtn);
			safeClick(saveLangLevelBtn);
			blinkElement(langLevelSaveOkBtn);
			handleModalOk(langLevelSaveOkBtn);

			// After adding, select the newly added level
			safeClick(langLvlField);
			for (WebElement option : langLvlFieldList) {
				if (option.getText().trim().equalsIgnoreCase(level)) {
					safeClick(option);
					break;
				}
			}
		}
	}

	public void saveBtn() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Language Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void saveOkBtn() {
		blinkElement(saveOkBtn);
		handleModalOk(saveOkBtn);

	}
	
	public boolean isLanguageInfoSavedSuccessfully() {
		return saveOkBtn.isDisplayed();
	}

	// fill the language information form
	public BasicInfo_GeneralInformation_Prerights fillLanguageInformationForm(String language, String level) {
		addLangBtn();
//		langField();
		langFieldList(language);
		langLvlField();
		langLvlFieldList(level);
		saveBtn();
		saveOkBtn();

		return new BasicInfo_GeneralInformation_Prerights(driver);
	}

}
