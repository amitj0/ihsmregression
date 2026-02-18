package com.ihsm.university.utilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.ihsm.university.base.BaseClass;

import org.openqa.selenium.logging.*;
import org.testng.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class ExtentListener implements ITestListener {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> childTest = new ThreadLocal<>();

	// ================= SUITE START =================
	@Override
	public void onStart(ITestContext context) {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(new Date());
		String reportName = "IHSM_University_Report_" + timeStamp + ".html";

		String reportPath = System.getProperty("user.dir") + "/reports/" + reportName;

		ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
		spark.config().setDocumentTitle("IHSM University Automation Report");
		spark.config().setReportName("Automation Execution Report");
		spark.config().setTimeStampFormat("dd MMM yyyy HH:mm:ss");
		spark.config().setTheme(Theme.DARK);

		spark.config().setTheme(Theme.DARK);
		spark.config().setTimeStampFormat("dd MMM yyyy HH:mm:ss");

		spark.config().setCss(

		        /* ===== GLOBAL FONT ===== */
		        "body { font-family: 'Segoe UI', Roboto, sans-serif !important; }" +

		        /* ===== NAVBAR ===== */
		        ".navbar { background: linear-gradient(90deg,#0f2027,#203a43,#2c5364) !important; }" +
		        ".navbar-brand { font-size: 22px !important; font-weight: 700 !important; }" +

		        /* ===== CARD DESIGN ===== */
		        ".card { border-radius: 16px !important; box-shadow: 0 6px 18px rgba(0,0,0,0.4) !important; transition: 0.3s ease-in-out; }" +
		        ".card:hover { transform: translateY(-3px); }" +

		        /* ===== TEST TITLE ===== */
		        ".test-name { font-size: 19px !important; font-weight: 600 !important; }" +
		        ".node-name { font-weight: 500 !important; }" +

		        /* ===== STATUS BADGES ===== */
		        ".badge-success { background-color: #00e676 !important; }" +
		        ".badge-danger { background-color: #ff1744 !important; }" +
		        ".badge-warning { background-color: #ff9100 !important; }" +

		        /* ===== STATUS TEXT ===== */
		        ".status.pass { color: #00e676 !important; }" +
		        ".status.fail { color: #ff5252 !important; }" +
		        ".status.skip { color: #ffab00 !important; }" +

		        /* ===== SIDEBAR ===== */
		        ".side-nav { background-color: #1c1f26 !important; }" +
		        ".side-nav li a { font-weight: 500 !important; }" +

		        /* ===== TABLE SPACING ===== */
		        "table tr { line-height: 1.8 !important; }" +

		        /* ===== TIMESTAMP STYLE ===== */
		        ".timestamp { font-size: 13px !important; opacity: 0.8 !important; }"

		);




		extent = new ExtentReports();
		extent.attachReporter(spark);

		extent.setSystemInfo("Project", "IHSM University");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Executed By", System.getProperty("user.name"));
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Build Version", "2.0.0.48");
	}

	// ================= TEST START =================
	@Override
	public void onTestStart(ITestResult result) {

		String testName = result.getMethod().getDescription();
		
		// Fallback to method name if testName is not provided
		if (testName == null || testName.trim().isEmpty()) {
			testName = result.getMethod().getMethodName();
		}
		ExtentTest parent = extent.createTest(testName);
		parent.assignAuthor("Amit");
		parent.assignCategory(result.getMethod().getGroups());
		parent.assignDevice("Chrome");

		parentTest.set(parent);
		childTest.remove();
	}

	// ================= TEST PASS =================
	@Override
	public void onTestSuccess(ITestResult result) {

		ExtentTest test = parentTest.get();
		if (test != null) {
			test.pass(MarkupHelper.createLabel("TEST PASSED", ExtentColor.GREEN));
		}
	}

	// ================= TEST FAIL =================
	@Override
	public void onTestFailure(ITestResult result) {

		ExtentTest test = parentTest.get();

		if (test == null) {
			return;
		}

		test.fail(MarkupHelper.createLabel("TEST FAILED", ExtentColor.RED));

		test.fail(result.getThrowable());

		try {
			String path = BaseClass.captureScreenshot(result.getName());
			test.addScreenCaptureFromPath("../screenshots/" + new File(path).getName());
		} catch (Exception e) {
			test.warning("Screenshot capture failed");
		}
	}

	// ================= TEST SKIP =================
	
	@Override
	public void onTestSkipped(ITestResult result) {

	    ExtentTest test = parentTest.get();

	    if (test != null) {
	        test.skip(MarkupHelper.createLabel("TEST SKIPPED", ExtentColor.YELLOW));
	        if (result.getThrowable() != null) {
	            test.skip(result.getThrowable());
	        }
	    }
	}

	// ================= SUITE FINISH =================
	@Override
	public void onFinish(ITestContext context) {

		if (extent != null) {
			extent.createTest("Execution Summary")
	        .info("Total Tests: " + context.getAllTestMethods().length)
	        .info("Passed: " + context.getPassedTests().size())
	        .info("Failed: " + context.getFailedTests().size())
	        .info("Skipped: " + context.getSkippedTests().size());

			extent.flush();
		}
	}

	// ================= NODE CREATION =================
	public static ExtentTest createNode(String stepName) {

	    ExtentTest parent = parentTest.get();
	    if (parent == null) {
	        return null;
	    }

	    ExtentTest node = parent.createNode(stepName);
	    childTest.set(node);
	    return node;
	}


	public static ExtentTest getNode() {
	    return childTest.get() != null ? childTest.get() : parentTest.get();
	}


	public static ExtentTest getTest() {
		return parentTest.get();
	}
}
