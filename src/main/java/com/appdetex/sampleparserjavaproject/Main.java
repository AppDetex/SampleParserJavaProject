package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

	public static void main(String[] args) {
		
    	if (args.length != 1)
    	{
    		System.err.println("Only one argument is supported");
    		usage();
    	}
    	
    	try
    	{
    		URL passedURL = new URL(args[0]);
    		if (!passedURL.getHost().equals("play.google.com"))
    		{
    			System.err.println("Invalid Host");
    			usage();
    		}
    		else if (!passedURL.getPath().equals("/store/apps/details"))
    		{
    			System.err.println("Invalid path");
    			usage();
    		}
    		else
    		{
	        	Document doc = Jsoup.connect(args[0]).get();
	        	JsonObject jsonDoc = new JsonObject();
	        	jsonDoc.addProperty("publisher", Extractor.GetPublisher(doc));
	        	jsonDoc.addProperty("title", Extractor.GetTitle(doc));
	        	jsonDoc.addProperty("rating", Extractor.GetRating(doc));
	        	jsonDoc.addProperty("price", Extractor.GetPrice(doc));
	        	jsonDoc.addProperty("description", Extractor.GetDescription(doc));
	        	
	        	 Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
	             System.out.println(gson.toJson(jsonDoc));
    		}
    	}
    	catch (MalformedURLException e)
    	{
    		System.err.println("Bad url: "+args[0]);
    		usage();
    	}
    	catch (Exception e)
    	{
    		System.err.println("Exception caught "+e.getMessage());
    		usage();
    	}
	}
	
    private static void usage()
    {
    	System.err.println("Usage:  program url\nwhere url is something that points to a google play store app\n"
    			+ "e.g: https://play.google.com/store/apps/details?id=com.icegame.candyline");
    }

}
