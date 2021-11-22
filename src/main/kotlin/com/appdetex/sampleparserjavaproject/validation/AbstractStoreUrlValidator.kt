package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.model.AppStore
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.*
import java.net.URL


internal abstract class AbstractStoreUrlValidator(
    private val appStore: AppStore,
) : UrlValidator {

    override fun validate(appUrl: URL) : ValidationResult =
        when {
            badDomain(appUrl) -> Failed.WrongDomain("Invalid domain. The domain should be ${appStore.domain}, but we received ${appUrl.host}.")
            urlNotSecured(appUrl) -> Failed.UnsecuredUrl("Url must use https for your protection.")
            idNotSupplied(appUrl) -> Failed.MissingId(getIdNotSuppliedMessage(appUrl))
            badPath(appUrl) -> Failed.WrongPath(getWrongPathMessage(appUrl))
            else -> Success(appUrl)
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
