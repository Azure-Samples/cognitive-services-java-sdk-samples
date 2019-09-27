/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

//package com.microsoft.azure.cognitiveservices.search.visualsearch.samples;
// <imports>
package main.java;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.microsoft.azure.cognitiveservices.search.visualsearch.BingVisualSearchAPI;
import com.microsoft.azure.cognitiveservices.search.visualsearch.BingVisualSearchManager;
import com.microsoft.azure.cognitiveservices.search.visualsearch.models.CropArea;
import com.microsoft.azure.cognitiveservices.search.visualsearch.models.ErrorResponseException;
import com.microsoft.azure.cognitiveservices.search.visualsearch.models.Filters;
import com.microsoft.azure.cognitiveservices.search.visualsearch.models.ImageInfo;
import com.microsoft.azure.cognitiveservices.search.visualsearch.models.ImageKnowledge;
import com.microsoft.azure.cognitiveservices.search.visualsearch.models.ImageTag;
import com.microsoft.azure.cognitiveservices.search.visualsearch.models.KnowledgeRequest;
import com.microsoft.azure.cognitiveservices.search.visualsearch.models.VisualSearchRequest;
// </imports>
import java.awt.*;

/**
 * Sample code for searching news using Bing Video Search, an Azure Cognitive Service.
 *  - Search using an image binary and print out the image insights token, the number of tags, the number of actions, and the first action type.
 *  - Search
 *  - Search
 *  - Search
 *  - Search
 */



public class BingVisualSearchSample {

    // Send an image binary and print out the image insights token, the number of tags, the number
    // of actions, and the first action type
    // <visualSearch>
    public static void visualSearch(BingVisualSearchAPI client, byte[] imageBytes){
        System.out.println("Calling Bing Visual Search with image binary");
        ImageKnowledge visualSearchResults = client.bingImages().visualSearch()
                .withImage(imageBytes)
                .execute();
        PrintVisualSearchResults(visualSearchResults);

    }
    // </visualSearch>

    // This will send an image binary specifying the crop area and print out the image insights token, the
    // number of tags, the number of actions, and the first action type
    // <visualSearchWithCropArea>
    public static void searchWithCropArea(BingVisualSearchAPI client, byte[] imageBytes){
        System.out.println("Calling Bing Visual Search with image binary, using crop area");
        VisualSearchRequest visualSearchRequest;
        ImageKnowledge visualSearchResults;
        Gson gson = new Gson();

        CropArea cropArea = new CropArea()
                .withTop(0.1)
                .withBottom(0.5)
                .withLeft(0.1)
                .withRight(0.9);
        ImageInfo imageInfo = new ImageInfo().withCropArea(cropArea);
        visualSearchRequest = new VisualSearchRequest().withImageInfo(imageInfo);
        System.out.println(gson.toJson(visualSearchRequest));
        visualSearchResults = client.bingImages().visualSearch()
                .withImage(imageBytes)
                .withKnowledgeRequest(gson.toJson(visualSearchRequest))
                .execute();

        PrintVisualSearchResults(visualSearchResults);
    }
    // </visualSearchWithCropArea>

    //=============================================================
    // This will send an image url in the knowledgeReques along with a "site:www.bing.com" filter, and print out
    // the image insights token, the number of tags, the number of actions, and the first action type
    // <searchWithFilter>
    public static void searchWithFilter(BingVisualSearchAPI client){
        System.out.println("Calling Bing Visual Search with image binary and insights token");
        Gson gson = new Gson();
        String imageUrl = "https:///photo-1512546148165-e50d714a565a?w=600&q=80";


        ImageInfo imageInfo = new ImageInfo().withUrl(imageUrl);
        Filters filters = new Filters().withSite("www.bing.com");
        KnowledgeRequest knowledgeRequest = new KnowledgeRequest().withFilters(filters);
        VisualSearchRequest visualSearchRequest = new VisualSearchRequest()
                .withImageInfo(imageInfo)
                .withKnowledgeRequest(knowledgeRequest);
        System.out.println(gson.toJson(visualSearchRequest));
        ImageKnowledge visualSearchResults = client.bingImages().visualSearch()
                .withKnowledgeRequest(gson.toJson(visualSearchRequest))
                .execute();

        PrintVisualSearchResults(visualSearchResults);
    }
    // </searchWithFilter>

