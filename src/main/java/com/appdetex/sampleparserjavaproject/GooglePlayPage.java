package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.*;
import org.jsoup.nodes.*;

/**
 * Accepts a URL for a Google Play app page, and returns relevant data for the app.
 * @author Sam McMahon
 */
public class GooglePlayPage {
	private Document doc;
	private String title, description, rating, price, publisher, json;

	/**
	 * Gets Google Play app page at given URL, parses it for data, and builds JSON representation of page.
	 * @param string
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public GooglePlayPage(String url) throws MalformedURLException, IOException {
		this.doc = Jsoup.parse(new URL(url), 4000);
		parsePage();
		buildJson();
	}
	
	/**
	 * Parses the page for certain fields.
	 */
	private void parsePage() {
		title = doc.select(".id-app-title").first().text();
		
		// Build description string from just first paragraph from page
		description = doc.select("div[jsname=C4s9Ed]").toString();
		description = description.substring(0, description.indexOf("<p>"));
		description = description.substring(description.lastIndexOf('>') + 1, description.length()-1);
		description = description.replace('\n', ' ').trim();
		
		publisher  = doc.select("div.left-info a > *[itemprop=name]").text();
		price = doc.select(":containsOwn(Buy)").first().text().split(" ")[0];
		rating = doc.select(".score").first().text();
	}
	
	/**
	 * Builds the JSON string.
	 */
	private void buildJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("\t\"title\": " + title + "\n");
		sb.append("\t\"description\": " + description + "\n");
		sb.append("\t\"publisher\": " + publisher + "\n");
		sb.append("\t\"price\": " + price + "\n");
		sb.append("\t\"rating\": " + rating + "\n");
		sb.append("}");
		json = sb.toString();
	}
	
	/**
	 * @return JSON representation of page
	 */
	public String getJson() {
		return json;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	


}
