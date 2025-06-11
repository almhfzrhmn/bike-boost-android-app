package com.romen.bikeboost;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.MenuInflater;
import android.widget.TextView;
import android.content.Intent;
import android.widget.LinearLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProfileActivity extends BaseActivity {

    private TextView tvUserName, tvUserHandle, tvTotalRides, tvTotalDistance;
    private TextView tvAvgSpeed, tvCO2Saved, tvWeatherStatus,tvRideHistory;
    private ImageView ivProfilePic, ivEditProfile, ivWeatherIcon, profileIcon;
    protected LinearLayout homeNav, achievementNav,stationNav;
    private BarChart monthlyProgressChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        setupBottomNavigation();

        initializeViews();
        setupUserData();
        setupStatistics();
        setupChart();
        setupClickListeners();
    }
    @Override
    protected  String getCurrentTabName() {
        return "profile";
    }

    private void initializeViews() {
        // Profile section
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserHandle = findViewById(R.id.tv_user_handle);
        ivProfilePic = findViewById(R.id.iv_profile_pic);
        ivEditProfile = findViewById(R.id.iv_edit_profile);

        // Weather section
//        tvWeatherStatus = findViewById(R.id.tv_weather_status);
//        ivWeatherIcon = findViewById(R.id.iv_weather_icon);

        // Statistics
        tvTotalRides = findViewById(R.id.tv_total_rides);
        tvTotalDistance = findViewById(R.id.tv_total_distance);
        tvAvgSpeed = findViewById(R.id.tv_avg_speed);
        tvCO2Saved = findViewById(R.id.tv_co2_saved);

        // Chart
        monthlyProgressChart = findViewById(R.id.monthly_progress_chart);

        // Bottom navigation cards
        homeNav = findViewById(R.id.nav_home);
        achievementNav = findViewById(R.id.nav_achievements);
        stationNav = findViewById(R.id.nav_stations);

        profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(v -> showProfileDropdown(v));
    }

    @SuppressLint("SetTextI18n")
    private void setupUserData() {
        tvUserName.setText("Roman");
        tvUserHandle.setText("roman");
//        tvWeatherStatus.setText("Sunny");

        // Set profile picture (you would load from URL or drawable)
        ivProfilePic.setImageResource(R.drawable.ic_profile);
//        ivWeatherIcon.setImageResource(R.drawable.ic_profile);
    }

    @SuppressLint("SetTextI18n")
    private void setupStatistics() {
        tvTotalRides.setText("156");
        tvTotalDistance.setText("1250 km");
        tvAvgSpeed.setText("18.5 km/h");
        tvCO2Saved.setText("85 kg");
    }

    private void setupChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();

        // Sample data for monthly progress (Jan to Dec)
        entries.add(new BarEntry(0f, 15f)); // Jan
        entries.add(new BarEntry(1f, 25f)); // Feb
        entries.add(new BarEntry(2f, 30f)); // Mar
        entries.add(new BarEntry(3f, 35f)); // Apr
        entries.add(new BarEntry(4f, 40f)); // May
        entries.add(new BarEntry(5f, 45f)); // Jun
        entries.add(new BarEntry(6f, 50f)); // Jul
        entries.add(new BarEntry(7f, 55f)); // Aug
        entries.add(new BarEntry(8f, 60f)); // Sep
        entries.add(new BarEntry(9f, 65f)); // Oct
        entries.add(new BarEntry(10f, 70f)); // Nov
        entries.add(new BarEntry(11f, 75f)); // Dec

        BarDataSet dataSet = new BarDataSet(entries, "Monthly Progress");
        dataSet.setColor(getResources().getColor(R.color.purple_primary));
        dataSet.setValueTextColor(getResources().getColor(android.R.color.white));

        BarData barData = new BarData(dataSet);
        monthlyProgressChart.setData(barData);
        monthlyProgressChart.getDescription().setEnabled(false);
        monthlyProgressChart.getLegend().setEnabled(false);
        monthlyProgressChart.getXAxis().setTextColor(getResources().getColor(android.R.color.white));
        monthlyProgressChart.getAxisLeft().setTextColor(getResources().getColor(android.R.color.white));
        monthlyProgressChart.getAxisRight().setEnabled(false);
        monthlyProgressChart.invalidate();
    }

    private void setupClickListeners() {
//        ivEditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle edit profile click
//                openEditProfile();
//            }
//        });

//        cvRideHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {openRideHistory();
//                // Handle ride history click
//                openRideHistory();
//            }
//        });

        achievementNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle achievements click
                openAchievements();
            }
        });

        stationNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle stations click
                openStations();
            }
        });
    }

//    private void openEditProfile() {
//        // Intent to edit profile activity
//        // Intent intent = new Intent(this, EditProfileActivity.class);
//        // startActivity(intent);
//    }

//    private void openRideHistory() {
//        // Intent to ride history activity
//        // Intent intent = new Intent(this, RideHistoryActivity.class);
//        // startActivity(intent);
//    }

    private void openAchievements() {
        // Intent to achievements activity
         Intent intent = new Intent(ProfileActivity.this, AchievementsActivity.class);
         startActivity(intent);
    }

    private void openStations() {
        // Intent to stations activity
         Intent intent = new Intent(ProfileActivity.this, StationsActivity.class);
         startActivity(intent);
    }

    private void showProfileDropdown(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_profile_dropdown, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_my_profile) {
                Toast.makeText(ProfileActivity.this, "You're already on Profile", Toast.LENGTH_SHORT).show();
                return true;

            } else if (itemId == R.id.menu_settings) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_notifications) {
                Intent intent = new Intent(ProfileActivity.this, StationsActivity.class);
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

        Intent intent = new Intent(ProfileActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}