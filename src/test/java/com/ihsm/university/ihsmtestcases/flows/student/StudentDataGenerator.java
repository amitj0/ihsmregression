package com.ihsm.university.ihsmtestcases.flows.student;

import java.util.UUID;

public class StudentDataGenerator {

    public static String generateStudentPayload() {

        String uniqueId = UUID.randomUUID().toString().substring(0, 6);

        return String.format(
                "{ \"name\":\"Student%s\", " +
                "\"email\":\"student%s@test.com\", " +
                "\"password\":\"Test@123\", " +
                "\"course\":\"Computer Science\" }",
                uniqueId, uniqueId
        );
    }
}
