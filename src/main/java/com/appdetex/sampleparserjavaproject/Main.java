package com.appdetex.sampleparserjavaproject;

import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL and parse out certain
 * data, printing that data to stdout in a JSON format.
 */
public class Main {

	public static void main(String[] args) throws IOException {

		if (args.length < 1 || args.length > 1) {
			System.out.println("Usage: java Main <URL>");
		} else {
			Extractor extractor = new Extractor(args[0]);
			String json = extractor.extractAndBuildJSON();
			System.out.println(json);

		}
	}

}
