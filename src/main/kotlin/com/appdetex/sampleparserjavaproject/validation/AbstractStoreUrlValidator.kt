package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore
import com.appdetex.sampleparserjavaproject.validation.UrlValidationResult.*
import java.net.MalformedURLException
import java.net.URL


internal abstract class AbstractStoreUrlValidator(
    private val appStore: AppStore,
) : UrlValidator {

    override fun validate(urlString: String) : UrlValidationResult = try {
        val url = URL(urlString)

        when {
            badDomain(url) -> WrongDomain("Invalid domain. The domain should be ${appStore.domain}, but we received ${url.host}.")
            urlNotSecured(url) -> UnsecuredUrl("Url must use https for your protection.")
            idNotSupplied(url) -> MissingId(getIdNotSuppliedMessage(url))
            badPath(url) -> WrongPath(getWrongPathMessage(url))
            else -> Success(url)
        }

    } catch (e: MalformedURLException) {
        MalformedUrl(e.message ?: "no message supplied", e)
    }

    private fun badDomain(url: URL) : Boolean = url.host != appStore.domain

    private fun idNotSupplied(url: URL) : Boolean = appStore.getId(url) == null

    private fun urlNotSecured(url: URL) : Boolean = url.protocol != "https"

    protected open fun badPath(url: URL): Boolean = url.path != appStore.path

    protected open fun getIdNotSuppliedMessage(url: URL): String =
        "The id for the app was not found in the url querystring."

    protected open fun getWrongPathMessage(url: URL): String =
        "Invalid path. The path should be ${appStore.path}, but we received ${url.path}."
}
