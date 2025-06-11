package com.romen.bikeboost;

public class Achievement {
    private String title;
    private String description;
    private int iconResource;
    private int currentProgress;
    private int maxProgress;
    private boolean isCompleted;

    public Achievement(String title, String description, int iconResource,
                       int currentProgress, int maxProgress, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.iconResource = iconResource;
        this.currentProgress = currentProgress;
        this.maxProgress = maxProgress;
        this.isCompleted = isCompleted;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResource() {
        return iconResource;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public int getProgressPercentage() {
        if (maxProgress == 0) return 0;
        return (int) ((float) currentProgress / maxProgress * 100);
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        this.isCompleted = (currentProgress >= maxProgress);
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}