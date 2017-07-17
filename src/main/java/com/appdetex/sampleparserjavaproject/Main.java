package com.appdetex.sampleparserjavaproject;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
        // Put code here

		String urlIn = "";
    	if (args.length > 0) {
    		urlIn = args[0];
    	} else {
    		System.out.println("Application requires input parameter");
    		System.exit(9);
    	}
    	    	
    	Document doc = null;
    	try {
    		doc = Jsoup.connect(urlIn).get();
    	} catch (IOException e) {
    		System.out.println("Problem parsing page with JSoup");
    		System.out.println(e.getMessage());
    		System.exit(9);
    	}
    	
                
        String title = doc.select("div.id-app-title").text();
        String desc = doc.select("div[jsname=C4s9Ed]").text();
        String publisher = doc.select("span[itemprop=name]").text();
        String rating = doc.select("div.score").text();
        String price = doc.select("meta[itemprop=price]").attr("content");
        
        if (title != null) {
        	title = title.trim();
        } else {
        	title = "";
        }

        if (desc != null) {
        	int paraEnd = desc.indexOf(".");
        	if (paraEnd != -1) {
        		desc = desc.substring(0,  paraEnd + 1);
        	}
        } else {
        	desc = "";
        }
        
        if (publisher != null) {
        	publisher = publisher.trim();
        } else {
        	publisher = "";
        }
        
        if (rating != null) {
        	rating = rating.trim();
        } else {
        	rating = "";
        }
        
        if (price != null) {
        	price = price.trim();
        } else {
        	price = "";
        }
        
    	GooglePlayApp gpa = new GooglePlayApp(title, desc, publisher, price, rating);

    	String jsonOut = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
        	jsonOut = mapper.writeValueAsString(gpa);
        } catch (JsonProcessingException e) {
        	System.out.println("Probelm converting object to JSON");
        	System.out.println(e.getMessage());
        	System.exit(9);
        }
        
    	System.out.println(jsonOut);
    	System.exit(0);
    }

}
