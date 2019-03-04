# Sample Parser Java Project

The project is a small scraping tool for parsing mobile app information from the Google Play app store's webpage. This 
program accepts a URL for a Google Play app page as a command line argument, and returns a JSON-formatted string 
containing relevant data for the app: app title, first paragraph of the description, publisher name, price, and rating
(average review score).

## Getting Started

### Openning and Running in IntelliJ
* [Download and install IntelliJ](https://www.jetbrains.com/idea/download/)
* [Download and install Git](https://git-scm.com/downloads)
* [Download and install Java 8 SE](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Download and install Maven](https://maven.apache.org/download.cgi)

1) Run IntelliJ and on the welcome screen choose "Check out from Version Control" and
select "git".
2) In the "URL" field, enter [https://github.com/nbrinton/SampleParserJavaProject.git](https://github.com/nbrinton/SampleParserJavaProject.git).
3) Once the project opens, a prompt in should pop up that says "Maven projects need to be imported". You can choose 
either option, but I would recommend just manually importing the changes for now by selecting "Import Changes".
4) The "Maven" side-bar should already be open on the right-side of your screen, but if not you can open it by clicking
on the icon in the very bottom left of the editor. In here you can execute any of the Maven lifecycles.
5) Edit the configuration settings to include the google playstore uri for the app you want to have parsed as a command
line argument, and also make sure to select the parent directory of the project in the "Use classpath of module:" for 
the "Run as Application" and the "HTMLParserTest" configurations. Now you should be able to run both through IntelliJ.
6) You can also run the unit tests by running the "test" lifecycle in the Maven bar.


### Running in Terminal
TODO

I am not super familiar with Maven and this is the first I have worked with it. I tried following along with the 
[Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) tutorial, but for some
reason even though the project builds correctly by running the `mvn package` phase, when you run it via the command
`java -cp target/sampleparserjavaproject-1.0-SNAPSHOT.jar com.appdetex.sampleparserjavaproject.App`, the program will
execute up until the point where a dependency library is used in the code and then it says it can't find it and throws
an exception.


### Prerequisites
If you're running the project in IntelliJ then you should only need to have that installed as well as the Java version
that you want it to use.

If you're wanting to run from the terminal, then you'll need to have Git installed to clone the repo, Maven installed to
build and run the project, and the version of Java you want to run it with also.

## Built With
* [Maven](https://maven.apache.org/) - Dependency management
* [JSoup](https://jsoup.org/) - Java HTML parsing library
* [JUnit 4](https://junit.org/junit4/) - Java testing framework
* [Apache Commons URL Validator](http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/routines/UrlValidator.html) - URL validation

## Authors
* **Nathan Brinton**

## Acknowledgments
* [README template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
* [Helpful IntelliJ YouTube video on Maven integration](https://www.youtube.com/watch?v=pt3uB0sd5kY)
* [Helpful JSoup tutorial](https://medium.com/@werdna3232/how-to-use-jsoup-to-scrape-webpages-tutorial-d0a676661b4a)
* [JUnit 4 POM entry](https://github.com/junit-team/junit4/wiki/Download-and-Install)
* [Java URL validation Stack Overflow post](https://stackoverflow.com/questions/2230676/how-to-check-for-a-valid-url-in-java)
* [Apache URL validation POM entry](https://mvnrepository.com/artifact/commons-validator/commons-validator/1.4.0)
* [JUnit: test that an exception is thrown when it should be](https://www.codejava.net/testing/junit-test-exception-examples-how-to-assert-an-exception-is-thrown)

