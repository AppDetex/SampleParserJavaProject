package com.appdetex.sampleparserjavaproject.model;

import java.util.HashMap;
import java.util.Map;

public class GooglePlay {
    private GooglePlay() {}

    // For website: https://play.google.com/store/search?q=appdetox&c=apps
    public static final Map<String, String> SEARCH_MODEL = Map.of(
            "title", "div.vWM94c",
            "publisher", "div.LbQbAe",
            "description", "div.omXQ6c",
            "rating", "div.TT9eCd",
            "price", "span.VfPpkd-vQzf8d"
    );

    // For website: https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en=US
    public static final Map<String, String> DETAIL_MODEL = Map.of(
            "title", "h1.Fd93Bb",
            "publisher", "div.Vbfug",
            "description", "div.bARER",
            "rating", "div.TT9eCd",
            "price", "span.VfPpkd-vQzf8d"
    );
}
