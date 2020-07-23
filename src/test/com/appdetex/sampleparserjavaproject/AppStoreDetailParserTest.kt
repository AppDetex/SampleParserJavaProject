package com.appdetex.sampleparserjavaproject

import com.fasterxml.jackson.databind.ObjectMapper
import org.testng.Assert
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

/** Tests the AppStoreDetailParser */
@Test
class AppStoreDetailParserTest {
    var urls: ArrayList<String> = ArrayList()
    var parser: AppStoreDetailParser = AppStoreDetailParser()

    @BeforeClass
    fun setup() {
        urls.add("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&amp;hl=en_US")
        urls.add("https://play.google.com/store/apps/details?id=com.fingersoft.hcr2")
        urls.add("https://play.google.com/store/apps/details?id=com.topfreegames.bikeracefreeworld")
        urls.add("https://play.google.com/store/apps/details?id=com.creditcom")
        urls.add("https://play.google.com/store/apps/details?id=air.com.hypah.io.slither")
        urls.add("https://play.google.com/store/apps/details?id=com.zeptolab.ctr.paid")
        urls.add("https://play.google.com/store/apps/details?id=com.worms2armageddon.app")
    }

    @Test
    fun testParser() {
        var mapper = ObjectMapper()

        for (url in urls) {
            var mobileApp: MobileApp = parser.parse(url)

            println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mobileApp))

            Assert.assertNotNull(mobileApp, "mobileApp result should not be null")
            Assert.assertNotNull(mobileApp.title, "title should not be null")
            Assert.assertNotNull(mobileApp.description, "description should not be null")
            Assert.assertNotNull(mobileApp.price, "price should not be null")
            Assert.assertNotNull(mobileApp.rating, "rating should not be null")
        }
    }
}