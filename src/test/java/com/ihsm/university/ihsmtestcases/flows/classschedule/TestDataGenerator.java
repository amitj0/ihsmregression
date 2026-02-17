package com.ihsm.university.ihsmtestcases.flows.classschedule;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {
	
	private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("ddMMyyyy");

	public static String getRandomDate(String start, String end) {

		LocalDate startDate = LocalDate.parse(start, FORMAT);
		LocalDate endDate = LocalDate.parse(end, FORMAT);

		long randomDay = ThreadLocalRandom.current().nextLong(startDate.toEpochDay(), endDate.toEpochDay() + 1);

		return LocalDate.ofEpochDay(randomDay).format(FORMAT);
	}

	// Generate random FROM and TO dates (TO always after FROM)
	public static String[] getRandomScheduleDates() {

		String startRange = "20012026"; // 20 Jan 2026
		String endRange = "20052026"; // 20 May 2026

		String fromDate = getRandomDate(startRange, endRange);
		String toDate = getRandomDate(fromDate, endRange);

		return new String[] { fromDate, toDate };
	}
	
	private static final Random random = new Random();
    public static String generate4DigitPIN() {
        int pin = 1000 + random.nextInt(9000); // ensures 4 digits
        return String.valueOf(pin);
    }
    
    public static String generateRandomFirstName() {

		String[] names = { "Ram", "Shyam", "Amit", "Rohit", "Karan", "Arjun", "Vijay", "Rahul", "Suresh", "Rakesh",
				"Manish", "Anil", "Deepak", "Sunil", "Vikas", "Sachin" };

		Random random = new Random();
		return names[random.nextInt(names.length)];
	}
    
    public static String generateRandomMiddleName() {
        String[] middleNames = { "Kumar", "Raj", "Singh", "Dev", "Prasad", "Chand", "Verma", "Gupta", "Rai", "Kashyap" };
        Random random = new Random();
        return middleNames[random.nextInt(middleNames.length)];
    }

    public static String generateRandomLastName() {
        String[] lastNames = { "Sharma", "Patel", "Mehta", "Reddy", "Joshi", "Chopra", "Kapoor", "Malhotra", "Singh", "Verma" };
        Random random = new Random();
        return lastNames[random.nextInt(lastNames.length)];
    }


	public static String generateRandomGender() {
		String[] genders = { "Male", "Female" };
		Random random = new Random();
		return genders[random.nextInt(genders.length)];
	}
	
	public static String generateRandomPhoneNumber() {
	    Random random = new Random();
	    StringBuilder phoneNumber = new StringBuilder("9"); // start with 9 (common for India)
	    for (int i = 0; i < 9; i++) {
	        phoneNumber.append(random.nextInt(10)); // append 0-9
	    }
	    return phoneNumber.toString();
	}

	public static String randomEmail() {
		return ("User" + ((int) (Math.random() * 1000)) + "_" + UUID.randomUUID().toString().substring(0, 6)
				+ "@testmail.com");
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
	public static String generateRandomMotherName() {
	    String[] motherNames = { "Sita", "Radha", "Lakshmi", "Pooja", "Anita", "Kavita", "Sunita", "Rekha", "Meena", "Shanti" };
	    Random random = new Random();
	    return motherNames[random.nextInt(motherNames.length)];
	}

	public static String randomNotes() {
		String[] notes = { "Patient is recovering well", "Follow-up required in 2 weeks", "All vitals are normal",
				"Monitor for any side effects", "Next appointment scheduled soon", "No complications observed",
				"Advised rest and hydration", "Vaccination administered successfully" };
		return notes[(int) (Math.random() * notes.length)];
	}
	

}
