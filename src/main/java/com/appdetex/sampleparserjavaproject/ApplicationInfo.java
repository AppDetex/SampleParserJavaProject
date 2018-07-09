package com.appdetex.sampleparserjavaproject;

public class ApplicationInfo {
    private String title;
    private String description;
    private String publisher;
    private String price;
    private Double rating;

    public ApplicationInfo (String title, String description, String publisher, String price, String rating) {

        this.title = title;
        this.description = description;
        this.publisher = publisher;

//   Since there is not really a price of "0", Free should be the output value
        if(price.equals("0")){
          this.price = "Free";
        }else{
          this.price = price;
        }

        if (!rating.equals("")) {
            this.rating = Double.parseDouble(rating);
        } else {
            this.rating = 0.0;
        }

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
    public Double getRating() {
        return rating;
    }

}

