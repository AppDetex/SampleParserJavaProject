package com.appdetex.sampleparserjavaproject;
public class AppDescription {
    private String title;
    private String description;
    private String publisher;
    private String price;
    private double rating;

    AppDescription(String title, String description, String publisher, String price, double rating) {
        this.title = title;
        this.description = description;
        this.publisher = publisher;
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
        return price;
    }

    public double getRating() {
        return rating;
    }
}
