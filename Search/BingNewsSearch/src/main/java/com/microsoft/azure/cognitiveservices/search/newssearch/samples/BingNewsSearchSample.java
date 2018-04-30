/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.newssearch.samples;

import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchAPI;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchManager;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsArticle;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsModel;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.SearchOptionalParameter;

/**
 * Sample code for searching news using Bing News Search, an Azure Cognitive Service.
 *  - Search the news for "Quantum  Computing" and print out the results.
 */
public class BingNewsSearchSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing News Search API client
     * @return true if sample runs successfully
     */
    public static boolean runSample(BingNewsSearchAPI client) {
        try {

            //=============================================================
            // This will search news for (Quantum  Computing) with market and count parameters then verify number of
            // results and print out total estimated matches, name, url, description, published time and name of provider
            // of the first item in the list of news result list.

            NewsModel newsResults = client.bingNews().search("Quantum  Computing",
                    new SearchOptionalParameter()
                        .withMarket("en-us")
                        .withCount(100));
            System.out.println("Search news for query \"Quantum  Computing\" with market and count");

            if (newsResults != null)
            {
                if (newsResults.value().size() > 0)
                {
                    NewsArticle firstNewsResult = newsResults.value().get(0);

                    System.out.println(String.format("TotalEstimatedMatches value: %d", newsResults.totalEstimatedMatches()));
                    System.out.println(String.format("News result count: %d", newsResults.value().size()));
                    System.out.println(String.format("First news name: %s", firstNewsResult.name()));
                    System.out.println(String.format("First news url: %s", firstNewsResult.url()));
                    System.out.println(String.format("First news description: %s", firstNewsResult.description()));
                    System.out.println(String.format("First news published time: %s", firstNewsResult.datePublished()));
                    System.out.println(String.format("First news provider: %s", firstNewsResult.provider().get(0).name()));
                }
                else
                {
                    System.out.println("Couldn't find news results!");
                }
            } else {
                System.out.println("Didn't see any news result data..");
            }

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        return false;
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

            final String subscriptionKey = System.getenv("AZURE_BING_SAMPLES_API_KEY");

            BingNewsSearchAPI bingNewsSearchAPIClient = BingNewsSearchManager.authenticate(subscriptionKey);


            runSample(bingNewsSearchAPIClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
