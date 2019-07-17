---
topic:
  - sample
languages:
  - Java
products:
  - Azure
  - Cognitive Services
  - Content Moderator
---

# Sample Code for Content Moderator

This sample code shows you how to moderate images with Content Moderator.

## Contents

| File/folder | Description |
|-------------|-------------|
| `src\main\java` | Java source code. |
| `README.md`            | This README file. |
| `src\main\Resources\ImageFiles.txt`       | URLs for the images to moderate. |
| `src\main\Resources\ModerationOutput.json`| Program output. The sample also writes to standard output. |

## Prerequisites

- Java development environment : (IDE or command line)
- Maven : make sure Maven is installed: https://maven.apache.org/download.cgi
- Download Content Moderator SDK from Maven (for IDE): https://mvnrepository.com/artifact/com.microsoft.azure.cognitiveservices/azure-cognitiveservices-contentmoderator

## Setup

- [Clone this sample repository](https://github.com/Azure-Samples/cognitive-services-samples-pr.git).

## Modifying the Sample for your Configuration

1. Store your Content Moderator API key in the `CONTENT_MODERATOR_SUBSCRIPTION_KEY` environment variable.
2. Store your Content Moderator base endpoint in the `CONTENT_MODERATOR_ENDPOINT` environment variable. For example: `https://westus.api.cognitive.microsoft.com`. Be sure to use the correct region your subscription is in, in the URL.

## Building and Running the Sample
### From command line:
1. Navigate to the example's root directory.
2. Enter `mvn compile exec:java -Dexec.cleanupDaemonThreads=false`.
   Maven will automatically install all dependencies.

## Next steps

You can learn more about image moderation with Content Moderator at the [official documentation site](https://docs.microsoft.com/en-us/azure/cognitive-services/content-moderator/).
