package com.appdetex.sampleparserjavaproject;

/**
 * 
 * @author asharma
 *
 *
 * Class that represents the "expected" attributes of the page
 */
public class PageInfo {
	
	private String title;
	private String description;
	private String nameOfPublisher;
	private String price;
	private String rating;

	public PageInfo(String title, String description, String nameOfPublisher, String price, String rating) {
		this.title = title;
		this.description = description;
		this.nameOfPublisher = nameOfPublisher;
		this.price = price;
		this.rating = rating;
	}
	
	public PageInfo() {
		// do initializations (if needed)
	}
		
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getNameOfPublisher() {
		return nameOfPublisher;
	}
	public String getPrice() {
		return price;
	}
	public String getRating() {
		return rating;
	}
	

	

}
