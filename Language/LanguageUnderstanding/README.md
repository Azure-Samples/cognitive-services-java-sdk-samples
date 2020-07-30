---
services: cognitive-services, luis, language-understanding
platforms: java
author: milismsft
---

# Language Understanding SDK Sample ##

Sample code for Language Understanding, an Azure Cognitive Service, demonstrating how to consume the LUIS Authoring SDK to build an app or manage it and how to consume the LUIS Runtime SDK to predict user utterances.
- Create an application with two entities.
- Create a "Flight" composite entity including "Class" and "Destination"
- Create a new "FindFlights" intent with two utterances and build two EntityLabel Object
- Train and publish the application
- Execute a LUIS prediction for a "find second class flight to new york" utterance and print the results
- Execute a LUIS prediction for a "find flights to London in first class" utterance and print the results


## Features

This project framework provides examples for the **Language Understanding SDK** for the [Language Understanding API](https://azure.microsoft.com/en-us/services/cognitive-services/)

## Getting Started

### Prerequisites

- A Cognitive Services Language Understanding resource.
- A Cognitive Services Language Understanding authoring key with which to authenticate the SDK's calls. [Sign up here](https://www.luis.ai/). After After creating your LUIS account, a starter key, also known as a programmatic key, is created automatically for LUIS account. To find the programmatic key, click on the account name in the upper-right navigation bar to open Account Settings, which displays the Authoring Key.
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Language/LanguageUnderstanding
3. set env variable AZURE_LUIS_API_KEY to your cognitive services API key.
4. mvn compile exec:java

## More information ##

* [http://azure.com/java](http://azure.com/java)
* [Language Understanding Intelligent Service](https://azure.microsoft.com/en-us/services/cognitive-services/language-understanding-intelligent-service/)
* [LUIS.ai](https://www.luis.ai)
* [LUIS Docs](https://docs.microsoft.com/en-us/azure/cognitive-services/luis/home)
* [LUIS Programmatic API v2 - Specification](https://github.com/Azure/azure-rest-api-specs/tree/current/specification/cognitiveservices/data-plane/LUIS/Programmatic)
* [LUIS Programmatic API v2 - Documentation](https://westus.dev.cognitive.microsoft.com/docs/services/5890b47c39e2bb17b84a55ff/operations/5890b47c39e2bb052c5b9c2f)

If you don't have a Microsoft Azure subscription you can visit [the Microsoft Cognitive Services Web site](https://azure.microsoft.com/free/cognitive-services/), create a new Azure account, and try Cognitive Services for free.

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
