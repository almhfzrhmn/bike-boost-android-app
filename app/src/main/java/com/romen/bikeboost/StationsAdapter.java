package com.romen.bikeboost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationViewHolder> {

    private List<Station> stationsList;
    private Context context;
    private OnStationClickListener listener;

    public interface OnStationClickListener {
        void onStationClick(Station station);

        void onNavigateClick(Station station);
    }

    public StationsAdapter(List<Station> stationsList, Context context) {
        this.stationsList = stationsList;
        this.context = context;
    }

    public void setOnStationClickListener(OnStationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_station, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Station station = stationsList.get(position);
        holder.bind(station);
    }

    @Override
    public int getItemCount() {
        return stationsList.size();
    }

    public void updateList(List<Station> newList) {
        this.stationsList = newList;
        notifyDataSetChanged();
    }

    class StationViewHolder extends RecyclerView.ViewHolder {
        private TextView stationName;
        private TextView stationAddress;
        private TextView availableBikes;
        private TextView distance;
        private TextView statusText;
        private ProgressBar occupancyProgress;
        private ImageView statusIcon;
        private ImageView navigateButton;
        private View statusIndicator;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);

            stationName = itemView.findViewById(R.id.stationName);
            stationAddress = itemView.findViewById(R.id.stationAddress);
            availableBikes = itemView.findViewById(R.id.availableBikes);
            distance = itemView.findViewById(R.id.distance);
            statusText = itemView.findViewById(R.id.statusText);
            occupancyProgress = itemView.findViewById(R.id.occupancyProgress);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            navigateButton = itemView.findViewById(R.id.navigateButton);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
        }

        public void bind(Station station) {
            stationName.setText(station.getName());
            stationAddress.setText(station.getAddress());
            availableBikes.setText(station.getAvailableBikes() + "/" + station.getTotalCapacity() + " bikes");
            distance.setText(station.getDistanceText());
            statusText.setText(station.getAvailabilityStatus());

            // Set progress bar
            occupancyProgress.setProgress(station.getOccupancyPercentage());
            occupancyProgress.setMax(100);

            // Set status indicator color
            setStatusIndicator(station);

            // Set click listeners
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onStationClick(station);
                }
            });

            if (navigateButton != null) {
                navigateButton.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onNavigateClick(station);
                    }
                });
            }
        }

        private void setStatusIndicator(Station station) {
            if (!station.isActive()) {
                if (statusIndicator != null) {
                    statusIndicator.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                }
                if (statusIcon != null) {
                    statusIcon.setImageResource(R.drawable.ic_station_inactive);
                }
                return;
            }

            int percentage = station.getOccupancyPercentage();
            int color;
            int iconRes;

            if (percentage >= 80) {
                color = context.getResources().getColor(android.R.color.holo_red_light);
                iconRes = R.drawable.ic_station_full;
            } else if (percentage >= 50) {
                color = context.getResources().getColor(android.R.color.holo_green_light);
                iconRes = R.drawable.ic_station_available;
            } else if (percentage >= 20) {
                color = context.getResources().getColor(android.R.color.holo_orange_light);
                iconRes = R.drawable.ic_station_low;
            } else {
                color = context.getResources().getColor(android.R.color.holo_red_light);
                iconRes = R.drawable.ic_station_empty;
            }

            if (statusIndicator != null) {
                statusIndicator.setBackgroundColor(color);
            }

            if (statusIcon != null) {
                statusIcon.setImageResource(iconRes);
            }
        }
    }
}