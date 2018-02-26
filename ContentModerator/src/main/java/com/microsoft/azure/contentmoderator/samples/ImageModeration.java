package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageModeration {
    /*
     * Contains the image moderation results for an image, including
     * text and face detection results.
    */
    public static class EvaluationData
    {
        /*
         * The URL of the evaluated image.
        */
        public String ImageUrl;

        /*
         * The image moderation results.
        */
        public EvaluateInner ImageModeration;

        /*
         * The text detection results.
        */
        public OCRInner TextDetection;

        /*
         * The face detection results;
        */
        public FoundFacesInner FaceDetection;
    }

    /*
     * The name of the file that contains the image URLs to evaluate.
     * You will need to create an input file and update this path
     * accordingly. Relative paths are ralative the execution directory.
    */
    private static String ImageUrlFile = "ImageFiles.txt";

    /*
     * The name of the file to contain the output from the evaluation.
     * Relative paths are ralative the execution directory.
     */
    private static String OutputFile = "ModerationOutput.json";

    public static void execute(ContentModeratorClientImpl client) throws IOException, InterruptedException {
        // Create an object in which to store the image moderation results.
        List<EvaluationData> evaluationData = new ArrayList<EvaluationData>();

        // Read image URLs from the input file and evaluate each one.
        try (BufferedReader inputStream =
                     new BufferedReader(new FileReader(new File(ImageUrlFile)))) {
            String line;
            while ((line = inputStream.readLine()) != null)
            {
                if (line.length() > 0)
                {
                    EvaluationData imageData = EvaluateImage(client, line);
                    evaluationData.add(imageData);
                }
            }
        }

        // Save the moderation results to a file.
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(new File(OutputFile))))
        {
            writer.write(evaluationData.toString());
        }
    }

    /*
     * Evaluates an image using the Image Moderation APIs.
     * This method throttles calls to the API.
     * Your Content Moderator service key will have a requests per second (RPS)
     * rate limit, and the SDK will throw an exception with a 429 error code
     * if you exceed that limit. A free tier key has a 1 RPS rate limit.
     * @param client The Content Moderator API wrapper to use.
     * @param imageUrl The URL of the image to evaluate.
     * @return Aggregated image moderation results for the image.
    */
    private static EvaluationData EvaluateImage(
            ContentModeratorClientImpl client, String imageUrl) throws InterruptedException {
        BodyModelInner url = new BodyModelInner();
        EvaluationData imageData = new EvaluationData();
        url.withDataRepresentation("URL");
        url.withValue(imageUrl);
        imageData.ImageUrl = url.value();

        // Evaluate for adult and racy content.
        imageData.ImageModeration =
                client.imageModerations().evaluateUrlInput(
                        "application/json",
                        url,
                        true);
        Thread.sleep(1000);

        // Detect and extract text.
        imageData.TextDetection =
                client.imageModerations().oCRUrlInput(
                        "eng",
                        "application/json",
                        url,
                        true,
                        false);
        Thread.sleep(1000);

        // Detect faces.
        imageData.FaceDetection =
                client.imageModerations().findFacesUrlInput(
                        "application/json",
                        url,
                        true);
        Thread.sleep(1000);

        return imageData;
    }
}
