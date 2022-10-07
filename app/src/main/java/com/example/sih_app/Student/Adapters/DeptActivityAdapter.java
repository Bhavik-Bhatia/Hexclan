package com.example.sih_app.Student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sih_app.R;
import com.example.sih_app.Student.Models.StudentActivityModel;
import java.util.ArrayList;

public class DeptActivityAdapter extends RecyclerView.Adapter<DeptActivityAdapter.ViewHolder> {
    ArrayList<StudentActivityModel> deptActivityModelArrayList;
    Context context;

    public DeptActivityAdapter(ArrayList<StudentActivityModel> deptActivityModelArrayList, Context context) {
        this.deptActivityModelArrayList = deptActivityModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.dept_activity_item, parent, false);
        return new DeptActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeptActivityAdapter.ViewHolder holder, int position) {
        if (deptActivityModelArrayList.get(position).getStatus().equals("In")){
            holder.time.setText(deptActivityModelArrayList.get(position).getTime());
            holder.status.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.ic_baseline_arrow_in));
        }else if (deptActivityModelArrayList.get(position).getStatus().equals("Out")){
            holder.time.setText(deptActivityModelArrayList.get(position).getTime());
            holder.status.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.ic_baseline_arrow_out));
        }
    }

    @Override
    public int getItemCount() {
        return deptActivityModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.Time);
            status = itemView.findViewById(R.id.Status);
        }
    }
}
