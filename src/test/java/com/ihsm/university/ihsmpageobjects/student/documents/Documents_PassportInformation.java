package com.ihsm.university.ihsmpageobjects.student.documents;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;
import com.ihsm.university.ihsmpageobjects.student.academics.Academics_Qualification_LastEducation;

public class Documents_PassportInformation extends BasePage {

	public Documents_PassportInformation(WebDriver driver) {
		super(driver);
	}

	private String lastSuccessMsg;

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#PassportId']")
	private WebElement passportTab;

	@FindBy(xpath = "//div[@id='PassportId']//div[contains(@class,'input-group')]//input[@name='PASSPORT']")
	private WebElement passNumberField;

	@FindBy(xpath = "//div[@id='PassportId']//div[contains(@class,'input-group')]//ng-select[@name='ISSUEPLACE']")
	private WebElement placeOfIssueField;

	@FindBy(xpath = "//div[@role='listbox' and contains(@class, 'ng-dropdown-panel-items')]//div[@role='option']")
	private List<WebElement> placeOfIssueList;

	@FindBy(xpath = "//input[@name='ISSUEDate1']")
	private WebElement issueDateField;

	@FindBy(xpath = "//div[@id='PassportId']//div//input[@placeholder='DD-MM-YYYY']")
	private WebElement expiryDateField;

	@FindBy(xpath = "//h5[normalize-space(text())='Passport']/ancestor::div[contains(@class,'modal-content')]//button[normalize-space(text())='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnSuccessTextContent'])[1]")
	private WebElement modalSuccessMsg;

	// methods to perform actions on the web elements can be added here
	public void clickPassportTab() {
		blinkElement(passportTab);
		safeClick(passportTab);
	}

	public void enterPassportNumber(String passportNumber) {
		safeClick(passNumberField);
		passNumberField.sendKeys(passportNumber);
	}

	public void selectPlaceOfIssue(String place) {
		safeClick(placeOfIssueField);
//		for (WebElement option : placeOfIssueList) {
//			if (option.getText().trim().equalsIgnoreCase(place)) {
//				safeClick(option);
//				return;
//			}
//		}
		for (WebElement option : placeOfIssueList) {
			if (option.getText().trim().equalsIgnoreCase(place)) {
				wait.until(ExpectedConditions.elementToBeClickable(option));
				safeClick(option);
				return;
			}
		}
	}

	public void enterIssueDate(String issueDate) {

		issueDateField.sendKeys(issueDate);
	}

	public void enterExpiryDate(String expiryDate) {

		expiryDateField.sendKeys(expiryDate);

	}

	public void clickSaveButton() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Documents Passport Information Saved");
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

	public boolean isPassportInfoSavedSuccessfully() {
		return okButton.isDisplayed();
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

	// fill passport information
	public Academics_Qualification_LastEducation fillPassportInformation(String passportNumber, String placeOfIssue,
			String issueDate, String expiryDate) throws TimeoutException {
		clickPassportTab();
		enterPassportNumber(passportNumber);
		selectPlaceOfIssue(placeOfIssue);
		enterIssueDate(issueDate);
		enterExpiryDate(expiryDate);
		clickSaveButton();
		lastSuccessMsg = modalSuccessMsg();

		clickOkButton();

		return new Academics_Qualification_LastEducation(driver);
	}

}
