/**
 * 
 */
package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author sammcmahon
 *
 */
public class GooglePlayPage {
	private Document doc;
	private String title, description, rating, price, publisher;

	/**
	 * @param string
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public GooglePlayPage(String string) throws MalformedURLException, IOException {
		this.doc = Jsoup.parse(new URL(string), 4000);
		parsePage();
	}
	
	/**
	 * 
	 */
	private void parsePage() {
		rating = doc.getElementsByClass("score").first().text();
		title = doc.getElementsByClass("id-app-title").first().text();
		description = doc.getElementsByClass("description").first().text();
		// Ugly line of code, I know
		price = doc.select(":containsOwn(Buy)").first().text().split(" ")[0];
		publisher = doc.select("span.author-name").last().text();
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
