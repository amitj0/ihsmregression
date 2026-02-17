package com.ihsm.university.ihsmtestcases.dataprovider;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ihsm.university.ihsmtestcases.pojo.*;
import com.ihsm.university.utilities.ExcelToPojoUtils;

public class ProfessionalInformationDataProvider {

	private static final String FILE_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/student_registration.xlsx";

	// ================= DEGREE =================
	@DataProvider(name = "ProfessionalDegreeData")
	public Object[][] getProfessionalDegreeData() {

		List<ProfessionalDegreeData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "Degree",
				ProfessionalDegreeData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	// ================= ACADEMICS =================
	@DataProvider(name = "ProfessionalAcademicsData")
	public Object[][] getProfessionalAcademicsData() {

		List<ProfessionalAcademicsData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "Academics",
				ProfessionalAcademicsData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	// ================= TITLE =================
	@DataProvider(name = "ProfessionalTitleData")
	public Object[][] getProfessionalTitleData() {

		List<ProfessionalTitleData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "Title",
				ProfessionalTitleData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	// ================= SCIENTIFIC RESEARCH =================
	@DataProvider(name = "ProfessionalResearchData")
	public Object[][] getProfessionalResearchData() {

		List<ProfessionalScientificData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "Research",
				ProfessionalScientificData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	// ================= REWARDS =================
	@DataProvider(name = "ProfessionalRewardsData")
	public Object[][] getProfessionalRewardsData() {

		List<ProfessionalRewardsData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "Rewards",
				ProfessionalRewardsData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	// ================= PATENT =================
	@DataProvider(name = "ProfessionalPatentData")
	public Object[][] getProfessionalPatentData() {

		List<ProfessionalPatentData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "Patent",
				ProfessionalPatentData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	// ================= ATTESTATION =================
	@DataProvider(name = "ProfessionalAttestationData")
	public Object[][] getProfessionalAttestationData() {

		List<ProfessionalAttestationData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "Attestation",
				ProfessionalAttestationData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}

	// ================= MILITARY =================
	@DataProvider(name = "ProfessionalMilitaryData")
	public Object[][] getProfessionalMilitaryData() {

		List<ProfessionalMilitaryData> list = ExcelToPojoUtils.getDataAsPojo(FILE_PATH, "Military",
				ProfessionalMilitaryData.class);

		return list.stream().map(data -> new Object[] { data }).toArray(Object[][]::new);
	}
}
