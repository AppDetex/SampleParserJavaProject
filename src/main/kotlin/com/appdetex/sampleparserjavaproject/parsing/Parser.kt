package com.appdetex.sampleparserjavaproject.parsing

import org.jsoup.nodes.Document

/**
 * The parser has the job of scraping the
 * app store based on the jsoup Document
 * passed to it. Each parser will be very
 * store dependent.
 */
interface Parser {
    fun parse(doc: Document) : ParseResult
}