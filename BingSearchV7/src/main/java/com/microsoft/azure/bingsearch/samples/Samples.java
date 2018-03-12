/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.bingsearch.samples;

public class Samples {
    /**
     * The key for the bing api.
     */
    public static String bingAPIKey = null;

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            if(bingAPIKey == null) {
                bingAPIKey = System.getenv("AZURE_BING_SAMPLES_API_KEY");
                if(bingAPIKey == null) {
                    throw new Exception("Azure bing samples api key not found.");
                }
            }

            //custom examples
            CustomSearchSamples.customSearchWebPageResultLookup(bingAPIKey, 0);

            //entity examples
            EntitySearchSamples.dominantEntityLookup(bingAPIKey);
            EntitySearchSamples.handlingDisambiguation(bingAPIKey);
            EntitySearchSamples.storeLookup(bingAPIKey);
            EntitySearchSamples.multipleRestaurantLookup(bingAPIKey);
            EntitySearchSamples.error(bingAPIKey);

            //image examples
            ImageCheckSamples.imageDetail(bingAPIKey);
            ImageCheckSamples.imageSearch(bingAPIKey);
            ImageCheckSamples.imageSearchWithFilters(bingAPIKey);
            ImageCheckSamples.imageTrending(bingAPIKey);

            //news search examples
            NewsSearchSamples.newsSearch(bingAPIKey);
            NewsSearchSamples.newsSearchWithFilters(bingAPIKey);
            NewsSearchSamples.newsCategory(bingAPIKey);
            NewsSearchSamples.trendingTopics(bingAPIKey);

            //spell check samples
            SpellCheckSamples.spellCheckCorrection(bingAPIKey);
            SpellCheckSamples.spellCheckError(bingAPIKey);

            //video search samples
            VideoSearchSamples.VideoDetail(bingAPIKey);
            VideoSearchSamples.VideoSearch(bingAPIKey);
            VideoSearchSamples.VideoSearchWithFilters(bingAPIKey);
            VideoSearchSamples.VideoTrending(bingAPIKey);

            //web search sampels
            WebSearchSamples.WebResultsWithCountAndOffset(bingAPIKey);
            WebSearchSamples.WebSearchResultTypesLookup(bingAPIKey);
            WebSearchSamples.WebSearchWithAnswerCountPromoteAndSafeSearch(bingAPIKey);
            WebSearchSamples.WebSearchWithResponseFilter(bingAPIKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
