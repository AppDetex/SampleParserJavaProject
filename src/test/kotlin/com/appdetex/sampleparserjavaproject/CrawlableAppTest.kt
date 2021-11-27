package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.CrawlableApp.Companion.PARSE_SUCCESS
import com.appdetex.sampleparserjavaproject.CrawlableApp.Companion.PARSING_COULD_NOT_START
import com.appdetex.sampleparserjavaproject.CrawlableApp.Companion.SCRAPING_COULD_NOT_START
import com.appdetex.sampleparserjavaproject.CrawlableApp.Companion.VALIDATION_COULD_NOT_START
import com.appdetex.sampleparserjavaproject.CrawlableApp.Companion.VALIDATION_SUCCESS
import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.AppStore.GooglePlayStore
import com.appdetex.sampleparserjavaproject.parsing.ParseResult
import com.appdetex.sampleparserjavaproject.parsing.ParserFactory
import com.appdetex.sampleparserjavaproject.validation.UrlValidatorFactory
import com.appdetex.sampleparserjavaproject.validation.ValidationResult
import com.appdetex.sampleparserjavaproject.validation.ValidationResult.Success
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.*
import org.jsoup.nodes.Document
import java.net.URL

class CrawlableAppTest : StringSpec({

    val malFormedUrl = "httpss://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276"
    val malFormedErrorMessage = "unknown protocol: httpss. \"$malFormedUrl\", is not a valid url."
    val validGoogleUrlString = "https://play.google.com/store/apps/details?id=com.duolingo"
    val validGoogleUrl = URL(validGoogleUrlString)

    lateinit var io: IOService
    lateinit var urlValidatorFactory: UrlValidatorFactory
    lateinit var parserFactory: ParserFactory
    lateinit var crawlableApp: CrawlableAppFactory

    beforeTest {
        io = mockk(relaxed = true)
        urlValidatorFactory = mockk(relaxed = true)
        parserFactory = mockk(relaxed = true)
        crawlableApp = CrawlableAppFactory(io, urlValidatorFactory, parserFactory)
    }

    "malformed app store url fails validation and reports the error" {
        val expectedErrorMessage = "unknown protocol: httpss. \"$malFormedUrl\", is not a valid url."

        crawlableApp.instance(malFormedUrl)

        verifySequence{
            io.reportError(expectedErrorMessage)
        }
    }

    "init should set appUrl" {
        val crawledApp = crawlableApp.instance(validGoogleUrlString)

        crawledApp.appUrl shouldBe validGoogleUrl
    }

    "init should set appStore" {
        val crawledApp = crawlableApp.instance(validGoogleUrlString)

        crawledApp.appStore.shouldBeInstanceOf<GooglePlayStore>()
    }

    "if appUrl and appStore are not set, validateUrl() shouldn't attempt to validate" {
        crawlableApp.instance(malFormedUrl)
            .validateUrl()

        verifySequence{
            io.reportError(malFormedErrorMessage, null)
            io.debug(VALIDATION_COULD_NOT_START)
        }
    }

    "on Success validateUrl() should log a success message to debug" {
        every {
            urlValidatorFactory.instance(any()).validate(any())
        } returns Success(validGoogleUrl)
        crawlableApp.instance(validGoogleUrlString)
            .validateUrl()

        verify(exactly = 1) { io.debug(VALIDATION_SUCCESS) }
        verify(exactly = 0) { io.reportError(any(), null)}
    }

    "on Failure validateUrl() should report an error message" {
        every { urlValidatorFactory.instance(any()).validate(any())
        } returns ValidationResult.Failed.MissingId("failed")

        crawlableApp.instance(validGoogleUrlString)
            .validateUrl()

        verify(exactly = 0) { io.debug(VALIDATION_SUCCESS) }
        verify(exactly = 1) { io.reportError(any(), any())}
    }

    "if appUrl is not set, scrape() shouldn't attempt to scrape" {
        crawlableApp.instance(malFormedUrl)
            .scrape()

        verifySequence{
            io.reportError(malFormedErrorMessage, null)
            io.debug(SCRAPING_COULD_NOT_START)
        }
    }

    "on scrape() success, doc should be set" {
        val scrapedApp = crawlableApp.instance(validGoogleUrlString)
            .scrape()

        scrapedApp.doc.shouldBeInstanceOf<Document>()
    }

    "on scrape() failure should report an error message" {
        crawlableApp.instance("https://play.google.com/store/apps/details")
            .scrape()

        verifySequence{
            io.reportError("Scraping failed with message: HTTP error fetching URL. Status=404, URL=[https://play.google.com/store/apps/details]")
        }
    }

    "if parse() called out of order log debug message and don't parse" {
        crawlableApp.instance(validGoogleUrlString)
            .parse()

        verifySequence{
            io.debug(PARSING_COULD_NOT_START)
        }
    }

    "on parse() success, should log a success message to debug and set App" {
        val app = App("title", "descrip", "me", "$0.00", 4.5f)
        every {
            parserFactory.instance(any()).parse(any())
        } returns ParseResult.Success(app)

        val parsedApp = crawlableApp.instance(validGoogleUrlString)
            .scrape()
            .parse()

        verify(exactly = 1) { io.debug(PARSE_SUCCESS) }
        parsedApp.app.shouldBeInstanceOf<App>()
        verify(exactly = 0) { io.reportError(any(), null)}
    }

    "on Failure parse() should report an error message" {
        every {
            parserFactory.instance(any()).parse(any())
        } returns ParseResult.Failed("failed")

        crawlableApp.instance(validGoogleUrlString)
            .scrape()
            .parse()

        verify(exactly = 0) { io.debug(PARSE_SUCCESS) }
        verify(exactly = 1) { io.reportError("failed", any())}
    }


})