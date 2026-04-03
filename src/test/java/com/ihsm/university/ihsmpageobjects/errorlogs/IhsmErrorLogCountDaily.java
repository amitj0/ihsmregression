package com.ihsm.university.ihsmpageobjects.errorlogs;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IhsmErrorLogCountDaily {

	private static final Logger log = LogManager.getLogger(IhsmErrorLogCountDaily.class);

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	@FindBy(name = "txtEmail")
	private WebElement emailInput;

	@FindBy(name = "txtPassword")
	private WebElement passwordInput;

	@FindBy(xpath = "//button[contains(normalize-space(), 'Log In')] ")
	private WebElement loginButton;

	@FindBy(xpath = "//input[@id='dateInput']")
	private WebElement dateInputField;

	@FindBy(xpath = "//input[@id='countInput']")
	private WebElement countInputField;

	@FindBy(xpath = "//button[normalize-space()='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "//span[@title='Personal']")
	private WebElement whatsappTargetChat;

	private final By errorLogRows = By.xpath("//table[@id='errorlogcount']//tbody//tr");
	private final By whatsappSearchBox = By
			.xpath("//div[@contenteditable='true' and (@data-tab='3' or @data-tab='2')]");
	private final By whatsappLeftPanel = By.xpath("//div[@id='pane-side' or @id='side']");
	private final By whatsappMessageBox = By.xpath("(//div[@contenteditable='true' and @role='textbox'])[last()]");
	private final By whatsappSendButton = By.xpath("//span[@data-icon='wds-ic-send-filled']");

	@BeforeClass
	public void setUp() {
		log.info("========================================");
		log.info("   IHSM ERROR LOGS COUNT DAILY TEST - STARTING ");
		log.info("========================================");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("user-data-dir=C:\\WhatsappAutomationProfile");
		options.addArguments("--profile-directory=Default");
		options.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		js = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://universityism.com/#/");

		PageFactory.initElements(driver, this);

		log.info(">> Browser launched and navigated to application");
		log.info(">> Using Chrome profile: C:\\WhatsappAutomationProfile");
	}

	public void login(String email, String password) {
		wait.until(ExpectedConditions.visibilityOf(emailInput)).clear();
		emailInput.sendKeys(email);

		wait.until(ExpectedConditions.visibilityOf(passwordInput)).clear();
		passwordInput.sendKeys(password);

		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
		log.info(">> Login submitted with email: " + email);
	}

	@Test
	public void verifyErrorLogCount() throws InterruptedException {
		String email = "admin@gmail.com";
		String password = "Gl0b@l$2204";

		login(email, password);
		Assert.assertTrue(wait.until(ExpectedConditions.urlContains("/Dashboard/HrDashboard")), "Login failed or Dashboard not loaded");
		Thread.sleep(3000);

		driver.get("https://universityism.com/#/Report/ErrorLogs");
		Thread.sleep(2000);

		String errorCount = searchAndCaptureTodayCount();
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy hh:mm a"));

		String whatsappMessage = "*IHSM -> Error Count Details:* " + "Error Count: " + errorCount + " | Date: "
				+ currentDate;

		log.info(">> Final IHSM Error Count Details Message: " + whatsappMessage);
		System.out.println(whatsappMessage);

		Assert.assertFalse(errorCount.isEmpty(), "Error count should not be empty");

		openWhatsAppWebInNewTab();
		waitForWhatsAppLogin();
		openTargetWhatsAppChat();
		enterMessageInChatBox(whatsappMessage);
		sendMessage();
		Thread.sleep(2000);
	}

	public String searchAndCaptureTodayCount() {
		String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		wait.until(ExpectedConditions.visibilityOf(dateInputField));
		dateInputField.click();
		dateInputField.sendKeys(Keys.CONTROL + "a");
		dateInputField.sendKeys(Keys.DELETE);
		dateInputField.sendKeys(todayDate);

		log.info(">> Date entered in search box: " + todayDate);

		String oldCountValue = countInputField.getAttribute("value");
		if (oldCountValue == null) {
			oldCountValue = "";
		}
		oldCountValue = oldCountValue.trim();

		log.info(">> Old count value before search: " + oldCountValue);

		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		safeClick(searchButton);
		log.info(">> Search button clicked");

		String errorCount = "0";

		try {
			WebDriverWait countWait = new WebDriverWait(driver, Duration.ofSeconds(30));

			final String previousValue = oldCountValue;

			errorCount = countWait.until(driver -> {
				String newValue = countInputField.getAttribute("value");

				if (newValue != null) {
					newValue = newValue.trim();

					if (!newValue.isEmpty() && !newValue.equals(previousValue)) {
						return newValue;
					}
				}
				return null;
			});

			log.info(">> New Error Count captured after search: " + errorCount);

		} catch (Exception e) {
			log.info(">> Count value did not change after search within timeout, using existing or default value");

			String fallbackValue = countInputField.getAttribute("value");
			if (fallbackValue != null && !fallbackValue.trim().isEmpty()) {
				errorCount = fallbackValue.trim();
			} else {
				errorCount = "0";
			}
		}

		return errorCount;
	}

	public void openWhatsAppWebInNewTab() {
		js.executeScript("window.open('https://web.whatsapp.com/', '_blank');");
		switchToNewestTab();
		wait.until(ExpectedConditions.urlContains("web.whatsapp.com"));
		log.info(">> WhatsApp Web opened in new tab");
	}

	protected void switchToNewestTab() {
		String newestWindow = null;

		for (String windowHandle : driver.getWindowHandles()) {
			newestWindow = windowHandle;
		}

		if (newestWindow != null) {
			driver.switchTo().window(newestWindow);
			driver.manage().window().maximize();
		}
	}

	public void waitForWhatsAppLogin() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(whatsappLeftPanel));
			log.info(">> WhatsApp already logged in");
		} catch (Exception e) {
			log.info(">> Please scan QR once in Selenium browser profile: C:\\WhatsappAutomationProfile");
			wait.until(ExpectedConditions.presenceOfElementLocated(whatsappLeftPanel));
			log.info(">> WhatsApp login completed after QR scan");
		}
	}

	public void openTargetWhatsAppChat() {
		try {
			wait.until(ExpectedConditions.visibilityOf(whatsappTargetChat));
			blinkElement(whatsappTargetChat);
			safeClick(whatsappTargetChat);
			log.info(">> WhatsApp target chat clicked directly");
		} catch (Exception e) {
			WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappSearchBox));
			searchBox.click();
			searchBox.sendKeys(Keys.CONTROL + "a");
			searchBox.sendKeys(Keys.DELETE);
			searchBox.sendKeys("Personal");

			WebElement searchedChat = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@title='Personal']")));
			blinkElement(searchedChat);
			safeClick(searchedChat);
			log.info(">> WhatsApp target chat opened via search");
		}
	}

	public void enterMessageInChatBox(String message) {
		WebElement msgBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappMessageBox));
		scrollToCenter(msgBox);
		safeClick(msgBox);
		msgBox.sendKeys(message);

		log.info(">> Message entered in WhatsApp chat box: " + message);
	}

	public void sendMessage() {
		try {
			WebElement sendBtn = wait.until(ExpectedConditions.elementToBeClickable(whatsappSendButton));
			safeClick(sendBtn);
			log.info(">> Message sent by send button");
		} catch (Exception e) {
			WebElement msgBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappMessageBox));
			msgBox.sendKeys(Keys.ENTER);
			log.info(">> Message sent by ENTER key");
		}
	}

	protected void safeClick(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();

		} catch (StaleElementReferenceException e) {
			wait.until(ExpectedConditions.visibilityOf(element));
			scrollToCenter(element);
			jsClick(element);

		} catch (ElementClickInterceptedException e) {
			wait.until(ExpectedConditions.visibilityOf(element));
			scrollToCenter(element);
			jsClick(element);

		} catch (UnhandledAlertException e) {
			try {
				driver.switchTo().alert().accept();
			} catch (Exception ignored) {
			}

			wait.until(ExpectedConditions.visibilityOf(element));
			scrollToCenter(element);
			jsClick(element);

		} catch (Exception e) {
			wait.until(ExpectedConditions.visibilityOf(element));
			scrollToCenter(element);
			jsClick(element);
		}
	}

	protected void scrollToCenter(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
	}

	protected void jsClick(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	protected boolean waitUntilVisible(WebElement element, Duration timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			log.debug("Element not visible within timeout", e);
			return false;
		}
	}

	public void blinkElement(WebElement element) {
		try {
			if (waitUntilVisible(element, Duration.ofMillis(5000))) {
				String originalStyle = element.getAttribute("style");

				for (int i = 0; i < 2; i++) {
					js.executeScript("arguments[0].setAttribute('style','border:3px solid black; background: yellow;')",
							element);
					Thread.sleep(100);

					js.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
					Thread.sleep(100);
				}
			}
		} catch (Exception e) {
			log.debug("Unable to highlight element", e);
			Thread.currentThread().interrupt();
		}
	}

	@AfterClass
	public void tearDown() {
		log.info("========================================");
		log.info("   IHSM ERROR LOGS COUNT DAILY TEST - COMPLETED ");
		log.info("========================================");

		if (driver != null) {
			driver.quit();
		}

	}

}
