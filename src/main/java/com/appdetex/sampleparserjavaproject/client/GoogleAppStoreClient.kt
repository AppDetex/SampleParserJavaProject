package com.appdetex.sampleparserjavaproject.client

import com.appdetex.sampleparserjavaproject.config.OBJECT_MAPPER
import com.appdetex.sampleparserjavaproject.domain.AppStoreSynopsis
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL

class GoogleAppStoreClient private constructor(
  private val objectMapper: ObjectMapper
) {
  fun findExampleSynopsisFromAppStore(): AppStoreSynopsis = findSynopsisFromAppStore(EXAMPLE)

  fun findSynopsisFromAppStore(uri: String): AppStoreSynopsis {
    val html: String = URL(uri).readText()

    val context: String = html.parseWithRegex(SCRIPT_REGEX)
    val jsonString: String = "{${context.parseWithRegex(CONTEXT_REGEX)}".also { it.replace("@", "") }

    return objectMapper.readValue(jsonString, AppStoreSynopsis::class.java)
  }


  private fun String.parseWithRegex(regex: String): String = this.split(regex)[1]

  companion object {
    const val EXAMPLE = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US "
    const val SCRIPT_REGEX = "<script type=\"application/ld+json\""
    const val CONTEXT_REGEX = "\"@context\":\"https://schema.org\","
    val instance: GoogleAppStoreClient = GoogleAppStoreClient(OBJECT_MAPPER)
  }
}