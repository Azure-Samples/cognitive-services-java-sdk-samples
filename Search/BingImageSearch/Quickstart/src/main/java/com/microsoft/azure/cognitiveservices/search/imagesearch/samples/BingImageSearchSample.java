/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.imagesearch.samples;

import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchAPI;
import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchManager;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageObject;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImagesModel;

/**
 * Sample code for searching images using Bing Image Search, an Azure Cognitive Service.
 *  - Searches images for "canadian rockies" then outputs the result.
 */
public class BingImageSearchSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing News Search API client
     * @param searchTerm a term to use in the image search request
     * @return true if sample runs successfully
     */
    public static void runSample(BingImageSearchAPI client, String searchTerm) {
        try {

            //=============================================================
            // This will search images for "canadian rockies" then print the first image result,

            System.out.println(String.format("Search images for query %s", searchTerm));

            ImagesModel imageResults = client.bingImages().search()
                .withQuery(searchTerm)
                .withMarket("en-us")
                .execute();

            if (imageResults != null && imageResults.value().size() > 0) {
                // Image results
                ImageObject firstImageResult = imageResults.value().get(0);

                System.out.println(String.format("Image result count: %d", imageResults.value().size()));
                System.out.println(String.format("First image insights token: %s", firstImageResult.imageInsightsToken()));
                System.out.println(String.format("First image thumbnail url: %s", firstImageResult.thumbnailUrl()));
                System.out.println(String.format("First image content url: %s", firstImageResult.contentUrl()));
            }
            else {
                    System.out.println("Couldn't find any image results!");
                }
            }
        catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
    }

    /**
     * Main entry point.
     *
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            // Authenticate
            // Set the BING_SEARCH_V7_SUBSCRIPTION_KEY environment variable, 
            // then reopen your command prompt or IDE for changes to take effect.
            final String subscriptionKey = System.getenv("BING_SEARCH_V7_SUBSCRIPTION_KEY");
            
            BingImageSearchAPI client = BingImageSearchManager.authenticate(subscriptionKey);
            String searchTerm = "canadian rockies";
            runSample(client, searchTerm);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
