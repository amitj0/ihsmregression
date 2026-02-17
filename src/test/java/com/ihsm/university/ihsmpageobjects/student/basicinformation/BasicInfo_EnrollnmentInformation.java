package com.ihsm.university.ihsmpageobjects.student.basicinformation;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ihsm.university.base.BasePage;

public class BasicInfo_EnrollnmentInformation extends BasePage {

	public BasicInfo_EnrollnmentInformation(WebDriver driver) {
		super(driver);
	}

	// locate the web element here

//	@FindBy(xpath = "//span[normalize-space()='Student']")
	@FindBy(xpath = "//a[@id='a3']")
	private WebElement studentMenuItems;

	@FindBy(xpath = "//a[@href='#/Student/NewenrollmentsStudent']")
	private WebElement addNewStudent;

	@FindBy(xpath = "//span[@data-bs-target='#EnrollmentInformationId']")
	private WebElement addEnrollBtn;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//ng-select[@name='ADMISSION_BATCH']")
	private WebElement addmissionBtchField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> addmissionBtchFieldOptions;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//ng-select[@name='UNIVERSITY_CURRICULAM']")
	private WebElement universityCurrField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> universityCurrFieldOptions;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//ng-select[@name='SEMESTER']")
	private WebElement semesterField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> semesterFieldList;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//ng-select[@name='GROUP']")
	private WebElement groupField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> groupFieldList;

	@FindBy(xpath = "//input[@name='pin']")
	private WebElement pinNoField;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//input[@name='FIRSTNAME']")
	private WebElement firstNameField;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//input[@placeholder='Middle Name']")
	private WebElement middleNameField;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//input[@name='LASTNAME']")
	private WebElement lastNameField;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//ng-select[@name='GENDER']")
	private WebElement genderField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> genderFieldList;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//input[@name='DOB']")
	private WebElement dobField;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//ng-select[@name='NATIONALITY']")
	private WebElement nationalityField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> nationalityFieldList;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//ng-select[@name='STATE']")
	private WebElement stateField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> stateFieldList;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//input[@name='MOBILE1']")
	private WebElement mobileNoField;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//input[@name='EMAIL']")
	private WebElement emailField;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//textarea[@name='ADDRESS']")
	private WebElement addressField;

