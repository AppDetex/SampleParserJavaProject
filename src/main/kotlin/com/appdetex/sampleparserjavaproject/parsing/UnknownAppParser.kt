package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.AppStore.UnknownAppStore
import java.net.URL

internal class UnknownAppParser (val appStore: UnknownAppStore) : Parser {

    override fun parse(url: URL): ParseResult = ParseResult.Failed("Not yet implemented")
}