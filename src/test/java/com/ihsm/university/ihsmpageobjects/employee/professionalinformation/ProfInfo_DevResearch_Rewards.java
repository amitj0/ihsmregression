package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class ProfInfo_DevResearch_Rewards extends BasePage {
	public ProfInfo_DevResearch_Rewards(WebDriver driver) {
		super(driver);
	}

	private String lastSuccessMsg;

	// locate the web elements here

	@FindBy(xpath = "//div[@id='BTNDIVDEVELOPMENT']//span")
	private WebElement devResearchAddBtn;

	@FindBy(xpath = "//a[@href='#tab126']")
	private WebElement rewardsSubTab;

	@FindBy(name = "strTypeRewards")
	private WebElement typeOfRewardField;

	@FindBy(name = "dttDateReward")
	private WebElement dateOfRewardField;

	@FindBy(name = "strDocumentReward")
	private WebElement documentOfRewardField;

	@FindBy(name = "Docno")
	private WebElement documentNumberField;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Notes')]//following-sibling::div//textarea[@id='exampleFormControlTextarea1'])[2]")
	private WebElement notesField;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//button[contains(@class, 'btnprimary') and text()='Save'])[2]")
	private WebElement saveRewardsBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonRewards;

	@FindBy(xpath = "(//div[@class='modal-body']//span[@id='spnSuccessTextContent'])[1]")
	private WebElement modalSuccessMsg;

	// methods to perform the action

	public void devResearchAddBtn() {
		blinkElement(devResearchAddBtn);
		safeClick(devResearchAddBtn);
	}

	public void rewardsSubTab() {
		safeClick(rewardsSubTab);
	}

	public void typeOfRewardField(String rewardType) {
		typeOfRewardField.sendKeys(rewardType);
	}

	public void dateOfRewardField(String rewardDate) {
		enterDate(dateOfRewardField, rewardDate);
	}

	public void documentOfRewardField(String rewardDocument) {
		documentOfRewardField.sendKeys(rewardDocument);
	}

	public void documentNumberField(String docNumber) {
		documentNumberField.sendKeys(docNumber);
	}

	public void notesField(String notes) {
		notesField.sendKeys(notes);
	}

	public void saveRewardsBtn() {
		blinkElement(saveRewardsBtn);
		try {
			captureScreenshot("Rewared Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveRewardsBtn);
		handleAlertIfPresent();
	}

	public void okButtonRewards() {
		blinkElement(okButtonRewards);
		handleModalOk(okButtonRewards);
	}

	public boolean isRewardInfoSavedSuccessfully() {
		return okButtonRewards.isDisplayed();
	}

	public String modalSuccessMsg() throws TimeoutException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(modalSuccessMsg));
		wait.until(d -> !modalSuccessMsg.getText().trim().isEmpty());
		return modalSuccessMsg.getText().trim();
	}

	public String getLastSuccessMsg() {
		return lastSuccessMsg;
	}

	// fill rewards form
	public ProfInfo_DevResearch_Patent fillRewardsForm(String rewardType, String rewardDate, String rewardDocument,
			String docNumber, String notes) {
		devResearchAddBtn();
		rewardsSubTab();
		typeOfRewardField(rewardType);
		dateOfRewardField(rewardDate);
		documentOfRewardField(rewardDocument);
		documentNumberField(docNumber);
		notesField(notes);
		saveRewardsBtn();
		lastSuccessMsg = modalSuccessMsg();
		okButtonRewards();

		return new ProfInfo_DevResearch_Patent(driver);
	}

}
