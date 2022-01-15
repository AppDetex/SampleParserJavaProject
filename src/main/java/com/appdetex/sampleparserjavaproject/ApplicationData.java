package com.appdetex.sampleparserjavaproject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Holds the data that we are parsing
 *
 * I was originally was using lombok but I converted it to a "plain" POJO
 */
public class ApplicationData {

	private String title;
	private String description;
	private String publisher;
	private String price;
	private double rating;

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public String getPrice() {
		return this.price;
	}

	public double getRating() {
		return this.rating;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setRating(String ratingAsString, Locale locale) {
		var nf = NumberFormat.getInstance(locale);
		try {
			rating = nf.parse(ratingAsString).doubleValue();
		} catch (ParseException pe) {
			throw new NumberFormatException(pe.getMessage());
		}
	}
}
