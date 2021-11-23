package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.AppStore

/**
 * Factory pattern for choosing a Parser.
 */
internal object ParserFactory {
    fun instance(appStore: AppStore): Parser =
        when (appStore) {
            is AppStore.GooglePlayStore -> JsonLDStoreAppParserWithPriceList()
            is AppStore.AppleAppStore -> JsonLDStoreAppParser()
            is AppStore.UnknownAppStore -> JsonLDStoreAppParser()
        }
}