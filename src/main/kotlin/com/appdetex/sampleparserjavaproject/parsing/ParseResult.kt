package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App

/**
 * This state tree is much less detailed than I'd
 * like. If I work on this further, I will flesh
 * this out a bit to provide the user with more
 * detailed feedback.
 */
sealed class ParseResult {
    data class Success(val app: App) : ParseResult()
    data class Failed(val message: String) : ParseResult()
}

