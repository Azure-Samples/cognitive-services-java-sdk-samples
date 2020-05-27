/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.textanalytics.samples;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DetectLanguageInput;
import com.azure.ai.textanalytics.models.DetectLanguageResult;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.ai.textanalytics.models.TextAnalyticsRequestOptions;
import com.azure.ai.textanalytics.models.TextDocumentBatchStatistics;
import com.azure.ai.textanalytics.models.TextDocumentInput;
import com.azure.ai.textanalytics.util.TextAnalyticsPagedIterable;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Context;

import java.util.ArrayList;
import java.util.List;

public class Samples {
    /**
     * The key for the api.
     */
    public static String apiKey = null;

    /**
     * Main entry point.
     * 
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            if (apiKey == null) {
                apiKey = System.getenv("AZURE_TEXTANALYTICS_API_KEY");
                if (apiKey == null) {
                    throw new Exception("Azure text analytics samples api key not found.");
                }
            }

            // Create a client.
            AzureKeyCredential credential = new AzureKeyCredential(apiKey);
            TextAnalyticsClient textAnalyticsClient = new TextAnalyticsClientBuilder().apiKey(credential)
                    .endpoint("{endpoint}").buildClient();
            // Extracting language
            System.out.println("===== LANGUAGE EXTRACTION ======");
            List<DetectLanguageInput> documents = new ArrayList<DetectLanguageInput>();
            documents.add(new DetectLanguageInput("1", "This is a document written in English.", "us"));
            documents.add(new DetectLanguageInput("2", "Este es un document escrito en Español.", "es"));
            documents.add(new DetectLanguageInput("3", "这是一个用中文写的文件", "cn"));

            TextAnalyticsPagedIterable<DetectLanguageResult> result = textAnalyticsClient.detectLanguageBatch(documents,
                    null, Context.NONE);
            // Printing language results.
            for (DetectLanguageResult document : result) {
                System.out.println(String.format("Document ID: %s , Language: %s", document.getId(),
                        document.getPrimaryLanguage().getName()));
            }

            // Getting key-phrases
            System.out.println("\n\n===== KEY-PHRASE EXTRACTION ======");
            List<TextDocumentInput> textDocumentInputs = new ArrayList<TextDocumentInput>();
            textDocumentInputs.add(new TextDocumentInput("1", "猫は幸せ", "ja"));
            textDocumentInputs.add(new TextDocumentInput("2", "Fahrt nach Stuttgart und dann zum Hotel zu Fu.", "de"));
            textDocumentInputs.add(new TextDocumentInput("3", "My cat is stiff as a rock.", "en"));
            textDocumentInputs.add(new TextDocumentInput("4", "A mi me encanta el fútbol!", "es"));
            textAnalyticsClient
                    .extractKeyPhrasesBatch(textDocumentInputs,
                            new TextAnalyticsRequestOptions().setIncludeStatistics(true), Context.NONE)
                    .iterableByPage().forEach(response -> {
                        // Batch statistics
                        TextDocumentBatchStatistics batchStatistics = response.getStatistics();
                        System.out.printf(
                                "A batch of documents statistics, transaction count: %s, valid document count: %s.%n",
                                batchStatistics.getTransactionCount(), batchStatistics.getValidDocumentCount());

                        // Extracted key phrase for each of documents from a batch of documents
                        response.getElements().forEach(extractKeyPhraseResult -> {
                            System.out.printf("Document ID: %s%n", extractKeyPhraseResult.getId());
                            // Valid document
                            System.out.println("Extracted phrases:");
                            extractKeyPhraseResult.getKeyPhrases()
                                    .forEach(keyPhrase -> System.out.printf("%s.%n", keyPhrase));
                        });
                    });

            // Extracting sentiment
            System.out.println("\n\n===== SENTIMENT ANALYSIS ======");
            List<TextDocumentInput> sentimentInput = new ArrayList<TextDocumentInput>();
            sentimentInput.add(new TextDocumentInput("0", "I had the best day of my life.", "en"));
            sentimentInput
                    .add(new TextDocumentInput("1", "This was a waste of my time. The speaker put me to sleep.", "en"));
            sentimentInput.add(new TextDocumentInput("2", "No tengo dinero ni nada que dar...", "es"));
            sentimentInput.add(new TextDocumentInput("3",
                    "L'hotel veneziano era meraviglioso. È un bellissimo pezzo di architettura.", "it"));
            textAnalyticsClient.analyzeSentimentBatch(sentimentInput, 
            new TextAnalyticsRequestOptions().setIncludeStatistics(true), Context.NONE).iterableByPage()
            .forEach(response -> {
                // Batch statistics
                TextDocumentBatchStatistics batchStatistics = response.getStatistics();
                System.out.printf("A batch of documents statistics, transaction count: %s, valid document count: %s.%n",
                    batchStatistics.getTransactionCount(), batchStatistics.getValidDocumentCount());
        
                // Analyzed sentiment for each of documents from a batch of documents
                response.getElements().forEach(analyzeSentimentResult -> {
                    System.out.printf("Document ID: %s%n", analyzeSentimentResult.getId());
                    // Valid document
                    DocumentSentiment documentSentiment = analyzeSentimentResult.getDocumentSentiment();
                    System.out.printf(
                        "Recognized document sentiment: %s, positive score: %.2f, neutral score: %.2f, "
                            + "negative score: %.2f.%n",
                        documentSentiment.getSentiment(),
                        documentSentiment.getConfidenceScores().getPositive(),
                        documentSentiment.getConfidenceScores().getNeutral(),
                        documentSentiment.getConfidenceScores().getNegative());
                    documentSentiment.getSentences().forEach(sentenceSentiment -> {
                        System.out.printf(
                            "Recognized sentence sentiment: %s, positive score: %.2f, neutral score: %.2f,"
                                + " negative score: %.2f.%n",
                            sentenceSentiment.getSentiment(),
                            sentenceSentiment.getConfidenceScores().getPositive(),
                            sentenceSentiment.getConfidenceScores().getNeutral(),
                            sentenceSentiment.getConfidenceScores().getNegative());
                    });
                });
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
