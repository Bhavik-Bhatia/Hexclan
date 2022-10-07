package com.example.sih_app.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.example.sih_app.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeActivityStudent extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CardView AttendanceView,RequestLeaveView,StudentActivityView,totalAttendanceView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView profileNameDrawer;
    private CircleImageView profileImgDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_student);
        init();
        onclick();
    }
    @Override
    protected void onStart() {
        super.onStart();
        try {
            mDatabase.child(Objects.requireNonNull(mAuth.getUid())).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds:snapshot.getChildren()){
                        if (ds.getKey()!=null){
                            if (ds.getKey().equals("studentName")){
                                profileNameDrawer.setText(ds.getValue(String.class));
                            }else if(ds.getKey().equals("imagePath")){
                                Picasso.get().load(ds.getValue(String.class)).into(profileImgDrawer);
                            }
                        }
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
    private void init(){
        AttendanceView=findViewById(R.id.AttendanceView);
        RequestLeaveView=findViewById(R.id.RequestLeaveView);
        StudentActivityView=findViewById(R.id.StudentActivityView);
        totalAttendanceView=findViewById(R.id.totalAttendanceView);
        drawerLayout = findViewById(R.id.drawlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        navigationView=findViewById(R.id.navmenu);
        profileNameDrawer =navigationView.getHeaderView(0).findViewById(R.id.profileNameDrawer);
        profileImgDrawer = navigationView.getHeaderView(0).findViewById(R.id.profileImgDrawer);
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
                startActivity(new Intent(WelcomeActivityStudent.this,ProfileStudent.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(item.getItemId()==R.id.Notifications){
                startActivity(new Intent(WelcomeActivityStudent.this,Notifications.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(item.getItemId()==R.id.Logout){
                DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("loggedBy");
                try {
                    mDatabase.child(Objects.requireNonNull(mAuth.getUid())).child("student").setValue("false");
                }catch (Exception e){
                    e.printStackTrace();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                finish();
            }
            return false;
        });
        AttendanceView.setOnClickListener(v -> startActivity(new Intent(WelcomeActivityStudent.this,AttendanceActivity.class)));
        RequestLeaveView.setOnClickListener(v -> startActivity(new Intent(WelcomeActivityStudent.this,RequestLeaveStudent.class)));
        StudentActivityView.setOnClickListener(v -> startActivity(new Intent(WelcomeActivityStudent.this,StudentActivity.class)));
        totalAttendanceView.setOnClickListener(v -> startActivity(new Intent(WelcomeActivityStudent.this,totalAttendance.class)));
    }
}