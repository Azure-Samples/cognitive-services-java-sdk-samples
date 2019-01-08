---
services: cognitive-services, bing-image-search
platforms: java
author: aahill
---

# Java sample for the Bing Image Search SDK Quickstart ##

This [code](https://github.com/Azure-Samples/cognitive-services-java-sdk-samples/blob/master/Search/BingImageSearch/Quickstart/src/main/java/com/microsoft/azure/cognitiveservices/search/imagesearch/samples/BingImageSearchSample.java) sample compliments the [Bing Image Search Quickstart](https://docs.microsoft.com/azure/cognitive-services/bing-image-search/image-sdk-java-quickstart) available on docs.microsoft.com.

This Java application sends an image search query to the Bing Image Search API, parses the JSON response, and displays the URL of the first image returned, along with some other information.

The [Bing Image Search API](https://azure.microsoft.com/services/cognitive-services/bing-image-search-api/) is an Azure Cognitive service, available from Microsoft.  

## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Sign up here](https://azure.microsoft.com/en-us/services/cognitive-services/directory/) by navigating to the **Search** services and acquiring a Bing Image Search API key. You can get a trial key for **free** which will expire after 30 days.
- The latest version of the [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (JDK)
- Maven

You must have a [Cognitive Services API account](https://docs.microsoft.com/azure/cognitive-services/cognitive-services-apis-create-account) with access to the Bing Search APIs. If you don't have an Azure subscription, you can [create an account](https://azure.microsoft.com/try/cognitive-services/?api=bing-web-search-api) for free. Before continuing, You will need the access key provided after activating your free trial, or a paid subscription key from your Azure dashboard.

### Quickstart

After getting the prerequisites above:
1. View the quickstart available [here]([Quickstart: Search for images using the Bing Image Search SDK and Java](https://docs.microsoft.com/azure/cognitive-services/bing-image-search/image-sdk-java-quickstart))
1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingImageSearch/Quickstart
3. Either set a system environment variable named `AZURE_BING_SAMPLES_API_KEY` to your cognitive Services API key, or copy your key into the program. If you are going to set the AZURE_BING_SAMPLES_API_KEY environment variable, make sure to set it based on your OS, then reopen your command prompt or IDE. If not, you might get an API key not found exception.
4. mvn compile exec:java

## More information

* [Quickstart: Search for images using the Bing Image Search SDK and Java](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-image-search/image-sdk-java-quickstart)
* [Java on Azure](http://azure.com/java)
* [The Bing Image Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)

If you don't have a Microsoft Azure subscription you can get a FREE trial account [here](http://go.microsoft.com/fwlink/?LinkId=330212)

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
