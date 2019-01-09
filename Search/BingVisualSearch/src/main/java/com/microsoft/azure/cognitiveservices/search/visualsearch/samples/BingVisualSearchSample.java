/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.visualsearch.samples;

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

/**
 * Sample code for searching news using Bing Video Search, an Azure Cognitive Service.
 *  - Search using an image binary and print out the image insights token, the number of tags, the number of actions, and the first action type.
 *  - Search
 *  - Search
 *  - Search
 *  - Search
 */
public class BingVisualSearchSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing Video Search API client
     * @return true if sample runs successfully
     */
    public static boolean runSample(BingVisualSearchAPI client) {
        try {
            byte[] imageBytes = ByteStreams.toByteArray(BingVisualSearchSample.class.getResourceAsStream("/image.jpg"));

            Gson gson = new Gson();
            VisualSearchRequest visualSearchRequest;
            ImageKnowledge visualSearchResults;

            //=============================================================
            // This will send an image binary and print out the image insights token, the number of tags, the number
            // of actions, and the first action type

            try {
                System.out.println("Search with a binary of dog image");
                visualSearchResults = client.bingImages().visualSearch()
                        .withImage(imageBytes)
                        .execute();

                PrintVisualSearchResults(visualSearchResults);


                //=============================================================
                // This will send an image binary specifying the crop area and print out the image insights token, the
                // number of tags, the number of actions, and the first action type

                System.out.println("Search with a binary of dog image using crop area");
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
//                .withKnowledgeRequest("{\"imageInfo\":{\"cropArea\":{\"top\":\"0.1\",\"bottom\":\"0.5\",\"left\":\"0.1\",\"right\":\"0.9\"}}}")
                            .execute();

                    PrintVisualSearchResults(visualSearchResults);

                    //=============================================================
                    // This will send an image url in the knowledgeReques along with a "site:www.bing.com" filter, and print out
                    // the image insights token, the number of tags, the number of actions, and the first action type

                    System.out.println("Search with an url of dog image");
                    String imageUrl = "https://images.unsplash.com/photo-1512546148165-e50d714a565a?w=600&q=80";


                    imageInfo = new ImageInfo().withUrl(imageUrl);
                    Filters filters = new Filters().withSite("www.bing.com");
                    KnowledgeRequest knowledgeRequest = new KnowledgeRequest().withFilters(filters);
                    visualSearchRequest = new VisualSearchRequest()
                            .withImageInfo(imageInfo)
                            .withKnowledgeRequest(knowledgeRequest);
                    System.out.println(gson.toJson(visualSearchRequest));
                    visualSearchResults = client.bingImages().visualSearch()
                            .withKnowledgeRequest(gson.toJson(visualSearchRequest))
                            .execute();

                    PrintVisualSearchResults(visualSearchResults);

                    //=============================================================
                    // This will send an image insights token specifying the crop area and print out the image insights token,
                    // the number of tags, the number of actions, and the first action type


                    System.out.println("Search with an image insights token using crop area");
                    String imageInsightsToken = "bcid_113F29C079F18F385732D8046EC80145*ccid_oV/QcH95*mid_687689FAFA449B35BC11A1AE6CEAB6F9A9B53708*thid_R.113F29C079F18F385732D8046EC80145";

                    cropArea = new CropArea()
                            .withTop(0.1)
                            .withBottom(0.5)
                            .withLeft(0.1)
                            .withRight(0.9);
                    imageInfo = new ImageInfo()
                            .withImageInsightsToken(imageInsightsToken)
                            .withCropArea(cropArea);
                    visualSearchRequest = new VisualSearchRequest().withImageInfo(imageInfo);
                    System.out.println(gson.toJson(visualSearchRequest));
                    visualSearchResults = client.bingImages().visualSearch()
                            .withKnowledgeRequest(gson.toJson(visualSearchRequest))
                            .execute();

                    PrintVisualSearchResults(visualSearchResults);

                    //=============================================================
                    // This will send an image url specifying the crop area and print out the image insights token,
                    // the number of tags, the number of actions, and the first action type


                    System.out.println("Search with an url of dog image using crop area");
                    String visualSearchRequestJSON = "{\"imageInfo\":{\"url\":\"https://images.unsplash.com/photo-1512546148165-e50d714a565a?w=600&q=80\",\"cropArea\":{\"top\":0.1,\"bottom\":0.5,\"left\":0.1,\"right\":0.9}},\"knowledgeRequest\":{\"filters\":{\"site\":\"www.bing.com\"}}}";
                    System.out.println(visualSearchRequestJSON);
                    visualSearchResults = client.bingImages().visualSearch()
                            .withKnowledgeRequest(visualSearchRequestJSON)
                            .execute();

                    PrintVisualSearchResults(visualSearchResults);

                    return true;
                }  catch (ErrorResponseException e) {
                    System.out.println(
                            String.format("Exception occurred, status code %s with reason %s.", e.response().code(), e.response().message()));
                    if (e.response().code() == 401) {
                        System.out.println("Make sure that you are using the S9 pricing tier for the Bing Search v7 API for visual search.");
                    } else {
                        throw e;
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception f) {
                System.out.println(f.getMessage());
                f.printStackTrace();
            }
        return false;
    }

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

    /**
     * Main entry point.
     *
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate

            // If you are going to set the AZURE_BING_SAMPLES_API_KEY environment variable, make sure you set it for your OS, then reopen your command prompt or IDE.
            // If not, you may get an API key not found exception.
            // IMPORTANT: MAKE SURE TO USE S9 PRICING TIER OF THE BING SEARCH V7 API KEY FOR VISUAL SEARCH. Otherwise, you will get an invalid subscription key error.
            // IMPORTANT: if you have not set the `AZURE_BING_SAMPLES_API_KEY` environment variable to your cognitive services API key:
            // 1. comment out the below line
            final String subscriptionKey = System.getenv("AZURE_BING_SAMPLES_API_KEY");
            // 2. paste your cognitive services API key below, and uncomment the line
            //final String subscriptionKey = "enter your key here";

            BingVisualSearchAPI client = BingVisualSearchManager.authenticate(subscriptionKey);

            runSample(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
