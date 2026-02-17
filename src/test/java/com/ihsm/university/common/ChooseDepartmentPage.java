package com.ihsm.university.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;
import com.ihsm.university.ihsmpageobjects.student.basicinformation.BasicInfo_EnrollnmentInformation;

public class ChooseDepartmentPage extends BasePage {

	public ChooseDepartmentPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[contains(concat(' ', normalize-space(@class), ' '), ' departmentname ') and contains(normalize-space(.), 'Super Admin')]")
	private WebElement superAdmin;

	@FindBy(xpath = "//small[text()='Super Admin']")
	private WebElement superAdminLabel;

	// methods to perform the action
	public void departAdmin() {
		blinkElement(superAdmin);
		safeClick(superAdmin);
	}

	// Department position
	public BasicInfo_EnrollnmentInformation chooseDepartPosition() {
		departAdmin();

		return new BasicInfo_EnrollnmentInformation(driver);
	}

}
