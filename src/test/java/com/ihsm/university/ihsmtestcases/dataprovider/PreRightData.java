package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentPreRightData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class PreRightData {

	@DataProvider(name = "StudentPreRightData")
	public Object[][] preRightsInformationPojoData() {

		List<StudentPreRightData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
				"StGeneralData1", StudentPreRightData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
