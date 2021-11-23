package com.appdetex.sampleparserjavaproject.validation

import java.net.URL

/**
 * I'm really digging sealed classes and when() for managing state.
 *
 * Validation result gives a detailed map of all possible fail states,
 * and the success state. I'd like to explore this even furthers.
 * Maybe have a single state tree for the whole app.
 *
 * @constructor Create empty Validation result
 */
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
