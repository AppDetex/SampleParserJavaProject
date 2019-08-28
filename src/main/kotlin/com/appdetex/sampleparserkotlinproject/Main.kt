package com.appdetex.sampleparserkotlinproject;

/**
 * Main creates an App obj from user input Url
 * and prints its data to stdout in a JSON format.
 */
fun main (args: Array<String>) {
    if(args.isEmpty()) {
        println("Please provide url of app found in Google Play")
        return
    }
    val googlePlayUrl = args[0]
    val app: App? = GooglePlayApp().extractGooglePlayAppData(googlePlayUrl)
    if(app != null){
        println(app.toJson())
    }
}


