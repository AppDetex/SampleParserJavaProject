Candy Scrape
===========================================================

Candy Scrape is a model-driven declarative scraper. It
makes for flexible and easily maintained data acquisition projects, free
from repetitious manual scraping procedures.

##### Example Scraper Model

```Java
@Data
public class GooglePlayAppModel {

    @CssSelectorPath(value="meta[itemprop=\"name\"]", attribute="content")
    private String title;

    @CssSelectorPath("a[href^=\"https://play.google.com/store/apps/developer?id=\"]:not(:has(div))")
    private String publisher;
    
    @CssSelectorPath("c-wiz div[aria-label^=\"Rated \"]:not(:has(div))")
    private Float rating;

    ...
}
```

##### Adding a New Model

Candy Scrape can easily be extended beyond its current Google Play Store
scraping functionality. By adding new target to model keypairs in 
`config.DomainModelConfiguration` other static web resources can easily
be parsed.

Models are simply POJOs with `@CssSelectorPath` annotations defining how
fields should be parsed. 

##### @CssSelectorPath Options

- **value**: a valid CSS selector path.
- **attribute**: (Optional) If the scraped text should be retrieved from an attribute over
    inner text, provide the attribute name here.
- **defaultValue**: (Optional) If the field should be populated with a default
    value when retrieval fails, specify it here.
- **regex**: (Optional) Field values can be filtered down using regex group matching.
    Specify a capture group here.
- **regexGroupName**: (Optional) In the case of multiple capture groups, a named
    group can be specified here. 

#### Building the Project

The project can be built using Maven 3.
 
```bash
mvn clean package
```

#### Running the Project

The application takes a single command line parameter, the URL to scrape. If 
an URL is specified with no model backing the application will fail. Currently
only google play app store pages are modeled.

```bash
java -jar target/candyscrape-1.0-SNAPSHOT.jar https://play.google.com/store/apps/details?id=com.jds.batim
```

#### Testing

The project can be tested with the `test` maven lifecycle command.
 
```bash
mvn clean compile test
```