package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.util.*;
import javax.json.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */

public class Main {

    public static void main( String[] args ) {
		HashMap<String, Object> appInfo = new HashMap<String, Object>();
		Document doc;
		
		if (args.length != 1) {
			System.out.println("Missing argument: please supply an app URL.");
			System.exit(-1);
		}
		
		try {
			// The Jsoup.connect() is not reliable or retrieval of some portion of the target
			// page is not reliable. Retrying up to five times to get all five desired properties.
			int retries = 5;
			while(retries-- > 0) {
    			// Open the document
				doc = Jsoup.connect(args[0]).get();
				InfoParser info = new InfoParser(doc);
				
				setNonNullValue(appInfo, "title", info.getTitle());
				setNonNullValue(appInfo, "publisher", info.getPublisher());
				setNonNullValue(appInfo, "price", info.getPrice());
				setNonNullValue(appInfo, "rating", info.getRating());
				setNonNullValue(appInfo, "description", info.getDescription());
				
				if (appInfo.keySet().size() == 5) {
					break;
				}
			}
		}
		catch (IOException e)
		{
			System.out.printf("Cannot open %s", args[0]);
			System.exit(-2);
		}
		
		JsonObject json = Json.createObjectBuilder(appInfo).build();
		System.out.println(json.toString());
    }
    
    private static void setNonNullValue(Map<String, Object> map, String key, Object value) {
    		if (value != null) {
    			map.put(key, value);
    		}
    }
}