package com.ihsm.university.ihsmtestcases.apitests;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateStudentData {

    public static void main(String[] args) {

        String fileName  = "student_registration.csv";
        int totalStudents = 100; 

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            // Header - exact match to your payload
            writer.println(
                "intStudentInformationId," +
                "intAcademicPlanId," +
                "intProgramId," +
                "intCurrentSessionId," +
                "intAdmissionType," +
                "intState," +
                "intBatchId," +
                "intCampusId," +
                "intDegreeId," +
                "intLanguageId," +
                "intCourseId," +
                "intSemesterId," +
                "intNewGroupId," +
                "strEnrollmentNo," +
                "strApplicationNo," +
                "strFirstName," +
                "strMiddleName," +
                "strLastName," +
                "strFullnameOther," +
                "strFatherName," +
                "strMotherName," +
                "strDOB," +
                "strAddress," +
                "strGender," +
                "strCity," +
                "strParentPhone," +
                "strParentEmail," +
                "strNationality," +
                "intCategoryId," +
                "dttCreationDate," +
                "strAadhaarNo," +
                "strCityName," +
                "intStatusId," +
                "strBloodGroup," +
                "intChildDiseaseCategoryId," +
                "strParentsWorkingBRGS," +
                "strDistanceFromSchool," +
                "strHealthCard"
            );

            // Generate rows
            for (int i = 1; i <= totalStudents; i++) {

                String enrollmentNo  = "654" + String.format("%03d", i); // 654001, 654002...
                String applicationNo = "654" + String.format("%03d", i);
                String firstName     = "Student" + i;
                String middleName    = "K";
                String lastName      = "Sharma";
                String dob           = "2000-01-01";
                String parentPhone   = "98981" + String.format("%05d", i);
                String parentEmail   = "parent" + i + "@gmail.com";
                String aadhaarNo     = "1234" + String.format("%08d", i);
                String creationDate  = "2023-03-04";

                writer.println(
                    "0," +                  // intStudentInformationId
                    "1," +                  // intAcademicPlanId
                    "0," +                  // intProgramId
                    "1," +                  // intCurrentSessionId
                    "1," +                  // intAdmissionType
                    "14," +                 // intState
                    "1," +                  // intBatchId
                    "1," +                  // intCampusId
                    "0," +                  // intDegreeId
                    "0," +                  // intLanguageId
                    "2," +                  // intCourseId
                    "3," +                  // intSemesterId
                    "0," +                  // intNewGroupId
                    enrollmentNo + "," +
                    applicationNo + "," +
                    firstName + "," +
                    middleName + "," +
                    lastName + "," +
                    "," +                   // strFullnameOther
                    "," +                   // strFatherName
                    "," +                   // strMotherName
                    dob + "," +
                    "India," +              // strAddress
                    "2," +                  // strGender
                    "166," +               // strCity
                    parentPhone + "," +
                    parentEmail + "," +
                    "98," +                // strNationality
                    "0," +                 // intCategoryId
                    creationDate + "," +
                    aadhaarNo + "," +
                    "Gurugram," +          // strCityName
                    "3," +                 // intStatusId
                    "," +                  // strBloodGroup
                    "0," +                 // intChildDiseaseCategoryId
                    "," +                  // strParentsWorkingBRGS
                    "," +                  // strDistanceFromSchool
                    ""                     // strHealthCard
                );
            }

            System.out.println("✅ Done! student_registration.csv created with " + totalStudents + " rows.");

        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}