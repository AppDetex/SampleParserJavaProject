package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

object JsonSerializer {
    // https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/json.md
    private val prettyFormat = Json { prettyPrint = true }

    fun asJson(app: App): String {
        return prettyFormat.encodeToString(app)
    }
}