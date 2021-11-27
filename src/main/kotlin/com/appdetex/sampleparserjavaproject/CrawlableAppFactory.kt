package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.parsing.ParserFactory
import com.appdetex.sampleparserjavaproject.validation.UrlValidatorFactory

internal class CrawlableAppFactory(
    private val io: IOService,
    private val urlValidatorFactory: UrlValidatorFactory,
    private val parserFactory: ParserFactory
) {

    fun instance(appUrl: String): CrawlableApp =
        CrawlableApp(appUrl, io, urlValidatorFactory, parserFactory)
}