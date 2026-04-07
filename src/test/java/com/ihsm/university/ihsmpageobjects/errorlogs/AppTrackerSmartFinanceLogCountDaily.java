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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AppTrackerSmartFinanceLogCountDaily {

	private static final Logger log = LogManager.getLogger(AppTrackerSmartFinanceLogCountDaily.class);

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	@FindBy(xpath = "//span[@title='Personal']")
	private WebElement whatsappTargetChat;

	private final By whatsappSearchBox = By.xpath("//div[@contenteditable='true' and (@data-tab='3' or @data-tab='2')]");
	private final By whatsappLeftPanel = By.xpath("//div[@id='pane-side' or @id='side']");
	private final By whatsappMessageBox = By.xpath("(//div[@contenteditable='true' and @role='textbox'])[last()]");
	private final By whatsappSendButton = By.xpath("//span[@data-icon='wds-ic-send-filled']");

	@BeforeClass
	public void setUp() {
		log.info("========================================");
		log.info("   APP TRACKER / FINANCE EDUTECH / SMART EDUTECH LOG COUNT DAILY TEST - STARTING ");
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
		driver.get("https://apptracker.in/Logtofix");

		PageFactory.initElements(driver, this);

		log.info(">> Browser launched and navigated to application");
		log.info(">> Using Chrome profile: C:\\WhatsappAutomationProfile");
	}

	@Test
	public void testLogCountsAndSendWhatsAppMessage() throws InterruptedException {
		log.info(">> Starting log count retrieval...");

		String appTrackerCount = getRowCountByKeywordAndCurrentDate("gvLogsKsam", "apptracker.in/application");
		String smartFinanceCount = getRowCountByKeywordAndCurrentDate("gvLogs", "fin.smartedutech.in/feereceipt");
		String smartEduTechCount = getRowCountByKeywordAndCurrentDate("gvLogs", "smartedutech.in/student");

		log.info(">> App Tracker Error Log Row Count: " + appTrackerCount);
		log.info(">> Smart Finance Error Log Row Count: " + smartFinanceCount);
		log.info(">> Smart EduTech Error Log Row Count: " + smartEduTechCount);

		String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy hh:mm a"));

		String whatsappMessage = "*APP TRACKER / SMART FINANCE / SMART EDUTECH -> Error Count Details:* "
				+ "App Tracker Count: " + appTrackerCount
				+ " | Finance Module Count: " + smartFinanceCount
				+ " | Smart EduTech Count: " + smartEduTechCount
				+ " | Date: " + currentDateTime;

		log.info(">> Final WhatsApp Message: " + whatsappMessage);
		System.out.println(whatsappMessage);

		openWhatsAppWebInNewTab();
		waitForWhatsAppLogin();
		openTargetWhatsAppChat();
		enterMessageInChatBox(whatsappMessage);
		sendMessage();
		Thread.sleep(2000);
	}

	public String getRowCountByKeywordAndCurrentDate(String tableId, String keyword) {
		String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("M/d/yyyy"));
		int matchedRowCount = 0;

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='" + tableId + "']//tbody")));

			List<WebElement> rows = driver.findElements(By.xpath("//table[@id='" + tableId + "']//tbody//tr"));

			if (rows == null || rows.isEmpty()) {
				log.info(">> No rows found in table: " + tableId + " | Returning 0");
				return "0";
			}

			for (WebElement row : rows) {
				try {
					if (!row.isDisplayed()) {
						continue;
					}

					String rowText = row.getText().trim().toLowerCase();

					log.info(">> Checking Row Text: " + rowText);
					log.info(">> Current Date To Match: " + currentDate);

					if (rowText.contains(keyword.toLowerCase()) && rowText.contains(currentDate)) {
						matchedRowCount++;

						log.info(">> Matched row found for keyword + current date: " + keyword);

						scrollToCenter(row);
						blinkElement(row);

						List<WebElement> columns = row.findElements(By.tagName("td"));
						for (int i = 0; i < columns.size(); i++) {
							String colText = columns.get(i).getText().trim();
							log.info(">> Column [" + i + "] = " + colText);

							if (colText.toLowerCase().contains(keyword.toLowerCase())) {
								scrollToCenter(columns.get(i));
								blinkElement(columns.get(i));
							}
						}
					}

				} catch (StaleElementReferenceException e) {
					log.warn(">> Stale row encountered while checking keyword/date match");
				} catch (Exception e) {
					log.warn(">> Skipping one row while checking keyword/date match", e);
				}
			}

			log.info(">> Total rows matching keyword [" + keyword + "] and current date [" + currentDate + "] : " + matchedRowCount);
			return String.valueOf(matchedRowCount);

		} catch (Exception e) {
			log.error(">> Exception while counting rows by keyword and date for: " + keyword + " | Returning 0", e);
			return "0";
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

				for (int i = 0; i < 3; i++) {
					js.executeScript(
							"arguments[0].setAttribute('style','border:3px solid red; background: yellow;');",
							element);
					Thread.sleep(200);

					js.executeScript(
							"arguments[0].setAttribute('style', arguments[1]);",
							element, originalStyle);
					Thread.sleep(200);
				}
			}
		} catch (Exception e) {
			log.debug("Unable to highlight element", e);
			Thread.currentThread().interrupt();
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

	@AfterClass
	public void tearDown() throws InterruptedException {
		log.info("========================================");
		log.info("   APP TRACKER / SMART FINANCE / SMART EDUTECH LOG COUNT DAILY TEST - COMPLETED ");
		log.info("========================================");
		Thread.sleep(5000);
		if (driver != null) {
			driver.quit();
		}
	}
}