package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.ClassSchedulingData;
import com.ihsm.university.ihsmtestcases.pojo.ClassSchedulingData2;
import com.ihsm.university.ihsmtestcases.pojo.ClassSchedulingData3;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class ClassSchedulingDataProvider {

	@DataProvider(name = "ClassScheduling")
	public Object[][] getClassSchedulingData() {

		List<ClassSchedulingData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "ClassSchedule",
				ClassSchedulingData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	@DataProvider(name = "ClassScheduling2")
	public Object[][] getClassSchedulingData2() {

		List<ClassSchedulingData2> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "ClassSchedule2",
				ClassSchedulingData2.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	@DataProvider(name = "ClassScheduling3")
	public Object[][] getClassSchedulingData3() {

		List<ClassSchedulingData3> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx", "ClassSchedule3",
				ClassSchedulingData3.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}