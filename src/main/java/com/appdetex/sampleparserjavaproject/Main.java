package com.appdetex.sampleparserjavaproject;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.select.Selector.*;

import org.json.simple.*;
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

	public static void main(String[] args) {
		if (args.length > 0) {		//Check that we have been given input
	        String URL = new String(args[0]);
	        if (URL.startsWith("https://play.google.com/")) {
	        	try {
	        		Document doc = Jsoup.connect(URL).get();	//Create a connection to the URL, then get the DOM data and return it as a Document
	        		Elements appTitle = doc.select(".id-app-title");	//Select the elements from the Document with the specified classes and attributes
	        		Elements description = doc.select(".show-more-content.text-body");
	        		Elements publisher = doc.select(".document-subtitle.primary span");
	        		Elements price = doc.select(".price.buy.id-track-click [itemprop=price]");
	        		Elements rating = doc.select(".score");

	        		JSONObject appData = new JSONObject();		//Build our JSON-formatted string of outputs
	        		appData.put("title", new String(appTitle.text()));
	        		appData.put("description", new String(description.text()));
	        		appData.put("publisher", new String(publisher.text()));
	        		appData.put("price", new String(price.attr("content")));
        			appData.put("rating", new Float(rating.text()));

	        		System.out.println(appData);		//Output the requested app data

	        	} catch (MalformedURLException e) {	//if the requested URL is not a HTTP or HTTPS URL, or is otherwise malformed
	        		System.out.println("There was an unexpected error with my handling of your input. Did you feed me a URL to a Google Play Store page?");
	        		System.exit(1);
	        	} catch (HttpStatusException e) {	//if the response is not OK and HTTP response errors are not ignored
	        		System.out.println("I was unable to connect to the provided URL. That site may be down.");
	        		System.exit(1);
	        	} catch (UnsupportedMimeTypeException e) {	//if the response mime type is not supported and those errors are not ignored
	        		System.out.println("I am unable to open that type of file. What are you trying to feed me? I only eat at accredited websites.");
	        		System.exit(1);
	        	} catch (UnknownHostException e) {	//if the IP address of the host could not be determined
	        		System.out.println("Couldn't determine the IP Address of the URL provided. You're probably not connected to the internet. Sip your coffee, then get connected before trying again.");
	        		System.exit(1);
	        	} catch (SocketTimeoutException e) {	//if the connection times out
	        		System.out.println("I have timed out waiting for a response. In other words, I couldn't wait any longer for my order.");
	        		System.exit(1);
	        	} catch (IOException e) {	//on error
	        		System.out.println("An unexpected error has occurred with my handling of your input. Did you feed me a URL to a Google Play Store page?");
	        		System.exit(1);
	        	} catch (SelectorParseException e) {	//if the select method is given an invalid CSS query
	        		System.out.println("I was unable to find the requested information about this app. A big part of food is the presentation and I just wasn't ready for how this looked.");
	        		System.exit(1);
	        	}
	        }
	        else {
        		StringBuilder improperInput = new StringBuilder("");
        		improperInput.append("The input '").append(URL).append("' is not a URL to the Google Play Store");
        		System.out.println(improperInput);
	        	System.exit(1);
	        }
		}
		else {
			System.out.println("No input was provided. Please feed me! I only work well with URLs to Google Play Store pages.");
			System.exit(1);
		}

		return;
	}
}
