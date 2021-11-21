package com.appdetex.sampleparserjavaproject

import java.net.URL

sealed class AppStore {
    abstract val domain: String
    abstract val path: String
    abstract val getId: (URL) -> String?

    /**
     * Example valid Google Play Store url:<br/>
     *
     * https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US
     */
    data class GooglePlayStore(
        override val domain: String = "play.google.com",
        override val path: String = "/store/apps/details",
        override val getId: (URL) -> String? = { url ->
            url.query
                .split('&')
                .filter { param -> param.contains("id=") }
                .map { param -> param.split('=').getOrNull(1) }
                .firstOrNull()
        }
    ) : AppStore()

    /**
     * Example valid Apple App Store url:<br/>
     *
     * https://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276
     */
    data class AppleAppStore(
        override val domain: String = "apps.apple.com",
        override val path: String = "/us/app",
        override val getId: (URL) -> String? = { url ->
            url.path
                .split('/')
                .filter { pathSegment -> pathSegment.startsWith("id") }
                .map { idPart -> idPart.removePrefix("id") }
                .firstOrNull()
        }
    ) : AppStore()
}
