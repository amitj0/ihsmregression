package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class ProfInfo_Military extends BasePage {

	public ProfInfo_Military(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@id='divbtnmilitaryrank']//span")
	private WebElement addMilitaryInfoBtn;

	@FindBy(xpath = "//div[@id='MilitaryRankModelId']//ng-select[@name='RANK']")
	private WebElement militaryRankDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> militaryRankDropdownOptions;

	@FindBy(xpath = "(//div[@id='MilitaryRankModelId']//label[contains(normalize-space(),'Rank')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addMilitaryRankPlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@type='text']")
	private WebElement militaryRankInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveMilitaryRankButton;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	@FindBy(xpath = "(//div[@id='MilitaryRankModelId']//input[@type=\"text\"])[2]")
	private WebElement militaryOrderField;

	@FindBy(xpath = "//div[@id='MilitaryRankModelId']//input[@type=\"date\"]")
	private WebElement militaryOrderDateField;

	@FindBy(xpath = "(//div[@id='MilitaryRankModelId']//textarea[@name='notes'])[1]")
	private WebElement notesField;

	@FindBy(xpath = "//div[@id='MilitaryRankModelId']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveMilitaryInfoBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonMilitry;

	// methods to perform the action
	public void addMilitaryInfoBtn() {
		blinkElement(addMilitaryInfoBtn);
		safeClick(addMilitaryInfoBtn);
	}

	public void militaryRankDropdownField() {
		safeClick(militaryRankDropdownField);
	}

	public void militaryRankDropdownOptions(String rank) {
		// Open dropdown
		safeClick(militaryRankDropdownField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : militaryRankDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(rank)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → add new Degree Type
		if (!found) {

			// Click +
			safeClick(addMilitaryRankPlusButton);

			// Enter Academics type
			safeClick(militaryRankInputField);
			militaryRankInputField.sendKeys(rank);

			// Save
			safeClick(saveMilitaryRankButton);

			// Ok
			safeClick(okButtonSuccessPopup);

			// Reopen dropdown
			safeClick(militaryRankDropdownField);

			// Select newly added value
			for (WebElement option : militaryRankDropdownOptions) {
				if (option.getText().trim().equalsIgnoreCase(rank)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Militry value not found even after adding: " + rank);
		}
	}

	public void enterMilitaryOrder(String militaryOrder) {
		militaryOrderField.sendKeys(militaryOrder);
	}

	public void enterMilitaryOrderDate(String militaryOrderDate) {
		enterDate(militaryOrderDateField, militaryOrderDate);
	}

	public void enterNotes(String notes) {
		notesField.sendKeys(notes);
	}

	public void saveMilitaryInfoBtn() {
		blinkElement(saveMilitaryInfoBtn);
		try {
			captureScreenshot("Military Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveMilitaryInfoBtn);
		handleAlertIfPresent();
	}

	public void okButtonMilitry() {
		try {
			// Wait for success modal to appear after form submission
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(okButtonMilitry));

			logger.info("Success modal appeared");

			blinkElement(okButtonMilitry);
			handleModalOk(okButtonMilitry);

		} catch (Exception e) {
			logger.error("Success modal did not appear", e);

			// Take screenshot for debugging
			try {
				captureScreenshot("No_Success_Modal_After_Biometrics_Save");
			} catch (Exception ex) {
				logger.error("Screenshot failed", ex);
			}

			throw new RuntimeException("Success modal did not appear after biometrics save", e);
		}
	}

	public boolean isMilitaryInfoSectionDisplayed() {
		return addMilitaryInfoBtn.isDisplayed();
	}

	// fill military information form
	public void fillMilitaryInformationForm(String rank, String militaryOrder, String militaryOrderDate, String notes) {

		addMilitaryInfoBtn();
		militaryRankDropdownField();
		militaryRankDropdownOptions(rank);
		enterMilitaryOrder(militaryOrder);
		enterMilitaryOrderDate(militaryOrderDate);
		enterNotes(notes);
		saveMilitaryInfoBtn();
		okButtonMilitry();

		// return instance of this class
	}

}
