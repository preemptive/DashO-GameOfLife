package com.preemptive.dasho.example.gameoflife;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import io.kimo.gameoflifeview.view.GameOfLifeView;

/**
 * A base class of the Activities which show the GameOfLifeView that supports interaction.
 */

public abstract class AbstractGameOfLifeActivity extends AppCompatActivity {

    private GameOfLifeView gameOfLifeView;
    private Boolean locked;

    protected void setupGameOfLife(GameOfLifeView view) {
        gameOfLifeView = view;
        checkTheLock();
        gameOfLifeView.setAllowInteraction(!locked);
    }

    private void checkTheLock() {
        if (locked == null) {
            locked = false;
            return;
        }
    }

    private void setLocked(boolean b) {
        locked = b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Touch the screen to revive a cell.", Toast.LENGTH_SHORT).show();
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
