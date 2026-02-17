package com.ihsm.university.ihsmtestcases.flows.employee;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class TestDataGenerator {

	private static Random random = new Random();

	// Random String
	public static String randomString(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	public static String randomGuardianName() {
		String[] firstNames = { "Raj", "Amit", "Priya", "Sonal", "Vikram", "Anita", "Rohit", "Neha" };
		String[] lastNames = { "Sharma", "Verma", "Patel", "Gupta", "Kumar", "Singh", "Mehta", "Reddy" };

		return firstNames[(int) (Math.random() * firstNames.length)] + " "
				+ lastNames[(int) (Math.random() * lastNames.length)];
	}

	public static String generateRandomLastName() {

		String[] lastNames = { "Kumar", "Sharma", "Singh", "Verma", "Gupta", "Patel", "Yadav", "Mehta", "Agarwal",
				"Jain", "Malhotra", "Kapoor", "Bansal", "Chopra", "Khanna" };

		Random random = new Random();
		return lastNames[random.nextInt(lastNames.length)];
	}

	public static String generateRandomRussianFirstName() {

		String[] russianFirstNames = { "Иван", "Дмитрий", "Сергей", "Алексей", "Михаил", "Николай", "Владимир",
				"Андрей", "Павел", "Юрий" };

		Random random = new Random();
		return russianFirstNames[random.nextInt(russianFirstNames.length)];
	}

	public static String generateRandomFirstName() {

		String[] names = { "Ram", "Shyam", "Amit", "Rohit", "Karan", "Arjun", "Vijay", "Rahul", "Suresh", "Rakesh",
				"Manish", "Anil", "Deepak", "Sunil", "Vikas", "Sachin" };

		Random random = new Random();
		return names[random.nextInt(names.length)];
	}

	public static String generateRandomGender() {
		String[] genders = { "Male", "Female" };
		Random random = new Random();
		return genders[random.nextInt(genders.length)];
	}

	// Random Number String
	public static String randomNumber(int length) {
		String nums = "0123456789";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(nums.charAt(random.nextInt(nums.length())));
		}
		return sb.toString();
	}

	public static String randomIndianAddress() {
		String[] streets = { "MG Road", "Brigade Road", "Park Street", "FC Road", "NH 48" };
		String[] cities = { "Mumbai", "Delhi", "Bengaluru", "Chennai", "Kolkata", "Hyderabad", "Pune" };
		String[] states = { "Maharashtra", "Delhi", "Karnataka", "Tamil Nadu", "West Bengal", "Telangana" };

		return (int) (Math.random() * 999) + " " + streets[(int) (Math.random() * streets.length)] + ", "
				+ cities[(int) (Math.random() * cities.length)] + ", " + states[(int) (Math.random() * states.length)]
				+ " - " + (100000 + (int) (Math.random() * 900000));
	}

	public static String randomVaccinationType() {
		String[] types = { "COVID-19", "Polio", "Hepatitis B", "Measles", "Tetanus", "Influenza", "BCG", "MMR" };
		return types[(int) (Math.random() * types.length)];
	}

	public static String randomPhotoFile() throws Exception {
		int width = 400 + (int) (Math.random() * 200);
		int height = 400 + (int) (Math.random() * 200);

		String imageUrl = "https://picsum.photos/" + width + "/" + height;
		String filePath = System.getProperty("user.dir") + "/randomImage.jpg";

		try (InputStream in = new URL(imageUrl).openStream()) {
			Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		}

		return filePath; // return LOCAL file path
	}

	public static String randomEmployeePhotoFile() throws Exception {

		String gender = Math.random() < 0.5 ? "men" : "women";
		int id = (int) (Math.random() * 100);

		String imageUrl = "https://randomuser.me/api/portraits/" + gender + "/" + id + ".jpg";

		String filePath = System.getProperty("user.dir") + "/employeePhoto.jpg";

		try (InputStream in = new URL(imageUrl).openStream()) {
			Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		}

		return filePath;
	}

	public static int randomSalary() {
		return 20000 + (int) (Math.random() * (200000 - 20000));
	}

	public static String randomNotes() {
		String[] notes = { "Patient is recovering well", "Follow-up required in 2 weeks", "All vitals are normal",
				"Monitor for any side effects", "Next appointment scheduled soon", "No complications observed",
				"Advised rest and hydration", "Vaccination administered successfully" };
		return notes[(int) (Math.random() * notes.length)];
	}

	private static Set<String> usedVaccinationPhases = new HashSet<>();

	public static String randomVaccinationPhase() {

		String[] phases = { "First Dose", "Second Dose1", "Booster", "Completed" };

		// Reset when all values used
		if (usedVaccinationPhases.size() == phases.length) {
			usedVaccinationPhases.clear();
		}

		String phase;
		do {
			phase = phases[(int) (Math.random() * phases.length)];
		} while (usedVaccinationPhases.contains(phase));

		usedVaccinationPhases.add(phase);
		return phase;
	}

	public static String randomUniversity() {
		String[] prefixes = { "National", "International", "Global", "State", "Central", "Premier", "Advanced" };
		String[] types = { "University", "Institute of Technology", "College", "Academy", "School of Science",
				"Institute" };
		String[] names = { "Innovation", "Science", "Arts", "Engineering", "Management", "Technology", "Research" };

		return prefixes[(int) (Math.random() * prefixes.length)] + " " + names[(int) (Math.random() * names.length)]
				+ " " + types[(int) (Math.random() * types.length)];
	}

	public static String randomUniversityPosition() {
		String[] positions = { "Professor", "Assistant Professor", "Associate Professor", "Lecturer", "Adjunct Faculty",
				"Research Scholar", "Teaching Assistant", "Visiting Professor" };
		return positions[(int) (Math.random() * positions.length)];
	}

	public static String randomDocumentType() {
		String[] docs = { "Passport Front", "Passport Back", "12th Marksheet", "Visa", "Admission Invitation", "Photo",
				"NEET Score Card", "10th Marksheet", "Diploma", "PAN Card", "Degree" };
		return docs[(int) (Math.random() * docs.length)];
	}

	public static String randomPassportType() {
		String[] types = { "Ordinary", "Diplomatic", "Official", "Service" };
		return types[(int) (Math.random() * types.length)];
	}

	public static String randomPassportCountry() {
		String[] countries = { "India", "United States", "United Kingdom", "Canada", "Australia", "Germany" };
		return countries[(int) (Math.random() * countries.length)];
	}

	public static String randomIssueAgency() {
		String[] agencies = { "Ministry of External Affairs", "Passport Seva Kendra", "Consulate General",
				"High Commission" };
		return agencies[(int) (Math.random() * agencies.length)];
	}

	public static String randomMilitaryRank() {
		String[] ranks = { "Private", "Corporal", "Sergeant", "Staff Sergeant", "Lieutenant", "Captain", "Major",
				"Lieutenant Colonel", "Colonel", "Brigadier", "Major General", "Lieutenant General", "General" };
		return ranks[(int) (Math.random() * ranks.length)];
	}

	public static String randomScienceResearchWork() {
		String[] topics = { "Artificial Intelligence in Healthcare", "Renewable Energy Optimization",
				"Genetic Mutation Analysis", "Nanotechnology in Drug Delivery", "Climate Change Impact Study",
				"Quantum Computing Algorithms", "Cancer Cell Detection Using ML", "Robotics Automation Systems",
				"Space Weather Forecasting", "Neural Network Optimization" };
		return topics[(int) (Math.random() * topics.length)];
	}

	public static String randomPublishingLevel() {
		String[] levels = { "International Journal", "National Journal", "Scopus Indexed Journal",
				"Web of Science Journal", "Conference Proceeding", "UGC Approved Journal", "Peer Reviewed Journal" };
		return levels[(int) (Math.random() * levels.length)];
	}

	public static String randomUrl() {
		return "https://www.journal" + (int) (Math.random() * 1000) + ".com/article/"
				+ (10000 + (int) (Math.random() * 90000));
	}

	public static String randomMagazineName() {
		String[] names = { "International Journal of Science", "Global Research Review", "Advanced Technology Journal",
				"Journal of Medical Research", "Engineering Innovation Today", "Scientific Discoveries" };
		return names[(int) (Math.random() * names.length)];
	}

	public static String randomArticleName() {
		String[] articles = { "A Study on Deep Learning Models", "Analysis of Renewable Energy Systems",
				"Blockchain Security Challenges", "Cancer Detection Using AI", "Climate Change and Sustainability",
				"IoT Applications in Smart Cities" };
		return articles[(int) (Math.random() * articles.length)];
	}

	public static String randomAuthors() {
		String[] authors = { "Amit Sharma", "Neha Verma", "Rohit Kumar", "Priya Singh", "Ankit Patel", "Sneha Gupta" };
		return authors[(int) (Math.random() * authors.length)];
	}

	public static String randomPassportNumber() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return "" + letters.charAt((int) (Math.random() * 26)) + letters.charAt((int) (Math.random() * 26))
				+ letters.charAt((int) (Math.random() * 26)) + letters.charAt((int) (Math.random() * 26))
				+ (100000 + (int) (Math.random() * 900000));
	}

	public static String randomPatentType() {
		String[] types = { "Utility Patent", "Design Patent", "Plant Patent", "Provisional Patent",
				"International Patent" };
		return types[(int) (Math.random() * types.length)];
	}

	public static String randomInvention() {
		String[] inventions = { "Smart Water Purification System", "AI Based Traffic Control Device",
				"Solar Powered Cooling Helmet", "Automatic Plant Watering Machine", "Biometric Attendance System",
				"Smart Blind Stick for Visually Impaired", "Energy Efficient Street Lighting System",
				"Wearable Health Monitoring Band", "Voice Controlled Home Automation",
				"Portable Medical Diagnostic Device" };
		return inventions[(int) (Math.random() * inventions.length)];
	}

	public static String randomInventionLevel() {
		String[] levels = { "Institution Level", "State Level", "National Level", "International Level",
				"Prototype Level", "Commercial Level", "Research Level" };
		return levels[(int) (Math.random() * levels.length)];
	}

	public static String randomDegreeLevel() {
		String[] levels = { "Diploma", "Bachelor", "Master", "Doctorate", "Post Doctorate" };
		return levels[(int) (Math.random() * levels.length)];
	}

	public static String randomSphere() {
		String[] spheres = { "Engineering", "Medical", "Science", "Arts", "Commerce", "Management", "Law", "Education",
				"Information Technology", "Pharmacy" };
		return spheres[(int) (Math.random() * spheres.length)];
	}

	public static String randomAcademicDegree() {
		String[] degrees = { "BTech", "BE", "BSc", "BA", "BCom", "MTech", "ME", "MSc", "MA", "MCom", "MBA", "PhD", "MD",
				"MS", "BPharm", "MPharm" };
		return degrees[(int) (Math.random() * degrees.length)];
	}

	public static String randomSpeciality() {
		String[] specialities = { "Computer Science", "Artificial Intelligence", "Data Science", "Cyber Security",
				"Mechanical Engineering", "Electrical Engineering", "Civil Engineering", "Biotechnology",
				"Microbiology", "Finance", "Marketing", "Human Resource Management", "Physics", "Chemistry",
				"Mathematics" };
		return specialities[(int) (Math.random() * specialities.length)];
	}

	public static String randomTitle() {
		String[] titles = { "Senior Software Engineer", "Assistant Professor", "Research Associate",
				"Project Coordinator", "System Analyst", "Technical Lead", "Program Manager", "Data Scientist",
				"Operations Manager", "Quality Assurance Engineer" };
		return titles[(int) (Math.random() * titles.length)];
	}

	// Unique Email
