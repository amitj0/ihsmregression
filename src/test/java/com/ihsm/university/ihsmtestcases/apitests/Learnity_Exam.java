package com.ihsm.university.ihsmtestcases.apitests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Learnity_Exam {

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

		// ── FIX: correct field is "access_Token" (already includes "Bearer ") ──
		accessToken = response.jsonPath().getString("access_Token");

		Assert.assertNotNull(accessToken, "Login failed — token not found!");
		System.out.println(">>> Token obtained successfully: " + accessToken.substring(0, 30) + "...");
	}

	// ══════════════════════════════════════════════════════════════════════════
	// STEP 2 — DataProvider: edit student IDs here
	// ══════════════════════════════════════════════════════════════════════════

	/** Option A — individual student IDs, one exam per student */
	@DataProvider(name = "studentIds")
	public Object[][] studentIds() {
	    return new Object[][] {
	    	{ "520" }, { "521" }, { "522" }, { "523" }, { "524" },
	    	{ "525" }, { "526" }, { "527" }, { "528" }, { "529" },
	    	{ "530" }, { "531" }, { "532" }, { "533" }, { "534" },
	    	{ "535" }, { "536" }, { "537" }, { "538" }, { "539" },
	    	{ "540" }, { "541" }, { "542" }, { "543" }, { "544" },
	    	{ "545" }, { "546" }, { "547" }, { "548" }, { "549" },
	    	{ "550" }, { "551" }, { "552" }, { "553" }, { "554" },
	    	{ "555" }, { "556" }, { "557" }, { "558" }, { "559" },
	    	{ "560" }, { "561" }, { "562" }, { "563" }, { "564" },
	    	{ "565" }, { "566" }, { "567" }, { "568" }, { "569" },
	    	
				/*
				 * { "570" }, { "571" }, { "572" }, { "573" }, { "574" }, { "575" }, { "576" },
				 * { "577" }, { "578" }, { "579" }, { "580" }, { "581" }, { "582" }, { "583" },
				 * { "584" }, { "585" }, { "586" }, { "587" }, { "588" }, { "589" }, { "590" },
				 * { "591" }, { "592" }, { "593" }, { "594" }, { "595" }, { "596" }, { "597" },
				 * { "598" }, { "599" }, { "600" }, { "601" }, { "602" }, { "603" }, { "604" },
				 * { "605" }, { "606" }, { "607" }, { "608" }, { "609" }, { "610" }, { "611" },
				 * { "612" }, { "613" }, { "614" }, { "615" }, { "616" }, { "617" }, { "618" },
				 * { "619" },
				 */
				/*
				 * { "620" }, { "621" }, { "622" }, { "623" }, { "624" }, { "625" }, { "626" },
				 * { "627" }, { "628" }, { "629" }, { "630" }, { "631" }, { "632" }, { "633" },
				 * { "634" }, { "635" }, { "636" }, { "637" }, { "638" }, { "639" }, { "640" },
				 * { "641" }, { "642" }, { "643" }, { "644" }, { "645" }, { "646" }, { "647" },
				 * { "648" }, { "649" }, { "650" }, { "651" }, { "652" }, { "653" }, { "654" },
				 * { "655" }, { "656" }, { "657" }, { "658" }, { "659" }, { "660" }, { "661" },
				 * { "662" }, { "663" }, { "664" }, { "665" }, { "666" }, { "667" }, { "668" },
				 * { "669" },
				 */
	  
	    };
	}
	

	@DataProvider(name = "studentIdRange")
	public Object[][] studentIdRange() {
		int start = 89;
		int end = 135;
		Object[][] data = new Object[end - start + 1][1];
		for (int i = start; i <= end; i++) {
			data[i - start][0] = String.valueOf(i);
		}
		return data;
	}

	/** Option C — all students in ONE single exam call */
	public String allStudentIdsAsOneString() {
		List<Integer> ids = Arrays.asList(89, 90, 91, 92, 93, 94, 95);
		return ids.stream().map(String::valueOf).collect(Collectors.joining(","));
	}

	// ══════════════════════════════════════════════════════════════════════════
	// STEP 3 — Save Exam
	// ══════════════════════════════════════════════════════════════════════════
	@Test(dataProvider = "studentIds")
	public void saveExamLearnity(String arrStudentIds) {
		System.out.println(">>> Saving exam for student ID(s): " + arrStudentIds);

		String body = "{" + "\"intSemesterId\": 0," + "\"intModuleId\": 0," + "\"intDiciplineId\": 0,"
				+ "\"intSubjectId\": 194," + "\"intPeriod\": 343," + "\"intLanguageId\": 17,"
				+ "\"intContinueAbsentee\": 0," + "\"strGroups\": \"\"," + "\"strStartDate\": \"2026-03-10\","
				+ "\"strStartTime\": \"08:00\"," + "\"strComplitionDate\": \"2026-03-17\","
				+ "\"strComplitionTime\": \"23:59\"," + "\"intDuration\": 120," + "\"intTotalQuestion\": 0,"
				+ "\"intProcessTypeId\": 0," + "\"strExamSetId\": \"\"," + "\"strAdditionalTeacher\": \"\","
				+ "\"strRoom\": \"\"," + "\"strSubjects\": \"\"," + "\"lstSubjectQuestion\": [],"
				+ "\"arrStudentIds\": \"" + arrStudentIds + "\"," + "\"intTopicId\": 107," + "\"strSubject\": \"\","
				+ "\"intScreenerType_Role\": 4," + "\"intMainSubjectId\": 0," + "\"strGuid\": \"\"" + "}";

		Response response = RestAssured.given().baseUri(BASE_URL).contentType(ContentType.JSON)
				.header("access_token", accessToken) // ← token already contains "Bearer ..."
				.body(body).when().post("/API/ExamSystem/SaveExam_Learnity").then().extract().response();

		System.out.println(">>> Response Status : " + response.getStatusCode());
		System.out.println(">>> Response Body   : " + response.getBody().asString());

		Assert.assertEquals(response.getStatusCode(), 200, "Exam save failed for student(s): " + arrStudentIds);

		System.out.println(">>> Exam saved successfully for: " + arrStudentIds);
	}

	
	// @Test
	public void saveExamAllStudents() {
		saveExamLearnity(allStudentIdsAsOneString());
	}
}