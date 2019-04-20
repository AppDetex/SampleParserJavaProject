package com.appdetex.sampleparserjavaproject

import org.apache.commons.validator.UrlValidator;
import org.jsoup.Jsoup


class GoogleAppStoreScraper(url: String){
    private val target = url


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

    private fun validUrl(): Boolean {
        val urlValidator = UrlValidator()
        return urlValidator.isValid(this.target)
    }

    fun result(): GoogleAppStoreInfo {
        if( validUrl() ) {
            return scrapedApp()
        }

        return GoogleAppStoreInfo()
    }
}
