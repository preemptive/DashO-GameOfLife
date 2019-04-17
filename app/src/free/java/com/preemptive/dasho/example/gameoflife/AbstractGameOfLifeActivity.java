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
    private Boolean locked;

    protected void setupGameOfLife(GameOfLifeView view) {
        this.gameOfLifeView = view;
        checkTheLock();
    }

    private boolean checkTheLock() {
        if (locked == null) {
            locked = false;
        }
        return locked;
    }

    private void setLocked(boolean b) {
        locked = b;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        long time = System.currentTimeMillis();
        if ((time - lastToastTime) > MIN_TIME_BETWEEN_TOASTS) {
            lastToastTime = time;
            Toast.makeText(this, locked ?
                    "Please upgrade now. Emulators are not supported with the free version."
                    : "Please upgrade now. The paid version allows you to interact.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locked) {
            gameOfLifeView.setupBlinkers();
        }
        gameOfLifeView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameOfLifeView.stop();
    }

}
