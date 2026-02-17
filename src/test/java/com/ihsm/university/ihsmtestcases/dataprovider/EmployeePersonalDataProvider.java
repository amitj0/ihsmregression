package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.EmployeePersonalInfoData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EmployeePersonalDataProvider {

    @DataProvider(name = "PersonalInfoData")
    public Object[][] personalInformationPojoData() {

        List<EmployeePersonalInfoData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "sheet name",
                EmployeePersonalInfoData.class);

        return list.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }
}
