package com.appdetex.sampleparserjavaproject.service

/**
 * Setup as a singleton so that we only build the objects once.
 */
class App {

  companion object {
    fun run() {
      CommandLineRunner.instance.startProgram()
    }
  }
}