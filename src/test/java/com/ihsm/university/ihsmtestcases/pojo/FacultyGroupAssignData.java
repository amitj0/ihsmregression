package com.ihsm.university.ihsmtestcases.pojo;

public class FacultyGroupAssignData {

	private String session;
	private String batch;
	private String academicPlan;
	private String semester;
	private String groupType;
	private String selectFaculty;

	public String getSelectFaculty() {
		return selectFaculty;
	}

	public void setSelectFaculty(String selectFaculty) {
		this.selectFaculty = selectFaculty;
	}

	// Getters and Setters
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getAcademicPlan() {
		return academicPlan;
	}

	public void setAcademicPlan(String academicPlan) {
		this.academicPlan = academicPlan;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
}
