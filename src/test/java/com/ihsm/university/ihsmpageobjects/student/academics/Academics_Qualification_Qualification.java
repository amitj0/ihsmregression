package com.ihsm.university.ihsmpageobjects.student.academics;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;
import com.ihsm.university.ihsmpageobjects.student.status.Status_Status;

public class Academics_Qualification_Qualification extends BasePage {

	public Academics_Qualification_Qualification(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target='#divQualificationModel']")
	private WebElement qualTab;

	@FindBy(xpath = "//a[@href='#tab311']")
	private WebElement qualificationTab;

	@FindBy(xpath = "//div[@id='divQualificationModel']//ng-select[@name='QUALIFICATION']")
	private WebElement qualificationField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//div[@role='option']")
	private List<WebElement> qualificationFieldList;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[normalize-space()='School/College']//following-sibling::div/input")
	private WebElement schoolField;

	//developer mistake here
	@FindBy(xpath = "//div[@id='divQualificationModel']//label[normalize-space()='Certificate No']//following-sibling::div/input")
	private WebElement certificateNoField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'From')]/following-sibling::div//input[@type='date']")
	private WebElement fromDateField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'Issue Date')]/following-sibling::div//input[@type='date']")
	private WebElement issueDateField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'To')]/following-sibling::div//input[@type='date']")
	private WebElement issueDateToField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'Occupation')]//following-sibling::div/input")
	private WebElement occupationField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//ng-select[@name='COUNTRY']")
	private WebElement countryField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//div[@role='option']")
	private List<WebElement> countryFieldList;

	@FindBy(xpath = "//div[@id='divQualificationModel']//ng-select[@name='REGION']")
	private WebElement regionField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//div[@role='option']")
	private List<WebElement> regionFieldList;

	@FindBy(xpath = "//div[@id='divQualificationModel']//ng-select[@name='CITY']")
	private WebElement cityField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//div[@role='option']")
	private List<WebElement> cityFieldList;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//ngx-dropzone[@class='uploadresyou']//input[@type='file'])[3]")
	private WebElement fileUploadField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//div[@class='modal-content']//button[contains(@class,'btnprimary') and text()='Save'])[3]")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	@FindBy(xpath = "//div[@id='divQualificationModel']//div[contains(@class,'modal-header')]//button[@data-bs-dismiss='modal']")
	private WebElement popCut;

	// methods to perform actions on the web elements

	public void qualificationTab() {
		safeClick(qualificationTab);
	}

	public void qualificationField() {
		safeClick(qualificationField);
	}

	public void selectQualification(String qualification) {
		safeClick(qualificationField);
		for (WebElement option : qualificationFieldList) {
			if (option.getText().trim().equalsIgnoreCase(qualification)) {
				safeClick(option);
				return;
			}
		}
	}

	public void enterSchool(String school) {
		schoolField.sendKeys(school);
	}

	public void enterCertificateNo(String certificateNo) {

		certificateNoField.sendKeys(certificateNo);
	}

	public void enterFromDate(String fromDate) {

		fromDateField.sendKeys(fromDate);
	}

	public void enterIssueDate(String issueDate) {

		issueDateField.sendKeys(issueDate);
	}

	public void enterIssueDateTo(String issueDateTo) {

		issueDateToField.sendKeys(issueDateTo);
	}

	public void enterOccupation(String occupation) {

		occupationField.sendKeys(occupation);
	}

	public void selectCountry(String country) {
		safeClick(countryField);
		for (WebElement option : countryFieldList) {
			if (option.getText().trim().equalsIgnoreCase(country)) {
				safeClick(option);
				return;
			}
		}
	}

	public void selectRegion(String region) {
		safeClick(regionField);
		for (WebElement option : regionFieldList) {
			if (option.getText().trim().equalsIgnoreCase(region)) {
				safeClick(option);
				return;
			}
		}
	}

	public void selectCity(String city) {
		safeClick(cityField);
		for (WebElement option : cityFieldList) {
			if (option.getText().trim().equalsIgnoreCase(city)) {
				safeClick(option);
				return;
			}
		}
	}

	public void uploadQualificationDocument(String filePath) throws Exception {

		fileUploadField.sendKeys(filePath);
	}

	public void saveQualification() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Qualification Qual Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
		refreshPageSafely();
	}

	public void popCut() {
		blinkElement(popCut);
		safeClick(popCut);
	}

	public boolean isQualificationInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// Fill the Qualification Information
	public Status_Status fillQualificationInformation(String qualification, String school, String certificateNo,
			String fromDate, String issueDate, String issueDateTo, String occupation, String country, String region,
			String city, String filePath) throws Exception {

		qualificationTab();
		qualificationField();
		selectQualification(qualification);
		enterSchool(school);
		enterCertificateNo(certificateNo);
		enterFromDate(fromDate);
		enterIssueDate(issueDate);
		enterIssueDateTo(issueDateTo);
		enterOccupation(occupation);
		selectCountry(country);
		selectRegion(region);
		selectCity(city);
		uploadQualificationDocument(filePath);
		saveQualification();
		okButtonSuccessPopup();

		return new Status_Status(driver);
	}

}
