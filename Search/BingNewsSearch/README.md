---
services: Cognitive Services Bing News Search
platforms: java
author: milismsft
---

# Bing News Search SDK Sample ##

This Azure Cognitive Service sample will show you how to get up and running using the SDK for Bing News Search service. They'll cover a few rudimentary use cases and hopefully express best practices for interacting with the data from these APIs.
- Search the news for "Quantum  Computing" and print out the results.
- 

## Features

This project framework provides examples for the **Bing News Search SDK** for the [News Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)

## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Sign up here](https://azure.microsoft.com/en-us/services/cognitive-services/directory/) by navigating to the **Search** services and acquiring an API key. You can get a trial key for **free** which will expire after 30 days.
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingNewsSearch
3. mvn compile
4. set env variable AZURE_BING_SAMPLES_API_KEY to your cognitive services API key.
5. mvn exec:java

## More information ##

[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can get a FREE trial account [here](http://go.microsoft.com/fwlink/?LinkId=330212)

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.