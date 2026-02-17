package com.ihsm.university.ihsmpageobjects.employee.basicinformation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihsm.university.base.BasePage;

public class BasicInfo_VaccinationInformation extends BasePage {

	public BasicInfo_VaccinationInformation(WebDriver driver) {
		super(driver);
	}

	// locate the web element here
	@FindBy(xpath = "//div[@id='divbtnVaccination']//span")
	private WebElement addVaccinationInfoBtn;

	@FindBy(xpath = "//div[@id='VaccinationModelID']//ng-select[@name='strVaccinationType']")
	private WebElement vaccinationTypeDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> vaccinationTypeOptions;

	private By dropdownOptions = By.xpath("//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']");

	@FindBy(xpath = "//div[@id='VaccinationModelID']//label[contains(normalize-space(),'Type')]/following::span[contains(@class,'addvalue')][1]")
	private WebElement addVaccinationTypePlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//input[@name='strMasterName']")
	private WebElement vaccinationTypeInputField;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveVaccinationTypeButton;

	// phase

	@FindBy(xpath = "//div[@id='VaccinationModelID']//ng-select[@name='intdose']")
	private WebElement vaccinationPhaseDropdownField;

	@FindBy(xpath = "//div[contains(@class,'ng-dropdown-panel')]//div[@role='option']")
	private List<WebElement> vaccinationPhaseOptions;

	@FindBy(xpath = "//div[@id='VaccinationModelID']//label[contains(normalize-space(),'Phase')]/following::span[contains(@class,'addvalue')][1]")
	private WebElement addVaccinationPhasePlusButton;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//input[@name='strMasterName']")
	private WebElement addVaccFieldInput;

	@FindBy(xpath = "//div[@id='AddMasterDataModal']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement addSaveBtn;

	@FindBy(xpath = "//div[@id='VaccinationModelID']//button[contains(@class, 'btnprimary') and text()='Save']")
	private WebElement saveButton;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButton;

	@FindBy(name = "strVaccinationCertificateNumber")
	private WebElement vaccinationCertificateNumberField;

	@FindBy(name = "dttVaccinationDate")
	private WebElement vaccinationDateField;

	@FindBy(xpath = "//div[@id='VaccinationModelID']//textarea[@id='exampleFormControlTextarea1']")
	private WebElement remarksField;

	@FindBy(xpath = "//div[@id='VaccinationModelID']//ancestor::div[contains(@class,'modal-content')]//button[normalize-space(text())='Save']")
	private WebElement saveVaccinationInfoBtn;

	@FindBy(xpath = "//div[@id='AlertSuccesModal' and contains(@class,'show')]//button[normalize-space()='Ok']")
	private WebElement okButtonSuccessPopup;

	private final By successModal = By.id("AlertSuccesModal");

	private final By successOkButton = By.xpath("//div[@id='AlertSuccesModal']//button[normalize-space()='Ok']");

	/*
	 * public void okButtonSuccessPopup() { WebDriverWait wait = new
	 * WebDriverWait(driver, Duration.ofSeconds(25));
	 * 
	 * try { // Try multiple strategies to find and click the OK button WebElement
	 * okBtn = wait.until(driver -> { // Check specific success modal OK button
	 * List<WebElement> okButtons = driver.findElements(successOkButton); if
	 * (!okButtons.isEmpty() && okButtons.get(0).isDisplayed()) {
	 * logger.info("Found OK button via successOkButton locator"); return
	 * okButtons.get(0); }
	 * 
	 * // Check any visible modal with OK button List<WebElement> anyOkBtn =
	 * driver.findElements(By.xpath(
	 * "//div[contains(@class,'modal') and contains(@class,'show')]//button[normalize-space()='Ok']"
	 * )); if (!anyOkBtn.isEmpty() && anyOkBtn.get(0).isDisplayed()) {
	 * logger.info("Found OK button via generic modal search"); return
	 * anyOkBtn.get(0); }
	 * 
	 * // Check if modal exists in DOM (even without show class) List<WebElement>
	 * modalOkBtn = driver .findElements(By.xpath(
	 * "//div[@id='AlertSuccesModal']//button[normalize-space()='Ok']")); if
	 * (!modalOkBtn.isEmpty() && modalOkBtn.get(0).isDisplayed()) {
	 * logger.info("Found OK button via modal ID (no show class required)"); return
	 * modalOkBtn.get(0); }
	 * 
	 * return null; });
	 * 
	 * blinkElement(okBtn); safeClick(okBtn);
	 * 
	 * // Wait briefly for modal to close try { Thread.sleep(500); } catch
	 * (InterruptedException e) { Thread.currentThread().interrupt(); }
	 * 
	 * logger.info("Success popup handled successfully");
	 * 
	 * } catch (TimeoutException e) {
	 * logger.error("Success modal did not appear within timeout", e);
	 * 
	 * try { captureScreenshot("Vaccination_Save_Failed");
	 * 
	 * // Log diagnostic information
	 * logger.info("Checking if modal exists in DOM..."); List<WebElement> modals =
	 * driver.findElements(successModal); logger.info("Modal elements found: " +
	 * modals.size());
	 * 
	 * if (!modals.isEmpty()) { WebElement modal = modals.get(0);
	 * logger.info("Modal display: " + modal.getCssValue("display"));
	 * logger.info("Modal visibility: " + modal.getCssValue("visibility"));
	 * logger.info("Modal class: " + modal.getAttribute("class")); }
	 * 
	 * } catch (Exception ex) { logger.error("Screenshot/diagnostic capture failed",
	 * ex); }
	 * 
	 * throw new
	 * RuntimeException("Success modal did not appear after vaccination save", e);
	 * 
	 * } catch (Exception e) {
	 * logger.error("Unexpected error handling success popup", e);
	 * 
	 * try { captureScreenshot("Success_Popup_Error"); } catch (Exception ex) {
	 * logger.error("Screenshot capture failed", ex); }
	 * 
	 * throw new RuntimeException("Error handling success modal", e); } }
	 */

	// methods to perform the action
	public void addVaccinationInfoBtn() {
		blinkElement(addVaccinationInfoBtn);
		safeClick(addVaccinationInfoBtn);
	}

	public void vaccinationTypeDropdownField() {
		safeClick(vaccinationTypeDropdownField);
	}

	public void selectVaccinationTypeOption(String vaccinationType) {

		// Open dropdown
		safeClick(vaccinationTypeDropdownField);

		boolean found = false;
		List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions));
		// Try selecting existing value
		for (WebElement option : options) {
			if (option.getText().trim().equalsIgnoreCase(vaccinationType)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → Add new value
		if (!found) {

			// Click +
			safeClick(addVaccinationTypePlusButton);

			// Enter value
			vaccinationTypeInputField.clear();
			vaccinationTypeInputField.sendKeys(vaccinationType);

			// Save
			safeClick(saveVaccinationTypeButton);

			// OK
			safeClick(okButton);

			// Reopen dropdown
			safeClick(vaccinationTypeDropdownField);

			List<WebElement> options2 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions));
			// Select newly added value
			for (WebElement option : options2) {
				if (option.getText().trim().equalsIgnoreCase(vaccinationType)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Vaccination Type not found even after adding: " + vaccinationType);
		}
	}

	public void vaccinationPhaseDropdownField() {
		safeClick(vaccinationPhaseDropdownField);
	}

	public void selectVaccinationPhaseOption(String vaccinationPhase) {

		// Open dropdown
		safeClick(vaccinationPhaseDropdownField);

		boolean found = false;
		List<WebElement> option3 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions));
		// Try selecting existing value
		for (WebElement option : option3) {
			if (option.getText().trim().equalsIgnoreCase(vaccinationPhase)) {
				safeClick(option);
				found = true;
				break;
			}
		}

