package com.appdetex.sampleparserjavaproject

import org.jsoup.Jsoup
import com.google.gson.Gson

class GooglePlay {

    fun getPage(url: String): List<Output> {
        return Jsoup.connect(url)
            .get()
            .getElementsByAttributeValue("type", "application/ld+json")
            .map { element -> element.data()}
            .map { data -> Gson().fromJson(data, PlayData::class.java)}
            .map { playData -> Output(playData.name, playData.description.substringBefore("\n\n"), playData.author.name, playData.offers[0].price, playData.aggregateRating.ratingValue)}
    }
}