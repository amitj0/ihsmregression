package com.ihsm.university.ihsmtestcases.flows.student;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;

public class CaptureEntireApplicationText extends BaseClass {

    @Test(description = "Login and capture complete application DOM (visible + hidden)")
    public void captureEntireApplicationText() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(40));

        // Wait until page completely loads
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // Small extra wait for Angular/React rendering
        try {
            Thread.sleep(5000);  // allow SPA to fully render
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Capture full DOM (VISIBLE + HIDDEN)
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        String fullDOM = (String) js.executeScript(
                "return document.documentElement.outerHTML;"
        );

        System.out.println("============= FULL APPLICATION DOM =============");
        System.out.println(fullDOM);
        System.out.println("============= END OF DOM =============");
    }
}
