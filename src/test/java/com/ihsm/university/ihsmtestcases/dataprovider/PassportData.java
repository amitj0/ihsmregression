package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentPassportData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class PassportData {

	@DataProvider(name = "StudentPassportData")
	public Object[][] passportPojoData() {
		List<StudentPassportData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "PassportLocation",
				StudentPassportData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
