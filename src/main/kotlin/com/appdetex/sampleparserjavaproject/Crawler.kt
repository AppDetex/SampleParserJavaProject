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