/*
 *  This project was built with maven, and the following command:
 *    mvn compile exec:java -Dexec.cleanupDaemonThreads=false
 *  To build this project, you'll need a properly configured maven project,
 *  with a valid pom.xml file. Include the following <build> block in your maven
 *  project:
 *       <build>
 *         <plugins>
 *     		   <plugin>
 *             <groupId>org.codehaus.mojo</groupId>
 *             <artifactId>exec-maven-plugin</artifactId>
 *             <version>1.4.0</version>
 *             <configuration>
 *               <mainClass>ComputerVisionQuickstart</mainClass>
 *             </configuration>
 *           </plugin>
 *           <plugin>
 *             <artifactId>maven-assembly-plugin</artifactId>
 *             <executions>
 *               <execution>
 *                 <phase>package</phase>
 *                 <goals>
 *                   <goal>attached</goal>
 *                 </goals>
 *                 <configuration>
 *                   <descriptorRefs>
 *                     <descriptorRef>jar-with-dependencies</descriptorRef>
 *                   </descriptorRefs>
 *                   <archive>
 *                     <manifest>
 *                       <mainClass>ComputerVisionQuickstart</mainClass>
 *                     </manifest>
 *                   </archive>
 *                 </configuration>
 *               </execution>
 *             </executions>
 *           </plugin>
 *     	   <plugin>
 *           <artifactId>maven-compiler-plugin</artifactId>
 *             <version>3.0</version>
 *             <configuration>
 *               <source>1.7</source>
 *               <target>1.7</target>
 *             </configuration>
 *           </plugin>
 *         </plugins>
 *       </build>
 *
 *    Include the following <dependencies> block in your maven project:
 *      <dependencies>
 *        <dependency>
 *          <groupId>com.fasterxml.jackson.core</groupId>
 *          <artifactId>jackson-databind</artifactId>
 *          <version>2.9.9</version>
 *        </dependency>
 *        <dependency>
 *          <groupId>com.microsoft.azure</groupId>
 *          <artifactId>azure</artifactId>
 *          <version>1.9.0</version>
 *        </dependency>
 *        <dependency>
 *          <groupId>commons-net</groupId>
 *          <artifactId>commons-net</artifactId>
 *          <version>3.3</version>
 *        </dependency>
 *        <dependency>
 *          <groupId>com.microsoft.azure.cognitiveservices</groupId>
 *          <artifactId>azure-cognitiveservices-computervision</artifactId>
 *          <version>1.0.2-beta</version>
 *        </dependency>
 *        <dependency>
 *          <groupId>com.microsoft.rest</groupId>
 *          <artifactId>client-runtime</artifactId>
 *          <version>1.6.6</version>
 *        </dependency>
 *      </dependencies>
 *
 *  When you build the maven project, it will pull in the following compile-time
 *  dependencies/versions:
 *    com.microsoft.azure:azure-mgmt-redis:jar:1.9.0
 *    org.apache.commons:commons-lang3:jar:3.4
 *    org.slf4j:slf4j-api:jar:1.7.22
 *    net.jcip:jcip-annotations:jar:1.0
 *    org.slf4j:slf4j-simple:jar:1.7.5
 *    com.squareup.okhttp3:logging-interceptor:jar:3.11.0
 *    com.squareup.retrofit2:converter-jackson:jar:2.5.0
 *    com.microsoft.azure:azure-mgmt-storage:jar:1.9.0
 *    com.microsoft.azure:azure-mgmt-cdn:jar:1.9.0
 *    commons-net:commons-net:jar:3.3
 *    com.microsoft.azure:azure-mgmt-monitor:jar:1.9.0
 *    com.microsoft.azure:azure-mgmt-network:jar:1.9.0
 *    org.apache.httpcomponents:httpcore:jar:4.4.5
 *    com.fasterxml.jackson.core:jackson-core:jar:2.9.9
 *    com.fasterxml.jackson.datatype:jackson-datatype-joda:jar:2.9.8
 *    io.reactivex:rxjava:jar:1.3.8
 *    com.microsoft.azure:azure-mgmt-keyvault:jar:1.9.0
 *    com.nimbusds:oauth2-oidc-sdk:jar:4.5
 *    com.microsoft.azure:azure-mgmt-containerinstance:jar:1.9.0
 *    com.squareup.okio:okio:jar:1.14.0
 *    com.squareup.retrofit2:adapter-rxjava:jar:2.4.0
 *    com.microsoft.azure:azure-annotations:jar:1.7.0
 *    com.microsoft.azure:azure-mgmt-trafficmanager:jar:1.9.0
 *    com.microsoft.azure:azure-keyvault-webkey:jar:1.0.0
 *    com.fasterxml.jackson.core:jackson-annotations:jar:2.9.0
 *    com.microsoft.azure:azure-client-runtime:jar:1.4.0
 *    com.microsoft.azure:azure-mgmt-graph-rbac:jar:1.9.0
 *    com.microsoft.azure:azure-keyvault-core:jar:0.8.0
 *    com.google.guava:guava:jar:20.0
 *    com.microsoft.azure:azure-mgmt-resources:jar:1.9.0
 *    javax.mail:mail:jar:1.4.7
 *    com.microsoft.azure:azure-mgmt-eventhub:jar:1.9.0
 *    com.microsoft.azure:azure-mgmt-sql:jar:1.9.0
 *    com.microsoft.azure:azure-client-authentication:jar:1.4.0
 *    com.nimbusds:nimbus-jose-jwt:jar:3.1.2
 *    org.bouncycastle:bcprov-jdk15on:jar:1.51
 *    com.google.code.gson:gson:jar:2.2.4
 *    com.microsoft.azure:azure-mgmt-msi:jar:1.9.0
 *    com.squareup.okhttp3:okhttp:jar:3.11.0
 *    com.microsoft.azure:azure-mgmt-locks:jar:1.9.0
 *    joda-time:joda-time:jar:2.1
 *    com.fasterxml.jackson.core:jackson-databind:jar:2.9.9
 *    com.nimbusds:lang-tag:jar:1.4
 *    com.squareup.okhttp3:okhttp-urlconnection:jar:3.11.0
 *    com.microsoft.azure.cognitiveservices:azure-cognitiveservices-computervision:jar:1.0.2-beta
 *    com.microsoft.azure:azure-mgmt-cosmosdb:jar:1.9.0
 *    javax.activation:activation:jar:1.1
 *    com.microsoft.azure:azure-mgmt-compute:jar:1.9.0
 *    net.minidev:json-smart:jar:1.1.1
 *    com.microsoft.azure:azure-mgmt-appservice:jar:1.9.0
 *    commons-codec:commons-codec:jar:1.10
 *    com.microsoft.azure:azure-mgmt-containerregistry:jar:1.9.0
 *    com.microsoft.azure:azure:jar:1.9.0
 *    com.microsoft.azure:azure-mgmt-batch:jar:1.9.0
 *    com.microsoft.azure:azure-mgmt-dns:jar:1.9.0
 *    com.microsoft.azure:azure-keyvault:jar:1.0.0
 *    com.microsoft.azure:azure-mgmt-containerservice:jar:1.9.0
 *    com.microsoft.rest:client-runtime:jar:1.6.6
 *    com.microsoft.azure:azure-mgmt-servicebus:jar:1.9.0
 *    com.microsoft.azure:azure-mgmt-batchai:jar:1.9.0
 *    com.microsoft.azure:azure-mgmt-search:jar:1.9.0
 *    com.squareup.retrofit2:retrofit:jar:2.5.0
 *    com.microsoft.azure:azure-storage:jar:6.1.0
 *    com.microsoft.azure:adal4j:jar:1.1.2
 */


