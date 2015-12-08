// File: GoooglePlayStoreObject.java
// Author: Jack K. Seaton (310) 896-6050
// Description: Used in tandem with Main.java in order to create objects 
//				with information from the Google Play Store, as well as to access the information
// 				stored in these objects and print out their information in a JSON format.


package com.appdetex.sampleparserjavaproject;

public class GooglePlayStoreObject 
{
	// The variables to 
	private String appTitle, appDescription, appPublisher, appPrice, appRating;
	
	/**
	 *  Called to create each GooglePlayStoreObject and sets all the app's traits
	 *  	to null by default.
	 */
	GooglePlayStoreObject()
	{
		appTitle = null;
		appDescription = null;
		appPublisher = null;
		appPrice = null;		
		appRating = null;
	}
	
	// Start of Setters --------------------------------------------------
	
	/**
	 * Pass in a String to set the title of the app.
	 * 
	 * @param title - The String to represent the App's title.
	 */
	protected void setAppTitle(String title)
	{
		appTitle = title;		
	}
	
	/**
	 * Pass in a String to set the description of the app. 
	 * 
	 * @param description - The String to represent the App's description.
	 */
	protected void setAppDescription(String description)
	{
		appDescription = description;		
	}
	
	/**
	 * Pass in a String to set the publisher of the app.
	 * 
	 * @param publisher - The String to represent the App's publisher.
	 */
	protected void setAppPublisher(String publisher) 
	{
		appPublisher = publisher;
	}
	
	/**
	 * Pass in a String to set the price of the app.
	 * 
	 * @param price - The String to represent the App's price.
	 */
	protected void setAppPrice(String price) 
	{
		appPrice = price;
	}
	
	/**
	 * Pass in a String to set the rating of the app.
	 * 
	 * @param rating - The String to represent the App's rating.
	 */
	protected void setAppRating(String rating) 
	{
		appRating = rating;
	}
	
	// Start of Getters --------------------------------------------------

	/**
	 * When called it will return the title of the app that is using the function
	 * 
	 * @return - The App's title as a String.
	 */
	protected String getAppTitle()
	{
		return appTitle;
	}
	
	/**
	 * When called it will return the description of the app that is using the function.
	 * 
	 * @return - The App's description as a String.
	 */
	protected String getAppDescription()
	{
		return appDescription;
	}
	
	/**
	 * When called it will return the publisher of the app that is using the function.
	 * 
	 * @return - The App's publisher as a String.
	 */
	protected String getAppPublisher()
	{
		return appPublisher;
	}	
	
	/**
	 * When called it will return the price of the app that is using the function.
	 * 
	 * @return - The App's price as a String.
	 */
	protected String getAppPrice()
	{
		return appPrice;
	}
	
	/**
	 * When called it will return the rating of the app that is using the function.
	 * 
	 * @return - The App's rating as a String.
	 */
	protected String getAppRating()
	{
		return appRating;
	}
	

	/**
	 * 	Prints out the information for the app in the specified JSON format.
	 * 
	 * 	NOTE: By default each of these fields are set to null, however, null 
	 * 	should not print out as that would entail an unsuccessful attempt at finding 
	 * 	the app's information.
	 */
	protected void printJSON()
	{
		System.out.println("{");
		System.out.println("\t\"title\": " + "\"" + getAppTitle() + "\"" + ",");
		System.out.println("\t\"description\": " + "\"" + getAppDescription() + "\"" + ",");
		System.out.println("\t\"publisher\": " + "\"" + getAppPublisher() + "\"" + ",");
		System.out.println("\t\"price\": " + "\"" + getAppPrice() + "\"" + ",");
		System.out.println("\t\"rating\": " + getAppRating() );
		System.out.println("}");
	}
	
}
