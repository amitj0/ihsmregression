package com.ihsm.university.ihsmpageobjects.exammanagement;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class IHSM_ManageExam extends BasePage {

	public IHSM_ManageExam(WebDriver driver) {
		super(driver);
	}

	private String lastSuccessMsg;

	
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
	
	
	@FindBy(xpath = "(//div[contains(@class,'text-danger') and contains(@class,'error')])[3]")
	private WebElement labelErrorMsg;
	
	// logout here
	@FindBy(xpath = "//span[@class='d-block']")
	private WebElement usernameFieldOfLogout;
	
	@FindBy(xpath = "//a[@class='dropdown-item' and normalize-space()='Sign Out']")
	private WebElement signOutButton;
	
	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnSuccessTextContent'])[1]")
	private WebElement modalSuccessMsg;
	
	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnErrorTextContent'])[1]")
	private WebElement modalErrorMsg;
	
	@FindBy(xpath = "(//div[@class='text-danger error' and text()='This Field is Required.'])[1]")
	private WebElement fieldRequiredErrorMsg;


	
	// method to perform logout action
	public void logout() {
		blinkElement(usernameFieldOfLogout);
		safeClick(usernameFieldOfLogout);
		blinkElement(signOutButton);
		safeClick(signOutButton);
		handleAlertIfPresent();
	}

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
	
	public String labelErrorMsg() {
		String errorMsg = labelErrorMsg.getText();
		System.out.println("Label Error Message: " + errorMsg);
		return errorMsg;
	}
	
	public String modalErrorMsg() throws TimeoutException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(modalErrorMsg));
		wait.until(d -> !modalErrorMsg.getText().trim().isEmpty());
		return modalErrorMsg.getText().trim();
	}
	
	public String getLastErrorMsg() {
        return lastSuccessMsg;
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
		
		lastSuccessMsg = modalErrorMsg();
		handleErrorOrOkButton();
		
		// logout after performing the action
		logout();

	}
}
