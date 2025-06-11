package com.romen.bikeboost;
import java.util.Locale;

public class Station {
    private String name;
    private String address;
    private int availableBikes;
    private int totalCapacity;
    private float distance; // dalam km
    private boolean isActive;
    private String operatingHours;
    private double latitude;
    private double longitude;

    public Station(String name, String address, int availableBikes, int totalCapacity,
                   float distance, boolean isActive) {
        this.name = name;
        this.address = address;
        this.availableBikes = availableBikes;
        this.totalCapacity = totalCapacity;
        this.distance = distance;
        this.isActive = isActive;
        this.operatingHours = "24/7"; // Default
    }

    public Station(String name, String address, int availableBikes, int totalCapacity,
                   float distance, boolean isActive, String operatingHours,
                   double latitude, double longitude) {
        this(name, address, availableBikes, totalCapacity, distance, isActive);
        this.operatingHours = operatingHours;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public float getDistance() {
        return distance;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvailableBikes(int availableBikes) {
        this.availableBikes = availableBikes;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Utility methods
    public int getAvailableSlots() {
        return totalCapacity - availableBikes;
    }

    public int getOccupancyPercentage() {
        if (totalCapacity == 0) return 0;
        return (int) ((float) availableBikes / totalCapacity * 100);
    }

    public String getAvailabilityStatus() {
        if (!isActive) return "Inactive";

        int percentage = getOccupancyPercentage();
        if (percentage >= 80) return "Full";
        else if (percentage >= 50) return "Available";
        else if (percentage >= 20) return "Low Stock";
        else return "Almost Empty";
    }

    public String getDistanceText() {
        if (distance < 1.0f) {
            return String.format(Locale.US,"%.0f m", distance * 1000);
        } else {
            return String.format(Locale.US,"%.1f km", distance);
        }
    }
}