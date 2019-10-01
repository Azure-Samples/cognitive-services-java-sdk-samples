import com.microsoft.azure.cognitiveservices.vision.computervision.*;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.*;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Computer Vision Quickstart - Java
 * 
 * Prerequisites:
 *   - Download/clone this repo: https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
 *   - From your Azure account, get your subscription key and the endpoint, and add them as
       environment variables named COMPUTER_VISION_SUBSCRIPTION_KEY and COMPUTER_VISION_ENDPOINT.
 *   - Create a 'src/main/java/resources' folder and add images (landmark.jpg and printed_text.jpg) from here: 
 *     https://github.com/Azure-Samples/cognitive-services-sample-data-files/tree/master/ComputerVision/Images
 * 
 * To Run:
 *   - cd to cognitive-services-java-sdk-samples\Vision\ComputerVision
 *   - mvn compile exec:java
 * 
 * This quickstart performs the following tasks with both local and URL images:
 *   - Authenticate: creates a single client for use with all examples.
 *   - Describe Image: gives a description of the main features of an image.
 *   - Categorize Image: returns a few general categories related to the image.
 *   - Tag Image: returns keywords from all objects in the image.
 *   - Detect Faces: returns a list of all faces found in the image.
 *   - Detect adult/racy content: rates whether an image is adult or racy.
 *   - Detect Color Scheme: retuns dominant colors and accent color in the image.
 *   - Detect Domain-Specific Content: identifies celebrities or landmarks in an image.
 *   - Detect Image Type: returns the file type (clip art, line drawing, etc.) of an image.
 *   - Recongnize Printed Text: uses optical character recognition (OCR) to find text in an image.
 * 
 * NOTE: Ignore the "Warning..." text in the console when run. The sample still runs fine despite it.
 */

class ComputerVisionQuickstart {
  public static void main(String[] args) {

    // Images for use in several examples. Get from here: 
    // https://github.com/Azure-Samples/cognitive-services-sample-data-files/tree/master/ComputerVision/Images
    // Create a resources folder as in src/main/resources, then add the images below to it.
    String localImagePath = "src\\main\\resources\\landmark.jpg";
    String localTextImagePath = "src\\main\\resources\\printed_text.jpg";
    String remoteImageURL = "https://github.com/Azure-Samples/cognitive-services-sample-data-files/raw/master/ComputerVision/Images/faces.jpg";
    // Use a remote image for recognizing text with OCR
    String remoteTextImageURL = "https://moderatorsampleimages.blob.core.windows.net/samples/sample2.jpg";

    /**
     * AUTHENTICATE
     * Create a client that authorizes your Computer Vision subscription key and region.
     */	
    String subscriptionKey = System.getenv("COMPUTER_VISION_SUBSCRIPTION_KEY");
    if (subscriptionKey == null) {
      System.out.println("\n\nPlease set the COMPUTER_VISION_SUBSCRIPTION_KEY environment variable." +
      "\n**You might need to restart your shell or IDE after setting it.**\n");
      System.exit(0);
    }

    String endpoint = System.getenv("COMPUTER_VISION_ENDPOINT");
    if (endpoint == null) {
      System.out.println("\n\nPlease set the COMPUTER_VISION_ENDPOINT environment variable." +
      "\n**You might need to restart your shell or IDE after setting it.**\n");
      System.exit(0);
    }

    ComputerVisionClient computerVisionClient = 
            ComputerVisionManager.authenticate(subscriptionKey).withEndpoint(endpoint);
    /**
     * END - Authenticate
     */

    // Analyze local and remote images
    DescribeImage(computerVisionClient, localImagePath, remoteImageURL);
    CategorizeImage(computerVisionClient, localImagePath, remoteImageURL);
    TagImage(computerVisionClient, localImagePath, remoteImageURL);
    DetectFacesInImage(computerVisionClient, localImagePath, remoteImageURL);
    DetectAdultOrRacyContentInImage(computerVisionClient, localImagePath, remoteImageURL);
    DetectColorSchemeInImage(computerVisionClient, localImagePath, remoteImageURL);
    DetectDomainSpecificContentInImage(computerVisionClient, localImagePath, remoteImageURL);
    DetectImageTypesInImage(computerVisionClient, localImagePath, remoteImageURL);

    // Analyze local and remote text-image for recognizing printed text with OCR
    RecognizeTextOCR(computerVisionClient, localTextImagePath, remoteTextImageURL);
  }

