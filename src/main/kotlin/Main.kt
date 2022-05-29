var helpText = "Useage kotlin MainTk [url]\n" +
        "    Options and arguments\n" +
        "    --help: provides this output\n" +
        "    url   : https url from play.google.com"

fun main(args: Array<String>) {
    println("Running SampleProjectForAppDetex")
    if (!validateArgs(args)) {
        println("Invalid arguments")
        println(helpText)
        return
    }

    val playStoreConnector = PlayStoreConnector()
    try {
        val details = playStoreConnector.fetchDetails(args[0])

        println(details)
    }
    catch (e: Exception) {
        println("Error")
        println(e)
    }

    println("Finished")
}

fun validateArgs(args: Array<String>) : Boolean {
    val validatedLength = args.size == 1
    val validatedFormat = args.size > 0 && args[0].contains("https://play.google.com/store/apps/details")

    return validatedLength && validatedFormat
}