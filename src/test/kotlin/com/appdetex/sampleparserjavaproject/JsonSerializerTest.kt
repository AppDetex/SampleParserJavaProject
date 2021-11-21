package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.GooglePlayApp.Companion.DESCRIPTION_FIELD
import com.appdetex.sampleparserjavaproject.GooglePlayApp.Companion.PRICE_FIELD
import com.appdetex.sampleparserjavaproject.GooglePlayApp.Companion.PUBLISHER_FIELD
import com.appdetex.sampleparserjavaproject.GooglePlayApp.Companion.RATING_FIELD
import com.appdetex.sampleparserjavaproject.GooglePlayApp.Companion.TITLE_FIELD
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class JsonSerializerTest : StringSpec ({
    val app = GooglePlayApp(title, description, publisher, price, rating)
    val json = JsonSerializer.asJson(app)

    "$RATING_FIELD included in JSON" {
        json shouldContain "\"$RATING_FIELD\": $rating"
    }

    // there is a better way to do these with data driven tests but the feature is still experimental
    // see https://kotest.io/docs/framework/datatesting/data-driven-testing.html
    // 5 tests in one
    listOf(
        Pair(title, title),
        Pair(description, description),
        Pair(publisher, publisher),
        Pair(PRICE_FIELD, price),
    ).forEach { (field, value) ->
        "$field included in JSON" {
            json shouldContain "\"$field\": \"$value\""
        }
    }

    "json should be pretty printed" {
        json shouldBe prettyFormat
    }

})

private const val title = TITLE_FIELD
private const val description = DESCRIPTION_FIELD
private const val publisher = PUBLISHER_FIELD
private const val price = "$0.00"
private const val rating = 11.0f

private val prettyFormat = """
{
    "$TITLE_FIELD": "$title",
    "$DESCRIPTION_FIELD": "$description",
    "$PUBLISHER_FIELD": "$publisher",
    "$PRICE_FIELD": "$price",
    "$RATING_FIELD": $rating
}
        """.trimIndent()
