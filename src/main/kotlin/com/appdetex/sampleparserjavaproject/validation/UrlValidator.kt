package com.appdetex.sampleparserjavaproject.validation

interface UrlValidator {
    fun validate(urlString: String) : UrlValidationResult
}