package com.appdetex.candyscrape.scraper;

import com.appdetex.candyscrape.meta.CssSelectorPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


/**
 * Jsoup based web-scraper implementation.
 */
@RequiredArgsConstructor
@Slf4j
public class JsoupScraper extends ScraperBase {

    private final IDocumentRetriever documentRetriever;

    /**
     * Invokes the scraper, returning a populated model.
     * @param targetUrl the url to retrieve.
     * @param modelClass the class definition of the model.
     * @return a populated model or null if a failure occurred.
     */
    public <T> T scrape(String targetUrl, Class<T> modelClass) {
        T model;
        try {
            model = modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Could not instantiate scraping model.", e);
            return null;
        }

        Document doc = documentRetriever.fetch(targetUrl);
        if (doc == null) {
            log.error("Could not retrieve document at path [{}]", targetUrl);
            return null;
        }

        for (Field field : modelClass.getDeclaredFields()) {
            for (Annotation ann : field.getAnnotations()) {
                if (ann instanceof CssSelectorPath) {
                    CssSelectorPath cssSelectorPathAnnotation = (CssSelectorPath)ann;
                    String selectorPath = cssSelectorPathAnnotation.value();
                    String attribute = cssSelectorPathAnnotation.attribute();
                    String defaultValue = cssSelectorPathAnnotation.defaultValue();
                    String regex = cssSelectorPathAnnotation.regex();
                    String regexGroupName = cssSelectorPathAnnotation.regexGroupName();
                    Elements elList = doc.select(selectorPath);

                    if (elList.size() != 1 && StringUtils.isEmpty(defaultValue)) {
                        log.error("Failed to parse selector path [{}], required exactly one element match.", selectorPath);
                        return null;
                    } else {
                        String value;
                        if (elList.isEmpty()) {
                            value = defaultValue;
                        } else if (StringUtils.isEmpty(attribute)) {
                            value = elList.get(0).ownText().trim();
                        } else {
                            value = elList.get(0).attr(attribute).trim();
                        }

                        value = this.transformValueUsingRegex(value, regex, regexGroupName);
                        if (value == null) {
                            log.error("Failed to parse value for selector [{}], no regex match {}, {}.",
                                    selectorPath, regex, regexGroupName);
                            return null;
                        }

                        try {
                            this.setValueOnModel(model, field, value);
                        } catch (NoSuchMethodException | InvocationTargetException e) {
                            log.error("Failed to parse selector path [{}], field has no compatible setter.", selectorPath);
                            return null;
                        } catch (IllegalAccessException e) {
                            log.error("Failed to parse selector path [{}], setter is not accessible.", selectorPath);
                            return null;
                        } catch (NumberFormatException e) {
                            log.error("Failed to parse selector path [{}], value was not numeric.", selectorPath);
                            return null;
                        }
                    }
                }
            }
        }

        return model;
    }
}
