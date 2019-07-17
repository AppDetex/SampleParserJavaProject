package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.playstore.LdJsonScraper
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test
import kotlin.test.assertEquals

class DocumentScraperTest {
    @Test
    fun testErrorHandling() {
        val exampleScraper = DocumentScraper(mapOf("title" to listOf(::parseError, ::getTitle),
                "price" to listOf(constantly(null), constantly("9.99"))))
        val exampleHtml = """
            <html>
            <body>
                <h1 itemprop="name">TheTitle!<h1>
            </body>
            </html>
        """.trimIndent()
        val doc = Jsoup.parse(exampleHtml)

        val result = exampleScraper.extract(doc)

        assertEquals(mapOf("title" to "TheTitle!",
                "price" to "9.99"), result)
    }

    @Test
    fun testWithMinecraft() {
        val exampleScraper = DocumentScraper(mapOf("title" to listOf(::getTitle),
                "price" to listOf(LdJsonScraper { json ->
                    json.getAsJsonArray("offers")
                            .map { it.asJsonObject.getAsJsonPrimitive("price") }
                            .first()
                            .asString
                })))

        val html = readFile("/minecraft.html")
        val doc = Jsoup.parse(html)

        val result = exampleScraper.extract(doc)
        assertEquals(mapOf("title" to "Minecraft",
                "price" to "6.99"
        ), result)
    }


    @Test
    fun testPlayStoreScraperMinecraft() {
        val minecraftDescription = "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10."
        val extracted = playStoreExtract("/minecraft.html")

        assertEquals(mapOf("title" to "Minecraft",
                "publisher" to "Mojang",
                "price" to "6.99",
                "description" to minecraftDescription,
                "rating" to "4.5"), extracted)
    }

    @Test
    fun testPlayStoreScraperRoblox() {
        val robloxDescription = "Roblox is the ultimate virtual universe that lets you play, create, and be anything you can imagine. Join millions of players and experience a diverse collection of games created by a global community!"
        val extracted = playStoreExtract("/roblox.html")

        assertEquals(mapOf("title" to "Roblox",
                "publisher" to "Roblox Corporation",
                "price" to "0.0",
                "description" to robloxDescription,
                "rating" to "4.5"), extracted)
    }

    private fun playStoreExtract(resourceUrl: String): Map<String, String?> {
        val html = readFile(resourceUrl)
        val doc = Jsoup.parse(html)

        val extracted = Scrapers.playStoreScraper.extract(doc)
        return extracted
    }

    private fun parseError(unused: Document): String {
        throw RuntimeException()
    }

    private fun getTitle(doc: Document): String = doc.select("h1[itemprop=name]").text()

    private fun constantly(value: String?): (Document) -> String? = { value }

    // https://stackoverflow.com/a/42740416/2039914
    private fun readFile(resourceUrl: String): String = DocumentScraperTest::class.java.getResource(resourceUrl).readText()
}