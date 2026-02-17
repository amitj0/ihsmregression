package com.ihsm.university.ihsmpageobjects.employee.professionalinformation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;

public class ProfInfo_ProfessionalInfoAcademics extends BasePage {

	public ProfInfo_ProfessionalInfoAcademics(WebDriver driver) {
		super(driver);
	}

	// locate the web element here

	@FindBy(xpath = "//span[@data-bs-target=\"#ProfessionalInformationid\"]")
	private WebElement professionalInfoLink;

	@FindBy(xpath = "//a[@href='#tab26']")
	private WebElement academicsSubTab;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//ng-select[@name=\"type\"]")
	private WebElement academicTypeDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> academicTypeDropdownOptions;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//label[contains(normalize-space(),'Academic Degree')]/following::span[contains(@class,'addvalue')])[1]")
	private WebElement addAcademicDegreePlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//input[@type='text']")
	private WebElement academicDegreeInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal3']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveAcademicDegreeButton;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//ng-select[@name=\"SelectedDocumentType\"]")
	private WebElement documentTypeDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> documentTypeDropdownOptions;

	@FindBy(name = "dttDateEnterAcademicDegree")
	private WebElement dateOfEnteringAcademicDegreeField;

	@FindBy(name = "dttGraduateDateEnterAcademicDegree")
	private WebElement dateOfGraduationAcademicDegreeField;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//input[@name=\"organization\"])[1]")
	private WebElement qualificationField;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//input[@name=\"strOrganizationAcademicDegree\"]")
	private WebElement organizationField;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//input[@name=\"speciality\"]")
	private WebElement specialityField;

	@FindBy(xpath = "//div[@id='ProfessionalInformationid']//input[@name=\"strDocunmentAcademicDegree\"]")
	private WebElement certificateNumberField;

	@FindBy(name = "dttCertificateDateAcademicDegree")
	private WebElement certificateDateField;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//textarea[@name='notes'])[2]")
	private WebElement notesField;

	@FindBy(xpath = "(//div[@id='ProfessionalInformationid']//button[contains(@class, 'btnprimary') and text()='Save'])[2]")
	private WebElement saveAcademicInfoBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopupAcademicInfo;

	// methods to perform the action
	public void professionalInfoLink() {
		blinkElement(professionalInfoLink);
		safeClick(professionalInfoLink);
	}

	public void academicsSubTab() {
		safeClick(academicsSubTab);
	}

	public void academicTypeDropdownField() {
		safeClick(academicTypeDropdownField);
	}

	public void academicTypeDropdownOptions(String value) {
		// Open dropdown
		safeClick(academicTypeDropdownField);

		boolean found = false;

		// Try selecting existing value
		for (WebElement option : academicTypeDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → add new Degree Type
		if (!found) {

			// Click +
			safeClick(addAcademicDegreePlusButton);

			// Enter Academics type
			safeClick(academicDegreeInputField);
			academicDegreeInputField.sendKeys(value);

			// Save
			safeClick(saveAcademicDegreeButton);

			// Ok
			safeClick(okButtonSuccessPopup);

			// Reopen dropdown
			safeClick(academicTypeDropdownField);

			// Select newly added value
			for (WebElement option : academicTypeDropdownOptions) {
				if (option.getText().trim().equalsIgnoreCase(value)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Academics value not found even after adding: " + value);
		}
	}

	public void documentTypeDropdownField() {
		safeClick(documentTypeDropdownField);
	}

	public void documentTypeDropdownOptions(String documentType) {
		safeClick(documentTypeDropdownField);

		// Try selecting existing value
		for (WebElement option : documentTypeDropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(documentType)) {
				safeClick(option);
				return;
			}
		}
	}

	public void enterDateOfEnteringAcademicDegree(String dateOfEnteringAcademicDegree) {
		enterDate(dateOfEnteringAcademicDegreeField, dateOfEnteringAcademicDegree);
	}

	public void enterDateOfGraduationAcademicDegree(String dateOfGraduationAcademicDegree) {
		enterDate(dateOfGraduationAcademicDegreeField, dateOfGraduationAcademicDegree);
	}

	public void enterQualification(String qualification) {
		qualificationField.sendKeys(qualification);
	}

	public void enterOrganization(String organization) {
		organizationField.sendKeys(organization);
	}

	public void enterSpeciality(String speciality) {
		specialityField.sendKeys(speciality);
	}

	public void enterCertificateNumber(String certificateNumber) {
		certificateNumberField.sendKeys(certificateNumber);
	}

	public void enterCertificateDate(String certificateDate) {
		enterDate(certificateDateField, certificateDate);
	}

	public void enterNotes(String notes) {
		notesField.sendKeys(notes);
	}

	public void saveAcademicInfoBtn() {
		blinkElement(saveAcademicInfoBtn);
		try {
			captureScreenshot("Academics Information Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}
		safeClick(saveAcademicInfoBtn);
		handleAlertIfPresent();
	}

	public void okButtonSuccessPopupAcademicInfo() {
		blinkElement(okButtonSuccessPopupAcademicInfo);
		handleModalOk(okButtonSuccessPopupAcademicInfo);
	}
	
	public boolean isProfAcadInfoSavedSuccessfully() {
		return okButtonSuccessPopupAcademicInfo.isDisplayed();
	}

	// fill the academic info form
	public void fillAcademicInfoForm(String academicType, String docType, String dateOfEnteringAcademicDegree,
			String dateOfGraduationAcademicDegree, String qualification, String organization, String speciality,
			String certificateNumber, String certificateDate, String notes) {

		professionalInfoLink();
		academicsSubTab();
		academicTypeDropdownField();
		academicTypeDropdownOptions(academicType);
		documentTypeDropdownField();
		documentTypeDropdownOptions(docType);
		enterDateOfEnteringAcademicDegree(dateOfEnteringAcademicDegree);
		enterDateOfGraduationAcademicDegree(dateOfGraduationAcademicDegree);
		enterQualification(qualification);
		enterOrganization(organization);
		enterSpeciality(speciality);
		enterCertificateNumber(certificateNumber);
		enterCertificateDate(certificateDate);
		enterNotes(notes);
		saveAcademicInfoBtn();
		okButtonSuccessPopupAcademicInfo();
	}

}
