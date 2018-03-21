package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.DetectedTerms;
import com.microsoft.azure.cognitiveservices.contentmoderator.TranscriptModerationBodyItemTermsItem;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ContentModeratorClientImpl;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.CreateVideoReviewsBodyItemInner;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ScreenInner;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.TranscriptModerationBodyItemInner;

import java.util.ArrayList;
import java.util.List;

public class VideoTranscriptReviews {
    /*
     * The minimum amount of time, in milliseconds, to wait between calls
     * to the Image List API.
     */
    private static final int throttleRate = 2000;

    /*
     * Create a video review. For more information, see the API reference:
     * https://westus2.dev.cognitive.microsoft.com/docs/services/580519463f9b070e5c591178/operations/580519483f9b0709fc47f9c4
     * @param client The Content Moderator client.
     * @param id The ID to assign to the video review.
     * @param content The URL of the video to review.
     * @return The ID of the video review.
     */
    private static String CreateReview(ContentModeratorClientImpl client, String id, String content) throws InterruptedException {
        System.out.println("Creating a video review.");
        List<CreateVideoReviewsBodyItemInner> body = new ArrayList<CreateVideoReviewsBodyItemInner>();
        CreateVideoReviewsBodyItemInner item = new CreateVideoReviewsBodyItemInner()
                .withContent(content)
                .withContentId(id)
                .withStatus("Unpublished");
        List<String> result = client.reviews().createVideoReviews("application/json", Samples.TeamName, body);
        Thread.sleep(throttleRate);
        // We created only one review.
        return result.get(0);
    }

    /*
     * Add a transcript to the indicated video review.
     * The transcript must be in the WebVTT format.
     * For more information, see the API reference:
     * https://westus2.dev.cognitive.microsoft.com/docs/services/580519463f9b070e5c591178/operations/59e7b8b2e7151f0b10d451fe
     * @param client The Content Moderator client.
     * @param review_id The video review ID.
     * @param transcript The video transcript.
     */
    static void AddTranscript(ContentModeratorClientImpl client, String review_id, String transcript) throws InterruptedException {
        System.out.println(
                String.format("Adding a transcript to the review with ID %s.", review_id));
        client.reviews().addVideoTranscript(Samples.TeamName, review_id, review_id.getBytes());
        Thread.sleep(throttleRate);
    }

    /*
     * Add the results of moderating a video transcript to the indicated video review.
     * For more information, see the API reference:
     * https://westus2.dev.cognitive.microsoft.com/docs/services/580519463f9b070e5c591178/operations/59e7b93ce7151f0b10d451ff
     * @param client The Content Moderator client.
     * @param review_id The video review ID.
     * @param transcript The video transcript.
     */
    static void AddTranscriptModerationResult(ContentModeratorClientImpl client, String review_id, String transcript) throws InterruptedException {
        System.out.println(
                String.format("Adding a transcript moderation result to the review with ID %s.", review_id));

        // Screen the transcript using the Text Moderation API. For more information, see:
        // https://westus2.dev.cognitive.microsoft.com/docs/services/57cf753a3f9b070c105bd2c1/operations/57cf753a3f9b070868a1f66f
        ScreenInner screen = client.textModerations().screenText("eng", "text/plain", transcript);

        // Map the term list returned by ScreenText into a term list we can pass to AddVideoTranscriptModerationResult.
        List<TranscriptModerationBodyItemTermsItem> terms = new ArrayList<TranscriptModerationBodyItemTermsItem>();
        if (null != screen.terms())
        {
            for (DetectedTerms term : screen.terms())
            {
                if (term.index() != null)
                {
                    terms.add(new TranscriptModerationBodyItemTermsItem()
                            .withIndex(term.index())
                            .withTerm(term.term()));
                }
            }
        }

        List<TranscriptModerationBodyItemInner> body = new ArrayList<TranscriptModerationBodyItemInner>();
        body.add(new TranscriptModerationBodyItemInner()
                .withTimestamp("0")
                .withTerms(terms));
        client.reviews().addVideoTranscriptModerationResult("application/json", Samples.TeamName, review_id, body);
        Thread.sleep(throttleRate);
    }

    /*
     * Publish the indicated video review. For more information, see the reference API:
     * https://westus2.dev.cognitive.microsoft.com/docs/services/580519463f9b070e5c591178/operations/59e7bb29e7151f0b10d45201
     * @param client The Content Moderator client.
     * @param review_id The video review ID.
     */
    private static void PublishReview(ContentModeratorClientImpl client, String review_id) throws InterruptedException {
        System.out.println(
                String.format("Publishing the review with ID %s.", review_id));
        client.reviews().publishVideoReview(Samples.TeamName, review_id);
        Thread.sleep(throttleRate);
    }

    static void execute(ContentModeratorClientImpl client) throws InterruptedException {
            // Create a review with the content pointing to a streaming endpoint (manifest)
            String streamingcontent = "https://amssamples.streaming.mediaservices.windows.net/91492735-c523-432b-ba01-faba6c2206a2/AzureMediaServicesPromo.ism/manifest";
            String review_id = CreateReview(client, "review1", streamingcontent);

            String transcript = "WEBVTT" + System.lineSeparator() +
            "01:01.000 --> 02:02.000" + System.lineSeparator() +
            "First line with a crap word in a transcript." + System.lineSeparator() +
            "02:03.000 --> 02:25.000" + System.lineSeparator() +
            "This is another line in the transcript." + System.lineSeparator();

            AddTranscript(client, review_id, transcript);

            AddTranscriptModerationResult(client, review_id, transcript);

            // Publish the review
            PublishReview(client, review_id);

            System.out.println("Open your Content Moderator Dashboard and select Review > Video to see the review.");
            System.out.println("Press any key to close the application.");
    }
}
