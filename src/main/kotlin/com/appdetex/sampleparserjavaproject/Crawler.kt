package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.QUIT_COMMAND
import com.appdetex.sampleparserjavaproject.validation.UrlValidatorFactory
import com.appdetex.sampleparserjavaproject.validation.ValidationResult
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Failed.MalformedUrl
import java.net.MalformedURLException
import java.net.URL

internal class Crawler(val io: IOService) {

    fun start(args: Array<String>) {
        io.printBanner()

        var userInput: String = io.prompt(args)
        while (userInput != QUIT_COMMAND) {
            crawl(userInput)
            userInput = io.prompt()
        }
        io.printGoodbye()
    }

    private fun crawl(url: String) : Unit = try {
        val appUrl = URL(url)
        val appStore = AppStore.instance(appUrl)
        val urlValidator = UrlValidatorFactory.instance(appStore)

        when (val result = urlValidator.validate(appUrl)) {
            is ValidationResult.Success -> println("process url next")
            is ValidationResult.Failed -> io.reportError(result, appStore)
        }
    } catch (e: MalformedURLException) {
        val result = MalformedUrl("${e.message}. \"$url\", is not a valid url.", e)
        io.reportError(result)
    }
}