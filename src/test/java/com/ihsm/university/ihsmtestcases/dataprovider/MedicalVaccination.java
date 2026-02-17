package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentMedicalVaccinationData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class MedicalVaccination {

	@DataProvider(name = "StudentMedicalVaccinationData")
	public Object[][] medicalVaccinationData() {

		List<StudentMedicalVaccinationData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
				"StMedicalData1", StudentMedicalVaccinationData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

}
