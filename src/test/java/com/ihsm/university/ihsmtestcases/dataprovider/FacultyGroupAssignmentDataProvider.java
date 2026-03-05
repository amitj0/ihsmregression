package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.FacultyGroupAssignData;
import com.ihsm.university.ihsmtestcases.pojo.FacultyGroupAssignData2;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class FacultyGroupAssignmentDataProvider {

	@DataProvider(name = "FacultyGroupAssignData")
	public Object[][] getGroupAssignmentData() {
		List<FacultyGroupAssignData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "GroupAssignmentData",
				FacultyGroupAssignData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
	@DataProvider(name = "FacultyGroupAssignData2")
	public Object[][] getGroupAssignmentData2() {
		List<FacultyGroupAssignData2> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "GroupAssignmentData2",
				FacultyGroupAssignData2.class);
		
		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
