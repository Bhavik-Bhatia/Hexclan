 package com.example.sih_app.Student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sih_app.R;
import com.example.sih_app.Student.Models.NotificationsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

 public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    ArrayList<NotificationsModel> notificationsModelArrayList;
    Context context;
    SimpleDateFormat sdf;

    public NotificationsAdapter(ArrayList<NotificationsModel> notificationsModelArrayList, Context context) {
        this.notificationsModelArrayList = notificationsModelArrayList;
        this.context = context;
        sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        Date currentDate =new Date();
        Date toDate = new Date();
        try {
            currentDate=sdf.parse(getCurrentDate());
            toDate=sdf.parse(notificationsModelArrayList.get(position).getToDate());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "Some Error occurred!", Toast.LENGTH_SHORT).show();
        }

        switch (notificationsModelArrayList.get(position).getStatus()) {
            case "Approved":
                holder.message.setText(String.format("Your leave from date %s to date %s has been Approved.Reason stated, %s", notificationsModelArrayList.get(position).getFromDate(),
                        notificationsModelArrayList.get(position).getToDate(), notificationsModelArrayList.get(position).getMessage()));
                holder.status.setText(holder.itemView.getResources().getString(R.string.ApprovedStatus));
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.green));
                break;
            case "Rejected":
                holder.message.setText(String.format("Your leave from date %s to date %s has been Approved.Reason stated, %s", notificationsModelArrayList.get(position).getFromDate(),
                        notificationsModelArrayList.get(position).getToDate(), notificationsModelArrayList.get(position).getMessage()));
                holder.status.setText(holder.itemView.getResources().getString(R.string.RejectedStatus));
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.red));
                break;
            case "Pending":
                if (currentDate!=null && toDate!=null){
                    if (currentDate.after(toDate)) {
                        holder.message.setText(String.format("Your leave from date %s to date %s is now inaccessible.Reason stated, %s", notificationsModelArrayList.get(position).getFromDate(),
                                notificationsModelArrayList.get(position).getToDate(), notificationsModelArrayList.get(position).getMessage()));
                        holder.status.setText(holder.itemView.getResources().getString(R.string.Inaccessible));
                        holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.grey));
                    }else {
                        holder.message.setText(String.format("Your leave request from date %s to date %s is Pending.Reason stated, %s", notificationsModelArrayList.get(position).getFromDate(),
                                notificationsModelArrayList.get(position).getToDate(), notificationsModelArrayList.get(position).getMessage()));
                        holder.status.setText(holder.itemView.getResources().getString(R.string.PendingStatus));
                        holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
                    }
                }else {
                    holder.message.setText(String.format("Your leave request from date %s to date %s is Pending.Reason stated, %s", notificationsModelArrayList.get(position).getFromDate(),
                            notificationsModelArrayList.get(position).getToDate(), notificationsModelArrayList.get(position).getMessage()));
                    holder.status.setText(holder.itemView.getResources().getString(R.string.PendingStatus));
                    holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
                }
                break;
        }
    }

     private String getCurrentDate() {
         return sdf.format(new Date());
     }

     @Override
    public int getItemCount() {
        return notificationsModelArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView message,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            status = itemView.findViewById(R.id.status);
        }
    }
}
