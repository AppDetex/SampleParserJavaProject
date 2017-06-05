# SampleParserJavaProject

A small scraping tool for parsing mobile app information from the Google Play app store's webpage.

# Getting started

Because I have a lot of custom environments it made sense to encapsulate this project using Docker. So at a minimum, you should be able to build and run this project if you have `make` and *Docker* installed. Just do

```
make env
```

which will provision the container and land you into a Bash shell. Everything you should need for development is here. You can then do 

```
make
```

which will build the app. You can run the app as follows.

```
% ./scrape

Usage: app <url> | <path to file>

Where <path to file> contains one URL per line
```

`scrape` accepts either a single URL or a file with a list of URLs. There are also some simple tests, which you can run by doing

```
make test
```

You can build without using Docker as well, but you will need a Bash compatible environment like Cygwin/MSYS. You will also need [jq](https://stedolan.github.io/jq/) in order to run tests.

## Kotlin

Just for grins, I decided to see what this would look like ported over to Kotlin, since it's [so hot right now](https://capitalventured.files.wordpress.com/2014/03/hansel.gif) given the official Android support. It's pretty cool, kind of like Python with types and curly braces.

You can build that with

```
make kotlin
```

The source file is `src/scrape.kt`.

## Maven

I admit I sort of glossed over the `pom.xml` file and its implication that I should build this using Maven. As a result, I sort of went ahead and did all the above by hand. :/

I've included a Maven target however, and you can also build that way by doing

```
make maven
```

This will build and run the `jar` file under the `target/` folder.
