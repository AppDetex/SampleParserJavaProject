
package com.appdetex.sampleparserjavaproject;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 * 
 * Main Java Class This class will use Jsoup to retrieve a provided URL and
 * parse out certain data, printing that data to stdout in a JSON format.
 */
public class Main {
	public final static int ERROR_EXIT_CODE = 1;
	public static final String DESCRIPTION_DIV = "div[jsname=C4s9Ed]";
	public static final String RATING_DIV = "div[class=score]";
	public static final String PUBLISHER_DIV = "span[itemprop=name]";
	public static final String PRICE_META = "meta[itemprop=price]";
	private static String singleLineREGX = "[\r\n]+"; // regex to remove return
														// carriage & new line

	/**
	 * main method
	 * 
	 * @param args
	 *            - URL of the app's web-page
	 */
	public static void main(String[] args) {

		StringBuilder builder = new StringBuilder("");
		if (args.length < 1) {
			System.err.println("Usage: java parse <URL>");
			System.exit(ERROR_EXIT_CODE);
		}

		try {
				String appURL = args[0];
				Document doc = Jsoup.connect(appURL).get();

				builder.append("{\n \"title\": " + "\"" + formatTitle(doc.title()) + "\",\n");

				Elements descriptionBody = doc.select(DESCRIPTION_DIV);

				String cleanBody = Jsoup.clean(descriptionBody.html(), "", Whitelist.basic().removeTags("p").removeTags("a"));

				builder.append(" \"description\": " + "\"" + deepClean(cleanBody) + "\",\n");

				String appPublisher = doc.select(PUBLISHER_DIV).text();
				builder.append(" \"publisher\": " + "\"" + appPublisher + "\",\n");

				String appPrice = doc.select(PRICE_META).first().attr("content");
				builder.append(" \"price\": " + "\"" + priceLabel(appPrice) + "\",\n");

				double appRating = Double.parseDouble(doc.select(RATING_DIV).text());
				builder.append(" \"rating\": " + appRating + "\n}");
				System.out.println(builder);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Converts the string to a single line and replaces double quotes.
	 * 
	 * @param string
	 *            - string to clean
	 * @return sanitized string.
	 */
	private static String deepClean(String string) {
		return string.replaceAll(singleLineREGX, "").replace("\"", ""); // replace
																		// "" (double quotes);
	}

	/**
	 * Determines the price label
	 * 
	 * @param price
	 * @return "Free" if price contains no dollar value; otherwise the dollar
	 *         value as a string(with $).
	 */
	private static String priceLabel(String price) {
		StringBuilder builder = new StringBuilder("");
		if (price.charAt(0) != '$') {
			return builder.append("Free").toString();
		}
		return price;
	}

	/**
	 * Formats the title to only include the app's title
	 * 
	 * @param docTitle
	 *            - Jsoup Document title
	 * @return - String containing just the app's name.
	 */
	private static String formatTitle(String docTitle) {
		return docTitle.substring(0, docTitle.indexOf('-')).trim();
	}
}
