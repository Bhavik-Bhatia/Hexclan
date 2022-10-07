package com.example.sih_app.Student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sih_app.R;
import com.example.sih_app.Student.Models.AttendanceModel;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    ArrayList<AttendanceModel> attendanceModelArrayList;
    Context context;

    public AttendanceAdapter(ArrayList<AttendanceModel> attendanceModelArrayList, Context context) {
        this.attendanceModelArrayList = attendanceModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.attendance_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
        if (attendanceModelArrayList.get(position).getStatus().startsWith("present")){
            holder.subject.setText(attendanceModelArrayList.get(position).getSubject());
            holder.status.setText(attendanceModelArrayList.get(position).getStatus());
            holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.green));
        }else if (attendanceModelArrayList.get(position).getStatus().startsWith("absent")){
            holder.subject.setText(attendanceModelArrayList.get(position).getSubject());
            holder.status.setText(attendanceModelArrayList.get(position).getStatus());
            holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return attendanceModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        TextView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.Subject);
            status=itemView.findViewById(R.id.status);
        }
    }
}
