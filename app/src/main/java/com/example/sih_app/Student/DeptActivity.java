package com.example.sih_app.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sih_app.R;
import com.example.sih_app.Student.Adapters.StudentActivityAdapter;
import com.example.sih_app.Student.Models.StudentActivityModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeptActivity extends AppCompatActivity {
    private CircleImageView userImage;
    private TextView userName;
    private Button setDateBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StudentActivityAdapter deptActivityAdapter;
    private RecyclerView deptActivityRv;
    private ArrayList<StudentActivityModel> deptActivityModelArrayList;
    private LottieAnimationView deptActivityLoadingAnimation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept);
        init();
        setProfile();
    }
    private void init(){
        userImage=findViewById(R.id.userImage);
        userName=findViewById(R.id.userName);
        setDateBtn=findViewById(R.id.DatePicker);
        deptActivityLoadingAnimation2=findViewById(R.id.studentActivityLoadingAnimation2);
        deptActivityRv = findViewById(R.id.StudentActivityRv);
        deptActivityRv.setLayoutManager(new LinearLayoutManager(this));
        deptActivityModelArrayList=new ArrayList<>();
        deptActivityAdapter=new StudentActivityAdapter(deptActivityModelArrayList,DeptActivity.this);
        deptActivityRv.setAdapter(deptActivityAdapter);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        setDateBtn.setText(simpleDateFormat.format(date));
    }

    private void setProfile() {
        try {
            mDatabase.child("Profile").child(Objects.requireNonNull(mAuth.getUid())).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds:snapshot.getChildren()){
                        if (ds.getKey()!=null){
                            if (ds.getKey().equals("studentName")){
                                userName.setText(ds.getValue(String.class));
                            }else if(ds.getKey().equals("imagePath")){
                                Picasso.get().load(ds.getValue(String.class)).into(userImage);
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

    private void getStudentActivityDataOfCurrentDate(String currDate){
        String newCurrDate=currDate.replace('/','-');
        Log.d("Curr Date",newCurrDate+"");
        try {
            mDatabase.child("Profile").child(Objects.requireNonNull(mAuth.getUid())).child("loggedBy").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    String loggedBy=String.valueOf(task.getResult().getValue());
                    if (loggedBy.equals("0")){
                        //Student
                        getDataViaStudentProfile(newCurrDate);
                    }else {
                        //Parent
                        getDataViaParentProfile(newCurrDate);
                    }
                }
            });
        }catch (NullPointerException e){
            Log.e("NullPointerException",""+e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Exception",""+e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataViaStudentProfile(String Date){
        try {
            mDatabase.child("departmentActivity").child(Objects.requireNonNull(mAuth.getUid())).child(Date).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    deptActivityModelArrayList.clear();
                    if (snapshot.hasChildren()){
                        deptActivityLoadingAnimation2.setVisibility(View.GONE);
                        deptActivityRv.setVisibility(View.VISIBLE);
                        for (DataSnapshot ds:snapshot.getChildren()){
                            StudentActivityModel tempObj=new StudentActivityModel(ds.getKey(),ds.getValue(String.class));
                            deptActivityModelArrayList.add(tempObj);
                        }
                    }else {
                        deptActivityLoadingAnimation2.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
                    }
                    deptActivityAdapter.notifyDataSetChanged();
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

    private void getDataViaParentProfile(String Date){
        try {
            mDatabase.child("Profile").child(Objects.requireNonNull(mAuth.getUid())).child("studentUID").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    mDatabase.child("departmentActivity").child(Objects.requireNonNull(task.getResult().getValue(String.class))).child(Date).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            deptActivityModelArrayList.clear();
                            if (snapshot.hasChildren()){
                                deptActivityLoadingAnimation2.setVisibility(View.GONE);
                                deptActivityRv.setVisibility(View.VISIBLE);
                                for (DataSnapshot ds:snapshot.getChildren()){
                                    StudentActivityModel tempObj=new StudentActivityModel(ds.getKey(),ds.getValue(String.class));
                                    deptActivityModelArrayList.add(tempObj);
                                }
                            }else {
                                deptActivityLoadingAnimation2.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
                            }
                            deptActivityAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            });
        }catch (NullPointerException e){
            Log.e("NullPointerException",""+e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Exception",""+e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
        }
    }

    private void getStudentActivityDataFromNewDate(String newDate) {
        String newCurrDate=newDate.replace('/','-');
        deptActivityModelArrayList.clear();
        Log.d("New Date",newCurrDate+"");
        try {
            mDatabase.child("Profile").child(Objects.requireNonNull(mAuth.getUid())).child("loggedBy").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    String loggedBy=String.valueOf(task.getResult().getValue());
                    if (loggedBy.equals("0")){
                        //Student
                        getDataViaStudentProfile(newCurrDate);
                    }else {
                        //Parent
                        getDataViaParentProfile(newCurrDate);
                    }
                }
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