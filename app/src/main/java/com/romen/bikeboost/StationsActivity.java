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
import java.util.ArrayList;
import java.util.List;

public class StationsActivity extends BaseActivity {

    private RecyclerView stationsRecyclerView;
    private StationsAdapter stationsAdapter;
    private List<Station> stationsList;
    private ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);

        initViews();
        setupBottomNavigation();
        setupRecyclerView();
        loadStations();
    }

    private void initViews() {
        stationsRecyclerView = findViewById(R.id.recyclerViewStations);

        profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(v -> showProfileDropdown(v));
    }

    private void setupRecyclerView() {
        stationsList = new ArrayList<>();
        stationsAdapter = new StationsAdapter(stationsList, this);
        stationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stationsRecyclerView.setAdapter(stationsAdapter);
    }

    private void loadStations() {
        stationsList.clear();

        // Sample station data - dalam implementasi nyata akan dari API atau database
        stationsList.add(new Station("Central Station", "Jl. Sudirman No. 1", 15, 20, 0.5f, true));
        stationsList.add(new Station("Mall Station", "Grand Mall Lt. B1", 8, 12, 1.2f, true));
        stationsList.add(new Station("University Hub", "Kampus Universitas", 12, 15, 2.1f, false));
        stationsList.add(new Station("Park Station", "Taman Kota", 10, 18, 0.8f, true));
        stationsList.add(new Station("Airport Terminal", "Bandara Internasional", 25, 30, 15.5f, true));
        stationsList.add(new Station("Beach Station", "Pantai Indah", 6, 10, 8.3f, true));
        stationsList.add(new Station("Hospital Station", "RS Umum Pusat", 8, 12, 3.2f, false));
        stationsList.add(new Station("Stadium Station", "Stadion Utama", 20, 25, 4.7f, true));
        stationsList.add(new Station("Metro Station", "Stasiun Kereta", 18, 22, 2.8f, true));
        stationsList.add(new Station("Tech Hub", "Kawasan IT", 14, 16, 6.1f, true));

        stationsAdapter.notifyDataSetChanged();
    }

    /**
     * Method untuk refresh data stations
     */
    public void refreshStations() {
        loadStations();
    }

    /**
     * Method untuk mencari station berdasarkan nama
     */
    public void searchStations(String query) {
        List<Station> filteredList = new ArrayList<>();

        for (Station station : stationsList) {
            if (station.getName().toLowerCase().contains(query.toLowerCase()) ||
                    station.getAddress().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(station);
            }
        }

        stationsAdapter.updateList(filteredList);
    }

    private void showProfileDropdown(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_profile_dropdown, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_my_profile) {
                Intent intent = new Intent(StationsActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_settings) {
                Intent intent = new Intent(StationsActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            } else if (itemId == R.id.menu_notifications) {
                Intent intent = new Intent(StationsActivity.this, StationsActivity.class);
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

        Intent intent = new Intent(StationsActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected String getCurrentTabName() {
        return "stations";
    }
}