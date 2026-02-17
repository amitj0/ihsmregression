package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;
import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.EmployeePositionData;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class EmployeePositionDataProvider {

    @DataProvider(name = "PositionInformationData")
    public Object[][] positionInformationPojoData() {

        List<EmployeePositionData> list = ExcelToPojoUtils.getDataAsPojo(
                System.getProperty("user.dir") + "/src/test/resources/student_registration.xlsx",
                "sheet name",
                EmployeePositionData.class);

        return list.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }
}
