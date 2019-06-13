import os, io, uuid, glob, time
from msrest.authentication import CognitiveServicesCredentials
from azure.cognitiveservices.vision.face import FaceClient
from azure.cognitiveservices.vision.face.models import TrainingStatusType, Person

'''
Create/Train/Detect/Identify Person Group - Face API sample
This sample creates a Person Group, then trains it. It can then be used to detect and identify faces in group images.
References: 
    How-to guide: https://docs.microsoft.com/en-us/azure/cognitive-services/face/face-api-how-to-topics/howtoidentifyfacesinimage
    SDK: https://docs.microsoft.com/en-us/python/api/azure-cognitiveservices-vision-face/azure.cognitiveservices.vision.face?view=azure-python
    Sample images to download: https://github.com/Microsoft/Cognitive-Face-Windows/tree/master/Data
Prerequisites:
    Python 3+
    Install Face SDK: pip install azure-cognitiveservices-vision-face
    Run your python file with all images in the same directory
'''
# Group image for testing against
group_photo = 'test-image-person-group.jpg'
IMAGES_FOLDER = os.path.join(os.path.dirname(os.path.realpath(__file__)))

''' 
Authentication
'''
# Replace 'westus' if it's not your region
BASE_URL = 'https://westus.api.cognitive.microsoft.com'
# Add FACE_SUBSCRIPTION_KEY to your environment variables with your key as the value.
face_client = FaceClient(BASE_URL, CognitiveServicesCredentials(os.getenv('FACE_SUBSCRIPTION_KEY')))

''' 
Create the PersonGroup
'''
# Create empty Person Group. Person Group ID must be lower case, alphanumeric, and/or with '-', '_'.
person_group_id = 'my-unique-person-group'
print(person_group_id)
face_client.person_group.create(person_group_id=person_group_id, name=person_group_id)

# Define woman friend 
woman = face_client.person_group_person.create(person_group_id, "Woman")
# Define man friend
man = face_client.person_group_person.create(person_group_id, "Man")
# Define child friend
child = face_client.person_group_person.create(person_group_id, "Child")

'''
Detect faces and register to correct person
'''
# Find all jpeg images of friends in working directory
woman_images = [file for file in glob.glob('*.jpg') if file.startswith("woman")]
man_images = [file for file in glob.glob('*.jpg') if file.startswith("man")]
child_images = [file for file in glob.glob('*.jpg') if file.startswith("child")]

# Add to a woman person
for image in woman_images:
    w = open(image, 'r+b')
    face_client.person_group_person.add_face_from_stream(person_group_id, woman.person_id, w)

# Add to a man person
for image in man_images:
    m = open(image, 'r+b')
    face_client.person_group_person.add_face_from_stream(person_group_id, man.person_id, m)

# Add to a child person
for image in child_images:
    ch = open(image, 'r+b')
    face_client.person_group_person.add_face_from_stream(person_group_id, child.person_id, ch)

''' 
Train PersonGroup
'''
# Train the person group
face_client.person_group.train(person_group_id)
training_status = face_client.person_group.get_training_status(person_group_id)
while (True):
    print("Training status {}:".format(training_status.status))
    if (training_status.status is TrainingStatusType.succeeded):
        break
    time.sleep(5)

'''
Identify a face against a defined PersonGroup
'''
# Get test image
test_image_array = glob.glob(os.path.join(IMAGES_FOLDER, group_photo))
image = open(test_image_array[0], 'r+b')

# Detect faces
face_ids = []
faces = face_client.face.detect_with_stream(image)
for face in faces:
    face_ids.append(face.face_id)

# Identify faces
results = face_client.face.identify(face_ids, person_group_id)
if not results:
    print('No person identified in the person group for faces from the {}.'.format(os.path.basename(image.name)))
for person in results:
    print('Person for face ID {} is identified in {} with a confidence of {}.'.format(person.face_id, os.path.basename(image.name), person.candidates[0].confidence)) # Get topmost confidence score

# Once finished, if testing, delete the PersonGroup from your resource, otherwise when you create it again, it won't allow duplicate person groups.
face_client.person_group.delete(person_group_id)

'''
END - Create/Train/Detect/Identify Person Group sample
'''