/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.contentmoderator.samples;

import com.microsoft.azure.cognitiveservices.contentmoderator.implementation.ContentModeratorClientImpl;
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
    public static ContentModeratorClientImpl getClient(final String subscriptionKey) {
        return new ContentModeratorClientImpl(
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

    static String readFileContents(String filePath) throws IOException {
        // Load the input text.
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader inputStream =
                     new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while ((line = inputStream.readLine()) != null)
            {
                buffer.append(line);
            }
        }

        return buffer.toString();
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
