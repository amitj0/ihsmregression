package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;
import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.EmployeeLanguageInfoData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EmployeeLanguageDataProvider {

    @DataProvider(name = "LanguageInfoData")
    public Object[][] languageInformationPojoData() {

        List<EmployeeLanguageInfoData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "sheet name",
                EmployeeLanguageInfoData.class);

        return list.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }
}
