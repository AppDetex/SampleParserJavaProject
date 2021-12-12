package com.appdetex.sampleparserjavaproject.service

import com.appdetex.sampleparserjavaproject.client.GoogleAppStoreClient
import com.appdetex.sampleparserjavaproject.config.OBJECT_MAPPER
import com.appdetex.sampleparserjavaproject.domain.AppStoreSynopsis
import com.fasterxml.jackson.databind.ObjectMapper

class CommandLineSearchService private constructor(
  private val googleAppStoreClient: GoogleAppStoreClient,
  private val objectMapper: ObjectMapper
) : CommandLine() {
  fun runSearch() {
    val uri: String? = makeResponse("Google app store URI")

    if (uri.isNullOrEmpty()) return printError("URI cannot be empty or null\n")

    if (!uri.contains(VALIDATION_REGEX)) return printError("Not a valid URI: $uri")

    val synopsis: AppStoreSynopsis = googleAppStoreClient.findSynopsisFromAppStore(uri)

    printSynopsis(synopsis)
  }

  fun runExample() {
    val synopsis: AppStoreSynopsis = googleAppStoreClient.findExampleSynopsisFromAppStore()

    printSynopsis(synopsis)
  }

  private fun printSynopsis(synopsis: AppStoreSynopsis) {
    val jsonString: String =
      synopsis
        .toDisplay()
        .let(objectMapper.writerWithDefaultPrettyPrinter()::writeValueAsString)

    println("$jsonString\n")
  }

  companion object {
    const val VALIDATION_REGEX = "https://play.google.com/store/apps/details?id="
    val instance: CommandLineSearchService = CommandLineSearchService(GoogleAppStoreClient.instance, OBJECT_MAPPER)
  }
}