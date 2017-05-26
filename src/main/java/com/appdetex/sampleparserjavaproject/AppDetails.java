package com.appdetex.sampleparserjavaproject;

import java.math.BigDecimal;

/**
 * POJO to hold all the app details
 * 
 * @author Srisarguru
 *
 */
public class AppDetails {
	private String appTitle;
	private String appDescription;
	private String appPublisher;
	private BigDecimal appRating;
	private String appPrice;

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public String getAppDescription() {
		return appDescription;
	}

	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	public String getAppPublisher() {
		return appPublisher;
	}

	public void setAppPublisher(String appPublisher) {
		this.appPublisher = appPublisher;
	}

	public BigDecimal getAppRating() {
		return appRating;
	}

	public void setAppRating(BigDecimal appRating) {
		this.appRating = appRating;
	}

	public String getAppPrice() {
		return appPrice;
	}

	public void setAppPrice(String appPrice) {
		this.appPrice = appPrice;
	}

}
