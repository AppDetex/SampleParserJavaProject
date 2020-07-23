package com.appdetex.sampleparserjavaproject

/** Contains meta-data for a single app taken from the app's detail page. */
class MobileApp(title: String, description: String, publisher: String, price: String, rating: Float) {
    var title: String = title
    var description: String = description
    var publisher: String = publisher
    var price: String = price
    var rating: Float = rating
}