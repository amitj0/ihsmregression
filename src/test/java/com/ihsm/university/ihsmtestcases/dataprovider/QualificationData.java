package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentAcademicsDiplomaData;
import com.ihsm.university.ihsmtestcases.pojo.StudentAcademicsQualification;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class QualificationData {

	@DataProvider(name = "StudentAcademicsQualification")
	public Object[][] qualificationPojoData() {
		List<StudentAcademicsQualification> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "Qualification",
				StudentAcademicsQualification.class);
		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

}
