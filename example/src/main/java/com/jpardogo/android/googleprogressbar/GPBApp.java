package com.jpardogo.android.googleprogressbar;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * Created by jpardogo on 11/08/2014.
 */
public class GPBApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
}
