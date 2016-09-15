package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Accepts a URL for a Google Play app page, and returns relevant data for the
 * app.
 * 
 * @author Sam McMahon
 */
public class GooglePlayPage {
	private String title, description, rating, price, publisher;

	/**
	 * Gets Google Play app page at given URL, parses it for data from certain
	 * fields.
	 * 
	 * @param string
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public GooglePlayPage(String url) throws MalformedURLException, IOException {
		Document doc = Jsoup.parse(new URL(url), 4000);
		title = doc.select(".id-app-title").first().text();

		// Build description string from just first paragraph from page
		description = doc.select("div[jsname=C4s9Ed]").toString();
		// For the case that for some reason description == null
		if (description != null) {
			description = description.substring(0, description.indexOf("<p>"));
			description = description.substring(description.lastIndexOf('>') + 1, description.length() - 1);
			description = description.replace('\n', ' ').trim();
		}

		publisher = doc.select("div.left-info a > *[itemprop=name]").text();
		
		// Build price
		price = doc.select("[itemprop=price]").toString();
		// For the case that for some reason price == null
		if (price != null)
			price = price.substring(price.indexOf("\"") + 1, price.indexOf("itemprop") - 2);
		
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// Traditional Java toString()
		StringBuilder builder = new StringBuilder();
		builder.append("GooglePlayPage [title=");
		builder.append(title);
		builder.append(", description=");
		builder.append(description);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", price=");
		builder.append(price);
		builder.append(", publisher=");
		builder.append(publisher);
		builder.append("]");
		return builder.toString();
	}

}
