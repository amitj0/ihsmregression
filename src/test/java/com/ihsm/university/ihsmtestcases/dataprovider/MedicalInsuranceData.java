package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentMedicalInsuranceData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class MedicalInsuranceData {

	@DataProvider(name = "StudentMedicalInsuranceData")
	public Object[][] medicalInsurancePojoData() {

		List<StudentMedicalInsuranceData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
				"StMedicalData3", StudentMedicalInsuranceData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
