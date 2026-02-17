package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.StudentVisaRegisterData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class VisaRegisterData {

    @DataProvider(name = "StudentVisaRegisterData")
    public Object[][] visaRegisterPojoData() {
        List<StudentVisaRegisterData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "Registered",
                StudentVisaRegisterData.class);

        return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
    }
}
