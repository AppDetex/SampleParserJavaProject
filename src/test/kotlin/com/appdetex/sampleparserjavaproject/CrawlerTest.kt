package com.appdetex.sampleparserjavaproject

import com.appdetex.sampleparserjavaproject.CrawlableApp.Companion.PARSE_SUCCESS
import com.appdetex.sampleparserjavaproject.CrawlableApp.Companion.VALIDATION_SUCCESS
import com.appdetex.sampleparserjavaproject.io.IOService
import com.appdetex.sampleparserjavaproject.io.IOService.Companion.QUIT_COMMAND
import com.appdetex.sampleparserjavaproject.model.App
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
        val argsWithQuit = arrayOf(QUIT_COMMAND)
        crawler.start(argsWithQuit)

        verifySequence{
            io.printBanner()
            io.prompt(argsWithQuit)
            io.printGoodbye()
        }
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
            "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.",
            "Mojang",
            "$7.49",
            4.6f
        )
        val argsWithGoodUrl = arrayOf(goodUrl)
        every { io.prompt(argsWithGoodUrl) } returns goodUrl
        every { io.prompt() } returns QUIT_COMMAND

        crawler.start(argsWithGoodUrl)

        verifySequence{
            io.printBanner()
            io.prompt(argsWithGoodUrl)
            io.debug(VALIDATION_SUCCESS)
            io.debug(PARSE_SUCCESS)
            io.display(expectedApp)
            io.prompt()
            io.printGoodbye()
        }
    }
})