		// If not found → Add new value
		if (!found) {

			// Click +
			safeClick(addVaccinationPhasePlusButton);

			// Enter value
			addVaccFieldInput.clear();
			addVaccFieldInput.sendKeys(vaccinationPhase);

			// Save
			safeClick(addSaveBtn);

			// OK
			safeClick(okButton);

			// Reopen dropdown
			safeClick(vaccinationPhaseDropdownField);

			List<WebElement> options4 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions));
			// Select newly added value
			for (WebElement option : options4) {
				if (option.getText().trim().equalsIgnoreCase(vaccinationPhase)) {
					safeClick(option);
					return;
				}
			}

			throw new RuntimeException("Vaccination Phase not found even after adding: " + vaccinationPhase);
		}
	}

	public void fillVaccinationCertificateNumber(String certificateNumber) {
		vaccinationCertificateNumberField.sendKeys(certificateNumber);
	}

	public void fillVaccinationDate(String vaccinationDate) {
		enterDate(vaccinationDateField, vaccinationDate);
	}

	public void fillRemarks(String remarks) {
		remarksField.sendKeys(remarks);
	}

	public void saveVaccinationInfoBtn() {
		blinkElement(saveVaccinationInfoBtn);
		try {
			captureScreenshot("Vaccination_InfoFormation_Filled");
		} catch (Exception e) {
			e.printStackTrace();
		}

		safeClick(saveVaccinationInfoBtn);

		// Add delay for backend processing in headless mode
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

//		handleSubmissionConfirmation();
		handleAlertIfPresent();
	}
	public void okButtonSuccessPopup() {
		blinkElement(okButtonSuccessPopup);
		safeClick(okButtonSuccessPopup);
	}

	public boolean isVaccInfoSavedSuccessfully() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			return wait.until(ExpectedConditions.visibilityOf(okButtonSuccessPopup)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// fill the vaccination form
	public BasicInfo_BiometricsInformation fillVaccinationForm(String vaccinationType, String vaccinationPhase,
			String certificateNumber, String vaccinationDate, String remarks) {
		addVaccinationInfoBtn();
		selectVaccinationTypeOption(vaccinationType);
		selectVaccinationPhaseOption(vaccinationPhase);
		fillVaccinationCertificateNumber(certificateNumber);
		fillVaccinationDate(vaccinationDate);
		fillRemarks(remarks);
		saveVaccinationInfoBtn();
		okButtonSuccessPopup();

		return new BasicInfo_BiometricsInformation(driver);
	}

}