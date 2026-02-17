package com.ihsm.university.navigation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class Student_Search extends BasePage {

	public Student_Search(WebDriver driver) {
		super(driver);
	}

	// locate the web elements here
	@FindBy(xpath = "//span[normalize-space()='Student']")
	private WebElement studentSearchMenu;

	@FindBy(xpath = "//a[@href='#/Student/SearchStudents']")
	private WebElement searchStudentsSubMenu;

	@FindBy(xpath = "(//div[@id='SearchStudent']//input)[1]")
	private WebElement studentSearchField;

	@FindBy(xpath = "//div[@class='card-footer']//button[normalize-space()='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "//table[@id='tblStudentInfo']//tr//td[8]/a")
	private WebElement studentNameField;

	// methods to perform the action
	public void studentSearchMenu() {
		blinkElement(studentSearchMenu);
		safeClick(studentSearchMenu);
	}

	public void searchStudentsSubMenu() {
		blinkElement(searchStudentsSubMenu);
		safeClick(searchStudentsSubMenu);
	}

	public void enterStudentDetails(String details) {
		blinkElement(studentSearchField);
		studentSearchField.sendKeys(details);
	}

	public void clickSearchButton() {
		blinkElement(searchButton);
		safeClick(searchButton);
	}

	public void clickFirstStudentRow() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		By studentLocator = By.xpath("//table[@id='tblStudentInfo']//tbody//tr[1]//td[8]//a");

		wait.until(ExpectedConditions.elementToBeClickable(studentLocator));

		WebElement student = driver.findElement(studentLocator);

		blinkElement(student);
		safeClick(student);
	}

	// fill student search info here
	public void fillStudentSearchInfo(String details) {

	    studentSearchMenu();
	    searchStudentsSubMenu();

	    studentSearchField.clear();
	    enterStudentDetails(details);

	    clickSearchButton();

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    // Wait for table row to appear
	    By rowLocator = By.xpath("//table[@id='tblStudentInfo']//tbody//tr");
	    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(rowLocator, 0));

	    // Wait for student link clickable
	    By studentLinkLocator = By.xpath("//table[@id='tblStudentInfo']//tbody//tr[1]//td[8]//a");

	    WebElement student = wait.until(ExpectedConditions.elementToBeClickable(studentLinkLocator));

	    // Scroll to element (VERY IMPORTANT)
	    scrollToElement(student);

	    // Click using JavaScript (MOST RELIABLE)
	    jsClick(student);

	    // Switch tab
	    switchToNewTab();
	}


}
