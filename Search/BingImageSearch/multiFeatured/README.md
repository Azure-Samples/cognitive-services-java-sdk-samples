---
services: cognitive-services, bing-image-search
platforms: java
author: milismsft
---

# Bing Image Search SDK Sample ##

Sample [code](https://github.com/Azure-Samples/cognitive-services-java-sdk-samples/blob/master/Search/BingImageSearch/multiFeatured/src/main/java/com/microsoft/azure/cognitiveservices/search/imagesearch/samples/BingImageSearchSample.java) for searching images using Bing Image Search, an Azure Cognitive Service.
- Search images for "canadian rockies" then verify number of results and print out pivot suggestion and query expansion.
- Search images for "studio ghibli", filtered for animated GIFs and wide aspect and print out insightsToken, thumbnail url and web url.
- Search for trending images then print out categories and tiles.
- Search images for "degas" and then search the image details of the first item in result list.


## Features

This project framework provides examples for the **Bing Image Search SDK** for the [Image Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)

## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Create a new Azure account, and try Cognitive Services for free.](https://azure.microsoft.com/free/cognitive-services/)
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingImageSearch
3. Either set a system environment variable named `AZURE_BING_SAMPLES_API_KEY` to your cognitive Services API key, or copy your key into the program. If you are going to set the AZURE_BING_SAMPLES_API_KEY environment variable, make sure to set it based on your OS, then reopen your command prompt or IDE. If not, you might get an API key not found exception.
4. mvn compile exec:java

## More information ##

[Search for images using the Bing Image Search SDK and Java](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-image-search/image-sdk-java-quickstart)
[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can visit [the Microsoft Cognitive Services Web site](https://azure.microsoft.com/free/cognitive-services/), create a new Azure account, and try Cognitive Services for free.

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
