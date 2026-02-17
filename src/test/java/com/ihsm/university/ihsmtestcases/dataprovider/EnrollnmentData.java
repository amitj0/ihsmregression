package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentEnrollnmentData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EnrollnmentData {

	@DataProvider(name = "StudentEnrollnmentData")
	public Object[][] enrollmentPojoData() {

		List<StudentEnrollnmentData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "StudentEnrollnmentData",
				StudentEnrollnmentData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

}
