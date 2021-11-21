package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.AppStore.*
import com.appdetex.sampleparserjavaproject.AppStore.Companion.APPLE_APP_STORE_DOMAIN
import com.appdetex.sampleparserjavaproject.AppStore.Companion.GOOGLE_PLAY_STORE_DOMAIN
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs

class AppStoreTest: StringSpec({

    "should be able to create an instance of GooglePlayStore" {
        AppStore.instance("https://$GOOGLE_PLAY_STORE_DOMAIN/")
            .shouldBeInstanceOf<GooglePlayStore>()
    }

    "only one instance of GooglePlayStore should be created" {
        val instance1 = AppStore.instance("https://$GOOGLE_PLAY_STORE_DOMAIN/")
        val instance2 = AppStore.instance("https://$GOOGLE_PLAY_STORE_DOMAIN/")
        instance1 shouldBeSameInstanceAs instance2
    }

    "should be able to create an instance of AppleAppStore" {
        AppStore.instance("https://$APPLE_APP_STORE_DOMAIN")
            .shouldBeInstanceOf<AppleAppStore>()
    }

    "only one instance of AppleAppStore should be created" {
        val instance1 = AppStore.instance("https://$APPLE_APP_STORE_DOMAIN")
        val instance2 = AppStore.instance("https://$APPLE_APP_STORE_DOMAIN")
        instance1 shouldBeSameInstanceAs instance2
    }

    "should be able to create an instance of UnknownAppStore" {
        AppStore.instance("https://unknown.app.store/")
            .shouldBeInstanceOf<UnknownAppStore>()
    }

    "only one instance of each unknown app store should be created per domain" {
        val instance1 = AppStore.instance("https://unknown.app.store/")
        val instance2 = AppStore.instance("https://unknown.app.store/")
        instance1 shouldBeSameInstanceAs instance2
    }

    "multiple uknown app stores with different domains should get their own instance" {
        val instance1 = AppStore.instance("https://unknown.app.store/")
        val instance2 = AppStore.instance("https://another.app.store/")
        instance1 shouldNotBeSameInstanceAs instance2
    }
})