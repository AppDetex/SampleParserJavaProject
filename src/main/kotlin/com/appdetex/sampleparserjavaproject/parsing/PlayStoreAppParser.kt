package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.AppStore.GooglePlayStore
import com.appdetex.sampleparserjavaproject.model.google.PlayStoreApi
import kotlinx.serialization.decodeFromString as fromJsonStringTo
import kotlinx.serialization.json.Json
import org.jsoup.nodes.Document
import java.text.NumberFormat
import java.util.*
import kotlin.math.round

internal class PlayStoreAppParser(val appStore: GooglePlayStore) : Parser {

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