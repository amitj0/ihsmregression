package com.ihsm.university.ihsmpageobjects.employee.designation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Designation_EmploymentRights extends BasePage {

	public Designation_EmploymentRights(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@data-bs-target='#pills-professional']")
	private WebElement designationTab;

	@FindBy(xpath = "//div[@id='btnempright']//span")
	private WebElement addEmploymentRightsBtn;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//ng-select[@name='SelectedPosition']")
	private WebElement jobTypeDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> jobTypeDropdownOptions;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//ng-select[@name='strRating']")
	private WebElement ratingDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> ratingDropdownOptions;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//ng-select[@name='selectedStatus']")
	private WebElement operationStatusDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> operationStatusDropdownOptions;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//input[@name='IIN']")
	private WebElement orderNoField;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//input[@name='dttFromDatePosition']")
	private WebElement probationDateField;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//input[@name='dttProbationaryTill']")
	private WebElement probationTillDateField;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//ng-select[@name='selectedDepart']")
	private WebElement departmentDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> departmentDropdownOptions;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//ng-select[@name='selectedPartPosition']")
	private WebElement positionPartDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> positionPartDropdownOptions;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//ng-select[@name='type']")
	private WebElement programDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> programDropdownOptions;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//input[@name='basis']")
	private WebElement pensionField;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//input[@name='rPensionFrom']")
	private WebElement pensionFromField;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//input[@name='strEmpBookHistoryNo']")
	private WebElement employmentBookHistoryNoField;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//input[@type='number']")
	private WebElement salaryField;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//textarea[@name='Notes']")
	private WebElement notesField;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//input[@id='inlineCheckbox1']")
	private WebElement mainPositionCheckbox;

	@FindBy(xpath = "//div[@id='staticBackdropPosition']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to perform the action
	public void designationTab() {
		safeClick(designationTab);
	}

	public void addEmploymentRightsBtn() {
		blinkElement(addEmploymentRightsBtn);
		safeClick(addEmploymentRightsBtn);
	}

	public void jobTypeDropdownField() {
		safeClick(jobTypeDropdownField);
	}

	public void jobTypeDropdownOptions(String jobType) {
		safeClick(jobTypeDropdownField);
		for (WebElement option : jobTypeDropdownOptions) {
			if (option.getText().equalsIgnoreCase(jobType)) {
				safeClick(option);
				return;
			}
		}
	}

	public void ratingDropdownField() {
		safeClick(ratingDropdownField);
	}

	public void ratingDropdownOptions(String rating) {
		safeClick(ratingDropdownField);
		for (WebElement option : ratingDropdownOptions) {
			if (option.getText().equalsIgnoreCase(rating)) {
				safeClick(option);
				return;
			}
		}
	}

	public void operationStatusDropdownField() {
		safeClick(operationStatusDropdownField);
	}

	public void operationStatusDropdownOptions(String operationStatus) {
		safeClick(operationStatusDropdownField);
		for (WebElement option : operationStatusDropdownOptions) {
			if (option.getText().equalsIgnoreCase(operationStatus)) {
				safeClick(option);
				return;
			}
		}
	}

	public void orderNoField(String orderNo) {
		safeClick(orderNoField);
		orderNoField.sendKeys(orderNo);
	}

	public void probationDateField(String probationDate) {
		enterDate(probationDateField, probationDate);
	}

	public void probationTillDateField(String probationTillDate) {
		enterDate(probationTillDateField, probationTillDate);
	}

	public void departmentDropdownField() {
		safeClick(departmentDropdownField);
	}

	public void departmentDropdownOptions(String department) {
		safeClick(departmentDropdownField);
		for (WebElement option : departmentDropdownOptions) {
			if (option.getText().equalsIgnoreCase(department)) {
				safeClick(option);
				return;
			}
		}
	}

	public void positionPartDropdownField() {
		blinkElement(positionPartDropdownField);
		safeClick(positionPartDropdownField);
	}

	public void positionPartDropdownOptions(String positionPart) {
		safeClick(positionPartDropdownField);
		for (WebElement option : positionPartDropdownOptions) {
			if (option.getText().equalsIgnoreCase(positionPart)) {
				safeClick(option);
				return;
			}
		}
	}

	public void programDropdownField() {
		safeClick(programDropdownField);
	}

	public void programDropdownOptions(String program) {
		safeClick(programDropdownField);
		for (WebElement option : programDropdownOptions) {
			if (option.getText().equalsIgnoreCase(program)) {
				safeClick(option);
				return;
			}
		}
	}

	public void basisField(String basis) {
		pensionField.sendKeys(basis);
	}

	public void pensionFromField(String pensionFrom) {
		pensionFromField.sendKeys(pensionFrom);
	}

	public void employmentBookHistoryNoField(String employmentBookHistoryNo) {
		employmentBookHistoryNoField.sendKeys(employmentBookHistoryNo);
	}

	public void salaryField(String salary) {
		salaryField.sendKeys(salary);
	}

	public void notesField(String notes) {
		notesField.sendKeys(notes);
	}

	public void mainPositionCheckbox() {
		blinkElement(mainPositionCheckbox);
		safeClick(mainPositionCheckbox);
	}

	public void saveBtn() {
		blinkElement(saveBtn);
		try {
			captureScreenshot("Employment Rights Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}

		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}
	
	public boolean isDesEmpRightInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill employment rights form
	public Designation_Position fillEmploymentRightsForm(String jobType, String rating, String operationStatus,
			String orderNo, String probationDate, String probationTillDate, String department, String positionPart,
			String program, String basis, String pensionFrom, String employmentBookHistoryNo, String salary,
			String notes) {
		designationTab();
		addEmploymentRightsBtn();
		jobTypeDropdownField();
		jobTypeDropdownOptions(jobType);
		ratingDropdownField();
		ratingDropdownOptions(rating);
		operationStatusDropdownField();
		operationStatusDropdownOptions(operationStatus);
		orderNoField(orderNo);
		probationDateField(probationDate);
		probationTillDateField(probationTillDate);
		departmentDropdownField();
		departmentDropdownOptions(department);
		positionPartDropdownField();
		positionPartDropdownOptions(positionPart);
		programDropdownField();
		programDropdownOptions(program);
		basisField(basis);
		pensionFromField(pensionFrom);
		employmentBookHistoryNoField(employmentBookHistoryNo);
		salaryField(salary);
		notesField(notes);
		mainPositionCheckbox();
		saveBtn();
		okButtonSuccessPopup();
		return new Designation_Position(driver);

	}

}
