package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.EmployeeDocumentInfoData;
import com.ihsm.university.ihsmtestcases.pojo.EmployeePassportInfoData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EmployeeDocumentDataProvider {

	@DataProvider(name = "DocumentInfoData")
	public Object[][] getDocumentInfoData() {

		List<EmployeeDocumentInfoData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "sheet name",
				EmployeeDocumentInfoData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	@DataProvider(name = "PassportInfoData")
	public Object[][] getPassportInfoData() {

		List<EmployeePassportInfoData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "sheet Name",
				EmployeePassportInfoData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