//  Import the required libraries.
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
 *	- Recognizing printed text with OCR
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
    System.out.println("\nLocal image path:\n" + workingDirectory + "\\" + localImagePath);

    DescribeLocalImage(computerVisionClient, localImagePath);
  	CategorizeLocalImage(computerVisionClient, localImagePath);
  	TagLocalImage(computerVisionClient, localImagePath);
  	DetectFacesLocalImage(computerVisionClient, localImagePath);
  	DetectAdultOrRacyContentLocalImage(computerVisionClient, localImagePath);
  	DetectColorSchemeLocalImage(computerVisionClient, localImagePath);
  	DetectDomainSpecificContentLocalImage(computerVisionClient, localImagePath);
  	DetectImageTypesLocalImage(computerVisionClient, localImagePath);
    //  END - Analyze a local image

    //  Get a new local image for recognizing text with OCR
    String imgPath = "src\\main\\resources\\printed_text.jpg";
    RecognizeTextOCRLocal(computerVisionClient, imgPath);

    //  Analyze a remote image
    String remoteImageURL = "https://github.com/Azure-Samples/cognitive-services-sample-data-files/raw/master/ComputerVision/Images/faces.jpg";
    System.out.println("\nRemote image path: \n" + remoteImageURL);

    DescribeRemoteImage(computerVisionClient, remoteImageURL);
    CategorizeRemoteImage(computerVisionClient, remoteImageURL);
    TagRemoteImage(computerVisionClient, remoteImageURL);
    DetectFacesRemoteImage(computerVisionClient, remoteImageURL);
    DetectAdultOrRacyContentRemoteImage(computerVisionClient, remoteImageURL);
    DetectColorSchemeRemoteImage(computerVisionClient, remoteImageURL);
    DetectDomainSpecificContentRemoteImage(computerVisionClient, remoteImageURL);
    DetectImageTypesRemoteImage(computerVisionClient, remoteImageURL);
    //  END - Analyze a remote image

    //  Get a new remote image for recognizing text with OCR
    remoteImageURL = "https://github.com/Azure-Samples/cognitive-services-sample-data-files/raw/master/ComputerVision/Images/printed_text.jpg";
    RecognizeTextOCRRemote(computerVisionClient, remoteImageURL);
  }


  /*  Describe a local image by:
   *    1. Getting the raw image bytes.
   *    2. Defining what to extract from the image by initializing an array of VisualFeatureTypes.
   *    3. Calling the Computer Vision service's AnalyzeImageInStream with the:
   *       - image
   *       - Language in which to return the descriptions
   *       - number of candidate descriptions to return
   *    4. Displaying the image captions and their confidence values.
   */
  public static void DescribeLocalImage(ComputerVisionClient client, String localImagePath) {
    try {
        File localImage = new File(localImagePath);
        byte[] imgBytes = Files.readAllBytes(localImage.toPath());

        ImageDescription analysis = client.computerVision().describeImageInStream()
            .withImage(imgBytes)
            .withLanguage("en")
            .withMaxCandidates("1")
            .execute();

        System.out.println("\nCaptions from local image: ");
        if (analysis.captions().size() == 0) {
          System.out.println("No captions detected in local image.");
        } else {
          for (ImageCaption caption : analysis.captions()) {
              System.out.printf("\'%s\' with confidence %2.2f%%\n", caption.text(), caption.confidence() * 100);
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
  *       - Language in which to return the descriptions
  *       - number of candidate descriptions to return
  *    3. Displaying the image captions and their confidence values.
  */
  public static void DescribeRemoteImage(ComputerVisionClient client, String remoteImageURL) {
    try {
        ImageDescription analysis = client.computerVision().describeImage()
            .withUrl(remoteImageURL)
            .withLanguage("en")
            .withMaxCandidates("1")
            .execute();

        System.out.println("\nCaptions from remote image: ");
        if (analysis.captions().size() == 0) {
          System.out.println("No captions detected in remote image.");
        } else {
          for (ImageCaption caption : analysis.captions()) {
              System.out.printf("\'%s\' with confidence %2.2f%%\n", caption.text(), caption.confidence() * 100);
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
            System.out.printf("\'%s\' with confidence %2.2f%%\n", category.name(), category.score() * 100);
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
            System.out.printf("\'%s\' with confidence %2.2f%%\n", category.name(), category.score() * 100);
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
   *    3. Calling the Computer Vision service's tagImageInStream with the:
   *       - image
   *       - language in which to display the tags
   *    4. Displaying the image captions and their confidence values.
   */
  public static void TagLocalImage(ComputerVisionClient client, String localImagePath) {
    try {
      File localImage = new File(localImagePath);
      byte[] imgBytes = Files.readAllBytes(localImage.toPath());

      TagResult analysis = client.computerVision().tagImageInStream()
          .withImage(imgBytes)
          .withLanguage("en")
          .execute();

      System.out.println("\nTags from local image: ");
      if (analysis.tags().size() == 0) {
        System.out.println("No tags detected in local image.");
      } else {
        for (ImageTag tag : analysis.tags()) {
            System.out.printf("\'%s\' with confidence %2.2f%%\n", tag.name(), tag.confidence() * 100);
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
  *    2. Calling the Computer Vision service's tagImage with the:
  *       - image URL
  *       - language in which to display the tags
  *    3. Displaying the image captions and their confidence values.
   */
  public static void TagRemoteImage(ComputerVisionClient client, String remoteImageURL) {
    try {
      TagResult analysis = client.computerVision().tagImage()
          .withUrl(remoteImageURL)
          .withLanguage("en")
          .execute();

      System.out.println("\nTags from local image: ");
      if (analysis.tags().size() == 0) {
        System.out.println("No tags detected in local image.");
      } else {
        for (ImageTag tag : analysis.tags()) {
            System.out.printf("\'%s\' with confidence %2.2f%%\n", tag.name(), tag.confidence() * 100);
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
      System.out.printf("Is adult content: %b with confidence %2.2f%%\n", analysis.adult().isAdultContent(), analysis.adult().adultScore() * 100);
      System.out.printf("Has racy content: %b with confidence %2.2f%%\n", analysis.adult().isRacyContent(), analysis.adult().racyScore() * 100);

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
      System.out.printf("Is adult content: %b with confidence %2.2f%%\n", analysis.adult().isAdultContent(), analysis.adult().adultScore() * 100);
      System.out.printf("Has racy content: %b with confidence %2.2f%%\n", analysis.adult().isRacyContent(), analysis.adult().racyScore() * 100);

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
   *    2. Calling the Computer Vision service's analyzeImageByDomainInStream with the:
   *       - image
   *       - features to extract
   *    3. Displaying the image captions and their confidence values.
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
          if (category.detail() != null && category.detail().celebrities() != null)
          {
              for (CelebritiesModel celeb : category.detail().celebrities())
              {
                  System.out.printf("\'%s\' with confidence %2.2f%% at location (%d, %d), (%d, %d)\n",
                      celeb.name(), celeb.confidence() * 100,
                      celeb.faceRectangle().left(), celeb.faceRectangle().top(),
                      celeb.faceRectangle().left() + celeb.faceRectangle().width(),
                      celeb.faceRectangle().top() + celeb.faceRectangle().height());
              }
          } else {
            System.out.println("No celebrities in local image.");
          }
      }

      System.out.println("\nLandmarks in local image: ");
      for (Category category : analysis.categories()) {
          if (category.detail() != null && category.detail().landmarks() != null) {
              for (LandmarksModel landmark : category.detail().landmarks()) {
                  System.out.printf("\'%s\' with confidence %2.2f%%\n", landmark.name(), landmark.confidence() * 100);
              }
          } else {
            System.out.println("No landmarks in local image.");
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
      for (Category category : analysis.categories()) {
          if (category.detail() != null && category.detail().celebrities() != null) {
              for (CelebritiesModel celeb : category.detail().celebrities()) {
                  System.out.printf("\'%s\' with confidence %2.2f%% at location (%d, %d), (%d, %d)\n",
                      celeb.name(), celeb.confidence() * 100,
                      celeb.faceRectangle().left(), celeb.faceRectangle().top(),
                      celeb.faceRectangle().left() + celeb.faceRectangle().width(),
                      celeb.faceRectangle().top() + celeb.faceRectangle().height());
              }
          } else {
            System.out.println("No celebrities in remote image.");
          }
      }

      System.out.println("\nLandmarks in remote image: ");
      for (Category category : analysis.categories())
      {
          if (category.detail() != null && category.detail().landmarks() != null) {
              for (LandmarksModel landmark : category.detail().landmarks()) {
                  System.out.printf("\'%s\' with confidence %2.2f%%\n", landmark.name(), landmark.confidence() * 100);
              }
          } else {
            System.out.println("No landmarks in remote image.");
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
        System.out.println("Image is not a line drawing.");
      }

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
        System.out.println("Image is not a line drawing.");
      }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Detect image type of a remote image


  /*  Extract text with OCR from a local image by:
   *    1. Getting the raw image bytes.
   *    2. Calling the Computer Vision service's RecognizePrintedTextInStream with the:
   *		   - whether to detect the text orientation
   *       - image
   *       - language
   *    3. Displaying the extracted text and the bounding boxes.
   */
  private static void RecognizeTextOCRLocal(ComputerVisionClient computerVisionClient, String localImagePath){
    try {
        File rawImage = new File(localImagePath);
        byte[] localImageBytes = Files.readAllBytes(rawImage.toPath());

        OcrResult ocrResult = computerVisionClient.computerVision().recognizePrintedTextInStream()
            .withDetectOrientation(true)
            .withImage(localImageBytes)
            .withLanguage(OcrLanguages.EN)
            .execute();

        System.out.println("\nExtracting text from local image with OCR ...");
        System.out.println("\nLanguage: " + ocrResult.language());
        System.out.printf("Text angle: %1.3f\n", ocrResult.textAngle());
        System.out.println("Orientation: " + ocrResult.orientation());

        for (OcrRegion reg : ocrResult.regions()) {
            for (OcrLine line : reg.lines()) {
              System.out.print("\nLine:");
                for (OcrWord word : line.words()) {
                    System.out.println("\nWord bounding box: " + word.boundingBox());
                    System.out.println("Text: " + word.text() + " ");
                }
                System.out.println();
            }
            // System.out.println();
        }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Extract text with OCR from a local image


  /*  Extract text with OCR from a remote image by:
   *    1. Calling the Computer Vision service's RecognizePrintedTextInStream with the:
   *		   - whether to detect the text orientation
   *       - image
   *       - language
   *    2. Displaying the extracted text and the bounding boxes.
   */
  private static void RecognizeTextOCRRemote(ComputerVisionClient computerVisionClient, String remoteImageURL){
    try {
        OcrResult ocrResult = computerVisionClient.computerVision().recognizePrintedText()
            .withDetectOrientation(true)
            .withUrl(remoteImageURL)
            .withLanguage(OcrLanguages.EN)
            .execute();

        System.out.println("\nExtracting text from remote image with OCR ...");
        System.out.println("\nLanguage: " + ocrResult.language());
        System.out.printf("Text angle: %1.3f\n", ocrResult.textAngle());
        System.out.println("Orientation: " + ocrResult.orientation());

        for (OcrRegion reg : ocrResult.regions()) {
            for (OcrLine line : reg.lines()) {
              System.out.print("\nLine:");
                for (OcrWord word : line.words()) {
                    System.out.println("\nWord bounding box: " + word.boundingBox());
                    System.out.println("Text: " + word.text() + " ");
                }
                System.out.println();
            }
            // System.out.println();
        }

    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
  }
  //  END - Extract text with OCR from a remote image
}
