---
services: cognitive-services, bing-news-search
platforms: java
author: milismsft
---

# Bing News Search SDK Sample ##

Sample [code](https://github.com/Azure-Samples/cognitive-services-java-sdk-samples/blob/master/Search/BingNewsSearch/src/main/java/com/microsoft/azure/cognitiveservices/search/newssearch/samples/BingNewsSearchSample.java) for searching news using Bing News Search, an Azure Cognitive Service.
- Search the news for "Quantum  Computing" with market and count settings and print out the results.
- Search the news for "Artificial Intelligence" with market, freshness and sort-by settings and print out the results.
- Search the news category "Movie and TV Entertainment" with market and safe search settings and print out the results.
- Search the news trending topics with market and print out the results.


## Features

This project framework provides examples for the **Bing News Search SDK** for the [News Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)

## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Sign up here](https://azure.microsoft.com/en-us/services/cognitive-services/directory/) by navigating to the **Search** services and acquiring a Bing News Search API key. You can get a trial key for **free** which will expire after 30 days.
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingNewsSearch
3. Either set a system environment variable named `AZURE_BING_SAMPLES_API_KEY` to your cognitive Services API key, or copy your key into the program. If you are going to set the AZURE_BING_SAMPLES_API_KEY environment variable, make sure to set it based on your OS, then reopen your command prompt or IDE. If not, you might get an API key not found exception.
4. mvn compile exec:java

## More information ##
[Bing News Search API Documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-news-search/)

[Bing News Search API Documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-news-search/)
[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can get a FREE trial account [here](http://go.microsoft.com/fwlink/?LinkId=330212)

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.