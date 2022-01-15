package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Locale;

/*
 * This is the class that takes a URL (or HTML string), parses out the data wanted
 * and returns the ApplicationData object
 */
public class PageScraper {
	/* selectors for pulling the appropriate data out */
	private static final String PUBLISHER_SELECTOR = "a[href].hrTbp";
	private static final String RATING_SELECTOR = ".BHMmbe";
	private static final String TITLE_SELECTOR = "[itemprop=name]";
	private static final String PRICE_SELECTOR = "[itemprop=price]";
	private static final String DESCRIPTION_SELECTOR = "div[jsname=sngebd]";
	private static final String HTML_SELECTOR = "html";

	private final Document doc;

	private PageScraper(Document doc) {
		this.doc = doc;
	}

	public static PageScraper fromUrl(String url) {
		try {
			var doc = Jsoup.connect(url).get();
			return new PageScraper(doc);
		} catch (IOException ioe) {
			throw new RuntimeException("Cannot get url to parse",ioe);
		}
	}

	/* used for testing */
	static PageScraper fromHtml(String html) {
		Document doc = Jsoup.parse(html);
		return new PageScraper(doc);
	}


	public ApplicationData scrape() {
		var htmlNode = doc.selectFirst(HTML_SELECTOR);
		if (htmlNode == null) {
			throw new IllegalArgumentException("document is not properly formatted html");
		}

		var lang = htmlNode.attr("lang");
		var locale = Locale.forLanguageTag(lang);

		ApplicationData ad = new ApplicationData();

		ad.setTitle(parseTitle());
		ad.setPrice(parsePrice());
		ad.setDescription(parseDescription());
		ad.setRating(parseRating(),locale);
		ad.setPublisher(parsePublisher());

		return ad;
	}

	/*
	These are the various methods for pulling the data as needed. When the page format changes,
	these are the methods that may to be changed if it isn't a simple selector change.
	 */

	private String parsePublisher() {
		return pullSimple(PUBLISHER_SELECTOR);
	}

	private String parseRating() {
		return pullSimple(RATING_SELECTOR);
	}

	private String parseDescription() {
		var e = doc.selectFirst(DESCRIPTION_SELECTOR);
		if(e != null) {
			var desc = e.html();
			// first paragraph of the description will be before any <br> tags
			int ndx = desc.toLowerCase().indexOf("<br>");
			if (ndx > 1) {
				desc = desc.substring(0, ndx);
			}
			return desc.trim();
		}
		return null;
	}

	private String parsePrice() {
		var e = doc.selectFirst(PRICE_SELECTOR);
		if(e != null) {
			return e.attr("content");
		}
		return null;
	}

	private String parseTitle() {
		return pullSimple(TITLE_SELECTOR);
	}

	private String pullSimple(String selector) {
		var e = doc.selectFirst(selector);
		if(e != null) {
			return e.text();
		}
		return null;
	}

}
