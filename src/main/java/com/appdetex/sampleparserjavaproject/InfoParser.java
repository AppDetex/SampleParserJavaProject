package com.appdetex.sampleparserjavaproject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

// InfoParser encapsulates the CSS selector and logic required to retrieve
// each of the desired properties off of the application information page.
public class InfoParser {
	private Document doc = null;
	
	public InfoParser(Document doc) {
		this.doc = doc;
	}
	
	public String getTitle() {
		return getText(".document-title[itemprop=\"name\"]");
	}
	
	public String getDescription() {
		return getFirstParagraph("div [itemprop=\"description\"] div");
	}
	
	public String getRating() {
		return getText(".content-rating-title");
	}
	
	public String getPrice() {
		Element elem = getElement("meta[itemprop=\"price\"]");
		if (elem != null) {
			return elem.attr("content").toString();
		}
		return "";
	}
	
	public String getPublisher() {
		return getText("[itemprop=\"author\"] [itemprop=\"name\"]");
	}
	
	private Element getElement(String selector) {
		return doc.select(selector).first();
	}
	
	private String getText(String selector) {
		Element elem = getElement(selector);
		if (elem != null && elem.hasText()) {
			return elem.text();
		}
		return null;
	}
	
	private String getFirstParagraph(String selector) {
		Element elem = getElement(selector);
		if (elem != null && elem.childNodes().size() > 0) {
			if (elem.childNode(0) instanceof Element) {
				return elem.child(0).text();
			}
			else {
				return elem.childNode(0).toString();
			}
		}    
		return null;
	}
}
