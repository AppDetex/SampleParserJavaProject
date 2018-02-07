package com.appdetex.sampleparserjavaproject.utils;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.text.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.appdetex.sampleparserjavaproject.domain.ItemProperties;

public class GoogleAppStoreScraperTest {
	private long startTime;
	private long endTime;
	Scraper scraper;
	
	@Before
	public void startTiming() {
		startTime = System.nanoTime();
		scraper = new GoogleAppStoreScraper();
	}
	
	@After
	public void endTiming() {
		scraper = null;
		endTime = System.nanoTime();
		System.out.println("method took: " + (double)(endTime - startTime)/1000000000.0 +" seconds");
	}
	
	@Test
	public void testScrapeUrl() throws IOException, ParseException {
		ItemProperties ip = scraper.scrapeUrl("https://play.google.com/store/apps/details?id=com.anjunsang.candyline&hl-en-US");
		assertNotNull(ip.convertToJson());
		ip = scraper.scrapeUrl("https://play.google.com/store/apps/details?id=halloween.candy");
		assertNotNull(ip.convertToJson());
	}
	
	@Test
	public void testScrapeDocument() throws IOException, ParseException {
		Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=com.anjunsang.candyline&hl-en-US").get();
		ItemProperties ip = scraper.scrapeDocument(doc);
		assertNotNull(ip.convertToJson());
	}
}
