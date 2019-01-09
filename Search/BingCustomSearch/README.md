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

- A cognitive services API key with which to authenticate the SDK's calls. [Sign up here](https://azure.microsoft.com/en-us/services/cognitive-services/directory/) by navigating to the **Search** services and acquiring a Bing Custom Search API key. You can get a trial key for **free** which will expire after 30 days.
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Search/BingCustomSearch
3. Either set a system environment variable named `AZURE_BING_SAMPLES_API_KEY` to your cognitive Services API key, or copy your key into the program. If you are going to set the AZURE_BING_SAMPLES_API_KEY environment variable, make sure to set it based on your OS, then reopen your command prompt or IDE. If not, you might get an API key not found exception.
4. Set env variable AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID to your custom configuration id. Can set to 1 if you do not have a custom config id.
5. mvn compile exec:java

## More information ##
[Bing Custom Search Documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-custom-search/)

[Custom Search using the Bing Custom Search SDK and Java](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-custom-search/)
[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can get a FREE trial account [here](http://go.microsoft.com/fwlink/?LinkId=330212)

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.