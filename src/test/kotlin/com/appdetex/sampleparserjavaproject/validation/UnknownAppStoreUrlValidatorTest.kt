package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore.UnknownAppStore
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import java.net.URL

/**
 * https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US
 */
class UnknownAppStoreUrlValidatorTest : StringSpec({

    val unknownDomain = "some.domain.com"
    val unknownPath = "/path/to/some/app"

    val unknownAppStore = UnknownAppStore(unknownDomain, unknownPath)
    val unknownAppStoreUrlValidator: UrlValidator = UnknownAppStoreUrlValidator(unknownAppStore)

    val goodUrl = URL("https://$unknownDomain$unknownPath?id=com.mojang.minecraftpe&hl=en-US")
    "unkown url happy path" {
        unknownAppStoreUrlValidator.validate(goodUrl)
            .shouldBeInstanceOf<Success>()
    }

    val unsecuredUrl = URL("http://$unknownDomain$unknownPath?id=com.mojang.minecraftpe&hl=en-US")
    "unkown storeurl must be https or it will return UnsecuredUrl result" {
        unknownAppStoreUrlValidator.validate(unsecuredUrl)
            .shouldBeInstanceOf<Failed.UnsecuredUrl>()
    }

    val urlWithWrongDomain = URL("https://plays.google.com/$unknownPath?id=com.mojang.minecraftpe&hl=en-US")
    "invalid unkown store domain returns WrongDomain result" {
        unknownAppStoreUrlValidator
            .validate(urlWithWrongDomain)
            .shouldBeInstanceOf<Failed.WrongDomain>()
    }

    val urlWithInvalidPath = URL("https://$unknownDomain/store/app/details?id=com.mojang.minecraftpe&hl=en-US")
    "invalid unknown store path returns WrongPath result" {
        unknownAppStoreUrlValidator.validate(urlWithInvalidPath)
            .shouldBeInstanceOf<Failed.WrongPath>()
    }

    val urlwithMissSpelledId = URL("https://$unknownDomain$unknownPath?idd=test&a&&=a&a=")
    "unkown store querystring without app id returns MissingId result" {
        unknownAppStoreUrlValidator.validate(urlwithMissSpelledId)
            .shouldBeInstanceOf<Failed.MissingId>()
    }
})
