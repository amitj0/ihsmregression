package com.ihsm.university.ihsmpageobjects.classchedule;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class IHSM_PromoteGroup extends BasePage {

	public IHSM_PromoteGroup(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@id='formDiv']//ng-select[@name='strSessionId']")
	private WebElement sessionField;

	@FindBy(xpath = "//ng-select[@name='strSessionId']//div[@role='option']")
	private List<WebElement> sessionFieldList;

	@FindBy(xpath = "//div[@id='formDiv']//ng-select[@name='strBatchId']")
	private WebElement batchField;

	@FindBy(xpath = "//ng-select[@name='strBatchId']//div[@role='option']")
	private List<WebElement> batchFieldList;

	@FindBy(xpath = "//div[@id='formDiv']//ng-select[@name='strAcademicPlanId']")
	private WebElement academicPlanField;

	@FindBy(xpath = "//ng-select[@name='strAcademicPlanId']//div[@role='option']")
	private WebElement academicPlanFieldList;

	@FindBy(xpath = "")
	private List<WebElement> semField;

	@FindBy(xpath = "")
	private List<WebElement> semFieldList;

	@FindBy(xpath = "//div[@id='formDiv']//input[@name='Groups']")
	private WebElement groupField;

	@FindBy(xpath = "//div[@id='formDiv']//div[contains(@class,'card-footer')]//button[normalize-space()='Save']")
	private WebElement saveButton;

	// method to perform the action

}
