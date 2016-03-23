package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parser uses Jsoup to retrieve a provided url and parses out information from
 * the Google Play Store.
 * 
 * @author csilkwor
 *
 */
public class Parser {
	private String title;
	private String description;
	private String publisher;
	private String price;
	private double rating;
	private Document document;

	private Map<String, Object> object;
	private Gson gson;

	/**
	 * Default Constructor
	 */
	public Parser() {

	}
	
	/**
	 * Takes a URL and parses application title, description, publisher, price,
	 * and rating from the page.
	 * 
	 * @param url
	 *            - desired URL to fetch data from
	 */
	public void parseInfo(String url) {
		try {
			document = Jsoup.connect(url).get();

			title = document.select("h1.document-title").text();
			publisher = document.select("span[itemprop=name]").text();
			price = document.select("meta[itemprop=price]").get(0).attr("content");
			rating = Double.parseDouble(document.select("div[class=score").text());

			String descriptionHTML = document.select("div[itemprop=description]").toString();
			// Remove all empty <p> tags from the description's HTML
			descriptionHTML = descriptionHTML.replaceAll("<p></p>", "");
			// Parse the description's HTML and insert <br/> tags for
			// readability
			descriptionHTML = descriptionHTML.replaceAll("<p>", "_brTag_");
			Document descriptionDoc = Jsoup.parse(descriptionHTML);
			description = descriptionDoc.text();
			description = description.replace(" _brTag_", "<br/>");

		} catch (IOException e) {
			printUsage();
			e.printStackTrace();
			System.exit(1);
		}
	} // end parseInfo

	/**
	 * Encodes the data into a JSON formatted string
	 * 
	 * @return JSON formatted string
	 */
	public String jsonEncode() {
		// A LinkedHasMap ensures the order of the data going in is retained.
		object = new LinkedHashMap<String, Object>();
		gson = new GsonBuilder().setPrettyPrinting().create();

		object.put("title", title);
		object.put("description", description);
		object.put("publisher", publisher);
		object.put("price", price);
		object.put("rating", rating);

		// Return info in JSON format, UTF-8 encoded
		return gson.toJson(object);
	} // end jsonEncode

	/**
	 * Prints the proper usage statement to the console
	 */
	public void printUsage() {
		System.out.println("Usage: Main [https://play.google.com/store/your/url]");
	} // end printUsage
} // End Parser class
