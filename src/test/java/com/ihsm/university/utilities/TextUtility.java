package com.ihsm.university.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.ihsm.university.base.BaseClass;

public class TextUtility extends BaseClass {

	public static String getFullPageText(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript("return document.body.innerText;");
	}
}
