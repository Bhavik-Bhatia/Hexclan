package com.example.sih_app.Student;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sih_app.R;
import com.example.sih_app.Student.Models.RequestLeaveStudentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RequestLeaveStudent extends AppCompatActivity {

    private EditText editTextFromDate,editTextToDate,editTextTextMultiLine;
    private Button LeaveRequest;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private LottieAnimationView leaveRequestStudentAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_leave_student);
        init();
        onclick();
    }

    private void init(){
        editTextFromDate=findViewById(R.id.editTextFromDate);
        editTextToDate=findViewById(R.id.editTextToDate);
        editTextTextMultiLine=findViewById(R.id.editTextTextMultiLine);
        LeaveRequest=findViewById(R.id.LeaveRequest);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference("leaveRequests");
        leaveRequestStudentAnimation=findViewById(R.id.leaveRequestStudentAnimation);
    }

    private void onclick(){
        editTextFromDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            final int year=calendar.get(Calendar.YEAR);
            final int month=calendar.get(Calendar.MONTH);
            final int day=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(RequestLeaveStudent.this, (view, year1, month1, dayOfMonth) -> {
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
                editTextFromDate.setText(date);
                editTextToDate.setText("");
            },year,month,day);
            Calendar today = Calendar.getInstance();
            long now = today.getTimeInMillis();
            datePickerDialog.getDatePicker().setMinDate(now);
            datePickerDialog.show();
        });
        editTextToDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            final int year=calendar.get(Calendar.YEAR);
            final int month=calendar.get(Calendar.MONTH);
            final int day=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(RequestLeaveStudent.this, (view, year1, month1, dayOfMonth) -> {
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
                editTextToDate.setText(date);
            },year,month,day);
            if (editTextFromDate.getText().toString().equals("")){
                Calendar today = Calendar.getInstance();
                long now = today.getTimeInMillis();
                datePickerDialog.getDatePicker().setMinDate(now);

            }else {
                String givenDateString = editTextFromDate.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
                try {
                    Date mDate = sdf.parse(givenDateString);
                    long timeInMilliseconds = Objects.requireNonNull(mDate).getTime();
                    datePickerDialog.getDatePicker().setMinDate(timeInMilliseconds);
                } catch (ParseException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
            datePickerDialog.show();
        });
        LeaveRequest.setOnClickListener(v -> {
            leaveRequestStudentAnimation.setVisibility(View.VISIBLE);
            if (editTextToDate.getText().toString().equals("")||editTextFromDate.getText().toString().equals("")||editTextTextMultiLine.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Please provide all inputs",Toast.LENGTH_SHORT).show();
                leaveRequestStudentAnimation.setVisibility(View.GONE);
            }else {
                putLeaveRequest(editTextFromDate.getText().toString(),editTextToDate.getText().toString(),editTextTextMultiLine.getText().toString());
                editTextToDate.setText("");
                editTextFromDate.setText("");
                editTextTextMultiLine.setText("");
            }
        });
    }

    private void putLeaveRequest(String fromDate,String toDate,String reason) {
        String status="Pending";
        RequestLeaveStudentModel requestLeaveStudentModel = new RequestLeaveStudentModel(fromDate,toDate,reason,status);
        try {
            mDatabase.child(Objects.requireNonNull(mAuth.getUid())).push().setValue(requestLeaveStudentModel).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    leaveRequestStudentAnimation.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Leave requested",Toast.LENGTH_SHORT).show();
                }else {
                    leaveRequestStudentAnimation.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Some issue occurred,please try again later.",Toast.LENGTH_LONG).show();
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