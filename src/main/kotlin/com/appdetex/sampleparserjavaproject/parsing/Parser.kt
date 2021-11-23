package com.appdetex.sampleparserjavaproject.parsing

import org.jsoup.nodes.Document

interface Parser {
    fun parse(doc: Document) : ParseResult
}