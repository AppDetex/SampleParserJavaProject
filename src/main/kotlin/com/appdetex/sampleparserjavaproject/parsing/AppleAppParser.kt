package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.AppStore.AppleAppStore
import java.net.URL

internal class AppleAppParser(val appStore: AppleAppStore) : Parser {

    override fun parse(url: URL): ParseResult = ParseResult.Failed("Not yet implemented")
}