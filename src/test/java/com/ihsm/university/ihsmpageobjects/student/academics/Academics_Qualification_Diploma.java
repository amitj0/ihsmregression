package com.ihsm.university.ihsmpageobjects.student.academics;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Academics_Qualification_Diploma extends BasePage {

	public Academics_Qualification_Diploma(WebDriver driver) {
		super(driver);
	}

	// locate the web element here

	@FindBy(xpath = "//a[@href='#tab301']")
	private WebElement qualDiplomaTab;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[normalize-space()='Serial No*']//following-sibling::div/input")
	private WebElement serialNoField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[normalize-space()='Document No']//following-sibling::div/input")
	private WebElement documentNoField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'Certificate No')]//following-sibling::div/input[@name='CERTIFICATENO']")
	private WebElement certificateNoField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(text(),'Protection Date')]/following-sibling::div//input[@type='date']")
	private WebElement protectionDateField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(text(),'Diploma Date')]/following-sibling::div//input[@type='date']")
	private WebElement diplomaDateField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'Student Profession')]//following-sibling::div/input[@name='FROM']")
	private WebElement studentProfessionField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'Advice')]//following-sibling::div/input[@type='text']")
	private WebElement adviceField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'Topic Of Diploma')]//following-sibling::div/input[@type='text']")
	private WebElement topicOfDiplomaField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//label[contains(normalize-space(.),'Transcript No')]//following-sibling::div/input[@type='text']")
	private WebElement transcriptNoField;

	@FindBy(xpath = "//div[@id='divQualificationModel']//input[@placeholder='Date Of Transcript']")
	private WebElement dateOfTranscriptField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//ngx-dropzone[@class='uploadresyou']//input[@type='file'])[2]")
	private WebElement dragandDropField;

	@FindBy(xpath = "(//div[@id='divQualificationModel']//div[@class='modal-content']//button[contains(@class,'btnprimary') and text()='Save'])[2]")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;
	
	// method to perform the action

	public void qualDiplomaTab() {
		safeClick(qualDiplomaTab);
	}

	public void enterSerialNo(String serialNo) {
		serialNoField.sendKeys(serialNo);
	}

	public void enterDocumentNo(String documentNo) {
		documentNoField.sendKeys(documentNo);
	}

	public void enterCertificateNo(String certificateNo) {
		certificateNoField.sendKeys(certificateNo);
	}

	public void enterProtectionDate(String protectionDate) {
		protectionDateField.sendKeys(protectionDate);
	}

	public void enterDiplomaDate(String diplomaDate) {
		diplomaDateField.sendKeys(diplomaDate);
	}

	public void enterStudentProfession(String studentProfession) {
		studentProfessionField.sendKeys(studentProfession);
	}

	public void enterAdvice(String advice) {
		adviceField.sendKeys(advice);
	}

	public void enterTopicOfDiploma(String topicOfDiploma) {
		topicOfDiplomaField.sendKeys(topicOfDiploma);
	}

	public void enterTranscriptNo(String transcriptNo) {
		transcriptNoField.sendKeys(transcriptNo);
	}

	public void enterDateOfTranscript(String dateOfTranscript) {
		dateOfTranscriptField.sendKeys(dateOfTranscript);
	}

	public void uploadDocument(String documentFilePath) {
		dragandDropField.sendKeys(documentFilePath);
	}

	public void saveQualificationDiploma() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Qualification Diploma Information Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}

	public boolean isQualificationDiplomaSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill the Qualification Diploma information
	public Academics_Qualification_Qualification fillDiplomaDetails(String serialNo, String documentNo,
			String certificateNo, String protectionDate, String diplomaDate, String studentProfession, String advice,
			String topicOfDiploma, String transcriptNo, String dateOfTranscript, String documentFilePath) {

		qualDiplomaTab();
		enterSerialNo(serialNo);
		enterDocumentNo(documentNo);
		enterCertificateNo(certificateNo);
		enterProtectionDate(protectionDate);
		enterDiplomaDate(diplomaDate);
		enterStudentProfession(studentProfession);
		enterAdvice(advice);
		enterTopicOfDiploma(topicOfDiploma);
		enterTranscriptNo(transcriptNo);
		enterDateOfTranscript(dateOfTranscript);
		uploadDocument(documentFilePath);
		saveQualificationDiploma();
		okButtonSuccessPopup();

		return new Academics_Qualification_Qualification(driver);

	}

}
