package com.appdetex.sampleparserjavaproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 * 
 * NOTES by Ashish:
 * ---------------
 * By default URLs are read as command line arguments.
 * Here are the options:
 * 
 * a) 
 * Accepts "space delimited" string of URLs to be parsed
 * Prints the crawled data in JSON format to stdout
 * Also stores the output in a file "crawled.json"
 * 
 * b)
 * Can also read a file containing the URLs in different lines and collect 
 * 
 * IMPORTANT:
 * To read URLS from the file ("urls.txt"): Uncomment the "Comment Section 2" and comment out "Comment Section 1"
 * 
 * See the sample file "urls.txt" which I used to test. Please feel free to test any other similar webpages.
 * 
 */
public class Main {
	// collects the command line arguments
	private static Options options;
	private static CommandLine cmd; 
	// object to store multiple objects crawled from multiple urls
	private static ArrayList<PageInfo> listOfPageInfo;
	// object to store list of urls
	private static ArrayList<String> listOfUrls;
	
	// main method
    public static void main( String[] args ) { 
    	listOfPageInfo= new ArrayList<PageInfo>();
    	
    	// IMPORTANT: 
    	// Comment Section 1 starts here
    	// Read and parse URLs as command line arguments
    	if(!parseCommandLineArguments(args)){
    		printUsageAndExit();
    	}
    	// Comment Section 1 starts here
        
        
        // IMPORTANT: Comment Section 2 starts here
        // Read and parse URLs from file
//        try {
//        	listOfUrls = new ArrayList<String>();
//			readFile(new File("urls.txt"));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
     // Comment Section 2 ends here
        
        System.out.println("Started URLs crawling...");
        
        // Loop through the collected URLs and crawl one by one
        for(String targetUrl: listOfUrls){        	
        	// Crawl URL
        	Document pageDocObject = null;
    		try {
    			System.out.println("Crawling contents from url: "+ targetUrl);
    			pageDocObject = Jsoup.connect(targetUrl).get();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		// Parse HTML DOM 
    		parseHtmlDom(pageDocObject);
    		System.out.println("DONE crawling contents from url: "+ targetUrl);
        }
        
        
        Gson g = new Gson();
        String jsonString = g.toJson(listOfPageInfo);
        // dump all the collected data from all the urls into a single json file
        writeToFile(jsonString);
    	
    }
    
    // prints the command line usage and exit with non-zero code
    public static void printUsageAndExit(){
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Main", options, true);
		System.exit(1);
	}
    
    // parses command line arguments and store the list of urls
    // returns if the commandline arguments are valid or not
    private static boolean parseCommandLineArguments(String[] args){
    	options = new Options();
		
		Option optListOfUrls = Option.builder("lu").hasArg().desc("Space delimitedList of Urls").required(true).build();
		
		options.addOption(optListOfUrls);
	
		CommandLineParser parser = new DefaultParser();
		cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println("Argument parsing error \n"+e);
			return false;
		}
		String urls = "";
		if(cmd.hasOption("lu")){
			if(cmd.getOptionValue("lu") == null){
				return false;
			}
			urls = cmd.getOptionValue("lu");
			System.out.println(urls);
			String[] urlArray = urls.trim().split(" +");
			listOfUrls = new ArrayList<String>(Arrays.asList(urlArray));
		}
		return true;

    }
    
    private static void parseHtmlDom(Document pageDocObject){
    	// Gather the targeted html elements
    	Elements title = pageDocObject.select(Utils.TITLE);
    	Elements desc = pageDocObject.select(Utils.DESC);
    	Elements pub = pageDocObject.select(Utils.PUBLISHER);
    	Elements price = pageDocObject.select(Utils.PRICE);
    	Elements rating = pageDocObject.select(Utils.RATING);
    	
    	// Verify if the application is free or not
    	String priceOnly = "";
    	if(price.html().equals("Install")){
    		priceOnly = "Free";
    	} else {
    		priceOnly = price.html().split(" ")[0];
    	}
    	// prepare an object
    	PageInfo parsedObject = new PageInfo(title.text(), desc.text().replace("\\", ""), pub.text(), priceOnly, rating.text());
    	listOfPageInfo.add(parsedObject);
    	
    	Gson g = new Gson();
        String thisObjectInJsonFormat = g.toJson(parsedObject);
        System.out.println(thisObjectInJsonFormat);
    	
    }
    
    // read a file and prepare a list of urls
    private static void readFile(File fin) throws IOException {
    	FileInputStream fis = new FileInputStream(fin);
    	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
    	String line = null;
    	while ((line = br.readLine()) != null) {
    		listOfUrls.add(line.trim());
    	}
     
    	br.close();
    }
    
    // write a string to a file
    private static void writeToFile(String jsonString){
    	try {
			File file = new File("crawled.json");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(jsonString);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
