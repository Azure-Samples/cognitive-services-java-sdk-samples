/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.videosearch.samples;

import com.microsoft.azure.cognitiveservices.search.videosearch.BingVideoSearchAPI;
import com.microsoft.azure.cognitiveservices.search.videosearch.BingVideoSearchManager;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.ErrorResponseException;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.Freshness;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.TrendingVideos;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.TrendingVideosCategory;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.TrendingVideosSubcategory;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.TrendingVideosTile;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.VideoDetails;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.VideoInsightModule;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.VideoLength;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.VideoObject;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.VideoPricing;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.VideoResolution;
import com.microsoft.azure.cognitiveservices.search.videosearch.models.VideosModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample code for searching news using Bing Video Search, an Azure Cognitive Service.
 *  - Search videos for "SwiftKey" and print out id, name and url.
 *  - Search videos for "Bellevue Trailer" that is free, short and 1080p resolution and print out id, name and url.
 *  - Search for trending videos then verify banner tiles and categories.
 *  - Search videos for "Bellevue Trailer" and then search for detail information of the first video.
 */
public class BingVideoSearchSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing Video Search API client
     * @return true if sample runs successfully
     */
    public static boolean runSample(BingVideoSearchAPI client) {
        try {

            //=============================================================
            // This will search videos for (SwiftKey) then verify number of results and print out id,
            //   name and url of the first video result.

            System.out.println("Search videos for query \"SwiftKey\"");
            VideosModel videoResults = client.bingVideos().search()
                .withQuery("SwiftKey")
                .withCount(10)
                .withMarket("en-us")
                .execute();

            printVideoResults(videoResults);


            //=============================================================
            // This will search videos for "Bellevue Trailer" that is free, short and 1080p resolution then verify
            //   number of results and print out id, name and url of the first video result.

            System.out.println("Search videos for query \"Bellevue Trailer\" that is free, short and 1080p resolution");
            videoResults = client.bingVideos().search()
                .withQuery("Bellevue Trailer")
                .withFreshness(Freshness.MONTH)
                .withLength(VideoLength.SHORT)
                .withPricing(VideoPricing.FREE)
                .withResolution(VideoResolution.HD1080P)
                .withMarket("en-us")
                .execute();

            printVideoResults(videoResults);


            //=============================================================
            // This will search for trending videos then verify banner tiles and categories.

            System.out.println("Search trending videos");
            TrendingVideos trendingResults = client.bingVideos().trending()
                .withMarket("en-us")
                .execute();

            printTrendingResults(trendingResults);


            //=============================================================
            // This will search videos for "Bellevue Trailer" and then search for detail information of the first video.

            if (videoResults != null && videoResults.value().size() > 0)
            {
                VideoObject firstVideo = videoResults.value().get(0);
                List<VideoInsightModule> modules = new ArrayList<VideoInsightModule>();
                modules.add(VideoInsightModule.ALL);

                System.out.println(
                        String.format("Search detail for video id={firstVideo.VideoId}, name=%s", firstVideo.name()));
                int maxTries = 2;
                for (int i = 1; i <= 2; i++) {
                    try {
                        VideoDetails videoDetail = client.bingVideos().details()
                                .withQuery("Bellevue Trailer")
                                .withId(firstVideo.videoId())
                                .withModules(modules)
                                .withMarket("en-us")
                                .execute();

                        if (videoDetail != null) {
                            if (videoDetail.videoResult() != null) {
                                System.out.println(
                                        String.format("Expected video id: %s", videoDetail.videoResult().videoId()));
                                System.out.println(
                                        String.format("Expected video name: %s", videoDetail.videoResult().name()));
                                System.out.println(
                                        String.format("Expected video url: %s", videoDetail.videoResult().contentUrl()));
                            } else {
                                System.out.println("Couldn't find expected video!");
                            }

                            if (videoDetail.relatedVideos() != null && videoDetail.relatedVideos().value() != null &&
                                    videoDetail.relatedVideos().value().size() > 0) {
                                VideoObject firstRelatedVideo = videoDetail.relatedVideos().value().get(0);
                                System.out.println(
                                        String.format("Related video count: %d", videoDetail.relatedVideos().value().size()));
                                System.out.println(
                                        String.format("First related video id: %s", firstRelatedVideo.videoId()));
                                System.out.println(
                                        String.format("First related video name: %s", firstRelatedVideo.name()));
                                System.out.println(
                                        String.format("First related video url: %s", firstRelatedVideo.contentUrl()));
                            } else {
                                System.out.println("Couldn't find any related video!");
                            }
                        } else {
                            System.out.println("Couldn't find detail about the video!");
                        }
                        break;
                    } catch (ErrorResponseException e) {
                        System.out.println(
                                String.format("Exception occurred, status code %s with reason %s.", e.response().code(), e.response().message()));

                        if (e.response().code() == 429) {
                            System.out.println("You are getting a request exceeded error because you are using the free tier for this sample. Code will wait 1 second before resending request");
                        }

                        if (i == maxTries) {
                            throw e;
                        }
                        Thread.sleep(1000);
                        System.out.println("Resending request now...");
                    }
                }
            } else {
                System.out.println("Couldn't find video results!");
            }

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        return false;
    }

    /**
     * Print out id, name and url of the first video result.
     *
     * @param videoResults the video results
     */
    public static void printVideoResults(VideosModel videoResults) {
        if (videoResults != null && videoResults.value() != null) {
            if (videoResults.value().size() > 0) {
                VideoObject firstVideoResult = videoResults.value().get(0);
                System.out.println(String.format("Video result count: %d", videoResults.value().size()));
                System.out.println(String.format("First video id: %s", firstVideoResult.videoId()));
                System.out.println(String.format("First video name: %s", firstVideoResult.name()));
                System.out.println(String.format("First video url: %s", firstVideoResult.contentUrl()));
            } else {
                System.out.println("Couldn't find video results!");
            }
        } else {
            System.out.println("Didn't see any video result data..");
        }
    }

    /**
     * Print out id, name and url of the first video result.
     *
     * @param trendingResults the video results
     */
    public static void printTrendingResults(TrendingVideos trendingResults) {
        if (trendingResults != null) {
            // Banner Tiles
            if (trendingResults.bannerTiles().size() > 0) {
                TrendingVideosTile firstBannerTile = trendingResults.bannerTiles().get(0);
                System.out.println(
                        String.format("Banner tile count: {trendingResults.BannerTiles.Count}"));
                System.out.println(
                        String.format("First banner tile text: {firstBannerTile.Query.Text}"));
                System.out.println(
                        String.format("First banner tile url: {firstBannerTile.Query.WebSearchUrl}"));
            } else {
                System.out.println("Couldn't find banner tiles!");
            }

            // Categories
            if (trendingResults.categories().size() > 0) {
                TrendingVideosCategory firstCategory = trendingResults.categories().get(0);
                System.out.println(
                        String.format("Category count: %d", trendingResults.categories().size()));
                System.out.println(
                        String.format("First category title: %s", firstCategory.title()));

                if (firstCategory.subcategories().size() > 0) {
                    TrendingVideosSubcategory firstSubCategory = firstCategory.subcategories().get(0);
                    System.out.println(
                            String.format("SubCategory count: %d", firstCategory.subcategories().size()));
                    System.out.println(
                            String.format("First sub category title: %s", firstSubCategory.title()));

                    if (firstSubCategory.tiles().size() > 0) {
                        TrendingVideosTile firstTile = firstSubCategory.tiles().get(0);
                        System.out.println(
                                String.format("Tile count: %d", firstSubCategory.tiles().size()));
                        System.out.println(
                                String.format("First tile text: %s", firstTile.query().text()));
                        System.out.println(
                                String.format("First tile url: %s", firstTile.query().webSearchUrl()));
                    } else {
                        System.out.println("Couldn't find tiles!");
                    }
                } else {
                    System.out.println("Couldn't find subcategories!");
                }
            } else {
                System.out.println("Couldn't find categories!");
            }
        } else {
            System.out.println("Didn't see any trending video data..");
        }
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

            // If you are going to set the AZURE_BING_SAMPLES_API_KEY environment variable, make sure you set it for your OS, then reopen your command prompt or IDE.
            // If not, you may get an API key not found exception.
            // IMPORTANT: if you have not set the `AZURE_BING_SAMPLES_API_KEY` environment variable to your cognitive services API key:
            // 1. comment out the below line
            final String subscriptionKey = System.getenv("AZURE_BING_SAMPLES_API_KEY");
            // 2. paste your cognitive services API key below, and uncomment the line
            //final String subscriptionKey = "enter your key here";

            BingVideoSearchAPI client = BingVideoSearchManager.authenticate(subscriptionKey);

            runSample(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
