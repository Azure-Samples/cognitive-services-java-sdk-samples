/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.bingsearch.samples;

import com.microsoft.azure.cognitiveservices.customsearch.*;
import com.microsoft.azure.cognitiveservices.customsearch.implementation.CustomSearchAPIImpl;
import com.microsoft.azure.cognitiveservices.customsearch.implementation.SearchResponseInner;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Azure bing custom search samples
 */
public final class CustomSearchSamples {
    /**
     * Makes an instance of the CustomSearchAPIImpl.
     * @param subscriptionKey cognitive services bing subscription key
     * @return CustomSearchAPIImpl instance
     */
    public static CustomSearchAPIImpl getClient(final String subscriptionKey) {
        return new CustomSearchAPIImpl("https://api.cognitive.microsoft.com/bing/v7.0/",
                new ServiceClientCredentials() {
                    @Override
                    public void applyCredentialsFilter(OkHttpClient.Builder builder) {
                        builder.addNetworkInterceptor(
                                new Interceptor() {
                                    @Override
                                    public Response intercept(Interceptor.Chain chain) throws IOException {
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
     * Main function which runs the actual sample.
     * @param subscriptionKey cognitive services subscription key
     * @param customConfig value used for the custom config
     */
    public static void customSearchWebPageResultLookup(String subscriptionKey, int customConfig)
    {
        try
        {
            CustomSearchAPIImpl client = getClient(subscriptionKey);
            SearchResponseInner webData = client.customInstances().search("Xbox"
                    , null, null, null, null, customConfig, null, null, null, null, null, null, null, null);
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
        }
        catch (Exception ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }
}