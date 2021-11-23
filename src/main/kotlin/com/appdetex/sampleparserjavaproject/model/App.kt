package com.appdetex.sampleparserjavaproject.model

import kotlinx.serialization.Serializable

/**
 * The App is why we're all here. This is
 * the primary pojo, or poko?, for collecting
 * the application data in a cohesive format.
 *
 * @property title of the app
 * @property description is the first paragraph of the apps description on the store
 * @property publisher would be the creators and/or distributors of the app
 * @property price what does it sell for? Note that the Play Store has an array of
 *                 prices for each app. Most of those arrays only have one entry, but
 *                 it's still interesting.
 * @property rating displays the average number of stars users have awarded the app with
 */
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
