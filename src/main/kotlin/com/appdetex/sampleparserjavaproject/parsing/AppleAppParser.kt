package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.AppStore.AppleAppStore
import org.jsoup.nodes.Document

internal class AppleAppParser(val appStore: AppleAppStore) : Parser {

    override fun parse(doc: Document): ParseResult = ParseResult.Failed("Not yet implemented")
}