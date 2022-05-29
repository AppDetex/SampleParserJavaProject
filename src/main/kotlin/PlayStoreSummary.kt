class PlayStoreSummary (private val title: String, private val description: String, private val publisher: String, private val price: String, private val rating: String) {
    fun toJson(): String {
        return "{\"title\": \"$title\",\"description\": \"$description\",\"publisher\": \"$publisher\",\"price\": \"\$$price\",\"rating\": $rating}"
    }
}