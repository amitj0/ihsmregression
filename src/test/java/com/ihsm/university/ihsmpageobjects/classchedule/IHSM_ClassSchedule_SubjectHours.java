package com.ihsm.university.ihsmpageobjects.classchedule;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class IHSM_ClassSchedule_SubjectHours extends BasePage {

	public IHSM_ClassSchedule_SubjectHours(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//a[@id='a6']//span[normalize-space()='Course Planner']")
	private WebElement coursePlannerTab;

	@FindBy(xpath = "//a[@href='#/CoursePlanner/SubjectCredits']")
	private WebElement subjectCredit;

	@FindBy(xpath = "//div[@data-bs-target='#pills-contact']")
	private WebElement subjectHours;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strSessionIdSearch']")
	private WebElement sessionField;

	@FindBy(xpath = "//ng-select[@name='strSessionIdSearch']//div[@role='option']")
	private List<WebElement> sessionFieldList;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strBatchIdSearch']")
	private WebElement batchField;

	@FindBy(xpath = "//ng-select[@name='strBatchIdSearch']//div[@role='option']")
	private List<WebElement> batchFieldList;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strAcademicPlanIdSearch']")
	private WebElement acaPlanField;

	@FindBy(xpath = "//ng-select[@name='strAcademicPlanIdSearch']//div[@role='option']")
	private List<WebElement> acaPlanFieldList;

	@FindBy(xpath = "//div[@id='Tab2']//ng-select[@name='strSemesterIdSearch']")
	private WebElement semField;

	@FindBy(xpath = "//ng-select[@name='strSemesterIdSearch']//div[@role='option']")
	private List<WebElement> semFieldList;

	@FindBy(xpath = "//div[contains(@class,'card-footer')]//button[normalize-space()='Search']")
	private WebElement searchButton;

	// Subject Hours Details
	@FindBy(xpath = "//input[@type='search']")
	private WebElement inputSearch;

	@FindBy(xpath = "//table[@id='tblStudyPlan']//tbody//tr[1]//td[2]")
	private WebElement actionEditButton;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intTotalCreditHours']")
	private WebElement totalCreditHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intCreditLectureHours']")
	private WebElement totalLecHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intCreditPracticalhours']")
	private WebElement practicalHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intSelfStudyHours']")
	private WebElement studyHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intTotalAcademicHours']")
	private WebElement totalAcaHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intAcademicLectureHours']")
	private WebElement lectHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intAcademicPracticalHours']")
	private WebElement practicalHoursAc;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intAcademicSeminarHours']")
	private WebElement saminarHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intAcademicLabHours']")
	private WebElement labHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intAcademicFacultytativeHours']")
	private WebElement facHours;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//ng-select[@name='strExamTypeId']")
	private WebElement examTypeField;

	@FindBy(xpath = "//ng-select[@name='strExamTypeId']//div[@role='option']")
	private List<WebElement> examTypeList;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intControlPasssingMarks']")
	private WebElement controlPassinigMarks;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intMaxControlMark']")
	private WebElement maxControlMarks;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intExamPassingMarks']")
	private WebElement examPassingMarks;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//div[@class='modal-content']//div[2]//input[@name='intMaxExamMarks']")
	private WebElement maxExamMarks;

	@FindBy(xpath = "//div[@id='SaveCreditHours']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveButton;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	// method to perfom the action on these locators
	public void coursePlannerTab() {
		safeClick(coursePlannerTab);
	}

	public void subjectCredit() {
		safeClick(subjectCredit);
	}

	public void subjectHours() {
		safeClick(subjectHours);
	}

	public void sessionField() {
		safeClick(sessionField);
	}

	public void sessionFieldList(String value) {
		safeClick(sessionField);
		for (WebElement option : sessionFieldList) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				safeClick(option);
				return;
			}
		}

	}

	public void batchField() {
		safeClick(batchField);
	}

	public void batchFieldList(String value) {
		safeClick(batchField);
		for (WebElement option : batchFieldList) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				safeClick(option);
				return;
			}
		}

	}

	public void acaPlanField() {
		safeClick(acaPlanField);
	}

	public void selectAcademicPlanByIndex(int index) {

		// Open dropdown
		safeClick(acaPlanField);

		// Build xpath using index
		WebElement option = acaPlanField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	public void semField() {
		safeClick(semField);
	}

	public void semFieldList(String value) {
		safeClick(semField);
		for (WebElement option : semFieldList) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				safeClick(option);
				return;
			}
		}
	}

	public void searchButton() {
		blinkElement(searchButton);
		safeClick(searchButton);
	}

	public void inputSearch(String data) {
		blinkElement(inputSearch);
		safeClick(inputSearch);
		inputSearch.sendKeys(data);
	}

	public void actionEditButton() {
		blinkElement(actionEditButton);
		safeClick(actionEditButton);
	}

	public void totalCreditHours(String marks) {
		safeClick(totalCreditHours);
		totalCreditHours.clear();
		totalCreditHours.sendKeys(marks);
	}

	public void totalLecHours(String marks) {
		totalLecHours.clear();
		totalLecHours.sendKeys(marks);
	}

	public void practicalHours(String marks) {
		practicalHours.clear();
		practicalHours.sendKeys(marks);
	}

	public void studyHours(String marks) {
		studyHours.clear();
		studyHours.sendKeys(marks);
	}

	public void totalAcaHours(String marks) {
		totalAcaHours.clear();
		totalAcaHours.sendKeys(marks);
	}

	public void lectHours(String marks) {
		lectHours.clear();
		lectHours.sendKeys(marks);
	}

	public void practicalHoursAc(String marks) {
		practicalHoursAc.clear();
		practicalHoursAc.sendKeys(marks);
	}

	public void saminarHours(String marks) {
		saminarHours.clear();
		saminarHours.sendKeys(marks);
	}

	public void labHours(String marks) {
		labHours.clear();
		labHours.sendKeys(marks);
	}

	public void facHours(String marks) {
		facHours.clear();
		facHours.sendKeys(marks);
	}

	public void examTypeField() {
		safeClick(examTypeField);
	}

	public void examTypeFieldList(String examType) {
		scrollToElement(examTypeField);
		safeClick(examTypeField);
		for (WebElement option : examTypeList) {
			if (option.getText().trim().equalsIgnoreCase(examType)) {
				safeClick(option);
				return;
			}
		}
	}

	public void controlPassinigMarks(String marks) {
		controlPassinigMarks.clear();
		controlPassinigMarks.sendKeys(marks);
	}

	public void maxControlMarks(String marks) {
		maxControlMarks.clear();
		maxControlMarks.sendKeys(marks);
	}

	public void examPassingMarks(String marks) {
		examPassingMarks.clear();
		examPassingMarks.sendKeys(marks);
	}

	public void maxExamMarks(String marks) {
		maxExamMarks.clear();
		maxExamMarks.sendKeys(marks);
	}

	public void saveButton() {
		blinkElement(saveButton);
		safeClick(saveButton);
		handleAlertIfPresent();
	}

	public void okButtonPop() {
		blinkElement(okButton);
		safeClick(okButton);
	}

	// fill the subject hours information

	public void fillSubjectHoursInformation(String session, String batch, int acadIdx, String semester,
			 String crMarks, String lecHours, String praHours, String stHours, String acHours,
			String lecHours2, String prHoursAcs, String semiHours, String labHours, String facHours, String examType,
			String pMarks, String mxCtrlMark, String ePassMarks, String maxExamMarks) {
		coursePlannerTab();
		subjectCredit();
		subjectHours();
		sessionField();
		sessionFieldList(session);
		batchField();
		batchFieldList(batch);
		acaPlanField();
		selectAcademicPlanByIndex(acadIdx);
		semField();
		semFieldList(semester);
		searchButton();

		// hours details
//		inputSearch(inputData);
		actionEditButton();
		totalCreditHours(crMarks);
		totalLecHours(lecHours);
		practicalHours(praHours);
		studyHours(stHours);
		totalAcaHours(acHours);
		lectHours(lecHours2);
		practicalHoursAc(prHoursAcs);
		saminarHours(semiHours);
		labHours(labHours);
		facHours(facHours);
		examTypeField();
		examTypeFieldList(examType);
		controlPassinigMarks(pMarks);
		maxControlMarks(mxCtrlMark);
		examPassingMarks(ePassMarks);
		maxExamMarks(maxExamMarks);
		saveButton();
		okButtonPop();

	}
}
