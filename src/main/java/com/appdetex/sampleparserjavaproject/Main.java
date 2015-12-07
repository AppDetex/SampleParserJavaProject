
package com.appde.M<tex.sampleparserjavaproject;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * Main Java Class This class will use Jsoup to retrieve a provided URL and
 * parse out certain data, printing that data to stdout in a JSON format.
 */
public class Main {
	public final static int ERROR_EXIT_CODE = 1;
	public static final String DESCRIPTION_DIV = "div[jsname=C4s9Ed]";
	public static final String RATING_DIV = "div[class=score]";
	public static final String PUBLISHER_DIV = "span[itemprop=name]";
	public static final String PRICE_META = "meta[itemprop=price]";
	public static final String P_TAG = "<\\/?[^>]+>";

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

			builder.append("{\n\"title\": " + "\"" + formatTitle(doc.title()) + "\",\n");

			String descriptionBody = doc.select(DESCRIPTION_DIV).html();
			String cleanBody = Jsoup.clean(descriptionBody.toString(), Whitelist.basic());
			builder.append("\"description\": " + "\"" + cleanBody.replaceAll(P_TAG, "").trim() + "\",\n");

			String appPublisher = doc.select(PUBLISHER_DIV).text();
			builder.append("\"publisher\": " + "\"" + appPublisher + "\",\n");

			String appPrice = doc.select(PRICE_META).first().attr("content");
			builder.append("\"price\": " + "\"" + formatPrice(appPrice) + "\",\n");

			double appRating = Double.parseDouble(doc.select(RATING_DIV).text());
			builder.append("\"rating\": " + appRating + "\n}");
			System.out.println(builder);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String formatPrice(String price) {
		StringBuilder builder = new StringBuilder("");
		if (price.charAt(0) != '$') {
			return builder.append("Free").toString();
		}
		return price;
	}

	private static String formatTitle(String docTitle) {
		return docTitle.substring(0, docTitle.indexOf('-')).trim();
	}
}
