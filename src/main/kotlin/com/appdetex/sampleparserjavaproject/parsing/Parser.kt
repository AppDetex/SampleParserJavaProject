package com.appdetex.sampleparserjavaproject.parsing

import java.net.URL

interface Parser {
    fun parse(url: URL) : ParseResult
}