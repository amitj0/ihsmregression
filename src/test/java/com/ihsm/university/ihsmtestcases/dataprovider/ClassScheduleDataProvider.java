package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.SubjectCreditData;
import com.ihsm.university.ihsmtestcases.pojo.SubjectHoursData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class ClassScheduleDataProvider {

	private static final String FILE_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/student_registration.xlsx";

	@DataProvider(name = "SubjectCreditData")
	public static Object[][] getSubjectCreditData() {
		List<SubjectCreditData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "subjectCreditData",
				SubjectCreditData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	@DataProvider(name = "SubjectHoursData")
	public static Object[][] getSubjectHoursData() {
		List<SubjectHoursData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "subjectHoursData",
				SubjectHoursData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
