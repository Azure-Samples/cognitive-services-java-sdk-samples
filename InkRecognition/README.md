---
services: cognitive-services, inkrecognition
platforms: java, android
---

# Ink Recognition SDK Sample ##

Sample app for the Ink Recognizer Cognitive Service SDK which provides easy access to a cloud-based REST API to analyze and recognize digital ink content.
* Recognizes text and shapes drawn
* Shows how to set language on the request
* Shows how to set the ink content type on request

## Features

This project framework provides examples for using the Ink Recognition SDK.

## Getting Started

### Prerequisites

- Android 8.0 or later
- You must have a Cognitive Services API account. Please refer the [Ink Recognizer API](https://docs.microsoft.com/en-us/azure/cognitive-services/ink-recognizer/overview) if you don't have one already.

### Quickstart

To get these samples running locally, simply get the pre-requisites above, then:

1. Run 'git clone https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git' in the terminal
2. Run 'cd cognitive-services-java-sdk-samples/InkRecognition' in the terminal
3. Set the key in the app/src/main/java/CognitiveServices/Ink/Recognition/NoteTaker.java
4. Run 'gradlew build' in the terminal at the root of InkRecognition to build the project
5. Run 'gradlew assemble' in the terminal at the root of InkRecognition for the release apk under InkRecognition/app/build/outputs/apk/release
6. Deploy the APK to the phone or emulator using Android Developer Tools
7. Write on your app screen
8. View the results appear on the screen after 2 seconds of inactivity

---

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.