package com.appdetex.sampleparserjavaproject;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.select.Selector.*;
/**
 * Takes a URL for a Google Play app page as a command line parameter. Uses Jsoup to retrieve the URL,
 * and parse it. Then outputs a JSON-formatted string containing
 *      app title,
 *      description,
 *      publisher name,
 *      price,
 *      and rating (average review score)
 * for the app to stdout.
 * 
 * @author Zach Clegg
 */
public class Main {

	public static void main(String[] args) throws IOException, SelectorParseException {
		StringBuilder appData = new StringBuilder("");

		if (args.length > 0) {		//Check that we have been given input
	        Document doc = Jsoup.connect(args[0]).get();	//Create a connection to the URL, then get the DOM data and return it as a Document
	        Elements appTitle = doc.select(".id-app-title");	//Select the elements from the Document with the specified classes and attributes
	        Elements description = doc.select(".show-more-content.text-body");
	        Elements publisher = doc.select(".document-subtitle.primary span");
	        Elements price = doc.select(".price.buy.id-track-click [itemprop=price]");
	        Elements rating = doc.select(".score");
	        appData.append("{\n\t\"title\": ").append(appTitle.text());		//Build our JSON-formatted string of outputs
        	appData.append(",\n\t\"description\": ").append(description.text())
        	.append(",\n\t\"publisher\": ").append(publisher.text())
        	.append(",\n\t\"price\": ").append(price.attr("content"))
        	.append(",\n\t\"rating\": ").append(rating.text() + "\n}");
		}

		System.out.println(appData.toString());		//Output the requested app data
		return;
	}
}
