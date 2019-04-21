# Google App Store Scraper

## Use of Kotlin
- Changed base language to Kotlin
- Minimal implementation of Main
    - Use Data class to capture scraper results
    - Use of existing packages to convert data class to JSON
    - Print JSON result to stdout
- Documentation bases added to functional code segments
- Use Spock for testing to allow for easy test reading

## Overall Approach
- Don't do anything that has been done before
- Use existing tools
- Don't fight with Maven
- Run from IntelliJ to get around fighting with build tools
- Add minimal documentation for clarification
- Minimal main file to prevent over complications in non-critical files

### General thoughts
- I am not a Maven expert, do what I have to in order to get it to run in the IDE
- Don't use java, as I would have to implement many things that are already in Kotlin
- Use a data class to hold results thus allowing a standard format for any parser to use
- Don't implement conversion to JSON. There are plenty of tools that allow for this.
- Add some test to verify defaults return from a bad url
- Add a test for verification of the "good" result
- Don't spend an insane amount of time fighting with Java package issues


My thoughts pretty much boil down to:
- One data class
- One Scraper class
- Only use main to ingest url and print json result

## Things that are not working
- Spock testing via Maven
- Automated documentation building

## Things that are *not* going to done
- VCR or other interceptor to prevent direct calls to Google App Store
