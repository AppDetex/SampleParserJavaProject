# SampleParserJavaProject [![Build Status](https://travis-ci.org/benjaminbartels/SampleParserJavaProject.svg?branch=master)](https://travis-ci.org/benjaminbartels/SampleParserJavaProject)

SampleParser scrapes the provided Google Play Store application page and returns it's Title, Description (first paragraph)
Publisher, Price and Rating in JSON format. 

```json
{
  "title": "Minecraft",
  "description": "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.",
  "publisher": "Mojang",
  "price": "$6.99",
  "rating": 4.50
}

```

## To Build
```sh
mvn clean package
```

## To Run Tests
```sh
mvn test
```

## To Run the App 
```sh
cd target
java -jar SampleParser url
```

### Usage Example
```sh
java -jar SampleParse.jar 'https://play.google.com/store/apps/details?id=com.mojang.minecraftpe'
```