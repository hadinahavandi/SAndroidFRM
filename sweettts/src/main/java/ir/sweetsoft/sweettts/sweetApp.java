package ir.sweetsoft.sweettts;

import android.app.Application;

import common.FontsOverride;

/**
 * Created by Will on 1/24/2018.
 */

public class sweetApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/UbuntuMono-B.ttf");

    }
}
