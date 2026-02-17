package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentVisaOfflineData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class VisaOfflineData {

    @DataProvider(name = "StudentVisaOfflineData")
    public Object[][] visaOfflinePojoData() {
        List<StudentVisaOfflineData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "VisaOffline",
                StudentVisaOfflineData.class);

        return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
    }
}
