---
services: cognitive-services, bing-video-search
platforms: java
author: milismsft
---

# Bing Video Search SDK Sample ##

Sample [code](https://github.com/Azure-Samples/cognitive-services-java-sdk-samples/tree/master/Search/BingVideoSearch) for searching news using Bing Video Search, an Azure Cognitive Service.
- Search videos for "SwiftKey" and print out id, name and url.
- Search videos for "Bellevue Trailer" that is free, short and 1080p resolution and print out id, name and url.
- Search for trending videos then verify banner tiles and categories.
- Search videos for "Bellevue Trailer" and then search for detail information of the first video.

## Features

This project framework provides examples for the **Bing Video Search SDK** for the [Video Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)

## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Create a new Azure account, and try Cognitive Services for free.](https://azure.microsoft.com/free/cognitive-services/)
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingVideoSearch
3. Set a system environment variable named `BING_SEARCH_V7_SUBSCRIPTION_KEY` with your subscription key, 
   then reopen your command prompt or IDE. If not, you might get an API key not found exception.
4. mvn compile exec:java

## More information ##
[Bing Video Search Documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/Bing-Video-Search/)

[Bing Video Search Documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/Bing-Video-Search/)
[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can visit [the Microsoft Cognitive Services Web site](https://azure.microsoft.com/free/cognitive-services/), create a new Azure account, and try Cognitive Services for free.

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
