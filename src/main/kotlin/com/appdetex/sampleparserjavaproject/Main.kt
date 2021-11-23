package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOConfig
import com.appdetex.sampleparserjavaproject.io.IOService

/**
 * Main Kotlin Class - this was really my Hello World with Kotlin.
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 *
 * I named the main class Crawler, but that's not perfectly
 * appropriate yet. Maybe it'll get there. This has been fun!
 */
fun main(args: Array<String>) {

    val io = IOService(IOConfig())
    Crawler(io).start(args)
}