package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentVisaOnlineData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class VisaOnlineData {

    @DataProvider(name = "StudentVisaOnlineData")
    public Object[][] visaOnlinePojoData() {
        List<StudentVisaOnlineData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "VisaOnline",
                StudentVisaOnlineData.class);

        return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
    }
}
