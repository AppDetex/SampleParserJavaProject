package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.playstore.LdJsonScraper
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.URL

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Usage: SampleJavaParserProject <PlayStoreURL>")
        return
    }
    try {
        val doc = Jsoup.parse(URL(args.first()), 2000)
        val extracted = Scrapers.playStoreScraper.extract(doc)
        val output = GsonBuilder().setPrettyPrinting().create().toJson(extracted)
        println(output)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

object Scrapers {
    val playStoreScraper = DocumentScraper(ldJsonFieldExtractors)
}

val ldJsonFieldExtractors = mapOf("title" to LdJsonScraper { json -> json.get("name").asString },
        "description" to LdJsonScraper { json -> json.get("description").asString.lines().first() },
        "publisher" to LdJsonScraper { json -> json.getAsJsonObject("author").get("name").asString },
        "price" to LdJsonScraper { json -> json.getAsJsonArray("offers").map { it.asJsonObject.get("price").asString }.first() },
        "rating" to LdJsonScraper(::getRating))

private fun getRating(json: JsonObject): String {
    val rating = json.getAsJsonObject("aggregateRating").get("ratingValue").asDouble
    return String.format("%.1f", rating)
}

