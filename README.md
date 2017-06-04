# SampleParserJavaProject

### Assignment requested: 

The project we're asking you to complete is a small scraping tool for parsing mobile app information from the Google Play app store's webpage. We'd like you to build a Java program which accepts a URL for a Google Play app page as a command line parameter, and returns a JSON-formatted string containing relevant data for the app. The data we're interested in are: app title, first paragraph of the description, publisher name, price, and rating (average review score).

For example, for the app "Carcassonne," the scraper will be provided the URL https://play.google.com/store/apps/details?id=com.exozet.game.carcassonne and will output a string looking something like:

```JSON
{
    "title": "Carcassonne",
    "description": "The award-winning tile based board game is finally here on Android! Just a few years after its release, Carcassonne became a modern classic and a must-play. Turn by turn, the players create a landscape by placing tiles with roads, cities, fields, and cloisters. The players deploy their followers, so called Meeples, as knights, monks, thieves, or farmers to earn points. The player with the most points after the final scoring wins the game. The ever-changing landscape makes each game a new experience. You can play against clever AI opponents or with up to 5 other players in an online or local multiplayer match.",
    "publisher": "Exozet",
    "price": "$4.99",
    "rating": 4.3
}
```

An empty project has been created on GitHub.com here: https://github.com/AppDetex/SampleParserJavaProject. This project contains an empty Main.java that will need to be built out to complete the task. Additional files/classes can be added as you see fit. The existing Maven file (pom.xml) can also be modified as necessary.

To complete the above task, we'd like you to to create a free GitHub account (if you don't already have one), fork the empty project, complete the task in your fork, then submit a pull request to the original project. This process is described in this documentation on GitHub:
Fork a Repo: https://help.github.com/articles/fork-a-repo/
Creating a pull request: https://help.github.com/articles/creating-a-pull-request/
