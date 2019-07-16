package com.appdetex.sampleparserjavaproject

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.jsoup.nodes.Document

class LdJsonScraper(val jsonExtractor: (JsonObject) -> String): (Document) -> String {
    companion object {
        const val LD_JSON_SELECTOR = "script[type=application/ld+json]"
        val gson:Gson = Gson()
    }

    override fun invoke(doc:Document):String {
        val json = doc.select(LD_JSON_SELECTOR).html()
        val jsonObject = gson.fromJson(json, JsonObject::class.java)
        return jsonExtractor(jsonObject)
    }
}