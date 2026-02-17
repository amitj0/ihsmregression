package com.ihsm.university.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.ihsm.university.base.FlowStep;

public class FlowStateUtils {

	private static final String FILE = "flow.properties";

	public static void saveStep(FlowStep step) {
		try {
			Properties p = new Properties();
			p.setProperty("lastStep", step.name());
			p.store(new FileOutputStream(FILE), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static FlowStep getLastCompletedStep() {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(FILE));
			return FlowStep.valueOf(p.getProperty("lastStep"));
		} catch (Exception e) {
			return null;
		}
	}
}
