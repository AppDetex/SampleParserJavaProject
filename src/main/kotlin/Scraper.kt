package scraper

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import java.math.RoundingMode

fun getRating(json: JSONObject): Double {
    return json["ratingValue"].toString().toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
}

fun getPrice(array: JSONArray): String {
    array.forEach { it ->
        val element = it as JSONObject
        if (element["priceCurrency"] == "USD")
            return "$" + element["price"]
    }

    throw Exception("US Price not found")
}

fun getPublisher(json: JSONObject): String {
    return json["name"].toString()
}

fun truncateDescription(description: String): String {
    val index = description.indexOf("\n")
    return if (index > 0) description.substring(0, index) else description
}

suspend fun getJson(url: String): JSONObject {
    val jsonResult = JSONObject()
    HttpClient(Java).use { client ->
        val response: HttpResponse = client.get(url)
        if (response.status.value !in 200..299)
            throw Exception("url connection failure " + response.status.description)
        val htmlString: String = response.receive()

        try {
            val doc = Jsoup.parse(htmlString)
            val elements = doc.select("[type=\"application/ld+json\"]")
            val json = JSONObject(elements.first().data())

            jsonResult.put("title", json["name"])
            jsonResult.put("description", truncateDescription(json["description"] as String))
            jsonResult.put("publisher", getPublisher(json["author"] as JSONObject))
            jsonResult.put("price", getPrice(json["offers"] as JSONArray))
            jsonResult.put("rating", getRating(json["aggregateRating"] as JSONObject))
        } catch (ex: Exception) {
            throw Exception("Unable to parse url response", ex)
        }
    }
    return jsonResult
}

fun main(args: Array<String>) {
    if (args.size != 1)
        throw Exception("url of the page to scrape is required")

    runBlocking {
        println(getJson(args.first()))
    }
}
