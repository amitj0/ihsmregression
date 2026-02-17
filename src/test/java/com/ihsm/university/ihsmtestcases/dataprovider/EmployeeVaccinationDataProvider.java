package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;
import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.EmployeeVaccinationInfoData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EmployeeVaccinationDataProvider {

    @DataProvider(name = "VaccinationInfoData")
    public Object[][] vaccinationInformationPojoData() {

        List<EmployeeVaccinationInfoData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "sheet name",
                EmployeeVaccinationInfoData.class);

        return list.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }
}
