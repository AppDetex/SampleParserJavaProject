Created By
--------------------------
Jake Morrison
--------------------------

For
--------------------------
AppDetex
--------------------------

Objective
--------------------------
The project we're asking you to complete is a small scraping tool for parsing mobile app
information from the Google Play app store's webpage. We'd like you to build a Java program
which accepts a URL for a Google Play app page as a command line parameter, and returns a
JSON-formatted string containing relevant data for the app. The data we're interested in are:
app title, first paragraph of the description, publisher name, price, and rating (average review score).
--------------------------

Files
--------------------------
1) Main.java: Completed the objective by using jSoup.
2) MainNoSoup.java: Completed the objective by not using the jSoup library and a bunch more code.
   I did this on accident. I didn't fork repository before I wrote the code, so I didn't see the
   comments saying it was okay to use jSoup.
3) GooglePlayTop.java: Generates list of all urls from Google play homepage.
4) GooglePlayTest.java: Test the scrapper application using the url found in GooglePlayTop.java.
5) test_urls.txt: Output of GooglePlayTop.java
6) results_urls.txt: Output of GooglePlay.Test.java
--------------------------

To Run
--------------------------
javac filename.java
java filename 'some_url'