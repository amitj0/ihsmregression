package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class BasicInfo_GeneralInformation_SocialWorkLocation extends BasePage {

	public BasicInfo_GeneralInformation_SocialWorkLocation(WebDriver driver) {
		super(driver);
	}

	private String lastSuccessMsg;

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

	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnSuccessTextContent'])[1]")
	private WebElement modalSuccessMsg;

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

	public String modalSuccessMsg() throws TimeoutException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(modalSuccessMsg));
		wait.until(d -> !modalSuccessMsg.getText().trim().isEmpty());
		return modalSuccessMsg.getText().trim();
	}

	public String getLastSuccessMsg() {
		return lastSuccessMsg;
	}

	// fill Social Work Location details
	public BasicInfo_MedicalInformation_Vaccination fillSocialWorkLocationDetails(String filePath)
			throws TimeoutException {
		generalInfoTab();
		socialWorkTab();
		socialWorkDragField(filePath);
		saveBtnPreSocialWorkField();

		lastSuccessMsg = modalSuccessMsg();
		okBtnPreSocialWorkField();
		return new BasicInfo_MedicalInformation_Vaccination(driver);
	}

}
