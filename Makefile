.DEFAULT_GOAL := build

APPNAME='scrape'
TESTURL='https://play.google.com/store/apps/details?id=com.exozet.game.carcassonne'


# Sets up the Java environment via Docker (this is a host-only target)
.PHONY: env
env:
	docker build . -t javaenv
	docker run -v `pwd`:`pwd` -w `pwd` --rm --name javaenv-run -it javaenv


# "Neutral" targets hence (i.e. can run in host, not just container)
.PHONY: build
build:
	javac -classpath lib/json-simple-1.1.1.jar:lib/jsoup-1.10.2.jar \
		-d ./build `find . | grep -E '\.java$$'`
	cd build && jar cmvf META-INF/MANIFEST.MF ../$(APPNAME).jar * && cd ..
	ls -altr *.jar
	@echo java -cp $(APPNAME).jar:lib/* com.appdetex.sampleparserjavaproject.Main "$$"@ > $(APPNAME)
	@chmod a+x $(APPNAME)
	@echo Build finished. You can run with ./$(APPNAME)


.PHONY: test
test: build
	./test/run.sh


.PHONY: clean
clean:
	rm -fr build/com
	rm -f $(APPNAME)*.jar
	find src | grep -E '\.class' | xargs rm -f
	rm -f $(APPNAME)


# Just trying it out
.PHONY: kotlin
kotlin:
	if [ ! -d kotlinc ]; then \
		wget http://download.openpkg.org/components/cache/kotlin/kotlin-compiler-1.0.6.zip && \
		unzip kotlin-compiler-1.0.6.zip ; \
	fi
	kotlinc/bin/kotlinc -classpath lib/json-simple-1.1.1.jar:lib/jsoup-1.10.2.jar src/scrape.kt -include-runtime -d scraperkt.jar
	#java -cp scraperkt.jar:lib/* scraper.ScrapeKt $(TESTURL)
	java -cp scraperkt.jar:lib/* scraper.ScrapeKt test/urls.txt | jq '.'


.PHONY: maven
maven:
	mvn clean dependency:copy-dependencies package
	java -cp target/dependency/*:target/sampleparserjavaproject-1.0-SNAPSHOT.jar com.appdetex.sampleparserjavaproject.Main $(TESTURL)



