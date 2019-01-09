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

            // If you are going to set the AZURE_BING_SAMPLES_API_KEY environment variable, make sure you set it for your OS, then reopen your command prompt or IDE.
            // If not, you may get an API key not found exception.
            // IMPORTANT: if you have not set the `AZURE_BING_SAMPLES_API_KEY` environment variable to your cognitive services API key:
            // 1. comment out the below line
            final String subscriptionKey = System.getenv("AZURE_BING_SAMPLES_API_KEY");
            // 2. paste your cognitive services API key below, and uncomment the line
            //final String subscriptionKey = "enter your key here";
            
            BingImageSearchAPI client = BingImageSearchManager.authenticate(subscriptionKey);
            String searchTerm = "canadian rockies";
            runSample(client, searchTerm);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
