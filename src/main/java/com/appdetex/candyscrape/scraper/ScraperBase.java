package com.appdetex.candyscrape.scraper;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Base scraper common functionality inherited by all concrete implementations.
 */
public abstract class ScraperBase implements IScraper {

    /**
     * Selects a portion of an element's value given a regex matching group.
     * @param value element text.
     * @param regex regex containing matching group.
     * @param regexGroupName if specified the named matching group will be used instead of the first group.
     * @return matched group or null.
     */
    protected String transformValueUsingRegex(String value, String regex, String regexGroupName) {
        if (!StringUtils.isEmpty(regex)) {
            Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(value);
            if (matcher.find()) {
                if (!StringUtils.isEmpty(regexGroupName)) {
                    return matcher.group(regexGroupName).trim();
                } else {
                    return matcher.group(1).trim();
                }
            }
            return null;
        }

        return value;
    }

    /**
     * Given a scraper model, attempts to invoke a suitable setter for a field.
     * @param model scraper model.
     * @param field the field being set.
     * @param value the value to set the field to.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    protected void setValueOnModel(Object model, Field field, String value)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Method setter = null;
        try {
            setter = model.getClass().getDeclaredMethod(
                    "set" + StringUtils.capitalize(field.getName()), String.class);
        } catch (NoSuchMethodException e) {}

        if (setter == null) {
            setter = model.getClass().getDeclaredMethod(
                    "set" + StringUtils.capitalize(field.getName()), Float.class);
            setter.invoke(model, Float.parseFloat(value));
        } else {
            setter.invoke(model, value);
        }
    }
}
