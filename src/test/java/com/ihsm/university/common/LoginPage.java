package com.ihsm.university.common;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ihsm.university.base.BasePage;

;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// locate the web elements

	@FindBy(xpath = "//input[@name='txtEmail']")
	private WebElement username;
	@FindBy(xpath = "//input[@name='txtPassword']")
	private WebElement password;
	@FindBy(xpath = "//button[@value='Log In']")
	private WebElement loginBtn;

	// methods to perform the actions

	public void enterUsrName(String Uname) {
		username.sendKeys(Uname);
	}

	public void enterPassword(String Upass) {
		password.sendKeys(Upass);
	}

	public void clickButton() {
		safeClick(loginBtn);
//		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//		    wait.until(ExpectedConditions.urlContains("Management/DashboardSummary"));
	}

	// login method
	public void login(String name, String pass) {
		enterUsrName(name);
		enterPassword(pass);
		clickButton();

	}

}
