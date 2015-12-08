package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main
{
	
	public static void main(String args[]) throws IOException
    {     						
		// If incorect # of args, tell the user how to use properly, and kill program		
		if(args.length != 1)
		{
			System.out.println("Usage: Main <URL to Google Play Store>");
			System.exit(1);
		}
		
		// Save the URL passed via command line 
		String desiredURL = args[0];		
    	
    	Document doc = null; 
    	
    	try{
    	   doc = Jsoup.connect(desiredURL).get(); 
    	}catch (IOException e) {
    	   e.printStackTrace();
    	}   
    	
    	// Creates both the tester app, and the nullTester so I can see if the MyApp is working correctly
    	GooglePlayStoreObject testerApp = new GooglePlayStoreObject(); 
    	      
        // Gets the name of the App
        Elements titleHTML = doc.select("h1.document-title");
        
        // Gets the description of the App
        Elements descriptionHTML = doc.select("div[jsname]");        
        
        // Gets & prints the publisher of the app        
        Element publisherHTML = doc.select("span[itemprop]").first();
        
        // Gets the price of the app
        Elements priceHTML = doc.select("meta[itemprop]");
        
        // Gets the average rating of the app.
        Elements ratingHTML = doc.select("div.score");

                        
        // Sets all the values for the app
        testerApp.setAppTitle(getPassedTitle(titleHTML));
        testerApp.setAppDescription(getPassedDescription(descriptionHTML));
        testerApp.setAppPublisher(getPassedPublisher(publisherHTML));
        testerApp.setAppPrice(getPassedPrice(priceHTML)); 
        testerApp.setAppRating(getPassedRating(ratingHTML));
        
        testerApp.printJSON();
        
    } // End of main method
	
	
	/**
	 * Pass the Elements that contains the App's title, you will have the title of the app returned.	 
	 * 
	 * @param title - The Elements that contain the title ("h1.document-title")
	 * @return - Returns the app's title as a String.
	 */
	protected static String getPassedTitle(Elements title)
	{
        return title.text();
	}
	
	
	
	/**
	 * 	Pass the elements that contain the App's description and you will have the description 
	 *     of the App returned as a String in the previously specified format. 
	 * 
	 * @param description - the elements that contain the app's description ("div[jsname]")
	 * @return - the description of the app as a String in the format specified in the initial project
	 */
	protected static String getPassedDescription(Elements description)
	{		
        Element HTMLdesc = null;

        // Loop through every passed element looking for the element containing the attribute jsname = "C4s9Ed" (description)
	    for (Element link : description) 
        {
        	String jsname = link.attr("jsname");
        	
        	if( jsname.equals("C4s9Ed"))
        	{                		
        		HTMLdesc = link;        		
        		break;
        	}        	        	        	
        }	    
	    
	    // Saves the HTML code as a String to be manipulated via removeAll()
	    String HTMLstring = HTMLdesc.toString();
		
	    // Cuts the padded <p></p>'s that are at the end of some descriptions
		HTMLstring = HTMLstring.replaceAll("<p></p>", "");
		
		// Replaces all the </p>'s throughtout the program with "" (nothing)
		HTMLstring = HTMLstring.replaceAll("</p>", "");
		
		// Replaces all the <br>'s throughtout the program with "" (nothing)
		HTMLstring = HTMLstring.replaceAll("<br>", "");
				
		// Finds " <p>" in the code and puts in "_brSplitPos_" to be later switched to just <br/>
		HTMLstring = HTMLstring.replaceAll(" <p>", "_brSplitPos_");								
		
		// Parses the new HTML code (as a string)
		Document docH1 = Jsoup.parse(HTMLstring);		

		// Sets the new Description without the miscellaneous remaining HTML marks
		String formatedDescription = docH1.text();

		// Replaces the placeholder marks with the actual <br/>'s
		formatedDescription = formatedDescription.replace("_brSplitPos_", "<br/>");		
		
		// <br/>'s are padded with a " " for some reason, so remove those to match given requirement. 
		formatedDescription = formatedDescription.replace(" <br/>", "<br/>");
		
		// Return the new formated App Description
		return formatedDescription;
	}
	
	
	
	/**
	 * Pass the element that contains the publisher, and get the name of the publisher back as a String.
	 * 
	 * @param publisher - The element that contains the publisher. ex: ("span[itemprop]").first()	 
	 * @return - Returned with the Publisher who is offering the App on the GooglePlayStore
	 */
	protected static String getPassedPublisher(Element publisher) 	
	{
		return publisher.text();
	}
	
	
	
	/**
	 * Pass in the Elements that contain the price of the App, and you will have the price of the app returned.
	 * 
	 * @param price - Elements that contain price. ("meta[itemprop]")
	 * @return - Returns the price of the app as a String. If the app is free $0.00 will be returned. Otherwise, the price 
	 *           is pulled from the GooglePlayStore HTML code.
	 */
	protected static String getPassedPrice(Elements price) 
	{
		// The cost of the app that will eventually be returned.
		String appCost = null;
		
		for (Element link : price) 
        {
			// Saves the item's property and it's content in the respective strings
        	String itemProp = link.attr("itemprop");
        	String content = link.attr("content");

        	// Checks to see if the property is "price"
        	// If true, then content will contain the price of the app
        	if(itemProp.equals("price"))
        	{
        		// Saves the cost of the app into the variable to be returned
        		appCost = content;
        		
        		// If an app is free, it will only print 0, so this is added to change it to $0.00 for nicer formating
        		if(appCost.equals("0"))
        		{
        			appCost = "$0.00";
        		}
        		break;
        	}
        }
		
		return appCost;
	}

	
	
	/**
	 * Pass the Elements that contain the rating and have the rating returned as a String.
	 * 
	 * @param rating - The elements that contain the App's Average Rating
	 * @return - The average rating of the app as a String.
	 */
	protected static String getPassedRating(Elements rating)
	{
	    return rating.text();
	}	
	
} // End of class Main
