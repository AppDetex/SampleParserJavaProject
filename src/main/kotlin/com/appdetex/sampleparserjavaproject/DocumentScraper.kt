package com.appdetex.sampleparserjavaproject

import org.jsoup.nodes.Document
import java.lang.Exception

class DocumentScraper(val fieldExtractors:Map<String,(Document) -> String?>) {

    private fun safeRunExtraction(doc:Document, extractor: (Document) -> String?):String? {
        try {
            val result = extractor(doc)
            if(result != null)
                return result
        } catch (e: Exception) {
            //should log more properly, include full exception
            println("Error in extractor: ${e.message}")
        }
        return null
    }

    //Chose Map<String,String?> with the intent to have a separate normalization step
    fun extract(doc:Document):Map<String,String?> {
        return fieldExtractors.mapValues { safeRunExtraction(doc, it.value) }
    }

}