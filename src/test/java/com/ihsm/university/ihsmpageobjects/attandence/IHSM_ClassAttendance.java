package com.ihsm.university.ihsmpageobjects.attandence;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.ihsm.university.base.BasePage;

public class IHSM_ClassAttendance extends BasePage {

	public IHSM_ClassAttendance(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@class='departmentname']")
	private WebElement chooseDegreeFaculty; // now hardcode for the degree fac

	@FindBy(xpath = "//div[@class='departmentname' and normalize-space()='Manager']")
	private WebElement choosePosition; // now this one is also hard code

	@FindBy(xpath = "//a[@id='a7']//span[normalize-space()='Teacher']")
	private WebElement teacherTab;

	@FindBy(xpath = "//a[@href='#/Class/ClassAttendance']")
	private WebElement classAttendance;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='strAcademicPlanId']")
	private WebElement academicPlanField;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='strSemesterId']")
	private WebElement courseField;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='intAcademicPlanSubjectId']")
	private WebElement subjectField;

	// other
	@FindBy(xpath = "(//button[@data-bs-target='#updateattendance'])[1]")
	private WebElement markAttActionEdit;

	@FindBy(xpath = "//table[@id='tblAttendanceStudentListMain']//thead//th[7]//input[@type='checkbox']")
	private WebElement checkBox;

	@FindBy(xpath = "//div[@id='divStudents']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okBtn;

	@FindBy(xpath = "//div[@id='tblScheduleInfo_wrapper']//table[@id='tblScheduleInfo']//tfoot//tr//th[6]//input")
	private WebElement classTypeSearchField;

	@FindBy(xpath = "//table[@id='tblAttendanceStudentListMain']//thead//td//input")
	private WebElement performanceCheckBox;

	@FindBy(xpath = "//div[@id='divStudents']//table[@id='tblAttendanceStudentListMain']//tbody//tr//td[7]//input[@type='number']")
	private List<WebElement> attendanceInputs;

	@FindBy(xpath = "//div[@id='divStudentsPerfor']//tbody//tr//td[7]//input[@type='number']")
	private List<WebElement> studentPerForField;

	// others
	@FindBy(xpath = "//div[@data-bs-target='#pills-contact']")
	private WebElement performanceScoreTab;

	@FindBy(xpath = "//table[@id='tblPndingPerformanceInfo']//button[@data-bs-target='#updateattendance']")
	private WebElement performanceEditAction;

	@FindBy(xpath = "//div[@id='divStudentsPerfor']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveStBtn;

	// other
	@FindBy(xpath = "//div[@data-bs-target='#pills-contact111']")
	private WebElement performanceAbScoreTab;

	// method to perform the action
	public void chooseDegreeFaculty() {
		blinkElement(chooseDegreeFaculty);
		safeClick(chooseDegreeFaculty);
	}

	public void choosePosition() {
		blinkElement(choosePosition);
		safeClick(choosePosition);
	}

	public void teacherTab() {
		safeClick(teacherTab);
	}

	public void classAttendance() {
		safeClick(classAttendance);
	}

	public void selectAcademicPlanByIndex(int index) {

		// Open dropdown
		safeClick(academicPlanField);

		// Build xpath using index
		WebElement option = academicPlanField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	public void selectCourseFieldByIndex(int index) {

		// Open dropdown
		safeClick(courseField);

		// Build xpath using index
		WebElement option = courseField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	public void selectSubjectFieldByIndex(int index) {

		// Open dropdown
		safeClick(subjectField);

		// Build xpath using index
		WebElement option = subjectField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	// other
	public void markAttActionEdit() {
		blinkElement(markAttActionEdit);
		safeClick(markAttActionEdit);
	}

	public void checkBox() {

		if (checkBox.isSelected()) {
			safeClick(checkBox);

		}

		// Select again
		if (!checkBox.isSelected()) {
			safeClick(checkBox);

		}

	}

	public void checkBox2() {

		if (checkBox.isSelected()) {
			safeClick(checkBox);
		}

	}

	public void saveBtn() {
		blinkElement(saveBtn);
		safeClick(saveBtn);
		handleAlertIfPresent();
	}

	public void okBtn() {
		blinkElement(okBtn);
		safeClick(okBtn);
	}

	public void classTypeSearchField(String value) {
		safeClick(classTypeSearchField);
		classTypeSearchField.sendKeys(value);
	}

	public void performanceCheckBox() {
		safeClick(performanceCheckBox);
	}

	// other
	/*
	 * @FindBy(xpath =
	 * "//table[@id='tblAttendanceStudentListMain']/tbody//tr//td[7]//input[@type='number']")
	 * private List<WebElement> attendanceInputs;
	 */

	public void enterAttendanceValues(List<Integer> values) {

		if (values.size() != attendanceInputs.size()) {
			throw new IllegalArgumentException(
					"Values count (" + values.size() + ") does not match row count (" + attendanceInputs.size() + ")");
		}

		for (int i = 0; i < attendanceInputs.size(); i++) {
			WebElement input = attendanceInputs.get(i);
			input.clear();
			input.sendKeys(String.valueOf(values.get(i)));
		}
	}

	public void studentPerForField(List<Integer> values) {

		if (values.size() != studentPerForField.size()) {
			throw new IllegalArgumentException("Values count (" + values.size() + ") does not match row count ("
					+ studentPerForField.size() + ")");
		}

		for (int i = 0; i < studentPerForField.size(); i++) {
			WebElement input = studentPerForField.get(i);
			input.clear();
			input.sendKeys(String.valueOf(values.get(i)));
		}
	}

	// others
	public void performanceScoreTab() {
		safeClick(performanceScoreTab);
	}

	public void performanceEditAction() {
		blinkElement(performanceEditAction);
		safeClick(performanceEditAction);
	}

	public void saveStBtn() {
		blinkElement(saveStBtn);
		safeClick(saveStBtn);
		handleAlertIfPresent();
	}

	// others

	public void performanceAbScoreTab() {
		safeClick(performanceAbScoreTab);
	}

	// fill the class Attendance data
	public void fillClassAttendance(int acadIdx, int crsIdx, int subIdx) {
		chooseDegreeFaculty();
		choosePosition();
		teacherTab();
		classAttendance();
		selectAcademicPlanByIndex(acadIdx);
		selectCourseFieldByIndex(crsIdx);
		selectSubjectFieldByIndex(subIdx);

		// other
		markAttActionEdit();
		checkBox();
		saveBtn();
		okBtn();

	}

	public void fillClassAttendance2(int acadIdx, int crsIdx, int subIdx, String practical,
			List<Integer> attendanceValues) {

		teacherTab();
		selectAcademicPlanByIndex(acadIdx);
		selectCourseFieldByIndex(crsIdx);
		selectSubjectFieldByIndex(subIdx);

		// other
		classTypeSearchField(practical);
		markAttActionEdit();
		performanceCheckBox();
		enterAttendanceValues(attendanceValues);
		checkBox();
		saveBtn();
		okBtn();

	}

	public void fillClassAttendance3(int acadIdx, int crsIdx, int subIdx, List<Integer> attendanceValues) {

		teacherTab();
		classAttendance();
		selectAcademicPlanByIndex(acadIdx);
		selectCourseFieldByIndex(crsIdx);
		selectSubjectFieldByIndex(subIdx);

		// other
		performanceScoreTab();
		performanceEditAction();
		studentPerForField(attendanceValues);
		saveStBtn();
		okBtn();

	}

	public void fillClassAttendance4(int acadIdx, int crsIdx, int subIdx, List<Integer> attendanceValues) {

		
		teacherTab();
		classAttendance();
		selectAcademicPlanByIndex(acadIdx);
		selectCourseFieldByIndex(crsIdx);
		selectSubjectFieldByIndex(subIdx);

		// other
		markAttActionEdit();
		checkBox2();
		saveBtn();
		okBtn();

		selectAcademicPlanByIndex(acadIdx);
		selectCourseFieldByIndex(crsIdx);
		selectSubjectFieldByIndex(subIdx);

		// other
		performanceAbScoreTab();
		performanceEditAction();
		studentPerForField(attendanceValues);
		saveStBtn();
		okBtn();

	}
}
