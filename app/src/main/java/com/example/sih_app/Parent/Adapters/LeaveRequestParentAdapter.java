package com.example.sih_app.Parent.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sih_app.Parent.LeaveRequestsParent;
import com.example.sih_app.Parent.Models.LeaveRequestParentModel;
import com.example.sih_app.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LeaveRequestParentAdapter extends RecyclerView.Adapter<LeaveRequestParentAdapter.ViewHolder> {
    static ArrayList<LeaveRequestParentModel> leaveRequestParentModelArrayList;
    Context context;
    private SimpleDateFormat sdf;

    public LeaveRequestParentAdapter(ArrayList<LeaveRequestParentModel> leaveRequestParentModelArrayList, Context context) {
        LeaveRequestParentAdapter.leaveRequestParentModelArrayList = leaveRequestParentModelArrayList;
        this.context = context;
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.leave_request_parent_item, parent, false);
        return new LeaveRequestParentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Date currentDate =new Date();
        Date toDate = new Date();

        try {
            currentDate=sdf.parse(getCurrentDate());
            toDate=sdf.parse(leaveRequestParentModelArrayList.get(position).getToDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (leaveRequestParentModelArrayList.get(position).getStatus()) {
            case "Approved":
                holder.message.setText(String.format("Leave request from date %s to date %s has been Approved.Reason for leave, %s", leaveRequestParentModelArrayList.get(position).getFromDate(),
                        leaveRequestParentModelArrayList.get(position).getToDate(), leaveRequestParentModelArrayList.get(position).getMessage()));
                holder.status.setText(holder.itemView.getResources().getString(R.string.ApprovedStatus));
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.green));
                holder.menuLeaveRequestParent.setClickable(false);
                break;
            case "Rejected":
                holder.message.setText(String.format("Leave request from date %s to date %s has been Rejected.Reason for leave, %s", leaveRequestParentModelArrayList.get(position).getFromDate(),
                        leaveRequestParentModelArrayList.get(position).getToDate(), leaveRequestParentModelArrayList.get(position).getMessage()));
                holder.status.setText(holder.itemView.getResources().getString(R.string.RejectedStatus));
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.red));
                holder.menuLeaveRequestParent.setClickable(false);
                break;
            case "Pending":
                if (currentDate!=null && toDate!=null){
                    if (toDate.before(currentDate)){
                        holder.message.setText(String.format("The leave request from date %s to date %s is now inaccessible.Reason stated, %s",leaveRequestParentModelArrayList.get(position).getFromDate(),
                                leaveRequestParentModelArrayList.get(position).getToDate(),leaveRequestParentModelArrayList.get(position).getMessage()));
                        holder.status.setText(holder.itemView.getResources().getString(R.string.Inaccessible));
                        holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.grey));
                        holder.menuLeaveRequestParent.setClickable(false);
                    }else {
                        holder.message.setText(String.format("Leave request from date %s to date %s is Pending.Reason for leave, %s", leaveRequestParentModelArrayList.get(position).getFromDate(),
                                leaveRequestParentModelArrayList.get(position).getToDate(), leaveRequestParentModelArrayList.get(position).getMessage()));
                        holder.status.setText(holder.itemView.getResources().getString(R.string.PendingStatus));
                        holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
                        holder.menuLeaveRequestParent.setClickable(true);
                    }
                }else {
                    holder.message.setText(String.format("Leave request from date %s to date %s is Pending.Reason for leave, %s", leaveRequestParentModelArrayList.get(position).getFromDate(),
                            leaveRequestParentModelArrayList.get(position).getToDate(), leaveRequestParentModelArrayList.get(position).getMessage()));
                    holder.status.setText(holder.itemView.getResources().getString(R.string.PendingStatus));
                    holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
                    holder.menuLeaveRequestParent.setClickable(true);
                }
                break;
        }
    }

    private String getCurrentDate() {
        return sdf.format(new Date());
    }

    @Override
    public int getItemCount() {
        return leaveRequestParentModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView message,status;
        ImageView menuLeaveRequestParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuLeaveRequestParent=itemView.findViewById(R.id.menuLeaveRequestParent);
            status=itemView.findViewById(R.id.status);
            message=itemView.findViewById(R.id.message);
            menuLeaveRequestParent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.leave_requests_parent_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            LeaveRequestsParent obj = new LeaveRequestsParent();
            String requestID=LeaveRequestParentAdapter.leaveRequestParentModelArrayList.get(getAdapterPosition()).getLeaveRequestID();
            if (item.getItemId()==R.id.Approve){
                obj.updateLeaveRequestStatus("Approved",requestID,itemView.getContext());
                return true;
            }else if(item.getItemId()==R.id.Reject){
                obj.updateLeaveRequestStatus("Rejected",requestID,itemView.getContext());
                return true;
            }else {
                return false;
            }
        }
    }
}
