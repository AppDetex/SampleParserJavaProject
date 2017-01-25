#Sample Parser Java Project
A simple google play app details parser.

The details that are parsed are:

    Title
    Description
    Publisher
    Price
    Rating

To install run:
    
    maven install
    
A jar should be built that you can run with:

    java -jar target/sampleparserjavaproject-1.0-SNAPSHOT.jar <google play app url>
    
Where the <google play app url> is a valid app url. Example:

    https://play.google.com/store/apps/details?id=com.exozet.game.carcassonne
    

#Technologies Used
    Jsoup
    Maven
    Maven-shade
    Gson
    TestNG
    Mockito
    PowerMock
    
#Test Coverage
The total test coverage is not 100%. I added coverage for a decent portion of the code
which should be good enough for a fun test project like this.