package com.appdetex.candyscrape.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation drives the behavior of a concrete scraper implementation. It provides a CSS selector and related
 * metadata needed by the scraper to populate a model field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface CssSelectorPath {
    String value();
    String attribute() default "";
    String defaultValue() default "";

    String regex() default "";
    String regexGroupName() default "";
}
