package com.ihsm.university.ihsmpageobjects.classchedule;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class IHSM_FacultyShowData extends BasePage {

	public IHSM_FacultyShowData(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@data-bs-target='#pills-contact']")
	private WebElement showDataTab;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strSessionId']")
	private WebElement sessionField;

	@FindBy(xpath = "//ng-select[@name='strSessionId']//div[@role='option']")
	private List<WebElement> sessionFieldList;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strBatchId']")
	private WebElement batchField;

	@FindBy(xpath = "//ng-select[@name='strBatchId']//div[@role='option']")
	private List<WebElement> batchFieldList;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strAcademicPlanId']")
	private WebElement academicPlanField;

	@FindBy(xpath = "//ng-select[@name='strAcademicPlanId']//div[@role='option']")
	private List<WebElement> academicPlanFieldList;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strSemesterId']")
	private WebElement semField;

	@FindBy(xpath = "//ng-select[@name='strSemesterId']//div[@role='option']")
	private List<WebElement> semFieldList;

	@FindBy(xpath = "//div[@id='Tab2']//div[contains(@class,'card-footer')]//button[normalize-space()='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "//input[@type='search']")
	private WebElement searchBox;

	// other scenario

	@FindBy(xpath = "//div[@id='tblFacGroupData_wrapper']//table[@id='tblFacGroupData']//tbody//tr[1]//td[2]")
	private WebElement editBox;

	@FindBy(xpath = "//div[@id='changeFaculty']//div[@class='modal-body']//input[@type='checkbox']")
	private WebElement checkBox;

	@FindBy(xpath = "//ng-select[@name='intFacultyId']")
	private WebElement facultyChooseField;

	@FindBy(xpath = "(//ng-select[@name='intFacultyId']//div[@role='option'])[2]")
	private WebElement facultyChooseFieldList;

	@FindBy(xpath = "//div[@id='changeFaculty']//div[contains(@class,'modal-footer')]//button[normalize-space()='CHANGE_FACULTY']")
	private WebElement changeFacultyButton;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	// method to perform the action no these element

	public void showDataTab() {
		safeClick(showDataTab);
	}

	public void sessionField() {
		safeClick(sessionField);
	}

	public void sessionFieldList(String list) {
		for (WebElement option : sessionFieldList) {
			if (option.getText().trim().equalsIgnoreCase(list)) {
				safeClick(option);
				return;
			}
		}
	}

	public void batchField() {
		safeClick(batchField);
	}

	public void batchFieldList(String list) {
		for (WebElement option : batchFieldList) {
			if (option.getText().trim().equalsIgnoreCase(list)) {
				safeClick(option);
				return;
			}
		}
	}

	public void academicPlanField() {
		safeClick(academicPlanField);
	}

	public void academicPlanFieldList(String list) {
		for (WebElement option : academicPlanFieldList) {
			if (option.getText().trim().equalsIgnoreCase(list)) {
				safeClick(option);
				return;
			}
		}
	}

	public void semField() {
		safeClick(semField);
	}

	public void semFieldList(String list) {
		for (WebElement option : semFieldList) {
			if (option.getText().trim().equalsIgnoreCase(list)) {
				safeClick(option);
				return;
			}
		}
	}

	public void searchButton() {
		blinkElement(searchButton);
		safeClick(searchButton);
	}

	// other scenario here
	public void searchBox(String data) {
		blinkElement(searchBox);
		searchBox.sendKeys(data);
	}

	public void editBox() {
		blinkElement(editBox);
		safeClick(editBox);
	}

	public void checkBox() {
		safeClick(checkBox);
	}

	public void facultyChooseField() {
		safeClick(facultyChooseField);
	}

	public void facultyChooseFieldList(String value) {
		safeClick(facultyChooseFieldList);
	}

	public void changeFacultyButton() {
		blinkElement(changeFacultyButton);
		safeClick(changeFacultyButton);
	}

	public void okButton() {
		blinkElement(okButton);
		safeClick(okButton);
		handleModalOk(okButton);
	}

	// fill the faculty show data
	public void fillFacultyShowData(String sessionList, String batchList, String academicList, String semList,
			String facList) {
		showDataTab();
		sessionField();
		sessionFieldList(sessionList);
		batchField();
		batchFieldList(batchList);
		academicPlanField();
		academicPlanFieldList(academicList);
		semField();
		semFieldList(semList);
		searchButton();

		// other scenario
		editBox();
		checkBox();
		facultyChooseField();
		facultyChooseFieldList(facList);
		changeFacultyButton();
		okButton();

	}

}
