package com.ihsm.university.ihsmpageobjects.employee.basicinformation;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;
import com.ihsm.university.ihsmpageobjects.employee.designation.Designation_EmploymentRights;

public class BasicInfo_BiometricsInformation extends BasePage {

	public BasicInfo_BiometricsInformation(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@id='btndivbionetric']//span")
	private WebElement addBiometricsInfoBtn;

	@FindBy(xpath = "//div[@Id='staticBackdropBiometricID']//input[@type='file']")
	private WebElement biometricInfoField;

	@FindBy(xpath = "//div[@id='staticBackdropBiometricID']//ancestor::div[contains(@class,'modal-content')]//button[normalize-space(text())='Save']")
	private WebElement saveBiometricsInfoBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to perform the action
	
	public void addBiometricsInfoBtn() {
		blinkElement(addBiometricsInfoBtn);
		safeClick(addBiometricsInfoBtn);
	}

	public void uploadBiometricFile(String filePath) {
		biometricInfoField.sendKeys(filePath);
		
		// Wait for file to be attached
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void saveBiometricsInfoBtn() {
		blinkElement(saveBiometricsInfoBtn);
		
		try {
			captureScreenshot("Biometrics_Information_Before_Save");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Click Save button
		safeClick(saveBiometricsInfoBtn);
		logger.info("Save button clicked");
		
		// CRITICAL: Handle the confirmation alert "Are you sure you want to submit data?"
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			String alertText = alert.getText();
			logger.info("Confirmation alert appeared: " + alertText);
			
			// Accept the alert (click OK)
			alert.accept();
			logger.info("Confirmation alert accepted");
			
			// NOW wait for the biometrics modal to close
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
		} catch (Exception e) {
			logger.warn("No confirmation alert appeared", e);
		}
	}

	public void okButtonSuccessPopup() {
		try {
			// Wait for success modal to appear after form submission
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(okButtonSuccessPopup));
			
			logger.info("Success modal appeared");
			
			blinkElement(okButtonSuccessPopup);
			handleModalOk(okButtonSuccessPopup);
			
		} catch (Exception e) {
			logger.error("Success modal did not appear", e);
			
			// Take screenshot for debugging
			try {
				captureScreenshot("No_Success_Modal_After_Biometrics_Save");
			} catch (Exception ex) {
				logger.error("Screenshot failed", ex);
			}
			
			throw new RuntimeException("Success modal did not appear after biometrics save", e);
		}
	}

	public boolean isBiometricsInfoAdded() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOf(okButtonSuccessPopup));
			return okButtonSuccessPopup.isDisplayed();
		} catch (Exception e) {
			logger.error("Success modal validation failed", e);
			return false;
		}
	}

	// fill biometrics info here
	public Designation_EmploymentRights fillBiometricsInfo(String filePath) {
		addBiometricsInfoBtn();
		uploadBiometricFile(filePath);
		saveBiometricsInfoBtn();
		okButtonSuccessPopup();

		return new Designation_EmploymentRights(driver);
	}
}