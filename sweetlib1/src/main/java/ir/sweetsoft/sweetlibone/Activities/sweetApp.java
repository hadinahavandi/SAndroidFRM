package ir.sweetsoft.sweetlibone.Activities;

import android.app.Application;

import common.FontsOverride;

/**
 * Created by Will on 1/24/2018.
 */

public class sweetApp extends Application{

    public void setDefaultFontName(String defaultFontName) {
        DefaultFontName = defaultFontName;
    }

    private String DefaultFontName="fonts/IRANSansMobile.ttf";
    @Override
    public void onCreate() {
        super.onCreate();

//set Custom Typeface

        if(DefaultFontName!=null)
        FontsOverride.setDefaultFont(this, "MONOSPACE", DefaultFontName);
    }
}
