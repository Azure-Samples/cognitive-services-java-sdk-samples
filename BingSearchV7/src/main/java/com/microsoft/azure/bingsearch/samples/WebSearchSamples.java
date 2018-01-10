/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.bingsearch.samples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microsoft.azure.cognitiveservices.websearch.*;
import com.microsoft.azure.cognitiveservices.websearch.implementation.SearchResponseInner;
import com.microsoft.azure.cognitiveservices.websearch.implementation.WebSearchAPIImpl;
import com.microsoft.azure.management.cdn.ErrorResponseException;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Azure bing web search samples.
 */
@JsonIgnoreProperties
public final class WebSearchSamples {
    /**
     * Makes an instance of the WebSearchAPIImpl.
     * @param subscriptionKey cognitive services bing subscription key
     * @return WebSearchAPIImpl instance
     */
    public static WebSearchAPIImpl getClient(final String subscriptionKey) {
        return new WebSearchAPIImpl("https://api.cognitive.microsoft.com/bing/v7.0/",
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
     * This will look up a single query (Xbox) and print out name and url for first web, image, news and videos results
     * @param subscriptionKey cognitive services subscription key
     */
    public static void WebSearchResultTypesLookup(String subscriptionKey)
    {
        WebSearchAPIImpl client = getClient(subscriptionKey);

        try
        {
            SearchResponseInner webData = client.webs().search("Xbox");
            System.out.println("Searched for Query# \" Xbox \"");

            //WebPages
            if (webData.webPages().value().size() > 0)
            {
                // find the first web page
                WebPage firstWebPagesResult = webData.webPages().value().get(0);

                if (firstWebPagesResult != null)
                {
                    System.out.println(String.format("Webpage Results#%d", webData.webPages().value().size()));
                    System.out.println(String.format("First web page name: %s ", firstWebPagesResult.name()));
                    System.out.println(String.format("First web page URL: %s ", firstWebPagesResult.url()));
                }
                else
                {
                    System.out.println("Couldn't find web results!");
                }
            }
            else
            {
                System.out.println("Didn't see any Web data..");
            }

            //Images
            if (webData.images().value().size() > 0)
            {
                // find the first image result
                ImageObject firstImageResult = webData.images().value().get(0);

                if (firstImageResult != null)
                {
                    System.out.println(String.format("Image Results#%d", webData.images().value().size()));
                    System.out.println(String.format("First Image result name: %s ", firstImageResult.name()));
                    System.out.println(String.format("First Image result URL: %s ", firstImageResult.contentUrl()));
                }
                else
                {
                    System.out.println("Couldn't find first image results!");
                }
            }
            else
            {
                System.out.println("Didn't see any image data..");
            }

            //News
            if (webData.news().value().size() > 0)
            {
                // find the first news result
                NewsArticle firstNewsResult = webData.news().value().get(0);

                if (firstNewsResult != null)
                {
                    System.out.println(String.format("News Results#%d", webData.news().value().size()));
                    System.out.println(String.format("First news result name: %s ", firstNewsResult.name()));
                    System.out.println(String.format("First news result URL: %s ", firstNewsResult.url()));
                }
                else
                {
                    System.out.println("Couldn't find any News results!");
                }
            }
            else
            {
                System.out.println("Didn't see first news data..");
            }

            //Videos
            if (webData.videos().value().size() > 0)
            {
                // find the first video result
                VideoObject firstVideoResult = webData.videos().value().get(0);

                if (firstVideoResult != null)
                {
                    System.out.println(String.format("Video Results#%s", webData.videos().value().size()));
                    System.out.println(String.format("First Video result name: %s ", firstVideoResult.name()));
                    System.out.println(String.format("First Video result URL: %s ", firstVideoResult.contentUrl()));
                }
                else
                {
                    System.out.println("Couldn't find first video results!");
                }
            }
            else
            {
                System.out.println("Didn't see any video data..");
            }
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }

    }

    /**
     * This will search (Best restaurants in Seattle), verify number of results and print out name and url of
     * first result
     * @param subscriptionKey cognitive services subscription key
     */
    public static void WebResultsWithCountAndOffset(String subscriptionKey)
    {
        WebSearchAPIImpl client = getClient(subscriptionKey);

        try
        {
            SearchResponseInner webData = client.webs().search(
                    "Best restaurants in Seattle", null, null, null, null, null, 10, null, 20, null, "en-us", null,
                            null, null, SafeSearch.STRICT, null, null, null);
            System.out.println("Searched for Query# \" Best restaurants in Seattle \"");

            if (webData.webPages().value().size() > 0)
            {
                // find the first web page
                WebPage firstWebPagesResult = webData.webPages().value().get(0);

                if (firstWebPagesResult != null)
                {
                    System.out.println(String.format("Web Results#%d", webData.webPages().value().size()));
                    System.out.println(String.format("First web page name: %s ", firstWebPagesResult.name()));
                    System.out.println(String.format("First web page URL: %s ", firstWebPagesResult.url()));
                }
                else
                {
                    System.out.println("Couldn't find first web result!");
                }
            }
            else
            {
                System.out.println("Didn't see any Web data..");
            }
        }
        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }

    /**
     * This will search (Microsoft) with response filters to news and print details of news
     * @param subscriptionKey cognitive services subscription key
     */
    public static void WebSearchWithResponseFilter(String subscriptionKey)
    {
        WebSearchAPIImpl client = getClient(subscriptionKey);

        try
        {
            List<AnswerType> responseFilterstrings = new ArrayList<AnswerType>();
            responseFilterstrings.add(AnswerType.NEWS);
            SearchResponseInner webData = client.webs().search(
            "Best restaurants in Seattle", null, null, null, null, null, 10, null, 20, null, "en-us", null,
                    null, responseFilterstrings, SafeSearch.STRICT, null, null, null);

            System.out.println("Searched for Query# \" Microsoft \" with response filters \"news\"");

            //News
            if (webData.news() != null && webData.news().value().size() > 0)
            {
                // find the first news result
                NewsArticle firstNewsResult = webData.news().value().get(0);

                if (firstNewsResult != null)
                {
                    System.out.println(String.format("News Results#%d", webData.news().value().size()));
                    System.out.println(String.format("First news result name: %s ", firstNewsResult.name()));
                    System.out.println(String.format("First news result URL: %s ", firstNewsResult.url()));
                }
                else
                {
                    System.out.println("Couldn't find first News results!");
                }
            }
            else
            {
                System.out.println("Didn't see any News data..");
            }

        }
        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }

    /**
     * This will search (Lady Gaga) with answerCount and promote parameters and print details of answers
     * @param subscriptionKey cognitive services subscription key
     */
    public static void WebSearchWithAnswerCountPromoteAndSafeSearch(String subscriptionKey)
    {
        WebSearchAPIImpl client = getClient(subscriptionKey);

        try
        {
            List<AnswerType> promoteAnswertypeStrings = new ArrayList<AnswerType>();
            promoteAnswertypeStrings.add(AnswerType.VIDEOS);
            SearchResponseInner webData = client.webs().search(
                "Lady Gaga", null, null, null, null, null, 10, null, 20, null, "en-us", null,
                promoteAnswertypeStrings, null, SafeSearch.STRICT, null, null, null);
            System.out.println("Searched for Query# \" Lady Gaga \"");

            if (webData.videos().value().size() > 0)
            {
                VideoObject firstVideosResult = webData.videos().value().get(0);

                if (firstVideosResult != null)
                {
                    System.out.println(String.format("Video Results#%d", webData.videos().value().size()));
                    System.out.println(String.format("First Video result name: %s ", firstVideosResult.name()));
                    System.out.println(String.format("First Video result URL: %s ", firstVideosResult.contentUrl()));
                }
                else
                {
                    System.out.println("Couldn't find videos results!");
                }
            }
            else
            {
                System.out.println("Didn't see any data..");
            }
        }
        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }
}