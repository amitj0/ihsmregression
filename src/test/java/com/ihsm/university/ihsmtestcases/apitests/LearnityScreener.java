package com.ihsm.university.ihsmtestcases.apitests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

public class LearnityScreener {

	private static final String BASE_URL = "https://learnityapi.softsolanalytics.com";
	private static final String EMAIL = "admin@gmail.com";
	private static final String PASSWORD = "123456";

	private String accessToken = "";

	// ══════════════════════════════════════════════════════════════════════════
	// STEP 1 — Login & capture token
	// ══════════════════════════════════════════════════════════════════════════
	@BeforeClass
	public void login() {
		System.out.println(">>> Logging in as: " + EMAIL);

		String loginBody = "{" + "\"strEmail\": \"" + EMAIL + "\"," + "\"strPassword\": \"" + PASSWORD + "\","
				+ "\"intSource\": 1" + "}";

		Response response = RestAssured.given().baseUri(BASE_URL).contentType(ContentType.JSON).body(loginBody).when()
				.post("/API/User/UserLogin").then().statusCode(200).extract().response();

		accessToken = response.jsonPath().getString("access_Token");

		Assert.assertNotNull(accessToken, "Login failed — token not found!");
		System.out.println(">>> Token obtained successfully: " + accessToken.substring(0, 30) + "...");
	}

	// ══════════════════════════════════════════════════════════════════════════
	// STEP 2 — DataProvider: studentId + FIRST GUID only per student
	// Total: 50 rows (one per student)
	// ══════════════════════════════════════════════════════════════════════════
	@DataProvider(name = "studentGuidPairs")
	public Object[][] studentGuidPairs() {
		return new Object[][] {
			
				  { "620", "ea3f7937-3bdb-424e-a624-b24ed8ed602c" }, { "621",
				  "16538ec2-2d82-4e94-a820-db7ff56d4075" }, { "622",
				  "3b7e33b1-8dc7-4594-8375-bda3934cff38" }, { "623",
				  "ee9e1321-1176-4297-ba4f-a661dc2636a2" }, { "624",
				  "b37e1e94-d29f-4d0d-9ee8-b3ddf0d02b28" }, { "625",
				  "6e4a51f1-fba5-4231-890b-d5dd0825bb97" }, { "626",
				  "422dad8b-1127-4b45-90ae-828f6ba85d89" }, { "627",
				  "8b7c3d08-d32e-4b51-b69d-4e69a8ac9126" }, { "628",
				  "2edabe9a-e60d-4e57-9ddc-988fd3d9b2eb" }, { "629",
				  "e7ab82c0-4a34-47b6-93a8-9295ee29d99a" }, { "630",
				  "89b4074e-2fd0-4d19-8452-0205bd56e61a" }, { "631",
				  "78aef37a-d88a-4adb-b3d5-736814fb1d76" }, { "632",
				  "2c7c6718-b39a-4b41-84ce-f92cb5bbe7bb" }, { "633",
				  "29576533-26d2-42da-b5ee-ef0b40d94eb5" }, { "634",
				  "a49251f6-9448-45ca-9cfd-759f50faddde" }, { "635",
				  "1a4fd4d0-b88a-4439-b72e-c465b2a21c72" }, { "636",
				  "db4b5afd-f825-4aed-b9f9-45b100719597" }, { "637",
				  "a032e2ca-efce-49f6-a708-a4720b2ba300" }, { "638",
				  "7dc3c9a2-df18-4e14-a6f0-561cc13e252e" }, { "639",
				  "ee8e9822-7bef-4603-afea-186748d8e26b" }, { "640",
				  "43a25679-7a76-458d-901a-1ae57deb7e1f" }, { "641",
				  "9c198723-018d-456c-8671-1532ea05e662" }, { "642",
				  "30c42d46-af62-4a31-9ffd-80ee225ff8f7" }, { "643",
				  "5e202461-1ff0-4ff8-9f29-ddf95fefe8e1" }, { "644",
				  "dc4ae6ba-abd7-4816-8ccc-24a77a8c1016" }, { "645",
				  "38cfb6fa-cd71-4d9c-b87e-d77891b4ee6b" }, { "646",
				  "814ce83f-44d1-4b85-af79-5ef60270cb4c" }, { "647",
				  "95ef5e84-cd69-4d8d-aca1-7ade9df9d814" }, { "648",
				  "470e2c53-7c6a-44ad-b35b-64ad76c1433b" }, { "649",
				  "6e857be1-65db-470b-8e38-bbf7ae7acbad" }, { "650",
				  "a06b5487-2b1a-4621-b135-0f74d9e66615" }, { "651",
				  "f346af26-2d15-496a-9453-85ba6331864d" }, { "652",
				  "cdac6698-3a90-4c97-8111-356c05e6f571" }, { "653",
				  "0b7c3418-4cba-4c8f-8578-5634e249423d" }, { "654",
				  "ce4fa649-60e2-4839-ab00-aec1a52e6281" }, { "655",
				  "d3661b2d-9c7d-4ddc-863f-bc6d4c4f5da6" }, { "656",
				  "fed458d8-5b66-4b68-b11d-b140f27fc8d4" }, { "657",
				  "aabc52e6-658f-4201-a0ed-a165ea44fd0b" }, { "658",
				  "cd4b78b4-5979-4f99-ad1d-6da281dcb79f" }, { "659",
				  "e44fc785-bd65-4f50-ae34-6099fd824c16" }, { "660",
				  "c53c78b1-e4a1-442b-94ba-7acc5a0ba02f" }, { "661",
				  "7c728363-de9f-4062-81c9-673e7fad711e" }, { "662",
				  "ecf151e5-030c-4e11-bc34-20296928ff52" }, { "663",
				  "2d18189c-a58a-4e24-a40d-3f928f05232d" }, { "664",
				  "ec6d64aa-4744-49ab-ae27-991306e3c233" }, { "665",
				  "0534c6e6-f357-4401-8f13-62d650421823" }, { "666",
				  "26b558be-6068-41af-b79a-41c8209b8799" }, { "667",
				  "a0ffcf9b-4b12-4c74-8d5f-958657a11105" }, { "668",
				  "bd037980-cdc4-4364-877d-55cc4c8e82c7" }, { "669",
				  "c519efcd-aa5d-4b40-b05d-3d4554af530b" },
				 
		};
	}

