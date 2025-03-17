package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.activation.DataSource;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;

	public void onStart(ITestContext testContext) {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // timestamp
		repName = "Test-Report-" + timestamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // specify location of the report

		sparkReporter.config().setDocumentTitle("OpenCart Automation Report"); // Title of report
		sparkReporter.config().setReportName("OpenCart functional Testing"); // Name of report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "openCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);

		List<String> incGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!incGroups.isEmpty()) {
			extent.setSystemInfo("Groups", incGroups.toString());
		}

	}

	public void onTestSuccess(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display group in report
		test.log(Status.PASS, result.getName() + " got successfully executed.");

	}

	public void onTestFailure(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + " got failed.");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {

			String imgPath = new BaseClass().captureScreenshot(result.getName());
			test.addScreenCaptureFromPath(imgPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {

		extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " got skipped.");
		test.log(Status.INFO, result.getThrowable().getMessage());

	}

	public void onFinish(ITestContext testContext) {
		extent.flush();

		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * String userEmail = ""; String userPassword = ""; String receiverEmail = "";
		 * 
		 * // Attach report to Email try { URL url = new URL("file:///" +
		 * System.getProperty("user.dir") + "\\reports\\" + repName); // Create email
		 * message ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
		 * email.setHostName("smtp.googlemail.com"); // Particular Google
		 * email.setSmtpPort(465); email.setAuthenticator(new
		 * DefaultAuthenticator(userEmail, userPassword)); email.setSSLOnConnect(true);
		 * email.setFrom(userEmail); // Sender email.setSubject("Test Results");
		 * email.setMsg("Please find the attached reports"); email.addTo(receiverEmail);
		 * // Receiver email.attach(url, "extent report", "Please check the report...");
		 * email.send();
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}

}
