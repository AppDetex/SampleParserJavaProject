package com.appdetex.sampleparserjavaproject;
import com.appdetex.sampleparserjavaproject.lib.*;
import com.appdetex.sampleparserjavaproject.lib.xargs.Xargs;
import com.appdetex.sampleparserjavaproject.lib.xargs.XargsInputException;
import com.google.gson.Gson;

import java.util.*;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 *
 *
 * An design choice.
 * A state machine might be a good candidate for a future upgrade, especially if this evolves into
 * an interactive console application, where it would then become helpful for negotiating screen changes and
 * give an approximate service layer. Here is how it might look:
 *
 * app.start();
 * ApplicationFsm app = new ApplicationFsm(xargs, console, scrape, json);
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

        Cons console = new Cons(System.out);
        Xargs xargs = new Xargs(console);
        Json json = new Json(new Gson());
        Scrape scrape = new Scrape(json);

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
        xargsOptions.put( "opt", "u" );
        xargsOptions.put( "longOpt", "url" );
        xargsOptions.put( "hasArg", true );
        xargsOptions.put( "description", "url of play store app" );

        /*
         * Main application run
         */

        try {
            xargs.init(xargsOptions, args);

            scrape.init(xargs.get("url"));

            String result = scrape.attributes(attributes).toJson();

            console.write(result);

        } catch (XargsInputException e) {

            System.exit(1);

        } catch(Exception e) {

            console.write(e.getMessage());

            console.write(Arrays.toString(e.getStackTrace()));

            System.exit(1);
        }
    }
}
