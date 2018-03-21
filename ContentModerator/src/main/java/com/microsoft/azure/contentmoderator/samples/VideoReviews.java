package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.VideoFrameBodyItemMetadataItem;
import com.microsoft.azure.cognitiveservices.contentmoderator.VideoFrameBodyItemReviewerResultTagsItem;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VideoReviews {
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
    private static String CreateReview(ContentModeratorClientImpl client, String id, String content)
            throws InterruptedException {
        System.out.println("Creating a video review.");

        List<CreateVideoReviewsBodyItemInner> body = new ArrayList<CreateVideoReviewsBodyItemInner>();
        /* Note: to create a published review, set the Status to "Pending".
         * However, you cannot add video frames or a transcript to a published review. */
        CreateVideoReviewsBodyItemInner item = new CreateVideoReviewsBodyItemInner()
                .withContent(content)
                .withContentId(id)
                .withStatus("Unpublished");

        List<String> result = client.reviews().createVideoReviews(Samples.TeamName, "application/json", body);
        Thread.sleep(throttleRate);
        // We created only one review.
        return result.get(0);
    }

    /*
     * Create a video frame to add to a video review after the video review is created.
     * @param url The URL of the video frame image.
     * @return The video frame.
     */
    private static VideoFrameBodyItemInner CreateFrameToAddToReview(String url, String timestamp_seconds)
    {
        // We generate random "adult" and "racy" scores for the video frame.
        Random rand = new Random();
        List<VideoFrameBodyItemMetadataItem> metadata = new ArrayList<VideoFrameBodyItemMetadataItem>();
        metadata.add(getVideoFrameBodyItemMetadataItem("reviewRecommended", "true"));
        metadata.add(getVideoFrameBodyItemMetadataItem("adultScore", String.valueOf(rand.nextDouble())));
        metadata.add(getVideoFrameBodyItemMetadataItem("racyScore", String.valueOf(rand.nextDouble())));
        metadata.add(getVideoFrameBodyItemMetadataItem("r", "false"));
        VideoFrameBodyItemReviewerResultTagsItem reviewResultTag =
                new VideoFrameBodyItemReviewerResultTagsItem();
        reviewResultTag.withKey("tag1");
        reviewResultTag.withValue("value1");
        List<VideoFrameBodyItemReviewerResultTagsItem> reviewerResultTags =
                new ArrayList<VideoFrameBodyItemReviewerResultTagsItem>();
        reviewerResultTags.add(reviewResultTag);

        VideoFrameBodyItemInner frame = new VideoFrameBodyItemInner();
        frame.withTimestamp(String.valueOf(Integer.parseInt(timestamp_seconds) * 1000))
                .withFrameImage(url)
                .withMetadata(metadata)
                .withReviewerResultTags(reviewerResultTags);
        return frame;
    }

    /*
     * Add a video frame to the indicated video review. For more information, see the API reference:
     * https://westus2.dev.cognitive.microsoft.com/docs/services/580519463f9b070e5c591178/operations/59e7b76ae7151f0b10d451fd
     * @param client The Content Moderator client.
     * @param review_id The video review ID.
     * @param url The URL of the video frame image.
     */

    static void AddFrame(ContentModeratorClientImpl client, String review_id, String url, String timestamp_seconds) throws InterruptedException {
        System.out.println(
                String.format("Adding a frame to the review with ID %s.", review_id));
        List<VideoFrameBodyItemInner> frames = new ArrayList<VideoFrameBodyItemInner>();
        frames.add(CreateFrameToAddToReview(url, timestamp_seconds));
        client.reviews().addVideoFrameUrl("application/json", Samples.TeamName, review_id, frames);
        Thread.sleep(throttleRate);
    }

    /*
     * Get the video frames assigned to the indicated video review.  For more information, see the API reference:
     * https://westus2.dev.cognitive.microsoft.com/docs/services/580519463f9b070e5c591178/operations/59e7ba43e7151f0b10d45200
     * @param client The Content Moderator client.
     * @param review_id The video review ID.
     */
    static void GetFrames(ContentModeratorClientImpl client, String review_id) throws InterruptedException {
        System.out.println(
                String.format("Getting frames for the review with ID %s.", review_id));
        FramesInner result = client.reviews().getVideoFrames(Samples.TeamName, review_id);
        Thread.sleep(throttleRate);
    }

    /*
     * Get the information for the indicated video review. For more information, see the reference API:
     * https://westus2.dev.cognitive.microsoft.com/docs/services/580519463f9b070e5c591178/operations/580519483f9b0709fc47f9c2
     * @param client The Content Moderator client.
     * @param review_id The video review ID.
     */
    private static void GetReview(ContentModeratorClientImpl client, String review_id) throws InterruptedException {
        System.out.println(
                String.format("Getting the status for the review with ID %s.", review_id));
        ReviewInner result = client.reviews().getReview(Samples.TeamName, review_id);
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

    public static void execute(ContentModeratorClientImpl client) throws InterruptedException {
        // Create a review with the content pointing to a streaming endpoint (manifest)
        String streamingcontent = "https://amssamples.streaming.mediaservices.windows.net/91492735-c523-432b-ba01-faba6c2206a2/AzureMediaServicesPromo.ism/manifest";
        String review_id = CreateReview(client, "review1", streamingcontent);

        String frame1_url = "https://blobthebuilder.blob.core.windows.net/sampleframes/ams-video-frame1-00-17.PNG";
        String frame2_url = "https://blobthebuilder.blob.core.windows.net/sampleframes/ams-video-frame-2-01-04.PNG";
        String frame3_url = "https://blobthebuilder.blob.core.windows.net/sampleframes/ams-video-frame-3-02-24.PNG";

        // Add the frames from 17, 64, and 144 seconds.
        AddFrame(client, review_id, frame1_url, "17");
        AddFrame(client, review_id, frame2_url, "64");
        AddFrame(client, review_id, frame3_url, "144");

        // Get frames information and show
        GetFrames(client, review_id);
        GetReview(client, review_id);

        // Publish the review
        PublishReview(client, review_id);

        System.out.println("Open your Content Moderator Dashboard and select Review > Video to see the review.");
    }

    private static VideoFrameBodyItemMetadataItem getVideoFrameBodyItemMetadataItem(String key, String value) {
        VideoFrameBodyItemMetadataItem item = new VideoFrameBodyItemMetadataItem();
        item.withKey(key);
        item.withValue(value);
        return item;
    }
}
