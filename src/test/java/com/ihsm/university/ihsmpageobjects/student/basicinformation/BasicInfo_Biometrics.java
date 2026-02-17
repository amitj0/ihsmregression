package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class BasicInfo_Biometrics extends BasePage {

	public BasicInfo_Biometrics(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#biometricId']")
	private WebElement addBiometricsBtn;

	@FindBy(xpath = "//div[@Id='biometricId']//input[@type='file']")
	private WebElement biometricField;

	@FindBy(xpath = "//div[@id='biometricId']//ancestor::div[contains(@class,'modal-content')]//button[normalize-space(text())='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement saveOkBtn;

	// methods to interact with the web elements can be added here
	public void clickAddBiometrics() {
		blinkElement(addBiometricsBtn);
		safeClick(addBiometricsBtn);
	}

	public void uploadBiometricFile(String filePath) {
		biometricField.sendKeys(filePath);
	}

	public void clickSave() {
		try {
			captureScreenshot("Biometrics Information Filled");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void clickSaveOk() {
		blinkElement(saveOkBtn);
		handleModalOk(saveOkBtn);
	}
	
	public boolean isBioInfoSavedSuccessfully() {
		return saveOkBtn.isDisplayed();
	}

	// fill biometrics info here
	public BasicInfo_FamilyInformation fillBiometricsInfo(String filePath) {
		clickAddBiometrics();
		uploadBiometricFile(filePath);
		clickSave();
		clickSaveOk();
		return new BasicInfo_FamilyInformation(driver);
	}

}
