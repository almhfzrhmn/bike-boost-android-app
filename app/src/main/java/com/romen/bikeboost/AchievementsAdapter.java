package com.romen.bikeboost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder> {

    private List<Achievement> achievementsList;

    public AchievementsAdapter(List<Achievement> achievementsList) {
        this.achievementsList = achievementsList;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_achievement, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement achievement = achievementsList.get(position);

        holder.titleTextView.setText(achievement.getTitle());
        holder.descriptionTextView.setText(achievement.getDescription());
        holder.iconImageView.setImageResource(achievement.getIconResource());

        // Set progress
        holder.progressBar.setMax(achievement.getMaxProgress());
        holder.progressBar.setProgress(achievement.getCurrentProgress());

        // Set progress text
        String progressText = achievement.getCurrentProgress() + "/" + achievement.getMaxProgress();
        holder.progressTextView.setText(progressText);

        // Show completion checkmark if completed
        if (achievement.isCompleted()) {
            holder.checkmarkImageView.setVisibility(View.VISIBLE);
            holder.itemView.setAlpha(1.0f);
        } else {
            holder.checkmarkImageView.setVisibility(View.GONE);
            holder.itemView.setAlpha(0.8f);
        }
    }

    @Override
    public int getItemCount() {
        return achievementsList.size();
    }

    public void updateList(List<Achievement> newList) {
        this.achievementsList = newList;
        notifyDataSetChanged();
    }

    static class AchievementViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView titleTextView;
        TextView descriptionTextView;
        ProgressBar progressBar;
        TextView progressTextView;
        ImageView checkmarkImageView;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressTextView = itemView.findViewById(R.id.progressTextView);
            checkmarkImageView = itemView.findViewById(R.id.checkmarkImageView);
        }
    }
}