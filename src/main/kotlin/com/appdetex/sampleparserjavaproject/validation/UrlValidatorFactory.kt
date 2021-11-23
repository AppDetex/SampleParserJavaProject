package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.model.AppStore
import com.appdetex.sampleparserjavaproject.model.AppStore.*

/**
 * Url validator factory for getting Store specific url validators.
 *
 * @constructor Create empty Url validator factory
 */
internal object UrlValidatorFactory {

    fun instance(appStore: AppStore): UrlValidator =
        when (appStore) {
            is GooglePlayStore -> GooglePlayStoreUrlValidator(appStore)
            is AppleAppStore -> AppleAppStoreUrlValidator(appStore)
            is UnknownAppStore -> UnknownAppStoreUrlValidator(appStore)
        }
}