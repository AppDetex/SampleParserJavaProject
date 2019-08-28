package com.appdetex.sampleparserkotlinproject

import org.apache.logging.log4j.kotlin.Logging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

/**
 * Google Play Class uses Jsoup to retrieve a provided Url
 * and parses out particular data, retrieved uniquely per Google Play.
 * Sets App vals using parsed data
 */


class GooglePlayApp : Logging, App() {

    fun extractGooglePlayAppData(url: String): App? {

        val doc: Document
        try {
            doc = Jsoup.connect(url).get()
        } catch (e: Exception) {
            logger.error("Error retrieving data from url: $url")
            return null
        }

        try {
            val ratingElement: Element = doc.select(".BHMmbe").first()
            rating = ratingElement.getElementsByTag("div").text().toDouble()

        } catch (e: Exception) {
            logger.error("Unable to parse app rating, check if html accessor has changed")
        }
        try {
            val titleElement = doc.select("h1[itemprop=name]").first()
            title = titleElement.getElementsByTag("span").text()
        } catch (e: Exception) {
            logger.error("Unable to parse app name, check if html accessor has changed")
        }

        try {
            val descriptionElement = doc.select("div[itemprop=description]").first()
            description = descriptionElement.getElementsByTag("div").text()
        } catch (e: Exception) {
            logger.error("Unable to parse app description, check if html accessor has changed")
        }

        try {

            val publisherElement = doc.select(".hAyfc:contains(Offered By)").first()
            publisher = publisherElement.getElementsByTag("span").first().text()

        } catch (e: Exception) {
            logger.error("Unable to parse app publisher, check if html accessor has changed")
        }

        try {
            val priceElement = doc.select("meta[itemprop=price]").first()
            price = priceElement.attr("content")
        } catch (e: Exception) {
            logger.error("Unable to parse app price, check if html accessor has changed")
        }

        return this
    }
}

