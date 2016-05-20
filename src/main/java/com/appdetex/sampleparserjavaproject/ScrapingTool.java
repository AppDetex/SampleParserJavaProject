package com.appdetex.sampleparserjavaproject;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Using Jsoup this class obtains data from an App and displays it on a JSON-formatted file
 * @author amorales7 - Alex Morales
 *
 */
public class ScrapingTool {

	private Document doc;
	private String URL;
	private JSONObject jsonFile;
	private String title, description, publisher, price;
	private double rating;

	public ScrapingTool(String URL) {
		this.URL = URL;
		jsonFile = new JSONObject();
		scrapingTool();
	}

	/**
	 * Jsoup retrievers data from the URL provided by the user
	 */
	private void scrapingTool() {
		// Jsoup connects to the URL
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			System.out.println("URL was not found");
			e.printStackTrace();
		}
		MobileAppInfo(doc);
		produceJSONFile();
	}

	/**
	 * Once we obtained data from the App this function parses data using 
	 * jsoup.nodes.Element[s] libraries and stores them in Strings, later to be put
	 * in a JSONObject file.
	 * @param docInfo - Document
	 */
	private void MobileAppInfo(Document docInfo) {

		Element titleInfo = docInfo.getElementById("main-title");
		title = titleInfo.text().substring(0, titleInfo.text().lastIndexOf("-") - 1);

		Elements descriptionInfo = docInfo.getElementsByAttributeValueContaining("jsname", "C4s9Ed");
		descriptionInfo.select("p").remove();
		description = descriptionInfo.text();

		Elements publisherInfo = docInfo.getElementsByAttributeValueContaining("itemprop", "name");
		publisherInfo.select("div").remove();
		publisher = publisherInfo.text();

		Elements priceInfo = docInfo.getElementsByAttributeValueContaining("itemprop", "price");
		price = priceInfo.attr("content");

		Elements ratingInfo = docInfo.getElementsByAttributeValueContaining("itemprop", "ratingValue");
		rating = Math.round(Double.parseDouble(ratingInfo.attr("content")) * 10.0) / 10.0; //needs to be round to one decimal place
	}

	@SuppressWarnings("unchecked")
	/**
	 * Associates the specified value with the specified key in the JSONObject file
	 * 
	 * NOTE: The order of elements varies because JSON specification stays that 
	 * An JSONObject is an unordered set of name/value pairs
	 */
	private void produceJSONFile() {
		jsonFile.put("title", title);
		jsonFile.put("description", description);
		jsonFile.put("publisher", publisher);
		jsonFile.put("price", price);
		jsonFile.put("rating", rating);

	}

	/**
	 * Display and creates a JSON-formatted file from the JSONObject previously produced 
	 */
	public void getJsonFile() {

		//displays the mobile information in a very readable way 
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyout = gson.toJson(jsonFile);
		System.out.println(prettyout);

		System.out.println("");
		System.out.println("[ Also Check urlInfo.json to see results ]");
		//creates a json file
		try {
			FileWriter file = new FileWriter("urlInfo.json");
			file.write(prettyout);
			file.flush();
			file.close();
		} catch (IOException e) {
			System.out.println("ERROR: Can't create .json file");
			e.printStackTrace();
		}
	}
}
