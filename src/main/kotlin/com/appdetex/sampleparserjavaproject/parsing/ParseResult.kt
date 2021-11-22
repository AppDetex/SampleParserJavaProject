package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App

sealed class ParseResult {
    data class Success(val app: App) : ParseResult()
    data class Failed(val message: String) : ParseResult()
}

