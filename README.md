# SampleParserJavaProject

Example output from main with the program argument https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US

{"price":"$7.49","rating":4.5,"description":"Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.","publisher":"Mojang","title":"Minecraft"}


Example output from main with the program argument https://play.google.com/store/apps/details?id=me.lyft.android&hl=en-US

{"price":"$0","rating":3.5,"description":"Count on Lyft to take you where you need to go with safety first. Got an appointment? Need to pick up some groceries? We\u2019ll match you with a driver, help you find the quickest bus route, or show you the nearest scooter \u2014 you\u2019ll be on your way in minutes. If it gets you there, it\u2019s on the app.","publisher":"Lyft, Inc.","title":"Lyft - Rideshare, Bikes, Scooters & Transit"}


Result of mvn clean verify

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running scraper.tests.ScraperTest
{"price":"$7.49","rating":4.5,"description":"Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.","publisher":"Mojang","title":"Minecraft"}
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.195 sec

Results :

Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
