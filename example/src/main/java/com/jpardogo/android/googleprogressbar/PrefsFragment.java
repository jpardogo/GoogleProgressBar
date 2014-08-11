package com.jpardogo.android.googleprogressbar;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        final ListPreference listPreference = (ListPreference) findPreference(getString(R.string.progressBar_pref_key));
        if(listPreference.getValue()==null) {
            // to ensure we don't get a null value
            // set first value by default
            listPreference.setValueIndex(Integer.parseInt(getString(R.string.progressBar_pref_defValue)));
        }
        listPreference.setSummary(listPreference.getEntries()[Integer.parseInt(listPreference.getValue())].toString());
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(listPreference.getEntries()[Integer.parseInt(newValue.toString())]);
                return true;
            }
        });
    }
}