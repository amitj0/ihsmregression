package com.ihsm.university.ihsmtestcases.apitests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateStudentLoginData {

	public static void main(String[] args) {

		String loginFile = "C://Automation//com.ihsm.university.project//src//test//resources//login_data.csv";
		String studentFile = "C://Automation//com.ihsm.university.project//src//test//resources//student_data.csv";
		int totalUsers = 40000;

		// ── Login CSV ──────────────────────────────────────────
		try (PrintWriter loginWriter = new PrintWriter(new FileWriter(loginFile))) {

			loginWriter.println("strEmail,strPassword,strFirebaseDeviceId,intSource");

			for (int i = 1; i <= totalUsers; i++) {
				String enrollmentNo = "654" + String.format("%03d", i); 
				String email = "user" + i + "@yourdomain.com";
				String password = "Password" + i + "@123";
				String firebaseToken = "firebase_token_" + i;

				loginWriter.println(email + "," + password + "," + firebaseToken + "," + 2);
			}
			System.out.println("login_data.csv created!");

		} catch (IOException e) {
			System.out.println("Login CSV Error: " + e.getMessage());
		}

		// ── Student CSV ────────────────────────────────────────
		try (PrintWriter studentWriter = new PrintWriter(new FileWriter(studentFile))) {

			studentWriter.println("intStudentInformationId,intAcademicPlanId,intProgramId,"
					+ "intCurrentSessionId,intAdmissionType,intState,intBatchId,"
					+ "intCampusId,intDegreeId,intLanguageId,intCourseId,intSemesterId,"
					+ "intNewGroupId,strEnrollmentNo,strApplicationNo,strFirstName,"
					+ "strMiddleName,strLastName,strFullnameOther,strFatherName,"
					+ "strMotherName,strDOB,strAddress,strGender,strCity,"
					+ "strParentPhone,strParentEmail,strNationality,intCategoryId,"
					+ "dttCreationDate,strAadhaarNo,strCityName,intStatusId,"
					+ "strBloodGroup,intChildDiseaseCategoryId,strParentsWorkingBRGS,"
					+ "strDistanceFromSchool,strHealthCard");

			for (int i = 1; i <= totalUsers; i++) {
				String enrollmentNo = "654" + String.format("%03d", i);
				String applicationNo = "654" + String.format("%03d", i);
				String firstName = "Student" + i;
				String parentPhone = "98981" + String.format("%05d", i);
				String parentEmail = "parent" + i + "@gmail.com";
				String aadhaarNo = "1234" + String.format("%08d", i);

				studentWriter.println("0,1,0,1,1,14,1,1,0,0,2,3,0," + enrollmentNo + "," + applicationNo + ","
						+ firstName + ",K,Sharma,,,,," + "2000-01-01,India,2,166," + parentPhone + "," + parentEmail
						+ "," + "98,0,2023-03-04," + aadhaarNo + "," + "Gurugram,3,,0,,,");
			}
			System.out.println("student_data.csv created!");

		} catch (IOException e) {
			System.out.println("Student CSV Error: " + e.getMessage());
		}
	}
}