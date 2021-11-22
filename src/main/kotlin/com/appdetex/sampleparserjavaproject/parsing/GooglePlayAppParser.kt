package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.AppStore.GooglePlayStore
import org.jsoup.Jsoup
import java.net.URL

internal class GooglePlayAppParser(val appStore: GooglePlayStore) : Parser {

    override fun parse(url: URL): ParseResult {
        println(url.toString())
        val doc = Jsoup.connect("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US").get()
        val title = appStore.queries.title(doc)
        val description = "not set yet"
        val publisher = "not set yet"
        val price = "not set yet"
        val rating = 0.0f
        val app = App(title, description, publisher, price, rating)
        return ParseResult.Success(app)
    }
}