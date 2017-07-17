package com.appdetex.sampleparserjavaproject;

public class GooglePlayApp {
	
	private String title;
	private String desc;
	private String publisher;
	private String price;
	private String rating;
	
	public GooglePlayApp (String title, String desc, String publisher, String price, String rating) {
		this.title = title;
		this.desc = desc;
		this.publisher = publisher;
		this.price = price;
		this.rating = rating;
	}
	
	public String getTitle() {
		return title;
	}
	public String getDesc() {
		return desc;
	}
	public String getPublisher() {
		return publisher;
	}
	public String getPrice() {
		return price;
	}
	public String getRating() {
		return rating;
	}

}
