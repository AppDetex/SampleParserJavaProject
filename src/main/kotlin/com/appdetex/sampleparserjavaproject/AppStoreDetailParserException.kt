package com.appdetex.sampleparserjavaproject

import java.lang.Exception

class AppStoreDetailParserException : Exception {

    constructor(message: String?) : super(message)

    constructor(message: String?, throwable: Throwable) : super(message, throwable)
}