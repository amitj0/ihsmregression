package com.ihsm.university.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BasePage {

	protected static WebDriver driver;
	protected WebDriverWait wait;
	private static final Duration WAIT_TIMEOUT = Duration
			.ofSeconds(Integer.parseInt(System.getProperty("wait.timeout", "2")));

	protected static Logger logger;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
		logger = LogManager.getLogger((this.getClass()));
		PageFactory.initElements(driver, this);
	}

	protected void enterDate(WebElement dateElement, String dateValue) {

		if (dateValue == null || dateValue.trim().isEmpty()) {
			throw new IllegalArgumentException("Date value cannot be null or empty");
		}

		String input = dateValue.trim();

		LocalDate parsedDate = null;

		// Patterns for 4-digit year
		String[] patterns = { "dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy", "ddMMyyyy", "d/M/yyyy", "d-M-yyyy", "d.M.yyyy",
				"yyyy-MM-dd" };

		// Try 4-digit year patterns first
		for (String pattern : patterns) {
			try {
				parsedDate = LocalDate.parse(input, DateTimeFormatter.ofPattern(pattern));
				break;
			} catch (DateTimeParseException ignored) {
			}
		}

		// If still null, try 2-digit year patterns and interpret as 1900+
		if (parsedDate == null) {
			String[] shortPatterns = { "dd/MM/yy", "dd-MM-yy", "dd.MM.yy", "d/M/yy", "d-M-yy", "d.M.yy" };
			for (String pattern : shortPatterns) {
				try {
					DateTimeFormatter formatter = new DateTimeFormatterBuilder()
							.appendPattern(pattern.substring(0, pattern.length() - 2))
							.appendValueReduced(ChronoField.YEAR, 2, 2, 1900).toFormatter();
					parsedDate = LocalDate.parse(input, formatter);
					break;
				} catch (DateTimeParseException ignored) {
				}
			}
		}

		if (parsedDate == null) {
			throw new IllegalArgumentException(
					"Invalid date format: " + dateValue + ". Use dd/MM/yyyy, dd-MM-yyyy, dd/MM/yy, etc.");
		}

		// Minimal validation
		if (parsedDate.getYear() < 1900) {
			throw new IllegalArgumentException("Invalid year: " + parsedDate.getYear());
		}

		// UI-friendly format
		String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		try {
			safeClick(dateElement);
			dateElement.clear();
			dateElement.sendKeys(formattedDate);
			dateElement.sendKeys(Keys.TAB);
		} catch (Exception e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].value=arguments[1];"
							+ "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));"
							+ "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
					dateElement, formattedDate);
			dateElement.sendKeys(Keys.TAB);
		}
	}

	// ====================== Core Click Methods ======================

	protected void safeClick(WebElement element) {
		try {
			handleAlertIfPresent();
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();

		} catch (UnhandledAlertException e) {
			handleAlertIfPresent();
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();

		} catch (StaleElementReferenceException e) {
			reinitializePageElements();
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();

		} catch (ElementClickInterceptedException e) {
			scrollToCenter(element);
			jsClick(element);

		} catch (Exception e) {
			jsClick(element);
		}
	}

	protected void jsClick(WebElement element) {
		handleAlertIfPresent();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	private void clickWithFallback(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		} catch (Exception ex) {
			logger.warn("clickWithFallback failed, trying Actions then JS click", ex);
			try {
				new Actions(driver).moveToElement(element).click().perform();
			} catch (Exception aex) {
				logger.warn("Actions fallback failed, using JS click", aex);
				jsClick(element);
			}
		}
	}

	public boolean isElementVisible(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void selectNgDropdownValue(WebElement dropdown, String value, WebElement addButton, WebElement inputField,
			WebElement saveButton, WebElement okButton) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int maxRetries = 3;

		for (int attempt = 1; attempt <= maxRetries; attempt++) {
			try {
				System.out.println("🔄 Attempt " + attempt + " to select: " + value);

				// Step 1: Try to select existing value
				openDropdown(dropdown, wait);

				if (trySelectExistingValue(value, wait)) {
					System.out.println("✓ Selected existing value: " + value);
					return; // Success!
				}

				// Step 2: Value not found - close dropdown before adding
				System.out.println("⚠ Value not found, adding new: " + value);
				try {
					driver.findElement(By.xpath("//body")).click();
					Thread.sleep(500); // Increased wait
				} catch (Exception ignored) {
				}

				// Step 3: Add new value
				addNewDropdownValue(value, addButton, inputField, saveButton, okButton);

				// Step 4: IMPORTANT - Wait longer for value to be saved to backend
				System.out.println("⏳ Waiting for value to be saved...");
				Thread.sleep(2000); // Increased from 1000ms

				// Step 5: Close any lingering modals/dropdowns
				try {
					driver.findElement(By.xpath("//body")).click();
					Thread.sleep(500);
				} catch (Exception ignored) {
				}

				// Step 6: Reopen and try to select the newly added value
				System.out.println("🔄 Reopening dropdown to select newly added value...");
				openDropdown(dropdown, wait);

				if (trySelectExistingValue(value, wait)) {
					System.out.println("✓ Selected newly added value: " + value);
					return; // Success!
				}

				// Step 7: If still not found, throw error
				System.out.println("✗ Failed to find value even after adding: " + value);
				throw new RuntimeException("Failed to select value after adding: " + value);

			} catch (TimeoutException | StaleElementReferenceException e) {
				System.out.println("⚠ Attempt " + attempt + " failed: " + e.getMessage());

				if (attempt == maxRetries) {
					throw new RuntimeException(
							"Failed to select dropdown value after " + maxRetries + " attempts: " + value, e);
				}

				// Close everything before retry
				try {
					driver.findElement(By.xpath("//body")).click();
					Thread.sleep(1000);
				} catch (Exception ignored) {
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException("Thread interrupted", e);
			}
		}
	}

	// Add these helper methods to your BasePage class

	private void openDropdown(WebElement dropdown, WebDriverWait wait) {
		try {
			WebElement container = dropdown.findElement(By.xpath(".//div[contains(@class,'ng-select-container')]"));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", container);
			Thread.sleep(200);

			try {
				wait.until(ExpectedConditions.elementToBeClickable(container));
				container.click();
			} catch (Exception e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", container);
			}

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='option']")));

		} catch (Exception e) {
			throw new RuntimeException("Could not open dropdown", e);
		}
	}

	private boolean trySelectExistingValue(String value, WebDriverWait wait) {
		int maxAttempts = 3; // Try up to 3 times if stale

		for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='option']")));
				Thread.sleep(500);

				// ✅ Get fresh list of options each attempt
				List<WebElement> options = driver.findElements(By.xpath("//div[@role='option']"));

				System.out.println("📋 Found " + options.size() + " dropdown options (Attempt " + attempt + ")");

				if (options.isEmpty()) {
					System.out.println("⚠ No options found in dropdown");
					return false;
				}

				// ✅ Search by index to avoid stale elements
				for (int i = 0; i < options.size(); i++) {
					try {
						// Get fresh element each iteration
						WebElement option = driver.findElements(By.xpath("//div[@role='option']")).get(i);
						String text = getOptionText(option);
						System.out.println("  - Option: " + text);

						if (text.equalsIgnoreCase(value)) {
							System.out.println("✓ Found matching option: " + value);

							// Get fresh element again before clicking
							option = driver.findElements(By.xpath("//div[@role='option']")).get(i);
							((JavascriptExecutor) driver)
									.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", option);
							wait.until(ExpectedConditions.elementToBeClickable(option));
							safeClick(option);
							wait.until(
									ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='option']")));
							return true;
						}
					} catch (StaleElementReferenceException e) {
						System.out.println("⚠ Stale element at index " + i + ", continuing...");
						continue;
					} catch (IndexOutOfBoundsException e) {
						System.out.println("⚠ Index out of bounds, breaking...");
						break;
					}
				}

				System.out.println("✗ Value not found in dropdown: " + value);

				// If we got through all options without finding it, no need to retry
				return false;

			} catch (StaleElementReferenceException e) {
				System.out.println("⚠ Stale elements detected, retrying attempt " + (attempt + 1));
				if (attempt == maxAttempts) {
					System.out.println("✗ Max attempts reached, giving up");
					return false;
				}
				// Wait and retry
				try {
					Thread.sleep(800);
				} catch (InterruptedException ie) {
				}
			} catch (Exception e) {
				System.out.println("✗ Error selecting value: " + e.getMessage());
				return false;
			}
		}

		return false;
	}

	private String getOptionText(WebElement option) {
		try {
			return option.findElement(By.xpath(".//span[contains(@class,'ng-option-label')]")).getText().trim();
		} catch (NoSuchElementException e) {
			return option.getText().trim();
		}
	}

	private void addNewDropdownValue(String value, WebElement addButton, WebElement inputField, WebElement saveButton,
			WebElement okButton) {
		try {
			System.out.println("➕ Adding new dropdown value: " + value);

			// Close dropdown if open
			try {
				driver.findElement(By.xpath("//body")).click();
				Thread.sleep(300);
			} catch (Exception ignored) {
			}

			// Click add button
			safeClick(addButton);
			System.out.println("  ✓ Clicked add button");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(inputField));
			System.out.println("  ✓ Input field visible");

			// Enter value
			inputField.clear();
			inputField.sendKeys(value);
			System.out.println("  ✓ Entered value: " + value);

			// Click save
			wait.until(ExpectedConditions.elementToBeClickable(saveButton));
			safeClick(saveButton);
			System.out.println("  ✓ Clicked save button");

			// ✅ IMPROVED: Wait and verify modal response
			Thread.sleep(800); // Wait for modal to respond

			// Check if OK button appears
			boolean okButtonClicked = false;
			try {
				WebElement okBtn = wait.until(ExpectedConditions.visibilityOf(okButton));
				System.out.println("  ℹ OK button found, clicking...");
				safeClick(okBtn);
				okButtonClicked = true;
				System.out.println("  ✓ OK button clicked");

				// Wait for OK button to disappear (modal closed)
				wait.until(ExpectedConditions.invisibilityOf(okBtn));

			} catch (TimeoutException e) {
				System.out.println("  ⚠ No OK button appeared - checking if modal auto-closed");
			}

			// ✅ CRITICAL: Verify the add modal is actually closed
			Thread.sleep(500);

			try {
				List<WebElement> modals = driver
						.findElements(By.xpath("//div[contains(@class,'modal') and contains(@class,'show')]"));

				if (!modals.isEmpty()) {
					System.out.println("  ⚠ WARNING: Modal still open! Attempting to close...");

					// Try to find and click any OK/Close button
					try {
						WebElement anyOkBtn = driver.findElement(By.xpath(
								"//div[contains(@class,'modal') and contains(@class,'show')]//button[text()='Ok' or text()='Close' or contains(@class,'close')]"));
						safeClick(anyOkBtn);
						Thread.sleep(500);
						System.out.println("  ✓ Clicked modal close button");
					} catch (Exception e) {
						// Try pressing Escape
						System.out.println("  ⚠ Trying ESC key to close modal");
						driver.findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
						Thread.sleep(500);
					}
				} else {
					System.out.println("  ✓ Modal closed successfully");
				}
			} catch (Exception e) {
				System.out.println("  ⚠ Error checking modal state: " + e.getMessage());
			}

			// Final verification - ensure NO modals are open
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[contains(@class,'modal') and contains(@class,'show')]")));

			System.out.println("  ✓ Add value completed successfully");

		} catch (Exception e) {
			System.out.println("  ✗ ERROR adding dropdown value: " + e.getMessage());

			// Take screenshot for debugging
			try {
				captureScreenshot("AddDropdownValue_Failed_" + value);
			} catch (Exception ignored) {
			}

			throw new RuntimeException("Failed to add new dropdown value: " + value, e);
		}
	}

	public void handleModalOk(WebElement okButton) {
		try {
			// Handle any browser alerts first
			handleAlertIfPresent();

			// Wait up to 5 seconds for modal OK button
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			// Ensure button is visible
			wait.until(ExpectedConditions.visibilityOf(okButton));

			// Ensure button is clickable
			wait.until(ExpectedConditions.elementToBeClickable(okButton));

			// Small delay for modal animation to complete
			Thread.sleep(300);

			// Click the OK button
			okButton.click();

			// Wait for modal to close
			wait.until(ExpectedConditions.invisibilityOf(okButton));

			logger.info("Modal OK clicked successfully");

		} catch (Exception e) {
			logger.warn("Modal OK click failed with regular click, retrying with JavaScript click", e);
			try {
				// Fallback to JavaScript click
				jsClick(okButton);
				Thread.sleep(300);
				logger.info("Modal OK clicked successfully with JS");
			} catch (Exception ex) {
				logger.error("Failed to click modal OK button with both methods", ex);
			}
		}
	}

	protected void waitForNgSelectOptions() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ng-dropdown-panel-items")));
	}

	protected void refreshPageSafely() {
		try {
			logger.info("Refreshing page...");
			driver.navigate().refresh();

			// wait for page load
			new WebDriverWait(driver, Duration.ofSeconds(5)).until(
					d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

			// reinitialize page elements
			PageFactory.initElements(driver, this);

		} catch (Exception e) {
			logger.warn("Page refresh failed", e);
		}
	}

	// ====================== Input & Visibility ======================

	protected void clickWhenClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}

	protected void safeSendKeys(WebElement element, String text) {
		try {
			WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
			visibleElement.clear();
			visibleElement.sendKeys(text);
		} catch (StaleElementReferenceException e) {
			logger.warn("Stale element encountered while sending keys, reinitializing and retrying", e);
			reinitializePageElements();
			try {
				WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
				visibleElement.clear();
				visibleElement.sendKeys(text);
			} catch (Exception ex) {
				logger.error("Failed to send keys after retry", ex);
				throw ex;
			}
		} catch (TimeoutException e) {
			logger.warn("Timeout waiting for element to be visible before sendKeys", e);
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error in safeSendKeys", e);
			throw e;
		}
	}

	protected void waitForModalToClose() {
		try {
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'modal-content')]")));
		} catch (TimeoutException e) {
			logger.warn("Modal did not close in expected time");
		}
	}

	protected void triggerBlur(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].blur();", element);
	}

	public void handleAlertIfPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(500)); // Reduced from 2 seconds
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			String text = alert.getText();
			logger.info("Alert detected: " + text);
			alert.accept();
		} catch (TimeoutException | NoAlertPresentException e) {
			// No alert found → do nothing
		}
	}

	// ====================== Utilities ======================

	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	private void scrollToCenter(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	}

	public void blinkElement(WebElement element) {
		try {
			if (waitUntilVisible(element, Duration.ofMillis(5000))) { // Reduced from 1 second
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String originalStyle = element.getAttribute("style");

				for (int i = 0; i < 2; i++) { // Reduced from 3 to 2 blinks
					js.executeScript("arguments[0].setAttribute('style','border:3px solid black; background: yellow;')",
							element);
					Thread.sleep(100); // Reduced from 200ms

					js.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
					Thread.sleep(100); // Reduced from 200ms
				}
			}
		} catch (Exception e) {
			logger.debug("Unable to highlight element", e);
			Thread.currentThread().interrupt();
		}
	}

	public void blinkElementToVerify(WebElement element) {
		try {
			if (waitUntilVisible(element, Duration.ofMillis(500))) { // Reduced from 1 second
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String originalStyle = element.getAttribute("style");

				for (int i = 0; i < 2; i++) { // Reduced from 3 to 2 blinks
					js.executeScript("arguments[0].setAttribute('style','border:3px solid black; background: green;')",
							element);
					Thread.sleep(100); // Reduced from 200ms

					js.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
					Thread.sleep(100); // Reduced from 200ms
				}
			}
		} catch (Exception e) {
			logger.debug("Unable to highlight element", e);
			Thread.currentThread().interrupt();
		}
	}

	public void handleSubmissionConfirmation() {

		// Handle browser alert if present
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(500)); // Reduced from 2 seconds
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("[WARN] Browser alert detected: " + alert.getText());
			alert.accept();
		} catch (TimeoutException e) {
			// No browser alert appeared
		}

		// Handle success modal OK button if present
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(800)); // Reduced from 2 seconds
			WebElement okBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")));
			okBtn.click();
		} catch (TimeoutException e) {
			// Modal not present
		}
	}

	// Random Image picker
	public static String getRandomImage() {
		File folder = new File("src/test/resources/images");
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));

		if (files != null && files.length > 0) {
			Random rand = new Random();
			return files[rand.nextInt(files.length)].getName();
		}
		return "default.png";
	}

	public void waitForClickable(WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForVisibilityOfAll(List<WebElement> elements) {
		new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	private void reinitializePageElements() {
		PageFactory.initElements(driver, this);
	}

	protected boolean waitUntilVisible(WebElement element, Duration timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			logger.debug("Element not visible within timeout", e);
			return false;
		}
	}

	protected boolean waitUntilClickable(WebElement element, Duration timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			logger.debug("Element not clickable within timeout", e);
			return false;
		}
	}

	// ====================== Additional helpers ======================

	protected void waitForPageLoad() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(5)).until(webDriver -> // Reduced timeout
			((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			logger.debug("Page did not reach readyState 'complete' within timeout", e);
		}
	}

	protected void waitForAjax() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(2)).until(d -> { // Reduced timeout
				try {
					Object active = ((JavascriptExecutor) d)
							.executeScript("return (window.jQuery != null) ? jQuery.active : 0");
					if (active instanceof Long) {
						return (Long) active == 0L;
					} else if (active instanceof Double) {
						return ((Double) active).intValue() == 0;
					}
					return true;
				} catch (Exception ex) {
					return true;
				}
			});
		} catch (Exception e) {
			logger.debug("waitForAjax: exception while waiting for ajax to complete", e);
		}
	}

	protected WebElement findWhenVisible(By by) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	protected boolean isElementPresent(By by) {
		return !driver.findElements(by).isEmpty();
	}

	protected void clickIfVisible(WebElement element, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		} catch (TimeoutException e) {
			logger.info("Element not visible/clickable, skipping");
		}
	}

	public void selectDropdownOption(WebElement dropdown, String optionText) {

		try {
			// Open dropdown
			safeClick(dropdown);

			// Wait ONLY for panel (not all options)
			WebElement panel = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ng-dropdown-panel")));

			// Find matching option directly
			WebElement option = panel
					.findElement(By.xpath(".//div[@role='option'][normalize-space()='" + optionText + "']"));

			safeClick(option);

		} catch (Exception e) {
			throw new NoSuchElementException("Dropdown value not found: " + optionText, e);
		}
	}

	protected void handlePassportConfirmation() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {
			// Browser alert (rare case)
			try {
				Alert alert = wait.until(ExpectedConditions.alertIsPresent());
				logger.info("Alert detected: " + alert.getText());
				alert.accept();
				return;
			} catch (TimeoutException ignored) {
			}

			// Bootstrap success modal
			WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//div[contains(@class,'modal') and contains(@class,'show')]//button[normalize-space()='Ok']")));

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[contains(@class,'modal') and contains(@class,'show')]")));

			logger.info("Passport saved successfully");

		} catch (Exception e) {
			try {
				captureScreenshot("Passport_Save_Failed");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("Passport save confirmation failed", e);
		}
	}

	public void switchToNewTab() {

		String parentWindow = driver.getWindowHandle();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(driver -> driver.getWindowHandles().size() > 1);

		for (String window : driver.getWindowHandles()) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}

		wait.until(ExpectedConditions.urlContains("Student"));
	}

	// Utility method to capture screenshot
	public static String captureScreenshot(String testName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_"
				+ Thread.currentThread().getId() + "_" + timestamp + ".png";

		File destination = new File(screenshotPath);
		File parent = destination.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		FileUtils.copyFile(source, destination);

		logger.info("Screenshot captured: " + screenshotPath);
		return screenshotPath;
	}
}