package com.appdetex.sampleparserjavaproject;

import java.math.BigDecimal;

/**
 * Represents details of the Play Store application.
 */
public final class AppDetails {

    private String title;
    private String description;
    private String publisher;
    private String price;
    private BigDecimal rating;


    /**
     * Constructor for AppDetails.
     * @param title Application's title
     * @param description First paragraph of application's description
     * @param publisher Application's publisher
     * @param price  Application's price
     * @param rating Application's rating
     */
    public AppDetails(String title, String description, String publisher, String price, BigDecimal rating) {
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.price = price;
        this.rating = rating;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the rating
     */
    public BigDecimal getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}
