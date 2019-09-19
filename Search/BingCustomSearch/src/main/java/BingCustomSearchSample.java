// <imports>
package main.java;

import com.microsoft.azure.cognitiveservices.search.customsearch.BingCustomSearchAPI;
import com.microsoft.azure.cognitiveservices.search.customsearch.BingCustomSearchManager;
import com.microsoft.azure.cognitiveservices.search.customsearch.models.SearchResponse;
import com.microsoft.azure.cognitiveservices.search.customsearch.models.WebPage;
// </imports>

public class BingCustomSearchSample {

    // <runSample>
    public static boolean runSample(BingCustomSearchAPI client, String customConfigId) {
        try {
    
            // This will search for "Xbox" using Bing Custom Search 
            //and print out name and url for the first web page in the results list
    
            System.out.println("Searching for Query: \"Xbox\"");
            SearchResponse webData = client.bingCustomInstances().search()
                .withCustomConfig(customConfigId != null ? Long.valueOf(customConfigId) : 0)
                .withQuery("Xbox")
                .withMarket("en-us")
                .execute();
    
            if (webData != null && webData.webPages() != null && webData.webPages().value().size() > 0)
            {
                // find the first web page
                WebPage firstWebPagesResult = webData.webPages().value().get(0);
    
                if (firstWebPagesResult != null) {
                    System.out.println(String.format("Webpage Results#%d", webData.webPages().value().size()));
                    System.out.println(String.format("First web page name: %s ", firstWebPagesResult.name()));
                    System.out.println(String.format("First web page URL: %s ", firstWebPagesResult.url()));
                } else {
                    System.out.println("Couldn't find web results!");
                }
            } else {
                System.out.println("Didn't see any Web data..");
            }
    
            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        return false;
    }
    // </runSample>
    
    // <main>
    public static void main(String[] args) {
        try {
    
            // Set the BING_CUSTOM_SEARCH_SUBSCRIPTION_KEY and AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID environment variables, 
            // then reopen your command prompt or IDE. If not, you may get an API key not found exception.
            final String subscriptionKey = System.getenv("BING_CUSTOM_SEARCH_SUBSCRIPTION_KEY");
            // If you do not have a customConfigId, you can also use 1 as your value when setting your environment variable.
            final String customConfigId = System.getenv("AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID");
    
            BingCustomSearchAPI client = BingCustomSearchManager.authenticate(subscriptionKey);
    
            runSample(client, customConfigId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    // </main>
}