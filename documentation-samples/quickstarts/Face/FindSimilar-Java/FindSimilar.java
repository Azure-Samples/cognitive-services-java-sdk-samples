import com.microsoft.azure.cognitiveservices.vision.faceapi.*;
import com.microsoft.azure.cognitiveservices.vision.faceapi.models.*;

import java.io.*;
import java.lang.Object.*;
import java.util.*;
import java.net.*;

/**
 * Libraries needed: use these until the Face SDK is updated: see dependencies.txt for list of libraries
 * To compile and run, enter the following at a command prompt:
 *   javac FindSimilar.java -cp .;lib\*
 *   java -cp .;lib\* FindSimilar
 * This presumes your libraries are stored in a folder named "lib" in the same folder as MainClass.java. If not, adjust the -cp value accordingly.
 *
 * Note If you run this sample with JRE 9+, you may encounter the following issue: https://github.com/Azure/autorest-clientruntime-for-java/issues/569 which results in the following output:
 * WARNING: An illegal reflective access operation has occurred ... (plus several more warnings)
 *
 * This should not prevent the sample from running correctly.
 */

public class FindSimilar {

    // Add FACE_SUBSCRIPTION_KEY to your environment variables with your key as the value.
    private static String key = System.getenv("FACE_SUBSCRIPTION_KEY");

    // You must use the same region as you used to get your Face subscription keys. 
    // List of regions: https://docs.microsoft.com/en-us/java/api/com.microsoft.azure.cognitiveservices.vision.faceapi.models.azureregions?view=azure-java-stable
	private static AzureRegions region = AzureRegions.WESTUS;

    public static void main(String[] args) {
        /** 
         * Find Similar, Face API
         * This sample detects faces in a single-person image and in a group image. 
         * It then searches the group image to find a simlar face as seen in the single-person image.
         */

        /**
         * Authenticate
         */
        // This image should have a single face.
        final String singleFaceUrl = "https://www.biography.com/.image/t_share/MTQ1MzAyNzYzOTgxNTE0NTEz/john-f-kennedy---mini-biography.jpg";
        final String singleImageName = singleFaceUrl.substring( singleFaceUrl.lastIndexOf('/')+1, singleFaceUrl.length() );
        // This image should have several faces. At least one should be similar to the face in singleFaceImage.
        final String  groupFacesUrl = "http://www.historyplace.com/kennedy/president-family-portrait-closeup.jpg";
        final String groupImageName = groupFacesUrl.substring( groupFacesUrl.lastIndexOf('/')+1, groupFacesUrl.length() );
        // Create Face client
        FaceAPI client = FaceAPIManager.authenticate(region, key);

        /**
         * Detect the faces in both images
         */
        System.out.println();
        System.out.println("Detecting faces ...");
        // Create face ID list for single face image.
        List<DetectedFace> listSingle = client.faces().detectWithUrl(singleFaceUrl, new DetectWithUrlOptionalParameter().withReturnFaceId(true));
        System.out.println("Detected face ID from single image: " + singleImageName  + " :");
        // Get & display single face UUID
        System.out.println(listSingle.get(0).faceId()); 

        // Create face IDs list for group image.
        List<DetectedFace> listGroup = client.faces().detectWithUrl(groupFacesUrl, new DetectWithUrlOptionalParameter().withReturnFaceId(true));
        System.out.println("Detected face IDs from group image: " + groupImageName  + " :");
        // Get group face UUIDs
        List<UUID> groupListOfUuids = new ArrayList<>();
        for (DetectedFace face : listGroup) {
            groupListOfUuids.add(face.faceId());
            System.out.println(face.faceId()); 
        } 
        System.out.println();

        /**
         * Find similar faces
         */
        // Search for single face in the group image
        List<SimilarFace> listSimilars = client.faces().findSimilar(listSingle.get(0).faceId(), new FindSimilarOptionalParameter().withFaceIds(groupListOfUuids));
        // Display the similar faces found
        System.out.println();
        System.out.println("Similar faces found in group photo " + groupImageName + " are:");
        for (SimilarFace face : listSimilars) {
            System.out.println("Face ID: " + face.faceId());
            // Get and print the level of certainty that there is a match
            // Confidence range is 0.0 to 1.0. Closer to 1.0 is more confident
            System.out.println("Confidence: " + face.confidence());
        }

        // END - Find Similar, Face API

        
    }
}