  /**
   *  DESCRIBE IMAGE: 
   *  API call: AnalyzeImageInStream & DescribeImage
   *  Displays the image description (captions) and their confidence values.
   */
  public static void DescribeImage(ComputerVisionClient client, String localImagePath, String remoteImageURL) {
    System.out.println("-----------------------------------------------");
    System.out.println("DESCRIBE IMAGE");
    System.out.println();
    try {
      // Describe local image
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());
      ImageDescription analysisLocal = client.computerVision().describeImageInStream()
          .withImage(imgBytes)
          .withLanguage("en")
          .withMaxCandidates("1")
          .execute();
      
      // Describe remote URL image
      ImageDescription analysisRemote = client.computerVision().describeImage()
      .withUrl(remoteImageURL)
      .withLanguage("en")
      .withMaxCandidates("1")
      .execute();

      ImageDescription[] results = { analysisLocal, analysisRemote };

      // Print results of local and remote images
      for (ImageDescription result : results){
        String location = null;
        ImageDescription analysis = null;
        if (result == analysisLocal) { analysis = analysisLocal; location = "local"; }
        else { analysis = analysisRemote; location = "remote"; }

        System.out.println("Description of " + location +" image: ");
        if (analysis.captions().size() == 0) {
          System.out.println("No description detected in " + location +" image.");
        } else {
          for (ImageCaption caption : analysis.captions()) {
              System.out.printf("\'%s\' with confidence %2.2f%%\n", caption.text(), caption.confidence() * 100);
          }
        }
        System.out.println();
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Describe Image

  /**  
   * CATEGORIZE IMAGE:
   * API call: AnalyzeImageInStream & AnalyzeImage
   * Displays image categories and their confidence values.
   */
  public static void CategorizeImage(ComputerVisionClient client, String localImagePath, String remoteImageURL) {
    System.out.println("-----------------------------------------------");   
    System.out.println("CATEGORIZE IMAGE");
    System.out.println();
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.CATEGORIES);

      // Categorize local image
      ImageAnalysis analysisLocal = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();
      
      // Categorize remote URL image 
      ImageAnalysis analysisRemote = client.computerVision().analyzeImage()
      .withUrl(remoteImageURL)
      .withVisualFeatures(features)
      .execute();

      ImageAnalysis[] results = { analysisLocal, analysisRemote };

      // Print results of local and remote images
      for (ImageAnalysis result : results){
        String location = null;
        ImageAnalysis analysis = null;
        if (result == analysisLocal) { analysis = analysisLocal; location = "local"; }
        else { analysis = analysisRemote; location = "remote"; }

        System.out.println("\nCategories from " + location + " image: ");
        if (analysis.categories().size() == 0) {
          System.out.println("No categories detected in " + location + " image.");
        } else {
          for (Category category : analysis.categories()) {
              System.out.printf("\'%s\' with confidence %2.2f%%\n", category.name(), category.score() * 100);
          }
        }
      }  
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    System.out.println();
  }
  //  END - Categorize Image

  /**  
   * TAG IMAGE:
   * API call: TagImageInStream & TagImage
   * Displays the image tags and their confidence values.
   */
  public static void TagImage(ComputerVisionClient client, String localImagePath, String remoteImageURL) {
    System.out.println("-----------------------------------------------");
    System.out.println("TAG IMAGE");
    System.out.println();
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      // Get tags from local image
      TagResult analysisLocal = client.computerVision().tagImageInStream()
          .withImage(imgBytes)
          .withLanguage("en")
          .execute();
      
      // Get tags from remote URL image
      TagResult analysisRemote = client.computerVision().tagImage()
      .withUrl(remoteImageURL)
      .withLanguage("en")
      .execute();

      TagResult[] results = { analysisLocal, analysisRemote };

      // Print results of local and remote images
      for (TagResult result : results){
        String location = null;
        TagResult analysis = null;
        if (result == analysisLocal) { analysis = analysisLocal; location = "local"; }
        else { analysis = analysisRemote; location = "remote"; }

        System.out.println("Tags from " + location + " image: ");
        if (analysis.tags().size() == 0) {
          System.out.println("No tags detected in " + location + " image.");
        } else {
          for (ImageTag tag : analysis.tags()) {
              System.out.printf("\'%s\' with confidence %2.2f%%\n", tag.name(), tag.confidence() * 100);
          }
        }
        System.out.println();
      }  
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Tag Image

  /**  
   * DETECT FACES:
   * API call: AnalyzeImageInStream & AnalyzeImage
   * Displays gender, age, and bounding box from found faces in image.
   */
  public static void DetectFacesInImage(ComputerVisionClient client, String localImagePath, String remoteImageURL){
    System.out.println("-----------------------------------------------");
    System.out.println("DETECT FACES IN IMAGE");
    System.out.println();
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.FACES);

      // Detect faces in local image
      ImageAnalysis analysisLocal = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

      // Detect faces in remote URL image
      ImageAnalysis analysisRemote = client.computerVision().analyzeImage()
      .withUrl(remoteImageURL)
      .withVisualFeatures(features)
      .execute();

      ImageAnalysis[] results = { analysisLocal, analysisRemote };

      // Print results of local and remote images
      for (ImageAnalysis result : results){
        String location = null;
        ImageAnalysis analysis = null;
        if (result == analysisLocal) { analysis = analysisLocal; location = "local"; }
        else { analysis = analysisRemote; location = "remote"; }

        System.out.println("Faces from " + location + " image: ");
        if (analysis.faces().size() == 0) {
          System.out.println("No faces detected in " + location + " image.");
        } else {
          for (FaceDescription face : analysis.faces()) {
              System.out.printf("\'%s\' of age %d at location (%d, %d), (%d, %d)\n", face.gender(), face.age(),
                  face.faceRectangle().left(), face.faceRectangle().top(),
                  face.faceRectangle().left() + face.faceRectangle().width(),
                  face.faceRectangle().top() + face.faceRectangle().height());
          }
        }
        System.out.println();
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect Faces

  /*  
   * DETECT ADULT/RACY CONTENT:
   * API call: AnalyzeImageInStream & AnalyzeImage
   * Displays whether or not image contain adult or racy features with confidence values.
   */
  public static void DetectAdultOrRacyContentInImage(ComputerVisionClient client, String localImagePath, String remoteImageURL) {
    System.out.println("-----------------------------------------------");
    System.out.println("DETECT ADULT/RACY CONTENT IN IMAGE");
    System.out.println();
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.ADULT);

      // Detect adult/racy content in a local image
      ImageAnalysis analysisLocal = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

      // Detect adult/racy content in a remote image
      ImageAnalysis analysisRemote = client.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

      ImageAnalysis[] results = { analysisLocal, analysisRemote };

      // Print results of local and remote images
      for (ImageAnalysis result : results){
        String location = null;
        ImageAnalysis analysis = null;
        if (result == analysisLocal) { analysis = analysisLocal; location = "local"; }
        else { analysis = analysisRemote; location = "remote"; }    

        System.out.println("Adult or racy content in " + location + " image: ");
        System.out.printf("Is adult content: %b with confidence %2.2f%%\n", analysis.adult().isAdultContent(), analysis.adult().adultScore() * 100);
        System.out.printf("Has racy content: %b with confidence %2.2f%%\n", analysis.adult().isRacyContent(), analysis.adult().racyScore() * 100);
        System.out.println();
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect Adult/Racy Content

  /**
   * DETECT COLOR SCHEME:
   * Displays dominant color scheme of image and accent color.
   */
  public static void DetectColorSchemeInImage(ComputerVisionClient client, String localImagePath, String remoteImageURL) {
    System.out.println("-----------------------------------------------");
    System.out.println("DETECT COLOR SCHEME IN IMAGE");
    System.out.println();
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.COLOR);

      // Detect color scheme in local image
      ImageAnalysis analysisLocal = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

      // Detect color scheme in remote URL image
      ImageAnalysis analysisRemote = client.computerVision().analyzeImage()
      .withUrl(remoteImageURL)
      .withVisualFeatures(features)
      .execute();

      ImageAnalysis[] results = { analysisLocal, analysisRemote };

      // Print results of local and remote images
      for (ImageAnalysis result : results){
        String location = null;
        ImageAnalysis analysis = null;
        if (result == analysisLocal) { analysis = analysisLocal; location = "local"; }
        else { analysis = analysisRemote; location = "remote"; }   

        System.out.println("Color scheme of the " + location + " image: ");
        System.out.println("Is black and white: " + analysis.color().isBWImg());
        System.out.println("Accent color: 0x" + analysis.color().accentColor());
        System.out.println("Dominant background color: " + analysis.color().dominantColorBackground());
        System.out.println("Dominant foreground color: " + analysis.color().dominantColorForeground());
        System.out.println("Dominant colors: " + String.join(", ", analysis.color().dominantColors()));
        System.out.println();
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect Color Scheme

  /** 
   * DETECT DOMAIN-SPECIFIC CONTENT:
   * Displays detected celebrities and landmarks in images, in any, with confidence values.
   */
  public static void DetectDomainSpecificContentInImage(ComputerVisionClient client, String localImagePath, String remoteImageURL) {
    System.out.println("-----------------------------------------------");
    System.out.println("DETECT DOMAIN-SPECIFIC CONTENT IN IMAGE");
    System.out.println();
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.DESCRIPTION);
      features.add(VisualFeatureTypes.CATEGORIES);

      // Detect domain specific content in a local image
      ImageAnalysis analysisLocal = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

      // Detect domain specific content in a remote URL image
      ImageAnalysis analysisRemote = client.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

      // Print results of a local image
      System.out.println("Landmarks in local image: ");
      int num = 1;
      for (Category category : analysisLocal.categories()) {
        if (category.detail() != null && category.detail().landmarks() != null) {
          for (LandmarksModel landmark : category.detail().landmarks()) {
            System.out.println("In category: " + category.name());
            System.out.printf("\'%s\' with confidence %2.2f%%\n", landmark.name(), landmark.confidence() * 100);
          }
        } 
      }

      // Print results of a remote image
      System.out.println("\nCelebrities in remote image: ");
      for (Category category : analysisRemote.categories()) {
        if (category.detail() != null && category.detail().celebrities() != null) {
          for (CelebritiesModel celeb : category.detail().celebrities()) {
            // Location is the bounding box around the celebrity's face
              System.out.printf("\'%s\' with confidence %2.2f%% at location (%d, %d), (%d, %d)\n",
                  celeb.name(), celeb.confidence() * 100,
                  celeb.faceRectangle().left(), 
                  celeb.faceRectangle().top(),
                  celeb.faceRectangle().left() + celeb.faceRectangle().width(),
                  celeb.faceRectangle().top() + celeb.faceRectangle().height());
          }
        } else { System.out.println("No celebrities in remote image."); }
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    System.out.println();
  }
  //  END - Detect Domain-Specific Content

  /** 
   * DETECT IMAGE TYPE:
   * Displays type of image, whether or not it is clip art or a line drawing.
   */
  public static void DetectImageTypesInImage(ComputerVisionClient client, String localImagePath, String remoteImageURL) {
    System.out.println("-----------------------------------------------");
    System.out.println("DETECT TYPE OF IMAGE");
    System.out.println();
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.IMAGE_TYPE);

      // Detect type of local image
      ImageAnalysis analysisLocal = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();
          
      // Detect type of remote URL image
      ImageAnalysis analysisRemote = client.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

      ImageAnalysis[] results = { analysisLocal, analysisRemote };

      // Print results of local and remote images
      for (ImageAnalysis result : results){
        String location = null;
        ImageAnalysis analysis = null;
        if (result == analysisLocal) { analysis = analysisLocal; location = "local"; }
        else { analysis = analysisRemote; location = "remote"; } 

        System.out.println("The " + location + " image type is: ");
        System.out.println("Clip art type: ");
        switch ((int) analysis.imageType().clipArtType()) {
          case 0: System.out.println("Not clip art.");
            break;
          case 1: System.out.println("Ambiguously clip art.");
            break;
          case 2: System.out.println("Normal clip art.");
            break;
          case 3: System.out.println("Good clip art.");
            break;
          default:
            break;
        }
        System.out.println("\nLine drawing type: ");
        if (analysis.imageType().lineDrawingType() == 1) {
          System.out.println("Image is a line drawing.");
        } else {
          System.out.println("Image isn't a line drawing.");
        }
        System.out.println();
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect Image Type

  /** 
   * RECOGNIZE PRINTED TEXT:
   * Displays text found in image of printed text with angle and orientation of the block of text.
   */
  private static void RecognizeTextOCR(ComputerVisionClient client, String localTextImagePath, String remoteTextImageURL){
    System.out.println("-----------------------------------------------");
    System.out.println("RECOGNIZE PRINTED TEXT");
    try {
      File rawImage = new File(localTextImagePath);
      byte[] localImageBytes = Files.readAllBytes(rawImage.toPath());

      // Recognize printed text in local image
      OcrResult ocrResultLocal = client.computerVision().recognizePrintedTextInStream()
          .withDetectOrientation(true)
          .withImage(localImageBytes)
          .withLanguage(OcrLanguages.EN)
          .execute();

      // Recognize printed text in remote image
      OcrResult ocrResultRemote = client.computerVision().recognizePrintedText()
          .withDetectOrientation(true)
          .withUrl(remoteTextImageURL)
          .withLanguage(OcrLanguages.EN)
          .execute();    

      OcrResult[] results = { ocrResultLocal , ocrResultRemote };

      // Print results of local and remote images
      for (OcrResult result : results){
        String location = null;
        OcrResult ocrResult = null;
        if (result == ocrResultLocal) { ocrResult = ocrResultLocal; location = "local"; }
        else { ocrResult = ocrResultRemote; location = "remote"; } 
        System.out.println();
        System.out.println("Recognizing text from " + location + " image with OCR ...");
        System.out.println("\nLanguage: " + ocrResult.language());
        System.out.printf("Text angle: %1.3f\n", ocrResult.textAngle());
        System.out.println("Orientation: " + ocrResult.orientation());

        boolean firstWord = true; 
        // Gets entire region of text block
        for (OcrRegion reg : ocrResult.regions()) {
          // Get one line in the text block
          for (OcrLine line : reg.lines()) {
            for (OcrWord word : line.words()) {
              // get bounding box of first word recognized (just to demo)
              if (firstWord) {
                System.out.println("\nFirst word in first line is \"" + word.text() + "\" with  bounding box: " + word.boundingBox());
                firstWord = false;
                System.out.println();
              }
              System.out.print(word.text() + " ");
            }
            System.out.println();
          }
        }
      }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    //  END - Recognize Printed Text
    System.out.println();
    System.out.println("-----------------------------------------------");
    System.out.println("End of quickstart.");
    System.out.println();
  }
}
