package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.QUIT_COMMAND
import com.appdetex.sampleparserjavaproject.model.AppStore
import com.appdetex.sampleparserjavaproject.parsing.ParserFactory
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
        ParserFactory
            .instance(appStore)
            .parse(url)
    }

    private fun validate(appStore: AppStore, appUrl: URL) : ValidationResult =
        UrlValidatorFactory
            .instance(appStore)
            .validate(appUrl)
}