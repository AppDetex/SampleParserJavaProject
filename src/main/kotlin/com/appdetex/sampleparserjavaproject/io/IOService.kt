package com.appdetex.sampleparserjavaproject.io

import com.appdetex.sampleparserjavaproject.model.AppStore
import com.appdetex.sampleparserjavaproject.validation.ValidationResult

internal class IOService(private val io: IOConfig) {

    companion object {
        const val QUIT_COMMAND = "quit"
        const val BANNER = """
 ########################################################
  Appdetex Coding Test Crawler ~ @jsnbuchanan   
 ########################################################
  https://github.com/jsnbuchanan/SampleParserJavaProject
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"""
        const val ENTER_URL_PROMPT = "Please enter an app url to crawl ['quit' when done]: "

        const val ERROR_TOO_MANY_URLS = "Too many urls. Please only provide one url at a time."

        const val GOODBYE = "Thank you for using my program. Have a great day!"

        fun String.withErrorFormat(appStore: String? = null, domain: String? = null) : String {
            val safeAppStore = appStore ?: "NoAppStore"
            val safeDomain = domain ?: ""
            return "\n${safeAppStore}(${safeDomain}): [ERROR] ${this}\u0007"
        }
    }

    fun prompt(initialArgs: Array<String>? = null): String {
        newLine()
        if (initialArgs != null) {
            val argCount = initialArgs.size

            if(argCount == 1) return initialArgs[0]

            if (argCount > 1) {
                reportError(ValidationResult.Failed.TooManyArgsSupplied(ERROR_TOO_MANY_URLS))
            }
        }

        io.println(ENTER_URL_PROMPT)

        return io.readLine() ?: QUIT_COMMAND
    }

   fun reportError(result: ValidationResult.Failed, appStore: AppStore? = null) : ValidationResult {
        val appStoreClass = appStore?.javaClass?.simpleName
        val domain = appStore?.domain
        io.println(result.message.withErrorFormat(appStoreClass, domain))
        return result
    }

    fun printBanner() {
        io.println(BANNER)
    }

    fun printGoodbye() {
        io.println(GOODBYE)
    }

    fun newLine() {
        io.newLine()
    }
}