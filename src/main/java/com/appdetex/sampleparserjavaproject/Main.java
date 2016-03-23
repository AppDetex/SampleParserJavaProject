package com.appdetex.sampleparserjavaproject;

import java.util.regex.Pattern;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL and parse out certain
 * data, printing that data to stdout in a JSON format.
 * 
 * @author csilkwor
 * 
 */
public class Main {

	/**
	 * Main entry for the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Parser parser = new Parser();
		// Check for proper usage
		if (args.length != 1) {
			parser.printUsage();
			System.exit(1);
		}
		String url = args[0];
		String regex = "^(http|https)://play.google.com/store/.*$";

		// Check to ensure the input URL is a part of the Google Play Store
		if (!Pattern.matches(regex, url)) {
			parser.printUsage();
			System.exit(1);
		}
		parser.parseInfo(url);
		System.out.println(parser.jsonEncode());
	} // end main method
} // End Main class
