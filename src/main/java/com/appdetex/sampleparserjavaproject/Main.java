package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.commands.CliService;
import com.appdetex.sampleparserjavaproject.commands.CmdPrompt;
import com.appdetex.sampleparserjavaproject.parsing.ParseQuery;
import com.appdetex.sampleparserjavaproject.parsing.ParsedInfo;
import com.appdetex.sampleparserjavaproject.parsing.WebParsingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

    private final CliService cliService;
    private final WebParsingService webParsingService;

    public void run(String... args) throws Exception {
        log.info("SPRING BOOT CAN DO CLI APPS?! Neat!");

        CmdPrompt prompt = cliService.getCmdPrompt();

        ParseQuery parseQuery = ParseQuery.builder()
                .websiteUrl(prompt.getWebsiteUrl())
                .build();

        ParsedInfo parsedInfo = webParsingService.parseWebsite(parseQuery);
        cliService.printParsedInfo(parsedInfo);
    }

    public static void main( String[] args ) {
        SpringApplication.run(Main.class, args);
    }
}
