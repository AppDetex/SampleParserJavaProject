# AppDetex Coding Challenge

## Description

Build a program which accepts a URL for a Google Play app page as a command line parameter, and returns a JSON-formatted string containing relevant data for the app. The data we're interested in are: app title, first paragraph of the description, publisher name, price, and rating (average review score).


### Building
Maven project that requires Java 11 or above.

To build, simply run: `mvn install`

To run, execute: `java -jar target\sampleparserjavaproject-1.0-SNAPSHOT-jar-with-dependencies.jar <url>`

#### Tests
There are two main test classes included. One that runs against live data and one that runs against canned data.

`mvn test` will run both sets of tests

`mvn test -PLiveTests` will run the live URL tests - if all of these tests fail, then the page format likely has changed. If some fail, then the 
live data has probably drifted from the test expectations.

`mvn test -PCannedTests` will run the canned file tests.

### Example output
For example, for the app "Minecraft" the scraper will be provided the URL https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US and will output a string looking something like:

```json lines
{
"title": "Minecraft",
"description": "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.",
"publisher": "Mojang",
"price": "$7.49",
"rating": 4.5
}
```