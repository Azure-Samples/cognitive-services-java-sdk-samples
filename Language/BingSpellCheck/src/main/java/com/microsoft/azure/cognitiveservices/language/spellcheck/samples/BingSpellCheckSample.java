/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.language.spellcheck.samples;

import com.microsoft.azure.cognitiveservices.language.spellcheck.BingSpellCheckAPI;
import com.microsoft.azure.cognitiveservices.language.spellcheck.BingSpellCheckManager;
import com.microsoft.azure.cognitiveservices.language.spellcheck.models.SpellCheck;
import com.microsoft.azure.cognitiveservices.language.spellcheck.models.SpellCheckerOptionalParameter;
import com.microsoft.azure.cognitiveservices.language.spellcheck.models.SpellingFlaggedToken;
import com.microsoft.azure.cognitiveservices.language.spellcheck.models.SpellingTokenSuggestion;

import java.util.List;

/**
 * Sample code for spell checking using Bing Spell Check, an Azure Cognitive Service.
 *  - Spell check "Bill Gatas" with market and mode settings and print out the flagged tokens and suggestions.
 */
public class BingSpellCheckSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing Spell Check API client
     * @return true if sample runs successfully
     */
    public static boolean runSample(BingSpellCheckAPI client) {
        try {

            //=============================================================
            // This will use Bing Spell Check Cognitive Service to spell check "Bill Gatas" with market and mode
            //   parameters then verify number of results and print out flagged tokens count, token and type of the
            //   first item in the list of flagged tokens, the suggestions total count and the first suggestion in the
            //   list and its score.

            SpellCheck result = client.bingSpellCheckOperations().spellChecker()
                .withText("Bill Gatas")
                .withMode("proof")
                .withMarket("en-us")
                .execute();

            // SpellCheck Results
            if (result.flaggedTokens().size() > 0)
            {
                // find the first spellcheck result
                SpellingFlaggedToken firstspellCheckResult = result.flaggedTokens().get(0);

                if (firstspellCheckResult != null)
                {
                    System.out.println(String.format("SpellCheck Results#%d", result.flaggedTokens().size()));
                    System.out.println(String.format("First SpellCheck Result token: %s ", firstspellCheckResult.token()));
                    System.out.println(String.format("First SpellCheck Result Type: %s ", firstspellCheckResult.type()));
                    System.out.println(String.format("First SpellCheck Result Suggestion Count: %d ",
                            firstspellCheckResult.suggestions().size()));

                    List<SpellingTokenSuggestion> suggestions = firstspellCheckResult.suggestions();
                    if (suggestions.size() > 0)
                    {
                        SpellingTokenSuggestion firstSuggestion = suggestions.get(0);
                        System.out.println(String.format("First SpellCheck Suggestion Score: %f ", firstSuggestion.score()));
                        System.out.println(String.format("First SpellCheck Suggestion : %s ", firstSuggestion.suggestion()));
                    }
                }
                else
                {
                    System.out.println("Couldn't get any Spell check results!");
                }
            }
            else
            {
                System.out.println("Didn't see any SpellCheck results..");
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

            BingSpellCheckAPI client = BingSpellCheckManager.authenticate(subscriptionKey);


            runSample(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
