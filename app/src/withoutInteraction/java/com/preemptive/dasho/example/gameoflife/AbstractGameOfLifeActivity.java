package com.preemptive.dasho.example.gameoflife;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import io.kimo.gameoflifeview.view.GameOfLifeView;

/**
 * A base class of the Activities which show the GameOfLifeView that does not support interaction.
 */

public abstract class AbstractGameOfLifeActivity extends AppCompatActivity {

    private GameOfLifeView gameOfLifeView;

    private long lastToastTime = 0;
    private static final long MIN_TIME_BETWEEN_TOASTS = 3000;

    protected void setupGameOfLife(GameOfLifeView view) {
        this.gameOfLifeView = view;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        long time = System.currentTimeMillis();
        if ((time - lastToastTime) > MIN_TIME_BETWEEN_TOASTS) {
            lastToastTime = time;
            Toast.makeText(this, "This is a non-interactive view", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameOfLifeView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameOfLifeView.stop();
    }

}
