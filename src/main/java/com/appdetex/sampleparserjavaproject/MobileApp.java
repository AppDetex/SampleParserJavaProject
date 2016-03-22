package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.simple.JSONObject;

//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
import java.io.IOException;


public class MobileApp {

    private String url;
    private Document document;
    private String title;
    private String description;
    private String publisher;
    private String price;
    private String rating;
    
    private JSONObject object = new JSONObject();


    MobileApp (String url) {
        this.url = url;
    }

    public void parseInfo() {
        try {
            document = Jsoup.connect(url).get();

            title = document.select("div[class=id-app-title]").text();
            description = document.select("div[itemprop=description]").text();
            publisher = document.select("span[itemprop=name]").text();
            price = document.select("meta[itemprop=price]").get(0).attr("content");
            rating = document.select("div[class=score").text();
            //System.out.println(document);
            //printResults();
            jsonEncode();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jsonEncode() {
    	object.put("title", title);
    	object.put("description", description);
    	object.put("publisher", publisher);
    	object.put("price", price);
    	object.put("rating", rating);
    	
    	System.out.println(object.toJSONString());	
    }
    
    public void printResults() {
        System.out.println("");
        System.out.println("title: " + title);
        System.out.println("description: " + description);
        System.out.println("publisher: " + publisher);
        System.out.println("price: " + price);
        System.out.println("rating: " + rating);
        System.out.println("");
    }

}
