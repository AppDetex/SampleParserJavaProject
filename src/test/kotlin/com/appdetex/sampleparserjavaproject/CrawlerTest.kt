package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.QUIT_COMMAND
import io.kotest.core.spec.style.StringSpec
import io.mockk.*

class CrawlerTest : StringSpec({

    lateinit var io: IOService
    lateinit var crawler: Crawler

    beforeTest {
        io = mockk(relaxed = true)
        crawler = spyk(Crawler(io), recordPrivateCalls = true)
    }

    "when user enters quit as a commandline arg the app immediately quits" {
        every { io.prompt(arrayOf(QUIT_COMMAND)) } returns QUIT_COMMAND

        crawler.start(arrayOf(QUIT_COMMAND))

        // ensure that private crawl() isn't called
        verify(exactly = 0, timeout = 50) { crawler["crawl"](allAny<String>()) }
        verify(exactly = 1, timeout = 50) { io.printGoodbye() }
    }

    "banner is displayed when program starts" {
        every { io.prompt() } returns QUIT_COMMAND
        crawler.start(arrayOf())

        verify(exactly = 1, timeout = 50) { io.printBanner() }
    }

    "good url happy path from commandline arg" {
        val goodUrl = "https://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276"
        val argsWithGoodUrl = arrayOf(goodUrl)
        every { io.prompt(argsWithGoodUrl) } returns goodUrl
        every { io.prompt() } returns QUIT_COMMAND

        crawler.start(argsWithGoodUrl)

        verifySequence{
            io.printBanner()
            io.prompt(argsWithGoodUrl)
            io.prompt()
            io.printGoodbye()
        }
    }

    "malformed app store url fails validation and reports the error" {
        val malformedUrl = "httpss://apps.apple.com/us/app/multicraft-build-and-mine/id1174039276"
        val argsWithMalformedUrl = arrayOf(malformedUrl)
        every { io.prompt(argsWithMalformedUrl) } returns malformedUrl
        every { io.prompt() } returns QUIT_COMMAND

        crawler.start(argsWithMalformedUrl)

        verifySequence{
            io.printBanner()
            io.prompt(argsWithMalformedUrl)
            io.reportError(any())
            io.prompt()
            io.printGoodbye()
        }
    }

})