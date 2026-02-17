package com.ihsm.university.ihsmpageobjects.employee.documents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class Documents_Passport extends BasePage {

	public Documents_Passport(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@id='divbtnpassportID']//span")
	private WebElement addPassportBtn;

	@FindBy(name = "strPassportType")
	private WebElement passportTypeDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> passportTypeDropdownOptions;

	@FindBy(xpath = "(//div[@id='PassportModel']//label[contains(normalize-space(),'P')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addPassportTypePlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal4']//input[@type='text']")
	private WebElement passportTypeInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal4']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement savePassportTypeButton;

	@FindBy(name = "strCountryIssueBy")
	private WebElement countryIssueByDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> countryIssueByDropdownOptions;

	@FindBy(xpath = "(//div[@id='PassportModel']//label[contains(normalize-space(),'C')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addCountryIssueByPlusButton;

	@FindBy(name = "strAgency")
	private WebElement agencyField;

	@FindBy(name = "strPassportSeries")
	private WebElement passportSeriesField;

	@FindBy(name = "strPassportNumber")
	private WebElement passportNumberField;

	@FindBy(xpath = "//div[@id='PassportModel']//ng-select[@name='ISSUEPLACE']")
	private WebElement issuePlaceDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> issuePlaceDropdownOptions;

	@FindBy(xpath = "(//div[@id='PassportModel']//label[contains(normalize-space(),'Place of issue')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addIssuePlacePlusButton;

	@FindBy(xpath = "//div[@id='PassportModel']//input[@name='ISSUEDATE']")
	private WebElement issueDateField;

	@FindBy(xpath = "(//input[@placeholder='DD-MM-YYYY'])[4]")
	private WebElement expiryDateField;

	@FindBy(xpath = "//div[@id='PassportModel']//div[@class='modal-content']//button[contains(@class,'btnprimary') and text()='Save']")
	private WebElement savePassportBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to perform the action
	public void addPassportBtn() {
		blinkElement(addPassportBtn);
		safeClick(addPassportBtn);
	}

	public void passportTypeDropdownField() {
		safeClick(passportTypeDropdownField);
	}

	public void selectPassportTypeOption(String passportType) {

		// Open dropdown
		safeClick(passportTypeDropdownField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : passportTypeDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(passportType)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → add new Passport Type
		if (!found) {

			// Click +
			safeClick(addPassportTypePlusButton);

			// Enter passport type
			safeClick(passportTypeInputField);
			passportTypeInputField.sendKeys(passportType);

			// Save
			safeClick(savePassportTypeButton);

			// Ok -> Not add
//			safeClick(okButtonSuccessPopup);

			// Reopen dropdown
			safeClick(passportTypeDropdownField);

			// Select newly added value
			for (WebElement option : passportTypeDropdownOptions) {
				if (option.getText().trim().equalsIgnoreCase(passportType)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Passport Type value not found even after adding: " + passportType);
		}
	}

	public void countryIssueByDropdownField() {
		safeClick(countryIssueByDropdownField);
	}

	public void selectCountryIssueByOption(String countryIssueBy) {

		// Open dropdown
		safeClick(countryIssueByDropdownField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : countryIssueByDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(countryIssueBy)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → add new Country Issue By
		if (!found) {

			// Click +
			safeClick(addCountryIssueByPlusButton);

			// Enter country issue by
			safeClick(passportTypeInputField);
			passportTypeInputField.sendKeys(countryIssueBy);

			// Save
			safeClick(savePassportTypeButton);

			// Ok -> Not add
//			safeClick(okButtonSuccessPopup);

			// Reopen dropdown
			safeClick(countryIssueByDropdownField);

			// Select newly added value
			for (WebElement option : countryIssueByDropdownOptions) {
				if (option.getText().trim().equalsIgnoreCase(countryIssueBy)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Country Issue By value not found even after adding: " + countryIssueBy);
		}
	}

	public void enterAgency(String agency) {
		safeClick(agencyField);
		agencyField.clear();
		agencyField.sendKeys(agency);
		triggerBlur(agencyField);
	}

	public void enterPassportSeries(String passportSeries) {
		safeClick(passportSeriesField);
		passportSeriesField.clear();
		passportSeriesField.sendKeys(passportSeries);
		triggerBlur(passportSeriesField);
	}

	public void enterPassportNumber(String passportNumber) {
		safeClick(passportNumberField);
		passportNumberField.clear();
		passportNumberField.sendKeys(passportNumber);
		triggerBlur(passportNumberField);
	}

	public void issuePlaceDropdownField() {
		safeClick(issuePlaceDropdownField);

	}

	public void selectIssuePlaceOption(String issuePlace) {

		// Open dropdown
		safeClick(issuePlaceDropdownField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : issuePlaceDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(issuePlace)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → add new Issue Place
		if (!found) {

			// Click +
			safeClick(addIssuePlacePlusButton);

			// Enter issue place
			safeClick(passportTypeInputField);
			passportTypeInputField.sendKeys(issuePlace);

			// Save
			safeClick(savePassportTypeButton);

			// Ok -> Not add
//			safeClick(okButtonSuccessPopup);

			// Reopen dropdown
			safeClick(issuePlaceDropdownField);

			// Select newly added value
			for (WebElement option : issuePlaceDropdownOptions) {
				if (option.getText().trim().equalsIgnoreCase(issuePlace)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Issue Place value not found even after adding: " + issuePlace);
		}
	}

	public void enterIssueDate(String issueDate) {
		enterDate(issueDateField, issueDate);

	}

	public void enterExpiryDate(String expiryDate) {
		enterDate(expiryDateField, expiryDate);

	}

	public void savePassportBtn() {
		blinkElement(savePassportBtn);
		try {
			captureScreenshot("Passport Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(savePassportBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}

	public boolean isDocPassInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill passport details in one method
	public void fillPassportDetails(String passportType, String countryIssueBy, String agency, String passportSeries,
			String passportNumber, String issuePlace, String issueDate, String expiryDate) {
		addPassportBtn();
		passportTypeDropdownField();
		selectPassportTypeOption(passportType);
		countryIssueByDropdownField();
		selectCountryIssueByOption(countryIssueBy);
		enterAgency(agency);
		enterPassportSeries(passportSeries);
		enterPassportNumber(passportNumber);
		issuePlaceDropdownField();
		selectIssuePlaceOption(issuePlace);
		enterIssueDate(issueDate);

		savePassportBtn();
		okButtonSuccessPopup();

		// return here
	}

}
