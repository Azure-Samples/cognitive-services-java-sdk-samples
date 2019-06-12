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

- Java development environment
- Maven

## Setup

- [Clone this sample repository](https://github.com/Azure-Samples/cognitive-services-samples-pr.git).

## Modifying the Sample for your Configuration

1. Store your Content Moderator API key in the `AZURE_CONTENTMODERATOR_KEY` environment variable.
2. Store your Azure endpoint in the `AZURE_ENDPOINT` environment variable. (optional)

## Building and Running the Sample

1. From the command line, navigate to the samples root directory: `...\cognitive-services-samples-pr\java\ContentModerator`.
2. Enter `mvn compile exec:java -Dexec.cleanupDaemonThreads=false`.

## Next steps

You can learn more about image moderation with Content Moderator at the [official documentation site](https://docs.microsoft.com/en-us/azure/cognitive-services/content-moderator/).
