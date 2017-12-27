/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.bingsearch.samples;

import com.microsoft.azure.cognitiveservices.newssearch.*;
import com.microsoft.azure.cognitiveservices.newssearch.implementation.NewsInner;
import com.microsoft.azure.cognitiveservices.newssearch.implementation.NewsSearchAPIImpl;
import com.microsoft.azure.cognitiveservices.newssearch.implementation.TrendingTopicsInner;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Azure bing news search samples.
 */
public final class NewsSearchSamples {
    /**
     * Makes an instance of the NewsSearchAPIImpl.
     * @param subscriptionKey cognitive services bing subscription key
     * @return NewsSearchAPIImpl instance
     */
    public static NewsSearchAPIImpl getClient(final String subscriptionKey) {
        return new NewsSearchAPIImpl("https://api.cognitive.microsoft.com/bing/v7.0/",
                new ServiceClientCredentials() {
                    @Override
                    public void applyCredentialsFilter(OkHttpClient.Builder builder) {
                        builder.addNetworkInterceptor(
                                new Interceptor() {
                                    @Override
                                    public Response intercept(Chain chain) throws IOException {
                                        Request request = null;
                                        Request original = chain.request();
                                        // Request customization: add request headers
                                        Request.Builder requestBuilder = original.newBuilder()
                                                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
                                        request = requestBuilder.build();
                                        return chain.proceed(request);
                                    }
                                });
                    }
                });
    }

    /**
     * This will search news for (Quantum  Computing) with market and count parameters then verify number of results
     * and print out totalEstimatedMatches, name, url, description, published time and name of provider of the
     * first news result
     * @param subscriptionKey cognitive services subscription key
     */
    public static void newsSearch(String subscriptionKey)
    {
        NewsSearchAPIImpl client = getClient(subscriptionKey);

        try
        {
            NewsInner newsResults = client.searchs().list("Quantum  Computing", null, null, null,
                    null, null, 100, null, "en-us",
                    null, null, null, null, null,
                    null, null);

            System.out.println("Search news for query \"Quantum  Computing\" with market and count");

            if (newsResults == null)
            {
                System.out.println("Didn't see any news result data..");
            }
            else
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
            }
        }

        catch (Exception ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }

    /**
     * This will search most recent news for (Artificial Intelligence) with freshness and sortBy parameters then
     * verify number of results and print out totalEstimatedMatches, name, url, description, published time and
     * name of provider of the first news result
     * @param subscriptionKey cognitive services subscription key
     */
    public static void newsSearchWithFilters(String subscriptionKey)
    {
        NewsSearchAPIImpl client = getClient(subscriptionKey);

        try
        {
            NewsInner newsResults = client.searchs().list("Artificial Intelligence", null, null, null, null, null,
                        null, Freshness.WEEK, "en-us", null, null, null,
                        null, "Date", null, null);
            System.out.println("Search most recent news for query \"Artificial Intelligence\" with freshness and sortBy");

            if (newsResults == null)
            {
                System.out.println("Didn't see any news result data..");
            }
            else
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
            }
        }

        catch (Exception ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }

    /**
     * This will search category news for movie and TV entertainment with safe search then verify number of results
     * and print out category, name, url, description, published time and name of provider of the first news result
     * @param subscriptionKey cognitive services subscription key
     */
    public static void newsCategory(String subscriptionKey)
    {
        NewsSearchAPIImpl client = getClient(subscriptionKey);

        try
        {
            NewsInner newsResults = client.categorys().list(null, null, null, null, null, "Entertainment_MovieAndTV",
                    null, null, "en-us", null, null, SafeSearch.STRICT,
                    null, null, null);
            System.out.println("Search category news for movie and TV entertainment with safe search");

            if (newsResults == null)
            {
                System.out.println("Didn't see any news result data..");
            }
            else
            {
                if (newsResults.value().size() > 0)
                {
                    NewsArticle firstNewsResult = newsResults.value().get(0);

                    System.out.println(String.format("News result count: %d", newsResults.value().size()));
                    System.out.println(String.format("First news category: %d", firstNewsResult.category()));
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
            }
        }

        catch (Exception ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage()
            );
        }
    }

    /**
     * This will search news trending topics in Bing then verify number of results and print out name, text of query,
     * webSearchUrl, newsSearchUrl and image Url of the first news result
     * @param subscriptionKey cognitive services subscription key
     */
    public static void trendingTopics(String subscriptionKey)
    {
        NewsSearchAPIImpl client = getClient(subscriptionKey);

        try
        {
            TrendingTopicsInner trendingTopics = client.trendings().list(null, null, null, null, null, null,
                    "en-us", null, null, null, null, null, null, null);
            System.out.println("Search news trending topics in Bing");

            if (trendingTopics == null)
            {
                System.out.println("Didn't see any news trending topics..");
            }
            else
            {
                if (trendingTopics.value().size() > 0)
                {
                    NewsTopic firstTopic = trendingTopics.value().get(0);

                    System.out.println(String.format("Trending topics count: %s", trendingTopics.value().size()));
                    System.out.println(String.format("First topic name: %s", firstTopic.name()));
                    System.out.println(String.format("First topic query: %s", firstTopic.query().text()));
                    System.out.println(String.format("First topic image url: %s", firstTopic.image().url()));
                    System.out.println(String.format("First topic webSearchUrl: %s", firstTopic.webSearchUrl()));
                    System.out.println(String.format("First topic newsSearchUrl: %s", firstTopic.newsSearchUrl()));
                }
                else
                {
                    System.out.println("Couldn't find news trending topics!");
                }
            }
        }

        catch (Exception ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }
}