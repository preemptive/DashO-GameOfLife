package io.kimo.examples.gameoflifeview;

import android.os.Bundle;

import com.preemptive.dasho.example.gameoflife.AbstractGameOfLifeActivity;

import io.kimo.gameoflifeview.view.GameOfLifeView;

public class CustomParamsActivity extends AbstractGameOfLifeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.with_custom_params);
        Utils.configureToolbar(this, true);
        setTitle("Custom Parameters");

        setupGameOfLife((GameOfLifeView)findViewById(R.id.game_of_life));
    }


}
