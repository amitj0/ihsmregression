package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.EmployeeEnrollnmentInfoData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EmployeeEnrollnmentDataProvider {

    @DataProvider(name = "EnrollmentInfoData")
    public Object[][] enrollmentInformationPojoData() {

        List<EmployeeEnrollnmentInfoData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "sheet name",
                EmployeeEnrollnmentInfoData.class);

        return list.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }
}
