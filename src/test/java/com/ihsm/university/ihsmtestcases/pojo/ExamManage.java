package com.ihsm.university.ihsmtestcases.pojo;

public class ExamManage {

	private String testId;
	private String program;
	private String semester;
	private String startDate;
	private String endDate;
	private String expectedMessage;

	// Getters & Setters

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getExpectedMessage() {
		return expectedMessage;
	}

	public void setExpectedMessage(String expectedMessage) {
		this.expectedMessage = expectedMessage;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
