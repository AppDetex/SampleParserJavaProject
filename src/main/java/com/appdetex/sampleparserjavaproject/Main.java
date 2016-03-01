package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.Jsoup;

public class Main {
	
	private static final int urlArgIndex = 0;
	private static final int expectedArgLength = 1;
	
	private final String url;
	
	private String title, description, publisher, price;
	private double rating = -1;
	
	public Main(String url){
		this.url = url;
	}
	/**
	 * Uses a CSSquery language to parse through HTML
	 * @return
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	private String scrape() throws IOException, NumberFormatException{

		Document doc = Jsoup.connect(this.url).get();

		Element element = doc.getElementsByTag("title").first();
		this.title = constructTitle(element);

		element = doc.select("div[jsname=\"C4s9Ed\"]").first();
		this.description = constructDescription(element);

		element = doc.select("meta[itemprop=\"price\"").first();
		this.price = element.attr("content");

		element = doc.select("div[class=\"score\"").first();
		this.rating = Double.parseDouble(element.text());

		element = doc.select("span[itemprop=\"name\"]").first();
		this.publisher = element.text();

		return constructJSON();
	}
	/**
	 * Creates title (gets rid of any excess text that may have also been found)
	 * @param elementTitle
	 * @return
	 */
	private String constructTitle(Element elementTitle){
		String title = elementTitle.text();
		if(title.contains("-"))
			title = title.substring(0, title.lastIndexOf("-"));
		
		return title.trim();
	}
	/**
	 * Creates description. Begins at first <p> and ends with </p>.
	 * @param elementDescription
	 * @return
	 */
	private String constructDescription(Element elementDescription){
		StringBuilder description = new StringBuilder();
		List<Node> nodes = elementDescription.childNodes();
		
		for(Node node : nodes){
			String line = node.toString();
			
			String startPar = "<p>";
			String endPar = "</p>";
			
			//removes the <p> tags and retrieves the substring in between
			if(line.startsWith("<p>"))
				line = line.substring(startPar.length(), line.length() - endPar.length());
			
			//if the substring isn't empty, display the line
			if(!line.isEmpty())
				description.append(line + "\n");
		}
		
		String result = description.toString();
		
		//to remove extra new line at the end or beginning
		return result.trim();
	}
	/**
	 * Creates final output of JSON string
	 * @return
	 */
	private String constructJSON(){
		StringBuilder format = new StringBuilder("{\n");
		format.append("\t\"title\": \"%s\",\n");
		format.append("\t\"description\": \"%s\",\n");
		format.append("\t\"publisher\": \"%s\",\n");
		format.append("\t\"price\": \"%s\",\n");
		format.append("\t\"rating\": %.2f\n}");
		
		return String.format(format.toString(), title, description, publisher, price, rating);
	}
	
	public static void main(String[] args){
		if(args.length != expectedArgLength){
			System.out.println("java Main <url>");
			return;
		}
		
		Main scraper = new Main(args[urlArgIndex]);
		
		try {
			System.out.println(scraper.scrape());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something Went Wrong Opening the URL");
		}
	}
}
