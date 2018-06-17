package com.appdetex.sampleparserjavaproject;

import java.util.Objects;

public class PlayStoreApp {
    private final String title;
    private final String description;
    private final String publisher;
    private final String price;
    private final double rating;

    public PlayStoreApp(String title, String description, String publisher, String price, double rating) {
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.price = price;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public String getPublisher() {

        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayStoreApp that = (PlayStoreApp) o;
        return Double.compare(that.rating, rating) == 0 &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, description, publisher, price, rating);
    }

    @Override
    public String toString() {
        return "PlayStoreApp{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price='" + price + '\'' +
                ", rating=" + rating +
                '}';
    }
}
