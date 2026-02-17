package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentIdentificationData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class IdentificationCardData {

	@DataProvider(name = "StudentIdentificationCardData")
	public Object[][] identificationCardPojoData() {
		List<StudentIdentificationData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
				"IdentificationCard", StudentIdentificationData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
