package com.webtests;


/*
 * Contains all the information related to HomePage Logic
 * Including methods and locators.
 */

public class HomePage {
	
	//Method to open the homePage of the passed URL
	public void navigateToHomePage() {
			Browser.goToUrl(Config.url);
			Browser.waitFor("linkText", "Sign In");
	}
	
	//To verify if the HomePage has been opened or not
	public boolean checkForHomePage() {
		return Browser.title().equals(Config.title);
	}
	
}
