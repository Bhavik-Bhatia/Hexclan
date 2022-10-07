package com.example.sih_app.Student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sih_app.R;
import com.example.sih_app.Student.Models.UpcomingEventsModel;
import java.util.ArrayList;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder> {
    ArrayList<UpcomingEventsModel> upcomingEventsModelArrayList;
    Context context;

    public UpcomingEventAdapter(ArrayList<UpcomingEventsModel> upcomingEventsModelArrayList, Context context) {
        this.upcomingEventsModelArrayList = upcomingEventsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UpcomingEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.upcoming_events_item, parent, false);
        return new UpcomingEventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingEventAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return upcomingEventsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Date,eventTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.message);
            eventTitle = itemView.findViewById(R.id.status);
        }
    }
}
