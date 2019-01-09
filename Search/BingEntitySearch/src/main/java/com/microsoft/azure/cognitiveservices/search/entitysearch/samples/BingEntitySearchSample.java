/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.cognitiveservices.search.entitysearch.samples;

import com.microsoft.azure.cognitiveservices.search.entitysearch.BingEntitySearchAPI;
import com.microsoft.azure.cognitiveservices.search.entitysearch.BingEntitySearchManager;
import com.microsoft.azure.cognitiveservices.search.entitysearch.models.EntityScenario;
import com.microsoft.azure.cognitiveservices.search.entitysearch.models.Error;
import com.microsoft.azure.cognitiveservices.search.entitysearch.models.ErrorResponse;
import com.microsoft.azure.cognitiveservices.search.entitysearch.models.ErrorResponseException;
import com.microsoft.azure.cognitiveservices.search.entitysearch.models.Place;
import com.microsoft.azure.cognitiveservices.search.entitysearch.models.SearchOptionalParameter;
import com.microsoft.azure.cognitiveservices.search.entitysearch.models.SearchResponse;
import com.microsoft.azure.cognitiveservices.search.entitysearch.models.Thing;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample code for searching entities using Bing Entiy Search, an Azure Cognitive Service.
 *  - Search the entities for "Satya Nadella" and print out a short description.
 *  - Search the entities and handle disambiguation results for an ambiguous query "William Gates".
 *  - Search the entities for a single store "Microsoft Store" and print out its phone number.
 *  - Search the entities for a list of restaurants "seattle restaurants" and present their names and phone numbers.
 *  - Trigger a bad request and shows how to read the error response.
 */
