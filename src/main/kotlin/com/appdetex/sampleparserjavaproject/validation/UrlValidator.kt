package com.appdetex.sampleparserjavaproject.validation

import java.net.URL

/**
 * Url validator interface for promoting loose coupling.
 *
 * @constructor Create empty Url validator
 */
interface UrlValidator {

    /**
     * validate's the app url. The only state that this method doesn't
     * validate is the MalformedUrl state, but we needed for that to
     * occur earlier in the app state so that we have access to the url
     * parts sooner for selecting specific AppStore and validator instances
     * with the UrlValidatorFactory.instance(AppStore)
     *
     * @param appUrl is the app's url, not the store url
     * @return Success if everthing goes well, or one of the Failed states if not
     */
    fun validate(appUrl: URL) : ValidationResult
}