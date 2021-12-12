package com.appdetex.sampleparserjavaproject.service

class CommandLineRunner private constructor(
  private val commandLineSearchService: CommandLineSearchService
) : CommandLine() {
  fun startProgram() {
    print("Hello! ")
    runForResponse()
  }

  private fun runForResponse() {
    try {
      provideMenu()

      val response: String? = makeResponse("choice")

      if (response == "0") return

      checkResponse(response)

      return runForResponse()
    } catch (ex: Exception) {
      printError("Something went wrong! Try again.")
      runForResponse()
    }
  }

  private fun provideMenu() {
    println("Please choose an option:")
    println("(0) End program")
    println("(1) Example - https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US")
    println("(2) Search by google app uri")
  }

  private fun checkResponse(response: String?) {
    if (response == "1") return commandLineSearchService.runExample()
    if (response == "2") return commandLineSearchService.runSearch()
    printError("Incorrect response: $response")
  }

  companion object {
    val instance = CommandLineRunner(CommandLineSearchService.instance)
  }
}