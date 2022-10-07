package com.example.sih_app.Splash;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.sih_app.Parent.ParentLogin;
import com.example.sih_app.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this::goToParentLogin,2000);
    }
    private void goToParentLogin(){
        Intent i = new Intent(SplashActivity.this, ParentLogin.class);
        startActivity(i);
        finish();
    }
}