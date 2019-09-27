package com.azure.cognitiveservices.inkrecognitionsample;

import com.azure.ai.inkrecognizer.InkPoint;

class InkPointImplementor implements InkPoint {

    final private float x;
    final private float y;

    InkPointImplementor(float x, float y) {
        this.y = y;
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
