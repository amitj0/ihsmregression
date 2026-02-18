package com.ihsm.university.ihsmpageobjects.attandence;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class IHSM_UnitMarks extends BasePage {

	public IHSM_UnitMarks(WebDriver driver) {
		super(driver);
	}

	// locate the web element here

	@FindBy(xpath = "//div[@class='departmentname']")
	private WebElement chooseDegreeFaculty; // now hardcode for the degree fac

	@FindBy(xpath = "//div[@class='departmentname' and normalize-space()='Faculty']")
	private WebElement choosePosition; // now this one is also hard code

	@FindBy(xpath = "//a[@id='a7']//span[normalize-space()='Teacher']")
	private WebElement teacherTab;

	@FindBy(xpath = "//a[@href='#/Class/UnitMarks']")
	private WebElement unitMarksTab;

	@FindBy(xpath = "//input[@name='rClass']")
	private WebElement checkbox;

	@FindBy(name = "dttUnitDate")
	private WebElement unitDateField;

	@FindBy(xpath = "(//input[@name='intTotalCreditHours'])[1]")
	private WebElement input1;

	@FindBy(xpath = "(//input[@name='intTotalCreditHours'])[2]")
	private WebElement input2;

	@FindBy(xpath = "//input[@name='intCreditPracticalhours']")
	private List<WebElement> inputListOfAllField;

	@FindBy(xpath = "//div[@id='divUnitMarksStudents']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//div[@id='AlertErrorModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okErrorButton;
	
	@FindBy(xpath = "(//h5[@id='staticBackdropLabel']//following-sibling::button[@class='btn-close'])[3]")
	private WebElement cutButton;

	// others
	@FindBy(xpath = "//div[@data-bs-target='#pills-contact']")
	private WebElement showMarksTab;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strSessionIdSearch']")
	private WebElement sessionField;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strBatchIdSearch']")
	private WebElement batchField;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strAcademicPlanIdSearch']")
	private WebElement academicPlanField;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strSemesterIdSearch']")
	private WebElement courseSearchField;

	@FindBy(xpath = "//div[@id='pills-contact']//button[contains(@class, 'btnprimary') and text()='Search']")
	private WebElement searchButton;
	
	@FindBy(xpath = "//table[@id='tblUnitPlanReport']//tbody//tr[1]//td[2]")
	private WebElement seeDataField;
	
	@FindBy(xpath = "//div[@id='divUnitMarksReport']//button[contains(@class, 'btnsecondary') and text()='Close']")
	private WebElement closeBtn;
	


	// method to perform the actions

	public void chooseDegreeFaculty() {
		blinkElement(chooseDegreeFaculty);
		safeClick(chooseDegreeFaculty);
	}

	public void choosePosition() {
		safeClick(choosePosition);
	}

	public void teacherTab() {
		safeClick(teacherTab);
	}

	public void unitMarksTab() {
		safeClick(unitMarksTab);
	}

	public void checkbox() {
		safeClick(checkbox);
	}

	public void unitDateField(String date) {
		blinkElement(unitDateField);
		safeClick(unitDateField);
		unitDateField.sendKeys(date);
	}

	public void input1(String inputFirst) {
		input1.clear();
		input1.sendKeys(inputFirst);
	}

	public void input2(String inputSecond) {
		input2.clear();
		input2.sendKeys(inputSecond);
	}

	public void enterInputAllInputMarks(List<Integer> values) {

		if (values.size() != inputListOfAllField.size()) {
			throw new IllegalArgumentException("Values count (" + values.size() + ") does not match row count ("
					+ inputListOfAllField.size() + ")");
		}

		for (int i = 0; i < inputListOfAllField.size(); i++) {
			WebElement input = inputListOfAllField.get(i);
			input.clear();
			input.sendKeys(String.valueOf(values.get(i)));
		}
	}

	public void saveBtn() {
		safeClick(saveBtn);
	}
	
	public void okErrorButton() {
		blinkElement(okErrorButton);
		safeClick(okErrorButton);
	}
	
	public void cutButton() {
		safeClick(cutButton);
	}

	// fill the unit marks
	public void fillUnitMarksInformation(String date, String input1, String input2, List<Integer> values) {

		chooseDegreeFaculty();
		choosePosition();
		unitMarksTab();
		checkbox();
		unitDateField(date);
		input1(input1);
		input2(input2);
		enterInputAllInputMarks(values);
		saveBtn();
		okErrorButton();
		cutButton();
		
	}

	// method to perform the actions

	public void showMarksTab() {
		safeClick(showMarksTab);
	}

	public void selectSessionList(int index) {

		// Open dropdown
		safeClick(sessionField);

		// Build xpath using index
		WebElement option = sessionField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	public void selectBatchList(int index) {

		// Open dropdown
		safeClick(batchField);

		// Build xpath using index
		WebElement option = batchField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	public void selectAcademicList(int index) {

		// Open dropdown
		safeClick(academicPlanField);

		// Build xpath using index
		WebElement option = academicPlanField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	public void selectCourseSearchList(int index) {

		// Open dropdown
		safeClick(courseSearchField);

		// Build xpath using index
		WebElement option = courseSearchField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	public void searchButton() {
		blinkElement(searchButton);
		safeClick(searchButton);
	}
	
	public void seeDataField() {
		blinkElement(seeDataField);
		safeClick(seeDataField);
	}
	
	public void closeBtn() {
		scrollToElement(closeBtn);
		blinkElement(closeBtn);
		safeClick(closeBtn);
	}

	// show unit marks
	public void fillUnitMarksShowInformation(int session, int batch, int academics, int course) {

		chooseDegreeFaculty();
		choosePosition();
		unitMarksTab();
		showMarksTab();
		selectSessionList(session);
		selectBatchList(batch);
		selectAcademicList(academics);
		selectCourseSearchList(course);
		searchButton();
		seeDataField();
		closeBtn();
	

	}
}
