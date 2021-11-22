package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.AppStore

internal object ParserFactory {
    fun instance(appStore: AppStore): Parser =
        when (appStore) {
            is AppStore.GooglePlayStore -> GooglePlayAppParser(appStore)
            is AppStore.AppleAppStore -> AppleAppParser(appStore)
            is AppStore.UnknownAppStore -> UnknownAppParser(appStore)
        }
}