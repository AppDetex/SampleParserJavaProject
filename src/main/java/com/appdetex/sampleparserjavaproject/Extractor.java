package com.appdetex.sampleparserjavaproject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Extractor {

	public static String GetPublisher(Document doc)
	{
		Element e = doc.select(".hrTbp.R8zArc").first();
		if (e != null)
			return e.text();
		else
			return "Not Found";
	}
	
	public static String GetTitle(Document doc)
	{
		Element e = doc.select(".AHFaub").first();
		if (e != null)
			return e.text();
		else
			return "Not Found";
	}
	
	public static float GetRating(Document doc)
	{
		Element e = doc.select(".pf5lIe").first();
		if (e != null)
		{
			String s = e.child(0).attr("aria-label");
			String[] parts = s.split(" ");
			return Float.parseFloat(parts[1]);
		}
		else
			return (float) 0.0;
	}
	
	public static String GetPrice(Document doc)
	{
    	Element e = doc.select("[itemprop=price]").first();
    	if (e != null)
    	{
    		String price = e.attr("content");
    		return price.equals("0") ? "$0.00" : e.attr("content");
    	}
    	else
    		return "Not Found";
	}
	
	public static String GetDescription(Document doc)
	{
    	Element e = doc.select("[jsname=sngebd]").first();
    	if (e != null)
    	{
    		String[] descriptionParts = e.html().split("<br>");
    		return descriptionParts[0].trim();
    	}
    	else
    		return "Not Found";
	}
}
