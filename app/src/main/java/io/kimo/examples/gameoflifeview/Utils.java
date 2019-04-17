package io.kimo.examples.gameoflifeview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

class Utils {

     static void configureToolbar(AppCompatActivity activity, boolean isChild) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if(toolbar != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(isChild);
        }
    }
}
