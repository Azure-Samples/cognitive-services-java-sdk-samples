import os, asyncio, uuid, json
from msrest.authentication import CognitiveServicesCredentials
from azure.cognitiveservices.vision.face import FaceClient
from azure.cognitiveservices.vision.face.models import SnapshotObjectType, OperationStatusType

# TODO1 This was causing errors and doesn't seem to be needed; probably can just be deleted.
# RecognitionModel for Face detection and PersonGroup. Use only if you create your own PersonGroup with this sample (instead of using a pre-existing PersonGroup).
# recognitionModel = RecognitionModel.Recognition02;

'''
A sample of migrating a person group from a source subscription in the EastUS region to a target subscription in the WestUS2 Region. You can change the source and target regions. Note that you'll need two different Face resources with two different regions in Azure. Provide your subscription keys from the two regions and general Azure face subscription ID (found in Overview of any service).
'''

# Source endpoint. Change the region if desired, for example 'eastus' could be changed to 'westus'.
SOURCE_ENDPOINT = 'https://eastus.api.cognitive.microsoft.com/'
# Source subscription key. Must match the source endpoint region.
SOURCE_KEY = '5dc51a177c3b4b4a9635bfee4b31282c'
# Source subscription ID.
SOURCE_ID = '85000fc4-997c-470d-b4f1-6f59d3d6cc11'
# Target endpoint. Change the region if desired.
TARGET_ENDPOINT = 'https://westus2.api.cognitive.microsoft.com/'
# Target subscription key. Must match the target endpoint region.
TARGET_KEY = '3c6fa14d510e4e02b9ab036c49357ee2'
# Target subscription ID.
TARGET_ID = '85000fc4-997c-470d-b4f1-6f59d3d6cc11'
TARGET_PERSON_GROUP_ID = str(uuid.uuid4())

'''
Authenticate
'''
# Create a new FaceClient instance for your source with authentication.
face_client_source = FaceClient(SOURCE_ENDPOINT, CognitiveServicesCredentials(SOURCE_KEY))
# Create a new FaceClient instance for your target with authentication.
face_client_target = FaceClient(TARGET_ENDPOINT, CognitiveServicesCredentials(TARGET_KEY))

''' 
# Take the snapshot of your PersonGroup in one region, then send a copy of it to another region.
'''
async def run():
    # STEP 1, use or create a person group.
    # For this sample use a preexisting PersonGroup. 
    # To create a PersonGroup, if you don't have one, refer to the Face documentation, then paste in your person group ID here.
    # You can call list_person_groups to print a list of preexisting PersonGroups.
    #person_group_id = 'my-person-group'
    person_group_id = 'winonapersongroup'
    # Display your PersonGroup to confirm its existence
# TODO1 Fix. PersonGroup objects aren't JSON serializable.
#    await display_person_group(face_client_source, person_group_id)

    # STEP 2, take a snapshot of your person group, then track status.
	# Note This must include all subscription IDs from which you want to access the snapshot.
    source_list = [SOURCE_ID, TARGET_ID] # You may have many sources, if transferring from many regions
    # Note Snapshot.take is not asynchronous.
    # For information about Snapshot.take see:
    # https://github.com/Azure/azure-sdk-for-python/blob/master/azure-cognitiveservices-vision-face/azure/cognitiveservices/vision/face/operations/snapshot_operations.py#L36
    take_snapshot_result = face_client_source.snapshot.take(
        type=SnapshotObjectType.person_group,
        object_id=person_group_id,
        apply_scope=source_list,
        # Set this to tell Snapshot.take to return the response; otherwise it returns None.
        raw=True
        )
    # Get operation ID from response for tracking
    # Snapshot.type return value is of type msrest.pipeline.ClientRawResponse. See:
    # https://docs.microsoft.com/en-us/python/api/msrest/msrest.pipeline.clientrawresponse?view=azure-python
    take_operation_id = take_snapshot_result.response.headers['Operation-Location'].replace('/operations/', '')

    print('Taking snapshot(', take_operation_id, ')... Started')

    # STEP 3, Wait for snapshot taking to complete.
    take_status = await wait_for_operation(face_client_source, take_operation_id)

    # Get snapshot id from response.
    snapshot_id = take_status.additional_properties['ResourceLocation'].replace ('/snapshots/', '')

    print('Snapshot ID:', snapshot_id)
    print('Taking snapshot... Done\n')

    # STEP 4, apply the snapshot to target region(s)
    # Note Snapshot.apply is not asynchronous.
    # For information about Snapshot.apply see:
    # https://github.com/Azure/azure-sdk-for-python/blob/master/azure-cognitiveservices-vision-face/azure/cognitiveservices/vision/face/operations/snapshot_operations.py#L366
    apply_snapshot_result = face_client_target.snapshot.apply(
        snapshot_id=snapshot_id,
        object_id=TARGET_PERSON_GROUP_ID, 
        )
    apply_operation_id = apply_snapshot_result.response.headers['Operation-Location'].replace('/operations/', '')
    print('Applying snapshot(operation ID: ', apply_operation_id, '... Started')

    # STEP 5, wait for applying snapshot process to complete.
    await wait_for_operation(face_client_target, apply_operation_id)
    print('Applying snapshot... Done\n')
    print('End of transfer.')

