package com.appdetex.sampleparserjavaproject.io

import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.AppStore
import com.appdetex.sampleparserjavaproject.parsing.JsonSerializer
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Failed.TooManyArgsSupplied

/**
 * The IOService basically handles all communication with the
 * end-user.
 *
 * @property io configuration file allowing us to inject different implementations
 *
 * @see IOConfig
 */
class IOService(private val io: IOConfig) {

    companion object {
        const val BANNER = """
 ########################################################
  Appdetex Coding Test Crawler ~ @jsnbuchanan   
 ########################################################
  https://github.com/jsnbuchanan/SampleParserJavaProject
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"""
        const val ENTER_URL_PROMPT = "Please enter an app url to crawl ['quit' when done]: "
        const val ERROR_TOO_MANY_URLS = "Too many urls. Please only provide one url at a time."
        const val QUIT_COMMAND = "quit"
        const val GOODBYE = "Thank you for using my program. Have a great day!"

        fun String.withErrorFormat(appStore: String? = null, domain: String? = null) : String {
            val safeAppStore = appStore ?: "NoAppStore"
            val safeDomain = domain ?: ""
            return "\n${safeAppStore}(${safeDomain}): [ERROR] ${this}\u0007"
        }
    }

    fun printBanner() = io.println(BANNER)
    fun display(app: App) = io.println(JsonSerializer.asJson(app))
    fun printGoodbye() = io.println(GOODBYE)

    fun prompt(initialArgs: Array<String>? = null): String {
        io.newLine()
        if (initialArgs != null) {
            val argCount = initialArgs.size

            if(argCount == 1) return initialArgs[0]

            if (argCount > 1) {
                reportError(TooManyArgsSupplied(ERROR_TOO_MANY_URLS).message)
            }
        }

        io.println(ENTER_URL_PROMPT)

        return io.readLine() ?: QUIT_COMMAND
    }

    fun debug(message: String) {
        if (io.debug) io.println(message)
    }

    fun reportError(message: String, appStore: AppStore? = null) {
        val appStoreClass = appStore?.javaClass?.simpleName
        val domain = appStore?.domain
        io.println(message.withErrorFormat(appStoreClass, domain))
    }
}