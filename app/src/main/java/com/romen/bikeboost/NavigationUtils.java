package com.romen.bikeboost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Utility class untuk menangani navigation dan transition
 */
public class NavigationUtils {

    public static final String TAB_HOME = "home";
    public static final String TAB_ACHIEVEMENTS = "achievements";
    public static final String TAB_STATIONS = "stations";

    /**
     * Navigate ke activity dengan transition yang sesuai
     */
    public static void navigateWithTransition(Activity currentActivity, Class<?> targetActivity, String direction) {
        Intent intent = new Intent(currentActivity, targetActivity);
        currentActivity.startActivity(intent);

        // Apply transition berdasarkan direction
        switch (direction) {
            case "left":
                currentActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case "right":
                currentActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case "fade":
                currentActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            default:
                // No transition
                break;
        }

        currentActivity.finish();
    }

    /**
     * Get transition direction based on tab navigation
     */
    public static String getTransitionDirection(String fromTab, String toTab) {
        int fromIndex = getTabIndex(fromTab);
        int toIndex = getTabIndex(toTab);

        if (fromIndex < toIndex) {
            return "right";
        } else if (fromIndex > toIndex) {
            return "left";
        } else {
            return "none";
        }
    }

    private static int getTabIndex(String tabName) {
        switch (tabName) {
            case TAB_HOME: return 0;
            case TAB_ACHIEVEMENTS: return 1;
            case TAB_STATIONS: return 2;
            default: return 0;
        }
    }

    /**
     * Get activity class berdasarkan tab name
     */
    public static Class<?> getActivityClass(String tabName) {
        switch (tabName) {
            case TAB_HOME:
                return Dashboard.class;
            case TAB_ACHIEVEMENTS:
                return AchievementsActivity.class;
            case TAB_STATIONS:
                return StationsActivity.class;
            default:
                return Dashboard.class;
        }
    }

    /**
     * Check apakah user sudah login/register
     */
    public static boolean isUserLoggedIn(Context context) {
        // Implementasi check login status
        // Bisa menggunakan SharedPreferences
//        return true; // Sementara return true
        SharedPreferences prefs = context.getSharedPreferences("auth",context.MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }

    /**
     * Redirect ke login jika belum login
     */
    public static void redirectToLoginIfNeeded(Activity activity) {
        if (!isUserLoggedIn(activity)) {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}