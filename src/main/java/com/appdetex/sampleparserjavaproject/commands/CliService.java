package com.appdetex.sampleparserjavaproject.commands;

import com.appdetex.sampleparserjavaproject.parsing.ParsedInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class CliService {

    private final ObjectMapper objectMapper;

    public CmdPrompt getCmdPrompt() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("\r\n\r\nEnter app store url: ");
        String websiteUrl = scanner.next();

        return CmdPrompt.builder()
                .websiteUrl(websiteUrl)
                .build();
    }

    public void printParsedInfo(ParsedInfo parsedInfo) throws JsonProcessingException {
        System.out.println("\r\n\r\n" + objectMapper.writeValueAsString(parsedInfo) + "\r\n\r\n");
    }

}
