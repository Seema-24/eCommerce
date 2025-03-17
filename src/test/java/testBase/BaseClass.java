package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Properties prop;
	public Logger logs; // log4j

	@BeforeClass(groups = { "Master", "Regression", "Sanity" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String browse) throws IOException {
		
		logs = LogManager.getLogger(this.getClass());
		FileReader file = new FileReader("./src//test//resources//config.properties");
		prop = new Properties();
		prop.load(file);
		

		if (prop.getProperty("execution_env").equalsIgnoreCase("remote")) {

			DesiredCapabilities cap = new DesiredCapabilities();

			// OS
			if (os.equalsIgnoreCase("windows")) {

				cap.setPlatform(Platform.WIN11);

			} else if (os.equalsIgnoreCase("mac")) {

				cap.setPlatform(Platform.MAC);

			} else if (os.equalsIgnoreCase("linux")) {

				cap.setPlatform(Platform.LINUX);

			} else {
				System.err.println("Not matching OS");
				return; // exit from entire execution
			}

			// Browser
			switch (browse.toLowerCase()) {
				case "chrome":
					cap.setBrowserName("chrome");
					break;
					
				case "edge":
					cap.setBrowserName("MicrosoftEdge");
					break;
	
				case "firefox":
					cap.setBrowserName("firefox");
					break;
				default:
					System.err.println("Invalid Browser name");
					return; // exit from entire execution
			}
			
			driver = new RemoteWebDriver(new URL(prop.getProperty("hubUrl")), cap);

		} else if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {

			switch (browse.toLowerCase()) {
				case "chrome":
					driver = new ChromeDriver();
					break;
				case "edge":
					driver = new EdgeDriver();
					break;
	
				case "firefox":
					driver = new FirefoxDriver();
					break;
				default:
					System.err.println("Invalid Browser name");
					return; // exit from entire execution
			}

		}

		driver.get(prop.getProperty("appUrl")); // reading url forom properties file
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterClass(groups = { "Master", "Regression", "Sanity" })
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {
		String randomString = RandomStringUtils.randomAlphabetic(5);
		return randomString;
	}

	public String randomNumber() {
		String number = RandomStringUtils.randomNumeric(10);

		return number;

	}

	public String randomAlphaNumeric() {
		String alpha = RandomStringUtils.randomAlphabetic(5);
		String number = RandomStringUtils.randomNumeric(5);

		return alpha + "@" + number;
	}

	public String captureScreenshot(String testName) {

		String timestamp = new SimpleDateFormat("yyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;

		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timestamp
				+ ".png";
		File targetFile = new File(targetFilePath);

		srcFile.renameTo(targetFile);

		return targetFilePath;
	}

}
