package com.ihsm.university.ihsmpageobjects.classchedule;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class IHSM_ClassSchedule extends BasePage {

	public IHSM_ClassSchedule(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "(//div[@class='departmentbox'])[2]")
	private WebElement chooseDegreeFaculty; // now hardcode for the degree fac

	@FindBy(xpath = "(//div[@class='departmentbox'])[3]")
	private WebElement choosePosition; // now this one is also hard code

	@FindBy(xpath = "//a[@id='a6']//span[normalize-space()='Course Planner']")
	private WebElement coursePlannerTab;

	@FindBy(xpath = "//a[@href='#/ClassSchedule']")
	private WebElement classSchedule;

	@FindBy(name = "intSessionId")
	private WebElement sessionField;

	@FindBy(xpath = "//ng-select[@name='intSessionId']//div[@role='option']")
	private List<WebElement> sessionFieldList;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='strBatchId']")
	private WebElement batchField;

	@FindBy(xpath = "//ng-select[@name='strBatchId']//div[@role='option']")
	private List<WebElement> batchFieldList;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='strAcademicPlanId']")
	private WebElement academicPlanField;

	@FindBy(name = "intSemesterId")
	private WebElement semField;

	@FindBy(xpath = "(//ng-select[@name='strAcademicPlanId']//div[@role='option'])[3]")
	private List<WebElement> semFieldList;

	@FindBy(xpath = "//div[@id='Tab1']//ng-select[@name='SUBJECT_TYPE']")
	private WebElement subjectTypeField;

	@FindBy(xpath = "//div[@id='Tab1']//button[contains(@class, 'btnprimary') and text()='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "//a[contains(normalize-space(.),'LEC - 49')]")
	private WebElement lecField;

	@FindBy(xpath = "(//input[@name='SubjectGroup'])[1]")
	private WebElement groupFieldCheckBox1;

	@FindBy(xpath = "(//input[@name='SubjectGroup'])[2]")
	private WebElement groupFieldCheckBox2;

	@FindBy(xpath = "(//div[@id='pills-home']//input[@name='DOB'])[1]")
	private WebElement fromDateField;

	@FindBy(xpath = "(//div[@id='pills-home']//input[@name='DOB'])[2]")
	private WebElement toDateField;

	@FindBy(xpath = "//p[contains(text(),'First Week')]/following::div[contains(@class,'selectgroup-pills')][1]//label//span")
	private List<WebElement> weekFieldList2;
	
	@FindBy(xpath = "//p[contains(text(),'FIRST_WEEK')]/following::div[contains(@class,'selectgroup-pills')][1]//label//span")
	private List<WebElement> weekFieldList;

	/*
	 * @FindBy(xpath =
	 * "//p[contains(text(),'First Week')]/following::div[contains(@class,'selectgroup-pills')][1]//label//span"
	 * ) private List<WebElement> allWeekdayButtons;
	 * 
	 * // Usage - Click all buttons for (WebElement day : allWeekdayButtons) {
	 * day.click(); }
	 */

	@FindBy(xpath = "//p[contains(text(),'First Week')]/following::div[contains(@class,'selectgroup-pills')][1]//label")
	private List<WebElement> weekFieldLabels;
	
	@FindBy(xpath = "//p[contains(text(),'First Week')]/following::div[contains(@class,'selectgroup-pills')][1]//label")
	private List<WebElement> weekFieldLabels2;

	@FindBy(xpath = "//label[normalize-space()='ONLINE']")
	private WebElement onlineClass;

	@FindBy(xpath = "//input[@id='btnradio111']")
	private WebElement offlineClass;

	@FindBy(xpath = "//div[contains(label,'Room')]//select")
	private WebElement roomField;

	@FindBy(xpath = "//div[contains(label,'Room')]//option")
	private List<WebElement> roomList;

//	@FindBy(xpath = "//div[@id='Tab1']//label[contains(normalize-space(),'Time')]/following::ng-select[1]//div[@class='ng-select-container']")
//	private WebElement timeField;

	private WebElement waitForTimeDropdown() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//ng-select[.//span[contains(text(),'Time')]] | //label[contains(text(),'Time')]/following::ng-select[1]")));
	}

	@FindBy(xpath = "//div[@role='listbox']//span[@class='ng-option-label']")
	private List<WebElement> timeSlt;

	@FindBy(xpath = "//div[contains(label,'CONSTRAINTS')]//select")
	private WebElement constraintsField;

	@FindBy(xpath = "//div[contains(label,'CONSTRAINTS')]//option")
	private List<WebElement> constList;

	@FindBy(xpath = "//div[@id='btnSaveSchedule']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	@FindBy(xpath = "//div[@id='AlertErrorModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement errorButton;

	// method to perform the action on these elements
	// -------------------- Navigation --------------------

	public void chooseDegreeFaculty() {
		safeClick(chooseDegreeFaculty);
	}

	public void choosePosition() {
		safeClick(choosePosition);
	}

	public void openCoursePlannerTab() {
		safeClick(coursePlannerTab);
	}

	public void openClassSchedule() {
		safeClick(classSchedule);
	}

	// -------------------- Session --------------------

	public void selectSession(String sessionName) {
		safeClick(sessionField);

		for (WebElement option : sessionFieldList) {
			if (option.getText().trim().equalsIgnoreCase(sessionName)) {
				safeClick(option);
				return;
			}
		}
	}

	// -------------------- Batch --------------------

	public void selectBatch(String batchName) {
		safeClick(batchField);

		for (WebElement option : batchFieldList) {
			if (option.getText().trim().equalsIgnoreCase(batchName)) {
				safeClick(option);
				return;
			}
		}
	}

	// -------------------- Academic Plan --------------------

	public void selectAcademicPlanByIndex(int index) {

		// Open dropdown
		safeClick(academicPlanField);

		// Build xpath using index
		WebElement option = academicPlanField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index + "')]"));

		safeClick(option);
	}

	// -------------------- Semester --------------------

	public void selectSemester(int semesterIndex) {
		safeClick(semField);

		// Build xpath using index
		WebElement option = semField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + semesterIndex + "')]"));
		safeClick(option);
	}

	// -------------------- Subject Type --------------------

	public void selectSubjectType(int subjectTypeIndex) {
		safeClick(subjectTypeField);

		// Build xpath using index
		WebElement option = subjectTypeField
				.findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + subjectTypeIndex + "')]"));
		safeClick(option);
	}

	// -------------------- Search --------------------

	public void clickSearch() {
		safeClick(searchButton);
	}

	public void lecField() {
		safeClick(lecField);
	}

	public void clickLecByIndex(int index) {
		By lecLinks = By.xpath("//a[contains(normalize-space(.),'LEC - ')]");

		List<WebElement> elements = driver.findElements(lecLinks);

		if (index < 0 || index >= elements.size()) {
			throw new IllegalArgumentException("Invalid LEC index: " + index + ", total found: " + elements.size());
		}

		elements.get(index).click(); // index is 0-based here
	}

	public void groupField1() {
		safeClick(groupFieldCheckBox1);
	}

	public void groupdField2() {
		safeClick(groupFieldCheckBox2);
	}

	/*
	 * public void setDate(WebElement dateField, String dateIso) {
	 * JavascriptExecutor js = (JavascriptExecutor) driver;
	 * js.executeScript("arguments[0].value='" + dateIso + "';", dateField); }
	 */

	public void setDate(WebElement dateField, String dateIso) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].value = arguments[1];"
				+ "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
				+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dateField, dateIso);
	}

	public void fromDateField(String date) { // setDate(fromDateField, date); //
//		fromDateField.sendKeys(Keys.TAB); // enterDate(fromDateField, date);
//		fromDateField.sendKeys(date);
		setDate(fromDateField, date);
	}

	public void toDateField(String date) { // setDate(toDateField, date); //
//		toDateField.sendKeys(Keys.TAB); // enterDate(toDateField, date);
//		toDateField.sendKeys(date);
		setDate(toDateField, date);
	}

	/*
	 * public void fromDateField(String date) {
	 * System.out.println("Passing FromDate: " + date); WebElement freshFromDate =
	 * driver.findElement( By.
	 * xpath("//p[contains(normalize-space(),'From Date')]/following::input[@type='date'][1]"
	 * ) );
	 * 
	 * // freshFromDate.click(); freshFromDate.clear();
	 * freshFromDate.sendKeys(date); freshFromDate.sendKeys(Keys.TAB);
	 * 
	 * System.out.println("From Date UI Value: " +
	 * freshFromDate.getAttribute("value")); }
	 * 
	 * public void toDateField(String date) {
	 * 
	 * System.out.println("Passing ToDate: " + date);
	 * 
	 * WebElement freshToDate = driver.findElement( By.
	 * xpath("//p[contains(normalize-space(),'To Date')]/following::input[@type='date'][1]"
	 * ) );
	 * 
	 * // freshToDate.click(); freshToDate.clear(); freshToDate.sendKeys(date);
	 * freshToDate.sendKeys(Keys.TAB);
	 * 
	 * System.out.println("To Date UI Value: " + freshToDate.getAttribute("value"));
	 * }
	 */

	/*
	 * public void weekSelect(String weekList) {
	 * 
	 * String[] days = weekList.split(",");
	 * 
	 * for (String day : days) {
	 * 
	 * for (WebElement option : weekFieldList) {
	 * 
	 * if (option.getText().trim().equalsIgnoreCase(day.trim())) {
	 * safeClick(option); break; } } } }
	 */
	public void clearWeekSelection() {
		for (WebElement label : weekFieldList2) {
			if (label.getAttribute("class").contains("active")) {
				safeClick(label); // toggle OFF
			}
		}
	}

	public void weekSelect(String weekList) {

		clearWeekSelection(); // KEY FIX

		String[] days = weekList.split(",");

		for (String day : days) {
			for (WebElement label : weekFieldList2) {
				if (label.getText().trim().equalsIgnoreCase(day.trim())) {
					safeClick(label);
					break;
				}
			}
		}
	}

	public void onlineClass() {
		safeClick(onlineClass);
	}

	public void offlineClass() {
		safeClick(offlineClass);
	}

	public void roomField() {
		safeClick(roomField);
	}

	public void roomList(String roomName) {
		safeClick(roomField);

		for (WebElement option : roomList) {
			if (option.getText().trim().equalsIgnoreCase(roomName)) {
				safeClick(option);
				return;
			}
		}
	}

	public void selectTimeSlot(String timeValue) {

		WebElement timeDropdown = waitForTimeDropdown();
		safeClick(timeDropdown);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement option = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//ng-dropdown-panel//span[normalize-space()='" + timeValue + "']")));

		safeClick(option);
	}

	public void contraintsField() {
		safeClick(constraintsField);
	}

	public void constList(String value) {
		Select dropdown = new Select(constraintsField);
		List<WebElement> options = dropdown.getOptions();
		for (WebElement option : options) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				option.click();
				return;
			}
		}
	}

	public void saveBtn() {
		safeClick(saveBtn);
	}

	public void okButton() {
		blinkElement(okButton);
		handleModalOk(okButton);
	}

	public void errorButton() {

		try {
			if (errorButton.isDisplayed()) {
				blinkElement(errorButton);
				safeClick(errorButton);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	// fill the class scheduling information

	public void fillClassSchedulingInformation(String session, String batch, int optionIndex, int sem, int subject,
			int idx, String enterFromDate, String enterToDate, String week, String conList, String timeSelect)
			throws InterruptedException {
		openCoursePlannerTab();
		openClassSchedule();
		selectSession(session);
		selectBatch(batch);
		selectAcademicPlanByIndex(optionIndex);
		selectSemester(sem);
		selectSubjectType(subject);
		clickSearch();
		clickLecByIndex(idx);
		groupField1();

		fromDateField(enterFromDate);
		Thread.sleep(2000);
		toDateField(enterToDate);
		weekSelect(week);
		onlineClass();
//		timeField();
//		timeSlt(timeSelect);
		selectTimeSlot(timeSelect);

		constList(conList);
		saveBtn();
		okButton();
		errorButton();
	}

	public void fillClassSchedulingOffline(String session, String batch, int optionIndex, int sem, int subject, int idx,
			String enterFromDate, String enterToDate, String week, String roomName, String conList, String timeSelect)
			throws InterruptedException {
		openCoursePlannerTab();
		openClassSchedule();
		selectSession(session);
		selectBatch(batch);
		selectAcademicPlanByIndex(optionIndex);
		selectSemester(sem);
		selectSubjectType(subject);
		clickSearch();
		clickLecByIndex(idx);
		groupField1();

		fromDateField(enterFromDate);
		Thread.sleep(2000);
		toDateField(enterToDate);
		weekSelect(week);
		offlineClass();
		roomField();
		roomList(roomName);

		selectTimeSlot(timeSelect);

		constList(conList);
		saveBtn();
		okButton();
		errorButton();
	}
}
