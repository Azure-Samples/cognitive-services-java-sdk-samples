package com.azure.cognitiveservices.inkrecognitionsample;

import android.util.DisplayMetrics;
import com.microsoft.azure.cognitiveservices.inkrecognizer.InkPoint;
import com.microsoft.azure.cognitiveservices.inkrecognizer.InkStroke;
import com.microsoft.azure.cognitiveservices.inkrecognizer.InkStrokeKind;

import java.util.ArrayList;
import java.util.List;

// <inkStrokeImplementation>
class InkStrokeImplementor implements InkStroke {

    final private long strokeId;
    final private List<InkPoint> inkPoints = new ArrayList<>();
    final private String language;
    final private InkStrokeKind kind;
    private static int num = 0;
    private static final float INCH_TO_MM = 25.4f;

    // <createInkStroke>
    InkStrokeImplementor() {
        this.strokeId = getNextNum();
        this.language = "en-US";
        this.kind = InkStrokeKind.UNKNOWN;
    }
    // </createInkStroke>

    // <addInkPoint>
    public void addPoint(float x, float y, DisplayMetrics displayMetrics) {
        // Converts pixels to mm
        float xdpi = displayMetrics.xdpi;
        float ydpi = displayMetrics.ydpi;
        InkPointImplementor point = new InkPointImplementor(x / xdpi * INCH_TO_MM, y / ydpi * INCH_TO_MM);
        inkPoints.add(point);
    }
    // </addInkPoint>

    private int getNextNum() {
        return ++num;
    }

    // <getInkPoints>
    public Iterable<InkPoint> getInkPoints() {
        return inkPoints;
    }
    // </getInkPoints>

    // <getInkStrokeKind>
    public InkStrokeKind getKind() {
        return kind;
    }
    // </getInkStrokeKind>

    // <getInkStrokeId>
    public long getId() {
        return strokeId;
    }
    // </getInkStrokeId>

    // <getInkStrokeLanguage>
    public String getLanguage() {
        return language;
    }
    // </getInkStrokeLanguage>

}
// </inkStrokeImplementation>