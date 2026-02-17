package com.ihsm.university.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	private int count = 0;
	private int maxRetry = 2; // Retry 2 times

	@Override
	public boolean retry(ITestResult result) {
		if (count < maxRetry) {
			count++;
			System.out.println("Retrying test " + result.getName() + " - Attempt " + count);
			return true;
		}
		return false;
	}
}
