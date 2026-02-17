package com.ihsm.university.ihsmpageobjects.employee.basicinformation;

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

	// locate the web element
	@FindBy(xpath = "//div[@id='divbtnemppersonalinfo']//span")
	private WebElement addPersonalInfoBtn;

	@FindBy(name = "strWONumbers")
	private WebElement workOrderNumberField;

	@FindBy(xpath = "//div[@id='divPersonalInfoMaodel']//input[@type='date' and @name='DOB']")
	private WebElement dobFieldPersonalInfo;

	@FindBy(name = "strMaritialStatus")
	private WebElement maritalStatusDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> maritalStatusDropdownOptions;

	@FindBy(xpath = "(//div[@id='divPersonalInfoMaodel']//span[@data-bs-target='#addvalue'])[1]")
	private WebElement addMaritalStatusPlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//input[@name='strMasterName']")
	private WebElement maritalStatusInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveMaritalStatusButton;

	@FindBy(name = "dttDateOfJoining")
	private WebElement dateOfJoiningField;

	@FindBy(xpath = "//label[normalize-space()='Mobile No*']/following::ng-select[1]//div[contains(@class,'ng-select-container')]")
	private WebElement selectCountryCodeField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> selectCountryCodeOptions;

	@FindBy(xpath = "//div[@id='divPersonalInfoMaodel']//input[@name='MOBILE1']")
	private WebElement mobileNumberField;

	@FindBy(name = "strAddress")
	private WebElement addressField;

	@FindBy(name = "strSecondAddress")
	private WebElement addressLine2Field;

	@FindBy(xpath = "//div[@id='divPersonalInfoMaodel']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveButtonPersonalInfo;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement saveOkButtonPersonalInfo;

	// methods to perform the action

	public void addPersonalInfoBtn() {
		blinkElement(addPersonalInfoBtn);
		safeClick(addPersonalInfoBtn);
	}

	public void workOrderNumberField(String workOrderNumber) {
		safeClick(workOrderNumberField);
		workOrderNumberField.sendKeys(workOrderNumber);
	}

	public void dobFieldPersonalInfo(String dob) {
		enterDate(dobFieldPersonalInfo, dob);
	}

	public void maritalStatusDropdownField() {
		safeClick(maritalStatusDropdownField);
	}

	public void maritalStatusDropdownOptions(String maritalStatus) {
		selectNgDropdownValue(maritalStatusDropdownField, // dropdown element
				maritalStatus, // value to select
				addMaritalStatusPlusButton, // "+" button
				maritalStatusInputField, // input for new value
				saveMaritalStatusButton, // save button
				saveOkButtonPersonalInfo // OK button on modal
		);
	}

	public void addMaritalStatusPlusButton() {
		blinkElement(addMaritalStatusPlusButton);
		safeClick(addMaritalStatusPlusButton);
	}

	public void maritalStatusInputField(String maritalStatus) {
		safeClick(maritalStatusInputField);
		maritalStatusInputField.sendKeys(maritalStatus);
	}

	public void saveMaritalStatusButton() {
		blinkElement(saveMaritalStatusButton);
		safeClick(saveMaritalStatusButton);
	}

	public void dateOfJoiningField(String dateOfJoining) {
		enterDate(dateOfJoiningField, dateOfJoining);
	}

	public void selectCountryCodeField() {
		safeClick(selectCountryCodeField);
	}

	public void selectCountryCodeOptions(String countryCode) {
		safeClick(selectCountryCodeField);

		for (WebElement option : selectCountryCodeOptions) {
			if (option.getText().trim().equalsIgnoreCase(countryCode)) {
				safeClick(option);
				return;
			}
		}
	}

	public void mobileNumberField(String mobileNumber) {
		safeClick(mobileNumberField);
		mobileNumberField.sendKeys(mobileNumber);
	}

	public void addressField(String address) {
		addressField.clear();
		addressField.sendKeys(address);
	}

	public void addressLine2Field(String addressLine2) {
		addressLine2Field.clear();
		addressLine2Field.sendKeys(addressLine2);
	}

	public boolean saveButtonPersonalInfo() {
		blinkElement(saveButtonPersonalInfo);
		try {
			captureScreenshot("Personal InfoFormation Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveButtonPersonalInfo);
		handleAlertIfPresent();
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(1))
					.until(ExpectedConditions.visibilityOf(saveOkButtonPersonalInfo)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void okButtonPersonalInfo() {
		blinkElement(saveOkButtonPersonalInfo);
		handleModalOk(saveOkButtonPersonalInfo);
	}

	// fill the personal information form
	public BasicInfo_GuardianInformation fillPersonalInformationForm(String russianName, String englishName,
			String tinNumber, String workOrderNumber, String dob, String gender, String maritalStatus,
			String dateOfJoining, String countryCode, String mobileNumber, String address, String addressLine2) {
		addPersonalInfoBtn();
		workOrderNumberField(workOrderNumber);
		dobFieldPersonalInfo(dob);
		maritalStatusDropdownOptions(maritalStatus);
		dateOfJoiningField(dateOfJoining);
		selectCountryCodeOptions(countryCode);
		mobileNumberField(mobileNumber);
		addressField(address);
		addressLine2Field(addressLine2);
		saveButtonPersonalInfo();
		okButtonPersonalInfo();
		return new BasicInfo_GuardianInformation(driver);
	}

}
