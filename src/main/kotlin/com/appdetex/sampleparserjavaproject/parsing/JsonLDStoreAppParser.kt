package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.JsonLinkedDataApi
import kotlinx.serialization.decodeFromString as fromJsonStringTo
import kotlinx.serialization.json.Json
import org.jsoup.nodes.Document
import java.text.NumberFormat
import java.util.*
import kotlin.math.round

/**
 * Play Store App parser.
 *
 * I spent quite a bit of time trying to find workable
 * css selectors, but the Store's html is not very semantic
 * and some fields like Publisher weren't even marked with
 * any indicator that would last many weeks when the babel
 * compiler obfuscated the class names again.
 *
 * Here are a couple options I was working on before I found
 * the better solution documented below:
 *
 * css selectors:
 *     title > "body * h1[itemprop=name] > span"
 *     description > "body * div[itemprop=description] div"
 *     publisher > "body * h2[aria-label=Description] + div * html-blob"
 *
 * As you can see title and description are okay, but publisher's a real problem.
 *
 * Luckily I was able to find the JSON-LD format. JSON-LD is a standard for
 * expressing linked data in JSON. Learn more here:
 *
 *  https://json-ld.org/learn.html.
 *
 * Then it was just a matter of mapping fields and deserializing the json.
 */
internal open class JsonLDStoreAppParser : Parser {
    protected val jsonLdCssSelector = "html * script[type=application/ld+json]"
    protected val deserialize = Json { ignoreUnknownKeys = true }

    override fun parse(doc: Document): ParseResult {
        val jsonLDApp =
            deserialize.fromJsonStringTo<JsonLinkedDataApi.JsonLDApp>(
                doc.select(jsonLdCssSelector).html())

        return ParseResult.Success(
            App(title = jsonLDApp.name,
                description = format(jsonLDApp.description),
                publisher = jsonLDApp.author.name,
                price = format(jsonLDApp.offers),
                rating = format(jsonLDApp.aggregateRating.ratingValue)))
    }

    protected fun format(description: String) : String =
        description.substringBefore("\n")

    protected fun format(rating: Float) : Float =
        round(rating * 10) / 10

    protected fun getCurrencyFormatter(currencyCode: String) : NumberFormat {
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 2
        formatter.currency = Currency.getInstance(currencyCode)
        return formatter
    }

    private fun format(price: JsonLinkedDataApi.Price) : String =
        getCurrencyFormatter("USD").format(price.price)
}