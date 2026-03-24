package com.ihsm.university.ihsmpageobjects.classchedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
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
	
	private String errorModalMessage;

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

	@FindBy(xpath = "//ng-select[@name='strAcademicPlanId']//div[@role='option']")
	private List<WebElement> academicPlanFieldList;

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
	
	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnErrorTextContent'])[1]")
	private WebElement errorModalMsg;

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
	public void sessionField() {
		blinkElement(sessionField);
		safeClick(sessionField);
	}

	public void selectSession(String sessionName) {
		safeClick(sessionField);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(sessionFieldList));

		for (WebElement option : sessionFieldList) {
			if (option.getText().trim().equalsIgnoreCase(sessionName)) {
				safeClick(option);
				return;
			}
		}
		System.out.println("Session not found: " + sessionName);
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

	public void selectAcademicPlan(String acPlan) {
		safeClick(academicPlanField);

		for (WebElement option : academicPlanFieldList) {
			if (option.getText().trim().equalsIgnoreCase(acPlan)) {
				safeClick(option);
				return;
			}
		}

		/*
		 * // Build xpath using index WebElement option = academicPlanField
		 * .findElement(By.xpath(".//div[@role='option' and contains(@id,'-" + index +
		 * "')]"));
		 * 
		 * safeClick(option);
		 */
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

	public void clickSubjectWiseDistribution(String subjectName, String columnType) {

		/*
		 * String xpath = "//div[contains(@class,'subject-wise-box')]" +
		 * "[.//div[@class='fw-bold'][contains(.,'" + subjectName + "')]]" +
		 * "//a[div[contains(.,'" + columnType + "')]]";
		 */

		String xpath = "//div[contains(@class,'subject-wise-box')]" + "[.//div[contains(@class,'fw-bold')][contains(.,'"
				+ subjectName + "')]]" + "//a[contains(.,'" + columnType + "')]";

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		int attempts = 0;
		while (attempts < 3) {
			try {
				WebElement badge = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", badge);
				Thread.sleep(500);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", badge);

				System.out.println("Clicked " + columnType + " for: " + subjectName);
				break;

			} catch (StaleElementReferenceException e) {
				attempts++;
				System.out.println("Stale - Retrying: " + attempts);
				if (attempts == 3)
					throw new RuntimeException("Failed after 3 attempts", e);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public void clickLecByIndexPrac(int index) {
		By lecLinks = By.xpath("//a[contains(normalize-space(.),'PRA - ')]");

		List<WebElement> elements = driver.findElements(lecLinks);

		if (index < 0 || index >= elements.size()) {
			throw new IllegalArgumentException("Invalid LEC index: " + index + ", total found: " + elements.size());
		}

		elements.get(index).click(); // index is 0-based here
	}

	public void selectCheckboxBySubjectAndGroup(String subjectName, String teacherName, String groupNumber) {

		// Try multiple XPath strategies
		String[] xpaths = {
				// Option 1 - teacher + group only (most reliable)
				"//tr[td[contains(.,'" + teacherName + "')] " + "and td//label[normalize-space()='" + groupNumber
						+ "']]//input[@type='checkbox']",

				// Option 2 - subject without (Main) + teacher + group
				"//tr[td[contains(.,'Russian language') and contains(.,'" + teacherName + "')] "
						+ "and td//label[normalize-space()='" + groupNumber + "']]//input[@type='checkbox']",

				// Option 3 - full subject + teacher + group
				"//tr[td[contains(.,'" + subjectName + "') and contains(.,'" + teacherName + "')] "
						+ "and td//label[normalize-space()='" + groupNumber + "']]//input[@type='checkbox']" };

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement checkbox = null;

		for (String xpath : xpaths) {
			try {
				System.out.println("Trying XPath: " + xpath);
				checkbox = driver.findElement(By.xpath(xpath));
				System.out.println(" Found with XPath: " + xpath);
				break;
			} catch (Exception e) {
				System.out.println(" Not found, trying next...");
			}
		}

		if (checkbox == null) {
			throw new RuntimeException(
					" Checkbox not found for: " + subjectName + " Teacher: " + teacherName + " Group: " + groupNumber);
		}

		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
			Thread.sleep(300);

			if (!checkbox.isSelected()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
				System.out.println("Clicked checkbox - Teacher: " + teacherName + " Group: " + groupNumber);
			} else {
				System.out.println("Already selected - Group: " + groupNumber);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void groupdField2() {
		safeClick(groupFieldCheckBox2);
	}

	public void setDate(WebElement dateField, String dateIso) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].value = arguments[1];"
				+ "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
				+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dateField, dateIso);
	}
	
	public String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter);
    }
	
	public String getCurrentDayName() {
	    LocalDate today = LocalDate.now();
	    return today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();
	}
	
	

	public void fromDateField() {
        String currentDate = getCurrentDate();
        scrollToElement(fromDateField);
        enterDate(fromDateField, currentDate);
        System.out.println("From Date set to: " + currentDate);
    }

    
    public void toDateField() {
        String currentDate = getCurrentDate();
        scrollToElement(toDateField);
        enterDate(toDateField, currentDate);
        System.out.println("To Date set to: " + currentDate);
    }
    
    
	
	/*
	 * public void fromDateField(String date) { // setDate(fromDateField, date); //
	 * 
	 * scrollToElement(fromDateField); enterDate(fromDateField, date); }
	 * 
	 * public void toDateField(String date) { // setDate(toDateField, date); //
	 * 
	 * scrollToElement(toDateField); enterDate(toDateField, date); }
	 */

	void clearWeekSelection() {
		// Wait for week buttons to be visible first
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(weekFieldList2));

		for (WebElement label : weekFieldList2) {
			try {
				if (label.getAttribute("class").contains("active")) {
					safeClick(label); // toggle OFF
					Thread.sleep(300); // wait between each deselect
				}
			} catch (StaleElementReferenceException e) {
				System.out.println("Stale during clear: " + e.getMessage());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public void weekSelect(String weekList) {
		// Clear existing selections
		clearWeekSelection();

		// Wait for UI to settle
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		String[] days = weekList.split(",");

		for (String day : days) {
			for (WebElement label : weekFieldList2) {
				try {
					if (label.getText().trim().equalsIgnoreCase(day.trim())) {

						// Only click if NOT already active
						if (!label.getAttribute("class").contains("active")) {
							safeClick(label);
							System.out.println("Selected: " + day.trim());
						} else {
							System.out.println("Already selected: " + day.trim());
						}
						break;
					}
				} catch (StaleElementReferenceException e) {
					System.out.println("Stale during select - retrying: " + day);
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

	private WebElement waitForTimeDropdown() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//ng-select[.//span[contains(text(),'Time')]] | //label[contains(text(),'Time')]/following::ng-select[1]")));
	}

	public void selectTimeSlot(String timeValue) {

		WebElement timeDropdown = waitForTimeDropdown();
//		safeClick(timeDropdown); // Open the dropdown first to load options
		safeClick(timeDropdown);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Handle multiple time slots separated by comma
		String[] timeSlots = timeValue.split(",");

		for (String slot : timeSlots) {
			String trimmedSlot = slot.trim();
			try {
				WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//ng-dropdown-panel//span[normalize-space()='" + trimmedSlot + "']")));
				safeClick(option);
				System.out.println("Selected time slot: " + trimmedSlot);

			} catch (Exception e) {
				System.out.println("Time slot not found: " + trimmedSlot);
				throw new RuntimeException("Time slot not found: " + trimmedSlot, e);
			}
		}
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
	
	public String errorModalMsg() throws TimeoutException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(errorModalMsg));
		wait.until(d -> !errorModalMsg.getText().trim().isEmpty());
		return errorModalMsg.getText().trim();
	}
	
	public String errorModalMessage() {
        return errorModalMessage;
    }
	
	private String lastSuccessMsg;

	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnSuccessTextContent'])[1]")
		private WebElement modalSuccessMsg;


	public String modalSuccessMsg() throws TimeoutException {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(modalSuccessMsg));
			wait.until(d -> !modalSuccessMsg.getText().trim().isEmpty());
			return modalSuccessMsg.getText().trim();
		}
		
		public String getLastSuccessMsg() {
	        return lastSuccessMsg;
	    }

	



	/*
	 * fill the class scheduling information
	 */

	public void fillClassSchedulingInformation(String session, String batch, String academicPlan, int sem, int subject,
			String subName, String column, String sub, String teacher, String grpName, String enterFromDate,
			String enterToDate, String week, String timeSelect, String conList) throws InterruptedException {
		openCoursePlannerTab();
		openClassSchedule();
		sessionField();
		selectSession(session);
		selectBatch(batch);
		selectAcademicPlan(academicPlan);
		selectSemester(sem);
		selectSubjectType(subject);
		clickSearch();
		clickSubjectWiseDistribution(subName, column);
		selectCheckboxBySubjectAndGroup(sub, teacher, grpName);

		fromDateField();
		Thread.sleep(2000);
		toDateField();
		weekSelect(getCurrentDayName());
		onlineClass();
		selectTimeSlot(timeSelect);

		constList(conList);
		saveBtn();
		lastSuccessMsg = modalSuccessMsg();
		okButton();
		

//		errorButton();
//		refreshPageSafely();
	}
	public void fillClassSchedulingInformatio3(String session, String batch, String academicPlan, int sem, int subject,
			String subName, String column, String sub, String teacher, String grpName, String enterFromDate,
			String enterToDate, String week, String timeSelect, String conList) throws InterruptedException {
		openCoursePlannerTab();
		openClassSchedule();
		sessionField();
		selectSession(session);
		selectBatch(batch);
		selectAcademicPlan(academicPlan);
		selectSemester(sem);
		selectSubjectType(subject);
		clickSearch();
		clickSubjectWiseDistribution(subName, column);
		selectCheckboxBySubjectAndGroup(sub, teacher, grpName);
		
		fromDateField();
		Thread.sleep(2000);
		toDateField();
		weekSelect(getCurrentDayName());
		onlineClass();
		selectTimeSlot(timeSelect);
		
		constList(conList);
		saveBtn();
		errorModalMessage = errorModalMsg();
		okButton();
		errorButton();
		
		
//		errorButton();
//		refreshPageSafely();
	}

	public void fillClassSchedulingOffline(String session, String batch, String academicPlan, int sem, int subject,
			String subName, String column, String sub, String teacher, String grpName, String enterFromDate,
			String enterToDate, String week, String roomName, String conList, String timeSelect)
			throws InterruptedException {
		openCoursePlannerTab();
		openClassSchedule();
		sessionField();
		selectSession(session);
		selectBatch(batch);
		selectAcademicPlan(academicPlan);
		selectSemester(sem);
		selectSubjectType(subject);
		clickSearch();
		clickSubjectWiseDistribution(subName, column);
		selectCheckboxBySubjectAndGroup(sub, teacher, grpName);

		fromDateField();
		Thread.sleep(2000);
		toDateField();
		weekSelect(getCurrentDayName());
		offlineClass();
		roomField();
		roomList(roomName);

		selectTimeSlot(timeSelect);

		constList(conList);
		saveBtn();
		okButton();
		errorButton();
	}
	public void fillClassSchedulingOffline2(String session, String batch, String academicPlan, int sem, int subject,
			String subName, String column, String sub, String teacher, String grpName, String enterFromDate,
			String enterToDate, String week, String roomName, String conList, String timeSelect)
					throws InterruptedException {
		openCoursePlannerTab();
		openClassSchedule();
		sessionField();
		selectSession(session);
		selectBatch(batch);
		selectAcademicPlan(academicPlan);
		selectSemester(sem);
		selectSubjectType(subject);
		clickSearch();
		clickSubjectWiseDistribution(subName, column);
		selectCheckboxBySubjectAndGroup(sub, teacher, grpName);
		
		fromDateField();
		Thread.sleep(2000);
		toDateField();
		weekSelect(getCurrentDayName());
		offlineClass();
		roomField();
		roomList(roomName);
		
		selectTimeSlot(timeSelect);
		
		constList(conList);
		saveBtn();
		errorModalMessage = errorModalMsg();
		okButton();
		errorButton();
	}

}
