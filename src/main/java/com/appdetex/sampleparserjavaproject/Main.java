package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * Main Java Class
 * <p>
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

  public static void main(String[] args) {
    try {
      if(args.length == 0) {
        System.out.println("Please provice a valid app URL");
        System.exit(1);
      }

      Document doc = Jsoup.connect(args[0]).get();
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode node = mapper.createObjectNode();
      node.put("title", getTitle(doc));
      node.put("description", getDescription(doc));
      node.put("publisher", getPublisher(doc));
      node.put("price", getPrice(doc));
      node.put("rating", getRating(doc));

      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String getTitle(Document doc) {
    return doc.select("div.id-app-title").first().text();
  }

  private static String getPublisher(Document doc) {
    return doc.select("div.left-info").first().select("a.primary").text();
  }

  private static String getPrice(Document doc) {
    Element priceElement = doc.select("button.price").first();
    return priceElement.child(0).child(0).child(1).attr("content");
  }

  private static String getDescription(Document doc) {
    return doc.select("div.description").first().select("div.text-body").first().child(0).text();
  }

  private static String getRating(Document doc) {
    return doc.select("div.score").first().text();
  }

  /*
  Unfortunately this test method would need to be updated whenever the product's price/rating changes.  An alternative
  would be to save the HTML to a file and reload it, but then we would fail to detect when the live HTML's format
  changes.
   */
  @Test
  public void test() throws IOException {
    Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=com.exozet.game.carcassonne")
        .get();
    assert (getTitle(doc).equals("Carcassonne"));
    assert (getDescription(doc).startsWith("The award-winning tile based board game is finally here on Android!"));
    assert (getPublisher(doc).equals("Exozet"));
    assert (getPrice(doc).equals("$0.99"));
    assert (getRating(doc).equals("4.3"));
  }
}