	@FindBy(xpath = "//div[@id='EnrollmentInformationId']//ancestor::div[contains(@class,'modal-content')]//button[normalize-space(text())='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement alertOkBtn;

	@FindBy(xpath = "//div[@class='studentid']")
	private WebElement studentEnrollmentId;

	public String getStudentEnrollmentId() throws InterruptedException {

		Thread.sleep(3000);

		String enrollmentId = studentEnrollmentId.getText().trim();

		System.out.println("Captured Student Enrollment ID = " + enrollmentId);

		return enrollmentId;
	}

	// methods to perform the action
	public void studentMenuItems() {
		blinkElement(studentMenuItems);
		safeClick(studentMenuItems);
	}

	public void addNewStudent() {
		safeClick(addNewStudent);
	}

	public void addEnrollBtn() {
		safeClick(addEnrollBtn);
	}

	public void addmissionBtchField() {
		safeClick(addmissionBtchField);
	}

	public void addmissionBtchFieldOptions(String addmissionBatch) {
		safeClick(addmissionBtchField);

		for (WebElement option : addmissionBtchFieldOptions) {
			if (option.getText().trim().equalsIgnoreCase(addmissionBatch)) {
				safeClick(option);
				return;
			}
		}
	}

	public void universityCurrField() {
		safeClick(universityCurrField);
	}

	public void universityCurrFieldOptions(String universityCurr) {
		safeClick(universityCurrField);
		for (WebElement option : universityCurrFieldOptions) {
			if (option.getText().trim().equalsIgnoreCase(universityCurr)) {
				safeClick(option);
				return;
			}
		}

	}

	public void semesterField() {
		safeClick(semesterField);
	}

	public void semesterFieldList(String semester) {
		safeClick(semesterField);

		for (WebElement option : semesterFieldList) {
			if (option.getText().trim().equalsIgnoreCase(semester)) {
				safeClick(option);
				return;
			}
		}
	}

	public void groupField() {
		safeClick(groupField);
	}

	public void groupFieldList(String group) {
		safeClick(groupField);

		for (WebElement option : groupFieldList) {
			if (option.getText().trim().equalsIgnoreCase(group)) {
				safeClick(option);
				return;
			}
		}
	}

	public void fillPinNo(String pinNo) {
		pinNoField.clear();
		pinNoField.sendKeys(pinNo);
	}

	public void fillFirstName(String firstName) {
		firstNameField.clear();
		firstNameField.sendKeys(firstName);
	}

	public void fillMiddleName(String middleName) {;
		middleNameField.clear();
		middleNameField.sendKeys(middleName);
	}

	public void fillLastName(String lastName) {
		lastNameField.clear();
		lastNameField.sendKeys(lastName);
	}

	public void genderField() {
		safeClick(genderField);
	}

	public void genderFieldList(String gender) {
		safeClick(genderField);
		for (WebElement option : genderFieldList) {
			if (option.getText().trim().equalsIgnoreCase(gender)) {
				safeClick(option);
				return;
			}
		}
	}

	public void fillDob(String dob) throws InterruptedException {
		Thread.sleep(1000);
		enterDate(dobField, dob);
	}

	public void nationalityField() {
		safeClick(nationalityField);
	}

	public void nationalityFieldList(String nationality) {
		safeClick(nationalityField);
		for (WebElement option : nationalityFieldList) {
			if (option.getText().trim().equalsIgnoreCase(nationality)) {
				safeClick(option);
				return;
			}
		}
	}

	public void stateField() {
		safeClick(stateField);
	}

	public void stateFieldList(String state) {
		safeClick(stateField);
		for (WebElement option : stateFieldList) {
			if (option.getText().trim().equalsIgnoreCase(state)) {
				safeClick(option);
				return;
			}
		}
	}

	public void fillMobileNo(String mobileNo) {
		mobileNoField.clear();
		mobileNoField.sendKeys(mobileNo);
	}

	public void fillEmail(String email) {
		emailField.clear();
		emailField.sendKeys(email);
	}

	public void fillAddress(String address) {
		addressField.clear();
		addressField.sendKeys(address);
	}

	public void saveBtn() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Enrollment Information Details");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(saveBtn);

		handleAlertIfPresent();
	}

	public void alertOkBtn() {
		blinkElement(alertOkBtn);
		handleModalOk(alertOkBtn);
	}

	public boolean isEnrollmentInfoSavedSuccessfully() {
		return alertOkBtn.isDisplayed();
	}

	// Fill the Enrollment Information
	public BasicInfo_EnrollnmentInformation fillEnrollmentInformation(String addmissionBatch, String universityCurr,
			String semester, String group, String pinNo, String firstName, String middleName, String lastName,
			String gender, String dob, String nationality, String state, String mobileNo, String email,
			String address) {
		studentMenuItems();
		addNewStudent();
		addEnrollBtn();
		addmissionBtchFieldOptions(addmissionBatch);
		universityCurrFieldOptions(universityCurr);
		semesterFieldList(semester);
		groupFieldList(group);
		fillPinNo(pinNo);
		fillFirstName(firstName);
		fillMiddleName(middleName);
		fillLastName(lastName);
		genderFieldList(gender);
		fillDob(dob);
		nationalityFieldList(nationality);
		stateField();
		stateFieldList(state);
		fillMobileNo(mobileNo);
		fillEmail(email);
		fillAddress(address);
		saveBtn();
//		alertOkBtn(); 

		return new BasicInfo_EnrollnmentInformation(driver);

	}


}
