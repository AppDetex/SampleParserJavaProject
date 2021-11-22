package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore.GooglePlayStore
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Success
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Failed
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import java.net.URL

/**
 * https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US
 */
class GooglePlayStoreUrlValidatorTest : StringSpec({

    val googlePlayStoreUrlValidator: UrlValidator = GooglePlayStoreUrlValidator(GooglePlayStore())

    val goodUrl = URL("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
    "google url happy path" {
        googlePlayStoreUrlValidator.validate(goodUrl)
            .shouldBeInstanceOf<Success>()
    }

    val unsecuredUrl = URL("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
    "google url must be https or it will return UnsecuredUrl result" {
        googlePlayStoreUrlValidator.validate(unsecuredUrl)
            .shouldBeInstanceOf<Failed.UnsecuredUrl>()
    }

    val urlWithWrongDomain = URL("https://plays.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
    "invalid google domain returns WrongDomain result" {
        googlePlayStoreUrlValidator.validate(urlWithWrongDomain)
            .shouldBeInstanceOf<Failed.WrongDomain>()
    }

    val urlWithWrongPath = URL("https://play.google.com/store/app/details?id=com.mojang.minecraftpe&hl=en-US")
    "invalid google path returns WrongPath result" {
        googlePlayStoreUrlValidator.validate(urlWithWrongPath)
            .shouldBeInstanceOf<Failed.WrongPath>()
    }

    val urlWithIdMissSpelled = URL( "https://play.google.com/store/apps/details?idd=test&a&&=a&a=")
    "google querystring without app id returns MissingId result" {
        googlePlayStoreUrlValidator.validate(urlWithIdMissSpelled)
            .shouldBeInstanceOf<Failed.MissingId>()
    }
})
