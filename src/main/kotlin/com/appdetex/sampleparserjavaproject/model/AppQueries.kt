package com.appdetex.sampleparserjavaproject.model

import org.jsoup.nodes.Document

interface AppQueries {
    val title: (Document) -> String
    val description: (Document) -> String
    val publisher: (Document) -> String
    val price: (Document) -> String
    val rating: (Document) -> Float
}