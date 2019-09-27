package com.azure.cognitiveservices.inkrecognitionsample;

import com.azure.ai.inkrecognizer.*;

import java.util.ArrayList;
import java.util.List;

class InkStrokeImplementor implements InkStroke {

    final private long strokeId;
    final private List<InkPoint> inkPoints = new ArrayList<>();
    final private String language;
    final private InkStrokeKind kind;
    private static int num = 0;

    InkStrokeImplementor() {
        this.strokeId = getNextNum();
        this.language = "en-US";
        this.kind = InkStrokeKind.UNKNOWN;
    }

    public void addPoint(float x, float y) {
        InkPointImplementor point = new InkPointImplementor(x, y);
        inkPoints.add(point);
    }

    private int getNextNum() {
        return ++num;
    }

    public Iterable<InkPoint> getInkPoints() {
        return inkPoints;
    }

    public InkStrokeKind getKind() {
        return kind;
    }

    public long getId() {
        return strokeId;
    }

    public String getLanguage() {
        return language;
    }

}