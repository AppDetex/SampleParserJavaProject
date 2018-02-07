package com.appdetex.sampleparserjavaproject.utils;

import java.io.IOException;
import java.text.ParseException;

import org.jsoup.nodes.Document;
import com.appdetex.sampleparserjavaproject.domain.ItemProperties;

public interface Scraper {
	ItemProperties scrapeUrl(String url) throws ParseException, IOException;
	ItemProperties scrapeDocument(Document doc) throws ParseException;	
}
