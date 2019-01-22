package com.white5703.akyuu.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;
import com.white5703.akyuu.R;
import java.util.Objects;

public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final SharedPreferences sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(Objects.requireNonNull(getActivity()));
        final Preference author = findPreference("pref_setting_about_author_key");
    }
}
