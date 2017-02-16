package com.appdetex.sampleparserjavaproject;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {
	private Document doc;
	private String title;
	private String description;
	private String publisher;
	private String price;
	private String rating;
	private JSONObject appData;
	
	public Main (String url) throws IOException {
		this.doc = Jsoup.connect(url).get();
		title = "Unknown";
		description = "Unknown";
		publisher = "Unknown";
		price = "Unknown";
		rating = "Unknown";
	}

    public static void main( String[] args ) throws IOException, JSONException {
    	Validate.isTrue(args.length == 1, "usage: supply url as command line parameter");
		String url = args[0];
			
		Main scraper = new Main(url);
		scraper.setTitle();
		scraper.setDescription();	
		scraper.setPublisher();
		scraper.setPrice();
		scraper.setRating();
		scraper.buildJSONObj();
		scraper.printData();
    }
    
    public void setTitle() {
    	this.title = this.doc.select("div.id-app-title").first().text();
    }
    
    public void setDescription() {
		Elements descriptionElements = doc.select("div[jsname]");
			
		for (Element el : descriptionElements) {

			if (el.attr("jsname").equals("C4s9Ed")) {
				this.description = el.text();
				break;
			}
		}
    }
    
   public void setPublisher() {
	   Elements publisherElements = this.doc.select("span[itemprop]");

	   for (Element el : publisherElements) {

		   if (el.attr("itemprop").equals("name")) {
			   this.publisher = el.text();
			   break;
		   }
	   }
   }
   
   public void setPrice() {
	   Elements priceElements = this.doc.select("span[itemprop]");

	   for (Element el : priceElements) {

		   if (el.attr("itemprop").equals("offers")) {
			   Element el2 = el.child(1);
			   this.price = el2.attr("content");

			   if (this.price.equals("0")) {
				   this.price = "free";
			   }
			   break;
		   }
	   }
   }
   
   public void setRating() {
	    Elements scoreElements = this.doc.select("div.score");
	    this.rating = scoreElements.text();
   }
   
   public void buildJSONObj() throws JSONException {
	   this.appData = new JSONObject()
			   					.put("title", this.title)
			   					.put("description", this.description)
			   					.put("publisher", this.publisher)
			   					.put("price", this.price)
			   					.put("rating", this.rating);
   }
   
   public void printData() throws JSONException {
	   System.out.println(this.appData.toString(4));
   }
}

