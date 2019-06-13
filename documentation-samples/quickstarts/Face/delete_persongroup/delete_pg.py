import os
from msrest.authentication import CognitiveServicesCredentials
from azure.cognitiveservices.vision.face import FaceClient

# Replace 'westus' if it's not your region
BASE_URL = 'https://westus.api.cognitive.microsoft.com'
BASE_URL2 = 'https://eastus.api.cognitive.microsoft.com'

#Authenticate
face_client_source = FaceClient(BASE_URL, CognitiveServicesCredentials(os.getenv('FACE_SUBSCRIPTION_KEY')))
face_client_source2 = FaceClient(BASE_URL2, CognitiveServicesCredentials(os.getenv('FACE_SUBSCRIPTION_KEY2')))

# List all person groups in your region
def list_person_groups(face_client):
    # Note PersonGroup.list is not asynchronous.
    ids = list((map(lambda x: x.person_group_id, face_client.person_group.list())))
    print (ids)
    return ids

# Delete all person groups in your region
def delete_person_group(face_client, person_group_id):
    face_client.person_group.delete(person_group_id=person_group_id)
    print("Deleted the test person group {}.\n".format(person_group_id))

# RUN
# Create person group lists from two regions
list1  = list_person_groups(face_client_source)
list2 = list_person_groups(face_client_source2)

# Delete the items in list, region 1, if not empty
if (len(list1) > 0):
    for group in list1:
        delete_person_group(face_client_source, group)
    # run list again to verify deletion
    list_person_groups(face_client_source)

# Delete the items in list, region 2, if not empty
if (len(list2) > 0):
    for group in list2:
        delete_person_group(face_client_source2, group)
    # run list again to verify deletion
    list_person_groups(face_client_source2)