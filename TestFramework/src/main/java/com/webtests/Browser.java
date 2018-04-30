package com.webtests;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;

public final class Browser {
	private static WebDriver driver = new FirefoxDriver();
	private static WebDriverWait wait = new WebDriverWait(driver, 20);
	
	private Browser() {
	}
	
	//Browse to a URL
	public static void goToUrl(String url) {
		try {
			driver.get(url);
			driver.findElement(By.linkText("Sign In"));
		}
		catch(Exception e) {
			System.out.println("Exception: "+ e.getMessage());
		}
	}
	
	
	//Browse to a particular link
	public static void navigateByLinkText(String linkText) {
		driver.findElement(By.linkText(linkText)).click();;
	}
	
	//Abstraction for finding elements based on attributes
	public static WebElement findByType(String elementName, String elementType) {
		WebElement temp = null;
		switch(elementType) {
		case "name":
			temp = driver.findElement(By.name(elementName));
			break;
		case "xpath":
			temp = driver.findElement(By.xpath(elementName));
			break;
		case "id":
			temp = driver.findElement(By.id(elementName));
			break;
		}
		return temp;
	}
	
	//Abstraction for finding elements based on attributes
	public static void waitFor(String elementName, String elementType) {
		
		switch (elementType){
			case "title":
				wait.until(ExpectedConditions.titleContains(elementName));
				break;
			case "linkText":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(elementName)));
				System.out.println("This worked!");
				break;
			case "id":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementName)));
				break;
			case "xpath":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementName)));
		}	
	}
	
	//Get the title of the page for verification purposes
	public static String title() {
		return driver.getTitle();
	}
	
	//Method to check if we are already logged in or not
	public static boolean checkIfAlreadyLoggedIn() {
		boolean loggedIn = true;
		Actions builder = new Actions(driver);
		WebElement hoverElement = driver.findElement(By.xpath("/html/body/header/nav/div[2]/ul[3]/li[2]/a"));
		builder.moveToElement(hoverElement).perform();
		try {
			waitFor("/html/body/header/nav/div[2]/ul[3]/li[2]/div/ul[3]/li[9]/form/input[1]", "xpath");
		}
		catch(TimeoutException e) {
			System.out.println("Not yet logged in!");
			loggedIn = false;
		}
		if(loggedIn) {
			builder.moveByOffset(30, 250).perform();;
			System.out.println("Logged In!");
		}
		return loggedIn;
	}
	
	public static void close() {
		driver.close();
	}
	
	
	//How to handle pop-ups
	//The script was not recognizing pop-ups as 2 windows
	//Hence had to find an alternative way of handling the pop-ups
	//Code using window handles has been commented out
	//Might use it back in future
	public static void handlePopUp() {
		String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
		String subWindowHandler = null;
		WebElement modalBox = driver.findElement(By.id("JAModal"));
		System.out.println(modalBox.getText());
		WebElement clickButton = modalBox.findElement(By.className("mfp-close"));
		clickButton.click();
		/*
		Set<String> handles = driver.getWindowHandles();// get all window handles
		handles.remove(parentWindowHandler);
		subWindowHandler = handles.iterator().next();
		driver.switchTo().window(subWindowHandler); // switch to popup window
		WebElement closeButton = driver.findElement(By.xpath("/html/body/div[10]/div/div/div[2]/div/button"));
		closeButton.click();
		driver.switchTo().window(parentWindowHandler);
		Set<String> handles = driver.getWindowHandles();// get all window handles
		for(String s: handles) {
			System.out.println(s);
			System.out.println("\n\n\n\n");
		}
		handles.remove(parentWindowHandler);
		System.out.println("Do I enter this region");
		subWindowHandler = handles.iterator().next();
		driver.switchTo().window(subWindowHandler); // switch to popup window
		WebElement closeButton = driver.findElement(By.xpath("/html/body/div[10]/div/div/div[2]/div/button"));
		closeButton.click();
		driver.switchTo().window(parentWindowHandler);
		*/
	}
	
	public static boolean multiplePopUpCondition() {
		return driver.getWindowHandles().size() != 1;
	}
}
