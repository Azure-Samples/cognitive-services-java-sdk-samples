/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.autosuggest.samples;

import com.microsoft.azure.cognitiveservices.search.autosuggest.BingAutoSuggestSearchAPI;
import com.microsoft.azure.cognitiveservices.search.autosuggest.BingAutoSuggestSearchManager;

/**
 * Sample code for custom searching news using Bing Auto Suggest, an Azure Cognitive Service.
 *  - Custom search for "Xbox" and print out name and url for the first web page in the results list.
 */
public class BingAutoSuggestSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing Auto Suggest API client
     * @return true if sample runs successfully
     */
    public static boolean runSample(BingAutoSuggestSearchAPI client, String customConfigId) {
        try {

            //=============================================================
            // This will custom search for "Xbox" and print out name and url for the first web page in the results list

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
            final String customConfigId = System.getenv("AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID");

            BingAutoSuggestSearchAPI client = BingAutoSuggestSearchManager.authenticate(subscriptionKey);


            runSample(client, customConfigId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
