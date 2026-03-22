package com.ihsm.university.ihsmtestcases.timetablesihsm;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IhsmTimeTable {

	WebDriver driver;
	WebDriverWait wait;

	public IhsmTimeTable() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://timetable.ism.edu.kg/TimeTable/IHSM_TimeTable?id=8");
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='tab4']//select[@id='course']")
	private WebElement courseAndSemesterField;

	@FindBy(xpath = "//div[@id='tab4']//select[@id='groups']")
	private WebElement groupField;

	@FindBy(xpath = "//div[@id='tab4']//input[@id='date-picker']")
	private WebElement dateField;

	@FindBy(xpath = "(//div[@id='tab4']//button[normalize-space()='Search'])[1]")
	private WebElement searchButton;

	@FindBy(xpath = "//table[@id='SearchByFilter']//tbody//tr//td")
	private List<WebElement> searchResultTableData;

	// Scroll element to center of viewport
	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver)
				.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
		// Small pause for scroll to settle
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void enterDate(String date) {
		wait.until(ExpectedConditions.elementToBeClickable(dateField));
		dateField.clear();

		// Use JS to set value for input[type="month"] — more reliable than sendKeys
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", dateField, date);

		// Trigger change event so the page registers the input
		((JavascriptExecutor) driver)
				.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dateField);
	}

	public void selectCourseAndSemester(String value) {
		scrollToElement(courseAndSemesterField);
		wait.until(ExpectedConditions.elementToBeClickable(courseAndSemesterField));
		Select select = new Select(courseAndSemesterField);
		select.selectByValue(value);
	}

	public void selectGroup(String group) {
		scrollToElement(groupField);
		wait.until(ExpectedConditions.elementToBeClickable(groupField));
		Select select = new Select(groupField);
		select.selectByVisibleText(group);
	}

	public void enterDateH(String date) {
		wait.until(ExpectedConditions.elementToBeClickable(dateField));
		dateField.clear();
		dateField.sendKeys(date);
	}

	public void clickSearchButton() {
		scrollToElement(searchButton);
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
	}

	public void printGroupOptions() {
		wait.until(driver -> new Select(groupField).getOptions().size() > 1);
		Select select = new Select(groupField);
		select.getOptions().forEach(
				opt -> System.out.println("Value: [" + opt.getAttribute("value") + "] Text: [" + opt.getText() + "]"));
	}

	public void writeNoDataToFile(String courseValue, String group) {
		String fileName = "timetable_results.txt";
		try (java.io.FileWriter fw = new java.io.FileWriter(fileName, true);
				java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
			bw.write("\n" + "═".repeat(100));
			bw.newLine();
			bw.write("Course: " + courseValue + " | Group: " + group);
			bw.newLine();
			bw.write("═".repeat(100));
			bw.newLine();
			bw.write("NO DATA FOUND");
			bw.newLine();
		} catch (java.io.IOException e) {
			System.out.println("Failed to write to file: " + e.getMessage());
		}
	}

	public boolean isTableDataPresent() {
		try {
			// Wait up to 5 seconds for table data to appear
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
			shortWait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//table[@id='SearchByFilter']//tbody//tr//td")));

			// Check if cells exist and have non-empty text
			List<WebElement> cells = driver.findElements(By.xpath("//table[@id='SearchByFilter']//tbody//tr//td"));

			if (cells.isEmpty()) {
				return false;
			}

			// Check if at least one cell has visible text
			for (WebElement cell : cells) {
				if (!cell.getText().trim().isEmpty()) {
					return true;
				}
			}

			return false;

		} catch (Exception e) {
			// Element not found = no data
			return false;
		}
	}

	@DataProvider(name = "courseValues")
	public Object[][] courseValues() {
		return new Object[][] {
				 { "1" }, { "2" }, { "3" }, { "4" }, { "5" }, { "6" },  { "7" }, { "8" }, { "9" },
				{ "10" } };
	}

	@Test(dataProvider = "courseValues")
	public void testTimeTableSearch(String courseValue) throws InterruptedException {

		// Select course FIRST
		selectCourseAndSemester(courseValue);
		Thread.sleep(1000);

		// THEN get groups for that course
		wait.until(driver -> new Select(groupField).getOptions().size() > 1);
		Select select = new Select(groupField);
		List<String> groupTexts = new java.util.ArrayList<>();
		for (WebElement opt : select.getOptions()) {
			String text = opt.getText().trim();
			if (!text.isEmpty()) {
				groupTexts.add(text);
			}
		}

		// Loop through each group
		for (String group : groupTexts) {
			System.out.println("\n" + "═".repeat(100));
			System.out.println("Course: " + courseValue + " | Group: " + group);
			System.out.println("═".repeat(100));

			selectCourseAndSemester(courseValue);
			Thread.sleep(500);
			selectGroup(group);
			enterDate("2026-03"); // change date here
			clickSearchButton();
			Thread.sleep(2000);

			if (isTableDataPresent()) {
				System.out.println("Data found in table:");
				List<WebElement> freshCells = driver
						.findElements(By.xpath("//table[@id='SearchByFilter']//tbody//tr//td"));
				for (WebElement cell : freshCells) {
					System.out.println(cell.getText());
				}
			} else {
				System.out.println("No data found in the table.");
				 writeNoDataToFile(courseValue, group);  
			}
			}
		}
	}
