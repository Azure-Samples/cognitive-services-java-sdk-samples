import com.microsoft.azure.cognitiveservices.vision.faceapi.*;
import com.microsoft.azure.cognitiveservices.vision.faceapi.models.*;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * PersonGroup - Face API sample
 * References: 
 *   How-to guide: https://docs.microsoft.com/en-us/azure/cognitive-services/face/face-api-how-to-topics/howtoidentifyfacesinimage
 *   SDK: https://docs.microsoft.com/en-us/java/api/com.microsoft.azure.cognitiveservices.vision.faceapi?view=azure-java-stable
 * Sample images to download: https://github.com/Microsoft/Cognitive-Face-Windows/tree/master/Data

 * To build/run sample from command line:
 *      javac -cp lib\*;. CreatePersonGroup.java
 *      java -cp lib\*;. CreatePersonGroup
 */

 public class CreatePersonGroup {

    // Group image for testing against
    private static String groupPhoto = "test-image.jpg";

    /**
     * Authentication
     */
    // Replace with a valid subscription key (keeping the quotes in place).
    private static String KEY = System.getenv("FACE_SUBSCRIPTION_KEY");
    // Replace westus, if it's not your region
    private static AzureRegions region = AzureRegions.WESTUS;

    public static void main(String[] args) {       
        // Create client.
        FaceAPI face_client = FaceAPIManager.authenticate(region, KEY);

        // Create Person Group.
        String personGroupID = personGroupCreate(face_client);
        // Detect and assign faces.
        detectFaces(face_client, personGroupID);
        // Train the Person Group
        trainPersonGroup(face_client, personGroupID);
        // Identify members of person group in group photo, if any
        identifyFace(face_client, groupPhoto, personGroupID);
        // Delete after testing, creating another with the same name gives error if exists
        face_client.personGroups().delete(personGroupID);
    }

    /**
     * Create the Person Group
     */
    public static String personGroupCreate(FaceAPI client) { 
        // Person group ID can be any lower-cased, unique string of characters.
        final String PERSON_GROUP_ID = UUID.randomUUID().toString();
        CreatePersonGroupsOptionalParameter param = new CreatePersonGroupsOptionalParameter();
        param = param.withName ("my-person-group");

        System.out.println("Person Group ID: " + PERSON_GROUP_ID);
        try {
            // Adds the person group ID to your client
            client.personGroups().create(PERSON_GROUP_ID, param);
            System.out.println("Person group created");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return PERSON_GROUP_ID;
    }

    /**
     * Detect faces in individual person photos and assign them to the correct person.
     */
    public static void detectFaces(FaceAPI client, String groupId) {
        // Create a person ID for woman.               
        UUID WOMAN_ID = UUID.randomUUID(); 
        // Create a person ID for man.
        UUID MAN_ID = UUID.randomUUID(); 
        // Create a person ID for child.
        UUID CHILD_ID = UUID.randomUUID(); 
        // Find all images of friends in working directory, then add to your PersonGroup.
        try {
            File dir = new File(System.getProperty("user.dir"));
            File[] files = dir.listFiles();
            for (File image : files) {
                if(image.getName().startsWith("w")){
                    byte[] womanBytes = Files.readAllBytes(image.toPath());
                    client.personGroupPersons().addPersonFaceFromStream(groupId, WOMAN_ID, womanBytes, null);
                } else if (image.getName().startsWith("m")){
                    byte[] manBytes = Files.readAllBytes(image.toPath());
                    client.personGroupPersons().addPersonFaceFromStream(groupId, MAN_ID, manBytes, null);
                } else if (image.getName().startsWith("c")){
                    byte[] childBytes = Files.readAllBytes(image.toPath());
                    client.personGroupPersons().addPersonFaceFromStream(groupId, CHILD_ID, childBytes, null);
                }
            } 
        } catch (Exception e){
            System.out.println(e.getMessage());
        }  
    }

    /**
     * Train PersonGroup, so it can be used in other Face applications
     */
    public static void trainPersonGroup(FaceAPI client, String groupId){
        try {
            client.personGroups().train(groupId);
            TrainingStatusType status = client.personGroups().getTrainingStatus(groupId).status();
            while (status == TrainingStatusType.RUNNING || status == TrainingStatusType.NONSTARTED) {
                System.out.println(status);
                TimeUnit.SECONDS.sleep(2);
            }
            System.out.println(status);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Identify a face against a defined PersonGroup
     */
    public static void identifyFace(FaceAPI client, String photoName, String groupId) {
        try {
            // Detect all faces in the group photo.
            File groupPhoto = new File(photoName);
            byte[] groupPhotoBytes = Files.readAllBytes(groupPhoto.toPath());
            List<DetectedFace> groupFaces = client.faces().detectWithStream(groupPhotoBytes, null);
            // Get the UUID from each DetectedFace face & add to list of UUIDs
            List<UUID> groupFaceIds = groupFaces.stream().map(DetectedFace::faceId).collect(Collectors.toList());

            // In group photo, identify all faces from PersonGroup previously created.
            List<IdentifyResult> results = client.faces().identify(groupId, groupFaceIds, null); 
            if (results.isEmpty()) {
                System.out.println("No person identified in the person group for faces from the " + groupPhoto.getName());
            } else {
                UUID personIdentified = null;
                for (IdentifyResult result : results ) {
                    personIdentified = result.faceId();
                    System.out.println(
                        "Person ID " + 
                        personIdentified + 
                        " was found in " + 
                        groupPhoto.getName() +
                        " with a confidence of " +
                        result.candidates().get(0) + // Gets the topmost confidence score
                        "."
                    );
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
 }