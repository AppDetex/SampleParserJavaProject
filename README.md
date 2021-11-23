# Sample Parser Kotlin Project
@author [*Jason Buchanan*](https://github.com/jsnbuchanan)

## Developers 

### Prerequisites
- [Maven 3.8.3](https://maven.apache.org/install.html) 
- [JDK 11 or higher](https://www.oracle.com/java/technologies/java-se-glance.html)
- An IDE that supports Kotlin like [IntelliJ](https://www.jetbrains.com/idea/) or the [Kotlin command-line compiler](https://kotlinlang.org/docs/command-line.html)

### Setup
Install dependencies by running the following from the command line.
```
mvn clean install
```

### Build
To build the project run the command:
```
mvn compile
```

### Testing with Kotest
If you're using Intellij, install the [**Kotest plugin**](https://plugins.jetbrains.com/plugin/14080-kotest) for running the tests in the IDE.

To test the project run the command:
```
mvn test
```

### Package
Create a runnable jar with all dependencies included using the following command.
```
mvn package
```

### Run
Run the application with one of the three following command. The app runs on an 
input loop. The first url can be supplied from the command line, but after that 
the app does not immediately exit. It will continue to request crawl targets
until you're tired of it. Type 'quit' to exit.

#### 1. Run it with no command line args
It will operate as a text prompt application. I know you're homesick for zork, 
midnight commander and pine.
```
java -jar bin/sampleparserjavaproject-1.0-SNAPSHOT-jar-with-dependencies.jar
```
or
#### 2. Run it by passing a url in the commandline as specked
This is the implementation that was asked for. I could have stopped there, 
but was having fun learning Kotlin.
```
java -jar bin/sampleparserjavaproject-1.0-SNAPSHOT-jar-with-dependencies.jar "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"
```
or
#### 3. Pass a file of URL's to the app for maximum test speed
If you're an OG gangster, pass in a file with a url on each line. I've 
added a test file with ten urls to test the Play Store.
```
<src/test/resources/list-of-app-urls.txt xargs -n1 java -jar bin/sampleparserjavaproject-1.0-SNAPSHOT-jar-with-dependencies.jar
```


## Useful Tools
- **Semantic Commit Messages** For git commits I like to use the [Karma convention for commit messages](http://karma-runner.github.io/1.0/dev/git-commit-msg.html).


- **Kotest** For semantic tests with a StringSpec similar to spock. This is the first time I've touched. [Documentation](https://kotest.io/docs/quickstart) and [Code](https://github.com/kotest/kotest).


- **MockK** Kotest is not opinionated on Mocking, Stubbing and Spies, so I chose to use MockK as an elegant solution. [Documentation](https://mockk.io/) and [Code](https://github.com/mockk/mockk).


- **kotlinx-serialization-json** [Read about how Kotlin serializes JSON](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serialization-guide.md) beautifully.