public class BingEntitySearchSample {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing Entity Search API client
     * @return true if sample runs successfully
     */
    public static boolean runSample(BingEntitySearchAPI client) {
        try {

            //=============================================================
            // This will look up a single entity "Satya Nadella" and print out a short description of it.

            SearchResponse entityData = client.bingEntities().search()
                .withQuery("Satya Nadella")
                .withMarket("en-us")
                .execute();

            if (entityData.entities().value().size() > 0) {
                // find the entity that represents the dominant one
                List<Thing> entrys = entityData.entities().value();
                boolean hasDominateEntry = false;
                for (Thing thing : entrys) {
                    if (thing.entityPresentationInfo().entityScenario() == EntityScenario.DOMINANT_ENTITY) {
                        System.out.println("Searched for \"Satya Nadella\" and found a dominant entity with this description:");
                        System.out.println(thing.description());
                        hasDominateEntry = true;
                        break;
                    }
                }
                if (!hasDominateEntry) {
                    System.out.println("Couldn't find main entity Satya Nadella!");
                }
            } else {
                System.out.println("Didn't see any data..");
            }


            //=============================================================
            // This will handle disambiguation results for an ambiguous query "harry potter".

            int maxTries = 2;
            for (int i = 1; i <= 2; i++) {
                try {
                    entityData = client.bingEntities().search()
                            .withQuery("William Gates")
                            .withMarket("en-us")
                            .execute();

                    if (entityData.entities().value().size() > 0) {
                        // find the entity that represents the dominant one
                        List<Thing> entrys = entityData.entities().value();
                        boolean hasDominateEntry = false;
                        for (Thing thing : entrys) {
                            if (thing.entityPresentationInfo().entityScenario() == EntityScenario.DOMINANT_ENTITY) {
                                System.out.println("Searched for \"William Gates\" and found a dominant entity with this description:");
                                System.out.println(thing.description());
                                hasDominateEntry = true;
                                break;
                            }
                        }
                        if (!hasDominateEntry) {
                            System.out.println("Couldn't find main entity \"William Gates\"!");
                        } else {
                            List<Thing> dominateEntries = new ArrayList<>();
                            for (Thing thing : entrys) {
                                if (thing.entityPresentationInfo().entityScenario() == EntityScenario.DISAMBIGUATION_ITEM) {
                                    dominateEntries.add(thing);
                                }
                            }
                            if (dominateEntries.size() > 1) {
                                System.out.println("This query is pretty ambiguous and can be referring to multiple things. Did you mean one of these:");
                                for (Thing thing : dominateEntries) {
                                    System.out.format("\t%s (%s)\n", thing.name(), thing.entityPresentationInfo().entityTypeDisplayHint());
                                }
                            } else {
                                System.out.println("We didn't find any disambiguation items for William Gates, so we must be certain what you're talking about!");
                            }
                        }
                    } else {
                        System.out.println("Didn't see any data..");
                    }
                    break;
                } catch (ErrorResponseException e) {
                    System.out.println(
                            String.format("Exception occurred when searching for William Gates, status code %s with reason %s.", e.response().code(), e.response().message()));

                    if (e.response().code() == 429) {
                        System.out.println("You are getting a request exceeded error because you are using the free tier for this sample. Code will wait 1 second before resending request");
                    } else {
                        throw e;
                    }

                    if (i == maxTries) {
                        throw e;
                    }
                    Thread.sleep(1000);
                }
            }

            //=============================================================
            // This will look up a single store "Microsoft Store" and print out its phone number.

            System.out.println("Searching for \"Microsoft Store\"");
            for (int i = 1; i <= 2; i++) {
                try {
                    entityData = client.bingEntities().search()
                            .withQuery("Microsoft Store")
                            .withMarket("en-us")
                            .execute();

                    if (entityData.places() != null && entityData.places().value().size() > 0) {
                        // Some local entities will be places, others won't be. Depending on the data you want, try to cast to the appropriate schema
                        // In this case, the item being returned is technically a Store, but the Place schema has the data we want (telephone)
                        Place store = (Place) entityData.places().value().get(0);

                        if (store != null) {
                            System.out.println("Searched for \"Microsoft Store\" and found a store with this phone number:");
                            System.out.println(store.telephone());
                        } else {
                            System.out.println("Couldn't find a place!");
                        }
                    } else {
                        System.out.println("Didn't see any data..");
                    }
                    break;
                } catch (ErrorResponseException e) {
                    System.out.println(
                            String.format("Exception occurred when searching for Microsoft Store, status code %s with reason %s.", e.response().code(), e.response().message()));

                    if (e.response().code() == 429) {
                        System.out.println("You are getting a request exceeded error because you are using the free tier for this sample. Code will wait for 1 second before resending request");
                    } else {
                        throw e;
                    }
                    if (i == maxTries) {
                        throw e;
                    }
                    Thread.sleep(1000);
                }
            }


            //=============================================================
            // This will look up a list of restaurants "seattle restaurants" and present their names and phone numbers.
            for (int i = 1; i <= 2; i++) {
                try {
                    SearchResponse restaurants = client.bingEntities().search("",
                            new SearchOptionalParameter()
                                    .withMarket("en-us"));

                    if (restaurants.places() != null && restaurants.places().value().size() > 0) {
                        List<Thing> listItems = new ArrayList<Thing>();
                        for (Thing place : restaurants.places().value()) {
                            if (place.entityPresentationInfo().entityScenario() == EntityScenario.LIST_ITEM) {
                                listItems.add(place);
                            }
                        }

                        if (listItems.size() > 0) {
                            StringBuilder sb = new StringBuilder();

                            for (Thing item : listItems) {
                                Place place = (Place) item;
                                if (place == null) {
                                    System.out.println(String.format("Unexpectedly found something that isn't a place named \"%s\"", item.toString()));
                                    continue;
                                }

                                sb.append(String.format(",%s (%s) ", place.name(), place.telephone()));
                            }

                            System.out.println("Ok, we found these places: ");
                            System.out.println(sb.toString().substring(1));
                        } else {
                            System.out.println("Couldn't find any relevant results for \"seattle restaurants\"");
                        }
                    } else {
                        System.out.println("Didn't see any data..");
                    }
                    break;
                } catch (ErrorResponseException e) {
                    System.out.println(
                            String.format("Exception occurred when searching for seattle restaurants, status code %s with reason %s.", e.response().code(), e.response().message()));

                    if (e.response().code() == 429) {
                        System.out.println("You are getting a request exceeded error because you are using the free tier for this sample. Code will wait for 1 second before resending request");
                    } else {
                        throw e;
                    }
                    if (i == maxTries) {
                        throw e;
                    }
                    Thread.sleep(1000);
                }
            }


            //=============================================================
            // This triggers a bad request and shows how to read the error response.
            try {
                SearchResponse errorQuery = client.bingEntities().search()
                    .withQuery("harry potter")
                    .withMarket("no-NO")
                    .execute();
            } catch (ErrorResponseException ex) {
                // The status code of the error should be a good indication of what occurred. However, if you'd like more details, you can dig into the response.
                // Please note that depending on the type of error, the response schema might be different, so you aren't guaranteed a specific error response schema.
                System.out.println(
                        String.format("Exception occurred, status code %s with reason %s.", ex.response().code(), ex.response().message()));

                // Attempting to parse the content as an ErrorResponse
                ErrorResponse issue = ex.body();

                if (issue != null && issue.errors() != null && issue.errors().size() > 0 ) {
                    Error error = issue.errors().get(0);
                    System.out.println(
                            String.format("Turns out the issue is parameter \"%s\" has an invalid value \"%s\". Detailed message is \"%s\"",
                                    error.parameter(), error.value(), error.message()));
                }
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

            BingEntitySearchAPI client = BingEntitySearchManager.authenticate(subscriptionKey);


            runSample(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
