/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.bingsearch.samples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microsoft.azure.cognitiveservices.entitysearch.ErrorResponseException;
import com.microsoft.azure.cognitiveservices.videosearch.*;
import com.microsoft.azure.cognitiveservices.videosearch.Freshness;
import com.microsoft.azure.cognitiveservices.videosearch.VideoObject;
import com.microsoft.azure.cognitiveservices.videosearch.implementation.TrendingVideosInner;
import com.microsoft.azure.cognitiveservices.videosearch.implementation.VideoDetailsInner;
import com.microsoft.azure.cognitiveservices.videosearch.implementation.VideoSearchAPIImpl;
import com.microsoft.azure.cognitiveservices.videosearch.implementation.VideosInner;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Azure bing video search samples.
 */
@JsonIgnoreProperties
public final class VideoSearchSamples {
    /**
     * Makes an instance of the VideoSearchAPIImpl.
     * @param subscriptionKey cognitive services bing subscription key
     * @return VideoSearchAPIImpl instance
     */
    public static VideoSearchAPIImpl getClient(final String subscriptionKey) {
        return new VideoSearchAPIImpl("https://api.cognitive.microsoft.com/bing/v7.0/",
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
     * This will search videos for (Nasa CubeSat) then verify number of results and print out id,
     * name and url of first video result
     * @param subscriptionKey cognitive services subscription key
     */
    public static void VideoSearch(String subscriptionKey)
    {
        VideoSearchAPIImpl client = VideoSearchSamples.getClient(subscriptionKey);

        try
        {
            VideosInner videoResults = client.searchs().list("Nasa CubeSat");
            System.out.println("Search videos for query \"Nasa CubeSat\"");

            if (videoResults == null)
            {
                System.out.println("Didn't see any video result data..");
            }
            else
            {
                if (videoResults.value().size() > 0)
                {
                    VideoObject firstVideoResult = videoResults.value().get(0);

                    System.out.println(String.format("Video result count: %d", videoResults.value().size()));
                    System.out.println(String.format("First video id: %s", firstVideoResult.videoId()));
                    System.out.println(String.format("First video name: %s", firstVideoResult.name()));
                    System.out.println(String.format("First video url: %s", firstVideoResult.contentUrl()));
                }
                else
                {
                    System.out.println("Couldn't find video results!");
                }
            }
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }

    }

    /**
     * This will search videos for (Interstellar Trailer) that is free, short and 1080p resolution then verify
     * number of results and print out id, name and url of first video result
     * @param subscriptionKey cognitive services subscription key
     */
    public static void VideoSearchWithFilters(String subscriptionKey)
    {
        VideoSearchAPIImpl client = VideoSearchSamples.getClient(subscriptionKey);

        try
        {
            VideosInner videoResults = client.searchs().list("Interstellar Trailer", "en-us", null, null, null, null, null,
                    Freshness.MONTH, null, VideoLength.SHORT, "en-us", null, VideoPricing.FREE, VideoResolution.HD1080P, null, null, null, null);
            System.out.println("Search videos for query \"Interstellar Trailer\" that is free, short and 1080p resolution");

            if (videoResults == null)
            {
                System.out.println("Didn't see any video result data..");
            }
            else
            {
                if (videoResults.value().size() > 0)
                {
                    VideoObject firstVideoResult = videoResults.value().get(0);
                    System.out.println(String.format("Video result count: %d", videoResults.value().size()));
                    System.out.println(String.format("First video id: %s", firstVideoResult.videoId()));
                    System.out.println(String.format("First video name: %s", firstVideoResult.name()));
                    System.out.println(String.format("First video url: %s", firstVideoResult.contentUrl()));
                }
                else
                {
                    System.out.println("Couldn't find video results!");
                }
            }
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }

    }

    /**
     * This will trending videos then verify banner tiles and categories
     * @param subscriptionKey cognitive services subscription key
     */
    public static void VideoTrending(String subscriptionKey)
    {
        VideoSearchAPIImpl client = VideoSearchSamples.getClient(subscriptionKey);

        try
        {
            TrendingVideosInner trendingResults = client.trendings().list();
            System.out.println("Search trending videos");

            if (trendingResults == null)
            {
                System.out.println("Didn't see any trending video data..");
            }
            else
            {
                // Banner Tiles
                if (trendingResults.bannerTiles().size() > 0)
                {
                    TrendingVideosTile firstBannerTile = trendingResults.bannerTiles().get(0);
                    System.out.println(
                            String.format("Banner tile count: {trendingResults.BannerTiles.Count}"));
                    System.out.println(
                            String.format("First banner tile text: {firstBannerTile.Query.Text}"));
                    System.out.println(
                            String.format("First banner tile url: {firstBannerTile.Query.WebSearchUrl}"));
                }
                else
                {
                    System.out.println("Couldn't find banner tiles!");
                }

                // Categories
                if (trendingResults.categories().size() > 0)
                {
                    TrendingVideosCategory firstCategory = trendingResults.categories().get(0);
                    System.out.println(
                            String.format("Category count: %d", trendingResults.categories().size()));
                    System.out.println(
                            String.format("First category title: %s", firstCategory.title()));

                    if (firstCategory.subcategories().size() > 0)
                    {
                        TrendingVideosSubcategory firstSubCategory = firstCategory.subcategories().get(0);
                        System.out.println(
                                String.format("SubCategory count: %d", firstCategory.subcategories().size()));
                        System.out.println(
                                String.format("First sub category title: %s", firstSubCategory.title()));

                        if (firstSubCategory.tiles().size() > 0)
                        {
                            TrendingVideosTile firstTile = firstSubCategory.tiles().get(0);
                            System.out.println(
                                    String.format("Tile count: %d", firstSubCategory.tiles().size()));
                            System.out.println(
                                    String.format("First tile text: %s", firstTile.query().text()));
                            System.out.println(
                                    String.format("First tile url: %s", firstTile.query().webSearchUrl()));
                        }
                        else
                        {
                            System.out.println("Couldn't find tiles!");
                        }
                    }
                    else
                    {
                        System.out.println("Couldn't find subcategories!");
                    }
                }
                else
                {
                    System.out.println("Couldn't find categories!");
                }
            }
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }

    }

    /**
     * This will search videos for (Interstellar Trailer) and then search for detail information of the first video
     * @param subscriptionKey cognitive services subscription key
     */
    public static void VideoDetail(String subscriptionKey)
    {
        VideoSearchAPIImpl client = VideoSearchSamples.getClient(subscriptionKey);

        try
        {
            VideosInner videoResults = client.searchs().list("Interstellar Trailer");
            if (videoResults.value().size() > 0)
            {
                VideoObject firstVideo = videoResults.value().get(0);
                List<VideoInsightModule> modules = new ArrayList<VideoInsightModule>();
                modules.add(VideoInsightModule.ALL);
                VideoDetailsInner videoDetail = client.details().list("Interstellar Trailer", null, null, null, null, null,
                        firstVideo.videoId(), modules, "en-us", null, null, null, null, null);
                        //nc(query: "Interstellar Trailer", id: firstVideo.VideoId, modules: modules).Result;
                System.out.println(
                        String.format("Search detail for video id={firstVideo.VideoId}, name=%s", firstVideo.name()));

                if (videoDetail != null)
                {
                    if (videoDetail.videoResult() != null)
                    {
                        System.out.println(
                                String.format("Expected video id: %s", videoDetail.videoResult().videoId()));
                        System.out.println(
                                String.format("Expected video name: %s", videoDetail.videoResult().name()));
                        System.out.println(
                                String.format("Expected video url: %s", videoDetail.videoResult().contentUrl()));
                    }
                    else
                    {
                        System.out.println("Couldn't find expected video!");
                    }

                    if (videoDetail.relatedVideos().value().size() > 0)
                    {
                        VideoObject firstRelatedVideo = videoDetail.relatedVideos().value().get(0);
                        System.out.println(
                                String.format("Related video count: %d", videoDetail.relatedVideos().value().size() ));
                        System.out.println(
                                String.format("First related video id: %s", firstRelatedVideo.videoId()));
                        System.out.println(
                                String.format("First related video name: %s", firstRelatedVideo.name()));
                        System.out.println(
                                String.format("First related video url: %s", firstRelatedVideo.contentUrl()));
                    }
                    else
                    {
                        System.out.println("Couldn't find any related video!");
                    }
                }
                else
                {
                    System.out.println("Couldn't find detail about the video!");
                }
            }
            else
            {
                System.out.println("Couldn't find video results!");
            }
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }
}