package com.ihsm.university.ErrorLogAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllUniversityErrorLogWhatsAppAPI {

    private final String todayPayloadDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private final String todayResponseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private final By whatsappSearchBox = By.xpath("//div[@contenteditable='true' and (@data-tab='3' or @data-tab='2')]");
    private final By whatsappLeftPanel = By.xpath("//div[@id='pane-side' or @id='side']");
    private final By whatsappMessageBox = By.xpath("(//div[@contenteditable='true' and @role='textbox'])[last()]");
    private final By whatsappSendButton = By.xpath("//span[@data-icon='wds-ic-send-filled']");

    @Test
    public void captureAllErrorCountsAndSendWhatsApp() {

        List<ErrorLogResult> results = new ArrayList<>();

        results.add(captureAfsirErrorCount());
        results.add(captureBIUErrorCount());
        results.add(captureGGVErrorCount());
        results.add(captureIHSMErrorCount());
        results.add(captureMSITErrorCount());
        results.add(captureSemeyErrorCount());
        results.add(captureSPPUErrorCount());
        results.add(captureTheSEDErrorCount());

        String whatsappMessage = buildWhatsAppMessage(results);

        System.out.println("=======================================");
        System.out.println("FINAL WHATSAPP MESSAGE");
        System.out.println("=======================================");
        System.out.println(whatsappMessage);

        sendWhatsAppMessage(whatsappMessage, "Personal");
    }

    private ErrorLogResult captureAfsirErrorCount() {

        String universityName = "Afsir";

        try {
            RestAssured.baseURI = "https://liveafsir.afsir.in";

            String requestBody = "{\n" +
                    "  \"intType\": 0,\n" +
                    "  \"dttDate\": \"" + todayPayloadDate + "\"\n" +
                    "}";

            Response response =
                    given()
                            .header("Content-Type", "application/json")
                            .body(requestBody)
                    .when()
                            .post("/API/Common/GetErrorLogList")
                    .then()
                            .extract()
                            .response();

            System.out.println(universityName + " Response: " + response.asPrettyString());

            Assert.assertEquals(response.getStatusCode(), 200, universityName + " HTTP failed");
            Assert.assertEquals(response.jsonPath().getInt("statusCode"), 200, universityName + " API failed");

            int count = getCountFromLstLogCountThenLstLogs(response);

            return new ErrorLogResult(universityName, count, "SUCCESS");

        } catch (Exception ex) {
            return new ErrorLogResult(universityName, 0, "FAILED: " + ex.getMessage());
        }
    }

    private ErrorLogResult captureBIUErrorCount() {

        return captureCountFromErrorLogAPI(
                "BIU",
                "https://biuapi.softsolanalytics.com",
                "/API/User/UserLogin",
                "{\n" +
                        "  \"strEmail\": \"admin@biu.com\",\n" +
                        "  \"strPassword\": \"biu123\",\n" +
                        "  \"intSource\": 1\n" +
                        "}",
                "/API/ErrorLog/GetErrorLogList",
                "{\n" +
                        "  \"intType\": 0\n" +
                        "}"
        );
    }

    private ErrorLogResult captureGGVErrorCount() {

        return captureCountFromErrorLogAPI(
                "GGV",
                "https://api.ggu.ac.in",
                "/API/User/UserLogin",
                "{\n" +
                        "  \"strEmail\": \"admin@gmail.com\",\n" +
                        "  \"strPassword\": \"ggvlms@1\",\n" +
                        "  \"intSource\": 1\n" +
                        "}",
                "/API/ErrorLog/GetErrorLogList",
                "{\n" +
                        "  \"intType\": 0\n" +
                        "}"
        );
    }

    private ErrorLogResult captureIHSMErrorCount() {

        return captureCountFromErrorLogAPI(
                "IHSM",
                "https://api.universityism.com",
                "/API/User/UserLogin",
                "{\n" +
                        "  \"strEmail\": \"admin@gmail.com\",\n" +
                        "  \"strPassword\": \"Gl0b@l$2204\",\n" +
                        "  \"intSource\": 1\n" +
                        "}",
                "/API/ErrorLog/GetErrorLogList",
                "{\n" +
                        "  \"intType\": 0,\n" +
                        "  \"dttDate\": \"" + todayPayloadDate + "\"\n" +
                        "}"
        );
    }

    private ErrorLogResult captureMSITErrorCount() {

        String universityName = "MSIT";

        try {
            RestAssured.baseURI = "https://intelsmsitapi.softsolanalytics.com";

            String loginPayload = "{\n" +
                    "  \"strEmail\": \"emp-001\",\n" +
                    "  \"strPassword\": \"tech@7id=1\",\n" +
                    "  \"intSource\": 1\n" +
                    "}";

            String accessToken = loginAndGetTokenFlexible(loginPayload, "/API/User/UserLogin");

            String errorLogPayload = "{\n" +
                    "  \"intType\": 0,\n" +
                    "  \"dttDate\": \"" + todayPayloadDate + "\"\n" +
                    "}";

            Response errorLogResponse =
                    given()
                            .header("Content-Type", "application/json")
                            .header("Authorization", accessToken)
                            .header("access_Token", accessToken)
                            .header("access_token", accessToken)
                            .body(errorLogPayload)
                    .when()
                            .post("/API/Common/GetErrorLogList")
                    .then()
                            .extract()
                            .response();

            System.out.println(universityName + " Response: " + errorLogResponse.asPrettyString());

            Assert.assertEquals(errorLogResponse.getStatusCode(), 200, universityName + " HTTP failed");
            Assert.assertEquals(errorLogResponse.jsonPath().getInt("statusCode"), 200, universityName + " API failed");

            int count = getCountFromLstLogCountThenLstLogs(errorLogResponse);

            return new ErrorLogResult(universityName, count, "SUCCESS");

        } catch (Exception ex) {
            return new ErrorLogResult(universityName, 0, "FAILED: " + ex.getMessage());
        }
    }

    private ErrorLogResult captureSemeyErrorCount() {

        return captureCountFromErrorLogAPI(
                "Semey",
                "https://intelsapi.smu.edu.kz",
                "/API/User/UserLogin",
                "{\n" +
                        "  \"strEmail\": \"admin@gmail.com\",\n" +
                        "  \"strPassword\": \"N2SVJE5JOZJL3063\",\n" +
                        "  \"intSource\": 1\n" +
                        "}",
                "/API/ErrorLog/GetErrorLogList",
                "{\n" +
                        "  \"intType\": 0\n" +
                        "}"
        );
    }

    private ErrorLogResult captureSPPUErrorCount() {

        return captureCountFromErrorLogAPI(
                "SPPU",
                "https://api.sppuef.in",
                "/API/User/UserLogin",
                "{\n" +
                        "  \"strEmail\": \"admin@gmail.com\",\n" +
                        "  \"strPassword\": \"sppulms@1\",\n" +
                        "  \"intSource\": 1\n" +
                        "}",
                "/API/ErrorLog/GetErrorLogList",
                "{\n" +
                        "  \"intType\": 0\n" +
                        "}"
        );
    }

    private ErrorLogResult captureTheSEDErrorCount() {

        String universityName = "SED";

        try {
            RestAssured.baseURI = "https://wapi.thesed.in";

            String loginPayload = "{\n" +
                    "  \"strEmail\": \"admin@gmail.com\",\n" +
                    "  \"strPassword\": \"123456\",\n" +
                    "  \"intSource\": 1\n" +
                    "}";

            String accessToken = loginAndGetTokenFlexible(loginPayload, "/API/User/UserLogin");

            String errorLogPayload = "{\n" +
                    "  \"intType\": 0,\n" +
                    "  \"dttDate\": \"" + todayPayloadDate + "\"\n" +
                    "}";

            Response errorLogResponse =
                    given()
                            .header("Content-Type", "application/json")
                            .header("Authorization", accessToken)
                            .header("access_Token", accessToken)
                            .header("access_token", accessToken)
                            .body(errorLogPayload)
                    .when()
                            .post("/API/Common/GetErrorLogList")
                    .then()
                            .extract()
                            .response();

            System.out.println(universityName + " Response: " + errorLogResponse.asPrettyString());

            Assert.assertEquals(errorLogResponse.getStatusCode(), 200, universityName + " HTTP failed");
            Assert.assertEquals(errorLogResponse.jsonPath().getInt("statusCode"), 200, universityName + " API failed");

            int count = getCountFromLstLogCountThenLstLogs(errorLogResponse);

            return new ErrorLogResult(universityName, count, "SUCCESS");

        } catch (Exception ex) {
            return new ErrorLogResult(universityName, 0, "FAILED: " + ex.getMessage());
        }
    }

    private ErrorLogResult captureCountFromErrorLogAPI(
            String universityName,
            String baseUri,
            String loginPath,
            String loginPayload,
            String errorLogPath,
            String errorLogPayload
    ) {

        try {
            RestAssured.baseURI = baseUri;

            String accessToken = loginAndGetTokenFlexible(loginPayload, loginPath);

            Response errorLogResponse =
                    given()
                            .header("Content-Type", "application/json")
                            .header("Authorization", accessToken)
                            .header("access_Token", accessToken)
                            .header("access_token", accessToken)
                            .body(errorLogPayload)
                    .when()
                            .post(errorLogPath)
                    .then()
                            .extract()
                            .response();

            System.out.println(universityName + " Response: " + errorLogResponse.asPrettyString());

            Assert.assertEquals(errorLogResponse.getStatusCode(), 200, universityName + " HTTP failed");
            Assert.assertEquals(errorLogResponse.jsonPath().getInt("statusCode"), 200, universityName + " API failed");

            int count = getCountFromLstLogCountThenLstLogs(errorLogResponse);

            return new ErrorLogResult(universityName, count, "SUCCESS");

        } catch (Exception ex) {
            return new ErrorLogResult(universityName, 0, "FAILED: " + ex.getMessage());
        }
    }

    private int getCountFromLstLogCountThenLstLogs(Response errorLogResponse) {

        List<Map<String, Object>> lstLogCount =
                errorLogResponse.jsonPath().getList("data.lstLogCount");

        if (lstLogCount != null) {

            for (Map<String, Object> row : lstLogCount) {

                String strDate = String.valueOf(row.get("strDate"));

                if (todayResponseDate.equals(strDate)) {
                    Object countValue = row.get("intErrorCount");
                    return Integer.parseInt(String.valueOf(countValue));
                }
            }

            return 0;
        }

        List<Map<String, Object>> lstLogs =
                errorLogResponse.jsonPath().getList("data.lstLogs");

        if (lstLogs != null) {
            return lstLogs.size();
        }

        return 0;
    }

    private String loginAndGetTokenFlexible(String loginPayload, String loginPath) {

        Response loginResponse =
                given()
                        .header("Content-Type", "application/json")
                        .body(loginPayload)
                .when()
                        .post(loginPath)
                .then()
                        .extract()
                        .response();

        System.out.println("Login Response: " + loginResponse.asPrettyString());

        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login HTTP failed");
        Assert.assertEquals(loginResponse.jsonPath().getInt("statusCode"), 200, "Login API failed");

        String accessToken = loginResponse.jsonPath().getString("access_Token");

        if (accessToken == null || accessToken.trim().isEmpty()) {
            accessToken = loginResponse.jsonPath().getString("access_token");
        }

        Assert.assertNotNull(accessToken, "Access token is null");
        Assert.assertFalse(accessToken.trim().isEmpty(), "Access token is empty");

        return accessToken;
    }

    private String buildWhatsAppMessage(List<ErrorLogResult> results) {

        String todayDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy hh:mm a"));

        StringBuilder message = new StringBuilder();

        message.append("*Daily Error Log Report*").append("\n");
        message.append("*Date:* ").append(todayDateTime).append("\n\n");

        int totalCount = 0;

        for (ErrorLogResult result : results) {

            message.append("*")
                    .append(result.universityName)
                    .append(":* ")
                    .append(result.errorCount)
                    .append(" errors");

            if (!"SUCCESS".equalsIgnoreCase(result.status)) {
                message.append(" | ").append(result.status);
            }

            message.append("\n");

            totalCount += result.errorCount;
        }

        message.append("\n");
        message.append("*Total Errors:* ").append(totalCount);

        return message.toString();
    }

    private void sendWhatsAppMessage(String message, String chatName) {

        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("user-data-dir=C:\\WhatsappAutomationProfile");
            options.addArguments("--profile-directory=Default");
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(40));
            js = (JavascriptExecutor) driver;

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            driver.get("https://web.whatsapp.com/");

            waitForWhatsAppLogin();
            openTargetWhatsAppChat(chatName);
            enterMessageInChatBox(message);
            sendMessage();

            Thread.sleep(3000);

        } catch (Exception e) {
            throw new RuntimeException("WhatsApp message sending failed: " + e.getMessage(), e);

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void waitForWhatsAppLogin() {

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(whatsappLeftPanel));
            System.out.println("WhatsApp already logged in");

        } catch (Exception e) {
            System.out.println("Please scan QR once in Selenium browser profile: C:\\WhatsappAutomationProfile");
            wait.until(ExpectedConditions.presenceOfElementLocated(whatsappLeftPanel));
            System.out.println("WhatsApp login completed after QR scan");
        }
    }

    private void openTargetWhatsAppChat(String chatName) {

        try {
            WebElement directChat = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@title='" + chatName + "']"))
            );

            blinkElement(directChat);
            safeClick(directChat);

            System.out.println("WhatsApp target chat clicked directly: " + chatName);

        } catch (Exception e) {

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappSearchBox));

            searchBox.click();
            searchBox.sendKeys(Keys.CONTROL + "a");
            searchBox.sendKeys(Keys.DELETE);
            searchBox.sendKeys(chatName);

            WebElement searchedChat = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@title='" + chatName + "']"))
            );

            blinkElement(searchedChat);
            safeClick(searchedChat);

            System.out.println("WhatsApp target chat opened via search: " + chatName);
        }
    }

    private void enterMessageInChatBox(String message) {

        WebElement msgBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappMessageBox));

        scrollToCenter(msgBox);
        safeClick(msgBox);

        String[] lines = message.split("\n");

        for (int i = 0; i < lines.length; i++) {

            msgBox.sendKeys(lines[i]);

            if (i < lines.length - 1) {
                msgBox.sendKeys(Keys.SHIFT, Keys.ENTER);
            }
        }

        System.out.println("Message entered in WhatsApp chat box");
    }

    private void sendMessage() {

        try {
            WebElement sendBtn = wait.until(ExpectedConditions.elementToBeClickable(whatsappSendButton));
            safeClick(sendBtn);

            System.out.println("Message sent by send button");

        } catch (Exception e) {
            WebElement msgBox = wait.until(ExpectedConditions.visibilityOfElementLocated(whatsappMessageBox));
            msgBox.sendKeys(Keys.ENTER);

            System.out.println("Message sent by ENTER key");
        }
    }

    private void safeClick(WebElement element) {

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

    private void scrollToCenter(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    private void blinkElement(WebElement element) {

        try {
            String originalStyle = element.getAttribute("style");

            for (int i = 0; i < 2; i++) {
                js.executeScript(
                        "arguments[0].setAttribute('style','border:3px solid red; background: yellow;');",
                        element
                );

                Thread.sleep(150);

                js.executeScript(
                        "arguments[0].setAttribute('style', arguments[1]);",
                        element,
                        originalStyle
                );

                Thread.sleep(150);
            }

        } catch (Exception ignored) {
        }
    }

    static class ErrorLogResult {

        String universityName;
        int errorCount;
        String status;

        ErrorLogResult(String universityName, int errorCount, String status) {
            this.universityName = universityName;
            this.errorCount = errorCount;
            this.status = status;
        }
    }
}