package com.appdetex.sampleparserjavaproject

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

class AppStoreDetailParser {
    val ratingPattern: Pattern = Pattern.compile("(\\d+.\\d+)")
    var validator: AppStoreDetailParserValidator = AppStoreDetailParserValidator()

    fun parse(inputUrl: String): MobileApp {
        var url: URL = validator.isValidUrl(inputUrl)
        if (!validator.isSupportedSite(url.host)) {
            throw AppStoreDetailParserException(String.format("The passed url having host: %s is not supported.", url.host))
        }

        // if we get here, we know the url is valid so proceed to parse
        var doc: Document = Jsoup.connect(inputUrl).get()

        //println(doc)

        var title: String = extractTitle(doc)
        var description: String = extractDescription(doc)
        var publisher: String = extractPublisher(doc)
        var price: String = extractPrice(doc)
        var rating: Float = extractRating(doc)

        return MobileApp(title, description, publisher, price, rating)
    }

    // If we had to support multiple app stores, we could move these extract functions into an abstract class
    // and then get a specific config or implementation by using a factory

    fun extractTitle(doc: Document): String {
        // title: <h1 class="AHFaub" itemprop="name"><span>Minecraft</span></h1>

        return doc.getElementsByAttributeValue("itemprop", "name").first().child(0).text()
    }

    fun extractDescription(doc: Document): String {
        // description: <div jsname="sngebd"> (need entire description)

        var descBlock: String = doc.getElementsByAttributeValue("jsname", "sngebd").first().html()
        return descBlock.split("\n").get(0) // first paragraph
    }

    fun extractPublisher(doc: Document): String {
        // publisher: <a href="/store/apps/developer?id=Mojang" class="hrTbp R8zArc">Mojang</a>

        return doc.getElementsByAttributeValueStarting("class", "hrTbp").first().text()
    }

    fun extractPrice(doc: Document): String {
        // price: <meta itemprop="price" content="$6.99"></span></span>$6.99 Buy</button>

        return doc.getElementsByAttributeValue("itemprop", "price").first().attr("content")
    }

    fun extractRating(doc: Document): Float {
        // rating: <div class="pf5lIe"><div aria-label="Rated 4.6 stars out of five stars" role="img">

        var ratingLabel: String = doc.getElementsByAttributeValue("class", "pf5lIe").first().child(0).attr("aria-label")
        var matcher: Matcher = ratingPattern.matcher(ratingLabel)
        if (matcher.find()) {
            var text = matcher.group(1)
            return text.toFloat()
        }
        return 0f
    }

}