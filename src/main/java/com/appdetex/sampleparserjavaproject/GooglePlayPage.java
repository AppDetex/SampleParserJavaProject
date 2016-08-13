/**
 * 
 */
package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
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
	private String title, description, rating;

	/**
	 * @param string
	 */
	public GooglePlayPage(String string) {
		try {
			this.doc = Jsoup.parse(new URL(string), 4000);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to get: " + string);
			System.exit(1);
		}

		parseRating(doc.getElementsByClass("score"));
		parseTitle(doc.getElementsByClass("id-app-title"));
		parseDescription(doc.getElementsByClass("description"));
	}

	/**
	 * @param price
	 */
	private void parsePrice(Elements price) {

	}

	/**
	 * @param publisher
	 */
	private void parsePublisher(Elements publisher) {

	}

	/**
	 * @param score
	 */
	private void parseRating(Elements rating) {
		for (Element e : rating) {
			this.rating = new String("rating: " + e.text());
		}
	}

	/**
	 * @param title
	 */
	private void parseTitle(Elements title) {
		for (Element e : title) {
			this.title = new String("title: " + e.text());
		}
	}

	/**
	 * @param description
	 */
	private void parseDescription(Elements description) {
		for (Element e : description) {
			this.description = new String("description: " + e.text());
		}
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

}
