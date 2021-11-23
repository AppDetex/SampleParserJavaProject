package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

/**
 * Simple singleton for pretty formatting
 * Kotlin serialization library is pretty sweet. I need to spend more time
 * reading the docs later.
 *
 * https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/json.md
 */
object JsonSerializer {
    // https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/json.md
    private val prettyFormat = Json { prettyPrint = true }
    private val deserialize = Json { ignoreUnknownKeys = true }

    fun asJson(app: App): String {
        return prettyFormat.encodeToString(app)
    }

    fun fromString(string: String) : String {
        return deserialize.encodeToString(string)
    }
}