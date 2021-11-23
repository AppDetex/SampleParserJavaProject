package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.JsonLinkedDataApi
import kotlinx.serialization.decodeFromString as fromJsonStringTo
import org.jsoup.nodes.Document

/**
 * Json-ld store on the Play Store requires offers to be stored in an
 * array.
 *
 * @see JsonLDStoreAppParser
 * @see JsonLinkedDataApi
 */
internal class JsonLDStoreAppParserWithPriceList : JsonLDStoreAppParser() {

    override fun parse(doc: Document): ParseResult {
        val jsonLDApp =
            deserialize.fromJsonStringTo<JsonLinkedDataApi.JsonLDAppWithListPrice>(
                doc.select(jsonLdCssSelector).html())

        return ParseResult.Success(
            App(title = jsonLDApp.name,
                description = format(jsonLDApp.description),
                publisher = jsonLDApp.author.name,
                price = format(jsonLDApp.offers[0]),
                rating = format(jsonLDApp.aggregateRating.ratingValue))
        )
    }

    private fun format(price: JsonLinkedDataApi.PriceAndCurrency) : String =
        getCurrencyFormatter(price.priceCurrency).format(price.price)
}