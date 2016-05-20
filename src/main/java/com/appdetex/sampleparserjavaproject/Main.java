package com.appdetex.sampleparserjavaproject;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL and parse out certain
 * data, printing that data to stdout in a JSON format.
 */
public class Main {
	
	/**
	 * Usage statement, provides user an understanding on how to use the application
	 */
	public static void usage() {
		System.out.println("USAGE: java Main <url>");
		System.exit(1);
	}

	public static void main(String[] args) {
		
		if (args.length != 1) {
			usage();
		}
		
		String url = args[0];
		ScrapingTool stools = new ScrapingTool(url);
		stools.getJsonFile();
	}
}
