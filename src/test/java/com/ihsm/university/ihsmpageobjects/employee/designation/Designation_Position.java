package com.ihsm.university.ihsmpageobjects.employee.designation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class Designation_Position extends BasePage {

	public Designation_Position(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@id='btnemppositioninotherorg']//span")
	private WebElement addPositionInOtherOrgBtn;

	@FindBy(xpath = "//div[@id='staticBackdropPositionOther']//ng-select[@name='strRating']")
	private WebElement experienceRatingDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> experienceRatingDropdownOptions;

	@FindBy(name = "dttPostionOtherStartDate")
	private WebElement expStartDateField;

	@FindBy(name = "dttPostionOtherEndDate")
	private WebElement expEndDateField;

	@FindBy(name = "strOrganizationName")
	private WebElement organizationNameField;

	@FindBy(name = "strOtherPosition")
	private WebElement positionInOtherOrgField;

	@FindBy(xpath = "//div[@id='staticBackdropPositionOther']//input[@name='IIN']")
	private WebElement noteField;

	@FindBy(xpath = "//div[@id='staticBackdropPositionOther']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement savePositionInOtherOrgBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	// methods to perform the action
	public void addPositionInOtherOrgBtn() {
		blinkElement(addPositionInOtherOrgBtn);
		safeClick(addPositionInOtherOrgBtn);
	}

	public void experienceRatingDropdownField() {
		safeClick(experienceRatingDropdownField);
	}

	public void experienceRatingDropdownOptions(String rating) {
		safeClick(experienceRatingDropdownField);
		for (WebElement option : experienceRatingDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(rating)) {
				safeClick(option);
				return;
			}
		}
	}

	public void expStartDateField(String startDate) {
		enterDate(expStartDateField, startDate);
	}

	public void expEndDateField(String endDate) {
		enterDate(expEndDateField, endDate);
	}

	public void organizationNameField(String organizationName) {
		organizationNameField.sendKeys(organizationName);
	}

	public void positionInOtherOrgField(String positionInOtherOrg) {
		positionInOtherOrgField.sendKeys(positionInOtherOrg);
	}

	public void noteField(String note) {
		noteField.sendKeys(note);
	}

	public void savePositionInOtherOrgBtn() {
		blinkElement(savePositionInOtherOrgBtn);
		try {
			captureScreenshot("Position In Other Organization Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}

		safeClick(savePositionInOtherOrgBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		handleModalOk(okButtonSuccessPopup);
	}
	
	public boolean isDesPositionInfoSavedSuccessfully() {
		return okButtonSuccessPopup.isDisplayed();
	}

	// fill position in other organization form
	public void fillPositionInOtherOrgForm(String rating, String startDate, String endDate, String organizationName,
			String positionInOtherOrg, String note) {
		addPositionInOtherOrgBtn();
		experienceRatingDropdownField();
		experienceRatingDropdownOptions(rating);
		expStartDateField(startDate);
		expEndDateField(endDate);
		organizationNameField(organizationName);
		positionInOtherOrgField(positionInOtherOrg);
		noteField(note);
		savePositionInOtherOrgBtn();
		okButtonSuccessPopup();
		// return here
	}

}
