var helpText = "Useage kotlin MainTk [url]\n" +
        "    Options and arguments\n" +
        "    --help: provides this output\n" +
        "    url   : https url from play.google.com"

fun main(args: Array<String>) {
    println("Running SampleProjectForAppDetex")
    if (!validateArgs(args)) {
        println("Invalid arguments")
        println(helpText)
        return;
    }

    println("Finished")
}

fun validateArgs(args: Array<String>) : Boolean {
    var validatedLength = args.size == 1
    var validatedFormat = args.size > 0 && args[0].contains("https://play.google.com/store/apps/details")

    return validatedLength && validatedFormat
}