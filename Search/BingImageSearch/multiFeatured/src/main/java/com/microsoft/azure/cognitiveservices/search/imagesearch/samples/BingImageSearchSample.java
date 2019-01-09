/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.imagesearch.samples;

import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchAPI;
import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchManager;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.DetailsOptionalParameter;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageAspect;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageInsightModule;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageInsights;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageObject;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageType;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImagesModel;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.InsightsTag;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.PivotSuggestions;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.Query;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.TrendingImages;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.TrendingImagesCategory;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.TrendingImagesTile;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample code for searching images using Bing Image Search, an Azure Cognitive Service.
 *  - Search images for "canadian rockies" then verify number of results and print out pivot suggestion and query expansion.
 *  - Search images for "studio ghibli", filtered for animated GIFs and wide aspect and print out insightsToken, thumbnail url and web url.
 *  - Search for trending images then print out categories and tiles.
 *  - Search images for "degas" and then search the image details of the first item in result list.
 */
public class BingImageSearchSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing News Search API client
     * @return true if sample runs successfully
     */
    public static boolean runSample(BingImageSearchAPI client) {
        try {

            //=============================================================
            // This will search images for "canadian rockies" then verify number of results and print out first image result,
            //   pivot suggestion, and query expansion.

            System.out.println("Search images for query \"canadian rockies\"");
            ImagesModel imageResults = client.bingImages().search()
                .withQuery("canadian rockies")
                .withMarket("en-us")
                .execute();

            if (imageResults != null) {
                // Image results
                if (imageResults.value().size() > 0) {
                    ImageObject firstImageResult = imageResults.value().get(0);

                    System.out.println(String.format("Image result count: %d", imageResults.value().size()));
                    System.out.println(String.format("First image insights token: %s", firstImageResult.imageInsightsToken()));
                    System.out.println(String.format("First image thumbnail url: %s", firstImageResult.thumbnailUrl()));
                    System.out.println(String.format("First image content url: %s", firstImageResult.contentUrl()));
                } else {
                    System.out.println("Couldn't find image results!");
                }

                System.out.println(String.format("Image result total estimated matches: %s", imageResults.totalEstimatedMatches()));
                System.out.println(String.format("Image result next offset: %s", imageResults.nextOffset()));

                // Pivot suggestions
                if (imageResults.pivotSuggestions().size() > 0) {
                    PivotSuggestions firstPivot = imageResults.pivotSuggestions().get(0);

                    System.out.println(String.format("Pivot suggestion count: %d", imageResults.pivotSuggestions().size()));
                    System.out.println(String.format("First pivot: %s", firstPivot.pivot()));

                    if (firstPivot.suggestions().size() > 0) {
                        Query firstSuggestion = firstPivot.suggestions().get(0);

                        System.out.println(String.format("Suggestion count: %s", firstPivot.suggestions().size()));
                        System.out.println(String.format("First suggestion text: %s", firstSuggestion.text()));
                        System.out.println(String.format("First suggestion web search url: %s", firstSuggestion.webSearchUrl()));
                    } else {
                        System.out.println("Couldn't find suggestions!");
                    }
                } else {
                    System.out.println("Couldn't find pivot suggestions!");
                }

                // Query expansions
                if (imageResults.queryExpansions().size() > 0) {
                    Query firstQueryExpansion = imageResults.queryExpansions().get(0);

                    System.out.println(String.format("Query expansion count: %d", imageResults.queryExpansions().size()));
                    System.out.println(String.format("First query expansion text: %s", firstQueryExpansion.text()));
                    System.out.println(String.format("First query expansion search link: %s", firstQueryExpansion.searchLink()));
                } else {
                    System.out.println("Couldn't find query expansions!");
                }
            } else {
                System.out.println("No image result data.");
            }


            //=============================================================
            // This will search images for "studio ghibli", filtered for animated GIFs and wide aspect, then verify number
            //   of results and print out insightsToken, thumbnail url and web url of first result.

            System.out.println("Search images for \"studio ghibli\" results that are animated gifs and wide aspect");
            imageResults = client.bingImages().search()
                .withQuery("studio ghibli")
                .withAspect(ImageAspect.WIDE)
                .withImageType(ImageType.ANIMATED_GIF)
                .withMarket("en-us")
                .execute();

            if (imageResults != null) {
                // First image result
                if (imageResults.value().size() > 0) {
                    ImageObject firstImageResult = imageResults.value().get(0);

                    System.out.println(String.format("Image result count: %s", imageResults.value().size()));
                    System.out.println(String.format("First image insightsToken: %s", firstImageResult.imageInsightsToken()));
                    System.out.println(String.format("First image thumbnail url: %s", firstImageResult.thumbnailUrl()));
                    System.out.println(String.format("First image web search url: %s", firstImageResult.webSearchUrl()));
                } else {
                    System.out.println("Couldn't find image results!");
                }
            } else {
                System.out.println("No image result data.");
            }


            //=============================================================
            // This will search for trending images then verify categories and tiles.

            System.out.println("Search trending images");
            TrendingImages trendingResults = client.bingImages().trending()
                .withMarket("en-us")
                .execute();

            if (trendingResults != null) {
                // Categories
                if (trendingResults.categories().size() > 0) {
                    TrendingImagesCategory firstCategory = trendingResults.categories().get(0);
                    System.out.println(String.format("Category count: %d", trendingResults.categories().size()));
                    System.out.println(String.format("First category title: %s", firstCategory.title()));

                    // Tiles
                    if (firstCategory.tiles().size() > 0) {
                        TrendingImagesTile firstTile = firstCategory.tiles().get(0);
                        System.out.println(String.format("Tile count: %d", firstCategory.tiles().size()));
                        System.out.println(String.format("First tile text: %s", firstTile.query().text()));
                        System.out.println(String.format("First tile url: %s", firstTile.query().webSearchUrl()));
                    } else {
                        System.out.println("Couldn't find tiles!");
                    }
                } else {
                    System.out.println("Couldn't find categories!");
                }
            } else {
                System.out.println("Didn't see any trending image data.");
            }


            //=============================================================
            // This will search images for "degas" and then search for image details of the first image.

            System.out.println("Search images for query \"degas\"");
            imageResults = client.bingImages().search()
                .withQuery("degas")
                .withMarket("en-us")
                .execute();

            if (imageResults != null) {
                if (imageResults.value().size() > 0) {
                    ImageObject firstImage = imageResults.value().get(0);
                    List<ImageInsightModule> modules = new ArrayList<ImageInsightModule>();
                    modules.add(ImageInsightModule.ALL);

                    System.out.println(String.format("Search detail for image insightsToken=%s",
                            firstImage.imageInsightsToken()));
                    ImageInsights imageDetail = client.bingImages().details("degas",
                            new DetailsOptionalParameter()
                                    .withInsightsToken(firstImage.imageInsightsToken())
                                    .withModules(modules)
                                    .withMarket("en-us"));

                    if (imageDetail != null) {
                        // Insights token
                        System.out.println(String.format("Expected image insights token: %s",
                                imageDetail.imageInsightsToken()));

                        // Best representative query
                        if (imageDetail.bestRepresentativeQuery() != null) {
                            System.out.println(String.format("Best representative query text: %s",
                                    imageDetail.bestRepresentativeQuery().text()));
                            System.out.println(String.format("Best representative query web search url: %s",
                                    imageDetail.bestRepresentativeQuery().webSearchUrl()));
                        } else {
                            System.out.println("Couldn't find best representative query!");
                        }

                        // Caption
                        if (imageDetail.imageCaption() != null) {
                            System.out.println(String.format("Image caption: %s",
                                    imageDetail.imageCaption().caption()));
                            System.out.println(String.format("Image caption data source url: %s",
                                    imageDetail.imageCaption().dataSourceUrl()));
                        } else {
                            System.out.println("Couldn't find image caption!");
                        }

                        // Pages including the image
                        if (imageDetail.pagesIncluding().value().size() > 0) {
                            ImageObject firstPage = imageDetail.pagesIncluding().value().get(0);
                            System.out.println(String.format("Pages including count: %d",
                                    imageDetail.pagesIncluding().value().size()));
                            System.out.println(String.format("First page content url: %s",
                                    firstPage.contentUrl()));
                            System.out.println(String.format("First page name: %s",
                                    firstPage.name()));
                            System.out.println(String.format("First page date published: %s",
                                    firstPage.datePublished()));
                        } else {
                            System.out.println("Couldn't find any pages including this image!");
                        }

                        // Related searches
                        if (imageDetail.relatedSearches().value().size() > 0) {
                            Query firstRelatedSearch = imageDetail.relatedSearches().value().get(0);
                            System.out.println(String.format("Related searches count: %d",
                                    imageDetail.relatedSearches().value().size()));
                            System.out.println(String.format("First related search text: %s",
                                    firstRelatedSearch.text()));
                            System.out.println(String.format("First related search web search url: %s",
                                    firstRelatedSearch.webSearchUrl()));
                        } else {
                            System.out.println("Couldn't find any related searches!");
                        }

                        // Visually similar images
                        if (imageDetail.visuallySimilarImages().value().size() > 0) {
                            ImageObject firstVisuallySimilarImage = imageDetail.visuallySimilarImages().value().get(0);
                            System.out.println(String.format("Visually similar images count: %d",
                                    imageDetail.relatedSearches().value().size()));
                            System.out.println(String.format("First visually similar image name: %s",
                                    firstVisuallySimilarImage.name()));
                            System.out.println(String.format("First visually similar image content url: %s",
                                    firstVisuallySimilarImage.contentUrl()));
                            System.out.println(String.format("First visually similar image size: %s",
                                    firstVisuallySimilarImage.contentSize()));
                        } else {
                            System.out.println("Couldn't find any related searches!");
                        }

                        // Image tags
                        if (imageDetail.imageTags().value().size() > 0) {
                            InsightsTag firstTag = imageDetail.imageTags().value().get(0);
                            System.out.println(String.format("Image tags count: %d",
                                    imageDetail.imageTags().value().size()));
                            System.out.println(String.format("First tag name: %s",
                                    firstTag.name()));
                        } else {
                            System.out.println("Couldn't find any related searches!");
                        }
                    } else {
                        System.out.println("Couldn't find detail about the image!");
                    }
                } else {
                    System.out.println("Couldn't find image results!");
                }
            } else {
                System.out.println("No image result data.");
            }

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        return false;
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

            BingImageSearchAPI client = BingImageSearchManager.authenticate(subscriptionKey);

            runSample(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
