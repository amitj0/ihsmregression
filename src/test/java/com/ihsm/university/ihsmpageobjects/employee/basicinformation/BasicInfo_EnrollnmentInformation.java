package com.ihsm.university.ihsmpageobjects.employee.basicinformation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ihsm.university.base.BasePage;

public class BasicInfo_EnrollnmentInformation extends BasePage {

	public BasicInfo_EnrollnmentInformation(WebDriver driver) {
		super(driver);
	}

	// locate the web element
	@FindBy(xpath = "//span[normalize-space()='Employee']")
	private WebElement employeeTab;

	@FindBy(xpath = "//a[@href='#/Employee/NewEnrollEmployee']")
	private WebElement addEmpTab;;

	@FindBy(xpath = "(//span[@class='adddetailbtn'])[1]")
	private WebElement addEnrollnmentInfoBtn;

	@FindBy(name = "FIRSTNAME")
	private WebElement firstNameField;

	@FindBy(name = "SURNAME")
	private WebElement nameInEnglishField;

	@FindBy(xpath = "//div[@id='divEmployeeRegistration']//ng-select[@name='GENDER']")
	private WebElement genderDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> genderDropdownOptions;

	@FindBy(xpath = "//div[@id='divEmployeeRegistration']//input[@name='IIN']")
	private WebElement iinNumberField;

	@FindBy(xpath = "//div[@id='divEmployeeRegistration']//input[@name='EMAIL']")
	private WebElement emailField;

	@FindBy(xpath = "//div[@id='divEmployeeRegistration']//ng-select[@name='strCitizenShip']")
	private WebElement citizenshipDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> citizenshipDropdownOptions;

	@FindBy(xpath = "(//div[@id='divPersonalInfoMaodel']//span[@data-bs-target='#addvalue'])[2]")
	private WebElement addCitizenshipPlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//input[@name='strMasterName']")
	private WebElement citizenshipInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveCitizenshipButton;

	@FindBy(xpath = "//div[@id='divEmployeeRegistration']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveButtonEnrollnmentInfo;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement saveOkButtonEnrollnmentInfo;

	@FindBy(xpath = "//div[@class='studentid']")
	private WebElement enrollmentDisplayedUI;
	
	@FindBy(xpath = "//div[@class='studentid']")
	private WebElement employeeIdElement;


	public String getEmployeeId() throws InterruptedException {

	    Thread.sleep(3000);

	    String employeeId = employeeIdElement.getText().trim();

	    System.out.println("Captured Employee ID = " + employeeId);

	    return employeeId;
	}



	// methods to access the web elements
	public void employeeTab() {
		blinkElement(employeeTab);
		safeClick(employeeTab);
	}

	public void clickEnrollnmentInformationTab() {
		blinkElement(addEmpTab);
		safeClick(addEmpTab);
	}

	public void clickAddEnrollnmentInfoBtn() {
		blinkElement(addEnrollnmentInfoBtn);
		safeClick(addEnrollnmentInfoBtn);
	}

	public void enterFirstName(String firstName) {
		safeClick(firstNameField);
		firstNameField.sendKeys(firstName);
	}

	public void enterNameInEnglish(String nameInEnglish) {
		nameInEnglishField.sendKeys(nameInEnglish);
	}

	public void genderDropdownField() {
		safeClick(genderDropdownField);
	}

	public void selectGenderOption(String gender) {
		safeClick(genderDropdownField);
		for (WebElement option : genderDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(gender)) {
				safeClick(option);
				return;
			}
		}

	}

	public void enterIINNumber(String iinNumber) {
		iinNumberField.sendKeys(iinNumber);
	}

	public void enterEmail(String email) {
		emailField.sendKeys(email);
	}

	public void citizenshipDropdownField() {
		safeClick(citizenshipDropdownField);
	}

	public void selectCitizenshipOption(String citizenship) {
		selectNgDropdownValue(citizenshipDropdownField, // dropdown element
				citizenship, // value to select
				addCitizenshipPlusButton, // "+" button
				citizenshipInputField, // input field for new value
				saveCitizenshipButton, // save button
				saveOkButtonEnrollnmentInfo // OK button on modal
		);
	}

	public void clickSaveButtonEnrollnmentInfo() {
		blinkElement(saveButtonEnrollnmentInfo);
		try {
			captureScreenshot("Enrollnment InfoFormation Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveButtonEnrollnmentInfo);
		handleAlertIfPresent();
	}

	public void clickSaveOkButtonEnrollnmentInfo() {
		blinkElement(saveOkButtonEnrollnmentInfo);
		handleModalOk(saveOkButtonEnrollnmentInfo);
	}

	public boolean isEnrollnmentSavedSuccessfully() {
		return saveOkButtonEnrollnmentInfo.isDisplayed();
	}

	// fill the enrollnment information form
	public BasicInfo_PersonalInformation fillEnrollnmentInformationForm(String firstName, String nameInEnglish,
			String gender, String iinNumber, String email, String citizenship) {
		employeeTab();
		clickEnrollnmentInformationTab();
		clickAddEnrollnmentInfoBtn();
		enterFirstName(firstName);
		enterNameInEnglish(nameInEnglish);
		genderDropdownField();
		selectGenderOption(gender);
		enterIINNumber(iinNumber);
		enterEmail(email);
		citizenshipDropdownField();
		selectCitizenshipOption(citizenship);
		clickSaveButtonEnrollnmentInfo();
		return new BasicInfo_PersonalInformation(driver);

	}

}
