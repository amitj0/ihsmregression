package com.ihsm.university.utilities;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.ExamManage;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class ExamManageDataProvider {

	private static final String FILE_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/student_registration.xlsx";

	@DataProvider(name = "ExamManageData")
	public static Object[][] getExamManageData() {

		List<ExamManage> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "ExamManageData", ExamManage.class);
		System.out.println("Total rows fetched: " + list.size());

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
