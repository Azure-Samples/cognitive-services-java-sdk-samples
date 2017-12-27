/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.bingsearch.samples;

import com.microsoft.azure.cognitiveservices.entitysearch.ErrorResponseException;
import com.microsoft.azure.cognitiveservices.imagesearch.*;
import com.microsoft.azure.cognitiveservices.imagesearch.ImageObject;
import com.microsoft.azure.cognitiveservices.imagesearch.PivotSuggestions;
import com.microsoft.azure.cognitiveservices.imagesearch.Query;
import com.microsoft.azure.cognitiveservices.imagesearch.implementation.ImageInsightsInner;
import com.microsoft.azure.cognitiveservices.imagesearch.implementation.ImageSearchAPIImpl;
import com.microsoft.azure.cognitiveservices.imagesearch.implementation.ImagesInner;
import com.microsoft.azure.cognitiveservices.imagesearch.implementation.TrendingImagesInner;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Azure bing image search samples.
 */
public final class ImageCheckSamples {
    /**
     * Makes an instance of the ImageSearchAPIImpl.
     * @param subscriptionKey cognitive services bing subscription key
     * @return ImageSearchAPIImpl instance
     */
    public static ImageSearchAPIImpl getClient(final String subscriptionKey) {
        return new ImageSearchAPIImpl("https://api.cognitive.microsoft.com/bing/v7.0/",
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
     * This will search images for (canadian rockies) then verify number of results and print out first image result,
     * pivot suggestion, and query expansion
     * @param subscriptionKey cognitive services subscription key
     */
    public static void imageSearch(String subscriptionKey)
    {
         ImageSearchAPIImpl client = ImageCheckSamples.getClient(subscriptionKey);

        try
        {
            ImagesInner imageResults = client.searchs().list("canadian rockies");
            System.out.println("Search images for query \"canadian rockies\"");

            if (imageResults == null)
            {
                System.out.println("No image result data.");
            }
            else
            {
                // Image results
                if (imageResults.value().size() > 0)
                {
                    ImageObject firstImageResult = imageResults.value().get(0);

                    System.out.println(String.format("Image result count: %d", imageResults.value().size()));
                    System.out.println(String.format("First image insights token: %s", firstImageResult.imageInsightsToken()));
                    System.out.println(String.format("First image thumbnail url: %s", firstImageResult.thumbnailUrl()));
                    System.out.println(String.format("First image content url: %s", firstImageResult.contentUrl()));
                }
                else
                {
                    System.out.println("Couldn't find image results!");
                }

                System.out.println(String.format("Image result total estimated matches: %s", imageResults.totalEstimatedMatches()));
                System.out.println(String.format("Image result next offset: %s", imageResults.nextOffset()));

                // Pivot suggestions
                if (imageResults.pivotSuggestions().size() > 0)
                {
                    PivotSuggestions firstPivot = imageResults.pivotSuggestions().get(0);

                    System.out.println(String.format("Pivot suggestion count: %d", imageResults.pivotSuggestions().size()));
                    System.out.println(String.format("First pivot: %s", firstPivot.pivot()));

                    if (firstPivot.suggestions().size() > 0)
                    {
                        Query firstSuggestion = firstPivot.suggestions().get(0);

                        System.out.println(String.format("Suggestion count: %s", firstPivot.suggestions().size()));
                        System.out.println(String.format("First suggestion text: %s", firstSuggestion.text()));
                        System.out.println(String.format("First suggestion web search url: %s", firstSuggestion.webSearchUrl()));
                    }
                    else
                    {
                        System.out.println("Couldn't find suggestions!");
                    }
                }
                else
                {
                    System.out.println("Couldn't find pivot suggestions!");
                }

                // Query expansions
                if (imageResults.queryExpansions().size() > 0)
                {
                    Query firstQueryExpansion = imageResults.queryExpansions().get(0);

                    System.out.println(String.format("Query expansion count: %d", imageResults.queryExpansions().size()));
                    System.out.println(String.format("First query expansion text: %s", firstQueryExpansion.text()));
                    System.out.println(String.format("First query expansion search link: %s", firstQueryExpansion.searchLink()));
                }
                else
                {
                    System.out.println("Couldn't find query expansions!");
                }
            }
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }

    }

    /**
     * This will search images for (studio ghibli), filtered for animated gifs and wide aspect, then verify number
     * of results and print out insightsToken, thumbnail url and web url of first result
     * @param subscriptionKey cognitive services subscription key
     */
    public static void imageSearchWithFilters(String subscriptionKey)
    {
         ImageSearchAPIImpl client = ImageCheckSamples.getClient(subscriptionKey);

        try
        {
            ImagesInner imageResults = client.searchs().list("studio ghibli", null, null, null, null, ImageAspect.WIDE, null,
                    null, null, null, null, null, null, ImageType.ANIMATED_GIF, null, null, null, null, null, null,
                    null, null, null, null, null, null, null);
            System.out.println("Search images for \"studio ghibli\" results that are animated gifs and wide aspect");

            if (imageResults == null)
            {
                System.out.println("Didn't see any image result data.");
            }
            else
            {
                // First image result
                if (imageResults.value().size() > 0)
                {
                    ImageObject firstImageResult = imageResults.value().get(0);

                    System.out.println(String.format("Image result count: %s", imageResults.value().size()));
                    System.out.println(String.format("First image insightsToken: %s", firstImageResult.imageInsightsToken()));
                    System.out.println(String.format("First image thumbnail url: %s", firstImageResult.thumbnailUrl()));
                    System.out.println(String.format("First image web search url: %s", firstImageResult.webSearchUrl()));
                }
                else
                {
                    System.out.println("Couldn't find image results!");
                }
            }
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }

    }

    /**
     * This will search for trending images then verify categories and tiles
     * @param subscriptionKey cognitive services subscription key
     */
    public static void imageTrending(String subscriptionKey)
    {
        ImageSearchAPIImpl client = ImageCheckSamples.getClient(subscriptionKey);

        try
        {
            TrendingImagesInner trendingResults = client.trendings().list();
            System.out.println("Search trending images");

            if (trendingResults == null)
            {
                System.out.println("Didn't see any trending image data.");
            }
            else
            {
                // Categories
                if (trendingResults.categories().size() > 0)
                {
                    TrendingImagesCategory firstCategory = trendingResults.categories().get(0);
                    System.out.println(String.format("Category count: %d", trendingResults.categories().size()));
                    System.out.println(String.format("First category title: %s", firstCategory.title()));

                    // Tiles
                    if (firstCategory.tiles().size() > 0)
                    {
                        TrendingImagesTile firstTile = firstCategory.tiles().get(0);
                        System.out.println(String.format("Tile count: %d", firstCategory.tiles().size()));
                        System.out.println(String.format("First tile text: %s", firstTile.query().text()));
                        System.out.println(String.format("First tile url: %s", firstTile.query().webSearchUrl()));
                    }
                    else
                    {
                        System.out.println("Couldn't find tiles!");
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
     * This will search images for (degas) and then search for image details of the first image
     * @param subscriptionKey cognitive services subscription key
     */
    public static void imageDetail(String subscriptionKey)
    {
        ImageSearchAPIImpl client = ImageCheckSamples.getClient(subscriptionKey);

        try
        {
            ImagesInner imageResults = client.searchs().list("degas");
            if (imageResults.value().size() > 0)
            {
                ImageObject firstImage = imageResults.value().get(0);
                List<ImageInsightModule> modules = new ArrayList<ImageInsightModule>();
                modules.add(ImageInsightModule.ALL);
                ImageInsightsInner imageDetail = client.details().list("degas", null, null, null, null, null, null, null,
                    null, null, null, null, null, null, firstImage.imageInsightsToken(), modules, "en-us",
                    null, null);
                        //query: "degas", insightsToken: firstImage.ImageInsightsToken, modules: modules).Result;
                System.out.println(String.format("Search detail for image insightsToken=%s",
                        firstImage.imageInsightsToken()));

                if (imageDetail != null)
                {
                    // Insights token
                    System.out.println(String.format("Expected image insights token: %s",
                            imageDetail.imageInsightsToken()));

                    // Best representative query
                    if (imageDetail.bestRepresentativeQuery() != null)
                    {
                        System.out.println(String.format("Best representative query text: %s",
                                imageDetail.bestRepresentativeQuery().text()));
                        System.out.println(String.format("Best representative query web search url: %s",
                                imageDetail.bestRepresentativeQuery().webSearchUrl()));
                    }
                    else
                    {
                        System.out.println("Couldn't find best representative query!");
                    }

                    // Caption
                    if (imageDetail.imageCaption() != null)
                    {
                        System.out.println(String.format("Image caption: %s",
                                imageDetail.imageCaption().caption()));
                        System.out.println(String.format("Image caption data source url: %s",
                                imageDetail.imageCaption().dataSourceUrl()));
                    }
                    else
                    {
                        System.out.println("Couldn't find image caption!");
                    }

                    // Pages including the image
                    if (imageDetail.pagesIncluding().value().size() > 0)
                    {
                        ImageObject firstPage = imageDetail.pagesIncluding().value().get(0);
                        System.out.println(String.format("Pages including count: %d",
                                imageDetail.pagesIncluding().value().size()));
                        System.out.println(String.format("First page content url: %s",
                                firstPage.contentUrl()));
                        System.out.println(String.format("First page name: %s",
                                firstPage.name()));
                        System.out.println(String.format("First page date published: %s",
                                firstPage.datePublished()));
                    }
                    else
                    {
                        System.out.println("Couldn't find any pages including this image!");
                    }

                    // Related searches
                    if (imageDetail.relatedSearches().value().size() > 0)
                    {
                        Query firstRelatedSearch = imageDetail.relatedSearches().value().get(0);
                        System.out.println(String.format("Related searches count: %d",
                                imageDetail.relatedSearches().value().size()));
                        System.out.println(String.format("First related search text: %s",
                                firstRelatedSearch.text()));
                        System.out.println(String.format("First related search web search url: %s",
                                firstRelatedSearch.webSearchUrl()));
                    }
                    else
                    {
                        System.out.println("Couldn't find any related searches!");
                    }

                    // Visually similar images
                    if (imageDetail.visuallySimilarImages().value().size() > 0)
                    {
                        ImageObject firstVisuallySimilarImage = imageDetail.visuallySimilarImages().value().get(0);
                        System.out.println(String.format("Visually similar images count: %d",
                                imageDetail.relatedSearches().value().size()));
                        System.out.println(String.format("First visually similar image name: %s",
                                firstVisuallySimilarImage.name()));
                        System.out.println(String.format("First visually similar image content url: %s",
                                firstVisuallySimilarImage.contentUrl()));
                        System.out.println(String.format("First visually similar image size: %s",
                                firstVisuallySimilarImage.contentSize()));
                    }
                    else
                    {
                        System.out.println("Couldn't find any related searches!");
                    }

                    // Image tags
                    if (imageDetail.imageTags().value().size() > 0)
                    {
                        InsightsTag firstTag = imageDetail.imageTags().value().get(0);
                        System.out.println(String.format("Image tags count: %d",
                                imageDetail.imageTags().value().size()));
                        System.out.println(String.format("First tag name: %s",
                                firstTag.name()));
                    }
                    else
                    {
                        System.out.println("Couldn't find any related searches!");
                    }
                }
                else
                {
                    System.out.println("Couldn't find detail about the image!");
                }
            }
            else
            {
                System.out.println("Couldn't find image results!");
            }
        }

        catch (ErrorResponseException ex)
        {
            System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }
}