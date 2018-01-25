package io.kimo.examples.gameoflifeview;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

class Utils {

     static void configureToolbar(AppCompatActivity activity, boolean isChild) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if(toolbar != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(isChild);
        }
    }
}