    // <searchUsingInsightToken>
    public static void searchUsingInsightToken(BingVisualSearchAPI client){
        //=============================================================
        // This will send an image insights token specifying the crop area and print out the image insights token,
        // the number of tags, the number of actions, and the first action type


        System.out.println("Search with an image insights token using crop area");
        String imageInsightsToken = "bcid_113F29C079F18F385732D8046EC80145*ccid_oV/QcH95*mid_687689FAFA449B35BC11A1AE6CEAB6F9A9B53708*thid_R.113F29C079F18F385732D8046EC80145";
        Gson gson = new Gson();

        CropArea cropArea = new CropArea()
                .withTop(0.1)
                .withBottom(0.5)
                .withLeft(0.1)
                .withRight(0.9);
        ImageInfo imageInfo = new ImageInfo()
                .withImageInsightsToken(imageInsightsToken)
                .withCropArea(cropArea);
        VisualSearchRequest visualSearchRequest = new VisualSearchRequest().withImageInfo(imageInfo);
        System.out.println(gson.toJson(visualSearchRequest));
        ImageKnowledge visualSearchResults = client.bingImages().visualSearch()
                .withKnowledgeRequest(gson.toJson(visualSearchRequest))
                .execute();

        PrintVisualSearchResults(visualSearchResults);
    }
    // </searchUsingInsightToken>


    public static void searchUsingCropArea(BingVisualSearchAPI client){
        System.out.println("Search with an url of dog image using crop area");
        String visualSearchRequestJSON = "{\"imageInfo\":{\"url\":\"https://images.unsplash.com/photo-1512546148165-e50d714a565a?w=600&q=80\",\"cropArea\":{\"top\":0.1,\"bottom\":0.5,\"left\":0.1,\"right\":0.9}},\"knowledgeRequest\":{\"filters\":{\"site\":\"www.bing.com\"}}}";
        System.out.println(visualSearchRequestJSON);
        ImageKnowledge visualSearchResults = client.bingImages().visualSearch()
                .withKnowledgeRequest(visualSearchRequestJSON)
                .execute();

        PrintVisualSearchResults(visualSearchResults);
    }

    // <printVisualSearchResults>
    static void PrintVisualSearchResults(ImageKnowledge visualSearchResults) {
        if (visualSearchResults == null) {
            System.out.println("No visual search result data.");
        } else {
            // Print token

            if (visualSearchResults.image() != null && visualSearchResults.image().imageInsightsToken() != null) {
                System.out.println("Found uploaded image insights token: " + visualSearchResults.image().imageInsightsToken());
            } else {
                System.out.println("Couldn't find image insights token!");
            }

            // List tags

            if (visualSearchResults.tags() != null && visualSearchResults.tags().size() > 0) {
                System.out.format("Found visual search tag count: %d\n", visualSearchResults.tags().size());
                ImageTag firstTagResult = visualSearchResults.tags().get(0);

                // List of actions in first tag

                if (firstTagResult.actions() != null && firstTagResult.actions().size() > 0) {
                    System.out.format("Found first tag action count: %d\n", firstTagResult.actions().size());
                    System.out.println("Found first tag action type: " + firstTagResult.actions().get(0).actionType());
                }
            } else {
                System.out.println("Couldn't find image tags!");
            }
        }
    }
    // </printVisualSearchResults>

    /**
     * Main entry point.
     *
     * @param args the parameters
     * 
     * 
     */
    // <main>
    // IMPORTANT: MAKE SURE TO USE S9 PRICING TIER OF THE BING SEARCH V7 API KEY FOR VISUAL SEARCH.
    // Otherwise, you will get an invalid subscription key error.
    public static void main(String[] args) {

        // Set the BING_SEARCH_V7_SUBSCRIPTION_KEY environment variable with your subscription key,
        // then reopen your command prompt or IDE. If not, you may get an API key not found exception.
        final String subscriptionKey = System.getenv("BING_SEARCH_V7_SUBSCRIPTION_KEY");

        BingVisualSearchAPI client = BingVisualSearchManager.authenticate(subscriptionKey);

        //runSample(client);
        byte[] imageBytes;

        try {
            imageBytes = ByteStreams.toByteArray(ClassLoader.getSystemClassLoader().getResourceAsStream("image.jpg"));
            visualSearch(client, imageBytes);
            searchWithCropArea(client, imageBytes);
            // wait 1 second to avoid rate limiting
            Thread.sleep(1000);
            searchWithFilter(client);
            searchUsingCropArea(client);
            searchUsingInsightToken(client);
        }
        catch (java.io.IOException f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        catch (java.lang.InterruptedException f){
            f.printStackTrace();
        }

    }
    // </main>
}
