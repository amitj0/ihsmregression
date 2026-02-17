package com.ihsm.university.ihsmpageobjects.exammanagement;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class IHSM_ManageExam extends BasePage {

	public IHSM_ManageExam(WebDriver driver) {
		super(driver);
	}

	// locate the webelement here
	@FindBy(xpath = "//a[@id='a5']//span[normalize-space()='Exam Management']")
	private WebElement examManageTab;

	@FindBy(xpath = "//a[@href='#/Exam/ManageExams']")
	private WebElement manageExamTab;

	@FindBy(xpath = "(//div[@id='addExamSchedule']//ng-select[@name='UNIVERSITY_CURRICULAM'])[1]")
	private WebElement academicPlanField;

	@FindBy(xpath = "//ng-select[@name='UNIVERSITY_CURRICULAM']//div[@role='option']")
	private List<WebElement> academicPlanFieldList;

	@FindBy(xpath = "(//div[@id='addExamSchedule']//ng-select[@name='Zachot'])[1]")
	private WebElement zacotSemField;

	@FindBy(xpath = "//ng-select[@name='Zachot']//div[@role='option']")
	private List<WebElement> zacotSemFieldList;

	@FindBy(xpath = "(//div[@id='addExamSchedule']//input[@type='date' and @name='StartDate'])[1]")
	private WebElement startDate;

	@FindBy(xpath = "(//div[@id='addExamSchedule']//input[@type='date' and @name='EndDate'])[1]")
	private WebElement endDate;

	@FindBy(xpath = "//div[@id='addExamSchedule']//ancestor::div[contains(@class,'card')]//button[normalize-space(text())='Save']")
	private WebElement saveButton;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	@FindBy(xpath = "//div[@id='AlertErrorModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement errorButton;

	@FindBy(xpath = "//span[@id='spnErrorTextContent' and normalize-space()='Exam Not Saved.']")
	private WebElement errorMsgOfExam;

	@FindBy(xpath = "//span[@id='spnSuccessTextContent' and normalize-space()='Exam Saved Successfully']")
	private WebElement examSuccessMsg;

	// method to perform the action on the web element
	public void examManageTab() {
		safeClick(examManageTab);
	}

	public void manageExamTab() {
		safeClick(manageExamTab);
	}

	public void academicPlanField() {
		safeClick(academicPlanField);
	}

	public void academicPlanFieldList(String value) {
		for (WebElement option : academicPlanFieldList) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				safeClick(option);
				return;
			}
		}
	}

	public void zacotSemField() {
		safeClick(zacotSemField);
	}

	public void zacotSemFieldList(String value) {
		for (WebElement option : zacotSemFieldList) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				safeClick(option);
				return;
			}
		}
	}

	public void startDate(String sdate) {
		startDate.clear();
		enterDate(startDate, sdate);
	}

	public void endDate(String eDate) {
		endDate.clear();
		enterDate(endDate, eDate);
	}

	public void saveButton() {
		safeClick(saveButton);
	}

	public void okButton() {
		safeClick(okButton);
	}

	public void handleErrorOrOkButton() {

		if (isElementVisible(errorButton)) {
			blinkElement(errorButton);
			safeClick(errorButton);

		} else if (isElementVisible(okButton)) {
			blinkElement(okButton);
			safeClick(okButton);

		} else {
			System.out.println("Neither Error nor OK button is visible");
		}
	}

	public String isExamErrorMsg() {
		String errorMsg = errorMsgOfExam.getText();
		return errorMsg;
	}

	public String isExamSuccessMsg() {
		String successMsg = examSuccessMsg.getText();
		return successMsg;
	}

	// fill the manage exam information
	public void fillExamManageInfo(String exam, String zacotSem, String startDate, String endDate) {
		examManageTab();
		manageExamTab();
		academicPlanField();
		academicPlanFieldList(exam);
		zacotSemField();
		zacotSemFieldList(zacotSem);
		startDate(startDate);
		endDate(endDate);
		saveButton();
		handleErrorOrOkButton();

	}
}
