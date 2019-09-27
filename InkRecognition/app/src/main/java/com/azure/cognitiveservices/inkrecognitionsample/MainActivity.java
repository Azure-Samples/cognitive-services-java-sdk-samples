package com.azure.cognitiveservices.inkrecognitionsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "InkRecognizer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            NoteTaker noteTaker = new NoteTaker(this);
            setContentView(noteTaker);
        } catch (Exception e) {
            Log.d(TAG, "Error in ink analysis", e);
        }
    }
}
