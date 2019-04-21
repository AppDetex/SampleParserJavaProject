package com.appdetex.sampleparserjavaproject

/**
 * A data container for App information from
 * Google App Store
 *
 * This class has no useful logic; It is a data class only
 *
 * @param appTitle Application Title
 * @param description  First paragraph of the description from google app store
 * @param publisher The Application publisher
 * @param price Price of the application
 * @param rating Application rating
 */
data class GoogleAppStoreInfo (
    val appTitle: String = "Unknown Application",
    val description: String = "Unknown Application",
    val publisher: String = "Unknown Application",
    val price: String = "Free",
    val rating: Float = 0.0f
    )