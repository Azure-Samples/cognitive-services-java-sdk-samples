/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.bingsearch.samples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microsoft.azure.cognitiveservices.entitysearch.*;
import com.microsoft.azure.cognitiveservices.entitysearch.implementation.EntitySearchAPIImpl;
import com.microsoft.azure.cognitiveservices.entitysearch.implementation.SearchResponseInner;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Azure bing entity search sample.
 */
@JsonIgnoreProperties
public final class EntitySearchSamples {
    /**
     * Makes an instance of the EntitySearchAPIImpl.
     * @param subscriptionKey cognitive services bing subscription key
     * @return EntitySearchAPIImpl instance
     */
    public static EntitySearchAPIImpl getClient(final String subscriptionKey) {
        return new EntitySearchAPIImpl("https://api.cognitive.microsoft.com/bing/v7.0/",
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
     * This will look up a single entity (tom cruise) and print out a short description about them
     * @param subscriptionKey cognitive services subscription key
     */
    public static void dominantEntityLookup(final String subscriptionKey)
    {
        try
        {
            EntitySearchAPIImpl client = getClient(subscriptionKey);
            SearchResponseInner entityData = client.entities().search(
                    "tom cruise", null, null, null, null, null, null, "en-us", null, null, SafeSearch.STRICT, null);

            if (entityData.entities().value().size() > 0)
            {
                // find the entity that represents the dominant one
                List<Thing> entrys = entityData.entities().value();
                Thing dominateEntry = null;
                for(Thing thing : entrys) {
                    if(thing.entityPresentationInfo().entityScenario() == EntityScenario.DOMINANT_ENTITY) {
                        System.out.println("Searched for \"Tom Cruise\" and found a dominant entity with this description:");
                        System.out.println(thing.description());
                        break;
                    }
                }

                if(dominateEntry == null)
                {
                     System.out.println("Couldn't find main entity tom cruise!");
                }
            }
            else
            {
                 System.out.println("Didn't see any data..");
            }
        }
        catch (ErrorResponseException ex)
        {
             System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }

    /**
     * This will handle disambiguation results for an ambiguous query (harry potter)
     * @param subscriptionKey cognitive services subscription key
     */
    public static void handlingDisambiguation(String subscriptionKey)
    {
        try
        {
            EntitySearchAPIImpl client = getClient(subscriptionKey);
            SearchResponseInner entityData = client.entities().search(
                    "harry potter", null, null, null, null, null, null, "en-us", null, null, SafeSearch.STRICT, null);
            if (entityData.entities().value().size() > 0)
            {
                // find the entity that represents the dominant one
                List<Thing> entrys = entityData.entities().value();
                Thing dominateEntry = null;
                List<Thing> disambigEntities = new ArrayList<Thing>();
                for(Thing thing : entrys) {
                    if(thing.entityPresentationInfo().entityScenario() == EntityScenario.DOMINANT_ENTITY) {
                        System.out.println("Searched for \"Tom Cruise\" and found a dominant entity with this description:");
                        System.out.println(String.format("Searched for \"harry potter\" and found a dominant entity with type hint \"%s\" with this description:",
                                thing.entityPresentationInfo().entityTypeDisplayHint()));
                        System.out.println(thing.description());
                    }

                    if(thing.entityPresentationInfo().entityScenario() == EntityScenario.DISAMBIGUATION_ITEM) {
                        disambigEntities.add(thing);
                        System.out.println("Searched for \"Tom Cruise\" and found a dominant entity with this description:");
                        System.out.println(thing.description());
                    }

                }

                if (dominateEntry == null)
                {
                     System.out.println("Couldn't find a reliable dominant entity for harry potter!");
                }

                if (disambigEntities.size() > 0)
                {
                     System.out.println();
                     System.out.println("This query is pretty ambiguous and can be referring to multiple things. Did you mean one of these: ");

                    StringBuilder sb = new StringBuilder();

                    for (Thing disambig : disambigEntities)
                    {
                        sb.append(String.format(", or %s the %s", disambig.name(), disambig.entityPresentationInfo().entityTypeDisplayHint()));
                    }

                     System.out.println(sb.toString().substring(5) + "?");
                }
                else
                {
                     System.out.println("We didn't find any disambiguation items for harry potter, so we must be certain what you're talking about!");
                }
            }
            else
            {
                 System.out.println("Didn't see any data..");
            }
        }
        catch (ErrorResponseException ex)
        {
             System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }

    /**
     * This will look up a single restaurant (john howie bellevue) and print out its phone number
     * @param subscriptionKey cognitive services subscription key
     */
    public static void restaurantLookup(String subscriptionKey)
    {
        try
        {
            EntitySearchAPIImpl client = getClient(subscriptionKey);
            SearchResponseInner entityData = client.entities().search(
                    "John Howie Bellevue", null, null, null, null, null, null, "en-us", null, null, SafeSearch.STRICT, null);

            if (entityData.places() != null && entityData.places().value().size() > 0)
            {
                // Some local entities will be places, others won't be. Depending on the data you want, try to cast to the appropriate schema
                // In this case, the item being returned is technically a Restaurant, but the Place schema has the data we want (telephone)
                Place restaurant = (Place)entityData.places().value().get(0);

                if (restaurant != null)
                {
                     System.out.println("Searched for \"John Howie Bellevue\" and found a restaurant with this phone number:");
                     System.out.println(restaurant.telephone());
                }
                else
                {
                     System.out.println("Couldn't find a place!");
                }
            }
            else
            {
                 System.out.println("Didn't see any data..");
            }
        }
        catch (ErrorResponseException ex)
        {
             System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }

    /**
     * This will look up a list of restaurants (seattle restaurants) and present their names and phone numbers
     * @param subscriptionKey cognitive services subscription key
     */
    public static void multipleRestaurantLookup(String subscriptionKey)
    {
        try
        {
            EntitySearchAPIImpl client = getClient(subscriptionKey);
            SearchResponseInner restaurants = client.entities().search(
                    "seattle restaurants", null, null, null, null, null, null, "en-us", null, null, SafeSearch.STRICT, null);
            if (restaurants.places() != null && restaurants.places().value().size() > 0)
            {
                List<Thing> listItems = new ArrayList<Thing>();
                for(Thing place : restaurants.places().value()) {
                    if (place.entityPresentationInfo().entityScenario() == EntityScenario.LIST_ITEM) {
                        listItems.add(place);
                    }
                }

                if (listItems.size() > 0)
                {
                    StringBuilder sb = new StringBuilder();

                    for (Thing item : listItems)
                    {
                        Place place = (Place)item;
                        if (place == null)
                        {
                             System.out.println(String.format("Unexpectedly found something that isn't a place named \"%s\"", place.name()));
                            continue;
                        }

                        sb.append(String.format(",%s (%s) ", place.name(), place.telephone()));
                    }

                     System.out.println("Ok, we found these places: ");
                     System.out.println(sb.toString().substring(1));
                }
                else
                {
                     System.out.println("Couldn't find any relevant results for \"seattle restaurants\"");
                }
            }
            else
            {
                 System.out.println("Didn't see any data..");
            }
        }
        catch (ErrorResponseException ex)
        {
             System.out.println("Encountered exception. " + ex.getLocalizedMessage());
        }
    }

    /**
     * This triggers a bad request and shows how to read the error response
     * @param subscriptionKey cognitive services subscription key
     */
    public static void error(String subscriptionKey)
    {
        try
        {
            EntitySearchAPIImpl client = getClient(subscriptionKey);
            SearchResponseInner restaurants = client.entities().search(
                    "harry potter", null, null, null, null, null, null, "en-us", null, null, SafeSearch.STRICT, null);
        }
        catch (ErrorResponseException ex)
        {
            // The status code of the error should be a good indication of what occurred. However, if you'd like more details, you can dig into the response.
            // Please note that depending on the type of error, the response schema might be different, so you aren't guaranteed a specific error response schema.
             System.out.println(
                     String.format("Exception occurred, status code %s with reason %s.", ex.response().code(), ex.response().message()));
        }
    }
}