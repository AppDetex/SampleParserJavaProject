package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore.UnknownAppStore
import com.appdetex.sampleparserjavaproject.validation.UrlValidationResult.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US
 */
class UnknownAppStoreUrlValidatorTest : StringSpec({

    val unknownDomain = "some.domain.com"
    val unknownPath = "/path/to/some/app"

    val unknownAppStore = UnknownAppStore(unknownDomain, unknownPath)
    val unknownAppStoreUrlValidator: UrlValidator = UnknownAppStoreUrlValidator(unknownAppStore)

    "unkown url happy path" {
        unknownAppStoreUrlValidator
            .validate("https://$unknownDomain$unknownPath?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<Success>()
    }

    "malformed unkown storeurl returns MalformedUrl result" {
        unknownAppStoreUrlValidator
            .validate("httpss://$unknownDomain$unknownPath?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<MalformedUrl>()
    }

    "unkown storeurl must be https or it will return UnsecuredUrl result" {
        unknownAppStoreUrlValidator
            .validate("http://$unknownDomain$unknownPath?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<UnsecuredUrl>()
    }

    "invalid unkown storedomain returns WrongDomain result" {
        unknownAppStoreUrlValidator
            .validate("https://plays.google.com/$unknownPath?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<WrongDomain>()
    }

    "invalid unkown store path returns WrongPath result" {
        unknownAppStoreUrlValidator
            .validate("https://$unknownDomain/store/app/details?id=com.mojang.minecraftpe&hl=en-US")
            .shouldBeInstanceOf<WrongPath>()
    }

    "unkown storequerystring without app id returns MissingId result" {
        unknownAppStoreUrlValidator
            .validate("https://$unknownDomain$unknownPath?idd=test&a&&=a&a=")
            .shouldBeInstanceOf<MissingId>()
    }
})
