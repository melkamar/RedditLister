package cz.melkamar.redditlister.activities;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import cz.melkamar.redditlister.R;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 23.09.2017 16:46.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_visualizer);
    }
}
