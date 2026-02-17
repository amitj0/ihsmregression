package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentFamilyData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class FamilyData {

	@DataProvider(name = "StudentFamilyData")
	public Object[][] familyInformationPojoData() {

		List<StudentFamilyData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
				"StudentFamilyData", StudentFamilyData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
