package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentAcademicsLastEducation;
import com.ihsm.university.ihsmtestcases.pojo.StudentAcademicsQualification;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class LastEducationData {

	@DataProvider(name = "StudentAcademicsLastEducation")
	public Object[][] lastEducationPojoData() {
		List<StudentAcademicsLastEducation> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "LastEducation",
				StudentAcademicsLastEducation.class);
		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

}
