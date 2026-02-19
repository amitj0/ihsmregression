package com.ihsm.university.common;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ihsm.university.base.BasePage;

;

public class LoginPageAashu extends BasePage {

	public LoginPageAashu(WebDriver driver) {
		super(driver);
	}

	// locate the web elements

	@FindBy(id = "login-username")
	private WebElement username;
	@FindBy(id = "login-password")
	private WebElement password;
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Login']")
	private WebElement loginBtn;
	
	@FindBy(xpath = "//div[@class='conv-title']")
	private WebElement afterLoginSideMenu;
	
	@FindBy(xpath = "//input[@id='message-input']")
	private WebElement messageInputField;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()=\"Send\"]")
	private WebElement sendBtn;

	// methods to perform the actions

	public void enterUsrName(String Uname) {
		username.sendKeys(Uname);
	}

	public void enterPassword(String Upass) {
		password.sendKeys(Upass);
	}

	public void clickButton() {
		blinkElement(loginBtn);
		try {
			captureScreenshot("Login Details");
		} catch (IOException e) {
			e.printStackTrace();
		}
		safeClick(loginBtn);
	}
	
	public void afterLoginSideMenu() {
		safeClick(afterLoginSideMenu);
	}
	
	public void messageInputField(String message) {
		messageInputField.clear();
	    messageInputField.sendKeys(message);
	    messageInputField.sendKeys(Keys.ENTER);
	}
	
	public void clickSendButton() {
		safeClick(sendBtn);
	}
	
	

	// login method
	public void login2(String name, String pass) {
		enterUsrName(name);
		enterPassword(pass);
		clickButton();

	}
	public void login3(String msg) {
		
		afterLoginSideMenu();
		messageInputField(msg);
	}

}
