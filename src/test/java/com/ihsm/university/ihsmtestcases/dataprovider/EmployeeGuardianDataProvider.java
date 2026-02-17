package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;
import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.EmployeeGuardianInfoData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EmployeeGuardianDataProvider {

	@DataProvider(name = "GuardianInfoData")
	public Object[][] guardianInformationPojoData() {

		List<EmployeeGuardianInfoData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "sheet name",
				EmployeeGuardianInfoData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
