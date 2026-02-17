package com.ihsm.university.ihsmpageobjects.student.academics;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Academics_Qualification_LastEducation extends BasePage {

	public Academics_Qualification_LastEducation(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@data-bs-target='#pills-academic']")
	private WebElement academicsTab;

	@FindBy(xpath = "//span[@data-bs-target='#divQualificationModel']")
	private WebElement qualificationTab;

	@FindBy(xpath = "//a[@href='#tab291']")
	private WebElement lastEducationTab;

	@FindBy(xpath = "//div[@id='divQualificationModel']//ng-select[@name='REASON']")
	private WebElement typeOfEduField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//div[@role='option']")
	private List<WebElement> typeOfEduFieldList;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[normalize-space()='School*']//following-sibling::div/input")
	private WebElement schoolField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//label[contains(text(),'Start Date')]/following-sibling::div//input[@type='date'])[1]")
	private WebElement lEduStartDateField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//label[contains(text(),'End Date')]/following-sibling::div//input[@type='date'])[1]")
	private WebElement lEduEndDateField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//label[contains(text(),'Document Date')]/following-sibling::div//input[@type='date'])[1]")
	private WebElement documentDateField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//div[contains(@class, 'modal-content')]//input[@placeholder='Document No'])[1]")
	private WebElement documentNoField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//div[contains(@class, 'modal-content')]//input[@placeholder='Subject'])[1]")
	private WebElement docSubjectField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//div[contains(@class, 'modal-content')]//input[@placeholder='Marks'])[1]")
	private WebElement marksField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//ngx-dropzone[@class='uploadresyou']//input[@type='file'])[1]")
	private WebElement uploadField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//div[contains(@class,'modal-content')]//button[contains(@class,'btnprimary') and text()='Save'])[1]")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	@FindBy(xpath = "//div[@id='divQualificationModel']//div[contains(@class,'modal-header')]//button[@data-bs-dismiss='modal']")
	private WebElement popCut;

	// methods to perform actions on the web elements
	public void academicsTab() {
		safeClick(academicsTab);
	}

	public void qualificationTab() {
		safeClick(qualificationTab);
	}

	public void lastEducationTab() {
		safeClick(lastEducationTab);
	}

	public void typeOfEduField() {
		safeClick(typeOfEduField);
	}

	public void selectTypeOfEdu(String eduType) {
		safeClick(typeOfEduField);
		for (WebElement option : typeOfEduFieldList) {
			if (option.getText().trim().equalsIgnoreCase(eduType)) {
				safeClick(option);
				return;
			}
		}
	}

	public void enterSchool(String school) {
		schoolField.sendKeys(school);
	}

	public void enterLEduStartDate(String startDate) {
		lEduStartDateField.sendKeys(startDate);
	}

	public void enterLEduEndDate(String endDate) {
		lEduEndDateField.sendKeys(endDate);
	}

	public void enterDocDateField(String docDate) {
		documentDateField.sendKeys(docDate);
	}

	public void documentNoField(String docField) {
		documentNoField.sendKeys(docField);
	}

	public void enterDocSubject(String subject) {
		docSubjectField.sendKeys(subject);
	}

	public void enterMarks(String marks) {
		marksField.sendKeys(marks);
	}

	public void uploadDocument(String file) {
		uploadField.sendKeys(file);
	}

	public void saveLastEducationDetails() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Qualification Last Education Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void handleSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}

	public boolean isLastEduInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill the Last Education information
	public Academics_Qualification_Diploma fillLastEducationInfo(String eduType, String school, String startDate,
			String endDate, String docDate, String docFieldNo, String subject, String marks, String file) {
		academicsTab();
		qualificationTab();
		lastEducationTab();
		typeOfEduField();
		selectTypeOfEdu(eduType);
		enterSchool(school);
		enterLEduStartDate(startDate);
		enterLEduEndDate(endDate);
		enterDocDateField(docDate);
		documentNoField(docFieldNo);
		enterDocSubject(subject);
		enterMarks(marks);
		uploadDocument(file);
		saveLastEducationDetails();
		handleSuccessPopup();

		return new Academics_Qualification_Diploma(driver);
	}

}
