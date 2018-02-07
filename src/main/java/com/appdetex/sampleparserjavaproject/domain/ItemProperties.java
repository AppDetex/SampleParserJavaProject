package com.appdetex.sampleparserjavaproject.domain;

import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ItemProperties {
	private final String title;
	private final String description;
	private final String publisher;
	private final String price;
	private final Double rating;
	
	private static DecimalFormat df1 = new DecimalFormat(".#");
	private static DecimalFormat df2 = new DecimalFormat(".##");

	public ItemProperties(String title, String publisher, String description, String price, Double rating) {
		this.title = title;
		this.publisher = publisher;
		this.description = description;
		this.price = price;
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getPrice() {
		if(price.equals("0")) {
			return "free";
		} else {
			return price;
		}
	}

	public Double getRating() {
		return Double.parseDouble(df1.format(rating));
	}
	
	public String convertToJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
}
