package com.example.surakchya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
       /* if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    // .replace(R.id.settings, new SettingsFragment())
                    .replace(R.id.emergencylog, new SettingsFragment())
                    .commit();
        }
        */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button button = (Button) findViewById(R.id.emergencylog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EmergencyLog.class);
                startActivity(intent);
            }
        });
        Button button1 = (Button) findViewById(R.id.record);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(),recordSetting.class);
             startActivity(intent);
            }
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}