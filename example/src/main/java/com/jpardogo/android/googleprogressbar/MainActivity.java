package com.jpardogo.android.googleprogressbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleMusicDicesDrawable;
import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity {

    private final int FOLDING_CIRCLES = 0;
    private final int MUSIC_DICES = 1;
    private final int NEXUS_CROSS_ROTATION = 2;
    /**
     * Dynamically
     */
    @InjectView(R.id.google_progress)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**Dynamically*/
        Rect bounds = mProgressBar.getIndeterminateDrawable().getBounds();
        mProgressBar.setIndeterminateDrawable(getProgressDrawable());
        mProgressBar.getIndeterminateDrawable().setBounds(bounds);
    }

    private Drawable getProgressDrawable() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int value = Integer.parseInt(prefs.getString(getString(R.string.progressBar_pref_key), getString(R.string.progressBar_pref_defValue)));
        Drawable progressDrawable = null;
        switch (value) {
            case FOLDING_CIRCLES:
                progressDrawable = new FoldingCirclesDrawable.Builder(this)
                        .colors(getResources().getIntArray(R.array.rainbow))
                        .build();
                break;

            case MUSIC_DICES:
                progressDrawable = new GoogleMusicDicesDrawable.Builder().build();
                break;

            case NEXUS_CROSS_ROTATION:
                progressDrawable = new NexusRotationCrossDrawable.Builder(this)
                        .colors(getResources().getIntArray(R.array.rainbow))
                        .build();
                break;
        }

        return progressDrawable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(SettingsActivity.newInstance(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
