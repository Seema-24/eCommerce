package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups = {"Sanity", "Master"})
	public void verify_account_registration(){
		
		logs.info("****Starting TC_002 Login Test .....");
		try {
			
			//Home Page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLoginLink();
			
			//Login Page
			LoginPage lp = new LoginPage(driver);
			lp.enterEmail(prop.getProperty("email"));
			lp.enterPassword(prop.getProperty("password"));
			lp.clickLoginBtn();
			logs.info("****Login Successfully***");
		
			//MyAccount
			MyAccount myAcc = new MyAccount(driver);
			boolean msg = myAcc.isMyPageExist();
			Assert.assertEquals(msg, true);
			myAcc.clickLogoutLink();
			
		} catch (Exception e) {
			logs.error("Got some Error...");
			Assert.fail();
		}		
		
		logs.info("***Finished TC*****");
	}

}
