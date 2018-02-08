package com.appdetex.sampleparserjavaproject.googleplay.model;

import java.math.BigDecimal;

import com.appdetex.sampleparserjavaproject.googleplay.util.CurrencySerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class App {

	private String title;
	private String description;
	private String publisher;
	@JsonSerialize(using = CurrencySerializer.class)
	private BigDecimal price;
	private BigDecimal rating;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getRating() {
		return rating;
	}
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	
	public String toJSON() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			System.out.println("JSON processing error while converting App to JSON.");
			throw new RuntimeException(e);
		}
	}
}
