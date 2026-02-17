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

public class Documents_VisaInfo_OffVisa extends BasePage {

	public Documents_VisaInfo_OffVisa(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#STUDENTVISA']")
	private WebElement visaInfoTab;

	@FindBy(xpath = "//a[@href='#tabID25']")
	private WebElement offlineVisaTab;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//label[contains(normalize-space(.),'Visa Type')]/following-sibling::div[1]//ng-select[@name='VISATYPE'])[1]")
	private WebElement visaTypeField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> visaTypeFieldList;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//label[contains(normalize-space(),'Visa Type')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addVisaTypePlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1']//input[@name='ENTER_VALUE']")
	private WebElement visaTypeInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal1']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveVisaTypeButton;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonVisaType;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//label[contains(normalize-space(.),'Select Address')]/following-sibling::div[1]//ng-select[@name='DOCUMENTTYPE'])[1]")
	private WebElement selectAddField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> selectAddFieldList;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//input[@placeholder='Visa_Expiry_Date'])[1]")
	private WebElement currentVisaExpDateField;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//input[@placeholder='Visa_Expiry_Date'])[2]")
	private WebElement nextVisaIssDateField;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//input[@placeholder='Visa_Expiry_Date'])[3]")
	private WebElement nextVisaExpDateField;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//input[@placeholder='Visa_Expiry_Date'])[4]")
	private WebElement expiryDateNotaryField;

	@FindBy(xpath = "//input[@name='strVisaNumber']")
	private WebElement visaNumberField;

	@FindBy(xpath = "(//input[@placeholder='Current Reg Address'])[1]")
	private WebElement currentRegAdd;

	@FindBy(xpath = "(//textarea[@placeholder='Comments'])[1]")
	private WebElement commentsField;

	@FindBy(xpath = "(//ngx-dropzone[@class='uploadresyou']//input[@type='file'])[3]")
	private WebElement fileUploadField;

	@FindBy(xpath = "(//div[@id='STUDENTVISA']//button[@type='button' and normalize-space()='Save'])[1]")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	// methods to perform actions on the web elements can be added here
	public void clickVisaInfoTab() {
		blinkElement(visaInfoTab);
		safeClick(visaInfoTab);
	}

	public void clickOfflineVisaTab() {
		safeClick(offlineVisaTab);
	}

	public void selectVisaField() {
		safeClick(visaTypeField);
	}

	
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

	public void selectAddress(String addressType) {
		safeClick(selectAddField);
		for (WebElement option : selectAddFieldList) {
			if (option.getText().trim().equalsIgnoreCase(addressType)) {
				safeClick(option);
				return;
			}
		}
	}

	public void enterCurrentVisaExpDate(String date) {
		currentVisaExpDateField.sendKeys(date);
	}

	public void enterNextVisaIssDate(String date) {
		nextVisaIssDateField.sendKeys(date);
	}

	public void enterNextVisaExpDate(String date) {
		nextVisaExpDateField.sendKeys(date);
	}

	public void enterExpiryDateNotary(String date) {
		expiryDateNotaryField.sendKeys(date);
	}

	public void enterVisaNumber(String visaNumber) {
		visaNumberField.sendKeys(visaNumber);
	}

	public void enterCurrentRegAdd(String address) {
		currentRegAdd.sendKeys(address);
	}

	public void enterComments(String comments) {
		commentsField.sendKeys(comments);
	}

	public void uploadVisaDocument(String filePath) {
		fileUploadField.sendKeys(filePath);
	}

	public void clickSaveButton() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Documents Offline Visa Information Saved");
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

	public boolean isVisaInfoOffVisaSavedSuccessfully() {
		return okButton.isDisplayed();
	}

	// fill visa info offline visa form
	public Documents_VisaInfo_OnVisa fillVisaInfoOffVisaForm(String visaType, String addressType,
			String currentVisaExpDate, String nextVisaIssDate, String nextVisaExpDate, String expiryDateNotary,
			String visaNumber, String currentRegAdd, String comments, String filePath) {
		clickVisaInfoTab();
		clickOfflineVisaTab();
		selectVisaField();
		selectVisaType(visaType);
		selectAddress(addressType);
		enterCurrentVisaExpDate(currentVisaExpDate);
		enterNextVisaIssDate(nextVisaIssDate);
		enterNextVisaExpDate(nextVisaExpDate);
		enterExpiryDateNotary(expiryDateNotary);
		enterVisaNumber(visaNumber);
		enterCurrentRegAdd(currentRegAdd);
		enterComments(comments);
		uploadVisaDocument(filePath);
		clickSaveButton();
		clickOkButton();
		return new Documents_VisaInfo_OnVisa(driver);

	}

}
