package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.AppStore.AppleAppStore
import java.net.URL

internal class AppleAppParser(val appStore: AppleAppStore) : Parser {

    override fun parse(url: URL): ParseResult {
        TODO("Not yet implemented")
    }
}