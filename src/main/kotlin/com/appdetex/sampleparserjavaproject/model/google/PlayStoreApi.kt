package com.appdetex.sampleparserjavaproject.model.google

import kotlinx.serialization.Serializable

/**
 * The Google Play Store api is a collecting spot for api mapping classes.
 * It is possible to retrieve the JSON object for a complete app from the
 * Google Play Store page. It looks like this:
 *
 *  {
 *     "@context": "https://schema.org",
 *     "@type": "SoftwareApplication",
 *     "name": "Minecraft",
 *     "url": "https://play.google.com/store/apps/details/Minecraft?id\u003dcom.mojang.minecraftpe\u0026hl\u003den_US\u0026gl\u003dUS",
 *     "description": "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.\n\nEXPAND YOUR GAME:\nMarketplace - Discover the latest community creations in the marketplace! Get unique maps, skins, and texture packs from your favorite creators. \n\nSlash commands - Tweak how the game plays: you can give items away, summon mobs, change the time of day, and more. \n\nAdd-Ons - Customize your experience even further with free Add-Ons! If you\u0027re more tech-inclined, you can modify data-driven behaviors in the game to create new resource packs.\n\nMULTIPLAYER\nRealms - Play with up to 10 friends cross-platform, anytime, anywhere on Realms, your own private server that we host for you. Try a free 30-day trial in-app.\n\nMultiplayer - Play with up to 4 friends with a free Xbox Live account online.\nServers - Join free massive multiplayer servers and play with thousands of others! Discover gigantic community-run worlds, compete in unique mini-games and socialize in lobbies full of new friends!\n\nSUPPORT: https:///www.minecraft.net/help\nLEARN MORE: https:///www.minecraft.net/",
 *     "operatingSystem": "ANDROID",
 *     "applicationCategory": "GAME_ARCADE",
 *     "image": "https://play-lh.googleusercontent.com/VSwHQjcAttxsLE47RuS4PqpC4LT7lCoSjE7Hx5AW_yCxtDvcnsHHvm5CTuL5BPN-uRTP",
 *     "contentRating": "Everyone 10+",
 *     "author": {
 *            "@type": "Person",
 *            "name": "Mojang",
 *            "url": "http://help.mojang.com"
 *     },
 *     "aggregateRating": {
 *         "@type": "AggregateRating",
 *         "ratingValue": "4.6044511795043945",
 *         "ratingCount": "4358308"
 *     },
 *     "offers": [{
 *         "@type": "Offer",
 *         "price": "7.49",
 *         "priceCurrency": "USD",
 *         "availability": "https://schema.org/InStock"
 *     }]
 *  }
 */
internal object PlayStoreApi {

    @Serializable
    internal class Publisher (val name: String )

    @Serializable
    internal class Rating(val ratingValue: Float)

    @Serializable
    internal class Price(val price: Float, val priceCurrency: String)

    @Serializable
    internal class PlayStoreApp (
        val name: String,
        val description: String,
        val author: Publisher,
        val aggregateRating: Rating,
        val offers: List<Price>
    )
}