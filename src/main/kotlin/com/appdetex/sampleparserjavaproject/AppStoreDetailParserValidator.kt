package com.appdetex.sampleparserjavaproject

import java.net.MalformedURLException
import java.net.URL

class AppStoreDetailParserValidator {
    private val GOOGLE_PLAY: String = "play.google.com"

    /** Returns a URL instance if the url is valid. An AppStoreDetailParserException is thrown otherwise. */
    fun isValidUrl(url: String): URL {
        try {
            return URL(url)
        } catch (e: MalformedURLException) {
            throw AppStoreDetailParserException("Invalid url was supplied to parser.", e)
        }
    }

    /** Returns true if our parser supports the passed url, false otherwise. */
    fun isSupportedSite(domain: String): Boolean {
        return (domain == GOOGLE_PLAY)
    }

}