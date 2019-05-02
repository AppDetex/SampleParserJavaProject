package com.appdetex.sampleparserjavaproject.googlePlay;

import java.util.Objects;

public final class AppData {
    public final String title;
    public final String description;
    public final String publisher;
    public final String price;
    public final Float rating;

    AppData(String title, String description, String publisher, String price, Float rating) {
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.price = price;
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "AppData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price='" + price + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppData appData = (AppData) o;
        return Objects.equals(title, appData.title) &&
                Objects.equals(description, appData.description) &&
                Objects.equals(publisher, appData.publisher) &&
                Objects.equals(price, appData.price) &&
                Objects.equals(rating, appData.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, publisher, price, rating);
    }
}
