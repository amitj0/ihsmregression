package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class ProfInfo_DevResearch_Rewards extends BasePage {
	public ProfInfo_DevResearch_Rewards(WebDriver driver) {
		super(driver);
	}

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

	// fill rewards form
	public void fillRewardsForm(String rewardType, String rewardDate, String rewardDocument, String docNumber,
			String notes) {
		devResearchAddBtn();
		rewardsSubTab();
		typeOfRewardField(rewardType);
		dateOfRewardField(rewardDate);
		documentOfRewardField(rewardDocument);
		documentNumberField(docNumber);
		notesField(notes);
		saveRewardsBtn();
		okButtonRewards();

		// return instance for method chaining
	}

}
