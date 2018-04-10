package com.appdetex.sampleparserjavaproject;
import com.appdetex.sampleparserjavaproject.lib.*;
import com.appdetex.sampleparserjavaproject.lib.json.Json;
import com.appdetex.sampleparserjavaproject.lib.json.JsonStrategyGson;
import com.appdetex.sampleparserjavaproject.lib.scrape.Scrape;
import com.appdetex.sampleparserjavaproject.lib.scrape.ScrapeStrategyJsoup;
import com.appdetex.sampleparserjavaproject.lib.xargs.Xargs;
import com.appdetex.sampleparserjavaproject.lib.xargs.XargsInputException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 *
 * A state machine might be a good candidate for a future upgrade, especially if this evolves into
 * an interactive console application, where it would then become helpful for negotiating screen changes and
 * give an approximate service layer. Here is how it might look:
 *
 * ApplicationFsm app = new ApplicationFsm(xargs, console, scrape, json);
 * app.start();
 * app.transition("initialize"); *
 * app.transition("setup", attributes); // Bring in all data
 * app.transition("load-data-using-cli-param"); // A discreet loading step
 * app.transition("show-results")); // Display whatever data is present
 *
 * Under normal circumstances there might be a different design that emerges as a result
 * of other lean insights. Being open to change here is the goal without over-engineering.
 *
 * I'm using adapters in many places to insulate against tool changes. For instance, SimpleJson
 * might become deprecated and need a replacement.
 *
 * Constructor DI is used to keep dependencies high up the chain, using Standard Library only and KISS.
 */
public class Main {

    public static void main( String[] args ) {

        /*
         * Start your engines
         */

        Xargs xargs = new Xargs();
        JsonStrategyGson jsonStrategyGson = new JsonStrategyGson();
        Json json = new Json(jsonStrategyGson);
        ScrapeStrategyJsoup scrapeStrategy = new ScrapeStrategyJsoup();
        Scrape scrape = new Scrape(json, scrapeStrategy);
        PrintStream ps = new PrintStream(System.out);
        Cons console = new Cons(ps);

        /*
         * Transient config
         * Requested params and command line options
         */

        List<String> attributes = new ArrayList<String>();
        attributes.add("title");
        attributes.add("description");
        attributes.add("publisher");
        attributes.add("price");
        attributes.add("rating");

        Map<String, Object> xargsOptions = new HashMap<String, Object>();
        xargsOptions.put("opt", "u");
        xargsOptions.put("longOpt", "url");
        xargsOptions.put("hasArg", true);
        xargsOptions.put("description", "url of play store app");

        /*
         * Main application run
         */

        try {
            xargs.init(xargsOptions, args);
            scrape.init(xargs.get("url"));
            String result = scrape.queryAttributes(attributes).toJsonPretty();
            scrape.queryAttributes(attributes).toJsonPretty();
            console.write(result);

        } catch (XargsInputException e) {
            System.exit(1);

        } catch (UnknownHostException e) {
            console.write("Unknown host");
            xargs.printHelp();
            System.exit(1);

        } catch (IllegalArgumentException e) {
            console.write("Malformed URL");
            xargs.printHelp();
            System.exit(1);

        } catch (InvocationTargetException e) {
            console.write("Attribute not found");
            xargs.printHelp();
            System.exit(1);

        } catch(Exception e) {
            e.printStackTrace();
            xargs.printHelp();
            System.exit(1);
        }
    }
}