//	public static String randomEmail() {
//		return "name_" + System.currentTimeMillis() + "@testmail.com";
//	}

	public static String randomEmail() {
		return ("User" + ((int) (Math.random() * 1000)) + "_" + UUID.randomUUID().toString().substring(0, 6)
				+ "@testmail.com");
	}

	// Random Passport
	public static String randomPassport() {
		return "P" + randomNumber(7);
	}

	// Random Phone
	public static String randomPhone() {
		return "9" + randomNumber(9);
	}

	// Random Future Date
	public static String futureDate() {
		return "01012030";
	}

	// Random Past Date
	public static String pastDate() {
		return "01011995";
	}

	public static String randomCountry() {

		String[] countries = { "India", "United States", "United Kingdom", "Canada", "Germany", "France", "Australia",
				"Japan", "Switzerland", "Brazil", "Netherlands", "Singapore" };

		return countries[random.nextInt(countries.length)];
	}

	// Random Guardian Type
	public static String randomGuardian() {

		String[] guardians = { "Father", "Mother", "Spouse", "Brother", "Sister", "Uncle", "Aunt", "Guardian" };

		return guardians[random.nextInt(guardians.length)];
	}

	// Random Marital Status
	public static String randomMaritalStatus() {

		String[] statuses = { "Single", "Married", "Divorced", "Widowed", "Separated" };

		return statuses[random.nextInt(statuses.length)];
	}

}
