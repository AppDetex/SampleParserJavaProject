package com.appdetex.sampleparserjavaproject.service

abstract class CommandLine {
  protected fun makeResponse(message: String): String? {
    print("$ANSI_BLUE$message: $ANSI_RESET")
    return readLine()
  }

  protected fun printError(message: String) {
    println("$ANSI_RED$message\n$ANSI_RESET")
  }

  companion object {
    const val ANSI_RED = "\u001B[31m"
    const val ANSI_BLUE = "\u001B[34m"
    const val ANSI_RESET = "\u001B[0m"
  }
}