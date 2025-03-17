package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage {
	
	//calling another class constructor by super 
	public RegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txt_firstName;
	
	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txt_lastName;
	
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txt_email;
	
	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txt_telephone;
	
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txt_password;
	
	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txt_confirmpassword;
	
	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkbox_policy;
	
	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btn_continue;
	
	@FindBy(xpath = "//div[@id='content']/h1")
	WebElement msg_confirmation;
	
	
	public void enterFirstName(String firstName) {
		txt_firstName.sendKeys(firstName);
	}
	
	public void enterLastName(String lastName) {
		txt_lastName.sendKeys(lastName);
	}
	
	public void enterEmail(String email) {
		txt_email.sendKeys(email);
	}
	
	public void enterTelephone(String phone) {
		txt_telephone.sendKeys(phone);
	}
	
	public void enterPassword(String password) {
		txt_password.sendKeys(password);
	}
	
	public void enterConfirmPassword(String confPassword) {
		txt_confirmpassword.sendKeys(confPassword);
	}
	
	public void clickAgreeCheckbox() {
		chkbox_policy.click();
	}
	
	public void clickContinueBtn() {
		btn_continue.click();
	}
	
	public String getConfirmationMsg() {
		try {
			return msg_confirmation.getText(); //Your Account Has Been Created!
		} catch (Exception e) {
		 return e.getMessage();
		}
	}
}
