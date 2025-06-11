package com.romen.bikeboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class AchievementsActivity extends BaseActivity {

    private RecyclerView achievementsRecyclerView;
    private AchievementsAdapter achievementsAdapter;
    private List<Achievement> allAchievements;
    private TabLayout tabLayout;
    private ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        initViews();
        setupBottomNavigation();
        setupTabLayout();
        setupRecyclerView();
        loadAchievements();
    }

    private void initViews() {
        achievementsRecyclerView = findViewById(R.id.recyclerViewAchievements);
        tabLayout = findViewById(R.id.tabLayout);

        profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(v -> showProfileDropdown(v));
    }

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("In Progress"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterAchievements(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupRecyclerView() {
        allAchievements = new ArrayList<>();
        achievementsAdapter = new AchievementsAdapter(new ArrayList<>());
        achievementsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        achievementsRecyclerView.setAdapter(achievementsAdapter);
    }

    private void loadAchievements() {
        allAchievements.clear();

        // Achievement data - dalam implementasi nyata bisa dari database
        allAchievements.add(new Achievement("Early Bird", "Complete 5 morning rides", R.drawable.ic_early_bird, 4, 5, false));
        allAchievements.add(new Achievement("Night Owl", "Complete 3 night rides", R.drawable.ic_night_owl, 2, 3, false));
        allAchievements.add(new Achievement("Weekend Warrior", "Ride 50km on weekends", R.drawable.ic_weekend_warrior, 35, 50, false));
        allAchievements.add(new Achievement("Earth Saver", "Save 100kg CO2", R.drawable.ic_earth_saver, 85, 100, false));
        allAchievements.add(new Achievement("Power Rider", "Complete 100 rides", R.drawable.ic_power_rider, 100, 100, true));
        allAchievements.add(new Achievement("Speed Demon", "Maintain 25km/h for 10min", R.drawable.ic_speed_demon, 8, 10, false));
        allAchievements.add(new Achievement("Battery Master", "Complete 20 full charges", R.drawable.ic_battery_master, 20, 20, true));
        allAchievements.add(new Achievement("Explorer", "Visit 10 new locations", R.drawable.ic_explorer, 7, 10, false));
        allAchievements.add(new Achievement("Eco Warrior", "Use ECO mode for 50 rides", R.drawable.ic_eco_warrior, 42, 50, false));
//        allAchievements.add(new Achievement("Distance King", "Ride 1000km total", R.drawable.ic_distance_king, 850, 1000, false));
//        allAchievements.add(new Achievement("Consistency Master", "Ride for 30 consecutive days", R.drawable.ic_consistency, 25, 30, false));
//        allAchievements.add(new Achievement("Social Rider", "Share 10 rides on social media", R.drawable.ic_social, 10, 10, true));

        // Show all achievements by default
        filterAchievements(0);
    }

    private void filterAchievements(int tabPosition) {
        List<Achievement> filteredList = new ArrayList<>();

        switch (tabPosition) {
            case 0: // All
                filteredList.addAll(allAchievements);
                break;
            case 1: // In Progress
                for (Achievement achievement : allAchievements) {
                    if (!achievement.isCompleted() && achievement.getCurrentProgress() > 0) {
                        filteredList.add(achievement);
                    }
                }
                break;
            case 2: // Completed
                for (Achievement achievement : allAchievements) {
                    if (achievement.isCompleted()) {
                        filteredList.add(achievement);
                    }
                }
                break;
        }

        achievementsAdapter.updateList(filteredList);
    }

    /**
     * Method untuk update progress achievement dari activity lain
     */
    public static void updateAchievementProgress(String achievementTitle, int newProgress) {
        // Implementasi untuk update progress achievement
        // Bisa menggunakan SharedPreferences atau database
    }

    /**
     * Method untuk mendapatkan jumlah achievement yang completed
     */
    public int getCompletedAchievementsCount() {
        int count = 0;
        for (Achievement achievement : allAchievements) {
            if (achievement.isCompleted()) {
                count++;
            }
        }
        return count;
    }

    private void showProfileDropdown(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_profile_dropdown, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_my_profile) {
                Intent intent = new Intent(AchievementsActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_settings) {
                Intent intent = new Intent(AchievementsActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_notifications) {
                Intent intent = new Intent(AchievementsActivity.this, StationsActivity.class);
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

        Intent intent = new Intent(AchievementsActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



    @Override
    protected String getCurrentTabName() {
        return "achievements";
    }
}