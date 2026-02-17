package com.ihsm.university.ihsmtestcases.pojo;

public class StudentMedicalVaccinationData {

    private String vaccineName;
    private String dose;
    private String vaccinationDate;
    private String batchNo;
    private String remarks;
    private String documentPath;

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(String vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	/*
	 * public String getDocumentPath() { return documentPath; }
	 * 
	 * public void setDocumentPath(String documentPath) { this.documentPath =
	 * documentPath; }
	 */
}
