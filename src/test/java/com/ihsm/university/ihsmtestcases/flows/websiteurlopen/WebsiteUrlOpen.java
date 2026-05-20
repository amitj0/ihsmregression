package com.ihsm.university.ihsmtestcases.flows.websiteurlopen;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WebsiteUrlOpen {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;

    private static final String WHATSAPP_PROFILE_PATH = "C:\\WhatsappAutomationProfile";
    private static final String WHATSAPP_CHAT_NAME = "Personal";

    private static final By whatsappSearchBox =
            By.xpath("//div[@contenteditable='true' and (@data-tab='3' or @data-tab='2')]");

    private static final By whatsappLeftPanel =
            By.xpath("//div[@id='pane-side' or @id='side']");

    private static final By whatsappMessageBox =
            By.xpath("(//div[@contenteditable='true' and @role='textbox'])[last()]");

    private static final By whatsappSendButton =
            By.xpath("//span[@data-icon='wds-ic-send-filled']");

    @Test
    public void websiteUrlOpenTest() {

        String[] urls = {
                "https://werify.in/",
                "https://tajhind.com/",
                "https://www.ismedusoftsol.com/",
                "https://gdeconsultancy.com/",
                "https://caspianuniversity.com/",
                "https://biu.university/",
                "https://theamitchauhan.com/",
                "https://afsir.in/",
                "https://designandpave.co.uk/",
                "https://hippotravel.in/",
                "https://scope-electrical.com/",
                "https://learnity.in/",
                "https://apptracker.in/",
                "https://gnap.in/",
                "https://sanctuarygardenrooms.uk/",
                "https://kyromation.co.uk/",
                "https://universityism.com/#/"
        };

        int passCount = 0;
        int failCount = 0;

        List<String> failedUrlList = new ArrayList<>();

        try {
            ChromeOptions options = new ChromeOptions();

            // If Jenkins runs without visible desktop session, uncomment below line.
            // options.addArguments("--headless=new");

            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-notifications");
            options.addArguments("user-data-dir=" + WHATSAPP_PROFILE_PATH);
            options.addArguments("--profile-directory=Default");
            options.addArguments("--remote-allow-origins=*");
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(40));
            js = (JavascriptExecutor) driver;

            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            for (String url : urls) {
                try {
                    System.out.println("Checking URL: " + url);

                    driver.get(url);
                    waitUntilPageReady();

                    String currentUrl = driver.getCurrentUrl();
                    String title = driver.getTitle();

                    System.out.println("Current URL: " + currentUrl);
                    System.out.println("Page Title: " + title);

                    if (isUrlOpenedProperly(currentUrl, title)) {
                        System.out.println("PASS: URL opened successfully.");
                        passCount++;
                    } else {
                        String reason = "Page did not load properly";
                        System.out.println("FAIL: URL not opening: " + url);
                        System.out.println("Error: " + reason);

                        failedUrlList.add(url);
                        failCount++;
                    }

                    System.out.println("--------------------------------------");

                } catch (TimeoutException ex) {
                    String reason = "Page load timeout after 30 seconds";
                    System.out.println("FAIL: URL not opening: " + url);
                    System.out.println("Error: " + reason);

                    failedUrlList.add(url);
                    failCount++;
                    stopPageLoading();

                    System.out.println("--------------------------------------");

                } catch (Exception ex) {
                    String reason = ex.getMessage() == null ? ex.toString() : ex.getMessage();
                    System.out.println("FAIL: URL not opening: " + url);
                    System.out.println("Error: " + reason);

                    failedUrlList.add(url);
                    failCount++;

                    System.out.println("--------------------------------------");
                }
            }

            System.out.println("========== FINAL SUMMARY ==========");
            System.out.println("Total URLs Checked: " + urls.length);
            System.out.println("Total Passed: " + passCount);
            System.out.println("Total Failed: " + failCount);
            System.out.println("===================================");

            String whatsappMessage = buildFailedUrlWhatsAppMessage(
                    urls.length,
                    passCount,
                    failCount,
                    failedUrlList
            );

            System.out.println("Sending WhatsApp message...");
            System.out.println(whatsappMessage);

            openWhatsAppWebInNewTab();
            waitForWhatsAppLogin();
            openTargetWhatsAppChat();
            enterMessageInChatBox(whatsappMessage);
            sendMessage();

            System.out.println("WhatsApp message sent successfully.");

            if (failCount > 0) {
                System.out.println("Some URLs failed to open, but Jenkins build will remain SUCCESS.");
                System.out.println("Failed count: " + failCount);
                System.out.println("Failed URLs: " + failedUrlList);
            }

        } finally {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }

            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static boolean isUrlOpenedProperly(String currentUrl, String title) {
        if (currentUrl == null || currentUrl.trim().isEmpty()) {
            return false;
        }

        String lowerCurrentUrl = currentUrl.toLowerCase();
        String lowerTitle = title == null ? "" : title.toLowerCase();

        if (lowerCurrentUrl.startsWith("chrome-error://")) {
            return false;
        }

        if (lowerTitle.contains("this site can’t be reached")
                || lowerTitle.contains("this site can't be reached")
                || lowerTitle.contains("privacy error")
                || lowerTitle.contains("not found")
                || lowerTitle.contains("error")) {
            return false;
        }

        return true;
    }

    private static void waitUntilPageReady() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(webDriver ->
                    js.executeScript("return document.readyState").equals("complete"));
        } catch (Exception ignored) {
        }
    }

    private static void stopPageLoading() {
        try {
            js.executeScript("window.stop();");
        } catch (Exception ignored) {
        }
    }

    private static String buildFailedUrlWhatsAppMessage(int totalUrls, int passCount, int failCount, List<String> failedUrlList) {
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy hh:mm a"));

        StringBuilder sb = new StringBuilder();

        sb.append("*Website URL Open Test Report*").append("\n");
        sb.append("Date: ").append(currentDateTime).append("\n");
        sb.append("Total URLs Checked: ").append(totalUrls).append("\n");
        sb.append("Passed: ").append(passCount).append("\n");
        sb.append("Failed: ").append(failCount).append("\n\n");

        if (failCount == 0 || failedUrlList == null || failedUrlList.isEmpty()) {
            sb.append("All URLs opened successfully.");
            return sb.toString();
        }

        sb.append("*Failed URL Details:*").append("\n");

        for (int i = 0; i < failedUrlList.size(); i++) {
            sb.append(i + 1).append(". ").append(failedUrlList.get(i));

            if (i < failedUrlList.size() - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private static void openWhatsAppWebInNewTab() {
        js.executeScript("window.open('https://web.whatsapp.com/', '_blank');");
        switchToNewestTab();
        wait.until(ExpectedConditions.urlContains("web.whatsapp.com"));
        System.out.println("WhatsApp Web opened in new tab.");
    }

    private static void waitForWhatsAppLogin() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(whatsappLeftPanel));
            System.out.println("WhatsApp already logged in.");
        } catch (Exception e) {
            System.out.println("Please scan QR once in Selenium browser profile: " + WHATSAPP_PROFILE_PATH);
            wait.until(ExpectedConditions.presenceOfElementLocated(whatsappLeftPanel));
            System.out.println("WhatsApp login completed after QR scan.");
        }
    }

    private static void openTargetWhatsAppChat() {
        try {
            WebElement targetChat = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[@title='" + WHATSAPP_CHAT_NAME + "']")));

            safeClick(targetChat);
            System.out.println("WhatsApp target chat clicked directly.");

        } catch (Exception e) {
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappSearchBox));

            searchBox.click();
            searchBox.sendKeys(Keys.CONTROL + "a");
            searchBox.sendKeys(Keys.DELETE);
            searchBox.sendKeys(WHATSAPP_CHAT_NAME);

            WebElement searchedChat = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[@title='" + WHATSAPP_CHAT_NAME + "']")));

            safeClick(searchedChat);
            System.out.println("WhatsApp target chat opened via search.");
        }
    }

    private static void enterMessageInChatBox(String message) {
        WebElement msgBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappMessageBox));
        scrollToCenter(msgBox);
        safeClick(msgBox);

        String[] lines = message.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {
            msgBox.sendKeys(lines[i]);

            if (i < lines.length - 1) {
                msgBox.sendKeys(Keys.SHIFT, Keys.ENTER);
            }
        }

        System.out.println("Combined message entered in WhatsApp chat box.");
    }

    private static void sendMessage() {
        try {
            WebElement sendBtn = wait.until(ExpectedConditions.elementToBeClickable(whatsappSendButton));
            safeClick(sendBtn);
            System.out.println("Message sent by send button.");
        } catch (Exception e) {
            WebElement msgBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappMessageBox));
            msgBox.sendKeys(Keys.ENTER);
            System.out.println("Message sent by ENTER key.");
        }
    }

    private static void switchToNewestTab() {
        String newestWindow = null;

        for (String windowHandle : driver.getWindowHandles()) {
            newestWindow = windowHandle;
        }

        if (newestWindow != null) {
            driver.switchTo().window(newestWindow);
        }
    }

    private static void safeClick(WebElement element) {
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

    private static void scrollToCenter(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    private static void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }
}