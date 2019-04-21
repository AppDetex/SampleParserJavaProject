# Google App Store Scraper

## Use of Kotlin
- Changed base language to Kotlin
- Minimal implementation of Main
    - Use Data class to capture scraper results
    - Use of existing packages to convert data class to JSON
    - Print JSON result to stdout
- Documentation bases added to functional code segments

## Overall Approach


## How to Run
Due to the fact that I have not implemented the correct Maven configurations
use of this is limited to direct compilation and java execution
```
cd src/main/java/com/appdetex/sampleparserjavaproject
kotlinc .  -include-runtime -d scrape.jar
java -jar scrape.jar "<Full url including http/s>"
```


## Things that are not working
- Spock testing via Maven
- Automated documentation building

## Things that are *not* going to done
- VCR or other interceptor to prevent direct calls to Google App Store
