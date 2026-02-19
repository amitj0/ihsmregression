package com.ihsm.university.ihsmpageobjects.student.documents;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Documents_VisaInfo_Register extends BasePage {

	public Documents_VisaInfo_Register(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#STUDENTVISA']")
	private WebElement visaInfoTab;

	@FindBy(xpath = "//a[@href='#tabID27']")
	private WebElement registerTab;

	@FindBy(xpath = "//div[@id='STUDENTVISA']//div[@id='tabID27']//ng-select[@name='DOCUMENTTYPE']")
	private WebElement selectAddressField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> selectAddressFieldList;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//input[@placeholder='Current Reg Address'])[2]")
	private WebElement currentRegAddField;
	
	@FindBy(xpath = "(//div[@id='STUDENTVISA']//input[@placeholder='CURRENT_REG_ADDRESS'])[2]")
	private WebElement currentRegAddField2;

	@FindBy(xpath = "//div[@id='STUDENTVISA']//div[@id='tabID27']//input[@name='REGISTRATIONSUBMISSIONDATE']")
	private WebElement registrationSubmissionDateField;

	@FindBy(xpath = "(//textarea[@placeholder='Comments'])[2]")
	private WebElement commentsField;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//button[@type='button' and normalize-space()='Save'])[3]")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	// methods to perform actions on the web elements can be added here
	public void clickVisaInfoTab() {
		blinkElement(visaInfoTab);
		safeClick(visaInfoTab);
	}

	public void clickRegisterTab() {
		safeClick(registerTab);
	}

	public void selectAddress(String address) {
		safeClick(selectAddressField);
		for (WebElement option : selectAddressFieldList) {
			if (option.getText().trim().equalsIgnoreCase(address)) {
				safeClick(option);
				return;
			}
		}
	}

	public void enterCurrentRegAdd(String address) {
		currentRegAddField2.sendKeys(address);
	}

	public void enterRegistrationSubmissionDate(String date) {
		registrationSubmissionDateField.sendKeys(date);
	}

	public void enterComments(String comments) {
		commentsField.sendKeys(comments);
	}

	public void clickSaveButton() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Documents Visa Register Information Saved");
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
	
	public boolean isRegisterInfoSavedSuccessfully() {
		return okButton.isDisplayed();
	}

	// fill the register information

	public Documents_VisaInfo_PassportLocation fillRegisterInfo(String addressType, String currentRegAdd,
			String registrationSubmissionDate, String comments) {
		clickVisaInfoTab();
		clickRegisterTab();
		selectAddress(addressType);
		enterCurrentRegAdd(currentRegAdd);
		enterRegistrationSubmissionDate(registrationSubmissionDate);
		enterComments(comments);
		clickSaveButton();
		clickOkButton();
		return new Documents_VisaInfo_PassportLocation(driver);
	}

}
