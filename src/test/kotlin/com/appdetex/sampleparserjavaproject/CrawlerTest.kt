package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.QUIT_COMMAND
import com.appdetex.sampleparserjavaproject.model.App
import com.appdetex.sampleparserjavaproject.model.AppStore
import com.appdetex.sampleparserjavaproject.validation.ValidationResult
import io.kotest.core.spec.style.StringSpec
import io.mockk.*
import java.net.URL

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
        verify(exactly = 0, timeout = 50) { crawler["validate"](any<AppStore>(), any<URL>()) }
        verify(exactly = 1, timeout = 50) { io.printGoodbye() }
    }

    "banner is displayed when program starts" {
        every { io.prompt() } returns QUIT_COMMAND
        crawler.start(arrayOf())

        verify(exactly = 1, timeout = 50) { io.printBanner() }
    }

    "good url happy path from commandline arg" {
        val goodUrl = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"
        val expectedApp = App(
            "Minecraft",
            "not set yet",
            "not set yet",
            "not set yet",
            0.0f
        )
        val argsWithGoodUrl = arrayOf(goodUrl)
        every { io.prompt(argsWithGoodUrl) } returns goodUrl
        every { io.prompt() } returns QUIT_COMMAND

        crawler.start(argsWithGoodUrl)

        verifySequence{
            io.printBanner()
            io.prompt(argsWithGoodUrl)
            io.display(expectedApp)
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
            io.reportError(any<ValidationResult.Failed>())
            io.prompt()
            io.printGoodbye()
        }
    }

})