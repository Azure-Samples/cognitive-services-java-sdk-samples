/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.bingsearch.samples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microsoft.azure.cognitiveservices.spellcheck.SpellingFlaggedToken;
import com.microsoft.azure.cognitiveservices.spellcheck.SpellingTokenSuggestion;
import com.microsoft.azure.cognitiveservices.spellcheck.implementation.SpellCheckAPIImpl;
import com.microsoft.azure.cognitiveservices.spellcheck.implementation.SpellCheckInner;
import com.microsoft.azure.cognitiveservices.spellcheck.ErrorResponseException;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**
 * Azure bing spell check sample.
 */
@JsonIgnoreProperties
public final class SpellCheckSamples {
    /**
     * Makes an instance of the SpellCheckAPIImpl.
     * @param subscriptionKey cognitive services bing subscription key
     * @return SpellCheckAPIImpl instance
     */
    public static SpellCheckAPIImpl getClient(final String subscriptionKey) {
        return new SpellCheckAPIImpl("https://api.cognitive.microsoft.com/bing/v7.0/",
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
     * This will do a search for misspelled query and parse the response
     * @param subscriptionKey cognitive services subscription key
     */
    public static void spellCheckCorrection(String subscriptionKey)
    {
        SpellCheckAPIImpl client = SpellCheckSamples.getClient(subscriptionKey);

        try
        {
            SpellCheckInner result = client.spellChecker("en-us", null, null, null, null, null, null,
                    null, null, null, "en-us", null, null, null, "proof", null, null, "Bill  Gates");
            System.out.println("Correction for Query# \"bill gatas\"");

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
                        System.out.println(String.format("First SpellCheck Suggestion Score: %d ", firstSuggestion.score()));
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
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }

    }

    /**
     * This will trigger an error response from the API
     * @param subscriptionKey cognitive services subscription key
     */
    public static void spellCheckError(String subscriptionKey)
    {
        SpellCheckAPIImpl client = SpellCheckSamples.getClient(subscriptionKey);

        try
        {
            SpellCheckInner result = client.spellChecker("en-us", null, null, null, null, null, null,
                    null, null, null, "en-us", null, null, null, "proof", null, null, null);
            System.out.println("Correction for Query# \"empty text field\"");
        }
        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }
}