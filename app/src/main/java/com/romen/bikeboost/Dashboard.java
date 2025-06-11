package com.romen.bikeboost;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class Dashboard extends BaseActivity {

    private ProgressBar batteryProgress;
    private TextView batteryPercentage;
    private TextView batteryStatus;
    private TextView totalRides;
    private TextView totalDistance;
    private TextView co2Saved;
    private ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        // Initialize views
        initViews();

        // Setup bottom navigation
        setupBottomNavigation();

        // Set up battery progress and dashboard data
        setupBatteryProgress();
        setupDashboardStats();
    }


    private void initViews() {
        batteryProgress = findViewById(R.id.batteryProgress);
        batteryPercentage = findViewById(R.id.batteryPercentage);
//    totalRides = findViewById(R.id.totalRides);
        totalDistance = findViewById(R.id.totalDistance);
        co2Saved = findViewById(R.id.co2Saved);
        batteryStatus = findViewById(R.id.batteryStatus);



        profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(v -> showProfileDropdown(v));
    }


    private void setupBatteryProgress() {
        // Set battery level to 85%
        int batteryLevel = 85;
        batteryProgress.setProgress(batteryLevel);
        batteryProgress.setMax(100);
        batteryPercentage.setText(batteryLevel + "%");

        // Update battery status
        String status = getBatteryStatus(batteryLevel);
        if (batteryStatus != null) {
            batteryStatus.setText(status);
        }
    }

    private void setupDashboardStats() {
        // Sample data - dalam implementasi nyata, data ini akan diambil dari database/preferences
//        if (totalRides != null) {
//            totalRides.setText("142");
//        }

        if (totalDistance != null) {
            totalDistance.setText("1,247 km");
        }

        if (co2Saved != null) {
            co2Saved.setText("89.2 kg");
        }
    }

    private String getBatteryStatus(int batteryLevel) {
        if (batteryLevel >= 80) {
            return "Excellent";
        } else if (batteryLevel >= 50) {
            return "Good";
        } else if (batteryLevel >= 20) {
            return "Low";
        } else {
            return "Critical";
        }
    }
    private void showProfileDropdown(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_profile_dropdown, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_my_profile) {
                Intent intent = new Intent(Dashboard.this, ProfileActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_settings) {
                Intent intent = new Intent(Dashboard.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_notifications) {
                Intent intent = new Intent(Dashboard.this, StationsActivity.class);
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

        // Misal: clear user preferences kalau ada
        // SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        // prefs.edit().clear().apply();

        Intent intent = new Intent(Dashboard.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



    @Override
    protected String getCurrentTabName() {
        return "home";
    }
}
