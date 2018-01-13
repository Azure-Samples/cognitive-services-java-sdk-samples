/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.textanalytics.samples;

import com.microsoft.azure.cognitiveservices.textanalytics.*;
import com.microsoft.azure.cognitiveservices.textanalytics.implementation.*;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Samples {
    /**
     * The key for the api.
     */
    public static String apiKey = null;

    /**
     * Makes an instance of the EntitySearchAPIImpl.
     * @param subscriptionKey cognitive services subscription key
     * @return EntitySearchAPIImpl instance
     */
    public static TextAnalyticsAPIImpl getClient(final String subscriptionKey) {
        return new TextAnalyticsAPIImpl(
                "https://westus.api.cognitive.microsoft.com/text/analytics/",
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
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            if(apiKey == null) {
                apiKey = System.getenv("AZURE_TEXTANALYTICS_API_KEY");
                if(apiKey == null) {
                    throw new Exception("Azure text analytics samples api key not found.");
                }
            }

            // Create a client.
            TextAnalyticsAPIImpl client = getClient(apiKey);
            client.withAzureRegion(AzureRegions.WESTUS);
            // Extracting language
            System.out.println("===== LANGUAGE EXTRACTION ======");
            BatchInputInner batchInput = new BatchInputInner();
            List<Input> documents = new ArrayList<Input>();
            documents.add(makeInput("1", "This is a document written in English."));
            documents.add(makeInput("2", "Este es un document escrito en Español."));
            documents.add(makeInput("3", "这是一个用中文写的文件"));
            batchInput.withDocuments(documents);

            LanguageBatchResultInner result = client.detectLanguage(batchInput);
            // Printing language results.
            for(LanguageBatchResultItem document : result.documents())
            {
                System.out.println(
                        String.format("Document ID: %s , Language: %s", document.id(), document.detectedLanguages().get(0).name()));
            }

            // Getting key-phrases
            System.out.println("\n\n===== KEY-PHRASE EXTRACTION ======");
            List<MultiLanguageInput> keyPhraseInput = new ArrayList<MultiLanguageInput>();
            keyPhraseInput.add(makeMultiLanguageInput("ja","1","猫は幸せ"));
            keyPhraseInput.add(makeMultiLanguageInput("de", "2", "Fahrt nach Stuttgart und dann zum Hotel zu Fu."));
            keyPhraseInput.add(makeMultiLanguageInput("en", "3", "My cat is stiff as a rock."));
            keyPhraseInput.add(makeMultiLanguageInput("es", "4", "A mi me encanta el fútbol!"));
            MultiLanguageBatchInputInner keyPhraseInputs = new MultiLanguageBatchInputInner();
            keyPhraseInputs.withDocuments(keyPhraseInput);
            KeyPhraseBatchResultInner result2 = client.keyPhrases(keyPhraseInputs);

            // Printing keyphrases
            for(KeyPhraseBatchResultItem document : result2.documents())
            {
                System.out.println(
                        String.format("Document ID: %s ", document.id()));

                System.out.println("\t Key phrases:");

                for(String keyphrase : document.keyPhrases())
                {
                    System.out.println("\t\t" + keyphrase);
                }
            }

            // Extracting sentiment
            System.out.println("\n\n===== SENTIMENT ANALYSIS ======");
            List<MultiLanguageInput> sentimentInput = new ArrayList<MultiLanguageInput>();
            sentimentInput.add(makeMultiLanguageInput("en","0","I had the best day of my life."));
            sentimentInput.add(makeMultiLanguageInput("en","1","This was a waste of my time. The speaker put me to sleep."));
            sentimentInput.add(makeMultiLanguageInput("es","2","No tengo dinero ni nada que dar..."));
            sentimentInput.add(makeMultiLanguageInput("it","3","L'hotel veneziano era meraviglioso. È un bellissimo pezzo di architettura."));
            MultiLanguageBatchInputInner sentimentInputs = new MultiLanguageBatchInputInner();
            sentimentInputs.withDocuments(sentimentInput);
            SentimentBatchResultInner result3 = client.sentiment(sentimentInputs);


            // Printing sentiment results
            for(SentimentBatchResultItem document : result3.documents())
            {
                System.out.println(
                        String.format("Document ID: %s , Sentiment Score: %,.2f", document.id(), document.score()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static Input makeInput(String id, String text) {
        Input input = new Input();
        input.withId(id);
        input.withText(text);
        return input;
    }

    private static MultiLanguageInput makeMultiLanguageInput(String language, String id, String text) {
        MultiLanguageInput input = new MultiLanguageInput();
        input.withLanguage(language);
        input.withId(id);
        input.withText(text);
        return input;
    }
}
