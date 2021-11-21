package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore.GooglePlayStore
import com.appdetex.sampleparserjavaproject.validation.UrlValidationResult.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US
 */
class GooglePlayStoreUrlValidatorTest : StringSpec({

    val googlePlayStoreUrlValidator: UrlValidator = GooglePlayStoreUrlValidator(GooglePlayStore())

    "google url happy path" {
        googlePlayStoreUrlValidator
            .validate("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<Success>()
    }

    "malformed google url returns MalformedUrl result" {
        googlePlayStoreUrlValidator
            .validate("httpss://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<MalformedUrl>()
    }

    "google url must be https or it will return UnsecuredUrl result" {
        googlePlayStoreUrlValidator
            .validate("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<UnsecuredUrl>()
    }

    "invalid google domain returns WrongDomain result" {
        googlePlayStoreUrlValidator
            .validate("https://plays.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<WrongDomain>()
    }

    "invalid google path returns WrongPath result" {
        googlePlayStoreUrlValidator
            .validate("https://play.google.com/store/app/details?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<WrongPath>()
    }

    "google querystring without app id returns MissingId result" {
        googlePlayStoreUrlValidator
            .validate("https://play.google.com/store/apps/details?idd=test&a&&=a&a=")
            .shouldBeInstanceOf<MissingId>()
    }
})
