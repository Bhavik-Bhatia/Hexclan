package com.example.sih_app.Parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sih_app.R;
import com.example.sih_app.Student.AttendanceActivity;
import com.example.sih_app.Student.ProfileStudent;
import com.example.sih_app.Student.StudentActivity;
import com.example.sih_app.Student.totalAttendance;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

public class WelcomeActivityParent extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CardView AttendanceView,RequestLeaveView,StudentActivityView,totalAttendanceView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView profileNameDrawer;

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mDatabase.child(Objects.requireNonNull(mAuth.getUid())).addValueEventListener(new ValueEventListener() {
                @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getKey() != null) {
                        if (ds.getKey().equals("parentName")) {
                            profileNameDrawer.setText(ds.getValue(String.class));
                        }
                    }
                }
            }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (NullPointerException e) {
            Log.e("NullPointerException", "" + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Failure occurred please try later", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Exception", "" + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Failure occurred please try later", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_parent);
        init();
        onclick();
    }
    private void init(){
        drawerLayout = findViewById(R.id.drawlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navmenu);
        AttendanceView=findViewById(R.id.AttendanceView);
        RequestLeaveView=findViewById(R.id.RequestLeaveView);
        totalAttendanceView=findViewById(R.id.totalAttendanceView);
        StudentActivityView=findViewById(R.id.StudentActivityView);
        profileNameDrawer =navigationView.getHeaderView(0).findViewById(R.id.profileParentNameDrawer);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference("Profile");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void onclick(){
        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId()==R.id.Profile){
                startActivity(new Intent(WelcomeActivityParent.this, ProfileStudent.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(item.getItemId()==R.id.Logout){
                DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("loggedBy");
                try {
                    mDatabase.child(Objects.requireNonNull(mAuth.getUid())).child("parent").setValue("false");
                }catch (Exception e){
                    e.printStackTrace();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                finish();
            }
            return false;
        });
        AttendanceView.setOnClickListener(v -> startActivity(new Intent(WelcomeActivityParent.this, AttendanceActivity.class)));
        RequestLeaveView.setOnClickListener(v -> startActivity(new Intent(WelcomeActivityParent.this, LeaveRequestsParent.class)));
        StudentActivityView.setOnClickListener(v -> startActivity(new Intent(WelcomeActivityParent.this, StudentActivity.class)));
        totalAttendanceView.setOnClickListener(v -> startActivity(new Intent(WelcomeActivityParent.this, totalAttendance.class)));
    }
}