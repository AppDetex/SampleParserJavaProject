package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore.AppleAppStore
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Success
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Failed
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.withContext
import java.net.URL

/**
 * https://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276
 */
class AppleAppStoreUrlValidatorTest : StringSpec({

    val appleAppStoreUrlValidator: UrlValidator = AppleAppStoreUrlValidator(AppleAppStore())


    val goodUrl = URL("https://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276")
    "app store url happy path" {
        appleAppStoreUrlValidator.validate(goodUrl)
            .shouldBeInstanceOf<Success>()

    }

    val nonHttpsUrl = URL("ftp://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276")
    "app store url must be https returns UnsecuredUrl result" {
        appleAppStoreUrlValidator.validate(nonHttpsUrl)
            .shouldBeInstanceOf<Failed.UnsecuredUrl>()
    }

    val wrongDomainUrl = URL("https://app.apple.com/us/app/multicraft-build-and-mine/id1174039276")
    "invalid app store domain returns WrongDomain result" {
        appleAppStoreUrlValidator.validate(wrongDomainUrl)
            .shouldBeInstanceOf<Failed.WrongDomain>()
    }

    val missSpelledPathPrefix = URL("https://apps.apple.com/us/ap/multicraft-build-and-mine/id1174039276")
    "misspelled app path prefix returns WrongPath result" {
        appleAppStoreUrlValidator.validate(missSpelledPathPrefix)
            .shouldBeInstanceOf<Failed.WrongPath>()
    }

    val urlWithNoSlug = URL("https://apps.apple.com/us/app/id1174039276")
    "app path does not include app slug returns WrongPath result" {
        appleAppStoreUrlValidator.validate(urlWithNoSlug)
            .shouldBeInstanceOf<Failed.WrongPath>()
    }

    val urlWithNoAppId = URL("https://apps.apple.com/us/app/multicraft-build-and-mine/i1174039276")
    "app store path without app id returns MissingId result" {
        appleAppStoreUrlValidator.validate(urlWithNoAppId)
            .shouldBeInstanceOf<Failed.MissingId>()
    }
})
