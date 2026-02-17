package com.ihsm.university.ihsmpageobjects.student.status;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Status_Status extends BasePage {

	public Status_Status(WebDriver driver) {
		super(driver);
	}

	// locate the web element here

	@FindBy(xpath = "//div[@data-bs-target='#pills-contact7']")
	private WebElement statusTab;

	@FindBy(xpath = "//span[@data-bs-target='#AddStatusId']")
	private WebElement statusStatusTab;

	@FindBy(xpath = "//div[@id='AddStatusId']//ng-select[@name='GUARDIAN']//div[contains(@class,'ng-select-container')]")
	private WebElement chooseDocStatusField;

	@FindBy(xpath = "//div[@role='listbox']//div[@role='option']")
	private List<WebElement> chooseDocStatusOptions;

	@FindBy(xpath = "//div[@id='AddStatusId']//input[@placeholder='Date']")
	private WebElement dateField;

	@FindBy(xpath = "//div[@id='AddStatusId']//div[@class='modal-content']//input[@placeholder='Order No']")
	private WebElement orderNumberField;

	@FindBy(xpath = "//div[@id='AddStatusId']//div[@class='modal-content']//textarea[@placeholder='Comments']")
	private WebElement commentsField;

	@FindBy(xpath = "//div[@id='AddStatusId']//ngx-dropzone[@class='uploadresyou']//input[@type='file']")
	private WebElement uploadDocField;

	@FindBy(xpath = "//div[@id='AddStatusId']//div[@class='modal-content']//button[contains(@class,'btnprimary') and text()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	@FindBy(xpath = "//a[@id='a3']")
	private WebElement studentSearchMenu;

	@FindBy(xpath = "//a[@href='#/Student/SearchStudents']")
	private WebElement searchStudentsSubMenu;

	@FindBy(xpath = "(//div[@id='SearchStudent']//input)[1]")
	private WebElement studentSearchField;

	@FindBy(xpath = "//div[@class='card-footer']//button[normalize-space()='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "(//table[@id='tblStudentInfo']//tbody//td)[7]")
	private WebElement studentEnrollnmentNumber;

	// methods to perform actions on the web elements
	public void statusTab() {
		safeClick(statusTab);
	}

	public void statusStatusTab() {
		blinkElement(statusStatusTab);
		safeClick(statusStatusTab);
	}

	public void chooseDocStatusOptions(String statusOption) {
		safeClick(chooseDocStatusField);
		for (WebElement option : chooseDocStatusOptions) {
			if (option.getText().trim().equalsIgnoreCase(statusOption)) {
				safeClick(option);
				return;
			}
		}
	}

	public void dateField(String date) {
		enterDate(dateField, date);
	}

	public void orderNumberField(String orderNo) {
		orderNumberField.sendKeys(orderNo);
	}

	public void commentsField(String comments) {
		commentsField.sendKeys(comments);
	}

	public void uploadDocField(String filePath) {
		uploadDocField.sendKeys(filePath);
	}

	public void saveBtn() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Status Status Information Saved");
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

	public boolean isStatusStatusSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	public void studentSearchMenu() {

		String enroll = studentEnrollnmentNumber.getText();

		safeClick(studentSearchMenu);
		studentSearchMenu.clear();
		studentSearchMenu.sendKeys(enroll);
	}

	public void searchStudentsSubMenu() {
		safeClick(searchStudentsSubMenu);
	}

	public void studentSearchField(String keys) {
		safeClick(studentSearchField);
		studentSearchField.sendKeys(keys);
	}

	public void searchButton() {
		safeClick(searchButton);
	}

	public void studentEnrollnmentNumber() throws InterruptedException {
		blinkElementToVerify(studentEnrollnmentNumber);

		Thread.sleep(5000);
	}

	// fill status status form
	public Status_ExamStatus fillStatusStatusForm(String statusOption, String date, String orderNo, String comments,
			String filePath) {
		statusTab();
		statusStatusTab();
		chooseDocStatusOptions(statusOption);
		dateField(date);
		orderNumberField(orderNo);
		commentsField(comments);
		uploadDocField(filePath);
		saveBtn();
		okButtonSuccessPopup();
		
		
		return new Status_ExamStatus(driver);
	}

	public void searchStudentByEnroll(String enroll) {
		studentSearchMenu();
		searchStudentsSubMenu();
		studentSearchField(enroll);
		searchButton();
		
	}

}
