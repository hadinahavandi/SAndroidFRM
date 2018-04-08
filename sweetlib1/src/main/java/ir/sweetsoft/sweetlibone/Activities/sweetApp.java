package ir.sweetsoft.sweetlibone.Activities;

import android.app.Application;

import common.FontsOverride;

/**
 * Created by Will on 1/24/2018.
 */

public class sweetApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

//set Custom Typeface

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/IRANSansMobile.ttf");
    }
}
