package com.ihsm.university.utilities;

import java.io.*;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtility {

	private String path;

	public ExcelUtility(String path) {
		this.path = path;
	}

	// ---------------- READ CELL DATA ----------------
	public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
		try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				return "";

			Row row = sheet.getRow(rownum);
			if (row == null)
				return "";

			Cell cell = row.getCell(colnum);
			if (cell == null)
				return "";

			DataFormatter formatter = new DataFormatter();
			return formatter.formatCellValue(cell);
		}
	}

	// ---------------- GET ROW COUNT ----------------
	public int getRowCount(String sheetName) throws IOException {
		try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				return 0;
			return sheet.getLastRowNum();
		}
	}

	// ---------------- WRITE CELL DATA WITH MULTIPLE RULES ----------------
	public void setCellDataWithRules(String sheetName, int rownum, int colnum, String value) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			try (XSSFWorkbook newWorkbook = new XSSFWorkbook(); FileOutputStream fo = new FileOutputStream(path)) {
				newWorkbook.createSheet(sheetName);
				newWorkbook.write(fo);
			}
		}

		try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				sheet = workbook.createSheet(sheetName);

			Row row = sheet.getRow(rownum);
			if (row == null)
				row = sheet.createRow(rownum);

			Cell cell = row.getCell(colnum);
			if (cell == null)
				cell = row.createCell(colnum);

			// Set the value
			cell.setCellValue(value);

			// Create a new style based on existing style
			CellStyle style = workbook.createCellStyle();
			style.cloneStyleFrom(cell.getCellStyle());

			// ---------- Multiple rules ----------
			if ("PASS".equalsIgnoreCase(value)) {
				style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			} else if ("FAIL".equalsIgnoreCase(value)) {
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
			} else if (value.matches("\\d+")) { // numeric threshold example
				int num = Integer.parseInt(value);
				if (num >= 75) {
					style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
				} else if (num >= 50) {
					style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				} else {
					style.setFillForegroundColor(IndexedColors.RED.getIndex());
				}
			} else {
				style.setFillForegroundColor(IndexedColors.WHITE.getIndex()); // default
			}

			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);

			// Write back to file
			try (FileOutputStream fo = new FileOutputStream(path)) {
				workbook.write(fo);
			}
		}
	}

	// ---------------- AUTO FIT COLUMNS ----------------
	public void autoFitColumns(String sheetName, int totalColumns) throws IOException {
		try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				return;

			for (int i = 0; i < totalColumns; i++) {
				sheet.autoSizeColumn(i);
			}

			try (FileOutputStream fo = new FileOutputStream(path)) {
				workbook.write(fo);
			}
		}
	}

	// ---------------- FORMAT HEADER ROW ----------------
	public void formatHeaderRow(String sheetName, int totalColumns) throws IOException {
		try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				return;

			Row headerRow = sheet.getRow(0);
			if (headerRow == null)
				return;

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			for (int i = 0; i < totalColumns; i++) {
				Cell cell = headerRow.getCell(i);
				if (cell != null) {
					cell.setCellStyle(style);
				}
			}

			// Freeze header row
			sheet.createFreezePane(0, 1);

			try (FileOutputStream fo = new FileOutputStream(path)) {
				workbook.write(fo);
			}
		}
	}

	// ---------------- BULK READ ----------------
	public Object[][] getSheetData(String sheetName) throws IOException {
		try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				return new Object[0][0];

			int rows = sheet.getLastRowNum();
			int cols = sheet.getRow(0).getLastCellNum();

			Object[][] data = new Object[rows][cols];

			for (int i = 1; i <= rows; i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < cols; j++) {
					Cell cell = (row != null) ? row.getCell(j) : null;
					if (cell == null) {
						data[i - 1][j] = "";
					} else {
						switch (cell.getCellType()) {
						case STRING -> data[i - 1][j] = cell.getStringCellValue();
						case NUMERIC -> {
							if (DateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
								data[i - 1][j] = sdf.format(date);
							} else {
								data[i - 1][j] = String.valueOf((long) cell.getNumericCellValue());
							}
						}
						case BOOLEAN -> data[i - 1][j] = cell.getBooleanCellValue();
						case FORMULA -> {
							FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
							CellValue cellValue = evaluator.evaluate(cell);
							switch (cellValue.getCellType()) {
							case STRING -> data[i - 1][j] = cellValue.getStringValue();
							case NUMERIC -> data[i - 1][j] = String.valueOf((long) cellValue.getNumberValue());
							case BOOLEAN -> data[i - 1][j] = cellValue.getBooleanValue();
							default -> data[i - 1][j] = "";
							}
						}
						default -> data[i - 1][j] = "";
						}
					}
				}
			}
			return data;
		}
	}

}
