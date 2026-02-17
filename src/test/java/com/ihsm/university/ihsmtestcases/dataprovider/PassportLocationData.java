package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentPassportLocationData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class PassportLocationData {

    @DataProvider(name = "StudentPassportLocationData")
    public Object[][] passportLocationPojoData() {
        List<StudentPassportLocationData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "PassportLocation1",
                StudentPassportLocationData.class);

        return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
    }
}
