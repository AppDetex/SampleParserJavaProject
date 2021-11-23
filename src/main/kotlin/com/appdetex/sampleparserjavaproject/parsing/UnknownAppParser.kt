package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.AppStore.UnknownAppStore
import org.jsoup.nodes.Document

internal class UnknownAppParser (val appStore: UnknownAppStore) : Parser {

    override fun parse(doc: Document): ParseResult = ParseResult.Failed("Not yet implemented")
}