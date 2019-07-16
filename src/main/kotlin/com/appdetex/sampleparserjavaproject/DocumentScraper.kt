package com.appdetex.sampleparserjavaproject

import org.jsoup.nodes.Document
import java.lang.Exception

class DocumentScraper(val fieldExtractors:Map<String,List<(Document) -> String?>>) {

    private fun runExtraction(doc:Document, extractors: List<(Document) -> String?>):String? {
        for(extractor in extractors) {
            try {
                val result = extractor(doc)
                if(result != null)
                    return result
            } catch (e: Exception) {
                //should log more properly, include full exception
                println("Error in extractor: ${e.message}")
            }
        }
        return null
    }

    fun extract(doc:Document):Map<String,String?> {
        return fieldExtractors.mapValues { runExtraction(doc, it.value) }
    }

}