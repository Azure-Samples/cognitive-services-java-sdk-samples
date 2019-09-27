package com.azure.cognitiveservices.inkrecognitionsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            NoteTaker noteTaker = new NoteTaker(this);
            setContentView(noteTaker);
        } catch (Exception e) {
            System.out.println("Error in ink analysis");
        }
    }
}
