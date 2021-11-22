package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.AppStore.UnknownAppStore
import java.net.URL

internal class UnknownAppParser (val appStore: UnknownAppStore) : Parser {

    override fun parse(url: URL): ParseResult {
        TODO("Not yet implemented")
    }
}