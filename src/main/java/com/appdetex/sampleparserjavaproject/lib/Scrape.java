package com.appdetex.sampleparserjavaproject.lib;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Scrape {

    private Map<String, Object> data;
    private String url;
    private Json json;

    public Scrape(Json json) {
        this.json = json;
    }

    public Scrape attributes(List<String> attributes) throws IOException {

        Document doc = null;

        doc = Jsoup.connect(this.url).get(); // Refactor inject Jsoup

        String name = doc.select("[itemprop=name] > span").first().text();
        String description = doc.select("[itemprop=description]").attr("content");
        String publisher = doc.select(":contains(Offered By) + div > span").text();
        String price = doc.select("[itemprop=price]").attr("content");
        String rating = doc.select("[itemprop=ratingValue]").attr("content");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "name", name );
        data.put( "description", description );
        data.put( "publisher", publisher );
        data.put( "price", price );
        data.put( "rating", rating );

        this.setData(data);

        return this;
    }

    public void init(String url) {
        this.setUrl(url);
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String toJson() {
        return json.toJson(this.data);
    }
}