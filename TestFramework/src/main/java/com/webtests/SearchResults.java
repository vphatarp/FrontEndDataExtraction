package com.webtests;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
public class SearchResults {
	private static StringBuilder html = new StringBuilder();
	
	//Bring up the search results page by entering the location and job title
	public void enterSearchParameters() {
		WebElement temp = null;
		//Enter the job designation
		Browser.waitFor("sc.keyword", "id");
		temp = Browser.findByType("sc.keyword", "id");
		temp.sendKeys(Config.position);
		
		//Enter in the job location
		temp = Browser.findByType("sc.location", "id");
		temp.clear();
		temp.sendKeys(Config.location);
		
		//Click on the submit button
		temp = Browser.findByType("//*[@id=\"HeroSearchButton\"]", "xpath");
		temp.click();
	}
	
	//The method below will navigate the results page by page till the search hits the last one
	public boolean traversePages() throws InterruptedException {
		Thread.sleep(5000);
		WebElement pageNav = Browser.findByType("FooterPageNav", "id");
		WebElement nextPage = pageNav.findElement(By.className("next"));//Navigates to the next page number
		WebElement currentLastPage = pageNav.findElement(By.xpath("/html/body/div[3]/div/div/div/div[2]/div/div[2]/section/article/div/div[3]/div[2]/div/div/ul/li[6]"));
		int lastPage =  Integer.parseInt(currentLastPage.findElement(By.tagName("a")).getText());
		System.out.printf("The last current page is %d", lastPage);
		//The search is made to scan atleast 30 pages before it stops running.
		if((nextPage.findElement(By.tagName("span")).getAttribute("class").equals("disabled")) || (lastPage == 32)) {
			System.out.println("There is nothing more to be displayed!");
			return false;
		}
		else {
			nextPage.click();
			return true;
		}
	}
	
	//This method reads all the postings on a particular page and filters them out based on keywords
	public void readGrid() throws InterruptedException{
		int count = 0;
		//Defining all the keywords 
		String [] keywords = {"Automation", "QA", "Quality Assurance", "Quality Analyst"};
		Boolean alreadyVisited = false;
		
		do {
			Browser.waitFor("FooterPageNav", "id");
			WebElement outerGrid = Browser.findByType("//*[@id=\"MainCol\"]/div/ul", "xpath");
			List<WebElement> innerGrids = outerGrid.findElements(By.tagName("li"));
			for(WebElement innerGrid: innerGrids) {
				WebElement innerdiv = innerGrid.findElements(By.tagName("div")).get(1);
				WebElement linkName = innerdiv.findElement(By.tagName("a"));
				linkName.click();
				if(!alreadyVisited) {
					//A pop up shows up when the site is first clicked. This is handled by the method below
					Browser.handlePopUp();
					alreadyVisited = true;
				}
				if(stringContainsItemFromList(linkName.getText(), keywords)) 
				{
					//Create an HTML page based on the results of filtering
					htmlBuilder(linkName.getText(), linkName.getAttribute("href"));
					count++;
				}
			}
		}while(traversePages());
		System.out.printf("Reduced down to %d jobs", count);
		transferToFile();
	}
	
	
	//Create the HTML page
	public void htmlBuilder(String positionName, String link) {
		html.append("<a href=\""+link+"\">"+positionName+"</a></br>");
	}
	
	//Method to post all the result search to file
	public void transferToFile(){
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("NewJerseyAutomation.html"))){
			bw.write(html.toString());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	
	//Checking based on the keywords
	public boolean stringContainsItemFromList(String inputStr, String[] items) {
	    return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
	}

}
