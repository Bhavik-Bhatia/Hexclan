package com.example.sih_app.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sih_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Objects;

public class totalAttendance extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private int totalClasses=0;
    private int totalClassesPresent=0;
    private int totalClassesAbsent=0;
    private TextView tvPresent,tvAbsent,tvTotal;
    private PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_attendance);
        init();
        getAttendanceData();
    }

    private void setPie() {
        pieChart.addPieSlice(
                new PieModel(
                        "Present",
                        totalClassesPresent,
                        getResources().getColor(R.color.green)));
        pieChart.addPieSlice(
                new PieModel(
                        "Absent",
                        totalClassesAbsent,
                        getResources().getColor(R.color.red)));
        pieChart.startAnimation();
    }

    private void init(){
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference("attendanceActivity");
        tvPresent=findViewById(R.id.tvPresent);
        tvAbsent=findViewById(R.id.tvAbsent);
        tvTotal=findViewById(R.id.tvTotal);
        pieChart = findViewById(R.id.piechart);
    }
    private void getAttendanceData(){
        try {
            mDatabase.child(Objects.requireNonNull(mAuth.getUid())).addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    totalClasses=0;
                    totalClassesPresent=0;
                    totalClassesAbsent=0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        for (DataSnapshot ds2: ds.getChildren()){
                            totalClasses=totalClasses+1;
                            if (Objects.requireNonNull(ds2.getValue(String.class)).equals("present")){
                                totalClassesPresent=totalClassesPresent+1;
                            }else if (Objects.requireNonNull(ds2.getValue(String.class)).equals("absent")){
                                totalClassesAbsent=totalClassesAbsent+1;
                            }
                        }
                    }
                    tvPresent.setText(String.valueOf(totalClassesPresent));
                    tvAbsent.setText(String.valueOf(totalClassesAbsent));
                    tvTotal.setText(String.valueOf(totalClasses));
                    setPie();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}