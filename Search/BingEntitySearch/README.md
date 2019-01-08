---
services: cognitive-services, bing-entity-search
platforms: java
author: milismsft
---

# Bing Entity Search SDK Sample ##

Sample code for searching entities using Bing Entity Search, an Azure Cognitive Service.
- Search the entities for "Satya Nadella" and print out a short description.
- Search the entities and handle disambiguation results for an ambiguous query "William Gates".
- Search the entities for a single store "Microsoft Store" and print out its phone number.
- Search the entities for a list of restaurants "seattle restaurants" and present their names and phone numbers.
- Trigger a bad request and shows how to read the error response.


## Features

This project framework provides examples for the **Bing Entity Search SDK** for the [Entity Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)

## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Sign up here](https://azure.microsoft.com/en-us/services/cognitive-services/directory/) by navigating to the **Search** services and acquiring an API key. You can get a trial key for **free** which will expire after 30 days.
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingEntitySearch
3. set env variable AZURE_BING_SAMPLES_API_KEY to your cognitive services API key.
4. mvn compile exec:java

## More information ##

[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can get a FREE trial account [here](http://go.microsoft.com/fwlink/?LinkId=330212)

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.