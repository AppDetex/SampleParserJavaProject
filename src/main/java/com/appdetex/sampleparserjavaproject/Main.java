package com.appdetex.sampleparserjavaproject;

import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) throws IOException{
        String parsedApp;

        if (args.length > 0) {
            AppInfoParser parsedAppInfo = new AppInfoParser(args[0]);
            parsedApp = parsedAppInfo.parseAppInfo();
            System.out.println(parsedApp);
        } else {
            System.err.println("Please enter a valid URL to begin application translation");
            System.exit(9);
        }
    }
}

/**
 * Here is a test URL: https://play.google.com/store/apps/details?id=com.icegame.candyline
 *
 * This is a second test URL: https://play.google.com/store/apps/details?id=com.imangi.templerun2
 *
 * Here is a URL with a price other than $0: https://play.google.com/store/apps/details?id=com.mojang.minecraftpe
 *
 */