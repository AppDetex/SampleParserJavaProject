package com.appdetex.sampleparserjavaproject;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {
	
	Document doc;
	
	/**
	 * Main(): Default constructor
	 * 
	 * @param doc
	 */
	public Main (Document doc) {
		this.doc = doc;
	}
	
	
	/**
	 *	getTitle(): grabs the title from the app page.
	 *
	 * @return title
	 */
	public String getTitle() 
	{
		Element app_title;
		String title = "";
		
		//Extract title of the app from the page (NOT the title tag)
		app_title = doc.select(".id-app-title").first();
		
		//Check if element has data
		if (app_title == null) {
			return title;
		}
		
		//Get the text from the element
		if (app_title.hasText()) {
			title = (app_title.text());
		}
		
		return title;
	}
	
	
	/**
	 *	getDescription(): grabs the first paragraph from the specific div that contains the app description
	 *
	 * @return first_paragraph
	 */
	public String getDescription() 
	{
		Element app_description;
		String first_paragraph = "";
		
		//Extract description of app from the page
		app_description = doc.select("[jsname=C4s9Ed]").first();
		
		//Check if element has data
		if (app_description == null) {
			return first_paragraph;
		}
		
		//Store all text from first part of the div(getting the 0th textnodes grabs the text before the <p> tag)
		//This grabs the first paragraph form the description
		if (app_description.hasText()) {
			first_paragraph = (app_description.textNodes().get(0).text());
		}
		
		return first_paragraph;
	}
	
	
	/**
	 *	getPublisher(): grabs the publisher from the app page
	 *
	 * @return publisher
	 */
	public String getPublisher() 
	{
		Elements meta_infos;
		String publisher = "No publisher";
		
		//Extract details section meta_info divs in which one contains the publisher of app from the page
		meta_infos = doc.select(".details-section.metadata").first().select(".details-section-contents").select(".meta-info");

		//Check if element has data
		if (meta_infos == null) {
			return publisher;
		}
		
		//Loop through each meta-info div and look for the "Offered By" title
		for(Element meta_info : meta_infos) {
			if(meta_info.select(".title").text().equals("Offered By")) {
				publisher = meta_info.select(".content").text();
			}
		}
		return publisher;
	}
	
	
	/**
	 *	getPrice(): grabs the price from the app page
	 *
	 * @return Price
	 */
	public String getPrice() 
	{
		Element app_price_container;
		Elements spans;
		String price = "";
		
		//Extract container div that holds the price of the app
		app_price_container = doc.select(".details-actions-right").first();
		
		//Check if element has data
		if (app_price_container == null) {
			return price;
		}
		
		spans = app_price_container.select("span");
		
		//Only the span containing the price has text, extract that.
		for(Element span: spans) {
			if (span.hasText()) {
				price = span.text();
			}
		}
		
		//parse price (delete " Buy")
		String delims = "[ ]";
		String[] tokens = price.split(delims);
		price = tokens[0];
		
		if (price.equals("Install")) {
			price = "Free";
		}
		
		return price;
	}
	
	
	/**
	 *	getRating(): grabs the rating from the app page
	 *
	 * @return rating
	 */
	public double getRating() 
	{
		Element app_score;
		double rating = 0;
		
		//Select first class that is named score in the app page
		app_score = doc.select(".score").first();
		
		//Check if element has data
		if (app_score == null) {
			return rating;
		}
		
		//Get the text from the element
		if (app_score.hasText()) {
			rating = Double.parseDouble((app_score.text())); //Convert rating string to double
		}
		
		return rating;
	}
	
	
	/**
	 *	printJSON(): prints the results in JSON format using googles Gson library
	 *
	 */
	public void printJSON(String title, String description, String publisher, String price, double rating) 
	{
		//Convert rating into double
		
		//Create new object that contains all of the parsed data
		AppInfo info = new AppInfo(title, description, publisher, price, rating);
		
		//Create Gson object to format the data
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		//Format data into JSON format and store
		String jsonOutput2 = gson.toJson(info);
		
		//print to stdout JSON string
		System.out.println(jsonOutput2);
	}
	
	
	/**
	 * Main method
	 * 
	 * @param args
	 */
    public static void main( String[] args )
    { 
    	Main main;
    	Document doc = null;
    	String URL = "";
    	String title = "";
    	String description = "";
    	String publisher = "";
    	String price = "";
    	double rating = 0;
    	
    	//Check for command line args
    	if (args.length <= 0 || args.length> 1) {
    		System.err.println("Usage: Main <URL for Google Play app page>");
    		return;
    	}
    	else {
    		URL = args[0];
    	}
    	
    	//Load and parse the URL 
    	try {	
			doc = Jsoup.connect(URL).get();
    	}
		catch (IOException e) {
			System.out.println("Could not connect to specified URL. See stack trace below:");
			e.printStackTrace();
			return;
		}
		
    	main = new Main(doc);
			
		title = main.getTitle();
		description = main.getDescription();
		publisher = main.getPublisher();
		price = main.getPrice();
		rating = main.getRating();
		
		main.printJSON(title, description, publisher, price, rating);
    }

}
