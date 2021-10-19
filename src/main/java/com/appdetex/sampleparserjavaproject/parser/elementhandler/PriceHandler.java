package com.appdetex.sampleparserjavaproject.parser.elementhandler;

import com.appdetex.sampleparserjavaproject.DataFieldConsts;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Map;

@Slf4j
public class PriceHandler implements ElementHandler {
    @Override
    public Elements seek(Document doc) {
        // Seek 'price'
        //<span class="oocvOe"><button aria-label="$7.49 Buy" class=" LkLjZd ScJHi HPiPcc IfEcue  " jscontroller="chfSwc" jsaction="MH7vAb" jsmodel="UfnShf" data-item-id="%.@.&quot;com.mojang.minecraftpe&quot;,7]" jslog="36906; 1:200|CBSqARUKEwiOmdiX3NXzAhVPYWUKHX8/Dxw=; track:click,impression" jsdata="Ddi83b;CgYKBENBRT0=;23"><span><span itemprop="offers" itemscope="" itemtype="https://schema.org/Offer"><meta itemprop="url" content="https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&amp;rdid=com.mojang.minecraftpe&amp;feature=md&amp;offerId">
        // <meta itemprop="price" content="$7.49"></span></span>$7.49 Buy</button></span>
        return doc.select("meta[itemprop=price]");
    }

    @Override
    public String parse(Elements els, final Map<String, String> outputData) {
        String value = els.stream()
                .map(el -> el.attr("content"))
                .findFirst()
                .orElse("NONE FOUND");

        outputData.put(DataFieldConsts.PRICE.name(), value);

        return value;
    }
}
