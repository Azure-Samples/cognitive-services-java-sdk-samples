---
page_type: sample
languages:
- java
products:
- azure
description: "These samples will get you started using the Java SDKs for various Cognitive Services."
urlFragment: cognitive-services-java-sdk-samples
---

# Cognitive Services Java SDK Samples

These samples will get you started using the Java SDKs for various Cognitive Services. They'll cover a few common use cases that are end-to-end solutions, including best practices for interacting with the data from these APIs. 

For quickstart versions of many of these samples (and for more examples not covered here), view the [cognitive-services-quickstart-code](https://github.com/Azure-Samples/cognitive-services-quickstart-code) repo for Java. This repo demonstrates short "snippets" of code that back up many of the quickstarts in the [Cognitive Services documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/) pages.

For a general overview about Cognitive Services, view [What are Azure Cognitive Services?](https://docs.microsoft.com/en-us/azure/cognitive-services/welcome).

NOTE: QUICKSTARTS ARE BEING MOVED HERE: https://github.com/Azure-Samples/cognitive-services-quickstart-code/tree/master/java

## Features

This project framework provides examples for the following services:

### Language

* **Language Understanding (LUIS)**, using the [Language Understanding SDK](https://docs.microsoft.com/en-us/java/api/com.microsoft.azure.cognitiveservices.language.luis.authoring?view=azure-java-stable) for the [LUIS API](https://westus.dev.cognitive.microsoft.com/docs/services/5890b47c39e2bb17b84a55ff/operations/5890b47c39e2bb052c5b9c2f)
* **Spell Check**, using the [Bing Spell Check SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/bingspellcheck?view=azure-java-stable) for the [Spell Check API](https://dev.cognitive.microsoft.com/docs/services/5f7d486e04d2430193e1ca8f760cd7ed/operations/57855119bca1df1c647bc358)
* **Text Analytics**, using the [Text Analytics SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/textanalytics?view=azure-java-stable) for the [Text Analytics API](https://westcentralus.dev.cognitive.microsoft.com/docs/services/TextAnalytics-v2-1/operations/56f30ceeeda5650db055a3c7)

### Search

* **Autosuggest Search**, using the [Bing Autosuggest SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/bingautosuggest?view=azure-java-stable) for the [Autosuggest API](https://dev.cognitive.microsoft.com/docs/services/644e01b5a68c4fdb93e1f49b4f5c4ce1/operations/56c769a2cf5ff801a090fbd2)
* **Custom Search**, using the [Bing Custom Search SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/bingcustomsearch?view=azure-java-stable) for the [Custom Search API v7](https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-custom-search-api-v7-reference)
* **Image Search**, using the [Bing Custom Image Search SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/bingcustomimagesearch?view=azure-java-stable) for the [Image Search API v7](https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-images-api-v7-reference)
* **Entity Search**, using the [Bing Entity Search SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/bingentitysearchapi?view=azure-java-stable) for the [Bing Entity Search API](https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-entities-api-v7-reference)
* **News Search**, using the [Bing News Search SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/bingnewssearch?view=azure-java-stable) for the [News Search v7 API](https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-news-api-v7-reference)
* **Video Search**, using the [Bing Video Search SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/bingvideosearch?view=azure-java-stable) for the [Video Search API v7](https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-video-api-v7-reference)
* **Visual Search**, using the [Bing Visual Search SDK](https://docs.microsoft.com/en-us/java/api/com.microsoft.azure.cognitiveservices.search.visualsearch?view=azure-java-stable) for the [Bing Visual Search API](https://docs.microsoft.com/en-us/rest/api/cognitiveservices/bingvisualsearch/images)
* **Web Search**, using the [Bing Web Search SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/bingwebsearchapi?view=azure-java-stable) for the [Web Search API v7](https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-web-api-v7-reference)

### Vision

* **[Face](https://github.com/Azure-Samples/cognitive-services-quickstart-code/tree/master/java/Face)**, using the [Face SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/faceapi?view=azure-java-stable) for the [Face API](https://docs.microsoft.com/en-us/azure/cognitive-services/face/APIReference)
* **Computer Vision**, using the [Computer Vision SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/computervision?view=azure-java-stable) for the [Computer Vision API](https://westus.dev.cognitive.microsoft.com/docs/services/5cd27ec07268f6c679a3e641/operations/56f91f2e778daf14a499f21b)
* **[Content Moderator](https://github.com/Azure-Samples/cognitive-services-quickstart-code/tree/master/java/ContentModerator)**, using the [Content Moderator SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/contentmoderator?view=azure-java-stable) for the [Content Moderator API](https://docs.microsoft.com/en-us/azure/cognitive-services/content-moderator/api-reference)
* **Custom Vision**, using the [Custom Vision SDK](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client/customvision?view=azure-java-stable) for the [Custom Vision Training API](https://southcentralus.dev.cognitive.microsoft.com/docs/services/Custom_Vision_Training_3.0/operations/5c771cdcbf6a2b18a0c3b7fa) and the [Custom Vision Prediction API](https://southcentralus.dev.cognitive.microsoft.com/docs/services/Custom_Vision_Prediction_3.0/operations/5c82db60bf6a2b11a8247c15)
* **Ink Recognizer**, using the [Ink Recognizer SDK](https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/cognitiveservices/ms-azure-cs-inkrecognizer) for the [Ink Recognizer API](https://docs.microsoft.com/en-us/rest/api/cognitiveservices/inkrecognizer/inkrecognizer/recognize)

## Getting Started

### Prerequisites

1.  A cognitive services API key and/or endpoint with which to authenticate the SDK's calls. If you don't have an Azure account, you can visit [the Microsoft Cognitive Services Web site](https://azure.microsoft.com/free/cognitive-services/), create a new Azure account, and try Cognitive Services for free. Or follow the [Create a Cognitive Services resource using the Azure portal](https://docs.microsoft.com/en-us/azure/cognitive-services/cognitive-services-apis-create-account?tabs=multiservice%2Cwindows) guide. You can create a Cognitive Services account on the Azure portal through the *Use with an Azure subscription* button.
2. JDK 7 or 8

> Subscription keys are usually per service. For example, the subscription key for Spell Check will not be the same as it is for Custom Search.

### Installation

Copy this repository to your local machine by typing 
```
git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
```
in your command line/bash. Or download and open the zip file of this repo.

### Quickstart

In each sample you will need your subscription key and/or endpoint from your Azure Portal resource for the service. In the beginning of each sample, add your subscription key/endpoint where applicable.

## Resources

- Each service stems from a [Cognitive Service client](https://docs.microsoft.com/en-us/java/api/overview/azure/cognitiveservices/client?view=azure-java-stable)
- [azure-sdk-for-java](https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/cognitiveservices)