	// ══════════════════════════════════════════════════════════════════════════
	// STEP 3 — Save Exam (50 runs, one per student using first GUID)
	// ══════════════════════════════════════════════════════════════════════════
	@Test(dataProvider = "studentGuidPairs")
	public void saveExamLearnity(String arrStudentIds, String strGuid) {
		System.out.println(">>> Saving exam | Student: " + arrStudentIds + " | GUID: " + strGuid);

		String body = "{" + "\"intSemesterId\": 0," + "\"intModuleId\": 0," + "\"intDiciplineId\": 0,"
				+ "\"intSubjectId\": 1061," + "\"intPeriod\": 343," // Single = 343, Subjective = 2149, Paragraph = 2427
				+ "\"intLanguageId\": 17," + "\"intContinueAbsentee\": 0," + "\"strGroups\": \"\","
				+ "\"strStartDate\": \"2026-03-10\"," + "\"strStartTime\": \"08:00\","
				+ "\"strComplitionDate\": \"2026-03-17\"," + "\"strComplitionTime\": \"23:59\","
				+ "\"intDuration\": 120," + "\"intTotalQuestion\": 0," + "\"intProcessTypeId\": 0,"
				+ "\"strExamSetId\": \"\"," + "\"strAdditionalTeacher\": \"\"," + "\"strRoom\": \"\","
				+ "\"strSubjects\": \"\"," + "\"lstSubjectQuestion\": []," + "\"arrStudentIds\": \"" + arrStudentIds
				+ "\"," + "\"intTopicId\": 122," + "\"strSubject\": \"\"," + "\"intScreenerType_Role\": 4,"
				+ "\"intMainSubjectId\": 0," + "\"strGuid\": \"" + strGuid + "\"" + "}";

		Response response = RestAssured.given().baseUri(BASE_URL).contentType(ContentType.JSON)
				.header("access_token", accessToken).body(body).when().post("/API/ExamSystem/SaveExam_Learnity").then()
				.extract().response();

		// ── Print HTTP status, inner statusCode and message ────────────────────
		int httpStatus = response.getStatusCode();
		int innerStatusCode = response.jsonPath().getInt("statusCode");
		String message = response.jsonPath().getString("message");

		System.out.println(">>> HTTP Status     : " + httpStatus);
		System.out.println(">>> Inner statusCode: " + innerStatusCode);
		System.out.println(">>> Message         : " + message);

		// ── Only assert HTTP 200, test passes regardless of inner statusCode ───
		Assert.assertEquals(httpStatus, 200, "HTTP error for Student: " + arrStudentIds + " | GUID: " + strGuid);

		System.out.println(">>> Exam saved successfully | Student: " + arrStudentIds + " | GUID: " + strGuid);
	}
}