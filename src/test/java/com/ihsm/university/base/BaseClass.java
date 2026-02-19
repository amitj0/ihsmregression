package com.ihsm.university.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ihsm.university.common.LoginPage;
import com.ihsm.university.common.LoginPageAashu;
import com.ihsm.university.utilities.ExtentListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

@Listeners(ExtentListener.class)
public class BaseClass {

	protected static final ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(() -> null);

	private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

	protected static ExtentReports extent;
	protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	protected static WebDriver sharedDriver;

	public static Logger logger;
	protected static Properties prop;
	protected LoginPage loginPage;

	// ================= DRIVER GETTERS =================
	public static WebDriver getDriver() {
		return driver.get();
	}

	public WebDriverWait getWait() {
		return wait.get();
	}

	protected void saveCookiesToFile() throws IOException {
		Set<Cookie> cookies = getDriver().manage().getCookies();
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cookies.data"))) {
			oos.writeObject(cookies);
		}
		logger.info("Cookies saved to file");
	}

	protected void loadCookiesFromFile() throws IOException, ClassNotFoundException {
		File cookieFile = new File("cookies.data");
		if (cookieFile.exists()) {
			getDriver().get(prop.getProperty("url")); // Open base URL first
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cookieFile))) {
				Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
				for (Cookie cookie : cookies) {
					getDriver().manage().addCookie(cookie);
				}
			}
			getDriver().navigate().refresh();
			logger.info("Cookies loaded from file");
		}
	}

	// ================= LOAD CONFIG =================
	@BeforeSuite(alwaysRun = true)
	public void initConfig() {

		try {
			logger = LogManager.getLogger("BaseClass");

			prop = new Properties();
			String path = System.getProperty("user.dir") + "/src/test/resources/config.properties";

			FileInputStream fis = new FileInputStream(path);
			prop.load(fis);

			validateConfig();

		} catch (Exception e) {
			throw new RuntimeException("❌ Failed to load config.properties", e);
		}
	}

	private void validateConfig() {
		if (prop.getProperty("url") == null)
			throw new RuntimeException("❌ 'url' is missing in config.properties");

		if (prop.getProperty("username") == null)
			throw new RuntimeException("❌ 'username' is missing in config.properties");

		if (prop.getProperty("password") == null)
			throw new RuntimeException("❌ 'password' is missing in config.properties");
	}

	protected void checkServerAvailability() {
		try {
			// Use base URL from properties
			String baseUrl = prop.getProperty("url");
			if (baseUrl.contains("#")) {
				baseUrl = baseUrl.split("#")[0]; // remove fragment for health check
			}

			URL url = new URL(baseUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(30000); // 30 sec timeout
			connection.connect();
			int code = connection.getResponseCode();

			if (code != 200) {
				logger.warn("Server not ready. Status code: " + code);
				throw new SkipException("Server health check failed. Status code: " + code);
			} else {
				logger.info("Server is reachable. Status code: " + code);
			}

		} catch (SkipException se) {
			throw se; // skip gracefully
		} catch (Exception e) {
			logger.warn("Server is not reachable: " + e.getMessage());
			throw new SkipException("Server is not reachable: " + e.getMessage());
		}
	}

	// ================= BEFORE EACH TEST =================
	@Parameters({ "browser" })
	@BeforeClass(alwaysRun = true)
	public void beforeEachTest(@Optional("chrome") String browser) {

		checkServerAvailability();

		if (sharedDriver == null) {

			boolean seleniumGrid = Boolean.parseBoolean(prop.getProperty("seleniumGrid", "false"));
			boolean headless = Boolean.parseBoolean(prop.getProperty("headless", "false"));
			String gridUrl = prop.getProperty("gridURL");

			try {

				// ===== DRIVER CREATION =====
				if (seleniumGrid) {
					logger.info("Running on Selenium Grid");
					sharedDriver = getGridDriver(browser, headless, gridUrl);
				} else {
					logger.info("Running locally on browser: " + browser);
					sharedDriver = getLocalDriver(browser, headless);
				}

				driver.set(sharedDriver);
				wait.set(new WebDriverWait(sharedDriver, Duration.ofSeconds(10)));

				sharedDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
				sharedDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
				sharedDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

				sharedDriver.get(prop.getProperty("url"));
				logger.info("Navigated to URL: " + prop.getProperty("url"));

				// ===== SESSION REUSE LOGIC =====
				boolean loggedIn = false;

				try {
					loadCookiesFromFile();
					logger.info("Attempted to reuse login session from cookies");

					// Validate login by checking URL
					try {
						// Example: check logout button exists
						WebDriverWait shortWait = new WebDriverWait(sharedDriver, Duration.ofSeconds(5));

						shortWait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Sign Out')]")));

						loggedIn = true;
						logger.info("Session reused successfully.");

					} catch (Exception e) {
						logger.info("Session not valid. Login required.");
					}

				} catch (Exception e) {
					logger.info("Cookie loading failed. Will perform fresh login.");
				}

				// ===== FRESH LOGIN IF NEEDED =====
				if (!loggedIn) {

					logger.info("Performing fresh login...");

					loginPage = new LoginPage(sharedDriver);
					loginPage.login(prop.getProperty("username"), prop.getProperty("password"));

					try {
						saveCookiesToFile();
						logger.info("Cookies saved successfully.");
					} catch (IOException ioException) {
						logger.warn("Failed to save cookies: " + ioException.getMessage());
					}
				}

			} catch (Exception e) {
				logger.error("❌ Setup failed. Aborting test.", e);
				throw new RuntimeException("Test setup failed", e);
			}

		} else {

			driver.set(sharedDriver);
			wait.set(new WebDriverWait(sharedDriver, Duration.ofSeconds(10)));
			logger.info("Reusing shared driver");

		}
	}

	// ================= LOCAL DRIVER =================
	private WebDriver getLocalDriver(String browser, boolean headless) {

		switch (browser.toLowerCase()) {

		case "chrome":
			WebDriverManager.chromedriver().setup();

			ChromeOptions chrome = new ChromeOptions();
			chrome.addArguments("--disable-infobars");
			chrome.addArguments("--disable-blink-features=AutomationControlled");
			chrome.addArguments("--disable-notifications");
			chrome.addArguments("--disable-animations");
			chrome.addArguments("--disable-features=MediaRouter");
			chrome.addArguments("--block-new-web-contents");
			chrome.addArguments("--disable-save-password-bubble");
			chrome.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
			chrome.setExperimentalOption("useAutomationExtension", false);

			// Set preferences to block the permission popup
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("profile.default_content_setting_values.media_stream", 2);
			prefs.put("profile.default_content_setting_values.notifications", 2);
			prefs.put("profile.default_content_setting_values.local_discovery", 2);
			prefs.put("profile.default_content_setting_values.geolocation", 2);
			prefs.put("profile.default_content_setting_values.midi_sysex", 2);
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			chrome.setExperimentalOption("prefs", prefs);

			chrome.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
			chrome.setPageLoadStrategy(PageLoadStrategy.EAGER); // Changed from NORMAL for faster page loads

			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			chrome.setCapability("goog:loggingPrefs", logPrefs);

			if (headless) {
				chrome.addArguments("--headless=new", "--window-size=1920,1080", "--disable-gpu", "--no-sandbox",
						"--disable-dev-shm-usage");
			} else {
				chrome.addArguments("--start-maximized");
			}

			return new ChromeDriver(chrome);

		case "firefox":
			WebDriverManager.firefoxdriver().setup();

			FirefoxOptions firefox = new FirefoxOptions();
			if (headless) {
				firefox.addArguments("--headless");
				firefox.addArguments("--width=1920", "--height=1080");
			}

			return new FirefoxDriver(firefox);

		default:
			throw new RuntimeException("Unsupported browser: " + browser);
		}
	}

	// ================= GRID DRIVER =================
	private WebDriver getGridDriver(String browser, boolean headless, String gridUrl) throws Exception {

		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();

			// Disable automation detection and permissions
			options.addArguments("--disable-features=MediaRouter");
			options.addArguments("--disable-blink-features=AutomationControlled");
			options.addArguments("--disable-notifications");
			options.addArguments("--block-new-web-contents");
			options.addArguments("--disable-save-password-bubble");
			options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
			options.setExperimentalOption("useAutomationExtension", false);

			// Set preferences to block permission popups
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("profile.default_content_setting_values.media_stream", 2);
			prefs.put("profile.default_content_setting_values.notifications", 2);
			prefs.put("profile.default_content_setting_values.local_discovery", 2);
			prefs.put("profile.default_content_setting_values.geolocation", 2);
			prefs.put("profile.default_content_setting_values.midi_sysex", 2);
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);

			options.setPageLoadStrategy(PageLoadStrategy.EAGER); // Changed from NORMAL

			if (headless) {
				options.addArguments("--headless=new", "--window-size=1920,1080", "--disable-gpu", "--no-sandbox",
						"--disable-dev-shm-usage");
			}
			return new RemoteWebDriver(new URL(gridUrl), options);
		}

		if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			if (headless) {
				options.addArguments("--headless");
				options.addArguments("--width=1920", "--height=1080");
			}
			return new RemoteWebDriver(new URL(gridUrl), options);
		}

		throw new RuntimeException("Unsupported Grid browser: " + browser);
	}

	// ================= AFTER EACH TEST =================
	@AfterClass(alwaysRun = false)
	public void tearDown() {
		if (getDriver() != null) {

			driver.remove();
			wait.remove();
		}
	}

	@AfterSuite(alwaysRun = false)
	public void afterSuite() {
		if (sharedDriver != null) {
			sharedDriver.quit();
			sharedDriver = null;
			logger.info("Shared browser closed after suite");
		}
	}

	// for clean start for the new test
	@AfterMethod(alwaysRun = true)
	public void cleanUpAfterEachTest(ITestResult result) {

		// Refresh ONLY if test failed or skipped
		if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {

			try {
				System.out.println("Test failed/skipped → cleaning up");

				getDriver().switchTo().defaultContent();
				getDriver().navigate().refresh();

			} catch (Exception e) {
				System.out.println("Cleanup failed: " + e.getMessage());
			}
		}
	}

	// ================= SCREENSHOT =================
	public static String captureScreenshot(String testName) throws IOException {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

		String path = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + time + ".png";

		File dest = new File(path);
		dest.getParentFile().mkdirs();
		FileUtils.copyFile(src, dest);

		return path;
	}

	// ================= Get test data file paths ==========
	protected String getTestDataPath(String fileName) {
		return System.getProperty("user.dir") + "/src/test/resources/images/" + fileName;
	}
}