package com.azure.cognitiveservices.inkrecognitionsample;

import android.util.DisplayMetrics;
import com.azure.ai.inkrecognizer.InkPoint;
import com.azure.ai.inkrecognizer.InkStroke;
import com.azure.ai.inkrecognizer.InkStrokeKind;

import java.util.ArrayList;
import java.util.List;

class InkStrokeImplementor implements InkStroke {

    final private long strokeId;
    final private List<InkPoint> inkPoints = new ArrayList<>();
    final private String language;
    final private InkStrokeKind kind;
    private static int num = 0;
    private static final float INCH_TO_MM = 25.4f;

    InkStrokeImplementor() {
        this.strokeId = getNextNum();
        this.language = "en-US";
        this.kind = InkStrokeKind.UNKNOWN;
    }

    // Converts pixels to mm
    public void addPoint(float x, float y, DisplayMetrics displayMetrics) {
        float xdpi = displayMetrics.xdpi;
        float ydpi = displayMetrics.ydpi;
        InkPointImplementor point = new InkPointImplementor(x / xdpi * INCH_TO_MM, y / ydpi * INCH_TO_MM);
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