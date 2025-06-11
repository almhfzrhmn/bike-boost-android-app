package com.romen.bikeboost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "BikeBoostSettings";

    // UI Components
    private Spinner languageSpinner;
    private TextView darkModeBtn, lightModeBtn;
    private TextView metricBtn, imperialBtn;
    private Switch rideUpdatesSwitch, achievementsSwitch, maintenanceAlertsSwitch,
            socialUpdatesSwitch, shareLocationSwitch, shareActivitySwitch,
            publicProfileSwitch, soundsSwitch, vibrationSwitch, autoSyncSwitch;
    private ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        initializeViews();
        setupListeners();
        loadSettings();
    }

    private void initializeViews() {
        // Profile icon for dropdown
        profileIcon = findViewById(R.id.profile_icon);

        // Display Settings
        languageSpinner = findViewById(R.id.language_spinner);
        darkModeBtn = findViewById(R.id.dark_mode_btn);
        lightModeBtn = findViewById(R.id.light_mode_btn);
        metricBtn = findViewById(R.id.metric_btn);
        imperialBtn = findViewById(R.id.imperial_btn);

        // Notification switches
        rideUpdatesSwitch = findViewById(R.id.ride_updates_switch);
        achievementsSwitch = findViewById(R.id.achievements_switch);
        maintenanceAlertsSwitch = findViewById(R.id.maintenance_alerts_switch);
        socialUpdatesSwitch = findViewById(R.id.social_updates_switch);

        // Privacy switches
        shareLocationSwitch = findViewById(R.id.share_location_switch);
        shareActivitySwitch = findViewById(R.id.share_activity_switch);
        publicProfileSwitch = findViewById(R.id.public_profile_switch);

        // Other switches
        soundsSwitch = findViewById(R.id.sounds_switch);
        vibrationSwitch = findViewById(R.id.vibration_switch);
        autoSyncSwitch = findViewById(R.id.auto_sync_switch);

        // Setup language spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
    }

    private void setupListeners() {
        // Profile dropdown
        profileIcon.setOnClickListener(v -> showProfileDropdown(v));

        // Theme selection
        darkModeBtn.setOnClickListener(v -> selectTheme(true));
        lightModeBtn.setOnClickListener(v -> selectTheme(false));

        // Units selection
        metricBtn.setOnClickListener(v -> selectUnits(true));
        imperialBtn.setOnClickListener(v -> selectUnits(false));

        // Language spinner
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveStringSetting("language", parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Switch listeners
        setupSwitchListener(rideUpdatesSwitch, "ride_updates");
        setupSwitchListener(achievementsSwitch, "achievements");
        setupSwitchListener(maintenanceAlertsSwitch, "maintenance_alerts");
        setupSwitchListener(socialUpdatesSwitch, "social_updates");
        setupSwitchListener(shareLocationSwitch, "share_location");
        setupSwitchListener(shareActivitySwitch, "share_activity");
        setupSwitchListener(publicProfileSwitch, "public_profile");
        setupSwitchListener(soundsSwitch, "sounds");
        setupSwitchListener(vibrationSwitch, "vibration");
        setupSwitchListener(autoSyncSwitch, "auto_sync");
    }

    private void setupSwitchListener(Switch switchView, String key) {
        switchView.setOnCheckedChangeListener((buttonView, isChecked) ->
                saveBooleanSetting(key, isChecked));
    }

    private void selectTheme(boolean isDarkMode) {
        if (isDarkMode) {
            darkModeBtn.setBackgroundResource(R.drawable.selected_button_bg);
            lightModeBtn.setBackgroundResource(R.drawable.unselected_button_bg);
        } else {
            lightModeBtn.setBackgroundResource(R.drawable.selected_button_bg);
            darkModeBtn.setBackgroundResource(R.drawable.unselected_button_bg);
        }
        saveBooleanSetting("dark_mode", isDarkMode);
    }

    private void selectUnits(boolean isMetric) {
        if (isMetric) {
            metricBtn.setBackgroundResource(R.drawable.selected_button_bg);
            imperialBtn.setBackgroundResource(R.drawable.unselected_button_bg);
        } else {
            imperialBtn.setBackgroundResource(R.drawable.selected_button_bg);
            metricBtn.setBackgroundResource(R.drawable.unselected_button_bg);
        }
        saveBooleanSetting("metric_units", isMetric);
    }

    private void loadSettings() {
        // Load theme
        boolean isDarkMode = sharedPreferences.getBoolean("dark_mode", true);
        selectTheme(isDarkMode);

        // Load units
        boolean isMetric = sharedPreferences.getBoolean("metric_units", true);
        selectUnits(isMetric);

        // Load language
        String language = sharedPreferences.getString("language", "English");
        ArrayAdapter adapter = (ArrayAdapter) languageSpinner.getAdapter();
        int position = adapter.getPosition(language);
        languageSpinner.setSelection(position);

        // Load switches
        rideUpdatesSwitch.setChecked(sharedPreferences.getBoolean("ride_updates", true));
        achievementsSwitch.setChecked(sharedPreferences.getBoolean("achievements", true));
        maintenanceAlertsSwitch.setChecked(sharedPreferences.getBoolean("maintenance_alerts", true));
        socialUpdatesSwitch.setChecked(sharedPreferences.getBoolean("social_updates", false));
        shareLocationSwitch.setChecked(sharedPreferences.getBoolean("share_location", true));
        shareActivitySwitch.setChecked(sharedPreferences.getBoolean("share_activity", true));
        publicProfileSwitch.setChecked(sharedPreferences.getBoolean("public_profile", false));
        soundsSwitch.setChecked(sharedPreferences.getBoolean("sounds", true));
        vibrationSwitch.setChecked(sharedPreferences.getBoolean("vibration", true));
        autoSyncSwitch.setChecked(sharedPreferences.getBoolean("auto_sync", true));
    }

    private void saveBooleanSetting(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    private void saveStringSetting(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    private void showProfileDropdown(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_profile_dropdown, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_my_profile) {
                Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_settings) {
                // Already in settings, maybe show toast
                Toast.makeText(this, "Already in Settings", Toast.LENGTH_SHORT).show();
                return true;

            } else if (itemId == R.id.menu_notifications) {
                Intent intent = new Intent(SettingsActivity.this, StationsActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_logout) {
                handleLogout();
                return true;
            }

            return false;
        });

        popup.show();
    }

    private void handleLogout() {
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();

        // Clear user preferences if needed
        // SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        // prefs.edit().clear().apply();

        Intent intent = new Intent(SettingsActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}