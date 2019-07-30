// <snippet_imports>
import com.google.gson.*;

import com.microsoft.azure.cognitiveservices.vision.contentmoderator.*;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.*;

import java.io.*;
import java.lang.Object.*;
import java.util.*;
// </snippet_imports>

/** 
 * Prerequisites:
 * 1. Obtain an Azure Content Moderator resource 
 * 2. Clone or download this repo: https://github.com/Azure-Samples/cognitive-services-java-sdk-samples.git
 * 3. Use Java 8 or later
 * 4. Set and add your Content Moderator subscription key and endpoint environment variables in the authenticate section below.
 * 
 * How to run:
 *   Run in your favorite IDE or...
 *   To use the command line:
 *      - Make sure Maven is installed: https://maven.apache.org/install.html 
 *      - Run from the ContentModerator folder: mvn compile exec:java -Dexec.cleanupDaemonThreads=false
 *      - All dependencies will be automatically included. To list them (if desired): mvn dependency:list 
 * 
 * This sample reads two URL images from file, then moderates them. A successful response shows
 * a JSON representation of the moderation results.
 */ 

public class ContentModeratorQuickstart {

    // <snippet_evaluationdata>
    // Contains the image moderation results for an image, including text and face detection.
    public static class EvaluationData {
        // The URL of the evaluated image.
        public String ImageUrl;
        // The image moderation results.
        public Evaluate ImageModeration;
        // The text detection results.
        public OCR TextDetection;
        // The face detection results;
        public FoundFaces FaceDetection;
    }
    // </snippet_evaluationdata>

    public static void main(String[] args) {  
        // <snippet_client>
        /**
         * Authenticate
         */
        // Create a variable called CONTENT_MODERATOR_SUBSCRIPTION_KEY in your environment settings, with your key as its value.
        // Create an endpoint environment variables too, replace the first part ("westus") with your own, if needed.
        ContentModeratorClient client = ContentModeratorManager
            .authenticate(new AzureRegionBaseUrl()
            .fromString("CONTENT_MODERATOR_ENDPOINT"), System.getenv("CONTENT_MODERATOR_SUBSCRIPTION_KEY"));
        // </snippet_client>

        // <snippet_imagemod_iterate>
        // Create an object in which to store the image moderation results.
        List<EvaluationData> evaluationData = new ArrayList<EvaluationData>();
        
        /**
         * Read image URLs from the input file and evaluate/moderate each one.
         */
        // ImageFiles.txt is the file that contains the image URLs to evaluate.
        // Relative paths are relative to the execution directory.
        try (BufferedReader inputStream = new BufferedReader(new FileReader(new File("src\\main\\resources\\ImageFiles.txt")))){
            String line;
            while ((line = inputStream.readLine()) != null) {
                if (line.length() > 0) {
                    // Evalutate each line of text
                    BodyModelModel url = new BodyModelModel();
                    url.withDataRepresentation("URL");
                    url.withValue(line);
                    EvaluationData imageData = new EvaluationData(); 
                    imageData.ImageUrl = url.value();
                    // </snippet_imagemod_iterate>

                    // <snippet_imagemod_ar>
                    // Evaluate for adult and racy content.
                    imageData.ImageModeration = client.imageModerations().evaluateUrlInput("application/json", url, new EvaluateUrlInputOptionalParameter().withCacheImage(true));
                    Thread.sleep(1000);
                    // </snippet_imagemod_ar>

                    // <snippet_imagemod_text>
                    // Detect and extract text.
                    imageData.TextDetection = client.imageModerations().oCRUrlInput("eng", "application/json", url, new OCRUrlInputOptionalParameter().withCacheImage(true));
                    Thread.sleep(1000);
                    // </snippet_imagemod_text>

                    // <snippet_imagemod_faces>
                    // Detect faces.
                    imageData.FaceDetection = client.imageModerations().findFacesUrlInput("application/json", url, new FindFacesUrlInputOptionalParameter().withCacheImage(true));
                    Thread.sleep(1000);
                    // </snippet_imagemod_faces>

                    // <snippet_imagemod_storedata>
                    evaluationData.add(imageData);
                }
            }
            // </snippet_imagemod_storedata>

            // <snippet_imagemod_printdata>
            // Save the moderation results to a file.
            // ModerationOutput.json is the file to contain the output from the evaluation.
            // Relative paths are relative to the execution directory.
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src\\main\\resources\\ModerationOutput.json")));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();            
            System.out.println("adding imageData to file: " + gson.toJson(evaluationData).toString());
            writer.write(gson.toJson(evaluationData).toString());
            writer.close();
            // </snippet_imagemod_printdata>

        // <snippet_imagemod_catch>
        }   catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        // </snippet_imagemod_catch>
    }
} 
// END - Moderate Image with URL images in a file  
        
