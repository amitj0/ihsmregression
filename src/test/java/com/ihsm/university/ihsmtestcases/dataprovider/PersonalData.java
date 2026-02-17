package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentPersonalData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class PersonalData {

	@DataProvider(name = "StudentPersonalData")
	public Object[][] personalInformationPojoData() {

		List<StudentPersonalData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "StudentPersonalData",
				StudentPersonalData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
