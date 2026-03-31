package com.ihsm.university.ihsmpageobjects.apptrackers;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AppTrackersResetAutomation {

	private WebDriver driver;
	private WebDriverWait wait;

	private final By loaderLocator = By.id("divLoader");

	public AppTrackersResetAutomation(WebDriver driver) {
		this.driver = driver;
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		this.driver.manage().window().maximize();
		this.driver.get("https://sr.softsolanalytics.com/#/AdminLogin");
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(this.driver, this);
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
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
	}

	public void login(String email, String password) {
		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		blinkElement(submitLoginBtn);
		submitLoginBtn.click();
	}

	public void navigateToResetAppTrackers() {
		waitForLoaderToDisappear();
		userMenu.click();
		waitForLoaderToDisappear();
		blinkElement(resetAppTrackersLink);
		resetAppTrackersLink.click();
	}

	public void resetAppTrackers() {
		waitForLoaderToDisappear();
		blinkElement(resetBtn);
		resetBtn.click(); // triggers the browser confirmation alert

		// Accept the alert: "do you want to reset application?"
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		System.out.println("Accepting alert: " + alert.getText());
		alert.accept();
	}

	public String getAlertText() {
		wait.until(ExpectedConditions.visibilityOf(alertText));
		String text = alertText.getText();
		return text;
	}

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();

		AppTrackersResetAutomation page = new AppTrackersResetAutomation(driver);
		page.login("Admin", "admin@2014$");
		page.navigateToResetAppTrackers();
		page.resetAppTrackers();
		Assert.assertEquals(page.getAlertText(), "Application Can Not Reset", "Unexpected alert message!");
		System.out.println(">>>>> Alert message here: " + page.getAlertText());

		System.out.println("App trackers reset successfully!");

        driver.quit();
	}
}