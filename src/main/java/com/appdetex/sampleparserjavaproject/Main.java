package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


/**
 * Main Java Class
 * Edited by Matthew Gerber March 17 2017
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

	//Main method runs the parser
    public static void main(String[] args) {
        Document doc;
        
        //Checks for args, if none program exits
        if(args.length != 1){
        	System.err.println("Usage: Must enter URL");
        	System.exit(0);
        }
        
        //Tries to connect to url, if url cannot be reached returns IOExpection
        try{
        	doc = Jsoup.connect(args[0]).get();
        } catch(IOException e){
        	System.err.println("URL " + args[0] + " not found.");
			return;
        }
        
        //Formats String like a JSON object
        String formattedJSON = "{\n"
        		+ "     title: \"" + getTitle(doc) + "\"\n" 
        		+ "     description: \"" + getDescription(doc) + "\"\n"
        		+ "     publisher: \"" + getPublisher(doc) + "\"\n"
        		+ "     price: \"" + getPrice(doc) + "\"\n"
        		+ "     rating: \"" + getRating(doc) + "\"\n}";
        //Prints formatted String
        System.out.println(formattedJSON);
    }
    
    //Retrieves title from play store url.
    private static String getTitle(Document doc){
    	return doc.getElementsByClass("id-app-title").text();
    }
    
    //Retrieves description from play store url.
    private static String getDescription(Document doc){
        String description = doc.getElementsByClass("show-more-content text-body").html().split("<br>")[0].split("<div jsname=\"C4s9Ed\">\n ")[1];
        String tokens[] = description.split(" ");
        description = tokens[0];
        for (int i = 1; i < tokens.length; i++){
            description = description + " " + tokens[i];
            if(i % 10 == 0)
                description = description + "\n";
        }
        return description;
    }
    
    
    //Retireves publisher from play store url.
    private static String getPublisher(Document doc){
    	return doc.getElementsByClass("document-subtitle primary").text();
    }
    
    //Retrieves price from play store url.
    private static String getPrice(Document doc){
    	String price = doc.select("button.price > span:last-of-type").text().split(" ")[0]; 
    	price = price.toLowerCase();
    	if(price.equals("install")){  //Play store uses install instead of free
    		price = "Free";  //If play store has install, sets price to "Free"
    	}
    	return price;
    }

    //Retrieves rating from play store url.
    private static double getRating(Document doc){
    	return Double.parseDouble(doc.getElementsByClass("score").text());
    }
}
