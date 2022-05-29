import java.net.URL

class PlayStoreConnector {

    fun fetchDetails(url: String): String {
        val content = fetchContent(URL(url))

        val context = parseContentForContext(content)
        val title = extractTitle(context)
        val description = extractDescription(context)
        val price = extractPrice(context)
        val publisher = extractPublisher(context)
        val rating = extractRating(context)

        return PlayStoreSummary(title, description, publisher, price, rating).toJson()
    }

    private fun fetchContent(url: URL): String {
        return url.openStream().bufferedReader().use { it.readText() }
    }

    //example output
    //@context":"https://schema.org","@type":"SoftwareApplication","name":"Minecraft","url":"https://play.google.com/store/apps/details/Minecraft?id\u003dcom.mojang.minecraftpe\u0026hl\u003den_US\u0026gl\u003dUS","description":"Millions of crafters have smashed billions of blocks! Now you can join the fun!","operatingSystem":"ANDROID","applicationCategory":"GAME_ARCADE","image":"https://play-lh.googleusercontent.com/VSwHQjcAttxsLE47RuS4PqpC4LT7lCoSjE7Hx5AW_yCxtDvcnsHHvm5CTuL5BPN-uRTP","contentRating":"Everyone 10+","author":{"@type":"Person","name":"Mojang","url":"http://help.mojang.com"},"aggregateRating":{"@type":"AggregateRating","ratingValue":"4.599999904632568","ratingCount":"4594861"},"offers":[{"@type":"Offer","price":"7.49","priceCurrency":"USD","availability":"https://schema.org/InStock"}]}
    private fun parseContentForContext(content: String): String {
        val contextIndex = content.indexOf("@context\":\"https://schema.org\"")
        val endingIndex = content.indexOf("]}", contextIndex)
        return content.substring(contextIndex, endingIndex + 2)
    }

    private fun extractTitle(content: String): String {
        val startingIndex = content.indexOf("name") + 7
        val endingIndex = content.indexOf('"', startingIndex)
        return content.substring(startingIndex, endingIndex)
    }

    //A longer description is available deeper in the HTML that can be extracted if needed
    private fun extractDescription(content: String): String {
        val startingIndex = content.indexOf("description") + 14
        val endingIndex = content.indexOf('"', startingIndex)
        return content.substring(startingIndex, endingIndex)
    }

    private fun extractPrice(content: String): String {
        val startingIndex = content.indexOf("price") + 8
        val endingIndex = content.indexOf('"', startingIndex)
        return content.substring(startingIndex, endingIndex)
    }

    private fun extractPublisher(content: String): String {
        val firstNameIndex = content.indexOf("name") + 7 // 'name' is used to describe both the title and publisher
        val startingIndex = content.indexOf("name", firstNameIndex) + 7
        val endingIndex = content.indexOf('"', startingIndex)
        return content.substring(startingIndex, endingIndex)
    }

    private fun extractRating(content: String): String {
        val startingIndex = content.indexOf("ratingValue") + 14
        return content.substring(startingIndex, startingIndex + 3)
    }


}