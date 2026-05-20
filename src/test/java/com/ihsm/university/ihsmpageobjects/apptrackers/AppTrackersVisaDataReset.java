package com.ihsm.university.ihsmpageobjects.apptrackers;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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

public class AppTrackersVisaDataReset {

    private static final Logger log = LogManager.getLogger(AppTrackersVisaDataReset.class);

    private WebDriver driver;
    private WebDriverWait wait;

    private final By loaderLocator = By.id("divLoader");

    public AppTrackersVisaDataReset(WebDriver driver) {
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

    private void waitForLoaderToDisappear() {
        log.info(">> Waiting for page loader to disappear...");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
        log.info(">> Loader disappeared. Page is ready.");
    }

    public void login(String email, String password) {
        log.info("----------------------------------------");
        log.info(">> STEP 1: Logging in as -> {}", email);
        log.info("----------------------------------------");
        emailField.sendKeys(email);
        log.info(">> Email entered: {}", email);
        passwordField.sendKeys(password);
        log.info(">> Password entered");
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
        resetAppTrackersLink.click();
        log.info(">> Reset App Trackers link clicked");
    }

    public void resetAppTrackersIfDataPresent() {
        log.info("----------------------------------------");
        log.info(">> STEP 3: Checking for data and performing Reset if available");
        log.info("----------------------------------------");

        waitForLoaderToDisappear();

        if (resetBtn.isDisplayed() && resetBtn.isEnabled()) {
            resetBtn.click();
            log.info(">> Reset button clicked");

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertMsg = alert.getText();
            log.info(">> Alert appeared with message: {}", alertMsg);
            alert.accept();
            log.info(">> Alert accepted");

            String result = getAlertText();
            log.info(">> Result after reset: {}", result);

        } else {
            log.info("No data found to reset");
            System.out.println("No data found");
        }
    }

    public String getAlertText() {
        log.info(">> Waiting for success/failure message...");
        wait.until(ExpectedConditions.visibilityOf(alertText));
        String text = alertText.getText();
        log.info(">> Message received: {}", text);
        return text;
    }

    public static void main(String[] args) {
        Logger log = LogManager.getLogger(AppTrackersVisaDataReset.class);

        boolean headless = false;

        log.info("========================================");
        log.info("   JENKINS JOB STARTED                  ");
        log.info("   AppTrackers Reset Automation          ");
        log.info("   Mode: {}", headless ? "HEADLESS" : "UI");
        log.info("========================================");

        WebDriverManager.chromedriver().setup();
        log.info(">> WebDriverManager setup complete");

        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            log.info(">> Running in HEADLESS mode - Jenkins");
        } else {
            options.addArguments("--start-maximized");
            log.info(">> Running in UI mode - Local");
        }

        WebDriver driver = new ChromeDriver(options);
        log.info(">> ChromeDriver started successfully");

        try {
            AppTrackersVisaDataReset page = new AppTrackersVisaDataReset(driver);

            page.login("Admin", "admin@2014$");
            log.info(">> Login successful");

            page.navigateToResetAppTrackers();
            log.info(">> Navigation successful");

            page.resetAppTrackersIfDataPresent();
            log.info(">> Reset process completed");

            log.info("========================================");
            log.info("   APP TRACKERS RESET - FINISHED        ");
            log.info("========================================");

        } catch (Exception e) {
            log.error("========================================");
            log.error("   APP TRACKERS RESET - FAILED          ");
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