package com.appdetex.sampleparserjavaproject.domain


data class AppStoreSynopsis(
  val type: String? = null,
  val name: String? = null,
  val description: String? = null,
  val operatingSystem: String? = null,
  val applicationCategory: String? = null,
  val image: String? = null,
  val contentRating: String? = null,
  val author: Author? = null,
  val aggregateRating: AggregateRating? = null,
  val offers: List<Offer>? = null
) {
  private val descriptionFirstParagraph: String by lazy {
    description?.split("\n")?.firstOrNull() ?: UNKNOWN
  }

  fun toDisplay(): Display = Display(
    title = name ?: UNKNOWN,
    description = descriptionFirstParagraph ?: UNKNOWN,
    publisher = author?.name ?: UNKNOWN,
    rating = aggregateRating?.simpleRating ?: UNKNOWN,
    price = offers?.firstOrNull()?.priceWithSymbol ?: UNKNOWN
  )

  data class Author(
    val type: String? = null,
    val name: String? = null,
    val url: String? = null
  )

  data class AggregateRating(
    val type: String? = null,
    val ratingValue: String? = null,
    val ratingCount: String? = null
  ) {
    val simpleRating: String? by lazy {
      ratingValue
        ?.substring(0, 3)
    }
  }

  data class Offer(
    val type: String? = null,
    val price: String? = null,
    val priceCurrency: String? = null,
    val availability: String? = null
  ) {
    /** normally I would map this by [Offer.priceCurrency] and an enum **/
    val priceWithSymbol: String? by lazy {
      price?.let { "\$$it" }
    }
  }

  data class Display(
    val title: String = UNKNOWN,
    val description: String = UNKNOWN,
    val publisher: String = UNKNOWN,
    val price: String = UNKNOWN,
    val rating: String = UNKNOWN
  )

  companion object {
    const val UNKNOWN = "UNKNOWN"
  }
}