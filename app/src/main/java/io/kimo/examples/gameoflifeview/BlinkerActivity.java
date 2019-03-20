package io.kimo.examples.gameoflifeview;

import android.os.Bundle;

import com.preemptive.dasho.example.gameoflife.AbstractGameOfLifeActivity;

import io.kimo.gameoflifeview.view.GameOfLifeView;

public class BlinkerActivity extends ThroughCodeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golView.setupBlinkers();
    }

}
