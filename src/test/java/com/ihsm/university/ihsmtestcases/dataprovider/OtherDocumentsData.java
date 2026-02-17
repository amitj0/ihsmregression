package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentOtherDocuments;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class OtherDocumentsData {

	@DataProvider(name = "StudentOtherDocuments")
	public Object[][] otherDocumentsPojoData() {
		List<StudentOtherDocuments> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "OtherDocuments",
				StudentOtherDocuments.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
