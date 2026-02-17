package com.ihsm.university.navigation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class Employee_Search extends BasePage {

	public Employee_Search(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[normalize-space()='Employee']")
	private WebElement employeeSearchMenu;

	@FindBy(xpath = "//a[@href='#/Employee/Employees']")
	private WebElement searchEmployeeSubMenu;

	@FindBy(xpath = "//input[@type='text' and @placeholder='Employee Name / Code']")
	private WebElement employeeSearchField;

	@FindBy(xpath = "//div[@class='card-footer']//button[normalize-space()='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "//table[@id='tblEmployeeInfo']//tbody//tr[1]//td[3]//a")
	private WebElement employeeNameField;

	// methods to perform the action
	public void employeeSearchMenu() {
		blinkElement(employeeSearchMenu);
		safeClick(employeeSearchMenu);
	}

	public void searchEmployeeSubMenu() {
		blinkElement(searchEmployeeSubMenu);
		safeClick(searchEmployeeSubMenu);
	}

	public void employeeSearchField(String details) {
		blinkElement(employeeSearchField);
		employeeSearchField.sendKeys(details);
	}

	public void clickSearchButton() {
		blinkElement(searchButton);
		safeClick(searchButton);
	}

	public void clickFirstEmployeeRow() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		By employeeLocator = By.xpath("//table[@id='tblEmployeeInfo']//tbody//tr[1]//td[3]//a");

		wait.until(ExpectedConditions.elementToBeClickable(employeeLocator));

		WebElement employee = driver.findElement(employeeLocator);

		blinkElement(employee);
		safeClick(employee);
	}

	// fill student search info here
	public void fillEmployeeSearchInfo(String details) {

		employeeSearchMenu();
		searchEmployeeSubMenu();

		employeeSearchField.clear();
		employeeSearchField(details);

		clickSearchButton();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		// Wait for table row to appear
		By rowLocator = By.xpath("//table[@id='tblEmployeeInfo']//tbody//tr");
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(rowLocator, 0));

		// Wait for student link clickable
		By employeeLinkLocator = By.xpath("//table[@id='tblEmployeeInfo']//tbody//tr[1]//td[3]//a");

		WebElement employee = wait.until(ExpectedConditions.elementToBeClickable(employeeLinkLocator));

		// Scroll to element (VERY IMPORTANT)
		scrollToElement(employee);

		// Click using JavaScript (MOST RELIABLE)
		jsClick(employee);

		// Switch tab
		switchToNewTab();
	}

}
