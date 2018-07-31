/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.vision.computervision.samples;

import com.google.common.io.ByteStreams;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ImageAnalysis;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ImageTag;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.VisualFeatureTypes;

import java.util.ArrayList;
import java.util.List;


public class ComputerVisionSamples {
    /**
     * Main entry point.
     * @param client the Computer Vision API client object
     * @return true if sample runs successfully
     */
    public static boolean runSample(ComputerVisionClient client) {
        try {

            //=============================================================
            // This will request a description for the image that was sent for analyzing

            System.out.println("Send a resource image to be analyzed");
            List<VisualFeatureTypes> visualFeatureTypes = new ArrayList<>();
            visualFeatureTypes.add(VisualFeatureTypes.DESCRIPTION);
            visualFeatureTypes.add(VisualFeatureTypes.CATEGORIES);
            visualFeatureTypes.add(VisualFeatureTypes.COLOR);
            visualFeatureTypes.add(VisualFeatureTypes.FACES);
            visualFeatureTypes.add(VisualFeatureTypes.IMAGE_TYPE);
            visualFeatureTypes.add(VisualFeatureTypes.TAGS);

            ImageAnalysis imageAnalysis = client.computerVision().analyzeImageInStream()
                .withImage(GetImageBytes("/house.jpg"))
                .withVisualFeatures(visualFeatureTypes)
                .execute();

            if (imageAnalysis == null) {
                System.out.print("Unexpected resul (null) returned by the service for the given image.");
                return false;
            }

            if (imageAnalysis.description() != null && imageAnalysis.description().captions() != null) {
                System.out.println(
                    String.format("The image can be described as: %s\n", imageAnalysis.description().captions().get(0).text()));
            }

            if (imageAnalysis.tags() != null) {
                System.out.println("Tags associated with this image:\nTag\t\tConfidence");
                for (ImageTag tag : imageAnalysis.tags()) {
                    System.out.println(
                        String.format("%s\t\t%s", tag.name(), tag.confidence()));
                }
            }

            if (imageAnalysis.color() != null && imageAnalysis.color().dominantColors() != null) {
                String primaryColors = "";
                for (String color : imageAnalysis.color().dominantColors()) {
                    primaryColors += " " + color;
                }
                System.out.println("\nThe primary colors of this image are:" + primaryColors);
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private static byte[] GetImageBytes(String fileName)
    {
        try {
            return ByteStreams.toByteArray(ComputerVisionSamples.class.getResourceAsStream(fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return new byte[0];
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

            final String apiKey = System.getenv("AZURE_COMPUTERVISION_API_KEY");
            final String endpoint = "https://westus.api.cognitive.microsoft.com";

            if(apiKey == null) {
                throw new Exception("Azure computer vision samples api key not found.");
            }

            ComputerVisionClient client = ComputerVisionManager.authenticate(apiKey)
                .withEndpoint(endpoint);

            runSample(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
