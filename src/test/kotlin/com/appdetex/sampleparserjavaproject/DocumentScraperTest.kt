package com.appdetex.sampleparserjavaproject

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.test.assertEquals

class DocumentScraperTest {
    @Test
    fun testErrorHandling(): Unit {
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
    fun testWithMinecraft(): Unit {
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

    private fun parseError(doc:Document) : String {
        throw RuntimeException();
    }

    private fun getTitle(doc:Document) : String = doc.select("h1[itemprop=name]").text()

    private fun constantly(value:String?) : (Document) -> String? = {value}

    // https://stackoverflow.com/a/42740416/2039914
    private fun readFile(resourceUrl: String): String { return DocumentScraperTest::class.java.getResource(resourceUrl).readText() }
}