package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.model.AppStore.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf

class UrlValidatorFactoryTest : StringSpec({

    "instance(GooglePlayStore) returns a GooglePlayStoreUrlValidator" {
        UrlValidatorFactory.instance(GooglePlayStore())
            .shouldBeInstanceOf<GooglePlayStoreUrlValidator>()
    }

    "instance(AppleAppStore) returns a AppleAppStoreUrlValidator" {
        UrlValidatorFactory.instance(AppleAppStore())
            .shouldBeInstanceOf<AppleAppStoreUrlValidator>()
    }

    "instance(UnknownAppStore) returns a UnknownAppStoreUrlValidator" {
        UrlValidatorFactory.instance(UnknownAppStore("some.store.com","/some/path/to/app"))
            .shouldBeInstanceOf<UnknownAppStoreUrlValidator>()
    }
})