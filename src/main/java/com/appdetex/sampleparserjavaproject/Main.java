package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.models.PackageInfo;
import com.appdetex.sampleparserjavaproject.utils.Constants;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
        // Put code here
        if(args.length != 1){
            printHelp(Constants.MISSING_URL);
        }else if(!Pattern.matches(Constants.EXPR_URL, args[0])){
            printHelp(Constants.INVALID_URL + "\n" + Constants.NOT_A_PLAY_STORE_URL);
        }else{
            try {
                String info = PackageInfo.getJSON(args[0]);
                System.out.println(info);
            }catch (IOException x){
                System.out.println("OUCH.");
                System.out.println(x.getMessage());
            }
        }
    }
    
    private static void printHelp(String msg){
        System.out.println("OOPS");
        System.out.println("========================");
        System.out.println(msg);
        System.out.println();
        System.out.println("USAGE");
        System.out.println("========================");
        System.out.println("Provide a valid URL to an app in the Google Play Store.");
        System.out.println();
        System.out.println("For Example:");
        System.out.println("https://play.google.com/store/apps/details?id=com.anjunsang.candyline&hl=en-US");
        System.out.println();
    }
}