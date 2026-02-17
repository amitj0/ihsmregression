package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;
import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.EmployeeRightsData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EmployeeRightsDataProvider {

    @DataProvider(name = "EmploymentRightsData")
    public Object[][] employmentRightsPojoData() {

        List<EmployeeRightsData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "sheet name",
                EmployeeRightsData.class);

        return list.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }
}
