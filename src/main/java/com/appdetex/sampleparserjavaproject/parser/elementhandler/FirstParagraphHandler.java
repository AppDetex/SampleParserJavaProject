package com.appdetex.sampleparserjavaproject.parser.elementhandler;

import com.appdetex.sampleparserjavaproject.DataFieldConsts;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

@Slf4j
public class FirstParagraphHandler implements ElementHandler {
    @Override
    public Elements seek(Document doc) {
        // Seek 'first paragraph' - appears to be <div jsname="sngebd"> up to first <br>
        return doc.select("div[jsname=sngebd]");
    }

    @Override
    public String parse(Elements els, final Map<String, String> outputData) {
        String value = els.stream()
                .map(Element::html)//Use html since converting to text clobbers the <BR> tags
                .map(s -> s.substring(0, s.indexOf("<br>")))
                .findFirst()
                .orElse("NONE FOUND");

        outputData.put(DataFieldConsts.FIRST_PARAGRAPH.name(), value);

        return value;
    }
}
