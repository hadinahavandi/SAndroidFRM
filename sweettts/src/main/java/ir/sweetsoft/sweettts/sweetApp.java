package ir.sweetsoft.sweettts;

import android.app.Application;

import common.FontsOverride;

/**
 * Created by Will on 1/24/2018.
 */

public class sweetApp extends ir.sweetsoft.sweetlibone.Activities.sweetApp{
    @Override
    public void onCreate() {
        this.setDefaultFontName(null);
        super.onCreate();

//        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/UbuntuMono-B.ttf");

    }
}
