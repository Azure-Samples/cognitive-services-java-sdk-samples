/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.computervision.samples;

import com.microsoft.azure.cognitiveservices.computervision.*;
import com.microsoft.azure.cognitiveservices.computervision.implementation.ComputerVisionAPIImpl;
import com.microsoft.azure.cognitiveservices.computervision.implementation.ImageAnalysisInner;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Samples {
    /**
     * The key for the bing api.
     */
    public static String apiKey = null;

    /**
     * Makes an instance of the ComputerVisionAPIImpl.
     * @param subscriptionKey cognitive services bing subscription key
     * @return EntitySearchAPIImpl instance
     */
    public static ComputerVisionAPIImpl getClient(final String subscriptionKey) {
        return new ComputerVisionAPIImpl(
                "https://westus.api.cognitive.microsoft.com/vision/v1.0/",
                new ServiceClientCredentials() {
                    @Override
                    public void applyCredentialsFilter(OkHttpClient.Builder builder) {
                        builder.addNetworkInterceptor(
                                new Interceptor() {
                                    @Override
                                    public Response intercept(Interceptor.Chain chain) throws IOException {
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
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            if(apiKey == null) {
                apiKey = System.getenv("AZURE_COMPUTERVISION_API_KEY");
                if(apiKey == null) {
                    throw new Exception("Azure computer vision samples api key not found.");
                }
            }

            // Create a client.
            ComputerVisionAPIImpl client = Samples.getClient(apiKey);
            client.withAzureRegion(AzureRegions.WESTCENTRALUS);
            List<VisualFeatureTypes> visualFeatureTypes = new ArrayList<VisualFeatureTypes>();
            visualFeatureTypes.add(VisualFeatureTypes.DESCRIPTION);
            visualFeatureTypes.add(VisualFeatureTypes.CATEGORIES);
            visualFeatureTypes.add(VisualFeatureTypes.COLOR);
            visualFeatureTypes.add(VisualFeatureTypes.FACES);
            visualFeatureTypes.add(VisualFeatureTypes.IMAGE_TYPE);
            visualFeatureTypes.add(VisualFeatureTypes.TAGS);

            URL url = Samples.class.getClassLoader().getResource("house.jpg");
            File file = new File(url.toURI());
            byte[] fileData = new byte[(int)file.length()];

            // Read image file.
            try(InputStream iStream = new BufferedInputStream(new FileInputStream(file))) {
                iStream.read(fileData);
            }

            ImageAnalysisInner result = client.analyzeImageInStream(
                    fileData,
                    visualFeatureTypes,
                    null,
                    null);

            if(result.description() != null &&
                    result.description().captions() != null) {
                System.out.println(
                        String.format("The image can be described as: %s\n", result.description().captions().get(0).text()));
            }

            System.out.println("Tags associated with this image:\nTag\t\tConfidence");
            for(ImageTag tag : result.tags())
            {
                System.out.println(
                        String.format("%s\t\t%s", tag.name(), tag.confidence()));
            }

            System.out.println(
                    String.format("\nThe primary colors of this image are: %s", String.join(", ", result.color().dominantColors())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
