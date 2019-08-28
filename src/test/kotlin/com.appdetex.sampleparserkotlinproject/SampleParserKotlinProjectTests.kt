package com.appdetex.sampleparserkotlinproject

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SampleParserKotlinProjectTests {

    private val minecraftUrl: String = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"
    private val malformedUrl: String = "eehttps://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"
    private val nonGooglePlayUrl: String = "https://kotlinlang.org/"

    @Test
    fun `expected null with malformed url test`() {
        val result: App? = GooglePlayApp().extractGooglePlayAppData(malformedUrl)
        assertEquals(null, result)
    }

    @Test
    fun `expected empty json with non google play url test`() {
        val result = GooglePlayApp().extractGooglePlayAppData(nonGooglePlayUrl)
        assertEquals("{}", result!!.toJson())
    }

    @Test
    fun `expected title with minecraft url test`() {
        val result = GooglePlayApp().extractGooglePlayAppData(minecraftUrl)
        assertEquals("Minecraft", result!!.title)
    }

}
