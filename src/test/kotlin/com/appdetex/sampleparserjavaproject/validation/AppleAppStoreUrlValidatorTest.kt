package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore.AppleAppStore
import com.appdetex.sampleparserjavaproject.validation.UrlValidationResult.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * https://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276
 */
class AppleAppStoreUrlValidatorTest : StringSpec({

    val appleAppStoreUrlValidator: UrlValidator = AppleAppStoreUrlValidator(AppleAppStore())

    "app store url happy path" {
        appleAppStoreUrlValidator
            .validate("https://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276")
            .shouldBeInstanceOf<Success>()
    }

    "malformed app store url returns MalformedUrl result" {
        appleAppStoreUrlValidator
            .validate("httpss://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276")
            .shouldBeInstanceOf<MalformedUrl>()
    }

    "app store url must be https returns UnsecuredUrl result" {
        appleAppStoreUrlValidator
            .validate("ftp://apps.apple.com")
            .shouldBeInstanceOf<UnsecuredUrl>()
    }

    "invalid app store domain returns WrongDomain result" {
        appleAppStoreUrlValidator
            .validate("https://app.apple.com")
            .shouldBeInstanceOf<WrongDomain>()
    }

    "misspelled app path prefix returns WrongPath result" {
        appleAppStoreUrlValidator
            .validate("https://apps.apple.com/us/ap/multicraft-build-and-mine/id1174039276")
            .shouldBeInstanceOf<WrongPath>()
    }

    "app path does not include app slug returns WrongPath result" {
        appleAppStoreUrlValidator
            .validate("https://apps.apple.com/us/app/id1174039276")
            .shouldBeInstanceOf<WrongPath>()
    }

    "app store path without app id returns MissingId result" {
        appleAppStoreUrlValidator
            .validate("https://apps.apple.com/us/app/multicraft-build-and-mine/i1174039276")
            .shouldBeInstanceOf<MissingId>()
    }
})
