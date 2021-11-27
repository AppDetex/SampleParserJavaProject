package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.QUIT_COMMAND
import com.appdetex.sampleparserjavaproject.parsing.ParserFactory
import com.appdetex.sampleparserjavaproject.validation.UrlValidatorFactory

/**
 * Crawler... more like a text gui for scraping apps.
 *
 * Probably wouldn't take too much more to make it a
 * crawler though.
 *
 * This class is broken into three parts:
 *
 * 1. The main game loop that collects user input from
 *    through command line or prompt
 *
 * 2. Validating the urls that are passed are correct
 *    as far as we know.
 *
 * 3. Then parsing the content retrieved from the url
 *
 * I know url validation wasn't even part of the assignment
 * but I think the url validation will make it much easier
 * to add a second app store and then a third.
 *
 * I've already dropped in the underpinnings for the
 * Apple App Store and a miscellaneous unknown store, that
 * might copy Play Store conventions.
 *
 * @property io we inject the IOService to allow us to more
 *              easily test the system
 *
 * @constructor Create empty Crawler
 */
internal class Crawler(val io: IOService) {

    private val crawlable = CrawlableAppFactory(io, UrlValidatorFactory, ParserFactory)

    /**
     * Starts the main application loop.
     *
     * @param args accepts a single app url as an argument
     */
    fun start(args: Array<String>) {
        io.printBanner()
        var userInput: String = io.prompt(args)

        while (userInput != QUIT_COMMAND) {

            crawlable.instance(userInput)
                .validateUrl()
                .scrape()
                .parse()
                .display()

            userInput = io.prompt()
        }
        io.printGoodbye()
    }
}