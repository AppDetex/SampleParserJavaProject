package com.appdetex.sampleparserjavaproject.parsing;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ParsedInfo {
    private String title;
    private String description;
    private String publisher;
    private String price;
    private BigDecimal rating;
}
