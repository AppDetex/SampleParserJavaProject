package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.AppStore.*
import com.appdetex.sampleparserjavaproject.AppStore.Companion.APPLE_APP_STORE_DOMAIN
import com.appdetex.sampleparserjavaproject.AppStore.Companion.GOOGLE_PLAY_STORE_DOMAIN
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import java.net.URL

class AppStoreTest: StringSpec({

    val googlePlayStoreUrl = URL("https://$GOOGLE_PLAY_STORE_DOMAIN/")
    "should be able to create an instance of GooglePlayStore" {
        AppStore.instance(googlePlayStoreUrl)
            .shouldBeInstanceOf<GooglePlayStore>()
    }

    "only one instance of GooglePlayStore should be created" {
        val instance1 = AppStore.instance(googlePlayStoreUrl)
        val instance2 = AppStore.instance(googlePlayStoreUrl)
        instance1 shouldBeSameInstanceAs instance2
    }

    val applePlayStoreUrl = URL("https://$APPLE_APP_STORE_DOMAIN")
    "should be able to create an instance of AppleAppStore" {
        AppStore.instance(applePlayStoreUrl)
            .shouldBeInstanceOf<AppleAppStore>()
    }

    "only one instance of AppleAppStore should be created" {
        val instance1 = AppStore.instance(applePlayStoreUrl)
        val instance2 = AppStore.instance(applePlayStoreUrl)
        instance1 shouldBeSameInstanceAs instance2
    }

    val unknownAppStoreUrl = URL("https://unknown.app.store/")
    "should be able to create an instance of UnknownAppStore" {
        AppStore.instance(unknownAppStoreUrl)
            .shouldBeInstanceOf<UnknownAppStore>()
    }

    "only one instance of each unknown app store should be created per domain" {
        val instance1 = AppStore.instance(unknownAppStoreUrl)
        val instance2 = AppStore.instance(unknownAppStoreUrl)
        instance1 shouldBeSameInstanceAs instance2
    }

    val anotherUnknownAppStoreUrl = URL("https://another.app.store/")
    "multiple uknown app stores with different domains should get their own instance" {
        val instance1 = AppStore.instance(unknownAppStoreUrl)
        val instance2 = AppStore.instance(anotherUnknownAppStoreUrl)
        instance1 shouldNotBeSameInstanceAs instance2
    }
})