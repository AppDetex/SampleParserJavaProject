package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOConfig
import com.appdetex.sampleparserjavaproject.io.IOService

/**
 * Main Kotlin Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
fun main(args: Array<String>) {

    val io = IOService(IOConfig())
    Crawler(io).start(args)

// TODO("write some tests")
// TODO("create GooglePlayAppParser")
//         TODO("get app title")
//         TODO("get first paragraph of the description")
//         TODO("get publisher's name")
//         TODO("get price")
//            TODO("regular price")
//            TODO("free price")
// TODO("create info log file")
    //    val minecraft = File("minecraft-app-page.html")
    //    val doc = Jsoup.parse(minecraft, "UTF-8")
    //    val doc =  Jsoup.connect("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
    //    val doc =  Jsoup.connect("https://play.google.com/store/apps/details?id=com.roblox.client&hl=en-US")
    //    val doc =  Jsoup.connect("https://play.google.com/store/apps/details?id=com.innersloth.spacemafia&hl=en-US")
    //        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36")
    //        .get()
    //    val log = File("minecraft-app-page.html")
    //    log.appendText(doc.outerHtml())
// TODO("create debug log file with raw request data")
// TODO("create info log file entry for each successful retrieval")
// TODO("create error log file for failed retrievals with error messages")
// TODO("sanitize content for bad actors")
// TODO("check for scoping gafs")
// TODO("security pass analysis")
// TODO("do a fluency pass. Does everything read correctly")
// TODO("clean up documentation")
// TODO("clean up PR")
/**
 * TODO("check AppDetex grading")
 * It just occured to me that may be AppDetex has a program that
 * runs a bunch of tests against this app from the command line.
 * If that is the case we would want to respond differently for
 * command line input.
 */
// TODO("submit the PR and go celebrate with friends")
}