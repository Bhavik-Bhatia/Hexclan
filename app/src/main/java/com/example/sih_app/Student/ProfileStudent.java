package com.example.sih_app.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sih_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileStudent extends AppCompatActivity {

    private CircleImageView profileImg;
    private TextView profileName,profileNumber,profileEmail,profileAddress,profileInstitute,profileParentMobile,profileParentName,profileParentEmail;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);
        init();
        fetchProfileData();
    }

    private void init(){
        profileImg=findViewById(R.id.profileImg);
        profileName=findViewById(R.id.profileName);
        profileNumber=findViewById(R.id.profileNumber);
        profileEmail=findViewById(R.id.profileEmail);
        profileAddress=findViewById(R.id.profileAddress);
        profileInstitute=findViewById(R.id.profileInstitute);
        profileParentMobile=findViewById(R.id.profileParentMobile);
        profileParentName=findViewById(R.id.profileParentName);
        profileParentEmail=findViewById(R.id.profileParentEmail);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference("Profile");
    }
    private void fetchProfileData(){
    try{
       mDatabase.child(Objects.requireNonNull(mAuth.getUid())).addValueEventListener(new ValueEventListener(){
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds:snapshot.getChildren()){
                        if (ds.getKey()!=null) {
                            switch (ds.getKey()) {
                                case "studentName":
                                    profileName.setText(ds.getValue(String.class));
                                    break;
                                case "imagePath":
                                    Picasso.get().load(ds.getValue(String.class)).into(profileImg);
                                    break;
                                case "studentMobile":
                                    profileNumber.setText(String.valueOf(ds.getValue()));
                                    break;
                                case "address":
                                    profileAddress.setText(ds.getValue(String.class));
                                    break;
                                case "studentEmail":
                                    profileEmail.setText(ds.getValue(String.class));
                                    break;
                                case "instituteName":
                                    profileInstitute.setText(ds.getValue(String.class));
                                    break;
                                case "parentMobile":
                                    profileParentMobile.setText(String.valueOf(ds.getValue()));
                                    break;
                                case "parentName":
                                    profileParentName.setText(ds.getValue(String.class));
                                    break;
                                case "parentEmail":
                                    profileParentEmail.setText(ds.getValue(String.class));
                            }
                        }
                }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {}
       });
    }
    catch (NullPointerException e){
        Log.e("NullPointerException",""+e.getLocalizedMessage());
        Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
    }catch (Exception e){
        Log.e("Exception",""+e.getLocalizedMessage());
        Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
    }
}

}