package com.appdetex.sampleparserjavaproject.parsing

import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.AppStore.GooglePlayStore
import org.jsoup.Jsoup
import java.net.URL


internal class GooglePlayAppParser(val appStore: GooglePlayStore) : Parser {

    override fun parse(url: URL): ParseResult {
        val doc = Jsoup.connect(url.toString()).get()
        // TODO("parsing comes next")
        val title = "not set yet"
        val description = "not set yet"
        val publisher = "not set yet"
        val price = "not set yet"
        val rating = 0.0f
        val app = App(title, description, publisher, price, rating)
        return ParseResult.Success(app)
    }
}