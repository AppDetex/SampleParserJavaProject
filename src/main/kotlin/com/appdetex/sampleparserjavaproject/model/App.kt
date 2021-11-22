package com.appdetex.sampleparserjavaproject.model

import kotlinx.serialization.Serializable

@Serializable
data class App(
    val title: String,
    val description: String,
    val publisher: String,
    val price: String,
    val rating: Float
) {
    companion object {
        const val TITLE_FIELD = "title"
        const val DESCRIPTION_FIELD = "description"
        const val PUBLISHER_FIELD = "publisher"
        const val PRICE_FIELD = "price"
        const val RATING_FIELD = "rating"
    }
}
