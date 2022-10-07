package com.example.sih_app.Parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sih_app.Parent.Adapters.LeaveRequestParentAdapter;
import com.example.sih_app.Parent.Models.LeaveRequestParentModel;
import com.example.sih_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class LeaveRequestsParent extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private LeaveRequestParentAdapter leaveRequestParentAdapter;
    private ArrayList<LeaveRequestParentModel> leaveRequestParentModelArrayList;
    private LottieAnimationView leaveRequestLoadingAnimation;
    private RecyclerView LeaveRequestParentRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_requests_parent);
        init();
    }

    private void init(){
        LeaveRequestParentRv = findViewById(R.id.LeaveRequestParentRv);
        LeaveRequestParentRv.setLayoutManager(new LinearLayoutManager(this));
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        leaveRequestParentModelArrayList=new ArrayList<>();
        leaveRequestParentAdapter=new LeaveRequestParentAdapter(leaveRequestParentModelArrayList, LeaveRequestsParent.this);
        LeaveRequestParentRv.setAdapter(leaveRequestParentAdapter);
        leaveRequestLoadingAnimation=findViewById(R.id.leaveRequestLoadingAnimation);
        leaveRequestLoadingAnimation.setVisibility(View.VISIBLE);
        LeaveRequestParentRv.setVisibility(View.GONE);
        leaveRequestDataForParent();
    }


    private void leaveRequestDataForParent(){

        try {
            mDatabase.child("leaveRequests").child(Objects.requireNonNull(mAuth.getUid())).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    leaveRequestParentModelArrayList.clear();
                    if (snapshot.hasChildren()){
                        leaveRequestLoadingAnimation.setVisibility(View.GONE);
                        LeaveRequestParentRv.setVisibility(View.VISIBLE);
                        for (DataSnapshot ds:snapshot.getChildren()){
                            LeaveRequestParentModel obj=new LeaveRequestParentModel(ds.child("status").getValue(String.class),
                                    ds.child("fromDate").getValue(String.class),ds.child("toDate").getValue(String.class),ds.child("reason").getValue(String.class),ds.getKey());
                            leaveRequestParentModelArrayList.add(obj);
                        }
                        Collections.reverse(leaveRequestParentModelArrayList);
                        leaveRequestParentAdapter.notifyDataSetChanged();
                    }else {
                        leaveRequestLoadingAnimation.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }catch (NullPointerException e){
            Log.e("NullPointerException",""+e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Exception",""+e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
        }
    }
    public void updateLeaveRequestStatus(String status, String leaveRequestID, Context context){
        DatabaseReference mDb = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        try {
            mDb.child("leaveRequests").child(Objects.requireNonNull(mAuth.getUid())).child(leaveRequestID).child("status").setValue(status).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(context,String.format("Leave Request %s",status),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"Task failed ",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (NullPointerException e){
            Log.e("NullPointerException",""+e.getLocalizedMessage());
            Toast.makeText(context, "Failure occurred please try later",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Exception",""+e.getLocalizedMessage());
            Toast.makeText(context, "Failure occurred please try later",Toast.LENGTH_SHORT).show();
        }
    }
}