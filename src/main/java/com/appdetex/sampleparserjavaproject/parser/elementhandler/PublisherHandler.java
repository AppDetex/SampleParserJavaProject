package com.appdetex.sampleparserjavaproject.parser.elementhandler;

import com.appdetex.sampleparserjavaproject.DataFieldConsts;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

@Slf4j
public class PublisherHandler implements ElementHandler {
    @Override
    public Elements seek(Document doc) {
        // Seek 'publisher name'
        //<span class="T32cc UAO9ie"><a href="longUrl" class="hrTbp R8zArc">Mojang</a></span>
        // NOT: <span class="T32cc UAO9ie"><a itemprop="genre" href="longURL" class="hrTbp R8zArc">Arcade</a></span>
        return doc.select("span[class=T32cc UAO9ie] > a[class=hrTbp R8zArc]");
    }

    @Override
    public String parse(Elements els, final Map<String, String> outputData) {
        String value = els.stream()
                .filter(el -> !el.hasAttr("itemprop=genre")) //filter out genre (probably could improve this by putting it up in the seek() method)
                .map(Element::text)
                .findFirst()
                .orElse("NONE FOUND");

        outputData.put(DataFieldConsts.PUBLISHER.name(), value);

        return value;
    }
}
