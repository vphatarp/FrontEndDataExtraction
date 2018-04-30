package com.webtests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomePageTest {
	
	@Test
	public void testA() {
		assertTrue(Config.getUrl().equals("https://glassdoor.com/index.htm"));
	}
	
	@Test
	//BROWSING TO THE HOME PAGE
	public void testB(){
		HomePage h = Pages.homePage();
		h.navigateToHomePage();
		assertTrue(h.checkForHomePage());
	}
	
	@Test
	//CHECKING FOR SUCCESSFUL LOGIN
	public void testC(){
		LoginPage logPage = Pages.loginPage();
		logPage.bringUpLoginPage();
		logPage.submitCredentials();
		assertTrue(Browser.checkIfAlreadyLoggedIn());
	}
	
	@Test
	//PULL UP THE SEARCH RESULT PAGE
	public void testD() {
		SearchResults results = new SearchResults();
		results.enterSearchParameters();
		try {
			results.readGrid();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	@AfterClass
	public static void cleanup() {
		//Browser.close();
	}
}
