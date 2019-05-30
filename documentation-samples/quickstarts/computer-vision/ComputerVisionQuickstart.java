/*  Import the required libraries. If this is your first time running a Go program,
 *  you will need to 'go get' the azure-sdk-for-go and go-autorest packages.
 */
import com.microsoft.azure.cognitiveservices.vision.computervision.*;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.*;

import java.io.File;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

/*  The Quickstarts in this file are for the Computer Vision API for Microsoft
 *  Cognitive Services. In this file are Quickstarts for the following tasks:
 *  - Describing images
 *  - Categorizing images
 *  - Tagging images
 *  - Detecting faces
 *  - Detecting adult or racy content
 *  - Detecting the color scheme
 *  - Detecting domain-specific content (celebrities/landmarks)
 *  - Detecting image types (clip art/line drawing)
 */

class ComputerVisionQuickstart {
  public static void main(String[] args) {
    /*	Configure the Computer Vision client by:
  	 *    1. Reading the Computer Vision API key and the Azure region from environment
  	 *       variables (COMPUTERVISION_API_KEY and COMPUTERVISION_REGION), which must
  	 *       be set prior to running this code. After setting the	environment variables,
  	 *       restart your command shell or your IDE.
  	 *	  2. Constructing the endpoint URL from the base URL and the Azure region.
  	 *	  3. Setting up the authorization on the client with the subscription key.
  	 */
    String computerVisionAPIKey = System.getenv("COMPUTERVISION_API_KEY");
    if (computerVisionAPIKey == null) {
      System.out.println("\n\nPlease set the COMPUTERVISION_API_KEY environment variable.\n**Note that you might need to restart your shell or IDE.**\n");
      System.exit(0);
    }

    String computerVisionRegion = System.getenv("COMPUTERVISION_REGION");
    if (computerVisionRegion == null) {
      System.out.println("\n\nPlease set the COMPUTERVISION_REGION environment variable.\n**Note that you might need to restart your shell or IDE.**\n");
      System.exit(0);
    }

    String endpointURL = ("https://").concat(computerVisionRegion).concat(".api.cognitive.microsoft.com");
    ComputerVisionClient computerVisionClient = ComputerVisionManager.authenticate(computerVisionAPIKey).withEndpoint(endpointURL);
    //	END - Configure the Computer Vision client


    //  Analyze a local image
    String localImagePath = "src\\main\\resources\\landmark.jpg";
    String workingDirectory = System.getProperty("user.dir");
    System.out.println("\n\nLocal image path:\n" + workingDirectory + "\\" + localImagePath);

    DescribeLocalImage(computerVisionClient, localImagePath);
  	CategorizeLocalImage(computerVisionClient, localImagePath);
  	TagLocalImage(computerVisionClient, localImagePath);
  	DetectFacesLocalImage(computerVisionClient, localImagePath);
  	DetectAdultOrRacyContentLocalImage(computerVisionClient, localImagePath);
  	DetectColorSchemeLocalImage(computerVisionClient, localImagePath);
  	DetectDomainSpecificContentLocalImage(computerVisionClient, localImagePath);
  	DetectImageTypesLocalImage(computerVisionClient, localImagePath);
    //  END - Analyze a local image

    //  Analyze a remote image
    String remoteImageURL = "https://github.com/Azure-Samples/cognitive-services-sample-data-files/raw/master/ComputerVision/Images/faces.jpg";
    System.out.println("\n\nRemote image path: \n" + remoteImageURL);

    DescribeRemoteImage(computerVisionClient, remoteImageURL);
    CategorizeRemoteImage(computerVisionClient, remoteImageURL);
    TagRemoteImage(computerVisionClient, remoteImageURL);
    DetectFacesRemoteImage(computerVisionClient, remoteImageURL);
    DetectAdultOrRacyContentRemoteImage(computerVisionClient, remoteImageURL);
    DetectColorSchemeRemoteImage(computerVisionClient, remoteImageURL);
    DetectDomainSpecificContentRemoteImage(computerVisionClient, remoteImageURL);
    DetectImageTypesRemoteImage(computerVisionClient, remoteImageURL);
    //  END - Analyze a remote image
  }


