package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentLanguageData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class LanguageData {

	@DataProvider(name = "StudentLanguageData")
	public Object[][] languageInformationPojoData() {

		List<StudentLanguageData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "StudentLanguageData",
				StudentLanguageData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
