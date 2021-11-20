package com.appdetex.sampleparserjavaproject

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

// https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/json.md
val prettyFormat = Json { prettyPrint = true }

object JsonSerializer {
    fun asJson(app: GooglePlayApp): String {
        return prettyFormat.encodeToString(app)
    }
}