  /*  Describe a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - features to extract
   *    4. Displaying the image captions and their confidence values.
   */
  public static void DescribeLocalImage(ComputerVisionClient client, String localImagePath) {
    try {
        File localImage = new File(localImagePath);
        byte[] imgBytes = Files.readAllBytes(localImage.toPath());

        List<VisualFeatureTypes> features = new ArrayList<>();
        features.add(VisualFeatureTypes.DESCRIPTION);

        ImageAnalysis analysis = client.computerVision().analyzeImageInStream()
            .withImage(imgBytes)
            .withVisualFeatures(features)
            .execute();

        System.out.println("\nCaptions from local image: ");
        if (analysis.description().captions().size() == 0) {
          System.out.println("No captions detected in local image.");
        } else {
          for (ImageCaption caption : analysis.description().captions()) {
              System.out.printf("\'%s\' with confidence %f\n", caption.text(), caption.confidence());
          }
        }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Describe local image


  /*  Describe a remote image file by:
  *    1. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
  *    2. Calling the Computer Vision service's AnalyzeImage with the:
  *       - image URL
  *       - features to extract
  *    3. Displaying the image captions and their confidence values.
   */
  public static void DescribeRemoteImage(ComputerVisionClient client, String remoteImageURL) {
    try {
        List<VisualFeatureTypes> features = new ArrayList<>();
        features.add(VisualFeatureTypes.DESCRIPTION);

        ImageAnalysis analysis = client.computerVision().analyzeImage()
            .withUrl(remoteImageURL)
            .withVisualFeatures(features)
            .execute();

            System.out.println("\nCaptions from remote image: ");
            if (analysis.description().captions().size() == 0) {
              System.out.println("No captions detected in remote image.");
            } else {
              for (ImageCaption caption : analysis.description().captions()) {
                System.out.printf("\'%s\' with confidence %f\n", caption.text(), caption.confidence());
            }
          }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Describe remote image


  /*  Categorize a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - features to extract
   *    4. Displaying the image captions and their confidence values.
   */
  public static void CategorizeLocalImage(ComputerVisionClient client, String localImagePath) {
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.CATEGORIES);

      ImageAnalysis analysis = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nCategories from local image: ");
      if (analysis.categories().size() == 0) {
        System.out.println("No categories detected in local image.");
      } else {
        for (Category category : analysis.categories()) {
            System.out.printf("\'%s\' with confidence %f\n", category.name(), category.score());
        }
      }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Categorize local image


  /*  Categorize a remote image file by:
  *    1. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
  *    2. Calling the Computer Vision service's AnalyzeImage with the:
  *       - image URL
  *       - features to extract
  *    3. Displaying the image captions and their confidence values.
   */
  public static void CategorizeRemoteImage(ComputerVisionClient client, String remoteImageURL) {
    try {
      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.CATEGORIES);

      ImageAnalysis analysis = client.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nCategories from remote image: ");
      if (analysis.categories().size() == 0) {
        System.out.println("No categories detected in remote image.");
      } else {
        for (Category category : analysis.categories()) {
            System.out.printf("\'%s\' with confidence %f\n", category.name(), category.score());
        }
      }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Categorize local image


  /*  Tag a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - features to extract
   *    4. Displaying the image captions and their confidence values.
   */
  public static void TagLocalImage(ComputerVisionClient client, String localImagePath) {
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.TAGS);

      ImageAnalysis analysis = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nTags from local image: ");
      if (analysis.tags().size() == 0) {
        System.out.println("No tags detected in local image.");
      } else {
        for (ImageTag tag : analysis.tags()) {
            System.out.printf("\'%s\' with confidence %f\n", tag.name(), tag.confidence());
        }
      }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Tag local image


  /*  Tag a remote image file by:
  *    1. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
  *    2. Calling the Computer Vision service's AnalyzeImage with the:
  *       - image URL
  *       - features to extract
  *    3. Displaying the image captions and their confidence values.
   */
  public static void TagRemoteImage(ComputerVisionClient client, String remoteImageURL) {
    try {
      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.TAGS);

      ImageAnalysis analysis = client.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nTags from remote image: ");
      if (analysis.tags().size() == 0) {
        System.out.println("No tags detected in remote image.");
      } else {
        for (ImageTag tag : analysis.tags()) {
            System.out.printf("\'%s\' with confidence %f\n", tag.name(), tag.confidence());
        }
      }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Tag local image


  /*  Detect faces in a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - features to extract
   *    4. Displaying the image captions and their confidence values.
   */
  public static void DetectFacesLocalImage(ComputerVisionClient client, String localImagePath){
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.FACES);

      ImageAnalysis analysis = client.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nFaces from local image: ");
      if (analysis.faces().size() == 0) {
        System.out.println("No faces detected in local image.");
      } else {
        for (FaceDescription face : analysis.faces()) {
            System.out.printf("\'%s\' of age %d at location (%d, %d), (%d, %d)\n", face.gender(), face.age(),
                face.faceRectangle().left(), face.faceRectangle().top(),
                face.faceRectangle().left() + face.faceRectangle().width(),
                face.faceRectangle().top() + face.faceRectangle().height());
        }
      }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect faces in a local image

  /*  Detect faces in a remote image file by:
  *    1. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
  *    2. Calling the Computer Vision service's AnalyzeImage with the:
  *       - image URL
  *       - features to extract
  *    3. Displaying the image captions and their confidence values.
   */
  public static void DetectFacesRemoteImage(ComputerVisionClient client, String remoteImageURL){
    try {
      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.FACES);

      ImageAnalysis analysis = client.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nFaces from remote image: ");
      for (FaceDescription face : analysis.faces()) {
          System.out.printf("\'%s\' of age %d at location (%d, %d), (%d, %d)\n", face.gender(), face.age(),
              face.faceRectangle().left(), face.faceRectangle().top(),
              face.faceRectangle().left() + face.faceRectangle().width(),
              face.faceRectangle().top() + face.faceRectangle().height());
      }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect faces in a remote image

  /*  Detect adult or racy content in a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - features to extract
   *    4. Displaying the image captions and their confidence values.
   */
  public static void DetectAdultOrRacyContentLocalImage(ComputerVisionClient computerVisionClient, String localImagePath) {
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.ADULT);

      ImageAnalysis analysis = computerVisionClient.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nAdult or racy content in local image: ");
      System.out.printf("Is adult content: %b with confidence %f\n", analysis.adult().isAdultContent(), analysis.adult().adultScore());
      System.out.printf("Has racy content: %b with confidence %f\n", analysis.adult().isRacyContent(), analysis.adult().racyScore());

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect adult or racy content in a local image


  /*  Detect adult or racy content in a remote image file by:
  *    1. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
  *    2. Calling the Computer Vision service's AnalyzeImage with the:
  *       - image URL
  *       - features to extract
  *    3. Displaying the image captions and their confidence values.
   */
  public static void DetectAdultOrRacyContentRemoteImage(ComputerVisionClient computerVisionClient, String remoteImageURL) {
    try {
      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.ADULT);

      ImageAnalysis analysis = computerVisionClient.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nAdult or racy content in remote image: ");
      System.out.printf("Is adult content: %b with confidence %f\n", analysis.adult().isAdultContent(), analysis.adult().adultScore());
      System.out.printf("Has racy content: %b with confidence %f\n", analysis.adult().isRacyContent(), analysis.adult().racyScore());

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect adult or racy content in a local image


  /*  Detect the color scheme in a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - features to extract
   *    4. Displaying the image captions and their confidence values.
   */
  public static void DetectColorSchemeLocalImage(ComputerVisionClient computerVisionClient, String localImagePath) {
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.COLOR);

      ImageAnalysis analysis = computerVisionClient.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

          System.out.println("\nColor scheme of the local image: ");
          System.out.println("Is black and white: " + analysis.color().isBWImg());
          System.out.println("Accent color: 0x" + analysis.color().accentColor());
          System.out.println("Dominant background color: " + analysis.color().dominantColorBackground());
          System.out.println("Dominant foreground color: " + analysis.color().dominantColorForeground());
          System.out.println("Dominant colors: " + String.join(", ", analysis.color().dominantColors()));

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect color scheme in a local image


  /*  Detect the color scheme in a remote image file by:
  *    1. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
  *    2. Calling the Computer Vision service's AnalyzeImage with the:
  *       - image URL
  *       - features to extract
  *    3. Displaying the image captions and their confidence values.
   */
  public static void DetectColorSchemeRemoteImage(ComputerVisionClient computerVisionClient, String remoteImageURL) {
    try {
      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.COLOR);

      ImageAnalysis analysis = computerVisionClient.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

          System.out.println("\nColor scheme of the remote image: ");
          System.out.println("Is black and white: " + analysis.color().isBWImg());
          System.out.println("Accent color: 0x" + analysis.color().accentColor());
          System.out.println("Dominant background color: " + analysis.color().dominantColorBackground());
          System.out.println("Dominant foreground color: " + analysis.color().dominantColorForeground());
          System.out.println("Dominant colors: " + String.join(", ", analysis.color().dominantColors()));

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect color scheme in a remote image

  /*  Detect the domain-specific content (celebrities, landmarks) in a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - features to extract
   *    4. Displaying the image captions and their confidence values.
   */
  public static void DetectDomainSpecificContentLocalImage(ComputerVisionClient computerVisionClient, String localImagePath) {
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.DESCRIPTION);
      features.add(VisualFeatureTypes.CATEGORIES);

      ImageAnalysis analysis = computerVisionClient.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

          System.out.println("\nDetecting domain-specific content in the local image ...");
          System.out.println("\nCelebrities in local image: ");
          for (Category category : analysis.categories())
          {
              //System.out.println("category: " + category);
              //System.out.println("category.detail(): " + category.detail());

              if (category.detail() != null && category.detail().celebrities() != null)
              {
                  for (CelebritiesModel celeb : category.detail().celebrities())
                  {
                      System.out.printf("\'%s\' with confidence %f at location (%d, %d), (%d, %d)\n", celeb.name(), celeb.confidence(),
                          celeb.faceRectangle().left(), celeb.faceRectangle().top(),
                          celeb.faceRectangle().left() + celeb.faceRectangle().width(),
                          celeb.faceRectangle().top() + celeb.faceRectangle().height());
                  }
              }
          }

          System.out.println("\nLandmarks in local image: ");
          for (Category category : analysis.categories())
          {
              if (category.detail() != null && category.detail().landmarks() != null)
              {
                  for (LandmarksModel landmark : category.detail().landmarks())
                  {
                      System.out.printf("\'%s\' with confidence %f\n", landmark.name(), landmark.confidence());
                  }
              }
          }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect domain-specific content in a local image


  /*  Detect the domain-specific content (celebrities, landmarks) in a remote image file by:
  *    1. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
  *    2. Calling the Computer Vision service's AnalyzeImage with the:
  *       - image URL
  *       - features to extract
  *    3. Displaying the image captions and their confidence values.
   */
  public static void DetectDomainSpecificContentRemoteImage(ComputerVisionClient computerVisionClient, String remoteImageURL) {
    try {
      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.DESCRIPTION);
      features.add(VisualFeatureTypes.CATEGORIES);

      ImageAnalysis analysis = computerVisionClient.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

      System.out.println("\nDetecting domain-specific content in the remote image ...");
      System.out.println("\nCelebrities in remote image: ");
      for (Category category : analysis.categories())
      {
          //System.out.println("category: " + category);
          //System.out.println("category.detail(): " + category.detail());

          if (category.detail() != null && category.detail().celebrities() != null)
          {
              for (CelebritiesModel celeb : category.detail().celebrities())
              {
                  System.out.printf("\'%s\' with confidence %f at location (%d, %d), (%d, %d)\n", celeb.name(), celeb.confidence(),
                      celeb.faceRectangle().left(), celeb.faceRectangle().top(),
                      celeb.faceRectangle().left() + celeb.faceRectangle().width(),
                      celeb.faceRectangle().top() + celeb.faceRectangle().height());
              }
          }
      }

      System.out.println("\nLandmarks in remote image: ");
      for (Category category : analysis.categories())
      {
          if (category.detail() != null && category.detail().landmarks() != null)
          {
              for (LandmarksModel landmark : category.detail().landmarks())
              {
                  System.out.printf("\'%s\' with confidence %f\n", landmark.name(), landmark.confidence());
              }
          }
      }


    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect domain-specific content in a local image


  /*  Detect the image type of a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - features to extract
   *    4. Displaying the image captions and their confidence values.
   */
  public static void DetectImageTypesLocalImage(ComputerVisionClient computerVisionClient, String localImagePath) {
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.IMAGE_TYPE);

      ImageAnalysis analysis = computerVisionClient.computerVision().analyzeImageInStream()
          .withImage(imgBytes)
          .withVisualFeatures(features)
          .execute();

        System.out.println("\nImage type of local image:");
        System.out.println("Clip art type: " + analysis.imageType().clipArtType());
        System.out.println("Line drawing type: " + analysis.imageType().lineDrawingType());

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect image type of a local image

  /*  Detect the image type of a remote image file by:
  *    1. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
  *    2. Calling the Computer Vision service's AnalyzeImage with the:
  *       - image URL
  *       - features to extract
  *    3. Displaying the image captions and their confidence values.
   */
  public static void DetectImageTypesRemoteImage(ComputerVisionClient computerVisionClient, String remoteImageURL) {
    try {
      List<VisualFeatureTypes> features = new ArrayList<>();
      features.add(VisualFeatureTypes.IMAGE_TYPE);

      ImageAnalysis analysis = computerVisionClient.computerVision().analyzeImage()
          .withUrl(remoteImageURL)
          .withVisualFeatures(features)
          .execute();

          System.out.println("\nImage type of remote image:");
          System.out.println("Clip art type: " + analysis.imageType().clipArtType());
          System.out.println("Line drawing type: " + analysis.imageType().lineDrawingType());

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect image type of a remote image
  }
