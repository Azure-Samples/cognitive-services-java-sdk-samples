package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.CreateReviewBodyItemMetadataItem;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ContentModeratorClientImpl;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.CreateReviewBodyItemInner;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ReviewInner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageReviews {
    /*
     * Associates the review ID (assigned by the service) to the internal
     * content ID of the item.
     */
    public static class ReviewItem
    {
        /*
         * The media type for the item to review.
         */
        public String Type;

        /*
         * The URL of the item to review.
         */
        public String Url;

        /*
         * The internal content ID for the item to review.
         */
        public String ContentId;

        /*
         * The ID that the service assigned to the review.
         */
        public String ReviewId;
    }

    /*
     * The minimum amount of time, in milliseconds, to wait between calls
     * to the Image List API.
     */
    private static final int throttleRate = 2000;

    /*
     * The number of seconds to delay after a review has finished before
     * getting the review results from the server.
     */
    private static final int latencyDelay = 45;

    /*
     * The name of the log file to create.
     * <remarks>Relative paths are ralative the execution directory.</remarks>
     */
    private static final String OutputFile = "OutputLog.txt";

    /*
     * The optional name of the subteam to assign the review to.
     */
    private static final String Subteam = null;

    /*
     * The callback endpoint for completed reviews.
     * Reviews show up for reviewers on your team.
     * As reviewers complete reviews, results are sent to the
     * callback endpoint using an HTTP POST request.
     */
    private static final String CallbackEndpoint = "https://requestb.in/vxke1mvx";

    /*
     * The media type for the item to review.
     * Valid values are "image", "text", and "video".
     */
    private static final String MediaType = "image";

    /*
     * The URLs of the images to create review jobs for.
     */
    private static  String[] ImageUrls = new String[] {
        "https://moderatorsampleimages.blob.core.windows.net/samples/sample5.png"
    };

    /*
     * The metadata key to initially add to each review item.
     */
    private static final String MetadataKey = "sc";

    /*
     * The metadata value to initially add to each review item.
     */
    private static final String MetadataValue = "true";

    /*
     * The cached review information, associating a local content ID
     * to the created review ID for each item.
     */
    private static List<String> reviewItems =
            new ArrayList<String>();

    public static void execute(ContentModeratorClientImpl client) throws IOException, InterruptedException {
        CreateReviews(client);
        GetReviewDetails(client);

        System.out.println();
        System.out.println(
                String.format("Waiting %d seconds for results to propagate.", latencyDelay));
        Thread.sleep(latencyDelay * 1000);

        GetReviewDetails(client);
    }

    /*
     * Create the reviews using the fixed list of images.
     * @param client The Content Moderator client.
     */
    private static void CreateReviews(ContentModeratorClientImpl client) throws InterruptedException {
        System.out.println("Creating reviews for the following images:");

        // Create the structure to hold the request body information.
        List<CreateReviewBodyItemInner> requestInfo =
                new ArrayList<CreateReviewBodyItemInner>();

        CreateReviewBodyItemMetadataItem reviewBodyItemMetadataItem =
                new CreateReviewBodyItemMetadataItem();
        reviewBodyItemMetadataItem.withKey(MetadataKey);
        reviewBodyItemMetadataItem.withValue(MetadataValue);
        // Create some standard metadata to add to each item.
        List<CreateReviewBodyItemMetadataItem> metadata =
                new ArrayList<CreateReviewBodyItemMetadataItem>();
        metadata.add(reviewBodyItemMetadataItem);

        // Populate the request body information and the initial cached review information.
        for (int i = 0; i < ImageUrls.length; i++)
        {
            CreateReviewBodyItemInner reviewBodyItemInner = new CreateReviewBodyItemInner();
            System.out.println(" - " + ImageUrls[i] + "; with id = " + i + ".");
            reviewBodyItemInner.withType(MediaType);
            reviewBodyItemInner.withContentId(i + "");
            reviewBodyItemInner.withCallbackEndpoint(CallbackEndpoint);
            reviewBodyItemInner.withContent(ImageUrls[i]);
            reviewBodyItemInner.withMetadata(metadata);
            // Add the item informaton to the request information.
            requestInfo.add(reviewBodyItemInner);
        }

        List<String> reviewIds = client.reviews().createReviews(
                Samples.TeamName, "application/json", requestInfo);

        System.out.println("ReviewIds: ");
        for (int i = 0; i < reviewIds.size(); i++)
        {
            reviewItems.add(reviewIds.get(i));
            System.out.println("Id: " + reviewIds.get(i));
        }

        Thread.sleep(throttleRate);
    }

    /*
     * Gets the review details from the server.
     * @param client The Content Moderator client.
     */
    private static void GetReviewDetails(ContentModeratorClientImpl client) throws InterruptedException {
        System.out.println();
        System.out.println("Getting review details:");
        for (String reviewId : reviewItems)
        {
            ReviewInner reviewDetail = client.reviews().getReview(
                    Samples.TeamName, reviewId);

            System.out.println(
                    "Review " + reviewDetail.reviewId() + " for item ID " + reviewDetail.contentId() + " is " +
                            reviewDetail.status() + ".");
            Thread.sleep(throttleRate);
        }
    }
}
