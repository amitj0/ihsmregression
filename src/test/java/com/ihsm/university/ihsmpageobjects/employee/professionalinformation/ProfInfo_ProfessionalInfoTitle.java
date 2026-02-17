package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class ProfInfo_ProfessionalInfoTitle extends BasePage {

	public ProfInfo_ProfessionalInfoTitle(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//span[@data-bs-target=\"#ProfessionalInformationid\"]")
	private WebElement professionalInfoLink;

	@FindBy(xpath = "//a[@href='#tab27']")
	private WebElement titleTab;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//ng-select[@name=\"SelectedTitle\"]")
	private WebElement titleDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> titleDropdownOptions;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//label[contains(normalize-space(),'Academic Degree')]/following::span[contains(@class,'addvalue')])[2]")
	private WebElement addTitlePlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@type='text']")
	private WebElement titleInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveTitleButton;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//input[@name=\"organization\"])[2]")
	private WebElement organizationField;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//input[@name=\"organization\"])[3]")
	private WebElement documentNumberField;

	@FindBy(name = "dttDateTittle")
	private WebElement titleDateField;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//textarea[@name='notes'])[3]")
	private WebElement notesField;

	@FindBy(xpath = "//div[@Id='ProfessionalInformationid']//input[@type='file']")
	private WebElement uploadDocumentField;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//button[contains(@class, 'btnprimary') and text()='Save'])[3]")
	private WebElement saveTitleBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopupTitle;

	// methods to perform the action

	public void professionalInfoLink() {
		blinkElement(professionalInfoLink);
		safeClick(professionalInfoLink);
	}

	public void titleTab() {
		safeClick(titleTab);
	}

	public void titleDropdownField() {
		safeClick(titleDropdownField);
	}

	public void academicTypeDropdownOptions(String value) {
		// Open dropdown
		safeClick(titleDropdownField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : titleDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → add new Degree Type
		if (!found) {

			// Click +
			safeClick(addTitlePlusButton);

			// Enter Academics type
			safeClick(titleInputField);
			titleInputField.sendKeys(value);

			// Save
			safeClick(saveTitleButton);

			// Ok
			safeClick(okButtonSuccessPopup);

			// Reopen dropdown
			safeClick(titleDropdownField);

			// Select newly added value
			for (WebElement option : titleDropdownOptions) {
				if (option.getText().trim().equalsIgnoreCase(value)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Title value not found even after adding: " + value);
		}
	}

	public void organizationField(String organization) {
		organizationField.sendKeys(organization);
	}

	public void documentNumberField(String documentNumber) {
		documentNumberField.sendKeys(documentNumber);
	}

	public void titleDateField(String titleDate) {
		enterDate(titleDateField, titleDate);
	}

	public void notesField(String notes) {
		notesField.sendKeys(notes);
	}

	public void uploadDocumentField(String filePath) {
		uploadDocumentField.sendKeys(filePath);
	}

	public void saveTitleBtn() {
		blinkElement(saveTitleBtn);
		try {
			captureScreenshot("Title Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveTitleBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopupTitle() {
		blinkElement(okButtonSuccessPopupTitle);
		handleModalOk(okButtonSuccessPopupTitle);
	}
	
	public boolean isProfInfoTitleSavedSuccessfully() {
		return okButtonSuccessPopupTitle.isDisplayed();
	}

	// fill the Title form
	public void fillTitleForm(String title, String organization, String documentNumber, String titleDate, String notes,
			String filePath) {
		professionalInfoLink();
		titleTab();
		titleDropdownField();
		academicTypeDropdownOptions(title);
		organizationField(organization);
		documentNumberField(documentNumber);
		titleDateField(titleDate);
		notesField(notes);
		uploadDocumentField(filePath);
		saveTitleBtn();
		okButtonSuccessPopupTitle();
	}

}
