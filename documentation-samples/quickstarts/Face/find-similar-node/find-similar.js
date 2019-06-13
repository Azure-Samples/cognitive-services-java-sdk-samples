/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 * 
 * Prerequisites:
 * Install the FaceAPI: npm i azure-cognitiveservices-face
 * Install credentials: npm i ms-rest-azure
 */
'use strict';
const Map = require('collections/map');
const List = require('collections/list');
const UUID = require('uuid');
const Face = require('azure-cognitiveservices-face');
const CognitiveServicesCredentials = require('ms-rest-azure').CognitiveServicesCredentials;

/**
 * Authenticate
 */
// Set the AZURE_FACE_KEY environment variable on your local machine,
// with your subscription key as a value.
let key = 'AZURE_FACE_KEY';
let credentials = new CognitiveServicesCredentials(process.env[key]);
// Change the region (westus) to match the one from your face resource in the Azure portal.
let client = new Face.FaceClient(credentials, 'https://eastus.api.cognitive.microsoft.com/');

/**
 * Detect face(s) in first image and in second image
 */
// First image URL
let firstImageUrl = 'https://www.biography.com/.image/t_share/MTQ1MzAyNzYzOTgxNTE0NTEz/john-f-kennedy---mini-biography.jpg';

// Detect a face in an image. Returns a Promise<DetectedFace[]>.
let firstDetectedFace = client.face.detectWithUrl(firstImageUrl, {returnFaceId:true})
                .then(function(value) {
                    // Display the detected face ID in the first image.
                    let id = value[0].faceId;
                    console.log(`Face ID found in 1st image: ${id}.`)
                    return id;
                }).catch(err => {
                    console.log('No faces detected in 1st image:' + firstImageUrl);
                    console.log(err.message);
                });

// Second image URL
let secondImageUrl = 'https://www.biography.com/.image/t_share/MTQ1NDY3OTIxMzExNzM3NjE3/john-f-kennedy---debating-richard-nixon.jpg';
// Detect a face in an image. Returns a Promise<DetectedFace[]>.
var secondDetectedFace = client.face.detectWithUrl(secondImageUrl, {returnFaceId:true})
                .then(faces => {
                    // Display the detected face ID in the second image.
                    let id = faces[0].faceId;
                    console.log(`Face ID found in 2nd image: ${id}`);
                    return id;
                }).catch (err => {
                    console.log(`No faces detected in 2nd image: ${secondImageUrl}.`);
                    console.log(err.message);
                });

/**
 * Find the similar face()s in the group image, then display face attributes
 */
// Returns a Promise<SimilarFace[]>.
client.face.findSimilar(firstDetectedFace, {returnFaceId:true})
                .then(similars => {
                    console.log('Similar faces found in 2nd image:');
                    for (let similar of similars) {
                        // Search in 2nd image for 1st image
                        console.log(`Face ID: ${similar.faceId}.`);
                        console.log('Face rectangle:');
                        console.log('Left:' + similar.faceRectangle.left.toString());
                        console.log('Top:' + similar.faceRectangle.top.toString());
                        console.log('Width:' + similar.faceRectangle.width.toString());
                        console.log('Height:' + similar.faceRectangle.height.toString());
                    }
                }).catch(err => {
                    console.log(`No similar face found in 2nd image: ${secondImageUrl}.`);
                    console.log(err.message);
                });

