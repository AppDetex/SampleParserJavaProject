package com.appdetex.sampleparserjavaproject.parsing

import org.jsoup.nodes.Document

/**
 * Placeholder for the Unknown app parser
 */
internal class UnknownAppParser : Parser {

    override fun parse(doc: Document): ParseResult = ParseResult.Failed("Not yet implemented")
}