package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.QUIT_COMMAND
import com.appdetex.sampleparserjavaproject.model.AppStore
import com.appdetex.sampleparserjavaproject.parsing.ParseResult
import com.appdetex.sampleparserjavaproject.parsing.ParserFactory
import com.appdetex.sampleparserjavaproject.validation.UrlValidatorFactory
import com.appdetex.sampleparserjavaproject.validation.ValidationResult
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Failed.MalformedUrl
import org.jsoup.Jsoup
import java.net.MalformedURLException
import java.net.URL

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

    companion object {
        const val USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36"
    }

    fun start(args: Array<String>) {
        io.printBanner()
        var userInput: String = io.prompt(args)

        while (userInput != QUIT_COMMAND) {
            handleInput(userInput)
            userInput = io.prompt()
        }
        io.printGoodbye()
    }

    private fun handleInput(url:String) = try {
        val appUrl = URL(url)
        val appStore = AppStore.instance(appUrl)

        when(val result = validate(appStore, appUrl)) {
            is ValidationResult.Success -> parse(appStore, result.url)
            is ValidationResult.Failed -> io.reportError(result, appStore)
        }
    } catch (e: MalformedURLException) {
        val result = MalformedUrl("${e.message}. \"$url\", is not a valid url.", e)
        io.reportError(result)
    }

    private fun parse(appStore: AppStore, url: URL) {
        val doc = Jsoup.connect(url.toString())
            .userAgent(USER_AGENT)
            .get()
        when (val result = ParserFactory
            .instance(appStore)
            .parse(doc)) {
            is ParseResult.Success -> io.display(result.app)
            is ParseResult.Failed -> io.reportError(result, appStore)
        }
    }

    private fun validate(appStore: AppStore, appUrl: URL) : ValidationResult =
        UrlValidatorFactory
            .instance(appStore)
            .validate(appUrl)
}