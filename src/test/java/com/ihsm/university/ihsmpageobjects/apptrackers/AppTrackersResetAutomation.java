package com.ihsm.university.ihsmpageobjects.apptrackers;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AppTrackersResetAutomation {

	private static final Logger log = LogManager.getLogger(AppTrackersResetAutomation.class);

	private WebDriver driver;
	private WebDriverWait wait;

	private final By loaderLocator = By.id("divLoader");

	public AppTrackersResetAutomation(WebDriver driver) {
		log.info("========================================");
		log.info("   APP TRACKERS RESET - STARTING        ");
		log.info("========================================");
		this.driver = driver;
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		this.driver.manage().window().maximize();
		log.info(">> Browser launched and maximized");
		this.driver.get("https://sr.softsolanalytics.com/#/AdminLogin");
		log.info(">> Navigated to URL: https://sr.softsolanalytics.com/#/AdminLogin");
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(this.driver, this);
		log.info(">> Page Factory initialized");
	}

	@FindBy(name = "txtEmail")
	private WebElement emailField;

	@FindBy(name = "txtPassword")
	private WebElement passwordField;

	@FindBy(xpath = "//button[@value='Log In']")
	private WebElement submitLoginBtn;

	@FindBy(xpath = "//a[@id='navbarDropdownMenuLink']")
	private WebElement userMenu;

	@FindBy(xpath = "//ul[@aria-labelledby='navbarDropdownMenuLink']//li//a[@href='#/ResetApplicationForExpiredDoc']")
	private WebElement resetAppTrackersLink;

	@FindBy(xpath = "//button[normalize-space()='Reset']")
	private WebElement resetBtn;

	@FindBy(xpath = "//div[@class='swal-text']")
	private WebElement alertText;

	public void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid yellow'", element);
	}

	public void blinkElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("var element = arguments[0];" + "var blinkCount = 0;" + "var blink = setInterval(function() {"
				+ "    element.style.border = (blinkCount % 2 === 0) ? '3px solid yellow' : '';" + "    blinkCount++;"
				+ "    if (blinkCount >= 10) {" + "        clearInterval(blink);" + "        element.style.border = '';"
				+ "    }" + "}, 300);", element);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void waitForLoaderToDisappear() {
		log.info(">> Waiting for page loader to disappear...");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
		log.info(">> Loader disappeared. Page is ready.");
	}

	public void login(String email, String password) {
		log.info("----------------------------------------");
		log.info(">> STEP 1: Logging in as → {}", email);
		log.info("----------------------------------------");
		emailField.sendKeys(email);
		log.info(">> Email entered: {}", email);
		passwordField.sendKeys(password);
		log.info(">> Password entered");
		blinkElement(submitLoginBtn);
		submitLoginBtn.click();
		log.info(">> Login button clicked");
	}

	public void navigateToResetAppTrackers() {
		log.info("----------------------------------------");
		log.info(">> STEP 2: Navigating to Reset App Trackers page");
		log.info("----------------------------------------");
		waitForLoaderToDisappear();
		userMenu.click();
		log.info(">> User menu clicked");
		waitForLoaderToDisappear();
		blinkElement(resetAppTrackersLink);
		resetAppTrackersLink.click();
		log.info(">> Reset App Trackers link clicked");
	}

	public void resetAppTrackers() {
		log.info("----------------------------------------");
		log.info(">> STEP 3: Performing Reset");
		log.info("----------------------------------------");
		waitForLoaderToDisappear();
		blinkElement(resetBtn);
		resetBtn.click();
		log.info(">> Reset button clicked");

		log.info(">> Waiting for browser confirmation alert...");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String alertMsg = alert.getText();
		log.info(">> Alert appeared with message: {}", alertMsg);
		alert.accept();
		log.info(">> Alert accepted");
	}

	public String getAlertText() {
		log.info(">> Waiting for success/failure message...");
		wait.until(ExpectedConditions.visibilityOf(alertText));
		String text = alertText.getText();
		log.info(">> Message received: {}", text);
		return text;
	}

	public static void main(String[] args) {

		Logger log = LogManager.getLogger(AppTrackersResetAutomation.class);

		log.info("========================================");
		log.info("   JENKINS JOB STARTED                  ");
		log.info("   AppTrackers Reset Automation          ");
		log.info("========================================");

		// ✅ Headless Chrome Setup
		log.info(">> Setting up WebDriverManager...");
		WebDriverManager.chromedriver().setup();
		log.info(">> WebDriverManager setup complete");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1920,1080");
		log.info(">> Chrome headless options configured");

		WebDriver driver = new ChromeDriver(options);
		log.info(">> ChromeDriver started successfully");

		try {
			AppTrackersResetAutomation page = new AppTrackersResetAutomation(driver);

			page.login("Admin", "admin@2014$");
			log.info(">> Login successful");

			page.navigateToResetAppTrackers();
			log.info(">> Navigation successful");

			page.resetAppTrackers();
			log.info(">> Reset action performed");

			String result = page.getAlertText();
			log.info(">> Final Result Message: {}", result);

			Assert.assertEquals(result, "Application Can Not Reset", "Unexpected alert message!");
			log.info(">> Assertion PASSED");

			log.info("========================================");
			log.info("   ✅ APP TRACKERS RESET - SUCCESS      ");
			log.info("========================================");

		} catch (Exception e) {
			log.error("========================================");
			log.error("   ❌ APP TRACKERS RESET - FAILED       ");
			log.error("   Error: {}", e.getMessage());
			log.error("========================================");
			throw e;

		} finally {
			driver.quit();
			log.info(">> Browser closed");
			log.info(">> Job finished");
		}
	}
}