package com.ihsm.university.ihsmtestcases.flows.employee;

import org.testng.annotations.DataProvider;
import com.ihsm.university.utilities.ExcelUtility;

public class EmployeeFullRegistrationDataProvider {

	private static final String SHEET_NAME = "employeeData";

	@DataProvider(name = "employeeData", parallel = true)
	public static Object[][] getEmployeeData() {

		try {
			// 1️ Load Excel
			ExcelUtility excel = new ExcelUtility("src/test/resources/employee_registration.xlsx");

			// 2️ Format Header
			excel.formatHeaderRow(SHEET_NAME, 80);
			excel.autoFitColumns(SHEET_NAME, 80);

			// 3️ Read sheet data
			Object[][] rawData = excel.getSheetData(SHEET_NAME);

			if (rawData.length == 0) {
				throw new RuntimeException("No data found in sheet: " + SHEET_NAME);
			}

			Object[][] finalData = new Object[rawData.length][1];

			for (int i = 0; i < rawData.length; i++) {

				Object[] row = rawData[i];
				EmployeeFullRegistrationDataVariables data = new EmployeeFullRegistrationDataVariables();

				// ---------------- ENROLLMENT ----------------
				data.enrollmentCode = row[0].toString();
				data.enrollmentNumber = row[1].toString();
				data.gender = row[2].toString();
				data.employeeId = row[3].toString();
				data.email = row[4].toString();
				data.country = row[5].toString();

				// ---------------- PERSONAL ----------------
				data.firstName = row[6].toString();
				data.lastName = row[7].toString();
				data.nationalId = row[8].toString();
				data.personalCode = row[9].toString();
				data.dob = row[10].toString();
				data.maritalStatus = row[11].toString();
				data.joiningDate = row[12].toString();
				data.countryCode = row[13].toString();
				data.mobile = row[14].toString();
				data.nationality = row[15].toString();
				data.address = row[16].toString();

				// ---------------- GUARDIAN ----------------
				data.guardianType = row[17].toString();
				data.guardianName = row[18].toString();
				data.guardianDob = row[19].toString();
				data.guardianDependent = row[20].toString();

				// ---------------- LANGUAGE ----------------
				data.language = row[21].toString();
				data.languageLevel = row[22].toString();

				// ---------------- VACCINATION ----------------
				data.vaccineName = row[23].toString();
				data.vaccineType = row[24].toString();
				data.vaccineCode = row[25].toString();
				data.vaccineDate = row[26].toString();
				data.vaccineRemarks = row[27].toString();

				// ---------------- BIOMETRICS ----------------
				data.biometricImagePath = row[28].toString();

				// ---------------- EMPLOYMENT RIGHTS ----------------
				data.employmentType = row[29].toString();
				data.workload = row[30].toString();
				data.transferType = row[31].toString();
				data.contractNumber = row[32].toString();
				data.contractStartDate = row[33].toString();
				data.contractEndDate = row[34].toString();
				data.department = row[35].toString();
				data.position = row[36].toString();
				data.qualification = row[37].toString();
				data.experienceYears = row[38].toString();
				data.rightsStartDate = row[39].toString();
				data.salaryCode = row[40].toString();
				data.salaryAmount = row[41].toString();
				data.rightsRemarks = row[42].toString();

				// ---------------- POSITION ----------------
				data.positionType = row[43].toString();
				data.positionStartDate = row[44].toString();
				data.positionEndDate = row[45].toString();
				data.organizationName = row[46].toString();
				data.jobTitle = row[47].toString();
				data.positionRemarks = row[48].toString();

				// ---------------- DOCUMENTS ----------------
				data.documentType = row[49].toString();
				data.documentRemarks = row[50].toString();
				data.documentImage = row[51].toString();

				// ---------------- PASSPORT ----------------
				data.passportFirstName = row[52].toString();
				data.passportLastName = row[53].toString();
				data.passportMiddleName = row[54].toString();
				data.passportNumber = row[55].toString();
				data.passportSeries = row[56].toString();
				data.passportCountry = row[57].toString();
				data.passportIssueDate = row[58].toString();
				data.passportExpiryDate = row[59].toString();

				finalData[i][0] = data;
			}

			return finalData;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Object[0][0];
	}
}
