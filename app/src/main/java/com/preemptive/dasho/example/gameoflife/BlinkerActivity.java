package com.preemptive.dasho.example.gameoflife;

import android.os.Bundle;
import io.kimo.examples.gameoflifeview.ThroughCodeActivity;

public class BlinkerActivity extends ThroughCodeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golView.setupBlinkers();
    }

}
