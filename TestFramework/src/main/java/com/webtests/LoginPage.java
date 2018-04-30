package com.webtests;

import org.openqa.selenium.WebElement;
public class LoginPage {
	
	//Bringing up the login page
	public void bringUpLoginPage() {
		Browser.navigateByLinkText("Sign In");
	}
	
	//Enter Username and Password
	public void submitCredentials() {
		WebElement temp = null;
		temp = Browser.findByType("username", "name");
		temp.clear();
		temp.sendKeys(Config.username);
		temp = Browser.findByType("password", "name");
		temp.clear();
		temp.sendKeys(Config.password);
		temp = Browser.findByType("/html/body/div[7]/div/div/div[2]/div[2]/div[2]/div[2]/form/button", "xpath");
		temp.click();
		Browser.waitFor("sc.keyword", "id");
	}

}
