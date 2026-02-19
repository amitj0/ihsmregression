package com.ihsm.university.common;

import org.testng.annotations.Test;

import com.ihsm.university.base.BaseClass;

public class LoginTest extends BaseClass {

    @Test(priority = 0, description = "Send 1 lakh messages with 200ms delay")
    public void verifyLogin() throws InterruptedException {

        LoginPageAashu loginPage = new LoginPageAashu(getDriver());

        // First perform login
        loginPage.login3("Start");

        // Now send 1 lakh messages
        for (int i = 1; i <= 100000; i++) {

            loginPage.messageInputField("Testing number : " + i);

            Thread.sleep(200);  
        }
    }
}
