package com.appdetex.sampleparserjavaproject.model

import org.jsoup.nodes.Document
import java.net.URL
import java.util.concurrent.ConcurrentHashMap

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
        override val domain: String = GOOGLE_PLAY_STORE_DOMAIN,
        override val path: String = "/store/apps/details",
        override val getId: (URL) -> String? = ::defaultGetId,
        val queries: AppQueries = object : AppQueries {
            override val title = { d: Document -> d.select("body * h1 > span").text() }
            override val description = { d: Document -> "" }
            override val publisher = { d: Document -> "" }
            override val price = { d: Document -> "" }
            override val rating = { d: Document -> 0.0f }
        }
    ) : AppStore()

    /**
     * Example valid Apple App Store url:<br/>
     *
     * https://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276
     */
    data class AppleAppStore(
        override val domain: String = APPLE_APP_STORE_DOMAIN,
        override val path: String = "/us/app",
        override val getId: (URL) -> String? = { url ->
            url.path
                .split('/')
                .filter { pathSegment -> pathSegment.startsWith("id") }
                .map { idPart -> idPart.removePrefix("id") }
                .firstOrNull()
        }
    ) : AppStore()


    /**
     * An unknown app store defaults to the Google Play Store implementation
     * except the domain and path are passed in based on the url that was passed.
     *
     * <p>Example valid Unknown App Store url:
     *
     *     <blockquote>https://some.app.store/store/apps/details?id=com.mojang.minecraftpe</blockquote>
     * </p>
     */
    data class UnknownAppStore(
        override val domain: String,
        override val path: String,
        override val getId: (URL) -> String? = ::defaultGetId
        ) : AppStore()


    companion object {
        const val GOOGLE_PLAY_STORE_DOMAIN = "play.google.com"
        const val APPLE_APP_STORE_DOMAIN = "apps.apple.com"

        private val appStores = ConcurrentHashMap<String, AppStore>()

        /**
         * Factory method for retrieving specific AppStore singleton needed.
         *
         * <p>Current AppStores available are:
         * <ul>
         *     <li>GooglePlayStore</li>
         *     <li>AppleAppStore</li>
         *     <li>UnknownAppStore</li>
         * </ul>
         *
         *  If no recognized app store domain is passed, we will attempt to
         *  do our very best with the little data given by returning an UnknownAppStore.
         *
         * @param url passed into the application
         * @return a specific instance of AppStore or UnknownAppStore
         */
        fun instance(url: URL): AppStore {
            return when (val host = url.host) {
                GOOGLE_PLAY_STORE_DOMAIN -> appStores.getOrPut(host) { GooglePlayStore() }
                APPLE_APP_STORE_DOMAIN -> appStores.getOrPut(host) { AppleAppStore() }
                else -> appStores.getOrPut(host) { UnknownAppStore(host, url.path) }
            }
        }

        /**
         * Default get id function. I make the assumption, maybe poorly
         * that other app stores probably also pass ids as a parameter named id.
         *
         * <p>Assumptions:
         *    <ul>
         *        <li>the id is found in the querystring of the url</li>
         *        <li>the parameter name is "id"</li>
         *    </ul>
         * </p>
         * @return a default reference to a lambda for getting the id from the url querystring
         */
        fun defaultGetId(url: URL) : String? {
            val query = url.query ?: return null
            return query
                .split('&')
                .filter { param -> param.contains("id=") }
                .map { param -> param.split('=').getOrNull(1) }
                .firstOrNull()
        }
    }
}
