/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.language.luis.samples;

import com.microsoft.azure.cognitiveservices.language.luis.authoring.LUISAuthoringClient;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.LUISAuthoringManager;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.ApplicationCreateObject;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.ApplicationPublishObject;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.BatchLabelExample;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.CompositeEntityModel;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.EnqueueTrainingResponse;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.EntityLabelObject;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.ExampleLabelObject;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.HierarchicalEntityModel;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.ModelTrainingInfo;
import com.microsoft.azure.cognitiveservices.language.luis.authoring.models.ProductionOrStagingEndpointInfo;
import com.microsoft.azure.cognitiveservices.language.luis.runtime.LuisRuntimeAPI;
import com.microsoft.azure.cognitiveservices.language.luis.runtime.LuisRuntimeManager;
import com.microsoft.azure.cognitiveservices.language.luis.runtime.models.EntityModel;
import com.microsoft.azure.cognitiveservices.language.luis.runtime.models.LuisResult;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Sample code for spell checking using Language Understanding (LUIS), an Azure Cognitive Service.
 *  - Create an application with two entities.
 *  - Create a "Flight" composite entity including "Class" and "Destination"
 *  - Create a new "FindFlights" intent with two utterances and build two EntityLabel Object
 *  - Train and publish the application
 *  - Execute a LUIS prediction for a "find second class flight to new york" utterance and print the results
 *  - Execute a LUIS prediction for a "find flights to London in first class" utterance and print the results
 */
public class LuisSample {
    static String luisAuthoringKey;
    static String defaultApplicationName = String.format("Contoso-%d", DateTime.now().getMillis());
    static String versionId = "0.1";
    static UUID appId;
    static String appEndpoint;

