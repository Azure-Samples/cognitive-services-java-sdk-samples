# Bing Search SDK Samples

These samples will show you how to get up and running using the SDKs for various Bing Search services. They'll cover a few rudimentary use cases and hopefully express best practices for interacting with the data from these APIs.

## Features

This project framework provides examples for the following services:

* Using the **Bing Custom Search SDK** for the [Custom Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)
* Using the **Bing Entity Search SDK** for the [Entity Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)
* Using the **Bing Image Search SDK** for the [Image Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)
* Using the **Bing News Search SDK** for the [News Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)
* Using the **Bing Spell Check SDK** for the [Spell Check API](https://azure.microsoft.com/en-us/services/cognitive-services/)
* Using the **Bing Video Search SDK** for the [Video Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)
* Using the **Bing Web Search SDK** for the [Web Search API](https://azure.microsoft.com/en-us/services/cognitive-services/)


## Getting Started

### Prerequisites

- A cognitive services API key with which to authenticate the SDK's calls. [Sign up here](https://azure.microsoft.com/en-us/services/cognitive-services/directory/) by navigating to the **Search** services and acquiring an API key. You can get a trial key for **free** which will expire after 30 days.
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/BingSearchV7
3. mvn compile
4. set env variable AZURE_BING_SAMPLES_API_KEY to your cognitive services API key.
5. mvn exec:java -Dexec.mainClass="com.microsoft.azure.bingsearch.samples.Samples"

To see the code of each example, simply look at the examples in the Samples folder. They are written to be isolated in scope so that you can see only what you're interested in.
