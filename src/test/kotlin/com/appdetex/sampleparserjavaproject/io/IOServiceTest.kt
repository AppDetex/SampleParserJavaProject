package com.appdetex.sampleparserjavaproject.io

import com.appdetex.sampleparserjavaproject.io.IOService.Companion.BANNER
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.ENTER_URL_PROMPT
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.ERROR_TOO_MANY_URLS
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.GOODBYE
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.QUIT_COMMAND
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.withErrorFormat
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.net.MalformedURLException

class IOServiceTest : StringSpec({

    lateinit var io: IOConfig
    lateinit var ioService: IOService

    val firstArg = "first command line arg"
    val secondArg = "second command line arg"
    val userEntry = "user entry"

    beforeTest {
        io = mockk(relaxed = true)
        ioService = IOService(io)

        every { io.readLine() } returns userEntry
    }

    "if one commandline arg is passed to prompt return it" {
        val oneArg = arrayOf(firstArg)

        ioService.prompt(oneArg) shouldBe firstArg
    }

    "multiple commandline args should report error and then prompt" {
        val multipleArgs = arrayOf(firstArg, secondArg)

        ioService.prompt(multipleArgs)

        verifySequence {
            io.newLine()
            io.println(ERROR_TOO_MANY_URLS.withErrorFormat())
            io.println(ENTER_URL_PROMPT)
            io.readLine()
        }
    }

    "if no commandline args are passed prompt user and return that entry" {
        ioService.prompt() shouldBe userEntry

        verifySequence {
            io.newLine()
            io.println(ENTER_URL_PROMPT)
            io.readLine()
        }
    }

    "if user enters null at prompt return 'quit' command" {
        every { io.readLine() } returns null

        ioService.prompt() shouldBe QUIT_COMMAND
    }

    "if user passes quit as the commandline arg prompt return 'quit' command" {
        val quitArg = arrayOf(QUIT_COMMAND)

        ioService.prompt(quitArg) shouldBe QUIT_COMMAND
    }

    "withErrorFormat() works with no supplied appstore and domain" {
        "x".withErrorFormat() shouldBe "\nNoAppStore(): [ERROR] x\u0007"
    }

    "withErrorFormat() works with supplied domain but no appstore" {
        "x".withErrorFormat(
            domain = "y.com"
        ) shouldBe "\nNoAppStore(y.com): [ERROR] x\u0007"
    }

    "withErrorFormat() works with supplied appstore but no domain" {
        "x".withErrorFormat(
            appStore = "GooglePlayStore"
        ) shouldBe "\nGooglePlayStore(): [ERROR] x\u0007"
    }

    "withErrorFormat() works for with supplied appstore and domain" {
        "x".withErrorFormat(
            appStore = "GooglePlayStore",
            domain = "play.google.com"
        ) shouldBe "\nGooglePlayStore(play.google.com): [ERROR] x\u0007"
    }

    "printBanner() outputs the application banner" {
        ioService.printBanner()

        verify(exactly = 1) { io.println(BANNER) }
    }

    "printGoodbye() outputs goodby message" {
        ioService.printGoodbye()

        verify(exactly = 1) { io.println(GOODBYE) }
    }
})