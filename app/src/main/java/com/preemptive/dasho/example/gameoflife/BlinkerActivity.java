package com.preemptive.dasho.example.gameoflife;

import android.os.Bundle;
import io.kimo.examples.gameoflifeview.ThroughCodeActivity;
import io.kimo.gameoflifeview.Death;

@Death
public class BlinkerActivity extends ThroughCodeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golView.setupBlinkers();
    }

}
