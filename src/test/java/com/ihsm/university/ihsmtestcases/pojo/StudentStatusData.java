package com.ihsm.university.ihsmtestcases.pojo;

public class StudentStatusData {

	private String status; // e.g., "Enrolled and Active"
	private String statusDate; // e.g., "01012026"
	private String statusCode; // e.g., "45454"
	private String notes; // any notes
	private String documentPath; // employee photo or document

	// Getters and Setters
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/*
	 * public String getDocumentPath() { return documentPath; }
	 * 
	 * public void setDocumentPath(String documentPath) { this.documentPath =
	 * documentPath; }
	 */
}
