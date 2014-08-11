package com.jpardogo.android.googleprogressbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by jpardogo on 08/08/2014.
 */
public class SettingsActivity extends PreferenceActivity {

    public static Intent newInstance(Context context){
        return new Intent(context,SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
}
