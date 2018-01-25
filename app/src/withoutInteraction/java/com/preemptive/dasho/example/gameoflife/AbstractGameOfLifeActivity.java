package com.preemptive.dasho.example.gameoflife;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import io.kimo.gameoflifeview.view.GameOfLifeView;

/**
 * A base class of the Activities which show the GameOfLifeView that does not support interaction.
 */

public abstract class AbstractGameOfLifeActivity extends AppCompatActivity {

    protected GameOfLifeView gameOfLifeView;

}
