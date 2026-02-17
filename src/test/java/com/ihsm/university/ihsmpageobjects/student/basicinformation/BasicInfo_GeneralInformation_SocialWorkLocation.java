package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class BasicInfo_GeneralInformation_SocialWorkLocation extends BasePage {

	public BasicInfo_GeneralInformation_SocialWorkLocation(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#GeneralInfoId']")
	private WebElement generalInfoTab;

	@FindBy(xpath = "//a[@href='#tab23' and normalize-space(text())='Student Work Location']")
	private WebElement socialWorkTab;

	@FindBy(xpath = "//div[@id='tab23']//input[@type='file']")
	private WebElement socialWorkDragField;

	@FindBy(xpath = "//div[@id='tab23']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveBtnPreSocialWorkField;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okBtnPreSocialWorkField;

	// methods to perform the action
	public void generalInfoTab() {
		blinkElement(generalInfoTab);
		safeClick(generalInfoTab);
	}

	public void socialWorkTab() {
		safeClick(socialWorkTab);
	}

	public void socialWorkDragField(String filePath) {
		socialWorkDragField.sendKeys(filePath);
	}

	public void saveBtnPreSocialWorkField() {
		blinkElement(saveBtnPreSocialWorkField);
		try {
			captureScreenshot("Social Work Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtnPreSocialWorkField);
		handleAlertIfPresent();
	}

	public void okBtnPreSocialWorkField() {
		blinkElement(okBtnPreSocialWorkField);
		handleModalOk(okBtnPreSocialWorkField);
	}
	
	public boolean isSocialWorkInfoSavedSuccessfully() {
		return okBtnPreSocialWorkField.isDisplayed();
	}

	// fill Social Work Location details
	public BasicInfo_MedicalInformation_Vaccination fillSocialWorkLocationDetails(String filePath) {
		generalInfoTab();
		socialWorkTab();
		socialWorkDragField(filePath);
		saveBtnPreSocialWorkField();
		okBtnPreSocialWorkField();
		return new BasicInfo_MedicalInformation_Vaccination(driver);
	}

}
