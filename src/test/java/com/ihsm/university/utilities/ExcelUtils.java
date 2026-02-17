package com.ihsm.university.utilities;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

    private static Workbook workbook;

    public static Object[][] getSheetData(String filePath, String sheetName) {

        try (FileInputStream fis = new FileInputStream(filePath)) {

            workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();

            Object[][] data = new Object[rows][1];

            for (int i = 1; i <= rows; i++) {
                Map<String, String> rowData = new HashMap<>();
                Row headerRow = sheet.getRow(0);
                Row dataRow = sheet.getRow(i);

                for (int j = 0; j < cols; j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = dataRow.getCell(j) == null ? "" :
                                   dataRow.getCell(j).toString();
                    rowData.put(key, value);
                }
                data[i - 1][0] = rowData;
            }
            return data;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel", e);
        }
    }
}
