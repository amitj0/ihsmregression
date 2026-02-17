package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.FacultyShowData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class FacultyShowDataProvider {

	@DataProvider(name = "FacultyShowDataProvider")
	public Object[][] getFacultyShowData() {
		List<FacultyShowData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "FacultyGroupDataShow",
				FacultyShowData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
