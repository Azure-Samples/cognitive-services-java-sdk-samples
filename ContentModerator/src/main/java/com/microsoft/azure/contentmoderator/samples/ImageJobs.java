/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ContentInner;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ContentModeratorClientImpl;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.JobIdInner;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.JobInner;

import java.io.IOException;

public class ImageJobs {
    /*
     * The moderation job will use this workflow that you defined earlier.
     * See the quickstart article to learn how to setup custom workflows.
    */
    private static final String WorkflowName = "OCR";

    /*
     * The URL of the image to create a review job for.
    */
    private static final String ImageUrl =
            "https://moderatorsampleimages.blob.core.windows.net/samples/sample2.jpg";

    /*
     * The number of seconds to delay after a review has finished before
     * getting the review results from the server.
    */
    private static final int latencyDelay = 45;

    /*
     * The callback endpoint for completed reviews.
     * Reviews show up for reviewers on your team.
     * As reviewers complete reviews, results are sent to the
     * callback endpoint using an HTTP POST request.
    */
    private static final String CallbackEndpoint = "https%3A%2F%2Frequestb.in%2Fvxke1mvx";

    public static void execute(ContentModeratorClientImpl client) throws IOException, InterruptedException {
        System.out.println("Create moderation job for an image.");
        ContentInner contentInner = new ContentInner();
        contentInner.withContentValue(ImageUrl);

        // The WorkflowName contains the nameof the workflow defined in the online review tool.
        // See the quickstart article to learn more.
        JobIdInner jobResult = client.reviews().createJob(
                Samples.TeamName,
                "image",
                "contentID",
                WorkflowName,
                "application/json",
                contentInner,
                CallbackEndpoint);

        String jobId = jobResult.jobId();
        // Record the job ID.
        System.out.println("Job id created: " + jobId);

        Thread.sleep(2000);
        System.out.println();

        System.out.println("Get job status before review.");
        JobInner job = client.reviews().getJobDetails(
                Samples.TeamName, jobId);
        System.out.println("Job status:" + job.status());
        System.out.println();
        System.out.println("Perform manual reviews on the Content Moderator site.");
        System.out.println("Then, press any key to continue.");
        System.in.read();

        System.out.println();
        System.out.println("Waiting " + latencyDelay + " seconds for results to propagate.");
        Thread.sleep(latencyDelay * 1000);

        System.out.println("Get job status after review.");
        job = client.reviews().getJobDetails(
                Samples.TeamName, jobId);
        System.out.println("Job status:" + job.status());
    }
}
