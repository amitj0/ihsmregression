package com.ihsm.university.ihsmtestcases.apitests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.LogType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class FocusFlowTest {

    private static final String TARGET_URL = "https://focusflow.softsolanalytics.com/";
    private static final int TOTAL_USERS = 1000;
    private static final int THREAD_POOL_SIZE = 50;
    private static final int WAIT_SECONDS = 15;

    // ✅ SET THIS TO false FOR UI MODE, true FOR HEADLESS
    private static final boolean HEADLESS = false;

    private static final AtomicInteger success = new AtomicInteger(0);
    private static final AtomicInteger failed = new AtomicInteger(0);
    private static final AtomicInteger completed = new AtomicInteger(0);

    private static final String CONSOLE_INTERCEPTOR = """
        window.__consoleLogs = [];
        const originalLog = console.log;
        const originalWarn = console.warn;
        const originalError = console.error;
        
        console.log = function(...args) {
            window.__consoleLogs.push({level: 'LOG', message: args.join(' ')});
            originalLog.apply(console, args);
        };
        console.warn = function(...args) {
            window.__consoleLogs.push({level: 'WARN', message: args.join(' ')});
            originalWarn.apply(console, args);
        };
        console.error = function(...args) {
            window.__consoleLogs.push({level: 'ERROR', message: args.join(' ')});
            originalError.apply(console, args);
        };
    """;

    public static ChromeOptions getFakeCameraOptions() {
        ChromeOptions options = new ChromeOptions();

        // ✅ Fake webcam/microphone - no real camera needed
        options.addArguments("--use-fake-device-for-media-stream");
        options.addArguments("--use-fake-ui-for-media-stream");

        // ✅ Toggle headless/UI mode
        if (HEADLESS) {
            options.addArguments("--headless=new");
            System.out.println("🖥️  Mode: Headless");
        } else {
            System.out.println("🖥️  Mode: UI (visible browser)");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--window-size=1280,720");

        // ✅ Enable browser logging
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);

        return options;
    }

    public static void runUser(int userId) {
        ChromeDriver driver = null;
        try {
            driver = new ChromeDriver(getFakeCameraOptions());
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            // ✅ Inject console interceptor before page loads
            driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument",
                    Map.of("source", CONSOLE_INTERCEPTOR));

            long startTime = System.currentTimeMillis();
            driver.get(TARGET_URL);
            long loadTime = System.currentTimeMillis() - startTime;

            System.out.println("[User " + userId + "] ✅ Page loaded in " + loadTime + "ms");

            // ✅ Poll console logs every 3 seconds
            JavascriptExecutor js = driver;
            long endTime = System.currentTimeMillis() + (WAIT_SECONDS * 1000L);

            while (System.currentTimeMillis() < endTime) {
                Thread.sleep(3000);

                List<Object> logs = (List<Object>) js.executeScript(
                    "return window.__consoleLogs ? window.__consoleLogs.splice(0) : [];"
                );

                if (logs != null && !logs.isEmpty()) {
                    for (Object log : logs) {
                        Map<String, String> entry = (Map<String, String>) log;
                        String level = entry.get("level");
                        String message = entry.get("message");
                        System.out.println("[User " + userId + "] 🌐 [" + level + "] " + message);
                    }
                }
            }

            int done = completed.incrementAndGet();
            success.incrementAndGet();
            System.out.println("[User " + userId + "] ✅ Done | Completed: " + done + "/" + TOTAL_USERS);

        } catch (Exception e) {
            int done = completed.incrementAndGet();
            failed.incrementAndGet();
            System.out.println("[User " + userId + "] ❌ Failed: " + e.getMessage() + " | Completed: " + done + "/" + TOTAL_USERS);
        } finally {
            if (driver != null) {
                try { driver.quit(); } catch (Exception ignored) {}
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("🚀 Starting load test: " + TOTAL_USERS + " users on " + TARGET_URL);
        System.out.println("🧵 Thread pool size: " + THREAD_POOL_SIZE);
        System.out.println("📷 Fake camera stream enabled");
        System.out.println("📋 Browser console log capture enabled\n");

        long startTime = System.currentTimeMillis();

        // ⚠️ For UI mode testing, reduce users to just 1-5 first!
        int testUsers = HEADLESS ? TOTAL_USERS : 1;
        System.out.println("👥 Running " + testUsers + " user(s) in " + (HEADLESS ? "headless" : "UI") + " mode\n");

        ExecutorService executor = Executors.newFixedThreadPool(HEADLESS ? THREAD_POOL_SIZE : 1);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 1; i <= testUsers; i++) {
            final int userId = i;
            futures.add(executor.submit(() -> runUser(userId)));
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                System.out.println("Execution error: " + e.getMessage());
            }
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);

        double duration = (System.currentTimeMillis() - startTime) / 1000.0;

        System.out.println("\n========== RESULTS ==========");
        System.out.println("✅ Successful : " + success.get());
        System.out.println("❌ Failed     : " + failed.get());
        System.out.printf("⏱️  Total Time : %.2f seconds%n", duration);
        System.out.println("==============================");
    }
}