package com.example.sih_app.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sih_app.R;
import com.example.sih_app.Student.Adapters.NotificationsAdapter;
import com.example.sih_app.Student.Models.NotificationsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Notifications extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private NotificationsAdapter notificationsAdapter;
    private ArrayList<NotificationsModel> notificationsModelArrayList;
    private LottieAnimationView notificationLoadingAnimation;
    private RecyclerView notificationsRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        init();
    }
    private void init(){
        notificationsRv = findViewById(R.id.NotificationRv);
        notificationLoadingAnimation=findViewById(R.id.notificationLoadingAnimation);
        notificationsRv.setLayoutManager(new LinearLayoutManager(this));
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference("leaveRequests");
        notificationsModelArrayList=new ArrayList<>();
        notificationsAdapter=new NotificationsAdapter(notificationsModelArrayList,Notifications.this);
        notificationsRv.setAdapter(notificationsAdapter);
        notificationLoadingAnimation.setVisibility(View.VISIBLE);
        notificationsRv.setVisibility(View.GONE);
        getLeaveRequestsData();
    }
    private void getLeaveRequestsData(){
        try {
            mDatabase.child(Objects.requireNonNull(mAuth.getUid())).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChildren()){
                        notificationLoadingAnimation.setVisibility(View.GONE);
                        notificationsRv.setVisibility(View.VISIBLE);
                        for (DataSnapshot ds:snapshot.getChildren()){
                            NotificationsModel obj=new NotificationsModel(ds.child("status").getValue(String.class),
                                    ds.child("fromDate").getValue(String.class),ds.child("toDate").getValue(String.class),ds.child("reason").getValue(String.class));
                            notificationsModelArrayList.add(obj);
                        }
                        Collections.reverse(notificationsModelArrayList);
                        notificationsAdapter.notifyDataSetChanged();
                    }else {
                        notificationLoadingAnimation.setVisibility(View.GONE);
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
}