---
services: cognitive-services, bing-spell-check
platforms: java
author: milismsft
---

# Bing Spell Check Sample

This sample checks the spelling for the query "Bill Gatas" (with market and mode settings) and print outs the flagged tokens and spelling correction suggestions.

## Getting Started

### Prerequisites
- If you don't have a Microsoft Azure subscription you can visit [the Microsoft Cognitive Services Web site](https://azure.microsoft.com/free/cognitive-services/), create a new Azure account, and try Cognitive Services for free.
- Get a Cognitive Services API key with which to authenticate the SDK's calls. [Sign up here](https://azure.microsoft.com/en-us/services/cognitive-services/spell-check/).
- Set an environment variable named BING_SPELL_CHECK_SUBSCRIPTION_KEY with your Cognitive Services API key in the quickstart.

### Clone and run

Execute the following from a command line:

1. `git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git`
1. `cd cognitive-services-java-sdk-samples/Language/BingSpellCheck`
1. `mvn compile exec:java cleanupDaemonThreads = false`

## More information 

- [Build and deploy Java apps on Azure](http://azure.com/java)
- [The Java SDK reference](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client?view=azure-java-stable)
- [Bing Spell Check documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-spell-check/index)

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
