package com.appdetex.candyscrape.scraper.model;

import com.appdetex.candyscrape.meta.CssSelectorPath;
import lombok.Data;


@Data
public class TestModel {

    @CssSelectorPath(value="meta[charset]", attribute="charset")
    private String charset;

    @CssSelectorPath("#inner-text")
    private String innerText;

    @CssSelectorPath(value=".radio-test:has(on)", attribute="data-value")
    private String on;

    @CssSelectorPath(value=".radio-test:not(:has(on))", attribute="data-value")
    private String off;

    @CssSelectorPath("div#extended-tests numeric")
    private Float numeric;

    @CssSelectorPath(
            value="div#extended-tests regex",
            regex="^(.*?)-"
    )
    private String regexSimple;

    @CssSelectorPath(
            value="div#extended-tests regex",
            regex="(.*?)-(?<other>.*?)-(?<named>.*)",
            regexGroupName="named"
    )
    private String regexNamed;

    @CssSelectorPath(value="#bogus", defaultValue="defaulted")
    private String defaulted;
}