    /**
     * Main function which runs the authoring part of the sample.
     *
     * @param authoringClient instance of the LUIS Authoring API client
     * @return true if sample runs successfully
     */
    public static boolean runLuisAuthoringSample(LUISAuthoringClient authoringClient) {
        try {

            //=============================================================
            // This will create a LUIS application
            System.out.println("Creating a new application for " + defaultApplicationName);
            appId = authoringClient.apps().add(new ApplicationCreateObject()
                .withName(defaultApplicationName)
                .withInitialVersionId(versionId)
                .withDescription("Luis Java sample application")
                .withCulture("en-us")
            );

            System.out.println("Created application " + appId.toString());


            //=============================================================
            // This will add entities into the model
            System.out.println("Creating two new entities:");
            System.out.println("\t\"Destination\" - simple entity will hold the flight destination");
            System.out.println("\t\"Class\" - hierarchical entity will accept \"First\", \"Business\" and \"Economy\" values");

            String destinationName = "Destination";
            UUID destinationId = authoringClient.models().addEntity()
                .withAppId(appId)
                .withVersionId(versionId)
                .withName(destinationName)
                .execute();

            System.out.println("Created simple entity " + destinationName + "with ID " + destinationId.toString());

            String className = "Class";
            UUID classId = authoringClient.models().addHierarchicalEntity(appId, versionId, new HierarchicalEntityModel()
                .withName(className)
                .withChildren(Arrays.asList("First", "Business", "Economy")));

            System.out.println("Created hierarchical entity " + className + "with ID " + classId.toString());


            //=============================================================
            // This will create the "Flight" composite entity including "Class" and "Destination"
            System.out.println("Creating the \"Flight\" composite entity including \"Class\" and \"Destination\".");

            String flightName = "Flight";
            UUID flightId = authoringClient.models().addCompositeEntity(appId, versionId, new CompositeEntityModel()
                .withName(flightName)
                .withChildren(Arrays.asList(className, destinationName)));

            System.out.println("Created composite entity " + flightName + "with ID " + flightId.toString());


            //=============================================================
            // This will create a new "FindFlights" intent including the following utterances
            System.out.println("Creating a new \"FindFlights\" intent with two utterances");
            String utteranceFindEconomyToMadrid = "find flights in economy to Madrid";
            String utteranceFindFirstToLondon = "find flights to London in first class";
            String intentName = "FindFlights";

            UUID intendId = authoringClient.models().addIntent()
                .withAppId(appId)
                .withVersionId(versionId)
                .withName(intentName)
                .execute();

            System.out.println("Created intent " + intentName + "with ID " + intendId.toString());


            //=============================================================
            // This will build an EntityLabel Object
            System.out.println("Building an EntityLabel Object");

            ExampleLabelObject exampleLabelObject1 = new ExampleLabelObject()
                .withText(utteranceFindEconomyToMadrid)
                .withIntentName(intentName)
                .withEntityLabels(Arrays.asList(
                    getEntityLabelObject(utteranceFindEconomyToMadrid, "Flight", "economy to Madrid"),
                    getEntityLabelObject(utteranceFindEconomyToMadrid, "Destination", "Madrid"),
                    getEntityLabelObject(utteranceFindEconomyToMadrid, "Class", "economy")
                ));
            ExampleLabelObject exampleLabelObject2 = new ExampleLabelObject()
                .withText(utteranceFindFirstToLondon)
                .withIntentName(intentName)
                .withEntityLabels(Arrays.asList(
                    getEntityLabelObject(utteranceFindFirstToLondon, "Flight", "London in first class in first class"),
                    getEntityLabelObject(utteranceFindFirstToLondon, "Destination", "London in first class"),
                    getEntityLabelObject(utteranceFindFirstToLondon, "Class", "first")
                ));

            List<BatchLabelExample> utterancesResult = authoringClient.examples()
                .batch(appId, versionId, Arrays.asList(exampleLabelObject1, exampleLabelObject2));

            System.out.println("Utterances added to the " + intentName + " intent");


            //=============================================================
            // This will start training the application.
            System.out.println("Training the application");

            EnqueueTrainingResponse trainingResult = authoringClient.trains().trainVersion(appId, versionId);
            boolean isTrained = trainingResult.status().equals("UpToDate");

            while (!isTrained) {
                Thread.sleep(1000);
                List<ModelTrainingInfo> status = authoringClient.trains().getStatus(appId, versionId);
                isTrained = true;
                for (ModelTrainingInfo modelTrainingInfo : status) {
                    if (!modelTrainingInfo.details().status().equals("UpToDate") && !modelTrainingInfo.details().status().equals("Success")) {
                        isTrained = false;
                        break;
                    }
                }
            }


            //=============================================================
            // This will start publishing the application.
            System.out.println("Publishing the application");
            ProductionOrStagingEndpointInfo publishResult = authoringClient.apps().publish(appId, new ApplicationPublishObject()
                .withVersionId(versionId)
                .withIsStaging(false)
                .withRegion("westus")
            );

            appEndpoint = publishResult.endpointUrl() + "?subscription-key=" + luisAuthoringKey + "&q=";

            System.out.println("Your app is published. You can now go to test it on " + appEndpoint);

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        return false;
    }

    static EntityLabelObject getEntityLabelObject(String utterance, String entityName, String value) {
        return new EntityLabelObject()
            .withEntityName(entityName)
            .withStartCharIndex(utterance.indexOf(value))
            .withEndCharIndex(utterance.indexOf(value) + value.length());
    }


    /**
     * Main function which runs the runtime part of the sample.
     *
     * @param runtimeClient instance of the LUIS Runtime API client
     * @return true if sample runs successfully
     */
    public static boolean runLuisRuntimeSample(LuisRuntimeAPI runtimeClient) {
        try {

            appId = UUID.fromString("af93a1ca-1566-4b80-a645-49678345f958");
            //=============================================================
            // This will execute a LUIS prediction for a "find second class flight to new york" utterance

            String query = "find second class flight to new york";
            System.out.println("Executing query: " + query);

            LuisResult predictionResult = runtimeClient.predictions().resolve()
                .withAppId(appId.toString())
                .withQuery(query)
                .execute();

            if (predictionResult != null && predictionResult.topScoringIntent() != null) {
                System.out.format("Detected intent \"%s\" with the score %f%%\n", predictionResult.topScoringIntent().intent(), predictionResult.topScoringIntent().score() * 100);
                if (predictionResult.entities() != null && predictionResult.entities().size() > 0) {
                    for (EntityModel entityModel : predictionResult.entities()) {
                        System.out.format("\tFound entity \"%s\" with type %s\n", entityModel.entity(), entityModel.type());
                    }
                } else {
                    System.out.println("\tNo entities were found.");
                }
            } else {
                System.out.println("Intent not found.");
            }

            //=============================================================
            // This will execute a LUIS prediction for a "find flights to London in first class" utterance

            query = "find flights to London in first class";
            System.out.println("Executing query: " + query);

            predictionResult = runtimeClient.predictions().resolve()
                .withAppId(appId.toString())
                .withQuery(query)
                .execute();

            if (predictionResult != null && predictionResult.topScoringIntent() != null) {
                System.out.format("Detected intent \"%s\" with the score %f%%\n", predictionResult.topScoringIntent().intent(), predictionResult.topScoringIntent().score() * 100);
                if (predictionResult.entities() != null && predictionResult.entities().size() > 0) {
                    for (EntityModel entityModel : predictionResult.entities()) {
                        System.out.format("\tFound entity \"%s\" with type %s\n", entityModel.entity(), entityModel.type());
                    }
                } else {
                    System.out.println("\tNo entities were found.");
                }
            } else {
                System.out.println("Intent not found.");
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

            luisAuthoringKey = System.getenv("AZURE_LUIS_AUTHORING_KEY");

            LUISAuthoringClient authoringClient = LUISAuthoringManager
                .authenticate(com.microsoft.azure.cognitiveservices.language.luis.authoring.EndpointAPI.US_WEST, luisAuthoringKey);
            LuisRuntimeAPI runtimeClient = LuisRuntimeManager
                .authenticate(com.microsoft.azure.cognitiveservices.language.luis.runtime.EndpointAPI.US_WEST, luisAuthoringKey);

            runLuisAuthoringSample(authoringClient);
            runLuisRuntimeSample(runtimeClient);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}