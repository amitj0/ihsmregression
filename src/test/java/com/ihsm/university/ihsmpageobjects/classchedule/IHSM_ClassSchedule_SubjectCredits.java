package com.ihsm.university.ihsmpageobjects.classchedule;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class IHSM_ClassSchedule_SubjectCredits extends BasePage {

	public IHSM_ClassSchedule_SubjectCredits(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[@id='a6']//span[normalize-space()='Course Planner']")
	private WebElement coursePlannerTab;

	@FindBy(xpath = "//a[@href='#/CoursePlanner/SubjectCredits']")
	private WebElement subjectCredit;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='strSessionId']")
	private WebElement sessionField;

	@FindBy(xpath = "//ng-select[@name='strSessionId']//div[@role='option']")
	private List<WebElement> sessionFieldList;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='strBatchId']")
	private WebElement batchField;

	@FindBy(xpath = "//ng-select[@name='strBatchId']//div[@role='option']")
	private List<WebElement> batchFieldList;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='strAcademicPlanId']")
	private WebElement acaPlanField;

	@FindBy(xpath = "//ng-select[@name='strAcademicPlanId']//div[@role='option']")
	private List<WebElement> acaPlanFieldList;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='strSemesterId']")
	private WebElement semField;

	@FindBy(xpath = "//ng-select[@name='strSemesterId']//div[@role='option']")
	private List<WebElement> semFieldList;

	@FindBy(xpath = "//table[@id='tblTotalSubjects']//tbody//tr[1]//td[1]//input")
	private WebElement checkBox;

	@FindBy(xpath = "//div[contains(@class,'card-footer')]//button[normalize-space()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okBtn;

	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnSuccessTextContent'])[1]")
	private WebElement modalSuccessMsg;

	// method to perform the action

	public void coursePlannerTab() {
		safeClick(coursePlannerTab);
	}

	public void subjectCredit() {
		safeClick(subjectCredit);
	}

	public void sessionField() {
		safeClick(sessionField);
	}

	/*
	 * public void sessionFieldList(String value) { safeClick(sessionField); for
	 * (WebElement option : sessionFieldList) { if
	 * (option.getText().trim().equalsIgnoreCase(value)) { safeClick(option);
	 * return; } }
	 * 
	 * }
	 */
	public void sessionFieldList(String value) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Wait until dropdown is visible + clickable, then click it
		wait.until(ExpectedConditions.visibilityOf(sessionField));
		wait.until(ExpectedConditions.elementToBeClickable(sessionField));
		safeClick(sessionField);

		// Wait until options are visible (and not empty)
		wait.until(ExpectedConditions.visibilityOfAllElements(sessionFieldList));

		for (WebElement option : sessionFieldList) {
			String text = option.getText().trim();
			if (text.equalsIgnoreCase(value.trim())) {

				// Wait for this specific option to be visible + clickable
				wait.until(ExpectedConditions.visibilityOf(option));
				wait.until(ExpectedConditions.elementToBeClickable(option));
				safeClick(option);
				return;
			}
		}

		throw new NoSuchElementException("Session option not found: " + value);
	}

	public void batchField() {
		safeClick(batchField);
	}

	/*
	 * public void batchFieldList(String value) { safeClick(batchField); for
	 * (WebElement option : batchFieldList) { if
	 * (option.getText().trim().equalsIgnoreCase(value)) { safeClick(option);
	 * return; } }
	 * 
	 * }
	 */
	public void batchFieldList(String value) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Wait until dropdown is visible + clickable, then click it
		wait.until(ExpectedConditions.visibilityOf(batchField));
		wait.until(ExpectedConditions.elementToBeClickable(batchField));
		scrollIntoView(batchField);
		safeClick(batchField);

		// Wait until options are visible
		wait.until(ExpectedConditions.visibilityOfAllElements(batchFieldList));

		for (WebElement option : batchFieldList) {
			String text = option.getText().trim();
			if (text.equalsIgnoreCase(value.trim())) {

				// Wait for this specific option to be visible + clickable
				wait.until(ExpectedConditions.visibilityOf(option));
				wait.until(ExpectedConditions.elementToBeClickable(option));
				scrollIntoView(option);
				safeClick(option);
				return;
			}
		}

		throw new NoSuchElementException("Batch option not found: " + value);
	}

	public void acaPlanField() {
		safeClick(acaPlanField);
	}

	/*
	 * public void selectAcademicPlanOption(String value) { safeClick(acaPlanField);
	 * 
	 * for (WebElement option : acaPlanFieldList) { if
	 * (option.getText().trim().equalsIgnoreCase(value)) { safeClick(option);
	 * return; } } }
	 */
	public void selectAcademicPlanOption(String value) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Dropdown visible + clickable
		wait.until(ExpectedConditions.visibilityOf(acaPlanField));
		wait.until(ExpectedConditions.elementToBeClickable(acaPlanField));
		scrollIntoView(acaPlanField);
		safeClick(acaPlanField);

		// Options visible
		wait.until(ExpectedConditions.visibilityOfAllElements(acaPlanFieldList));

		for (WebElement option : acaPlanFieldList) {
			String text = option.getText().trim();
			if (text.equalsIgnoreCase(value.trim())) {

				// Option visible + clickable
				wait.until(ExpectedConditions.visibilityOf(option));
				wait.until(ExpectedConditions.elementToBeClickable(option));
				scrollIntoView(option);
				safeClick(option);
				return;
			}
		}

		throw new NoSuchElementException("Academic plan option not found: " + value);
	}

	public void semField() {
		safeClick(semField);
	}

	/*
	 * public void semFieldList(String value) { safeClick(semField); for (WebElement
	 * option : semFieldList) { if (option.getText().trim().equalsIgnoreCase(value))
	 * { safeClick(option); return; } }
	 * 
	 * }
	 */
	
	public void semFieldList(String value) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Dropdown visible + clickable
	    wait.until(ExpectedConditions.visibilityOf(semField));
	    wait.until(ExpectedConditions.elementToBeClickable(semField));
	    scrollIntoView(semField);
	    safeClick(semField);

	    // Options visible
	    wait.until(ExpectedConditions.visibilityOfAllElements(semFieldList));

	    for (WebElement option : semFieldList) {
	        String text = option.getText().trim();
	        if (text.equalsIgnoreCase(value.trim())) {

	            // Option visible + clickable
	            wait.until(ExpectedConditions.visibilityOf(option));
	            wait.until(ExpectedConditions.elementToBeClickable(option));
	            scrollIntoView(option);
	            safeClick(option);
	            return;
	        }
	    }

	    throw new NoSuchElementException("Semester option not found: " + value);
	}


	public void checkboxAccordingToSubject(String subjectName) {
		WebElement checkbox = driver.findElement(By.xpath(
				"//td[normalize-space(text())='" + subjectName + "']/preceding-sibling::td//input[@type='checkbox']"));
		if (!checkbox.isSelected()) {
			checkbox.click();
		}
	}

	public WebElement inputByValue(String value) {
		return driver.findElement(By.xpath("//input[@value='" + value + "']"));
	}

	public void saveButton() {
		blinkElement(saveBtn);
		safeClick(saveBtn);
	}

	public void okBtn() {
		blinkElement(okBtn);
		handleModalOk(okBtn);
	}

	public String modalSuccessMsg() throws TimeoutException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(modalSuccessMsg));
		wait.until(d -> !modalSuccessMsg.getText().trim().isEmpty());
		return modalSuccessMsg.getText().trim();
	}

	// fill the subject credit information

	public String fillSubjectCreditInformation(String sessionField, String batchField, String acaPlanField,
			String semField, String subjectName /* String value */) throws TimeoutException {
		coursePlannerTab();
		subjectCredit();
		sessionField();
		sessionFieldList(sessionField);
		batchFieldList(batchField);
		selectAcademicPlanOption(acaPlanField);
		semFieldList(semField);
		checkboxAccordingToSubject(subjectName);
		saveButton();
		
		String msg = modalSuccessMsg();
		okBtn();

		return msg;

	}
}
