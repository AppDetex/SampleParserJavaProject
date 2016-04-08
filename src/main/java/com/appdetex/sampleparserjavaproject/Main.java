package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL and parse out certain
 * data, printing that data to stdout in a JSON format.
 */
public class Main {

	public static void main(String[] args) throws IOException {

		// Checks for user input url
		if (args.length == 1) {

			// Reads url from command line and sets it to the string HTML
			String HTML = args[0];

			// Prints all of the desired app data to stdout
			System.out.println(toString(AppTitle(HTML), AppDescription(HTML), AppPublisher(HTML), AppPrice(HTML),
					AppRating(HTML)));

		} else {
			System.out.println("Proper Usage: <url>");
			System.exit(0);

		}

	}

	/*
	 * Parses and searches the HTML string for the element containing the app
	 * title using jsoup keywords.
	 */
	public static String AppTitle(String HTML) {

		org.jsoup.nodes.Document doc;
		try {
			doc = Jsoup.connect(HTML).get();
			Element title = doc.select("div.id-app-title").first();
			String appTitle = title.text();

			return appTitle;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Parses and searches the HTML string for the element containing the app
	 * description.
	 */
	public static String AppDescription(String HTML) {

		org.jsoup.nodes.Document doc;
		try {
			doc = Jsoup.connect(HTML).get();
			Element desc = doc.select("div.show-more-content.text-body").first();
			String appDesc = desc.text();
			String LongDesc = appDesc;
			LongDesc = LongDesc.substring(0, 619);
			return LongDesc;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Parses and searches the HTML string for the element containing the app
	 * publisher by using a for-each loop to iterate through each span[itemprop].
	 */
	public static String AppPublisher(String HTML) {

		org.jsoup.nodes.Document doc;
		try {
			doc = Jsoup.connect(HTML).get();
			Elements pub = doc.select("span[itemprop]");
			for (Element e : pub) {
				String itemProp = e.attr("itemprop");
				String appPublisher = e.text();

				if (itemProp.equals("name")) {
					return appPublisher;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();

		}
		return null;
	}

	/*
	 * Parses and searches the HTML string for the element containing the app
	 * price by using a for-each loop to iterate through each meta[itemprop].
	 */
	public static String AppPrice(String HTML) {

		org.jsoup.nodes.Document doc;
		try {
			doc = Jsoup.connect(HTML).get();
			Elements price = doc.select("meta[itemprop]");
			for (Element e : price) {
				String itemProp = e.attr("itemprop");
				String appPrice = e.attr("content");

				if (itemProp.equals("price")) {
					return appPrice;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;

	}

	/*
	 * Parses and searches the HTML string for the element containing the app
	 * rating.
	 */
	public static String AppRating(String HTML) throws IOException {

		org.jsoup.nodes.Document doc = Jsoup.connect(HTML).get();
		Elements appRating = doc.select("div.score");
		String rating = appRating.text();
		return rating;

	}

	/*
	 * Takes in app title, description, publisher, price, and rating as
	 * parameters and returns this data in a json format.
	 */
	public static String toString(String AppTitle, String AppDescription, String AppPublisher, String AppPrice,
			String AppRating) {
		String appData = "{" + "\n" + "\t\"title\": " + "\"" + AppTitle + "\"," + "\n" + "\t\"description\": " + "\""
				+ AppDescription + "\"," + "\n" + "\t\"publisher\": " + "\"" + AppPublisher + "\"," + "\n"
				+ "\t\"price\": " + "\"" + AppPrice + "\"," + "\n" + "\t\"rating\": " + "\"" + AppRating + "\"" + "\n"
				+ "}";

		return appData;
	}
}
