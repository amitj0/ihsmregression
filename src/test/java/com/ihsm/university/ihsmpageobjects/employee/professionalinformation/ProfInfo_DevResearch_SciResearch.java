package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class ProfInfo_DevResearch_SciResearch extends BasePage {

	public ProfInfo_DevResearch_SciResearch(WebDriver driver) {
		super(driver);
	}

	// locate the web elements here
	@FindBy(xpath = "//div[@id='BTNDIVDEVELOPMENT']//span")
	private WebElement devResearchAddBtn;

	@FindBy(xpath = "//a[@href='#tab125']")
	private WebElement sciResearchSubTab;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//ng-select[@name='SelectBookId']")
	private WebElement typeField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> typeOptions;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Type')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addTypePlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@type='text']")
	private WebElement addValueInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveTypeBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	@FindBy(name = "strPublicationPrint")
	private WebElement publicationPrintDateField;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//ng-select[@name='selectedPubLevel']")
	private WebElement publicationLevelDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> publicationLevelDropdownOptions;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Type')]/following::span[contains(@class,'addvalue')])[2]")
	private WebElement addPublicationLevelPlusBtn;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@type='text']")
	private WebElement publicationLevelInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement savePublicationLevelBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup2;

	@FindBy(name = "strPublicationUrl")
	private WebElement publicationURLField;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Name Of Magazine')]//following-sibling::div//input[@name='FULLNAME']")
	private WebElement nameOfMagazineField;
	
	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Name Of Magzine')]//following-sibling::div//input[@name='FULLNAME']")
	private WebElement nameOfMagazineField2;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Name Of Article')]//following-sibling::div//input[@name='FULLNAME']")
	private WebElement nameOfArticleField;

	@FindBy(xpath = "//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Authors')]//following-sibling::div//input[@name='FULLNAME']")
	private WebElement authorsField;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//label[contains(normalize-space(),'Notes')]//following-sibling::div//textarea[@id='exampleFormControlTextarea1'])[1]")
	private WebElement notesField;

	@FindBy(xpath = "(//div[@id='DevelopmentResearchID']//button[contains(@class, 'btnprimary') and text()='Save'])[1]")
	private WebElement saveDevResearchBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonDevResearch;

	// methods to perform the action
	public void devResearchAddBtn() {
		blinkElement(devResearchAddBtn);
		safeClick(devResearchAddBtn);
	}

	public void clickSciResearchSubTab() {
		safeClick(sciResearchSubTab);
	}

	public void selectTypeField() {
		safeClick(typeField);
	}

	public void typeOptions(String type) {
		safeClick(typeField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : typeOptions) {
			if (option.getText().trim().equalsIgnoreCase(type)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		if (!found) {
			// Add new value
			safeClick(addTypePlusBtn);

			safeClick(addValueInputField);
			addValueInputField.sendKeys(type);

			safeClick(saveTypeBtn);
			safeClick(okButtonSuccessPopup);
			// Select the newly added value
			safeClick(typeField);
			for (WebElement option : typeOptions) {
				if (option.getText().trim().equalsIgnoreCase(type)) {
					safeClick(option);
					return;
				}
			}
		}
	}

	public void publicationPrintFieldDate(String publicationPrint) {
		enterDate(publicationPrintDateField, publicationPrint);
	}

	public void publicationLevelDropdownField() {
		safeClick(publicationLevelDropdownField);
	}

	public void publicationLevelDropdownOptions(String pubLevel) {
		safeClick(publicationLevelDropdownField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : publicationLevelDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(pubLevel)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		if (!found) {
			// Add new value
			safeClick(addPublicationLevelPlusBtn);
			safeClick(publicationLevelInputField);
			publicationLevelInputField.sendKeys(pubLevel);
			safeClick(savePublicationLevelBtn);
			safeClick(okButtonSuccessPopup2);
			// Select the newly added value
			safeClick(publicationLevelDropdownField);
			for (WebElement option : publicationLevelDropdownOptions) {
				if (option.getText().trim().equalsIgnoreCase(pubLevel)) {
					safeClick(option);
					return;
				}
			}
		}
	}

	public void publicationURLField(String publicationURL) {
		publicationURLField.sendKeys(publicationURL);
	}

	public void nameOfMagazineField(String nameOfMagazine) {
		nameOfMagazineField.sendKeys(nameOfMagazine);
	}

	public void nameOfArticleField(String nameOfArticle) {
		nameOfArticleField.sendKeys(nameOfArticle);
	}

	public void authorsField(String authors) {
		authorsField.sendKeys(authors);
	}

	public void notesField(String notes) {
		notesField.sendKeys(notes);
	}

	public void saveDevResearchBtn() {
		blinkElement(saveDevResearchBtn);
		try {
			captureScreenshot("Sci Research Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveDevResearchBtn);
		handleAlertIfPresent();
	}

	public void okButtonDevResearch() {
		blinkElement(okButtonDevResearch);
		handleModalOk(okButtonDevResearch);
	}

	public boolean isDevReSciResearchInfoSavedSuccessfully() {
		return okButtonDevResearch.isDisplayed();
	}

	// fillDevResearchForm method to fill the form in one go
	public void fillDevResearchForm(String type, String publicationPrintDate, String pubLevel, String publicationURL,
			String nameOfMagazine, String nameOfArticle, String authors, String notes) {
		devResearchAddBtn();
		clickSciResearchSubTab();
		selectTypeField();
		typeOptions(type);
		publicationPrintFieldDate(publicationPrintDate);
		publicationLevelDropdownField();
		publicationLevelDropdownOptions(pubLevel);
		publicationURLField(publicationURL);
		nameOfMagazineField(nameOfMagazine);
		nameOfArticleField(nameOfArticle);
		authorsField(authors);
		notesField(notes);
		saveDevResearchBtn();
		okButtonDevResearch();

		// return instance of this class
	}

}
