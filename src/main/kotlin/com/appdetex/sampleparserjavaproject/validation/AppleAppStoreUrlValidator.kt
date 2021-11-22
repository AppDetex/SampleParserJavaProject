package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.model.AppStore.AppleAppStore
import java.net.URL

/**
 * Example valid Apple App Store url:<br/>
 *
 * https://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276
 */
internal class AppleAppStoreUrlValidator(private val appStore: AppleAppStore) : AbstractStoreUrlValidator(appStore) {

    override fun badPath(url: URL): Boolean {
        val path = url.path
        return !(path.correctPrefix()
                && path.includesSlug()
                && path.includesId())
    }

    override fun getWrongPathMessage(url: URL): String = """
        
        Invalid path. 
        Expected path "${appStore.path}/multicraft-build-and-mine/id1174039276", 
        received path "${url.path}"
        
    """.trimIndent()


    override fun getIdNotSuppliedMessage(url: URL): String =
        "The id for the app was not found in the url path."

    private fun String.correctPrefix() = this.startsWith(appStore.path)

    private fun String.includesSlug() = this.count { char -> char == '/' } == 4

    private fun String.includesId() =
        this.split('/')
            .getOrElse(4) { "" }
            .startsWith("id")
}