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

public class BasicInfo_Biometrics extends BasePage {

	public BasicInfo_Biometrics(WebDriver driver) {
		super(driver);
	}
	
	private String lastSuccessMsg;


	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#biometricId']")
	private WebElement addBiometricsBtn;

	@FindBy(xpath = "//div[@Id='biometricId']//input[@type='file']")
	private WebElement biometricField;

	@FindBy(xpath = "//div[@id='biometricId']//ancestor::div[contains(@class,'modal-content')]//button[normalize-space(text())='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement saveOkBtn;
	
	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnSuccessTextContent'])[1]")
	private WebElement modalSuccessMsg;

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

	
	public String modalSuccessMsg() throws TimeoutException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(modalSuccessMsg));
		wait.until(d -> !modalSuccessMsg.getText().trim().isEmpty());
		return modalSuccessMsg.getText().trim();
	}
	
	public String getLastSuccessMsg() {
        return lastSuccessMsg;
    }
	// fill biometrics info here
	public BasicInfo_FamilyInformation fillBiometricsInfo(String filePath) throws TimeoutException {
		clickAddBiometrics();
		uploadBiometricFile(filePath);
		clickSave();
		lastSuccessMsg = modalSuccessMsg();
		clickSaveOk();
		return new BasicInfo_FamilyInformation(driver);
	}

}
