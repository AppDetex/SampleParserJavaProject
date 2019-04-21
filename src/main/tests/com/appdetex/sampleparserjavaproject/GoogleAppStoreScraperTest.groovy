package com.appdetex.sampleparserjavaproject

import spock.lang.Specification

class GoogleAppStoreScraperTest extends Specification {

    def 'bad url returns defaults from #result'() {
        when:
        String badUrl = "https:://notValidUrl"
        GoogleAppStoreScraper scrap = new GoogleAppStoreScraper(badUrl)
        GoogleAppStoreInfo badUrlScrape = scrap.result()

        then:
        badUrlScrape.appTitle == "Unknown Application"
        badUrlScrape.description == "Unknown Application"
        badUrlScrape.publisher == "Unknown Application"
        badUrlScrape.price == "Free"
        badUrlScrape.rating == 0.0f


    }


    def 'Minecraft returns expected values from #result'() {
        when:
        String badUrl = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"
        // We should stub or VCR this to prevent calls to Google
        // getting our IP address blacklisted
        GoogleAppStoreScraper scrap = new GoogleAppStoreScraper(badUrl)
        GoogleAppStoreInfo minecraftApp = scrap.result()

        then:
        minecraftApp.rating == 4.5f
        minecraftApp.description == """Spring into action! The Minecraft Marketplace Spring Sale starts this weekend. BEGONE, FOUL WINTER WEATHER. Or, to put that another (slightly more flowery) way, spring has officially sprung! What better way to celebrate a budding season than with one of our biggest Minecraft Marketplace sales yet? Between April 19 and 22, you’ll want to spring into action to snap up discounts of up to 75% on some of the best skins, worlds, texture packs and more from your favourite community creators. We’ll be adding new deals every day of the sale, so make this spring season even sweeter with massive Marketplace savings! Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10. EXPAND YOUR GAME: Marketplace - Discover the latest community creations in the marketplace! Get unique maps, skins, and texture packs from your favorite creators. Slash commands - Tweak how the game plays: you can give items away, summon mobs, change the time of day, and more. Add-Ons - Customize your experience even further with free Add-Ons! If you're more tech-inclined, you can modify data-driven behaviors in the game to create new resource packs. MULTIPLAYER Realms - Play with up to 10 friends cross-platform, anytime, anywhere on Realms, your own private server that we host for you. Try a free 30-day trial in-app. Multiplayer - Play with up to 4 friends with a free Xbox Live account online. Servers - Join free massive multiplayer servers and play with thousands of others! Discover gigantic community-run worlds, compete in unique mini-games and socialize in lobbies full of new friends. SUPPORT: https:///www.minecraft.net/help LEARN MORE: https:///www.minecraft.net/ What's new in version 1.10? New blocks! Lantern, Loom, Lectern and Wood blocks that have bark! Arm yourself with the new crossbow & shield! Jellie, the new cat skin voted into the game by players! Sneak peak at the new hostile Pillager in Creative Mode New Vanilla Textures"""
        minecraftApp.publisher == "Mojang"
        minecraftApp.price == "\$6.99"


    }
}