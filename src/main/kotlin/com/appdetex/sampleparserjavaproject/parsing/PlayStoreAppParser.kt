package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.google.PlayStoreApi
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
 * Luckily I was able to find the whole app embedded in json
 * form in a <script> tag. Then it was just a matter of mapping
 * fields and deserializing the json.
 *
 * @constructor Create empty Play store app parser
 */
internal class PlayStoreAppParser : Parser {

    private val deserialize = Json { ignoreUnknownKeys = true }

    override fun parse(doc: Document): ParseResult {

        val playStoreApp =
            deserialize.fromJsonStringTo<PlayStoreApi.PlayStoreApp>(
                doc.select("body * script[type=application/ld+json]").html())

        return ParseResult.Success(
            App(title = playStoreApp.name,
                description = format(playStoreApp.description),
                publisher = playStoreApp.author.name,
                price = format(playStoreApp.offers[0]),
                rating = format(playStoreApp.aggregateRating.ratingValue)))
    }

    private fun format(description: String) : String =
        description.substringBefore("\n")

    private fun format(rating: Float) : Float =
        round(rating * 10) / 10

    private fun format(price: PlayStoreApi.Price) : String =
        getCurrencyFormatter(price.priceCurrency).format(price.price)


    private fun getCurrencyFormatter(currencyCode: String) : NumberFormat {
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 2
        formatter.currency = Currency.getInstance(currencyCode)
        return formatter
    }

}