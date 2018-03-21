package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.BodyMetadata;
import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class ImageList {
    /*
     * The minimum amount of time, im milliseconds, to wait between calls
     * to the Image List API.
    */
    private static final int throttleRate = 3000;

    /*
     * The number of minutes to delay after updating the search index before
     * performing image match operations against a the list.
    */
    private static final double latencyDelay = 0.5;

    /*
     * Define finalants for the labels to apply to the image list.
    */
    private class Labels
    {
        public static final String Sports = "Sports";
        public static final String Swimsuit = "Swimsuit";
    }

    /*
     * Define input data for images for this sample.
    */
    private static class Images
    {
        /*
         * Represents a group of images that all share the same label.
        */
        public class Data
        {
            /*
             * The label for the images.
            */
            public String Label;

            /*
             * The URLs of the images.
            */
            public String[] Urls;
        }

        /*
         * The initial set of images to add to the list with the sports label.
        */
        public final Data Sports = new Data();

        /*
         * The initial set of images to add to the list with the swimsuit label.
         * We're adding sample16.png (image of a puppy), to simulate
         * an improperly added image that we will later remove from the list.
         * Note: each image can have only one entry in a list, so sample4.png
         * will throw an exception when we try to add it with a new label.
        */
        public final Data Swimsuit = new Data();

        /*
         * The set of images to subsequently remove from the list.
        */
        public final String[] Corrections = new String[] {
            "https://moderatorsampleimages.blob.core.windows.net/samples/sample16.png"
        };

        public Images() {
            this.Sports.Label = Labels.Sports;
            this.Sports.Urls = new String[]{
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample4.png",
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample6.png",
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample9.png"
            };
            this.Swimsuit.Label = Labels.Swimsuit;
            this.Swimsuit.Urls = new String[] {
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample1.jpg",
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample3.png",
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample4.png",
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample16.png"
            };
        }
    }

    private static Images images = new Images();

    /*
     * The images to match against the image list.
     * Samples 1 and 4 should scan as matches; samples 5 and 16 should not.
    */
    private static final String[] ImagesToMatch = new String[] {
        "https://moderatorsampleimages.blob.core.windows.net/samples/sample1.jpg",
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample4.png",
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample5.png",
                "https://moderatorsampleimages.blob.core.windows.net/samples/sample16.png"
    };

    /*
     * A dictionary that tracks the ID assigned to each image URL when
     * the image is added to the list.
     * Indexed by URL.
    */
    private static final Hashtable<String, Integer> ImageIdMap =
            new Hashtable<String, Integer>();

    public static void execute(ContentModeratorClientImpl client) throws InterruptedException, IOException {
        // Create a custom image list and record the ID assigned to it.

        ImageListInner creationResult = CreateCustomList(client);
        if (creationResult.id() != null) {
            // Cache the ID of the new image list.
            int listId = creationResult.id();

            // Perform various operations using the image list.
            AddImages(client, listId, images.Sports.Urls, images.Sports.Label);
            AddImages(client, listId, images.Swimsuit.Urls, images.Swimsuit.Label);

            GetAllImageIds(client, listId);
            UpdateListDetails(client, creationResult);
            GetListDetails(client, listId);

            // Be sure to refresh search index
            RefreshSearchIndex(client, listId);

            // WriteLine();
            System.out.println(
                    String.format("Waiting %f minutes to allow the server time to propagate the index changes.", latencyDelay));
            Thread.sleep((int) (latencyDelay * 60 * 1000));

            // Match images against the image list.
            MatchImages(client, ImagesToMatch);

            // Remove images
            RemoveImages(client, listId, images.Corrections);

            // Be sure to refresh search index
            RefreshSearchIndex(client, listId);

            System.out.println();
            System.out.println(
                    String.format("Waiting %f minutes to allow the server time to propagate the index changes.", latencyDelay));
            Thread.sleep((int) (latencyDelay * 60 * 1000));

            // Match images again against the image list. The removed image should not get matched.
            MatchImages(client, ImagesToMatch);

            // Delete all images from the list.
            DeleteAllImages(client, listId);

            // Delete the image list.
            DeleteCustomList(client, listId);

            // Verify that the list was deleted.
            GetAllListIds(client);
            System.out.println();
        }
    }

    /*
     * Creates the custom list.
     * @param client The Content Moderator client
     * @return The response object from the operation.
    */
    private static ImageListInner CreateCustomList(ContentModeratorClientImpl client) throws InterruptedException {
        // Create the request body.
        BodyInner listDetails = new BodyInner();
        listDetails.withName("MyList");
        listDetails.withDescription("A sample list");
        listDetails.withMetadata(new BodyMetadata());
        listDetails.metadata().withKeyOne("Acceptable");
        listDetails.metadata().withKeyOne("Potentially racy");

        System.out.println("Creating list {listDetails.Name}.");
        ImageListInner result = client.listManagementImageLists().create(
                "application/json", listDetails);
        Thread.sleep(throttleRate);
        System.out.println("Response:");
        System.out.println("Image id: " + result.id());
        System.out.println("Image name: " + result.name());
        System.out.println("Image description: " + result.description());
        return result;
    }

    /*
     * Adds images to an image list.
     * Images are assigned content IDs when they are added to the list.
     * Track the content ID assigned to each image.
     * @param client The Content Moderator client.
     * @param listId The list identifier.
     * @param imagesToAdd The images to add.
     * @param label The label to apply to each image.
    */
    private static void AddImages(
            ContentModeratorClientImpl client,
            int listId,
            String[] imagesToAdd, String label) throws InterruptedException {
        for (String imageUrl : imagesToAdd)
        {
            System.out.println();
            System.out.println("Adding " + imageUrl + " to list " + listId + " with label " + label + ".");
            try
            {
                BodyModelInner bodyModelInner = new BodyModelInner();
                bodyModelInner.withDataRepresentation("URL");
                bodyModelInner.withValue(imageUrl);
                ImageInner result = client.listManagementImages().addImageUrlInput(
                        listId + "",
                        "application/json",
                        bodyModelInner,
                        null,
                        label);

                ImageIdMap.put(imageUrl, Integer.parseInt(result.contentId()));
                System.out.println("Response:");
                System.out.println("Image Content Id: " + result.contentId());
                System.out.println("Image Tracking Id: " + result.trackingId());
                System.out.println("Image Status: " + result.status());
            }
            catch (Exception ex)
            {
                System.out.println("Unable to add image to list. Caught {ex.GetType().FullName}: {ex.Message}");
            }
            finally
            {
                Thread.sleep(throttleRate);
            }
        }
    }

    /*
     * Removes images from an image list.
     * Images are assigned content IDs when they are added to the list.
     * Use the content ID to remove the image.
     * @param client The Content Moderator client.
     * @param listId The list identifier.
     * @param imagesToRemove The images to remove.
    */
    private static void RemoveImages(
            ContentModeratorClientImpl client, int listId,
            String[] imagesToRemove) throws InterruptedException {
        for (String imageUrl : imagesToRemove)
        {
            if (!ImageIdMap.containsKey(imageUrl)) continue;
            int imageId = ImageIdMap.get(imageUrl);

            System.out.println();
            System.out.println("Removing entry for {imageUrl} (ID = {imageId}) from list {listId}.");

            String result = client.listManagementImages().deleteImage(
                    listId + "", imageId + "");
            Thread.sleep(throttleRate);

            ImageIdMap.remove(imageUrl);

            System.out.println("Response:");
            System.out.println("Result: " + result);
        }
    }

    /*
     * Gets all image IDs in an image list.
     * @param client The Content Moderator client.
     * @param listId The list identifier.
     * @return The response object from the operation.
    */
    private static ImageIdsInner GetAllImageIds(
            ContentModeratorClientImpl client, int listId) throws InterruptedException {
        System.out.println();
        System.out.println("Getting all image IDs for list {listId}.");
        ImageIdsInner result = client.listManagementImages().getAllImageIds(String.valueOf(listId));
        Thread.sleep(throttleRate);
        System.out.println("Response:");
        System.out.println("Content Source: " + result.contentSource());
        System.out.println("Tracking Id: " + result.trackingId());
        System.out.println("Status: " + result.status());
        for(Integer contentId : result.contentIds()) {
            System.out.println("    Content Ids: " + contentId);
        }

        return result;
    }

    /*
     * Updates the details of an image list.
     * @param client The Content Moderator client.
     * @param listId The list identifier.
     * @return The response object from the operation.
    */
    private static ImageListInner UpdateListDetails(
            ContentModeratorClientImpl client,
            ImageListInner imageList) throws InterruptedException {
        System.out.println();
        System.out.println("Updating details for list {listId}.");

        BodyInner listDetails = new BodyInner();
        listDetails.withName( "Swimsuits and sports");
        listDetails.withDescription(imageList.description());
        listDetails.withMetadata(new BodyMetadata());
        listDetails.metadata().withKeyOne(imageList.metadata().keyOne());
        listDetails.metadata().withKeyOne(imageList.metadata().keyTwo());
        ImageListInner result = client.listManagementImageLists().update(
                imageList.id().toString(),
                "application/json",
                listDetails);
        Thread.sleep(throttleRate);

        System.out.println("Response:");
        System.out.println("Image id: " + result.id());
        System.out.println("Image name: " + result.name());
        System.out.println("Image description: " + result.description());
        return result;
    }

    /*
     * Gets the details for an image list.
     * @param client The Content Moderator client.
     * @param listId The list identifier.
     * @return The response object from the operation.
    */
    private static ImageListInner GetListDetails(
            ContentModeratorClientImpl client, int listId) throws InterruptedException {
        System.out.println();
        System.out.println("Getting details for list {listId}.");

        ImageListInner result = client.listManagementImageLists().getDetails(
                String.valueOf(listId));
        Thread.sleep(throttleRate);

        System.out.println("Response:");
        System.out.println("Image id: " + result.id());
        System.out.println("Image name: " + result.name());
        System.out.println("Image description: " + result.description());
        return result;
    }

    /*
     * Refreshes the search index for an image list.
     * @param client The Content Moderator client.
     * @param listId The list identifier.
     * @return The response object from the operation.
    */
    private static RefreshIndexInner RefreshSearchIndex(
            ContentModeratorClientImpl client, int listId) throws InterruptedException {
        System.out.println();
        System.out.println("Refreshing the search index for list {listId}.");

        RefreshIndexInner result = client.listManagementImageLists().refreshIndexMethod(
                String.valueOf(listId));
        Thread.sleep(throttleRate);
        System.out.println("Response:");
        System.out.println("Image id: " + result.contentSourceId());
        System.out.println("Image name: " + result.trackingId());
        return result;
    }

    /*
     * Matches images against an image list.
     * @param client The Content Moderator client.
     * @param listId The list identifier.
     * @param imagesToMatch The images to screen.
    */
    private static void MatchImages(
            ContentModeratorClientImpl client,
            String[] imagesToMatch) throws InterruptedException {
        for (String imageUrl : imagesToMatch)
        {
            System.out.println();
            System.out.println("Matching image {imageUrl} against list {listId}.");

            BodyModelInner bodyModel = new BodyModelInner();
            bodyModel.withDataRepresentation("URL");
            bodyModel.withValue(imageUrl);
            MatchResponseInner result = client.imageModerations().matchUrlInput(
                    "application/json",
                    bodyModel);
            Thread.sleep(throttleRate);
            System.out.println("Response:");
            System.out.println("Cache id: " + result.cacheID());
            System.out.println("Tracking Id: " + result.trackingId());
            System.out.println("Is Match: " + result.isMatch());
            System.out.println("Status: " + result.status());
        }
    }

    /*
     * Deletes all images from an image list.
     * @param client The Content Modertor client.
     * @param listId The list identifier.
    */
    private static void DeleteAllImages(
            ContentModeratorClientImpl client, int listId) throws InterruptedException {
        System.out.println();
        System.out.println("Deleting all images from list {listId}.");
        String result = client.listManagementImages().deleteAllImages(
                String.valueOf(listId));
        Thread.sleep(throttleRate);
        System.out.println("Response:");
        System.out.println(result);
    }

    /*
     * Deletes an image list.
     * @param client The Content Moderator client.
     * @param listId The list identifier.
    */
    private static void DeleteCustomList(
            ContentModeratorClientImpl client, int listId) throws InterruptedException {
        System.out.println();
        System.out.println("Deleting list {listId}.");

        String result = client.listManagementImageLists().delete(
                String.valueOf(listId));
        Thread.sleep(throttleRate);
        System.out.println("Response:");
        System.out.println(result);
    }

    /*
     * Gets all list identifiers for the client.
     * @param client The Content Moderator client.
     * @return The response object from the operation.
    */
    private static List<ImageListInner> GetAllListIds(
            ContentModeratorClientImpl client) throws InterruptedException {
        System.out.println();
        System.out.println("Getting all image list IDs.");

        List<ImageListInner> result = client.listManagementImageLists().getAllImageLists();
        Thread.sleep(throttleRate);
        System.out.println("Response:");
        System.out.println("Size of image list: " + result.size());
        return result;
    }
}
