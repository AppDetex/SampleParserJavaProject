package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App

sealed class ParseResult {
    data class Success(val app: App) : ParseResult()
    sealed class Failed(open val message: String) : ParseResult()
}

