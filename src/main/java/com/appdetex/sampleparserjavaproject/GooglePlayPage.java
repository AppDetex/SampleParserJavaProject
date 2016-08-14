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
	private String title, description, rating, price, publisher;

	/**
	 * Gets Google Play app page at given URL and parses it for data.
	 * @param string
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public GooglePlayPage(String url) throws MalformedURLException, IOException {
		this.doc = Jsoup.parse(new URL(url), 4000);
		parsePage();
	}
	
	/**
	 * Parses the page for certain fields.
	 */
	private void parsePage() {
		title = doc.select(".id-app-title").first().text();
		description = doc.select(".description").first().text();
		publisher  = doc.select("div.left-info a > *[itemprop=name]").text();
		price = doc.select(":containsOwn(Buy)").first().text().split(" ")[0];
		rating = doc.select(".score").first().text();
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
