package com.appdetex.sampleparserjavaproject.validation

import java.net.URL

sealed class ValidationResult {
    data class Success(val url: URL) : ValidationResult()
    sealed class Failed(open val message: String) : ValidationResult() {
        data class TooManyArgsSupplied(override val message: String) : Failed(message)
        data class MissingId(override val message: String) : Failed(message)
        data class WrongPath(override val message: String) : Failed(message)
        data class WrongDomain(override val message: String) : Failed(message)
        data class UnsecuredUrl(override val message: String) : Failed(message)
        data class MalformedUrl(override val message: String, val cause: Exception?) : Failed(message)
    }
}
