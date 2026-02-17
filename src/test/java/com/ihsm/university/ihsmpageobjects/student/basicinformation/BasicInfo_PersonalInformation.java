package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class BasicInfo_PersonalInformation extends BasePage {

	public BasicInfo_PersonalInformation(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//*[@data-bs-target='#basicInfoId']")
	private WebElement addPersonalInfoBtn;

	@FindBy(xpath = "//div[@id='basicInfoId']//input[@name='FIRST_NAME_IN_RUSSIAN']")
	private WebElement firstNameRussianLangField;

	@FindBy(xpath = "//div[@id='basicInfoId']//input[@name='LAST_NAME_IN_RUSSIAN']")
	private WebElement lastNameRussianLangField;

	@FindBy(xpath = "//div[@id='basicInfoId']//input[@name='CITY']")
	private WebElement cityField;

	@FindBy(xpath = "//div[@id='basicInfoId']//ng-select[@name='marital']")
	private WebElement maritalStatusField;

	@FindBy(xpath = "//div[@id='basicInfoId']//label[contains(normalize-space(),'Marital Status')]/following::span[contains(@class,'addvalue')][1]")
	private WebElement addMaritalStatusPlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//input[@name='ENTER_VALUE']")
	private WebElement maritalStatusInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1ID']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveMaritalStatusBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement alertOkMaritalStatusBtn;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> maritalStatusFieldList;

	@FindBy(xpath = "//div[@id='basicInfoId']//textarea[@name='ADDRESS']")
	private WebElement addressField;

	@FindBy(xpath = "//div[@id='basicInfoId']//ancestor::div[contains(@class,'modal-content')]//button[normalize-space(text())='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement alertOkBtn;

	// methods to perform the action

	public void addPersonalInfoBtn() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		wait.until(ExpectedConditions.visibilityOf(addPersonalInfoBtn));
		wait.until(ExpectedConditions.elementToBeClickable(addPersonalInfoBtn));

		blinkElement(addPersonalInfoBtn);
		safeClick(addPersonalInfoBtn);

	}

	public void firstNameRussianLangField(String firstNameRussian) {
		firstNameRussianLangField.clear();
		safeClick(firstNameRussianLangField);
		firstNameRussianLangField.sendKeys(firstNameRussian);
	}

	public void lastNameRussianLangField(String lastNameRussian) {
		lastNameRussianLangField.clear();
		lastNameRussianLangField.sendKeys(lastNameRussian);
	}

	public void cityField(String city) {
		cityField.clear();
		cityField.sendKeys(city);
	}

	public void maritalStatusFieldList(String maritalStatus) {
		safeClick(maritalStatusField);

		boolean statusFound = false;
		for (WebElement option : maritalStatusFieldList) {
			if (option.getText().trim().equalsIgnoreCase(maritalStatus)) {
				safeClick(option);
				statusFound = true;
				break;
			}
		}
		if (!statusFound) {
			// If the marital status is not found, add it
			blinkElement(addMaritalStatusPlusBtn);
			safeClick(addMaritalStatusPlusBtn);
			safeClick(maritalStatusInputField);
			maritalStatusInputField.sendKeys(maritalStatus);
			blinkElement(saveMaritalStatusBtn);
			safeClick(saveMaritalStatusBtn);
			handleModalOk(alertOkMaritalStatusBtn);

			// Select the newly added marital status
			safeClick(maritalStatusField);
			for (WebElement option : maritalStatusFieldList) {
				if (option.getText().trim().equalsIgnoreCase(maritalStatus)) {
					safeClick(option);
					break;
				}
			}
		}
	}

	public void addressField(String address) {
		addressField.clear();
		addressField.sendKeys(address);
	}

	public void saveBtn() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Personal InfoFormation Filled");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void alertOkBtn() {
		blinkElement(alertOkBtn);
		handleModalOk(alertOkBtn);
	}

	public boolean isPersonalInfoSavedSuccessfully() {
		try {
			// Wait for the success modal OK button to appear
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			wait.until(ExpectedConditions.visibilityOf(alertOkBtn));
			return alertOkBtn.isDisplayed();
		} catch (Exception e) {
			logger.error("Success modal did not appear", e);
			return false;
		}
	}

	// fill the personal information form
	public BasicInfo_Biometrics fillPersonalInformationForm(String firstNameRussian, String lastNameRussian,
			String city, String maritalStatus, String address) {
		addPersonalInfoBtn();
		firstNameRussianLangField(firstNameRussian);
		lastNameRussianLangField(lastNameRussian);
		cityField(city);
		maritalStatusFieldList(maritalStatus);
		addressField(address);
		saveBtn();
		alertOkBtn();
		return new BasicInfo_Biometrics(driver);
	}

}
