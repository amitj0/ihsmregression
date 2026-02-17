package com.ihsm.university.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

	private static Properties prop = new Properties();

	static {
		try {
			String path = System.getProperty("user.dir") + "/src/test/resources/config.properties";
			FileInputStream fis = new FileInputStream(path);
			prop.load(fis);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load config.properties", e);
		}
	}

	public static String get(String key) {
		return prop.getProperty(key);
	}

	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(prop.getProperty(key, "false"));
	}
}