# Prints a list of existing person groups.
def list_person_groups(face_client):
    # Note PersonGroup.list is not asynchronous.
    ids = list(map(lambda x: x.person_group_id, face_client.person_group.list()))
    for x in ids: print (x)

# Get your PersonGroup from your source region. 
async def display_person_group(client_source, person_group_id):
    # Note PersonGroup.get is not asynchronous.
    person_group = client_source.person_group.get(person_group_id)
    print('Person Group:')
    # TODO1 This isn't JSON serializable - just need to print the ID or something.
    print(json.dumps(person_group, indent=4))

    # List persons in PersonGroup.
    # Note PersonGroupPerson.list is not asynchronous.
    persons = client_source.person_group_person.list(person_group_id)
    for person in persons:
        # TODO1 This isn't JSON serializable - just need to print the ID or something.
        print(json.dumps(person, indent=4))

async def wait_for_operation(client, operation_id):
    # Track progress of taking the snapshot
    # Note Snapshot.get_operation_status is not asynchronous.
    # For information about Snapshot.get_operation_status see:
    # https://github.com/Azure/azure-sdk-for-python/blob/master/azure-cognitiveservices-vision-face/azure/cognitiveservices/vision/face/operations/snapshot_operations.py#L466
    # Set raw=True to tell Snapshot.get_operation_status to return the raw response so we can see the headers.
    result = client.snapshot.get_operation_status(operation_id=operation_id)

    # TODO1 If we call get_operation_status with raw=False (default), the return value is supposed to be
    # type OperationStatus instead of ClientRawResponse.
    # For information about OperationStatus, see:
    # https://github.com/Azure/azure-sdk-for-python/blob/master/azure-cognitiveservices-vision-face/azure/cognitiveservices/vision/face/models/operation_status.py#L15
    #
    # However, get_operation_status not returning an OperationStatus. It's returning a plain object
    # that looks like this:
    #  {'additional_properties': {'Status': 'succeeded', 'LastActionTime': '2019-05-07T18:42:58.0682132Z', 'Message': None, 'ResourceLocation': '/snapshots/92d24aff-498c-4b4a-9671-f7f0a721546c', 'CreatedTime': '2019-05-07T18:42:54.9835478Z'}, 'status': None, 'created_time': None, 'last_action_time': None, 'resource_location': None, 'message': None}
    #
    # See:
    # https://github.com/Azure/azure-sdk-for-python/blob/master/azure-cognitiveservices-vision-face/azure/cognitiveservices/vision/face/operations/snapshot_operations.py#L508
    # ```
    # deserialized = None
    # if response.status_code == 200:
    #     deserialized = self._deserialize('OperationStatus', response)
    # if raw:
    #     client_raw_response = ClientRawResponse(deserialized, response)
    #     return client_raw_response
    # return deserialized
    # ```

    # TODO1 This isn't printing output until the entire program is finished, so to the user it looks as if
    # it is just hanging while in fact we are waiting for the operation result.

    status = result.additional_properties['Status']
    print('Operation status: ', status)
    if (status == OperationStatusType.notstarted or status == OperationStatusType.running):
        print("Waiting 10 seconds...")
        await asyncio.sleep(10)
        result = await wait_for_operation(client, operation_id)
    return result

asyncio.run(run())