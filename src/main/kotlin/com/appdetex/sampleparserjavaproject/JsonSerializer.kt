package com.appdetex.sampleparserjavaproject

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

object JsonSerializer {
    // https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/json.md
    private val prettyFormat = Json { prettyPrint = true }

    fun asJson(app: GooglePlayApp): String {
        return prettyFormat.encodeToString(app)
    }
}