package com.appdetex.sampleparserjavaproject.validation

import java.net.URL

interface UrlValidator {
    fun validate(appUrl: URL) : ValidationResult
}