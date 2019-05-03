package com.appdetex.sampleparserjavaproject.googlePlay;

import com.appdetex.sampleparserjavaproject.Parser;
import com.google.common.base.Strings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AppParser implements Parser<AppData> {
    private static final Pattern RATING_PATTERN = Pattern.compile("Rated (.*) stars out of five stars");

    @Override
    public AppData parse(String html) {
        Document document = Jsoup.parse(html);

        return new AppData(
                parseTitle(document),
                parseDescription(document),
                parsePublisher(document),
                parsePrice(document),
                parseRating(document));
    }

    private Float parseRating(Document document) {
        Elements elements = document.select("div.dNLKff > c-wiz > div.pf5lIe > div[role=img]");

        String ratingSentence = elements.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("couldn't find field=rating"))
                .attr("aria-label");

        Matcher matcher = RATING_PATTERN.matcher(ratingSentence);

        if (matcher.find()) {
            return Float.parseFloat(matcher.group(1));
        } else {
            throw new IllegalStateException("Could not find field=rating");
        }
    }

    private String parsePrice(Document document) {
        Elements elements = document.select("span.oocvOe > button > span > span > meta[itemprop=price]");

        return elements.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("couldn't find field=price"))
                .attr("content");
    }

    private String parsePublisher(Document document) {
        Elements elements = document.select("span.T32cc > a:not([itemprop])");

        return unescape(elements
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("couldn't find field=publisher"))
                .html());
    }

    private String parseDescription(Document document) {
        Elements elements = document.select("content > div[jsname=sngebd]");

        String fullDescription = elements.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("couldn't find field=description"))
                .html();

        if (Strings.isNullOrEmpty(fullDescription)) {
            throw new RuntimeException("couldn't find field=description");
        }

        return Jsoup.parse(fullDescription.split("<br>")[0]).text();
    }

    private String parseTitle(Document document) {
        return unescape(document.select("[itemprop=name] > span")
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("couldn't find field=genre"))
                .html());
    }

    private String unescape(String raw) {
        return org.jsoup.parser.Parser.unescapeEntities(raw, true);
    }

}
