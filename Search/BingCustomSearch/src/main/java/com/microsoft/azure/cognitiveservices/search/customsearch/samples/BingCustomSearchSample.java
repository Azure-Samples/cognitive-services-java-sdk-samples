/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.customsearch.samples;

import com.microsoft.azure.cognitiveservices.search.customsearch.BingCustomSearchAPI;
import com.microsoft.azure.cognitiveservices.search.customsearch.BingCustomSearchManager;
import com.microsoft.azure.cognitiveservices.search.customsearch.models.SearchResponse;
import com.microsoft.azure.cognitiveservices.search.customsearch.models.WebPage;

/**
 * Sample code for custom searching news using Bing Custom Search, an Azure Cognitive Service.
 *  - Custom search for "Xbox" and print out name and url for the first web page in the results list.
 */
public class BingCustomSearchSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing Custom Search API client
     * @return true if sample runs successfully
     */
    public static boolean runSample(BingCustomSearchAPI client, String customConfigId) {
        try {

            //=============================================================
            // This will custom search for "Xbox" and print out name and url for the first web page in the results list

            System.out.println("Searched for Query# \"Xbox\"");
            SearchResponse webData = client.bingCustomInstances().search()
                .withCustomConfig(customConfigId != null ? Long.valueOf(customConfigId) : 0)
                .withQuery("Xbox")
                .withMarket("en-us")
                .execute();

            if (webData != null && webData.webPages() != null && webData.webPages().value().size() > 0)
            {
                // find the first web page
                WebPage firstWebPagesResult = webData.webPages().value().get(0);

                if (firstWebPagesResult != null) {
                    System.out.println(String.format("Webpage Results#%d", webData.webPages().value().size()));
                    System.out.println(String.format("First web page name: %s ", firstWebPagesResult.name()));
                    System.out.println(String.format("First web page URL: %s ", firstWebPagesResult.url()));
                } else {
                    System.out.println("Couldn't find web results!");
                }
            } else {
                System.out.println("Didn't see any Web data..");
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

            // If you do not have a customConfigId, you can also use 1 as your value when setting your environment variable.

            // If you are going to set the AZURE_BING_SAMPLES_API_KEY and AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID environment variables, make sure you set it for your OS, then reopen your command prompt or IDE.
            // If not, you may get an API key not found exception.
            // IMPORTANT: if you have not set the `AZURE_BING_SAMPLES_API_KEY` and AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID environment variables to your cognitive services API key:
            // 1. comment out the below lines
            final String subscriptionKey = System.getenv("AZURE_BING_SAMPLES_API_KEY");
            final String customConfigId = System.getenv("AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID");
            // 2. paste your cognitive services API key and custom config id below, and uncomment the following two lines
            //final String subscriptionKey = "enter your key here";
            //final String customConfigId = "enter your custom config id here";


            BingCustomSearchAPI client = BingCustomSearchManager.authenticate(subscriptionKey);


            runSample(client, customConfigId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
