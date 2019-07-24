package io.kimo.examples.gameoflifeview;

import android.os.Bundle;

import com.preemptive.dasho.example.gameoflife.AbstractGameOfLifeActivity;

import io.kimo.gameoflifeview.view.GameOfLifeView;

public class ThroughCodeActivity extends AbstractGameOfLifeActivity {
    protected GameOfLifeView golView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        golView = new GameOfLifeView(this);
        setContentView(golView);
        setupGameOfLife(golView);
    }

}
