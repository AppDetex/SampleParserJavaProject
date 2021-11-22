package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore
import com.appdetex.sampleparserjavaproject.AppStore.*

internal object UrlValidatorFactory {
    fun instance(appStore: AppStore): UrlValidator =
        when (appStore) {
            is GooglePlayStore -> GooglePlayStoreUrlValidator(appStore)
            is AppleAppStore -> AppleAppStoreUrlValidator(appStore)
            is UnknownAppStore -> UnknownAppStoreUrlValidator(appStore)
        }
}