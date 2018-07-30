/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.autosuggest.samples;

import com.microsoft.azure.cognitiveservices.search.autosuggest.BingAutoSuggestSearchAPI;
import com.microsoft.azure.cognitiveservices.search.autosuggest.BingAutoSuggestSearchManager;
import com.microsoft.azure.cognitiveservices.search.autosuggest.models.SearchAction;
import com.microsoft.azure.cognitiveservices.search.autosuggest.models.Suggestions;
import com.microsoft.azure.cognitiveservices.search.autosuggest.models.SuggestionsSuggestionGroup;

/**
 * Sample code for custom searching news using Bing Auto Suggest, an Azure Cognitive Service.
 *  - Search for "Satya Nadella" and print out the first group of suggestions returned from the service.
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
            // This will request suggestions for "Satya Nadella" and print out the results

            System.out.println("Searched for \"Satya Nadella\" and print out the returned suggestions");

            Suggestions suggestions = client.bingAutoSuggestSearch().autoSuggest()
                .withQuery("Satya Nadella")
                .execute();

            if (suggestions != null && suggestions.suggestionGroups() != null && suggestions.suggestionGroups().size() > 0) {
                System.out.println("Found the following suggestions:");
                for (SearchAction suggestion: suggestions.suggestionGroups().get(0).searchSuggestions()) {
                    System.out.println("....................................");
                    System.out.println(suggestion.query());
                    System.out.println(suggestion.displayText());
                    System.out.println(suggestion.url());
                    System.out.println(suggestion.searchKind());
                }
            } else {
                System.out.println("Didn't see any suggestion...");
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
            final String customConfigId = System.getenv("AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID");

            BingAutoSuggestSearchAPI client = BingAutoSuggestSearchManager.authenticate(subscriptionKey);


            runSample(client, customConfigId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
