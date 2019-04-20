package com.appdetex.sampleparserjavaproject

import com.google.gson.Gson

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */

fun main(args : Array<String>) {
    val url = args.first()
    val gson = Gson()


    // Send our url from command line to scraper
    val appInfo = GoogleAppStoreScraper(url)

    // convert data class result to json
    val appJson = gson.toJson(appInfo.result())

    // Drop it to std out
    print(appJson)
}
