package com.appdetex.sampleparserkotlinproject

import com.google.gson.Gson

/**
 * Abstract class App used to define an app from any store (Microsoft, Google, Apple)
 */
abstract class App {
    var title: String? = null
    var description: String? = null
    var publisher: String? = null
    var price: String? = null
    var rating: Double? = null

    fun toJson(): String {
        return Gson().toJson(this)
    }
}