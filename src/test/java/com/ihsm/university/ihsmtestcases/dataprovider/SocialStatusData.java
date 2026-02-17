package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentSocialStatusData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class SocialStatusData {

	@DataProvider(name = "StudentSocialStatusData")
	public Object[][] socialStatusInformationPojoData() {

		List<StudentSocialStatusData> list = ExcelToPojoUtils.getDataAsPojo(
				System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
				"StGeneralData2", StudentSocialStatusData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
