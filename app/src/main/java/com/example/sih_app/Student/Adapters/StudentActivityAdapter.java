package com.example.sih_app.Student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sih_app.R;
import com.example.sih_app.Student.Models.StudentActivityModel;
import java.util.ArrayList;

public class StudentActivityAdapter extends RecyclerView.Adapter<StudentActivityAdapter.ViewHolder> {
    ArrayList<StudentActivityModel> studentActivityModelArrayList;
    Context context;

    public StudentActivityAdapter(ArrayList<StudentActivityModel> studentActivityModelArrayList, Context context) {
        this.studentActivityModelArrayList = studentActivityModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_activity_item, parent, false);
        return new StudentActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (studentActivityModelArrayList.get(position).getStatus().startsWith("In")){
            holder.time.setText(studentActivityModelArrayList.get(position).getTime());
            holder.status.setText(studentActivityModelArrayList.get(position).getStatus());
            holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.green));
        }else if (studentActivityModelArrayList.get(position).getStatus().startsWith("Out")){
            holder.time.setText(studentActivityModelArrayList.get(position).getTime());
            holder.status.setText(studentActivityModelArrayList.get(position).getStatus());
            holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return studentActivityModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.Time);
            status = itemView.findViewById(R.id.Status);
        }
    }
}
