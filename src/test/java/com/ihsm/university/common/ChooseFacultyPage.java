package com.ihsm.university.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ihsm.university.base.BasePage;
import com.ihsm.university.common.ChooseDepartmentPage;

public class ChooseFacultyPage extends BasePage {

	public ChooseFacultyPage(WebDriver driver) {
		super(driver);
	}

	// locate the web element
	@FindBy(xpath = "//div[@id='divSelectEmployeeRights']//div[@class='departmentbox']")
	private WebElement departmentFaculty;

	// methods to perform the action

	public void degreeFacultyHere() {
		blinkElement(departmentFaculty);
		safeClick(departmentFaculty);

	}

	// Department position
	public ChooseDepartmentPage degreeFaculty() {
		degreeFacultyHere();

		return new ChooseDepartmentPage(driver);
	}
}
