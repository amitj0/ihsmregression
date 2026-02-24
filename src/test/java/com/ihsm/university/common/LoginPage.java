package com.ihsm.university.common;

import java.io.IOException;
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

	@FindBy(xpath = "//span[@class='d-block']")
	private WebElement afterLoginUserName;

	// methods to perform the actions

	public void enterUsrName(String Uname) {
		wait.until(ExpectedConditions.visibilityOf(username));
		username.clear();
		username.sendKeys(Uname);
	}

	public void enterPassword(String Upass) {
		wait.until(ExpectedConditions.visibilityOf(password));
		password.clear();
		password.sendKeys(Upass);
	}

	public void clickButton() throws IOException {
		wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
		blinkElement(loginBtn);
		captureScreenshot("LoginPage_ClickButton");
		safeClick(loginBtn);
	}
	
	public void verifyLogin(String expectedUserName) {
		wait.until(ExpectedConditions.visibilityOf(afterLoginUserName));
		String actualUserName = afterLoginUserName.getText();
		Assert.assertEquals(actualUserName, expectedUserName, "Login failed: Username does not match.");
	}

	// method to perform login action
	public void login(String name, String pass) throws IOException {
		enterUsrName(name);
		enterPassword(pass);
		clickButton();
		

	}

}
