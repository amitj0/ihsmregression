package com.ihsm.university.ihsmpageobjects.student.status;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Status_ExamStatus extends BasePage {

	public Status_ExamStatus(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = " //span[@data-bs-target='#modelnonexamId']")
	private WebElement examStatusTab;

	@FindBy(xpath = "//div[@id='modelnonexamId']//ng-select[@name='REASON']//input")
	private WebElement reasonDropDownField;

	@FindBy(xpath = "//div[@id='modelnonexamId']//div[@role='option']")
	private List<WebElement> resonDropdownList;

	@FindBy(xpath = "(//div[@id='modelnonexamId']//ng-select[@name='VACCINATIONTYPE'])[1]")
	private WebElement chooseSemesterField;

	@FindBy(xpath = "//div[@id='modelnonexamId']//div[@role='option']")
	private List<WebElement> chooseSemesterOptions;

	@FindBy(xpath = "(//div[@id='modelnonexamId']//ng-select[@name='VACCINATIONTYPE'])[2]")
	private WebElement chooseDepartmentField;

	@FindBy(xpath = "//div[@id='modelnonexamId']//div[@role='option']")
	private List<WebElement> chooseDepartmentOptions;

	@FindBy(xpath = "//div[@id='modelnonexamId']//ng-select[@name='SUBJECT']")
	private WebElement chooseSubjectField;

	@FindBy(xpath = "//div[@id='modelnonexamId']//div[@role='option']")
	private List<WebElement> chooseSubjectOptions;

	@FindBy(xpath = "//div[@id='modelnonexamId']//ng-select[@name='EXAM_TYPE']")
	private WebElement chooseExamTypeField;

	@FindBy(xpath = "//div[@id='modelnonexamId']//div[@role='option']")
	private List<WebElement> chooseExamTypeOptions;

	@FindBy(xpath = "//div[@id='modelnonexamId']//div[@class='modal-content']//textarea[@placeholder='Comments']")
	private WebElement commentsFieldExamStatus;

	@FindBy(xpath = "//div[@id='modelnonexamId']//ngx-dropzone[@class='uploadresyou']//input[@type='file']")
	private WebElement dragDropFieldExamStatus;

	@FindBy(xpath = "//div[@id='modelnonexamId']//div[@class='modal-content']//button[contains(@class,'btnprimary') and text()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to perform actions on the web elements
	public void examStatusTab() {
		blinkElement(examStatusTab);
		safeClick(examStatusTab);
	}

	public void reasonDropDownField() {
		safeClick(reasonDropDownField);
	}

	public void selectReason(String reason) {
		safeClick(reasonDropDownField);
		for (WebElement option : resonDropdownList) {
			if (option.getText().equalsIgnoreCase(reason)) {
				blinkElement(option);
				safeClick(option);
				return;
			}
		}
	}

	public void chooseSemesterField() {
		safeClick(chooseSemesterField);
	}

	public void selectSemester(String semester) {
		safeClick(chooseSemesterField);
		for (WebElement option : chooseSemesterOptions) {
			if (option.getText().equalsIgnoreCase(semester)) {
				blinkElement(option);
				safeClick(option);
				return;
			}
		}
	}

	public void chooseDepartmentField() {
		safeClick(chooseDepartmentField);
	}

	public void selectDepartment(String department) {
		safeClick(chooseDepartmentField);
		for (WebElement option : chooseDepartmentOptions) {
			if (option.getText().equalsIgnoreCase(department)) {
				blinkElement(option);
				safeClick(option);
				return;
			}
		}
	}

	public void chooseSubjectField() {
		safeClick(chooseSubjectField);
	}

	public void selectSubject(String subject) {
		safeClick(chooseSubjectField);
		for (WebElement option : chooseSubjectOptions) {
			if (option.getText().equalsIgnoreCase(subject)) {
				blinkElement(option);
				safeClick(option);
				return;
			}
		}
	}

	public void chooseExamTypeField() {
		safeClick(chooseExamTypeField);
	}

	public void selectExamType(String examType) {
		safeClick(chooseExamTypeField);
		for (WebElement option : chooseExamTypeOptions) {
			if (option.getText().trim().equalsIgnoreCase(examType)) {
				safeClick(option);
				return;
			}
		}
	}

	public void enterCommentsExamStatus(String comments) {
		commentsFieldExamStatus.sendKeys(comments);
	}

	public void uploadDocumentExamStatus(String examStatusFilePath) {
		dragDropFieldExamStatus.sendKeys(examStatusFilePath);
	}

	public void saveBtnExamStatus() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Status Exam Status Information Saved");
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
	
	public boolean isExamStatusInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// Fill the Exam Status Information
	public void fillExamStatusInformation(String reason, String semester, String department, String subject,
			String examType, String comments, String examStatusFilePath) {
		examStatusTab();
		reasonDropDownField();
		selectReason(reason);
		chooseSemesterField();
		selectSemester(semester);
		chooseDepartmentField();
		selectDepartment(department);
		chooseSubjectField();
		selectSubject(subject);
		chooseExamTypeField();
		selectExamType(examType);
		enterCommentsExamStatus(comments);
		uploadDocumentExamStatus(examStatusFilePath);
		saveBtnExamStatus();
		okButtonSuccessPopup();
		// return something here
	}

}
