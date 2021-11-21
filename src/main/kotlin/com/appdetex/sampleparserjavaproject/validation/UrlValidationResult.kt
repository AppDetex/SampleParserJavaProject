package com.appdetex.sampleparserjavaproject.validation

import java.net.URL

sealed class UrlValidationResult {
    data class Success(val url: URL) : UrlValidationResult()
    data class MissingId(val message: String) : UrlValidationResult()
    data class WrongPath(val message: String) : UrlValidationResult()
    data class WrongDomain(val message: String) : UrlValidationResult()
    data class UnsecuredUrl(val message: String) : UrlValidationResult()
    data class MalformedUrl(val message: String, val cause: Exception?) : UrlValidationResult()
}
