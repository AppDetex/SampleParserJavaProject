package com.appdetex.sampleparserjavaproject.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of DataFormatter interface that takes an object and maps it to JSON
 */
public class JsonFormatter implements DataFormatter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String format(final Object o) throws FormattingException {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new FormattingException(e.getMessage(), e);
        }
    }

}
