package com.appdetex.sampleparserjavaproject.parser.elementhandler;

import com.appdetex.sampleparserjavaproject.DataFieldConsts;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

@Slf4j
public class RatingHandler implements ElementHandler {
    @Override
    public Elements seek(Document doc) {
        // Seek 'price'
        //<div class="BHMmbe" aria-label="Rated 4.6 stars out of five stars">4.6</div>
        return doc.select("div.BHMmbe");
    }

    @Override
    public String parse(Elements els, final Map<String, String> outputData) {
        String value = els.stream()
                .map(Element::text)
                .findFirst()
                .orElse("NONE FOUND");

        outputData.put(DataFieldConsts.RATING.name(), value);

        return value;
    }
}
