package com.ihsm.university.ihsmpageobjects.student.documents;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;
import com.ihsm.university.ihsmpageobjects.student.academics.Academics_Qualification_LastEducation;

public class Documents_VisaInfo_PassportLocation extends BasePage {

	public Documents_VisaInfo_PassportLocation(WebDriver driver) {
		super(driver);
	}
	
	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#STUDENTVISA']")
	private WebElement passportInfoTab;
	
	@FindBy(xpath = "//a[@href='#tabID28']")
	private WebElement passLocationTab;
	
	@FindBy(xpath = "//div[@id='tabID28']//ng-select[@name='QUALIFICATION']")
	private WebElement passLocationField;
	
	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> passLocationFieldList;
	
	@FindBy(xpath = "//div[@id='tabID28']//input[@name='DATE']")
	private WebElement dateField;
	
	@FindBy(xpath = "//div[@id='tabID28']//button[contains(text(), 'Save')]")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;
	
	// methods to perform actions on the web elements can be added here
	public void clickPassportInfoTab() {
		blinkElement(passportInfoTab);
		safeClick(passportInfoTab);
	}
	
	public void clickPassLocationTab() {
		safeClick(passLocationTab);
	}
	public void selectPassLocation(String location) {
		safeClick(passLocationField);
		for (WebElement option : passLocationFieldList) {
			if (option.getText().trim().equalsIgnoreCase(location)) {
				safeClick(option);
				return;
			}
		}
	}
	public void enterDate(String date) {
		dateField.sendKeys(date);
	}
	public void clickSaveButton() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Docments Visa Passport Location Information Saved");
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
	
	public boolean isPassportLocationTabDisplayed() {
		return passLocationTab.isDisplayed();
	}
	
	// fill the passport location information
	public Academics_Qualification_LastEducation fillPassportLocationInfo(String location, String date) {
		clickPassportInfoTab();
		clickPassLocationTab();
		selectPassLocation(location);
		enterDate(date);
		clickSaveButton();
		clickOkButton();
		return new Academics_Qualification_LastEducation(driver);
	}
	

}
