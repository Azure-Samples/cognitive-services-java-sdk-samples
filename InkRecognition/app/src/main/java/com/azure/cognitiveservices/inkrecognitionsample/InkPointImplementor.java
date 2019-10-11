package com.azure.cognitiveservices.inkrecognitionsample;

import com.microsoft.azure.cognitiveservices.inkrecognizer.InkPoint;

// <inkPointImplementation>
class InkPointImplementor implements InkPoint {

    final private float x;
    final private float y;

    // <createInkPoint>
    InkPointImplementor(float x, float y) {
        this.y = y;
        this.x = x;
    }
    // </createInkPoint>

    // <getXCoordinate>
    public float getX() {
        return x;
    }
    // </getXCoordinate>

    // <getYCoordinate>
    public float getY() {
        return y;
    }
    // </getYCoordinate>

}
// </inkPointImplementation>
