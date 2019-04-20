package com.appdetex.sampleparserjavaproject

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */

fun main(args : Array<String>) {
    val url = args.first()


    val appInfo = GoogleAppStoreScraper(url)

    print(appInfo.results())
}
