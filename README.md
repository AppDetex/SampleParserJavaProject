
### To run the app
```
docker build -t sampleparserkotlinproject .
docker run --rm sampleparserkotlinproject <url>
```

alternatively...

```
mvn clean install
java -jar target/sampleparserkotlinproject-1.0-SNAPSHOT-jar-with-dependencies.jar <url>
```


### Notes
I renamed the project package from `com.appdetex.sampleparserjavaproject` to `com.appdetex.sampleparserkotlinproject`, as I wrote the project using Kotlin rather than Java and it seemed strange to keep it as 'java'. I did not change the repo name because I am still performing a pull-request into an existing repo.


I elected to have App as an abstract class with the idea that this would be far more simple to expand upon, and consequently more valuable, should one decide to scrape from additional app stores. Moving forward it would be simpler to digest, store, do-anything-with a shared concept of App.
 -  Inheritance was a little strange. Since I didn't have my values immediately available (had to parse the document) I couldn't simply instantiate the object by passing values to the constructor, which a lot of kotlin examples seemed to do


Being new to web scraping, I think there is _likely_ a better way to go about getting individual values from html. I'm particularly displeased with how I grab the rating value, using the vaguely identifying class ".BHMmbe". Should that be used again to style an element, my logic would be likely be broken. I'm a bit happier with when I was able to reference/access a value by 'itemprop'.

- Because of the unpredictable nature of (at least my implementation of..) web scraping I was quite liberal with my logging. I surrounded each attempt to parse the document with a try catch to not only ensure that a relevant error would be thrown, but to allow the program to keep running should it hit a snag. If my scrape for 'rating' is broken, I would still like to see price, title, etc.
- I pull the publisher from the "Offered By" section as that seemed like it would reliably be the publisher, not to  (potentially?) be mistaken with developer.

I grabbed the first paragraph based on the occurrence of a newline, which isn't ideal in the case where an app description uses newlines (or even <br>) for misc styling. I didn't see any use of <p> or even <pre> tags, however.

The tests I have are rudimentary. Ideally I would not be doing web calls in my tests. It would be worth looking into having a saved copy of the html and performing tests against that.

### Moving forward

As long as this is a CLI app, it would be simple enough to either...

1.  Rather than enter a url as an arg, run the app and be prompted to select a choice of supported stores. After selection, paste url of relevant store.
2.  Improvement upon '1.' is to parse the beginning of the url to determine the relevant store, if it's supported, parse accordingly
    *   This would allow you to make a further improvement by allowing a user to enter multiple urls and have the relevant json output for each one
    


### Things I will continue looking into

Kotlin.