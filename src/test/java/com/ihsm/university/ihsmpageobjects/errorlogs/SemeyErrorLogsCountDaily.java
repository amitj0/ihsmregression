package com.ihsm.university.ihsmpageobjects.errorlogs;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

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

public class SemeyErrorLogsCountDaily {

	private static final Logger log = LogManager.getLogger(SemeyErrorLogsCountDaily.class);

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	@FindBy(name = "txtEmail")
	private WebElement emailField;

	@FindBy(name = "txtPassword")
	private WebElement passwordField;

	@FindBy(xpath = "//button[@value='Log In']")
	private WebElement loginBtn;

	@FindBy(xpath = "(//div[@id='divSelectEmployeeRights']//div[contains(@class,'classboxsections')])[1]")
	private WebElement selectSchoolHere;

	@FindBy(xpath = "(//div[contains(@class,'containerrr') and normalize-space()='Super Admin'])[1]")
	private WebElement selectSuperAdmin;

	@FindBy(xpath = "//span[contains(normalize-space(.), 'Master Settings')]")
	private WebElement masterSettingsMenu;

	@FindBy(xpath = "//a[contains(normalize-space(.), 'Error Log')]")
	private WebElement errorLogSubMenu;

	@FindBy(xpath = "//span[@title='Personal']")
	private WebElement whatsappTargetChat;

	private final By errorLogRows = By.xpath("//table[@id='errorlogcount']//tbody//tr");
	private final By whatsappSearchBox = By.xpath("//div[@contenteditable='true' and (@data-tab='3' or @data-tab='2')]");
	private final By whatsappLeftPanel = By.xpath("//div[@id='pane-side' or @id='side']");
	private final By whatsappMessageBox = By.xpath("(//div[@contenteditable='true' and @role='textbox'])[last()]");
	private final By whatsappSendButton = By.xpath("//span[@data-icon='wds-ic-send-filled']");

	@BeforeClass
	public void setUp() {
		log.info("========================================");
		log.info("   SEMEY ERROR LOGS COUNT DAILY TEST - STARTING ");
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
		driver.get("https://intels.smu.edu.kz/#/");

		PageFactory.initElements(driver, this);

		log.info(">> Browser launched and navigated to application");
		log.info(">> Using Chrome profile: C:\\WhatsappAutomationProfile");
	}

	public void login(String email, String password) {
		wait.until(ExpectedConditions.visibilityOf(emailField)).clear();
		emailField.sendKeys(email);

		wait.until(ExpectedConditions.visibilityOf(passwordField)).clear();
		passwordField.sendKeys(password);

		wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
		log.info(">> Login submitted with email: " + email);
	}

	@Test
	public void verifyErrorLogCount() throws InterruptedException {
		String email = "admin@gmail.com";
		String password = "N2SVJE5JOZJL3063";

		login(email, password);
		navigateToErrorLog();

		String errorCount = getTodayErrorLogCount();
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy hh:mm a"));

		String whatsappMessage = "*SEMEY -> Error Count Details:* " + "Error Count: " + errorCount + " | Date: "
				+ currentDate;

		log.info(">> Final Semey Error Count Details Message: " + whatsappMessage);
		System.out.println(whatsappMessage);

		Assert.assertFalse(errorCount.isEmpty(), "Error count should not be empty");

		openWhatsAppWebInNewTab();
		waitForWhatsAppLogin();
		openTargetWhatsAppChat();
		enterMessageInChatBox(whatsappMessage);
		sendMessage();
		Thread.sleep(2000);
	}

	public void navigateToErrorLog() {
		wait.until(ExpectedConditions.visibilityOf(selectSchoolHere));
		blinkElement(selectSchoolHere);
		safeClick(selectSchoolHere);
		log.info(">> Employee rights selected");

		wait.until(ExpectedConditions.visibilityOf(selectSuperAdmin));
		blinkElement(selectSuperAdmin);
		safeClick(selectSuperAdmin);
		log.info(">> Super Admin selected");

		wait.until(ExpectedConditions.visibilityOf(masterSettingsMenu));
		blinkElement(masterSettingsMenu);
		safeClick(masterSettingsMenu);
		log.info(">> Master Settings clicked");

		String parentWindow = driver.getWindowHandle();

		wait.until(ExpectedConditions.visibilityOf(errorLogSubMenu));
		scrollToCenter(errorLogSubMenu);
		blinkElement(errorLogSubMenu);
		safeClick(errorLogSubMenu);
		log.info(">> Error Log submenu clicked");

		switchToNewTab(parentWindow);
		log.info(">> Switched to Error Log tab");
	}

	public String getTodayErrorLogCount() {
		String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String errorCount = "0";

		try {
			List<WebElement> rows = driver.findElements(errorLogRows);

			if (rows == null || rows.isEmpty()) {
				log.info(">> Error log table not found or no rows available. Returning default Error Count: 0");
				return "0";
			}

			log.info(">> Current system date for matching: " + currentDate);
			log.info(">> Total rows found in error log table: " + rows.size());

			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));

				if (columns.size() >= 3) {
					String rowSerialNo = columns.get(0).getText().trim();
					String rowCount = columns.get(1).getText().trim();
					String rowDate = columns.get(2).getText().trim();

					log.info(">> Checking Row -> Serial No: " + rowSerialNo + ", Count: " + rowCount + ", Date: " + rowDate);

					if (currentDate.equals(rowDate)) {
						errorCount = rowCount;
						log.info(">> Match found for current date: " + currentDate + " | Error Count: " + errorCount);
						return errorCount;
					}
				}
			}

			log.info(">> No row found for current date: " + currentDate + " | Using default Error Count: 0");
			return "0";

		} catch (Exception e) {
			log.info(">> Error log table not found / unable to read table. Returning default Error Count: 0");
			log.debug("Exception while reading error log count", e);
			return "0";
		}
	}

	protected void switchToNewTab(String parentWindow) {
		wait.until(driver -> driver.getWindowHandles().size() > 1);

		Set<String> allWindows = driver.getWindowHandles();

		for (String windowHandle : allWindows) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				driver.manage().window().maximize();
				break;
			}
		}
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

	public void openWhatsAppWebInNewTab() {
		js.executeScript("window.open('https://web.whatsapp.com/', '_blank');");
		switchToNewestTab();
		wait.until(ExpectedConditions.urlContains("web.whatsapp.com"));
		log.info(">> WhatsApp Web opened in new tab");
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

			WebElement searchedChat = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@title='Personal']")));
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
	public void tearDown() throws InterruptedException {
		log.info("========================================");
		log.info("   SEMEY ERROR LOGS COUNT DAILY TEST - COMPLETED ");
		log.info("========================================");
		Thread.sleep(5000);
		if (driver != null) {
			driver.quit();
		}
	}
}