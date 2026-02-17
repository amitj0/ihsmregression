package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentMedicalAtPolyData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class MedicalAtPolyData {

	@DataProvider(name = "StudentMedicalAtPolyData")
	public Object[][] medicalAtPolyPojoData() {

		List<StudentMedicalAtPolyData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
				"StMedicalData2", StudentMedicalAtPolyData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
