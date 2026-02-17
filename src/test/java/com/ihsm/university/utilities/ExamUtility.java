package com.ihsm.university.utilities;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

public class ExamUtility {

	public static Object[][] getTestData(String filePath, String sheetName) {
		Object[][] data = null;

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			int rows = sheet.getPhysicalNumberOfRows();
			int cols = sheet.getRow(0).getPhysicalNumberOfCells();

			data = new Object[rows - 1][cols];

			DataFormatter formatter = new DataFormatter(); // ⭐ KEY LINE

			for (int i = 1; i < rows; i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < cols; j++) {
					Cell cell = row.getCell(j);
					data[i - 1][j] = formatter.formatCellValue(cell).trim();
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to read Excel data", e);
		}

		return data;
	}

}
