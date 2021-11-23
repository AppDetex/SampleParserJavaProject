package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.AppStore.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf

class ParseFactoryTest : StringSpec({

    "instance(GooglePlayStore) returns a GooglePlayAppParser" {
        ParserFactory.instance(GooglePlayStore())
            .shouldBeInstanceOf<JsonLDStoreAppParserWithPriceList>()
    }

    "instance(AppleAppStore) returns a AppleAppParser" {
        ParserFactory.instance(AppleAppStore())
            .shouldBeInstanceOf<JsonLDStoreAppParser>()
    }

    "instance(UnknownAppStore) returns a UnknownAppStoreUrlValidator" {
        ParserFactory.instance(UnknownAppStore("some.store.com", "/some/path/to/app"))
            .shouldBeInstanceOf<JsonLDStoreAppParser>()
    }
})