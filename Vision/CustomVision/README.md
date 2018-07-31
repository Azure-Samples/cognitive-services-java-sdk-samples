---
services: cognitive-services, custom-vision
platforms: java
author: areddish
---

# Custom Vision SDK Sample ##

Sample code for image classification and object detection for Custom Vision, an Azure Cognitive Service.
- Creates an image classification project, upload images, train it and make a prediction.
- Creates an object detection project, upload images, train it and make a prediction.

## Features

This project framework provides examples for the **Custom Vision SDK** for the [Custom Vision API](https://azure.microsoft.com/en-us/services/cognitive-services/custom-vision-service/)

## Getting Started

### Prerequisites

- An account at [Custom Vision Service](https://www.customvision.ai)
- Maven

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
2. cd cognitive-services-java-sdk-samples/Vision/CustomVision
3. set env variable AZURE_CUSTOMVISION_TRAINING_API_KEY to your cognitive services TRAINING API key.
4. set env variable AZURE_CUSTOMVISION_PREDICTION_API_KEY to your cognitive services PREDICTION API key.
5. mvn compile exec:java

## More information ##

[http://azure.com/java](http://azure.com/java)

If you don't have a Microsoft Azure subscription you can get a FREE trial account [here](http://go.microsoft.com/fwlink/?LinkId=330212)

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.