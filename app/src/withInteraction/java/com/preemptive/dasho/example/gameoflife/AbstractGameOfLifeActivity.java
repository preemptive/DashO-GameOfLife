package com.preemptive.dasho.example.gameoflife;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import io.kimo.gameoflifeview.view.GameOfLifeView;

/**
 *
 */

public abstract class AbstractGameOfLifeActivity extends AppCompatActivity {

    protected GameOfLifeView gameOfLifeView;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameOfLifeView != null) {
            gameOfLifeView.reviveCellsAt(event.getX(), event.getY());
        }
        return super.onTouchEvent(event);
    }
}
