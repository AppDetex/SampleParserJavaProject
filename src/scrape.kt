package scraper;

import java.io.File
import java.util.ArrayList

import org.jsoup.Jsoup
import org.jsoup.helper.Validate
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import org.json.simple.JSONObject
import org.json.simple.JSONArray

fun usage() {
  println("""
Usage: app <url> | <path to file>
Where <path to file> contains one URL per line
""")
}

fun loadUrls(args: Array<String>): ArrayList<String> {
    val urls = ArrayList<String>()
    if (args[0].startsWith("http://") || args[0].startsWith("https://")) {
      urls.add(args[0])
    } else {
      try {
        File(args[0]).forEachLine { urls.add(it) }
      } catch (e: Exception) {
        when(e) {
          is java.nio.file.NoSuchFileException,
          is java.io.FileNotFoundException -> {
            println("Bad file path!")
            usage()
          } else -> throw e
        }
      }
    }
    return urls
}

fun main(args : Array<String>) {
  if (args.size == 0) {
    usage()
    return
  }

  val urls = loadUrls(args)
  if (urls.size == 0) return

  val scraped = JSONArray()

  for (url in urls) {
    try {
      val jo = JSONObject()

      val doc = org.jsoup.Jsoup.connect(url).timeout(10*1000).get()
      var el = doc.select("h1[itemprop = name]")
      jo.put("title", el.text())

      el = doc.select("div[itemprop = description] div")
      jo.put("description", el.text())

      el = doc.select("span[itemprop = name]")
      jo.put("publisher", el.text())

      el = doc.select("meta[itemprop = price]")
      jo.put("price", el.attr("content"))

      el = doc.select("meta[itemprop = ratingValue]")
      jo.put("rating", el.attr("content"))

      scraped.add(jo)
    } catch (e: Exception) {
      when(e) {
        is java.net.UnknownHostException,
        is java.net.SocketTimeoutException -> {} else -> throw e
      }
    }
  }
  if (scraped.size > 0) { println(scraped) }

}
