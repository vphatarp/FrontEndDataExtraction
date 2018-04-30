package com.webtests;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


//This particular class has all the details needed for login
public class Config {
	
	static String url;
	static String username;
	static String password;
	static String title;
	static String position;
	static String location;
	private static String filePath = "Config.json";
	
	//The Static initialization block
	//Reads all the login credentials from a json file
	static {
		
		try(FileReader reader = new FileReader(filePath)){ //Open the file
			JSONParser jParser = new JSONParser();                    //Fire up the JSON object
			JSONObject jObject = (JSONObject) jParser.parse(reader); //Pass the reader to the JSON object
			url = (String) jObject.get("url");                       //Parse it to get all the elements 
			username = (String) jObject.get("username");
			password = (String) jObject.get("password");
			title = (String) jObject.get("title");
			position = (String) jObject.get("position");
			location = (String) jObject.get("location");
		} 
		//Handle all the exceptions
		catch (FileNotFoundException e) {
			System.out.println("Please Retry with the correct filename");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error message: "+e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Check the format of the file to be opened: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String getUrl() {
		return url;
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static String getPassword() {
		return password;
	}
}
