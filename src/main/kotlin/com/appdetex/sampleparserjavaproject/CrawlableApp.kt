package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.AppStore
import com.appdetex.sampleparserjavaproject.parsing.ParseResult
import com.appdetex.sampleparserjavaproject.parsing.ParserFactory
import com.appdetex.sampleparserjavaproject.validation.UrlValidatorFactory
import com.appdetex.sampleparserjavaproject.validation.ValidationResult
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Failed.MalformedUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

/**
 * CrawlableApp is implementation for validating, scraping, parsing
 * and displaying an App urls details.
 *
 * Example:
 *
 * CrawlableApp("https://some.appstore.com/apps/1234", IOService())
 *     .validate()
 *     .scrape()
 *     .parse()
 *     .display()
 *
 * @property io instance of IOService for displaying App details and reporting errors
 * @property urlValidatorFactory for obtaining AppStore specific UrlValidators
 * @property parserFactory for obtaining specific Parsers
 * @constructor creates a CrawlableApp with the passed app url
 *
 * @param passedAppUrl for retrieving app information
 */
internal class CrawlableApp(
    passedAppUrl: String,
    private val io: IOService,
    private val urlValidatorFactory: UrlValidatorFactory,
    private val parserFactory: ParserFactory
    ) {
    lateinit var appUrl: URL private set
    lateinit var appStore: AppStore private set
    lateinit var doc: Document private set
    lateinit var app: App private set

    init {
        try {
            this.appUrl = URL(passedAppUrl)
            this.appStore = AppStore.instance(appUrl)

        } catch (e: MalformedURLException) {
            val failed = MalformedUrl("${e.message}. \"$passedAppUrl\", is not a valid url.", e)
            this.io.reportError(failed.message)
        }
    }

    companion object {
        const val USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36"
        const val VALIDATION_SUCCESS = "logging validation success"
        const val PARSE_SUCCESS = "logging parse success"

        const val VALIDATION_COULD_NOT_START = "Could not validate because either URL and/or AppStore not present. Initialization must have failed."
        const val SCRAPING_COULD_NOT_START = "Could not scrape because a valid app URL is not present. Initialization must have failed."
        const val PARSING_COULD_NOT_START = "Could not parse because either the appStore and/or doc is not present. Did you forget to call scrape()?"

        const val SCRAPING_FAILED = "Scraping failed with message: %s"
        const val DISPLAY_NOTHING = "nothing to display"
    }

    /**
     * First step in the CrawlableApp chain.
     *
     * Verifies whether the url meets our minimum
     * requirements for an AppStore crawled App.
     *
     * @return CrawlableApp for the next chained operation
     *
     * @see AppStore
     * @see ValidationResult
     */
    fun validateUrl(): CrawlableApp {
        if (isNotValidatable()) return this

        when(val result = urlValidatorFactory
            .instance(appStore)
            .validate(appUrl)) {

            is ValidationResult.Success -> io.debug(VALIDATION_SUCCESS)
            is ValidationResult.Failed -> io.reportError(result.message, appStore)
        }
        return this
    }

    /**
     * Should be called after validate().
     *
     * Retrieves a parsable document for the app.
     *
     * @return CrawlableApp for the next chained operation
     *
     * @see CrawlableApp.validateUrl()
     * @see CrawlableApp.parse()
     */
    fun scrape() : CrawlableApp {
        if (isNotScrapable()) return this

        try {
            this.doc = Jsoup.connect(appUrl.toString())
                .userAgent(USER_AGENT)
                .get()

        } catch (e: IOException) {
            io.reportError(SCRAPING_FAILED.format(e.message))
        }

        return this
    }

    /**
     * Should be called after scrape().
     *
     * Handles the parsing of scraped details and creates the App model.
     *
     * @return CrawlableApp for the next chained operation
     *
     * @see CrawlableApp.scrape()
     * @See App
     */
    fun parse() : CrawlableApp {
        if(isNotParseable()) return this

        when (val result = parserFactory
            .instance(appStore)
            .parse(doc)) {
            is ParseResult.Success -> handleParseSuccess(result)
            is ParseResult.Failed -> io.reportError(result.message, appStore)
        }
        return this
    }

    /**
     * Should be called after parse().
     *
     * Displays the JSON for the CrawlableApp or an error
     * message indicating there is nothing to display.
     *
     * @see CrawlableApp.parse()
     */
    fun display() {
        when {
            this::app.isInitialized -> io.display(app)
            this::appStore.isInitialized -> io.reportError(DISPLAY_NOTHING, appStore)
            else -> io.reportError(DISPLAY_NOTHING)
        }
    }

    private fun isNotValidatable() : Boolean {
        val urlOrStoreNotInitialized = !(this::appUrl.isInitialized && this::appStore.isInitialized)
        if (urlOrStoreNotInitialized) io.debug(VALIDATION_COULD_NOT_START)
        return urlOrStoreNotInitialized
    }

    private fun isNotScrapable() : Boolean {
        val urlNotInitialized = !this::appUrl.isInitialized
        if (urlNotInitialized) io.debug(SCRAPING_COULD_NOT_START)
        return urlNotInitialized
    }

    private fun isNotParseable() : Boolean {
        val storeOrDocNotInitialized = !(this::appStore.isInitialized && this::doc.isInitialized)
        if (storeOrDocNotInitialized) io.debug(PARSING_COULD_NOT_START)
        return storeOrDocNotInitialized
    }

    private fun handleParseSuccess(success: ParseResult.Success) {
        this.app = success.app
        io.debug(PARSE_SUCCESS)
    }
}