package com.appdetex.sampleparserjavaproject.formatter;

/**
 * Simple formatter interface
 */
public interface DataFormatter {
    String format(final Object o) throws FormattingException;
}
