---
services: cognitive-services, bing-auto-suggest
platforms: java
author: milismsft
---

# Bing Auto Suggest SDK Sample ##

Sample code for custom searching using Bing Auto Suggest, an Azure Cognitive Service.
- Search for "Satya Nadella" and print out the first group of suggestions returned from the service.


## Features

This project framework provides examples for the **Bing Auto Suggest SDK** for the [Auto Suggest API](https://azure.microsoft.com/en-us/services/cognitive-services/)

## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Create a new Azure account, and try Cognitive Services for free.](https://azure.microsoft.com/free/cognitive-services/)
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingAutoSuggest
3. set env variable BING_AUTOSUGGEST_SUBSCRIPTION_KEY to your cognitive services API key.
4. set env variable AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID to your custom configuration id.
5. mvn compile exec:java

## More information ##

[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can visit [the Microsoft Cognitive Services Web site](https://azure.microsoft.com/free/cognitive-services/), create a new Azure account, and try Cognitive Services for free.

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
