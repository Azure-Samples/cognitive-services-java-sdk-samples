---
services: cognitive-services, bing-custom-search
platforms: java
author: milismsft
---

# Bing Custom Search SDK Sample ##

Sample code for custom searching using Bing Custom Search, an Azure Cognitive Service.
- Custom search for "Xbox" and print out name and url for the first web page in the results list.


## Features

This project framework provides examples for the **Bing Custom Search SDK** for the [Custom Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)

## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Create a new Azure account, and try Cognitive Services for free.](https://azure.microsoft.com/free/cognitive-services/)
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingCustomSearch
3. set a system environment variable named `BING_CUSTOM_SEARCH_SUBSCRIPTION_KEY` with your subscription key as a value, then reopen your command prompt or IDE. If not, you might get an API key not found exception.
4. Set env variable AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID to your custom configuration id. Can set to 1 if you do not have a custom config id.
5. Use gradle to build and run the sample:
    1. `gradle build`
    2. `gradle run`

## More information ##
[Bing Custom Search Documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-custom-search/)

[Custom Search using the Bing Custom Search SDK and Java](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-custom-search/)
[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can visit [the Microsoft Cognitive Services Web site](https://azure.microsoft.com/free/cognitive-services/), create a new Azure account, and try Cognitive Services for free.

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
