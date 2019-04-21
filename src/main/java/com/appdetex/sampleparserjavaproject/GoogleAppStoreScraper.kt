package com.appdetex.sampleparserjavaproject

import org.apache.commons.validator.routines.UrlValidator
import org.jsoup.Jsoup


/**
 * Fetch, Scrape, and return a data object of the request application
 * from the Google App Store.
 *
 * Validates the passed url is a vaild url.
 * Attempts to scrape page assuming the format has not changed since 2019-04-20 @ 1802MST
 *
 * @param url Full url to scrape from Google Store
 *
 */
class GoogleAppStoreScraper(url: String){
    private val target = url


    /**
     * Scrapes App page
     * @return GoogleAppStoreInfo either with default values or Results of page scrape
     */
    private fun scrapedApp(): GoogleAppStoreInfo {
        var appInfo = GoogleAppStoreInfo()

        Jsoup.connect(target).ignoreHttpErrors(true).get().run {
            select("[class=\"LXrl4c\"]").forEachIndexed { _, element ->
                val name = element.select("[itemprop=\"name\"]").text()
                val fullDesc = element.select("[itemprop=\"description\"]").text()
                val description = fullDesc.split('\n').first()

                val publisher = element.select("div:contains(offered by) > span > div > span").text()


                val price = element.select("[itemprop=\"price\"]").attr("content")
                val rating = element.select("[aria-label*=stars out of five stars]").text().toFloat()

                appInfo = GoogleAppStoreInfo(
                        appTitle = name,
                        price = price,
                        rating = rating,
                        publisher = publisher,
                        description = description
                )

            }
        }

        return appInfo
    }

    /**
     * Validates that URL is a "valid" url.
     * @return boolean
     */
    private fun validUrl(): Boolean {
        val urlValidator = UrlValidator()
        return urlValidator.isValid(this.target)
    }


    /**
     * If valid url, scrapes store
     * Otherwise, returns default values from GoogleApp
     * @return GoogleAppStoreInfo
     */
    fun result(): GoogleAppStoreInfo {
        if( validUrl() ) {
            return scrapedApp()
        }

        return GoogleAppStoreInfo()
    }
}
