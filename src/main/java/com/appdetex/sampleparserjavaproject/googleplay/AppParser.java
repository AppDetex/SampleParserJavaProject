package com.appdetex.sampleparserjavaproject.googleplay;

import java.io.IOException;
import java.math.BigDecimal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.appdetex.sampleparserjavaproject.googleplay.model.App;

/**
 * Scrapes the Google Play store
 * @author Daniel Oliver
 */
public class AppParser {
	/**
	 * Scrapes the Google Play Store and returns an object containing scraped data.
	 * @param url The Google Play Store url of the app to be scraped
	 * @return App
	 */
	public static App scrapeGooglePlayApp(String url) {
		checkUrl(url);
		try {
			Document doc = Jsoup.connect(url).get();
			App app = new App();
			
			final String titleSelector = "div.id-app-title";
			final String descriptionSelector = "div.details-section.description div.show-more-content div";
			final String publisherSelector = "div[itemProp=author] > a.document-subtitle.primary > span";
			final String ratingSelector = "div.score-container > meta[itemprop=ratingValue]";
			final String priceSelector = "meta[itemprop=price]";
			
			app.setTitle(doc.select(titleSelector).text());
			
			String description = doc.select(descriptionSelector).html();
			if (description.indexOf("\n") != -1) { //Only grab first paragraph
				description = description.substring(0, description.indexOf("\n"));
			}		
			app.setDescription(description);

			app.setPublisher(doc.select(publisherSelector).text());
			String rating = doc.select(ratingSelector).attr("content");
			//Rating isn't set on "coming soon" apps
			if (rating != null && !rating.isEmpty()) {
				app.setRating(new BigDecimal(rating).setScale(1, BigDecimal.ROUND_HALF_UP));	
			}
			
			String price = doc.select(priceSelector).attr("content");
			//Price isn't set on "coming soon" apps
			if (price != null && !price.isEmpty()) {
				price = price.replace("$", "");
				app.setPrice(new BigDecimal(price));	
			}
			return app;
		} catch (IOException e) {
			System.out.println("IOException while trying to connect to Google Play link: " + url);
			throw new RuntimeException(e);
		}
	}
	
	private static void checkUrl(String url) {
		if (url == null) {
			throw new RuntimeException("Url provided must be non-null");
		}else if (!url.contains("play.google.com")) {
			throw new RuntimeException("Url must be a Google Play url");
		}
	}
}
