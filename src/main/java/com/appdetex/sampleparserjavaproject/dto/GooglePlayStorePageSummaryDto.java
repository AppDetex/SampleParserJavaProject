package com.appdetex.sampleparserjavaproject.dto;

import com.appdetex.sampleparserjavaproject.DataFieldConsts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Class that contains the required data of:
 * - app title,
 * - first paragraph of the description,
 * - publisher name,
 * - price,
 * - and rating (average review score).
 */

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class GooglePlayStorePageSummaryDto {
    public String title;
    public String firstParagraph;
    public String publisherName;
    public String price;
    public float rating;

    /**
     * Method to map data into this object.  Look into using MapStruct or similar in the future.
     */
    public static GooglePlayStorePageSummaryDto mapData(final Map<String, String> dataMap) {
        String title = dataMap.get(DataFieldConsts.APP_TITLE.name());
        String firstParagraph = dataMap.get(DataFieldConsts.FIRST_PARAGRAPH.name());
        String publisherName = dataMap.get(DataFieldConsts.PUBLISHER.name());
        String price = dataMap.get(DataFieldConsts.PRICE.name());
        String ratingStr = dataMap.get(DataFieldConsts.RATING.name());
        float rating = Float.valueOf(ratingStr); //Need more info about failure mode here.  Bailout or make assumption?
        return new GooglePlayStorePageSummaryDto(title, firstParagraph, publisherName, price, rating);
    }
}
