import os, asyncio, uuid, json
from msrest.authentication import CognitiveServicesCredentials
from azure.cognitiveservices.vision.face import FaceClient
from azure.cognitiveservices.vision.face.models import SnapshotObjectType, OperationStatusType

'''
A sample of migrating a person group from a source subscription in the EastUS region to a target subscription in the WestUS2 Region. You can change the source and target regions. Note that you'll need two different Face resources with two different regions in Azure. Provide your subscription keys from the two regions and general Azure face subscription ID (found in Overview of any service).
'''

# Source endpoint. Change the region if desired, for example 'eastus' could be changed to 'westus'.
SOURCE_ENDPOINT = 'https://eastus.api.cognitive.microsoft.com/'
# Source subscription key. Must match the source endpoint region.
SOURCE_KEY = 'INSERT SOURCE KEY HERE'
# Source subscription ID.
SOURCE_ID = 'INSERT SOURCE SUBSCRIPTION ID HERE'
# For this sample we use a preexisting PersonGroup. 
# To create a PersonGroup, if you don't have one, refer to the Face documentation, then paste in your person group ID here.
# You can call list_person_groups to print a list of preexisting PersonGroups.
# SOURCE_PERSON_GROUP_ID should be all lowercase and alphanumeric.
SOURCE_PERSON_GROUP_ID = 'INSERT SOURCE PERSON GROUP ID HERE'
# Target endpoint. Change the region (westus2) if desired.
TARGET_ENDPOINT = 'https://westus2.api.cognitive.microsoft.com/'
# Target subscription key. Must match the target endpoint region.
TARGET_KEY = 'INSERT TARGET KEY HERE'
# Target subscription ID. It will be the same as the source ID if created Face resources from the same subscription.
TARGET_ID = 'INSERT TARGET SUBSCRIPTION ID HERE'
# We do not need to specify the target PersonGroup ID here because we generate it ourselves later.

'''
Authenticate
'''
# Create a new FaceClient instance for your source with authentication.
face_client_source = FaceClient(SOURCE_ENDPOINT, CognitiveServicesCredentials(SOURCE_KEY))
# Create a new FaceClient instance for your target with authentication.
face_client_target = FaceClient(TARGET_ENDPOINT, CognitiveServicesCredentials(TARGET_KEY))

'''
Helper functions
'''
# Prints a list of existing person groups.
def list_person_groups(face_client):
    # Note PersonGroup.list is not asynchronous.
    ids = list(map(lambda x: x.person_group_id, face_client.person_group.list()))
    for x in ids: print (x)

# Prints a list of existing snapshots.
def list_snapshots(face_client):
    snapshots = face_client.snapshot.list()
    for x in snapshots:
        print ("Snapshot ID: " + x.id)
        print ("Snapshot type: " + x.type)
        print ()

# Delete the person group, if testing, since each execution creates a new person group in the target region.
# List the person groups in your account through the online testing console to check:
# https://westus2.dev.cognitive.microsoft.com/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395248
def delete_person_group(face_client, person_group_id):
    face_client.person_group.delete(person_group_id=person_group_id)
    print("Deleted the test person group {}.\n\n".format(person_group_id))

''' 
# Take the snapshot of your PersonGroup in one region, then send a copy of it to another region.
'''
async def run():
    # STEP 1, take a snapshot of your person group, then track status.
    # Note this list must include all subscription IDs from which you want to access the snapshot.
    source_list = [SOURCE_ID, TARGET_ID] # You may have many sources, if transferring from many regions
    # Remove any duplicates from the list. Passing the same subscription ID more than once causes
    # the Snapshot.take operation to fail.
    source_list = list(dict.fromkeys(source_list))

    # Note Snapshot.take is not asynchronous.
    # For information about Snapshot.take see:
    # https://github.com/Azure/azure-sdk-for-python/blob/master/azure-cognitiveservices-vision-face/azure/cognitiveservices/vision/face/operations/snapshot_operations.py#L36
    take_snapshot_result = face_client_source.snapshot.take(
        type=SnapshotObjectType.person_group,
        object_id=SOURCE_PERSON_GROUP_ID,
        apply_scope=source_list,
        # Set this to tell Snapshot.take to return the response; otherwise it returns None.
        raw=True
        )
    # Get operation ID from response for tracking
    # Snapshot.type return value is of type msrest.pipeline.ClientRawResponse. See:
    # https://docs.microsoft.com/en-us/python/api/msrest/msrest.pipeline.clientrawresponse?view=azure-python
    take_operation_id = take_snapshot_result.response.headers['Operation-Location'].replace('/operations/', '')

    print('Taking snapshot( operation ID:', take_operation_id, ')...')

    # STEP 2, Wait for snapshot taking to complete.
    take_status = await wait_for_operation(face_client_source, take_operation_id)

    # Get snapshot id from response.
    snapshot_id = take_status.additional_properties['ResourceLocation'].replace ('/snapshots/', '')

    print('Snapshot ID:', snapshot_id)
    print('Taking snapshot... Done\n')

    # STEP 3, apply the snapshot to target region(s)
    # Note Snapshot.apply is not asynchronous.
    # For information about Snapshot.apply see:
    # https://github.com/Azure/azure-sdk-for-python/blob/master/azure-cognitiveservices-vision-face/azure/cognitiveservices/vision/face/operations/snapshot_operations.py#L366
    target_person_group_id = str(uuid.uuid4())
    apply_snapshot_result = face_client_target.snapshot.apply(
        snapshot_id=snapshot_id,
        # Generate a new UUID for the target person group ID.
        object_id=target_person_group_id,
        # Set this to tell Snapshot.apply to return the response; otherwise it returns None.
        raw=True
        )
    apply_operation_id = apply_snapshot_result.response.headers['Operation-Location'].replace('/operations/', '')
    print('Applying snapshot( operation ID:', apply_operation_id, ')...')

    # STEP 4, wait for applying snapshot process to complete.
    await wait_for_operation(face_client_target, apply_operation_id)
    print('Applying snapshot... Done\n')
    print('End of transfer.')
    print()

    # Delete person group in target region after testing
    delete_person_group(face_client_target, target_person_group_id)

async def wait_for_operation(client, operation_id):
    # Track progress of taking the snapshot
    # Note Snapshot.get_operation_status is not asynchronous.
    # For information about Snapshot.get_operation_status see:
    # https://github.com/Azure/azure-sdk-for-python/blob/master/azure-cognitiveservices-vision-face/azure/cognitiveservices/vision/face/operations/snapshot_operations.py#L466
    result = client.snapshot.get_operation_status(operation_id=operation_id)

    status = result.additional_properties['Status'].lower()
    print('Operation status:', status)
    if ('notstarted' == status or 'running' == status):
        print("Waiting 10 seconds...")
        await asyncio.sleep(10)
        result = await wait_for_operation(client, operation_id)
    elif ('failed' == status):
        raise Exception("Operation failed. Reason:" + result.additional_properties['Message'])
    return result

asyncio.run(run())