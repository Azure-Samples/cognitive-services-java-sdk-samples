package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ContentModeratorClientImpl;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ScreenInner;

import java.io.*;

public class TextModeration {
    /*
     * The name of the file that contains the text to evaluate.
     * You will need to create an input file and update this path
     * accordingly. Relative paths are ralative the execution directory.
     */
    private static String TextFile = "TextFile.txt";

    static void execute(ContentModeratorClientImpl client) throws IOException {

        String text = Samples.readFileContents(TextFile);

        text = text.replaceAll(System.lineSeparator(), " ");

        // Screen the input text: check for profanity,
        // do autocorrect text, and check for personally identifying
        // information (PII)
        System.out.println("Normalize text and autocorrect typos.");
        ScreenInner result =
                client.textModerations().screenText("eng", "text/plain", text);
        System.out.println("Response:");
        System.out.println("Tracking Id: " + result.trackingId());
        System.out.println("Status: " + result.status().description());
        System.out.println("Auto corrected text: " + result.autoCorrectedText());
        if(result.classification() != null) {
            System.out.println("Classification: ");
            System.out.println("    Adult score: " + result.classification().adultScore());
            System.out.println("    Offensive score: " + result.classification().offensiveScore());
            System.out.println("    Racy score: " + result.classification().racyScore());
            System.out.println("    Review recommended: " + result.classification().reviewRecommended());
        }
    }

}
