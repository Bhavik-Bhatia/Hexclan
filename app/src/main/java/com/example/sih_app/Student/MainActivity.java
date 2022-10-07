package com.example.sih_app.Student;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.example.sih_app.Parent.ParentLogin;
import com.example.sih_app.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private TextInputLayout uNameTf,passTf;
    private FirebaseAuth mAuth;
    private LottieAnimationView loginLoadingAnimation;
    private TextView SignInWithParent;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onclick();
    }
    @Override
    protected void onStart() {
        super.onStart();
        makeLoadingBarVisible();
            //Check for if Parent was Logged In or Student
            try {
                mDatabase.child(Objects.requireNonNull(mAuth.getUid())).child("student").get().
                        addOnCompleteListener(task -> {
                            if (String.valueOf(task.getResult().getValue()).equals("true")){
                                    goToWelcomeScreen();
                            }else {
                                makeLoadingBarGone();
                            }
                        });
            } catch (Exception e){
                makeLoadingBarGone();
                e.printStackTrace();
            }
        }
    private void onclick() {
        loginBtn.setOnClickListener(v -> {
            if (Objects.requireNonNull(uNameTf.getEditText()).getText().toString().equals("") ||uNameTf.getEditText().getText()==null|| Objects.requireNonNull(passTf.getEditText()).getText()==null||passTf.getEditText().getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Fill all Fields",Toast.LENGTH_SHORT).show();
            }else {
                loginLoadingAnimation.setVisibility(View.VISIBLE);
                loginUser(uNameTf.getEditText().getText().toString(),passTf.getEditText().getText().toString());
            }
        });
        SignInWithParent.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ParentLogin.class));
            finish();
        });
    }
    private void init(){
        loginBtn=findViewById(R.id.loginBtn);
        uNameTf=findViewById(R.id.uNameTf);
        passTf=findViewById(R.id.passTf);
        mAuth=FirebaseAuth.getInstance();
        loginLoadingAnimation = findViewById(R.id.LoginLoadingAnimation);
        SignInWithParent=findViewById(R.id.SignInWithParent);
        mDatabase= FirebaseDatabase.getInstance().getReference("loggedBy");
    }
    private void goToWelcomeScreen(){
        Intent i = new Intent(MainActivity.this, WelcomeActivityStudent.class);
        startActivity(i);
        finish();
    }
    private void loginUser(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
             if (task.isSuccessful()){
                 /* (When credentials are correct) Try Catch, if mAuth is null */
                 try {
                     mDatabase.child(Objects.requireNonNull(mAuth.getUid())).child("student").setValue("true");
                     goToWelcomeScreen();
                 } catch (Exception e){
                     Toast.makeText(getApplicationContext(),"Some error while logging in",Toast.LENGTH_LONG).show();
                     e.printStackTrace();
                 }
             }else {
                 /* (When credentials are incorrect) Try Catch, if task.getException is null */
                 try {
                     loginLoadingAnimation.setVisibility(View.GONE);
                     Toast.makeText(getApplicationContext(), String.format("Failure occurred: %s", Objects.requireNonNull(task.getException()).getMessage()),Toast.LENGTH_SHORT).show();
                 }catch (NullPointerException e){
                     Log.e("NullPointerException",""+e.getLocalizedMessage());
                     Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
                 }catch (Exception e){
                     Log.e("Exception",""+e.getLocalizedMessage());
                     Toast.makeText(getApplicationContext(), "Failure occurred please try later",Toast.LENGTH_SHORT).show();
                 }
             }
        });
    }
    private void makeLoadingBarVisible(){
        loginLoadingAnimation.setVisibility(View.VISIBLE);
        uNameTf.setVisibility(View.GONE);
        passTf.setVisibility(View.GONE);
        SignInWithParent.setVisibility(View.GONE);
        loginBtn.setVisibility(View.GONE);
    }
    private void makeLoadingBarGone(){
        loginLoadingAnimation.setVisibility(View.GONE);
        uNameTf.setVisibility(View.VISIBLE);
        passTf.setVisibility(View.VISIBLE);
        SignInWithParent.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.VISIBLE);
    }
}