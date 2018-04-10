package com.appdetex.sampleparserjavaproject.lib.scrape;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

public class ScrapeStrategyJsoup {

    private static final String PARAGRAPH_SPLIT = "(?m)(?=^\\s{4})";
    private String url;
    Document document;

    public String getAttribute(String fn) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ScrapeParameterException {
        String capitalized = "get" + fn.substring(0, 1).toUpperCase() + fn.substring(1);
        java.lang.reflect.Method method;

        try {
            method = getClass().getMethod(capitalized);

        } catch (NoSuchMethodException e) {
            throw new ScrapeParameterException("Xargs: Input parse error");
        }
        return (String) method.invoke(this);
    }

    public void setUrl(String url) throws IOException {
        this.url = url;
        this.connect();
    }

    private void connect() throws IOException {
        this.document = Jsoup.connect(this.url).get();
    }

    private String htmlToNewlines(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        String s = document.html().replaceAll("\\\\n", "\n");
        return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
    }

    /**
     * Individual Jsoup element locators.
     *
     * I would expand these into classes if the requirements grew even a little.
     * Make it pluggable without code changes.
     */

    public String getTitle() throws IOException {
        return this.document.select("[itemprop=name] > span").first().text();
    }

    public String getDescription() throws IOException {
        String description = this.document.select("[itemprop=description] > content > div").html();
        int newLineIndex = description.indexOf("\n");
        return this.htmlToNewlines(description).substring(0, newLineIndex);
    }

    public String getPublisher() throws IOException {
        return this.document.select(":contains(Offered By) + div > span").text();
    }

    public String getPrice() throws IOException {
        return this.document.select("[itemprop=price]").attr("content");
    }

    public String getRating() throws IOException {
        return this.document.select("[itemprop=ratingValue]").attr("content");
    }
}
