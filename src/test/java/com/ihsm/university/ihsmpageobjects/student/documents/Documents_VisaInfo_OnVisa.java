package com.ihsm.university.ihsmpageobjects.student.documents;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class Documents_VisaInfo_OnVisa extends BasePage {

	public Documents_VisaInfo_OnVisa(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#STUDENTVISA']")
	private WebElement visaInfoTab;

	@FindBy(xpath = "//a[@href='#tabID26']")
	private WebElement onlineVisaTab;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//label[contains(text(),'Visa Type')]/following-sibling::div[1]//ng-select[@name='VISATYPE'])[2]")
	private WebElement visaTypeField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> visaTypeFieldList;

	// currentvisaexpdate --> doubt
	@FindBy(xpath = "(//input[@name='ISSUEDATE'])[6]")
	private WebElement currentVisaExpDateField;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//input[@name='ISSUEDATE' and @placeholder='Visa_Expiry_Date'])[5]")
	private WebElement issueDateField;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//input[@name='ISSUEDATE' and @placeholder='Visa_Expiry_Date'])[6]")
	private WebElement visaExpDateField;

	@FindBy(xpath = "//div[@id='STUDENTVISA']//input[@name='ISSUEDATE' and @placeholder='Visa Number']")
	private WebElement visaNumberField;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//button[@type='button' and normalize-space()='Save'])[2]")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	// methods to perform actions on the web elements can be added here
	public void clickVisaInfoTab() {
		blinkElement(visaInfoTab);
		safeClick(visaInfoTab);
	}

	public void clickOnlineVisaTab() {
		safeClick(onlineVisaTab);
	}

	/*
	 * public void selectVisaType(String visaType) { safeClick(visaTypeField); for
	 * (WebElement option : visaTypeFieldList) { if
	 * (option.getText().trim().equalsIgnoreCase(visaType)) { safeClick(option);
	 * return; } } }
	 */

	public void selectVisaType(String visaType) {
		safeClick(visaTypeField);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfAllElements(visaTypeFieldList));

		for (WebElement option : visaTypeFieldList) {
			if (option.getText().trim().equalsIgnoreCase(visaType)) {
				wait.until(ExpectedConditions.elementToBeClickable(option));
				safeClick(option);
				return;
			}
		}
	}

	public void enterCurrentVisaExpDate(String currentVisaExpDate) {
		currentVisaExpDateField.sendKeys(currentVisaExpDate);
	}

	public void enterIssueDate(String issueDate) {
		issueDateField.sendKeys(issueDate);
	}

	public void enterVisaExpDate(String visaExpDate) {
		visaExpDateField.sendKeys(visaExpDate);
	}

	public void enterVisaNumber(String visaNumber) {
		visaNumberField.sendKeys(visaNumber);
	}

	public void clickSaveButton() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Documents Visa Online Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void clickOkButton() {
		blinkElement(okButton);
		handleModalOk(okButton);
	}

	public boolean isOnlineVisaInfoSavedSuccessfully() {
		return okButton.isDisplayed();
	}

	// fill the online visa information
	public Documents_VisaInfo_Register fillOnlineVisaInfo(String visaType, String currentVisaExpDate, String issueDate,
			String visaExpDate, String visaNumber) {
		clickVisaInfoTab();
		clickOnlineVisaTab();
		selectVisaType(visaType);
		enterCurrentVisaExpDate(currentVisaExpDate);
		enterIssueDate(issueDate);
		enterVisaExpDate(visaExpDate);
		enterVisaNumber(visaNumber);
		clickSaveButton();
		clickOkButton();
		return new Documents_VisaInfo_Register(driver);
	}

}
