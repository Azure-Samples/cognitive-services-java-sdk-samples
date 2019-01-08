/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.newssearch.samples;

import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchAPI;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchManager;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.Freshness;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsArticle;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsModel;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsTopic;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.SafeSearch;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.TrendingTopics;

/**
 * Sample code for searching news using Bing News Search, an Azure Cognitive Service.
 *  - Search the news for "Quantum  Computing" with market and count settings and print out the results.
 *  - Search the news for "Artificial Intelligence" with market, freshness and sort-by settings and print out the results.
 *  - Search the news category "Movie and TV Entertainment" with market and safe search settings and print out the results.
 *  - Search the news trending topics with market and print out the results.
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

            System.out.println("Search news for query \"Quantum  Computing\" with market and count");
            NewsModel newsResults = client.bingNews().search()
                .withQuery("Quantum  Computing")
                .withMarket("en-us")
                .withCount(100)
                .execute();

            PrintNewsResult(newsResults);


            //=============================================================
            // This will search most recent news for (Artificial Intelligence) with freshness and sort-by parameters then
            //  verify number of results and print out totalEstimatedMatches, name, url, description, published time and
            //  name of provider of the first news result

            System.out.println("Search most recent news for query \"Artificial Intelligence\" with freshness and sortBy");
            newsResults = client.bingNews().search()
                .withQuery("Artificial Intelligence")
                .withMarket("en-us")
                .withFreshness(Freshness.WEEK)
                .withSortBy("Date")
                .execute();

            PrintNewsResult(newsResults);


            //=============================================================
            // This will search category news for movie and TV entertainment with safe search then verify number of results
            //  and print out category, name, url, description, published time and name of provider of the first news result

            System.out.println("Search category news for movie and TV entertainment with safe search");
            newsResults = client.bingNews().category()
                .withMarket("en-us")
                .withCategory("Entertainment_MovieAndTV")
                .withSafeSearch(SafeSearch.STRICT)
                .execute();

            PrintNewsResult(newsResults);


            //=============================================================
            // This will search news trending topics in Bing then verify number of results and print out name, text of query,
            //  webSearchUrl, newsSearchUrl and image Url of the first news result

            System.out.println("Search news trending topics in Bing");
            TrendingTopics trendingTopics = client.bingNews().trending()
                .withMarket("en-us")
                .execute();

            if (trendingTopics != null) {
                if (trendingTopics.value().size() > 0) {
                    NewsTopic firstTopic = trendingTopics.value().get(0);

                    System.out.println(String.format("Trending topics count: %s", trendingTopics.value().size()));
                    System.out.println(String.format("First topic name: %s", firstTopic.name()));
                    System.out.println(String.format("First topic query: %s", firstTopic.query().text()));
                    System.out.println(String.format("First topic image url: %s", firstTopic.image().url()));
                    System.out.println(String.format("First topic webSearchUrl: %s", firstTopic.webSearchUrl()));
                    System.out.println(String.format("First topic newsSearchUrl: %s", firstTopic.newsSearchUrl()));
                } else {
                    System.out.println("Couldn't find news trending topics!");
                }
            } else {
                System.out.println("Didn't see any news trending topics..");
            }

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        return false;
    }

    /**
     * Prints the first item in the list of news result list.
     *
     * @param newsResults the news result
     */
    public static void PrintNewsResult(NewsModel newsResults) {
        if (newsResults != null) {
            if (newsResults.value().size() > 0) {
                NewsArticle firstNewsResult = newsResults.value().get(0);

                System.out.println(String.format("TotalEstimatedMatches value: %d", newsResults.totalEstimatedMatches()));
                System.out.println(String.format("News result count: %d", newsResults.value().size()));
                System.out.println(String.format("First news name: %s", firstNewsResult.name()));
                System.out.println(String.format("First news url: %s", firstNewsResult.url()));
                System.out.println(String.format("First news description: %s", firstNewsResult.description()));
                System.out.println(String.format("First news published time: %s", firstNewsResult.datePublished()));
                System.out.println(String.format("First news provider: %s", firstNewsResult.provider().get(0).name()));
            } else {
                System.out.println("Couldn't find news results!");
            }
        } else {
            System.out.println("Didn't see any news result data..");
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
            // IMPORTANT: if you have not set the `AZURE_BING_SAMPLES_API_KEY` environment variable to your cognitive services API key:
            // 1. comment out the below line
            final String subscriptionKey = System.getenv("AZURE_BING_SAMPLES_API_KEY");
            // 2. paste your cognitive services API key below, and uncomment the line
            //final String subscriptionKey = "enter your key here";

            BingNewsSearchAPI bingNewsSearchAPIClient = BingNewsSearchManager.authenticate(subscriptionKey);


            runSample(bingNewsSearchAPIClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
