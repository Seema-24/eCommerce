package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.bytebuddy.implementation.bytecode.Throw;
import pageObjects.HomePage;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class TC001_AccountRegistration extends BaseClass {
	
	@Test(groups = {"Regression", "Master"})
	public void verify_account_registration() throws InterruptedException {
		
		try {
			
			logs.info("****Starting Home page TC001_Account Registration .....");
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickregisterLink();
			
			logs.info("****Starting registration page .....");
			RegistrationPage reg = new RegistrationPage(driver);
			logs.info("****Provide registration details .....");
			reg.enterFirstName(randomString().toUpperCase());
			reg.enterLastName(randomString().toUpperCase());
			reg.enterEmail(randomString() + "@gmail.com");
			reg.enterTelephone(randomNumber());
			
			logs.debug("This is debug message");
			String password = randomAlphaNumeric();
			
			reg.enterPassword(password);
			reg.enterConfirmPassword(password);
			reg.clickAgreeCheckbox();
			reg.clickContinueBtn();
			Thread.sleep(2000);
			String confMsg  = reg.getConfirmationMsg();
			
			logs.info("****Validate expected message.....");
			if (confMsg.equals("Your Account Has Been Created!")) {
				logs.info("****Verify the message successfull .....");
				Assert.assertTrue(true);
			} else {
				logs.error("test Failed");
				logs.debug("Debug log");
				logs.warn("warning show ");
				logs.info("***Failed tC*****");
				Assert.assertTrue(false);
			}
			
		} catch (Exception e) {
			logs.error("This is error msg");
			Assert.fail();
		}
		logs.info("***Finished TC*****");
	}
	
}

