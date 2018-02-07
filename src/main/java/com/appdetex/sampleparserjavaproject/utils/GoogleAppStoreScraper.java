package com.appdetex.sampleparserjavaproject.utils;

import java.io.IOException;
import java.text.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.appdetex.sampleparserjavaproject.domain.ItemProperties;


public class GoogleAppStoreScraper implements Scraper {
	private static String title;
	private static String description;
	private static String publisher;
	private static String price;
	private static Double rating;

	public ItemProperties scrapeUrl(String url)  throws ParseException, IOException {
        Document doc = Jsoup.connect(url).get();
        return scrapeDocument(doc);
	}

	public ItemProperties scrapeDocument(Document doc) throws ParseException {

		Elements itemProps = doc.getElementsByAttribute("ItemProp");

        for (Element prop : itemProps) {
        	if(prop.attr("ItemProp").equalsIgnoreCase("price")) {
        		price = prop.attr("content").trim();
        	} else if(prop.attr("ItemProp").equalsIgnoreCase("ratingValue")) {
        		rating = Double.parseDouble(prop.attr("content").trim());
        	} else if(prop.attr("ItemProp").equalsIgnoreCase("name") ) {
        		if(prop.hasClass("document-title")) {
        			title = prop.text().trim();
        		} else if(!prop.attr("content").equalsIgnoreCase("Android")){
        			publisher = prop.text().trim();
        		}
        	} else if(prop.attr("ItemProp").equalsIgnoreCase("description") ) {
      			description = prop.select("[jsname]").first().childNodes().get(0).toString().trim();
           	}
        }
        return new ItemProperties(title, publisher, description, price, rating);
	}
}
