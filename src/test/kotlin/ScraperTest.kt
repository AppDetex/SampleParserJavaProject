package scraper.tests

import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import scraper.getJson
import scraper.main
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ScraperTest {

    @Test
    fun testNoArguments() {
        assertFailsWith<Exception> {
            main(arrayOf())
        }
    }

    @Test
    fun testBadArguments() {
        assertFailsWith<Exception> {
            main(arrayOf("1", "2"))
        }
    }

    @Test
    fun testBadURL() {
        val exception = assertFailsWith<Exception> {
            main(arrayOf("1"))
        }
        Assert.assertEquals("Connection refused", exception.message)
    }

    @Test
    fun testParseError() {
        val exception = assertFailsWith<Exception> {
            main(arrayOf("http://google.com"))
        }
        assertEquals("Unable to parse url response", exception.message)
    }

    @Test
    fun testSuccess() {
        main(arrayOf("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"))
    }

    @Test
    fun testMinecraftResponse() {
        runBlocking {
            val json: JSONObject =
                getJson("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
            assertEquals("Minecraft", json["title"])
            assertEquals("Mojang", json["publisher"])
            assertTrue(json["price"].toString().startsWith("$7"))
            Assert.assertThat(json["rating"], CoreMatchers.instanceOf<Any>(Double::class.java))
            assertTrue(json["description"].toString().startsWith("Explore infinite"))
            assertFalse(json["description"].toString().contains("\n"))
        }
    }

    @Test
    fun testLyftResponse() {
        runBlocking {
            val json: JSONObject = getJson("https://play.google.com/store/apps/details?id=me.lyft.android&hl=en-US")
            assertEquals("Lyft - Rideshare, Bikes, Scooters & Transit", json["title"])
            assertEquals("Lyft, Inc.", json["publisher"])
            assertTrue(json["price"].toString().startsWith("$0"), "Actual price " + json["price"])
            Assert.assertThat(json["rating"], CoreMatchers.instanceOf<Any>(Double::class.java))
            assertTrue(json["description"].toString().startsWith("Count on Lyft"), "Actual description " + json["description"])
            assertFalse(json["description"].toString().contains("\n"))
        }
    }
}
