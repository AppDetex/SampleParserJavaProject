package com.appdetex.sampleparserjavaproject;

public class Utils {

	/**
	 * @author Ashish
	 *
	 * Just a util class to store the targeted html elements for each information that we need
	 */
	public Utils() {
		// TODO Auto-generated constructor stub
	}
	
	// Targeted DOM Elements
	public static final String TITLE = "div.id-app-title";
	public static final String DESC = ".show-more-content > div:nth-child(1)";
	public static final String PUBLISHER = "a.document-subtitle:nth-child(2) > span:nth-child(1)";
	public static final String PRICE = "span.apps:nth-child(1) > button:nth-child(2) > span:nth-child(3)";
	public static final String RATING = "div.score";
	

}
