package com.romen.bikeboost;

import android.content.Intent;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Base activity untuk menangani navigasi bottom nav yang konsisten
 * Semua activity utama harus extend dari BaseActivity ini
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected LinearLayout homeNav, achievementsNav, stationsNav;

    /**
     * Setup bottom navigation untuk semua activity
     * Method ini harus dipanggil di onCreate setelah setContentView
     */
    protected void setupBottomNavigation() {
        initNavigationViews();
        setupNavigationListeners();
        highlightCurrentTab();
    }

    private void initNavigationViews() {
        homeNav = findViewById(R.id.nav_home);
        achievementsNav = findViewById(R.id.nav_achievements);
        stationsNav = findViewById(R.id.nav_stations);

        if (homeNav == null || achievementsNav == null || stationsNav == null) {
            throw new RuntimeException("Navigation views not found. Make sure your layout includes bottom navigation.");
        }
    }

    private void setupNavigationListeners() {
        homeNav.setOnClickListener(v -> navigateToActivity(Dashboard.class, "home"));
        achievementsNav.setOnClickListener(v -> navigateToActivity(AchievementsActivity.class, "achievements"));
        stationsNav.setOnClickListener(v -> navigateToActivity(StationsActivity.class, "stations"));
    }

    private void navigateToActivity(Class<?> targetActivity, String currentTab) {
        // Jangan navigate jika sudah di activity yang sama
        if (this.getClass().equals(targetActivity)) {
            return;
        }

        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);

        // Tentukan animasi berdasarkan arah navigasi
        String currentActivityTab = getCurrentTabName();
        applyTransitionAnimation(currentActivityTab, currentTab);

        finish();
    }

    private void applyTransitionAnimation(String from, String to) {
        int fromIndex = getTabIndex(from);
        int toIndex = getTabIndex(to);

        if (fromIndex < toIndex) {
            // Slide ke kanan
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            // Slide ke kiri
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private int getTabIndex(String tabName) {
        switch (tabName) {
            case "home": return 0;
            case "achievements": return 1;
            case "stations": return 2;
            default: return 0;
        }
    }

    private void highlightCurrentTab() {
        // Reset semua tab
        resetAllTabs();

        // Highlight tab aktif
        String currentTab = getCurrentTabName();
        switch (currentTab) {
            case "home":
                homeNav.setAlpha(1.0f);
                homeNav.setSelected(true);
                break;
            case "achievements":
                achievementsNav.setAlpha(1.0f);
                achievementsNav.setSelected(true);
                break;
            case "stations":
                stationsNav.setAlpha(1.0f);
                stationsNav.setSelected(true);
                break;
        }
    }

    private void resetAllTabs() {
        float inactiveAlpha = 0.5f;

        homeNav.setAlpha(inactiveAlpha);
        homeNav.setSelected(false);

        achievementsNav.setAlpha(inactiveAlpha);
        achievementsNav.setSelected(false);

        stationsNav.setAlpha(inactiveAlpha);
        stationsNav.setSelected(false);
    }

    /**
     * Setiap activity harus implement method ini untuk menentukan tab mana yang aktif
     */
    protected abstract String getCurrentTabName();
}