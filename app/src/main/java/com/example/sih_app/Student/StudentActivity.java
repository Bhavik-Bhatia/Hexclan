package com.example.sih_app.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentActivity extends AppCompatActivity {
    private CircleImageView userImage;
    private TextView userName;
    private TextView userMobile;
    private Button setDateBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StudentActivityAdapter studentActivityAdapter;
    private RecyclerView studentActivityRv;
    private ArrayList<StudentActivityModel> studentActivityModelArrayList;
    private LottieAnimationView studentActivityLoadingAnimation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        init();
        setProfile();
        getStudentActivityDataOfCurrentDate(setDateBtn.getText().toString());
        onclick();
    }

    private void init(){
        userImage=findViewById(R.id.userImage);
        userName=findViewById(R.id.userName);
        userMobile=findViewById(R.id.userMobile);
        setDateBtn=findViewById(R.id.DatePicker);
        studentActivityLoadingAnimation2=findViewById(R.id.studentActivityLoadingAnimation2);
        studentActivityRv = findViewById(R.id.StudentActivityRv);
        studentActivityRv.setLayoutManager(new LinearLayoutManager(this));
        studentActivityModelArrayList=new ArrayList<>();
        studentActivityAdapter=new StudentActivityAdapter(studentActivityModelArrayList,StudentActivity.this);
        studentActivityRv.setAdapter(studentActivityAdapter);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        setDateBtn.setText(simpleDateFormat.format(date));
    }

    private void onclick() {
        setDateBtn.setOnClickListener(v -> {
            studentActivityLoadingAnimation2.setVisibility(View.VISIBLE);
            studentActivityRv.setVisibility(View.GONE);
            Calendar calendar = Calendar.getInstance();
            final int year=calendar.get(Calendar.YEAR);
            final int month=calendar.get(Calendar.MONTH);
            final int day=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(StudentActivity.this, (view, year1, month1, dayOfMonth) -> {
                month1=month1+1;
                String date="";
                if(month1 < 10){
                    date = dayOfMonth+"/0"+month1+"/"+year1;
                }
                if(dayOfMonth < 10){
                    date = "0"+dayOfMonth+"/"+month1+"/"+year1;
                }
                if (dayOfMonth>10 && month1>10){
                    date = dayOfMonth+"/"+month1+"/"+year1;
                }
                setDateBtn.setText(date);
                getStudentActivityDataFromNewDate(date);
            },year,month,day);
            Calendar today = Calendar.getInstance();
            long now = today.getTimeInMillis();
            datePickerDialog.getDatePicker().setMaxDate(now);
            datePickerDialog.show();
        });
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
                            }else if (ds.getKey().equals("studentMobile")){
                                userMobile.setText(String.valueOf(ds.getValue()));
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

    private void getDataViaStudentProfile(String Date){
        try {
            mDatabase.child("studentActivity").child(Objects.requireNonNull(mAuth.getUid())).child(Date).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    studentActivityModelArrayList.clear();
                    if (snapshot.hasChildren()){
                        studentActivityLoadingAnimation2.setVisibility(View.GONE);
                        studentActivityRv.setVisibility(View.VISIBLE);
                        for (DataSnapshot ds:snapshot.getChildren()){
                            StudentActivityModel tempObj=new StudentActivityModel(ds.getKey(),ds.getValue(String.class));
                            studentActivityModelArrayList.add(tempObj);
                        }
                    }else {
                        studentActivityLoadingAnimation2.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
                    }
                    studentActivityAdapter.notifyDataSetChanged();
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


    private void getStudentActivityDataFromNewDate(String newDate) {
        String newCurrDate=newDate.replace('/','-');
        studentActivityModelArrayList.clear();
        getDataViaStudentProfile(newCurrDate);
    }
    private void getStudentActivityDataOfCurrentDate(String currDate){
        String newCurrDate=currDate.replace('/','-');
        Log.d("Curr Date",newCurrDate+"");
        getDataViaStudentProfile(newCurrDate);
    }
}