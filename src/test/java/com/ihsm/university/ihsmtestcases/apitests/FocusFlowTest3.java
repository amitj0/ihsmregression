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

public class FocusFlowTest3 {

    private static final String TARGET_URL = "https://focusflow.softsolanalytics.com/";
    private static final int TOTAL_USERS = 1000;
    private static final int BATCH_SIZE   = 10;   // 20 browsers open at a time (safe for 16GB RAM)
    private static final int WAIT_SECONDS = 15;   // each user stays on page 15s

    // ✅ SET TO false FOR UI MODE, true FOR HEADLESS
    private static final boolean HEADLESS = false;

    // ✅ Path to your .y4m face video file
    private static final String FAKE_VIDEO_PATH = "C://Users//Amit Gangra//Downloads//face.y4m";

    private static final AtomicInteger success   = new AtomicInteger(0);
    private static final AtomicInteger failed    = new AtomicInteger(0);
    private static final AtomicInteger completed = new AtomicInteger(0);

    private static final String CONSOLE_INTERCEPTOR = """
        window.__consoleLogs = [];
        const originalLog   = console.log;
        const originalWarn  = console.warn;
        const originalError = console.error;
        console.log = function(...args) {
            window.__consoleLogs.push({level:'LOG',   message: args.join(' ')});
            originalLog.apply(console, args);
        };
        console.warn = function(...args) {
            window.__consoleLogs.push({level:'WARN',  message: args.join(' ')});
            originalWarn.apply(console, args);
        };
        console.error = function(...args) {
            window.__consoleLogs.push({level:'ERROR', message: args.join(' ')});
            originalError.apply(console, args);
        };
    """;

    public static ChromeOptions getFakeCameraOptions() {
        ChromeOptions options = new ChromeOptions();

        // ✅ Fake webcam/microphone
        options.addArguments("--use-fake-device-for-media-stream");
        options.addArguments("--use-fake-ui-for-media-stream");

        // ✅ Use real face video file as fake camera
        if (!FAKE_VIDEO_PATH.isEmpty()) {
            options.addArguments("--use-file-for-fake-video-capture=" + FAKE_VIDEO_PATH);
        }

        // ✅ Toggle headless/UI mode
        if (HEADLESS) {
            options.addArguments("--headless=new");
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
                        System.out.println("[User " + userId + "] 🌐 [" + entry.get("level") + "] " + entry.get("message"));
                    }
                }
            }

            success.incrementAndGet();
            int done = completed.incrementAndGet();
            System.out.println("[User " + userId + "] ✅ Done | Total completed: " + done + "/" + TOTAL_USERS);

        } catch (Exception e) {
            failed.incrementAndGet();
            int done = completed.incrementAndGet();
            System.out.println("[User " + userId + "] ❌ Failed: " + e.getMessage() + " | Total completed: " + done + "/" + TOTAL_USERS);
        } finally {
            if (driver != null) {
                try { driver.quit(); } catch (Exception ignored) {}
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int totalBatches = (int) Math.ceil((double) TOTAL_USERS / BATCH_SIZE);

        System.out.println("🚀 Load Test Started");
        System.out.println("👥 Total Users  : " + TOTAL_USERS);
        System.out.println("📦 Batch Size   : " + BATCH_SIZE + " browsers at a time");
        System.out.println("🔁 Total Batches: " + totalBatches);
        System.out.println("📷 Fake Camera  : " + (FAKE_VIDEO_PATH.isEmpty() ? "Default (no face)" : FAKE_VIDEO_PATH));
        System.out.println("🖥️  Mode         : " + (HEADLESS ? "Headless" : "UI (visible)"));
        System.out.println("⏱️  Wait/user    : " + WAIT_SECONDS + "s");
        System.out.println("⏳ Est. duration: ~" + (totalBatches * WAIT_SECONDS / 60) + " mins\n");

        long startTime = System.currentTimeMillis();

        // ✅ Run in batches: 20 users at a time, 50 batches = 1000 total
        for (int batch = 0; batch < totalBatches; batch++) {
            int batchStart = batch * BATCH_SIZE + 1;
            int batchEnd   = Math.min(batchStart + BATCH_SIZE - 1, TOTAL_USERS);

            System.out.println("\n--- 🔁 Batch " + (batch + 1) + "/" + totalBatches
                    + " | Users " + batchStart + " to " + batchEnd + " ---");

            ExecutorService executor = Executors.newFixedThreadPool(BATCH_SIZE);
            List<Future<?>> futures  = new ArrayList<>();

            for (int userId = batchStart; userId <= batchEnd; userId++) {
                final int uid = userId;
                futures.add(executor.submit(() -> runUser(uid)));
            }

            // Wait for entire batch to finish before starting next
            for (Future<?> future : futures) {
                try { future.get(); }
                catch (ExecutionException e) {
                    System.out.println("Batch execution error: " + e.getMessage());
                }
            }

            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.MINUTES);

            System.out.println("--- ✅ Batch " + (batch + 1) + " complete ---");
        }

        double duration = (System.currentTimeMillis() - startTime) / 1000.0;

        System.out.println("\n========== FINAL RESULTS ==========");
        System.out.println("✅ Total Users  : " + TOTAL_USERS);
        System.out.println("✅ Successful   : " + success.get());
        System.out.println("❌ Failed       : " + failed.get());
        System.out.printf("⏱️  Total Time   : %.2f seconds (%.1f mins)%n", duration, duration / 60);
        System.out.printf("⚡ Avg Speed    : %.2f users/sec%n", TOTAL_USERS / duration);
        System.out.println("====================================");
    }
}