package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataDriven") //getting data proviers from different class
	public void verify_loginDDT(String email, String password, String exp) {
		
		logs.info("****Starting TC003 Login Test with Data provider: "+ email +" ************");
		try {
			
			//Home Page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLoginLink();
			
			//Login Page
			LoginPage lp = new LoginPage(driver);
			lp.enterEmail(email);
			lp.enterPassword(password);
			lp.clickLoginBtn();
			
			//MyAccount
			MyAccount myAcc = new MyAccount(driver);
			boolean targetPage = myAcc.isMyPageExist();
			
			if (exp.equalsIgnoreCase("Valid")) { // Positive condition
				if(targetPage == true) {
					myAcc.clickLogoutLink();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			if (exp.equalsIgnoreCase("Invalid")) { // Negative condition
				if(targetPage == true) {
					myAcc.clickLogoutLink();
					Assert.assertTrue(false);
				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			logs.error("got error");
			Assert.fail();
		}
		
		logs.info("********Finished the TC003***********");
		
		
		
		
	}
}
