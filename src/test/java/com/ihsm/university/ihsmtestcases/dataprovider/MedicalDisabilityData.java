package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentMedicalDisabilityData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class MedicalDisabilityData {

	@DataProvider(name = "StudentMedicalDisabilityData")
	public Object[][] medicalDisabilityPojoData() {

		List<StudentMedicalDisabilityData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
				"StMedicalData4", StudentMedicalDisabilityData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
