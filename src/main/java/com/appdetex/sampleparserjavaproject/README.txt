README for AppDetex SampleParserJavaProject

Author: Jack K. Seaton
Date: Dec 7th, 2015

	 
I did copy the output of my program, put it into JSONLint and ran their test resulting in a "valid JSON".
		 
	link - http://jsonlint.com/
		 
		 		 
I also copied the JSON that is output from my program (when passed the URL to Air Navigation Pro via the 
	command line) and compared it to the initial JSON object provided in the first email detailing the 
	extent of the project.
	
	I first ran a comparison of the two strings:	
	"Jack goes to Boise State" vs. "Jack goes to Boise State" which resulted in no differences (as it should have).
	
	Meanwhile, the comparison of :	
	"Jack goes to Boise State" vs. "Jack goes to Boise Statee" showed that State in the 2nd String contained an 
	extra e (as it should have).
	
	After the two initial tests to make sure that the program would in fact catch differences I ran my version of 
	the JSON against the JSON that was supplied at the start of my program. 
	
	NOTE: The website did pick up 1 difference in that the given JSON has the price of the app listed at $23.43, 
	while the cost of the app on the website is now $23.42, but I'm just chalking that up to the price being 
	lowered by $0.01 between being given the project, and when I completed my project and ran it through my test cases
	
	Tests were performed on  -  https://www.diffnow.com/		
	
	Due to the various and through test cases I am confident that my program will meet your described desired results
	in creating a JSON object given a URL to the Google Play Store as a Command line argument.
	  
Various other Google Play Store App's used to test my program:
		  
   A well known, free app
   Hill Climb Racing - https://play.google.com/store/apps/details?id=com.fingersoft.hillclimb&hl=en 
  
   A random free app
   Converting Numbers into letter - https://play.google.com/store/apps/details?id=com.EricBROU.nombres_en_lettres&hl=en
   
   A random paid app
   Convertr : Units and Currency - https://play.google.com/store/apps/details?id=com.r3app.convertr&hl=en
   
