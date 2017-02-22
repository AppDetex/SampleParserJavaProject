package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Main Java Class
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {
    /* Items for output */
    private String fulltitle;
    private String title;
    private String description;
    private String publisher;
    private String price;
    private String rating;

    /* Items for use */
    private Document doc;

    public Main(String url) throws IOException {
        this.doc = Jsoup.connect(url).get();
        fulltitle = "";
        title = "";
        description = "";
        publisher = "";
        price = "";
        rating = "";
    }

    public static void main(String[] args) throws IOException {
        // test string:
        // https://play.google.com/store/apps/details?id=com.exozet.game.carcassonne

        if (args.length != 1) {
            System.out.println("Usage is: java main url ");
            System.exit(0);
        }

        Main Scraper = new Main(args[0]);

        Scraper.getTitle();
        Scraper.getDescription();
        Scraper.getPublisher();
        Scraper.getPrice();
        Scraper.getRating();

        // Prints a String that looks like a JSON and matches the order
        Scraper.buildOutputString();
    }

    public void getTitle() {
        // gets both the full title and the displayed title
        this.fulltitle = doc.title();  // not used but might be useful in the future
        this.title = doc.select("div.id-app-title").text();
    }

    public void getDescription() {
        this.description = doc.select("div.text-body > div").html().split("<br>")[0].trim();
    }

    public void getPublisher() {
        this.publisher = doc.select("a.document-subtitle.primary > span").text();
    }

    public void getPrice() {
        this.price = doc.select("button.price > span:nth-child(3)").text().split(" ")[0];
    }

    public void getRating() {
        this.rating = this.doc.select("div.score-container > div.score").text();
    }

    public void buildOutputString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("    \"title\": \"" + this.title + "\",\n");
        sb.append("    \"description\": \"" + this.description + "\",\n");
        sb.append("    \"publisher\": \"" + this.publisher + "\",\n");
        sb.append("    \"price\": \"" + this.price + "\",\n");
        sb.append("    \"rating\": \"" + this.rating + "\",\n");
        sb.append("}");
        System.out.println(sb.toString());


    }
}
